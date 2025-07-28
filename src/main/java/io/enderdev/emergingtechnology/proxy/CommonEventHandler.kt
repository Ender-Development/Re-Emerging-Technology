package io.enderdev.emergingtechnology.proxy

import io.enderdev.emergingtechnology.worldgen.OreGeneration
import net.minecraftforge.event.world.ChunkDataEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

class CommonEventHandler {
	@SubscribeEvent
	fun chunkSave(ev: ChunkDataEvent.Save) =
		OreGeneration.instance.chunkSave(ev)

	@SubscribeEvent
	fun chunkLoad(ev: ChunkDataEvent.Load) =
		OreGeneration.instance.chunkLoad(ev)
}
