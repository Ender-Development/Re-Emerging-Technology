package io.moonman.emergingtechnology.config.synthetics;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.Config.Name;
import net.minecraftforge.common.config.Config.RangeInt;

public class SyntheticsModuleScaffolder {

    @Name("Disable Machine")
    @LangKey("config.emergingtechnology.common.disable.title")
    public boolean disabled = false;

    @Name("Tissue Scaffolder Energy Usage")
    @Config.Comment("How much energy the Tissue Scaffolder uses per tick.")
    @RangeInt(min = 0, max = 1000)
    public int scaffolderEnergyUsage = 150;

    @Name("Tissue Scaffolder Operation Time")
    @Config.Comment("How long the Tissue Scaffolder takes to create samples from syringes.")
    @RangeInt(min = 0, max = 1000)
    public int scaffolderBaseTimeTaken = 150;
}