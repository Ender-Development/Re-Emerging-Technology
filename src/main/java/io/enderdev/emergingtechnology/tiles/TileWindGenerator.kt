package io.enderdev.emergingtechnology.tiles

import io.enderdev.catalyx.animation.NoopAnimationStateMachine
import io.enderdev.catalyx.tiles.BaseTile.Companion.ANIMATION_CAP
import io.enderdev.catalyx.tiles.BaseTile.Companion.ENERGY_CAP
import io.enderdev.catalyx.tiles.helper.EnergyTileImpl
import io.enderdev.catalyx.tiles.helper.IAnimatedTile
import io.enderdev.catalyx.tiles.helper.IEnergyTile
import io.enderdev.emergingtechnology.Tags
import io.enderdev.emergingtechnology.config.EmergingTechnologyConfig
import io.enderdev.emergingtechnology.utils.CapabilityUtils
import io.enderdev.emergingtechnology.utils.EnergyUtils
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.EnumFacing
import net.minecraft.util.ITickable
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.BlockPos
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.model.animation.IAnimationStateMachine
import java.util.*

class TileWindGenerator : TileEntity(), ITickable, IEnergyTile by EnergyTileImpl(10000), IAnimatedTile {
	override fun update() {
		generate()
		spread()
	}


	//var dirtyTicks = 0
	//var dirtyTickss = 0
	//
	//open fun markDirtyClient() {
	//	markDirty()
	//	val state = world.getBlockState(getPos())
	//	world.notifyBlockUpdate(getPos(), state, state, 3)
	//}
	//
	//open fun markDirtyClientEvery(ticks: Int) {
	//	dirtyTickss++
	//	if(dirtyTickss >= ticks) {
	//		markDirtyClient()
	//		dirtyTickss = 0
	//	}
	//}
	//
	//open fun markDirtyEvery(ticks: Int) {
	//	dirtyTicks++
	//	if(dirtyTicks >= ticks) {
	//		markDirty()
	//		dirtyTicks = 0
	//	}
	//}
	//
	//open fun markDirtyGUI() {
	//	markDirty()
	//	world?.let {
	//		val state = world.getBlockState(getPos())
	//		world.notifyBlockUpdate(pos, state, state, 6)
	//	}
	//}
	//
	//open fun markDirtyGUIEvery(ticks: Int) {
	//	dirtyTicks++
	//	if(dirtyTicks >= ticks) {
	//		markDirtyGUI()
	//		dirtyTicks = 0
	//	}
	//}

	var generated = -1
	var checkDelay = 20

	fun generate() {
		if(generated == -1 || checkDelay-- == 0) {
			checkDelay = 20

			val surroundedAir = BlockPos.getAllInBox(pos.x - 2, pos.y, pos.z - 2, pos.x + 2, pos.y, pos.z + 2).count {
				val state = world.getBlockState(it)
				state.block.isAir(state, world, pos)
			}

			if(surroundedAir < EmergingTechnologyConfig.ELECTRICS_MODULE.WIND.minimumAirBlocks) {
				checkDelay = 40
				generated = 0
				setAnimationState(AnimationState.OFF)
				return
			}

			generated = EmergingTechnologyConfig.ELECTRICS_MODULE.WIND.energyGenerated
			if(pos.y >= EmergingTechnologyConfig.ELECTRICS_MODULE.WIND.minOptimalHeight && pos.y <= EmergingTechnologyConfig.ELECTRICS_MODULE.WIND.maxOptimalHeight) {
				generated = generated shl 1
				setAnimationState(AnimationState.FAST)
			} else
				setAnimationState(AnimationState.SLOW)
		}

		energyStorage.receiveEnergy(generated, false)
	}

	fun spread() = CapabilityUtils.spreadEnergy(world, pos, energyStorage, EnumFacing.DOWN)

	val energyStorageWrapper = EnergyUtils.ExtractOnlyEnergyStorage(energyStorage)

	override fun hasCapability(capability: Capability<*>, facing: EnumFacing?) =
		(capability == ENERGY_CAP && (facing == null || facing == EnumFacing.DOWN)) || capability == ANIMATION_CAP

	override fun <T : Any?> getCapability(capability: Capability<T?>, facing: EnumFacing?): T? {
		if(capability == ANIMATION_CAP)
			return ANIMATION_CAP.cast(asm)

		if(capability != ENERGY_CAP || (facing != null && facing != EnumFacing.DOWN))
			return null

		return ENERGY_CAP.cast(energyStorageWrapper)
	}

	override fun writeToNBT(compound: NBTTagCompound): NBTTagCompound {
		super.writeToNBT(compound)
		compound.setInteger("Energy", energyStorage.energyStored)
		compound.setString("AnimationState", asm.currentState() ?: "off")
		return compound
	}

	override fun readFromNBT(compound: NBTTagCompound) {
		energyStorage.receiveEnergy(compound.getInteger("Energy"), false)
		setAnimationState(AnimationState.valueOf(compound.getString("AnimationState").ifEmpty { "off" }.uppercase(Locale.ENGLISH)))
		super.readFromNBT(compound)
	}

	// Rendering stuff
	// ASM stands for Animation State Machine btw, not Assembly
	override val asm: IAnimationStateMachine = NoopAnimationStateMachine.loadASM(ResourceLocation(Tags.MODID, "asms/block/wind_generator.json"), emptyMap())

	override fun hasFastRenderer() = true

	fun setAnimationState(state: AnimationState) {
		val newState = state.name.lowercase(Locale.ENGLISH)
		if(asm.currentState() != newState)
			asm.transition(newState)
	}

	enum class AnimationState {
		OFF, SLOW, FAST;
	}
}
