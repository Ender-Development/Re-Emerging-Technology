package io.moonman.emergingtechnology.block.blocks;

import io.moonman.emergingtechnology.EmergingTechnology;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class AquaponicBase extends Block {

    private final String _name = "aquaponicbase";
  
    public AquaponicBase() {
      super(Material.ROCK);
      this.setHardness(2.0f);
      this.setRegistryName(EmergingTechnology.MODID, _name);
      this.setTranslationKey(EmergingTechnology.MODID + "." + _name);
      this.setCreativeTab(EmergingTechnology.TECHNOLOGYTAB);
      this.setSoundType(SoundType.STONE);
    }
  
  }