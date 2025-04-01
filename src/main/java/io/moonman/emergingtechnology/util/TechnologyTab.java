package io.moonman.emergingtechnology.util;

import io.moonman.emergingtechnology.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class TechnologyTab extends CreativeTabs {

    public TechnologyTab(String label) {
        super(label);
    }

    @Override
    @NotNull
    public ItemStack createIcon() {
        return new ItemStack(ModItems.hydroponic);
    }

}