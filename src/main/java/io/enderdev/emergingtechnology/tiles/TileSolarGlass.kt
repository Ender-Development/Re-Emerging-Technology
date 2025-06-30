package io.enderdev.emergingtechnology.tiles

import io.enderdev.catalyx.tiles.BaseTile
import io.enderdev.catalyx.tiles.helper.EnergyTileImpl
import io.enderdev.catalyx.tiles.helper.IEnergyTile
import io.enderdev.emergingtechnology.EmergingTechnology
import io.enderdev.emergingtechnology.blocks.ModBlocks
import io.enderdev.emergingtechnology.config.EmergingTechnologyConfig
import io.enderdev.emergingtechnology.utils.CapabilityUtils
import io.enderdev.emergingtechnology.utils.EnergyUtils
import net.minecraft.block.BlockHorizontal
import net.minecraft.init.Blocks
import net.minecraft.util.EnumFacing
import net.minecraft.util.ITickable
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.energy.CapabilityEnergy
import org.apache.logging.log4j.core.tools.picocli.CommandLine.Help.Ansi.Style.off

class TileSolarGlass : BaseTile(EmergingTechnology.catalyxSettings), IEnergyTile by EnergyTileImpl(2500), ITickable {
	val energyPerTick = EmergingTechnologyConfig.ELECTRICS_MODULE.SOLARGLASS.solarEnergyGenerated

	override fun update() {
		generate()

		// funnily enough, original EMT didn't even implement this
		if(EmergingTechnologyConfig.ELECTRICS_MODULE.SOLARGLASS.pushEnergyDown && world.getBlockState(pos.down()).block === ModBlocks.solarGlass)
			(world.getTileEntity(pos.down())?.getCapability(CapabilityEnergy.ENERGY, EnumFacing.UP)!! as? EnergyUtils.ExtractOnlyEnergyStorage)?.let {
				energyStorage.extractEnergy(it.energyStorage.receiveEnergy(energyStorage.energyStored, false), false)
			}

		CapabilityUtils.spreadEnergy(world, pos, energyStorage, EnumFacing.UP, EnumFacing.DOWN)
	}

	fun generate() {
		if(!world.isDaytime || energyStorage.maxEnergyStored == energyStorage.energyStored)
			return

		val facing = world.getBlockState(pos).getValue(BlockHorizontal.FACING)
		if((1..2).any {
			val pos = pos.offset(facing, it)
			val state = world.getBlockState(pos)
			state.block.isAir(state, world, pos) && world.canSeeSky(pos)
		}) {
			val generated = if(world.isThundering || world.isRaining) energyPerTick shr 1 else energyPerTick
			energyStorage.receiveEnergy(generated, false)
		}
	}

	val energyStorageWrapper = EnergyUtils.ExtractOnlyEnergyStorage(energyStorage)

	override fun hasCapability(capability: Capability<*>, facing: EnumFacing?) =
		capability == CapabilityEnergy.ENERGY || super.hasCapability(capability, facing)

	override fun <T : Any> getCapability(capability: Capability<T>, facing: EnumFacing?) =
		if(capability == CapabilityEnergy.ENERGY)
			CapabilityEnergy.ENERGY.cast(energyStorageWrapper)
		else
			super.getCapability(capability, facing)
}
