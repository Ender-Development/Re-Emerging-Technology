package io.enderdev.emergingtechnology.blocks

import io.enderdev.emergingtechnology.blocks.machines.AlgaeBioreactorBlock
import net.minecraft.block.SoundType
import net.minecraft.block.material.Material
import net.minecraft.init.Blocks

object ModBlocks {
	// Hydroponics Blocks
	//public static final Hydroponic hydroponic = null; // te
	//public static final Light light = null; // te
	val frameBlock = FrameBlock()
	//public static final Harvester harvester = null; // te
	//public static final Filler filler = null; // te
	//public static final Scrubber scrubber = null; // te
	//public static final Diffuser diffuser = null; // te
	//public static final Injector injector = null; // te
	val aquaponicGlass = GlassBlock("aquaponic_glass")
	val aquaponicBlock = BaseBlock("aquaponic_block")
	val aquaponicBase = BaseBlock("aquaponic_base")

	// Polymers Blocks
	//public static final Processor processor = null; // te
	//public static final Shredder shredder = null; // te
	val plasticBlock = BaseBlock("plastic_block")
	val clearPlasticBlock = GlassBlock("clear_plastic_block")
	val machineCaseBlock = MachineCaseBlock()
	//public static final Fabricator fabricator = null; // te
	//public static final Collector collector = null; // te
	val ladderBlock = LadderBlock()
	val shreddedPlantBlock = BaseBlock("shredded_plant_block", Material.PLANTS, 1f, SoundType.PLANT)
	val shreddedPlasticBlock = BaseBlock("shredded_plastic_block", hardness = 1f)
	val shreddedStarchBlock = BaseBlock("shredded_starch_block", Material.PLANTS, 1f, SoundType.PLANT)

	// Synthetics Blocks
	//public static final Cooker cooker = null; // te
	//public static final Bioreactor bioreactor = null; // te
	//public static final Scaffolder scaffolder = null; // te
	val algaeBioreactor = AlgaeBioreactorBlock() // TODO: te, gui, container

	// Electrics Blocks
	//public static final Piezoelectric piezoelectric = null; // te
	//public static final TidalGenerator tidalgenerator = null; // te
	//public static final Solar solar = null; // te
	//public static final SolarGlass solarglass = null; // te
	//public static final Wind wind = null; // te
	//public static final Battery battery = null; // te
	//public static final BiomassGenerator biomassgenerator = null; // te
	val biocharBlock = BaseBlock("biochar_block", Material.WOOD, 1f, SoundType.WOOD)
	//public static final Optimiser optimiser = null; // te
	val torchBlock = TorchBlock()

	// Fluid Blocks
	//public static final CarbonDioxideBlock carbondioxideblock = null;
	//public static final NutrientBlock nutrientblock = null;

	// Ore Blocks
	val pollutedDirt = PollutedBlock("polluted_dirt", Material.GROUND, SoundType.GROUND, Blocks.DIRT)
	val pollutedSand = PollutedBlock("polluted_sand", Material.SAND, SoundType.SAND, Blocks.SAND)
	val pollutedGravel = PollutedBlock("polluted_gravel", Material.GROUND, SoundType.GROUND, Blocks.GRAVEL)

	val blocks = mutableListOf<BaseBlock>()
}
