package io.enderdev.emergingtechnology.tiles

import io.enderdev.emergingtechnology.EmergingTechnology
import io.enderdev.emergingtechnology.utils.CapabilityUtils
import io.enderdev.emergingtechnology.utils.EnergyUtils
import net.minecraft.block.BlockDirectional
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumFacing
import net.minecraft.util.ITickable
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.energy.EnergyStorage
import net.minecraftforge.energy.IEnergyStorage
import org.ender_development.catalyx.client.gui.BaseGuiTyped
import org.ender_development.catalyx.tiles.BaseTile
import org.ender_development.catalyx.tiles.helper.IEnergyTile

class TileBattery : BaseTile(EmergingTechnology.catalyxSettings), IEnergyTile, BaseGuiTyped.IDefaultButtonVariables, ITickable {
	// only here so I can make GuiBattery extend BaseETGuiTyped<TileBattery>
	override var isPaused = false
	override var needsRedstonePower = false

	var netInput = 0
		private set
	var netOutput = 0
		private set

	private companion object {
		// if you update this, also update the 'tile.emergingtechnology:battery.desc' translation, if you change this to a config option, I'd recommend making a util function that takes this in and spits out `100 k`/etc. (you can find an example impl in TOP)
		private const val ENERGY_CAPACITY = 100000
	}

	override val energyStorage: IEnergyStorage = object : EnergyStorage(ENERGY_CAPACITY) {
		override fun receiveEnergy(maxReceive: Int, simulate: Boolean): Int {
			val result = super.receiveEnergy(maxReceive, simulate)
			if(!simulate)
				netInput += result
			return result
		}

		override fun extractEnergy(maxExtract: Int, simulate: Boolean): Int {
			val result = super.extractEnergy(maxExtract, simulate)
			if(!simulate)
				netOutput += result
			return result
		}
	}

	override val energyCapacity = ENERGY_CAPACITY

	override fun update() {
		CapabilityUtils.spreadEnergy(world, pos, energyStorage, *EnumFacing.VALUES.filter { it != inputSide }.toTypedArray())
		markDirtyGUI()
	}

	val inputSide: EnumFacing // don't wanna inline this once in case a wrench mod or something rotates the block
		get() = world.getBlockState(pos).getValue(BlockDirectional.FACING)

	val extractOnlyWrapper = EnergyUtils.ExtractOnlyEnergyStorage(energyStorage)
	val receiveOnlyWrapper = EnergyUtils.ReceiveOnlyEnergyStorage(energyStorage)

	override fun <T : Any> getCapability(capability: Capability<T>, facing: EnumFacing?) =
		if(capability == ENERGY_CAP)
			ENERGY_CAP.cast(if(facing == inputSide) receiveOnlyWrapper else extractOnlyWrapper)
		else
			super.getCapability(capability, facing)

	override fun writeToNBT(compound: NBTTagCompound): NBTTagCompound {
		super.writeToNBT(compound)
		if(!world.isRemote) {
			compound.setInteger("NetInput", netInput)
			compound.setInteger("NetOutput", netOutput)
			netInput = 0
			netOutput = 0
		}
		return compound
	}

	override fun readFromNBT(compound: NBTTagCompound) {
		super.readFromNBT(compound)
		if(world?.isRemote == true) { // world is null on first load
			netInput = compound.getInteger("NetInput")
			netOutput = compound.getInteger("NetOutput")
		}
	}
}
