package io.enderdev.emergingtechnology.client.container

import io.enderdev.catalyx.client.container.BaseContainer
import io.enderdev.emergingtechnology.tiles.TileBattery
import net.minecraft.inventory.IInventory

class ContainerBattery(playerInv: IInventory, tile: TileBattery) : BaseContainer(playerInv, tile)
