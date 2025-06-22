package io.enderdev.emergingtechnology.config.electrics;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.Config.Name;
import net.minecraftforge.common.config.Config.RangeInt;

public class ElectricsModulePiezoelectric {

    @Name("Disable Machine")
    @LangKey("config.emergingtechnology.common.disable.title")
    public boolean disabled = false;

    @Name("Piezoelectric Energy Generated")
    @Config.Comment("How much energy the Piezoelectric tile generates when stepped on.")
    @RangeInt(min = 0, max = Integer.MAX_VALUE)
    public int piezoelectricEnergyGenerated = 1;
    
    @Name("Piezoelectric Step Cooldown")
    @Config.Comment("How long the Piezoelectric waits between steps to generate energy")
    @RangeInt(min = 0, max = Integer.MAX_VALUE)
    public int piezoelectricStepCooldown = 10;
}
