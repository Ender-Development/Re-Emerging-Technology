package io.enderdev.emergingtechnology.config.hydroponics.interfaces;

import io.enderdev.emergingtechnology.config.hydroponics.enums.CropTypeEnum;

public interface IIdealBoostsConfiguration {
    public int getBoost(CropTypeEnum cropType);
}
