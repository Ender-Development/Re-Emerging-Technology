package io.enderdev.emergingtechnology.utils.extensions

import net.minecraft.block.Block
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.Ingredient

fun Block.stack(quantity: Int = 1, meta: Int = 0) = ItemStack(this, quantity, meta)

fun Block.ingredient(quantity: Int = 1, meta: Int = 0): Ingredient = Ingredient.fromStacks(stack(quantity, meta))
