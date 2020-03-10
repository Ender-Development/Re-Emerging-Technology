package io.moonman.emergingtechnology.recipes.machines;

import java.util.ArrayList;
import java.util.List;

import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.init.ModItems;
import io.moonman.emergingtechnology.recipes.RecipeBuilder;
import io.moonman.emergingtechnology.recipes.classes.IMachineRecipe;
import io.moonman.emergingtechnology.recipes.classes.SimpleRecipe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;

public class AlgaeBioreactorRecipes {

    private static List<IMachineRecipe> algaeBioreactorRecipes = new ArrayList<IMachineRecipe>();

    private static boolean removedAll = false;

    private static List<ItemStack> recipesToRemove = new ArrayList<ItemStack>();

    private static List<String> gasNames = new ArrayList<String>();
    private static List<String> fluidNames = new ArrayList<String>();

    public static List<IMachineRecipe> getRecipes() {
        return algaeBioreactorRecipes;
    }

    public static void add(IMachineRecipe recipe) {
        algaeBioreactorRecipes.add(recipe);
    }

    public static void addGas(String gasName) {
        gasNames.add(gasName);
    }

    public static void addFluid(String fluidName) {
        fluidNames.add(fluidName);
    }

    public static void removeAll() {
        removedAll = true;
    }

    public static ItemStack removeByOutput(ItemStack itemStack) {
        recipesToRemove.add(itemStack);
        return itemStack;
    }

    public static ItemStack getOutputByItemStack(ItemStack itemStack) {
        ItemStack stack = RecipeBuilder.getOutputForItemStackFromRecipes(itemStack, getRecipes());

        if (stack == null) {
            return ItemStack.EMPTY;
        }

        return stack.copy();
    }

    public static boolean isValidInput(ItemStack itemStack) {
        return getOutputByItemStack(itemStack) != null;
    }

    public static IMachineRecipe getRecipeByInputItemStack(ItemStack itemStack) {
        return RecipeBuilder.getMatchingRecipe(itemStack, getRecipes());
    }

    public static void build() {
        
        if (EmergingTechnologyConfig.SYNTHETICS_MODULE.ALGAEBIOREACTOR.disabled || removedAll) return;

        gasNames.add("carbondioxide");
        gasNames.add("carbon_dioxide");
        gasNames.add("co2");

        fluidNames.add("water");
        fluidNames.add("nutrient");
        
        add(new SimpleRecipe(new ItemStack(ModItems.algae, 2), new ItemStack(ModItems.algae, 1)));

        for (ItemStack itemStack : getSlimeItems()) {
            add(new SimpleRecipe(new ItemStack(ModItems.algae, 4), itemStack));
        }

        for (ItemStack itemStack : recipesToRemove) {
            RecipeBuilder.removeRecipesByOutput(algaeBioreactorRecipes, itemStack);
        }
    }

    private static List<ItemStack> getSlimeItems() {
        List<ItemStack> itemInputs = new ArrayList<ItemStack>();

        List<String> oreInputs = new ArrayList<String>();
        oreInputs.add("slimeball");

        List<ItemStack> inputs = RecipeBuilder.buildRecipeList(itemInputs, oreInputs);

        for (ItemStack itemStack : inputs) {
            itemStack.setCount(1);
        }

        return inputs;
    }

    public static List<String> getValidGasNames() {
        return gasNames;
    }

    public static List<String> getValidFluidNames() {
        return fluidNames;
    }

    public static boolean isValidGas(Fluid fluid) {
        if (fluid == null) return false;

        for (String name : gasNames) {
            if (name.equalsIgnoreCase(fluid.getName())) {
                return true;
            }
        }

        return false;
    }
}