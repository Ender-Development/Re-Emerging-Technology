package io.enderdev.emergingtechnology.tiles

import io.enderdev.catalyx.tiles.helper.EnergyTileImpl
import io.enderdev.catalyx.tiles.helper.IEnergyTile
import io.enderdev.emergingtechnology.config.EmergingTechnologyConfig
import io.enderdev.emergingtechnology.utils.EnergyUtils
import net.minecraft.block.BlockHorizontal
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.EnumFacing
import net.minecraft.util.ITickable
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.energy.CapabilityEnergy

class TileSolarPanel : TileEntity(), ITickable, IEnergyTile by EnergyTileImpl(5000) {
	override fun update() {
		generate()
		spread()
	}

	val outputDirection: EnumFacing
		get() = world.getBlockState(pos).getValue(BlockHorizontal.FACING).opposite

	fun generate() {
		if(!world.isDaytime || (false && !world.canBlockSeeSky(pos))) // TODO idk why this doesn't really work (maybe just superflat quirkyness)
			return

		var generated = EmergingTechnologyConfig.ELECTRICS_MODULE.SOLAR.solarEnergyGenerated
		if(world.isThundering || world.isRaining)
			generated = generated shr 1

		energyStorage.receiveEnergy(generated, false)
	}

	fun spread() = EnergyUtils.spreadEnergy(world, pos, energyStorage, outputDirection)

	val energyStorageWrapper = EnergyUtils.ExtractOnlyEnergyStorage(energyStorage)

	override fun hasCapability(capability: Capability<*>, facing: EnumFacing?) =
		capability == CapabilityEnergy.ENERGY && (facing == null || facing == outputDirection)

	override fun <T : Any?> getCapability(capability: Capability<T?>, facing: EnumFacing?): T? {
		if(capability != CapabilityEnergy.ENERGY || (facing != null && facing != outputDirection))
			return null

		return CapabilityEnergy.ENERGY.cast(energyStorageWrapper)
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
