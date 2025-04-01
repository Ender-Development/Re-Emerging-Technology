package io.moonman.emergingtechnology.integration.groovyscript.machines;

import com.cleanroommc.groovyscript.api.GroovyBlacklist;
import com.cleanroommc.groovyscript.api.GroovyLog;
import com.cleanroommc.groovyscript.api.IIngredient;
import com.cleanroommc.groovyscript.api.documentation.annotations.*;
import com.cleanroommc.groovyscript.helper.SimpleObjectStream;
import com.cleanroommc.groovyscript.helper.recipe.AbstractRecipeBuilder;
import com.cleanroommc.groovyscript.registry.VirtualizedRegistry;
import io.moonman.emergingtechnology.Tags;
import io.moonman.emergingtechnology.integration.groovyscript.GSPlugin;
import io.moonman.emergingtechnology.recipes.classes.IMachineRecipe;
import io.moonman.emergingtechnology.recipes.classes.SimpleRecipe;
import io.moonman.emergingtechnology.recipes.machines.ProcessorRecipes;
import org.jetbrains.annotations.Nullable;

@RegistryDescription(linkGenerator = Tags.MOD_ID)
public class Processor extends VirtualizedRegistry<IMachineRecipe> {
    @Override
    @GroovyBlacklist
    public void onReload() {
        ProcessorRecipes.getRecipes().removeAll(removeScripted());
        ProcessorRecipes.getRecipes().addAll(restoreFromBackup());
    }

    @MethodDescription(type = MethodDescription.Type.ADDITION)
    public void add(IMachineRecipe recipe) {
        if (recipe != null) {
            addScripted(recipe);
            ProcessorRecipes.add(recipe);
        }
    }

    @MethodDescription(type = MethodDescription.Type.REMOVAL)
    public boolean remove(IMachineRecipe recipe) {
        if (ProcessorRecipes.getRecipes().remove(recipe)) {
            addBackup(recipe);
            return true;
        }
        return false;
    }

    @MethodDescription(type = MethodDescription.Type.REMOVAL)
    public boolean removeByOutput(IIngredient output) {
        return ProcessorRecipes.getRecipes().removeIf(recipe -> {
            if (output.test(recipe.getOutput())) {
                addBackup(recipe);
                return true;
            }
            return false;
        });
    }

    @MethodDescription(type = MethodDescription.Type.REMOVAL)
    public boolean removeByInput(IIngredient input) {
        return ProcessorRecipes.getRecipes().removeIf(recipe -> {
            if (input.test(recipe.getInput())) {
                addBackup(recipe);
                return true;
            }
            return false;
        });
    }

    @MethodDescription(type = MethodDescription.Type.REMOVAL, example = @Example(commented = true))
    public void removeAll() {
        ProcessorRecipes.getRecipes().forEach(this::addBackup);
        ProcessorRecipes.getRecipes().clear();
    }

    @MethodDescription(type = MethodDescription.Type.QUERY)
    public SimpleObjectStream<IMachineRecipe> streamRecipes() {
        return new SimpleObjectStream<>(ProcessorRecipes.getRecipes()).setRemover(this::remove);
    }

    @RecipeBuilderDescription(example = {
            @Example(".input(item('minecraft:apple')).output(item('minecraft:bone'))"),
    })
    public RecipeBuilder getRecipeBuilder() {
        return new RecipeBuilder();
    }

    @Property(property = "input", comp = @Comp(eq = 1))
    @Property(property = "output", comp = @Comp(eq = 1))
    public static class RecipeBuilder extends AbstractRecipeBuilder<IMachineRecipe> {

        @Override
        public String getErrorMsg() {
            return "Error adding Processor recipe";
        }

        @Override
        public void validate(GroovyLog.Msg msg) {
            validateItems(msg, 1,1,1,1);
        }

        @Override
        @Nullable
        @RecipeBuilderRegistrationMethod
        public IMachineRecipe register() {
            if (!validate()) return null;
            IMachineRecipe recipe = new SimpleRecipe(output.get(0), input.get(0).getMatchingStacks()[0]);
            GSPlugin.instance.processor.add(recipe);
            return recipe;
        }
    }
}
