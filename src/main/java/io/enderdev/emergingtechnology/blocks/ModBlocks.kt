package io.enderdev.emergingtechnology.blocks

import io.enderdev.emergingtechnology.blocks.machine.*
import io.enderdev.emergingtechnology.fluids.ModFluids
import io.enderdev.emergingtechnology.tiles.TileHydroponicGrowLight
import net.minecraft.block.SoundType
import net.minecraft.block.material.Material
import net.minecraft.client.Minecraft
import net.minecraft.init.Blocks
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

object ModBlocks {
	// TODO - CTM support for certain blocks (aquaponic_base, aquaponic_frame, aquaponic_glass)
	// TODO - run optipng once finished :3

	// Hydroponics
	//val hydroponicGrowBed = BlockHydroponicGrowBed() // if implemented, uncomment ModRecipes.kt#L37, ShredderRegister.kt#L22
	val hydroponicGrowLight = BlockHydroponicGrowLight()
	val harvester = BlockHarvester()
	val waterFiller = BlockWaterFiller()
	val co2Scrubber = BlockCo2Scrubber()
	val co2Diffuser = BlockCo2Diffuser()
	val nutrientInjector = BlockNutrientInjector()

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
	val solarCooker = BlockSolarCooker()
	val tissueBioreactor = BlockTissueBioreactor()
	val tissueScaffolder = BlockTissueScaffolder()
	val algaeBioreactor = BlockAlgaeBioreactor()

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

	// Creative
	val creativeFiller = BlockCreativeFiller()

	@SideOnly(Side.CLIENT)
	fun initColours() {
		// original EMT did this by changing texture in blockstate, but this felt like a better solution
		Minecraft.getMinecraft().blockColors.registerBlockColorHandler({ state, world, pos, tintIndex ->
			(world?.getTileEntity(pos ?: return@registerBlockColorHandler -1) as? TileHydroponicGrowLight)?.getColour() ?: -1
		}, hydroponicGrowLight)
	}
}
