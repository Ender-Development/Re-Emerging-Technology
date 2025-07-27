package io.enderdev.emergingtechnology.tiles

import com.google.common.collect.ImmutableMap
import io.enderdev.catalyx.tiles.helper.EnergyTileImpl
import io.enderdev.catalyx.tiles.helper.IEnergyTile
import io.enderdev.catalyx.utils.SideUtils
import io.enderdev.emergingtechnology.Tags
import io.enderdev.emergingtechnology.config.EmergingTechnologyConfig
import io.enderdev.emergingtechnology.utils.CapabilityUtils
import io.enderdev.emergingtechnology.utils.EnergyUtils
import net.minecraft.block.BlockHorizontal
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.EnumFacing
import net.minecraft.util.ITickable
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.BlockPos
import net.minecraftforge.client.model.ModelLoaderRegistry
import net.minecraftforge.common.animation.Event
import net.minecraftforge.common.animation.ITimeValue
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.model.IModelState
import net.minecraftforge.common.model.animation.CapabilityAnimation
import net.minecraftforge.common.model.animation.IAnimationStateMachine
import net.minecraftforge.energy.CapabilityEnergy
import org.apache.commons.lang3.tuple.Pair
import java.util.Locale

class TileWindGenerator : TileEntity(), ITickable, IEnergyTile by EnergyTileImpl(10000) {
	override fun update() {
		generate()
		spread()
	}

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
		(capability == CapabilityEnergy.ENERGY && (facing == null || facing == EnumFacing.DOWN)) || capability == CapabilityAnimation.ANIMATION_CAPABILITY

	override fun <T : Any?> getCapability(capability: Capability<T?>, facing: EnumFacing?): T? {
		if(capability == CapabilityAnimation.ANIMATION_CAPABILITY)
			return CapabilityAnimation.ANIMATION_CAPABILITY.cast(asm)

		if(capability != CapabilityEnergy.ENERGY || (facing != null && facing != EnumFacing.DOWN))
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

	// Rendering stuff
	// ASM stands for Animation State Machine btw, not Assembly
	val asm: IAnimationStateMachine = if(!SideUtils.isDedicatedServer) ModelLoaderRegistry.loadASM(ResourceLocation(Tags.MODID, "asms/block/wind_generator.json"), ImmutableMap.of()) else object : IAnimationStateMachine {
		// no-op
		override fun apply(time: Float): Pair<IModelState?, Iterable<Event?>?>? = null
		override fun transition(newState: String?) {}
		override fun currentState() = null
		override fun shouldHandleSpecialEvents(value: Boolean) {}
	}

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
