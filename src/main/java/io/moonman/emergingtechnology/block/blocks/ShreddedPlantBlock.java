package io.moonman.emergingtechnology.block.blocks;

import io.moonman.emergingtechnology.EmergingTechnology;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class ShreddedPlantBlock extends Block {

    private final String _name = "shreddedplantblock";
  
    public ShreddedPlantBlock() {
      super(Material.PLANTS);
      this.setHardness(1.0f);
      this.setRegistryName(EmergingTechnology.MODID, _name);
      this.setTranslationKey(EmergingTechnology.MODID + "." + _name);
      this.setCreativeTab(EmergingTechnology.TECHNOLOGYTAB);
      this.setSoundType(SoundType.PLANT);
    }
  
  }