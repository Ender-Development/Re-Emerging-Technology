package io.moonman.emergingtechnology.helpers.machines.classes;

/**
 * A packet containing performance boosts for machines. Generated by nearby Optimisers
 */
public class OptimiserPacket {

    private final int DEFAULT_VALUE = 1;

    public int energyModifier;
    public int fluidModifier;
    public int gasModifier;
    public int progressModifier;

    public OptimiserPacket() {
        reset();
    }

    public OptimiserPacket(int energy, int fluid, int gas, int progress) {
        energyModifier = energy;
        fluidModifier = fluid;
        progressModifier = progress;
        gasModifier = gas;
    }

    public int calculateEnergyUse(int value) {
        return value / energyModifier;
    }

    public int calculateFluidUse(int value) {
        return value / fluidModifier;
    }

    public int calculateProgress(int value) {
        return value / progressModifier;
    }

    public int calculateGasUse(int value) {
        return value / gasModifier;
    }

    public void reset() {
        energyModifier = DEFAULT_VALUE;
        fluidModifier = DEFAULT_VALUE;
        progressModifier = DEFAULT_VALUE;
        gasModifier = DEFAULT_VALUE;
    }

    public OptimiserPacket merge(OptimiserPacket packetToMerge) {
        this.energyModifier = packetToMerge.energyModifier > this.energyModifier ? packetToMerge.energyModifier : this.energyModifier;
        this.fluidModifier = packetToMerge.fluidModifier > this.fluidModifier ? packetToMerge.energyModifier : this.fluidModifier;
        this.progressModifier = packetToMerge.progressModifier > this.progressModifier ? packetToMerge.progressModifier : this.progressModifier;
        this.gasModifier = packetToMerge.gasModifier > this.gasModifier ? packetToMerge.gasModifier : this.gasModifier;

        return this;
    }
}