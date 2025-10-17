package io.enderdev.emergingtechnology.blocks.machine

import io.enderdev.emergingtechnology.EmergingTechnology
import io.enderdev.emergingtechnology.Tags
import io.enderdev.emergingtechnology.client.container.ContainerHydroponicGrowLight
import io.enderdev.emergingtechnology.client.gui.GuiHydroponicGrowLight
import io.enderdev.emergingtechnology.config.EmergingTechnologyConfig
import io.enderdev.emergingtechnology.tiles.TileHydroponicGrowLight
import io.enderdev.emergingtechnology.utils.ItemUtils
import net.minecraft.block.BlockHorizontal
import net.minecraft.block.properties.PropertyBool
import net.minecraft.block.state.BlockStateContainer
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.EntityLivingBase
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import org.ender_development.catalyx.items.TooltipItemBlock
import org.ender_development.catalyx.utils.extensions.translate

class BlockHydroponicGrowLight() : RotatableMachineBlock("hydroponic_grow_light", EmergingTechnology.guiHandler.registerId(TileHydroponicGrowLight::class.java, ContainerHydroponicGrowLight::class.java) { GuiHydroponicGrowLight::class.java }) {
	companion object {
		val LIT: PropertyBool = PropertyBool.create("lit")
	}

	init {
		blockHardness = 1f
		defaultState = blockState.baseState.withProperty(LIT, false)
	}

	override val item =
		TooltipItemBlock(this) { stack, world, flag ->
			ItemUtils.extendedTooltip(
				"tile.${Tags.MODID}:hydroponic_grow_light.desc".translate(EmergingTechnologyConfig.HYDROPONICS_MODULE.GROWLIGHT.lightBlockRange)
			)
		}

	@Deprecated("")
	override fun getLightValue(state: IBlockState) = if(state.getValue(LIT)) 15 else 0

	override fun createBlockState() = BlockStateContainer(this, LIT, BlockHorizontal.FACING)

	override fun getStateForPlacement(world: World, pos: BlockPos, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float, meta: Int, placer: EntityLivingBase, hand: EnumHand): IBlockState =
		super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand).withProperty(LIT, false)

	val aabb = AxisAlignedBB(.0, .125, .0, 1.0, 1.0, 1.0)

	@Deprecated("")
	override fun getBoundingBox(state: IBlockState, source: IBlockAccess, pos: BlockPos) = aabb
}
