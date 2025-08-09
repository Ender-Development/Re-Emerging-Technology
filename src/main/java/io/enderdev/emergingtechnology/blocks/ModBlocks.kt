package io.enderdev.emergingtechnology.blocks

import io.enderdev.catalyx.IBothProvider
import io.enderdev.emergingtechnology.blocks.machine.*
import io.enderdev.emergingtechnology.fluids.ModFluids
import io.enderdev.emergingtechnology.tiles.TileHydroponicGrowLight
import net.minecraft.block.Block
import net.minecraft.block.SoundType
import net.minecraft.block.material.Material
import net.minecraft.client.Minecraft
import net.minecraft.init.Blocks
import net.minecraft.item.Item
import net.minecraft.util.math.BlockPos
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

object ModBlocks {
	val blocks = mutableListOf<IBothProvider>()

	// TODO - CTM support for certain blocks (aquaponic_base, aquaponic_frame, aquaponic_glass)
	// TODO - run optipng once finished :3

	// Hydroponics
	//val hydroponicGrowBed = BlockHydroponicGrowBed()
	val hydroponicGrowLight = BlockHydroponicGrowLight()
	// harvester
	val waterFiller = BlockWaterFiller()
	// co2_scrubber
	// co2_diffuser
	// nutrient_injector

	val aquaponicBase = ModelBlock("aquaponic_base", hardness = 2f)
	val aquaponicFrame = ModelBlock("aquaponic_frame", hardness = 2f)
	val aquaponicGlass = GlassBlock("aquaponic_glass", hardness = 1f)

	// Polymers
	val processor = BlockProcessor()
	val shredder = BlockShredder()
	val fabricator = BlockFabricator()
	val wasteCollector = BlockWasteCollector()

	val machineCase = ModelBlock("machine_case", Material.IRON, SoundType.METAL, 1f)
	val bioplasticLadder = LadderBlock("bioplastic_ladder")

	val plasticBlock = ModelBlock("plastic_block", hardness = 2f)
	val clearPlasticBlock = GlassBlock("clear_plastic_block")

	val shreddedPlantBlock = ModelBlock("shredded_plant_block", Material.PLANTS, SoundType.PLANT, 1f)
	val shreddedPlasticBlock = ModelBlock("shredded_plastic_block", hardness = 1f)
	val shreddedStarchBlock = ModelBlock("shredded_starch_block", Material.PLANTS, SoundType.PLANT, 1f)

	// Synthetics
	// solar_cooker
	val tissueBioreactor = BlockTissueBioreactor()
	val tissueScaffolder = BlockTissueScaffolder()
	// algae_bioreactor

	// Electrics
	val piezoelectricGenerator = BlockPiezoelectricGenerator()
	val tidalGenerator = BlockTidalGenerator()
	val windGenerator = BlockWindGenerator()
	val biomassGenerator = BlockBiomassGenerator()
	val solarPanel = BlockSolarPanel()
	val solarGlass = BlockSolarGlass()
	val battery = BlockBattery()
	val biocharBlock = ModelBlock("biochar_block", Material.WOOD, SoundType.WOOD, 1f)
	val algorithmicOptimiser = BlockAlgorithmicOptimiser()
	val guideLight = BlockGuideLight()

	// Ores
	val pollutedDirt = PollutedBlock("dirt", Blocks.DIRT)
	val pollutedSand = PollutedBlock("sand", Blocks.SAND)
	val pollutedGravel = PollutedBlock("gravel", Blocks.GRAVEL)

	// Fluids
	val nutrientBlock = BaseFluidBlock(ModFluids.nutrient, Material.WATER)
	val co2Block = BaseFluidBlock(ModFluids.co2, Material.WATER)


	fun registerBlocks(event: RegistryEvent.Register<Block>) = blocks.forEach { it.registerBlock(event) }

	fun registerItems(event: RegistryEvent.Register<Item>) = blocks.forEach { it.registerItem(event) }

	@SideOnly(Side.CLIENT)
	fun registerModels() = blocks.forEach { if(it is IHasModel) it.registerModel() }

	@SideOnly(Side.CLIENT)
	fun initColours() {
		// original EMT did this by changing texture in blockstate, but this felt like a better solution
		Minecraft.getMinecraft().blockColors.registerBlockColorHandler({ state, world, pos, tintIndex ->
			(world?.getTileEntity(pos ?: BlockPos.ORIGIN) as? TileHydroponicGrowLight)?.getColour() ?: -1
		}, hydroponicGrowLight)
	}
}
