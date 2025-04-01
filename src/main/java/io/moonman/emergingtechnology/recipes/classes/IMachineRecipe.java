package io.moonman.emergingtechnology.recipes.classes;

import net.minecraft.item.ItemStack;

public interface IMachineRecipe {

   ItemStack getInput();
   ItemStack getOutput();
   
   String getInputOreName();
   boolean hasOreDictInput();

   int getInputCount();
   int getOutputCount();
}