package io.enderdev.emergingtechnology.tiles

import io.enderdev.emergingtechnology.config.EmergingTechnologyConfig
import io.enderdev.emergingtechnology.utils.CapabilityUtils
import io.enderdev.emergingtechnology.utils.EnergyUtils
import net.minecraft.block.BlockHorizontal
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.EnumFacing
import net.minecraft.util.ITickable
import net.minecraftforge.common.capabilities.Capability
import org.ender_development.catalyx.tiles.BaseTile.Companion.ENERGY_CAP
import org.ender_development.catalyx.tiles.helper.EnergyTileImpl
import org.ender_development.catalyx.tiles.helper.IEnergyTile

class TileSolarPanel : TileEntity(), ITickable, IEnergyTile by EnergyTileImpl(5000) {
	override fun update() {
		generate()
		spread()
	}

	val outputDirection: EnumFacing
		get() = world.getBlockState(pos).getValue(BlockHorizontal.FACING).opposite

	fun generate() {
		if(!world.isDaytime || !world.canSeeSky(pos))
			return

		var generated = EmergingTechnologyConfig.ELECTRICS_MODULE.SOLAR.solarEnergyGenerated
		if(world.isThundering || world.isRaining)
			generated = generated shr 1

		energyStorage.receiveEnergy(generated, false)
	}

	fun spread() = CapabilityUtils.spreadEnergy(world, pos, energyStorage, outputDirection, EnumFacing.DOWN)

	val energyStorageWrapper = EnergyUtils.ExtractOnlyEnergyStorage(energyStorage)

	override fun hasCapability(capability: Capability<*>, facing: EnumFacing?) =
		capability == ENERGY_CAP && (facing == null || facing == outputDirection)

	override fun <T : Any?> getCapability(capability: Capability<T?>, facing: EnumFacing?): T? {
		if(capability != ENERGY_CAP || (facing != null && facing != outputDirection))
			return null

		return ENERGY_CAP.cast(energyStorageWrapper)
	}

	override fun writeToNBT(compound: NBTTagCompound): NBTTagCompound {
		super.writeToNBT(compound)
		compound.setInteger("Energy", energyStorage.energyStored)
		return compound
	}

	override fun readFromNBT(compound: NBTTagCompound) {
		energyStorage.receiveEnergy(compound.getInteger("Energy"), false)
		super.readFromNBT(compound)
	}
}
