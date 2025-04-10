package io.enderdev.emergingtechnology.utils.extensions

import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.Ingredient

fun Item.stack(quantity: Int = 1, meta: Int = 0) = ItemStack(this, quantity, meta)

fun Item.ingredient(quantity: Int = 1, meta: Int = 0): Ingredient = Ingredient.fromStacks(stack(quantity, meta))
