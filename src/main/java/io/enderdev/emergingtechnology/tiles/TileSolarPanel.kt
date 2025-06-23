package io.enderdev.emergingtechnology.tiles

import io.enderdev.catalyx.tiles.helper.EnergyTileImpl
import io.enderdev.catalyx.tiles.helper.IEnergyTile
import io.enderdev.emergingtechnology.config.EmergingTechnologyConfig
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.EnumFacing
import net.minecraft.util.ITickable
import net.minecraftforge.energy.CapabilityEnergy

class TileSolarPanel : TileEntity(), ITickable, IEnergyTile by EnergyTileImpl(5000) {
	override fun update() {
		generate()
		spread()
	}

	fun generate() {
		if(!world.isDaytime || (false && !world.canBlockSeeSky(pos))) // TODO idk why this doesn't really work (maybe just superflat quirkyness)
			return

		var generated = EmergingTechnologyConfig.ELECTRICS_MODULE.SOLAR.solarEnergyGenerated
		if(world.isThundering || world.isRaining)
			generated = generated shr 1

		energyStorage.receiveEnergy(generated, false)
	}

	fun spread() {
		for(direction in EnumFacing.VALUES) {
			val te = world.getTileEntity(pos.offset(direction)) ?: continue
			if(!te.hasCapability(CapabilityEnergy.ENERGY, direction))
				continue
			val cap = te.getCapability(CapabilityEnergy.ENERGY, direction)!!
			energyStorage.extractEnergy(cap.receiveEnergy(energyStorage.energyStored, false), false)
			if(energyStorage.energyStored == 0)
				break
		}
	}

	// TODO overwrite getcap to disable receiving energy
	// TODO abstract away/put in a helper spread() and only send energy to the dedicated output side we have on the model

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
