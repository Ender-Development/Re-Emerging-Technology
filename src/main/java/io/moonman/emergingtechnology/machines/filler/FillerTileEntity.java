package io.moonman.emergingtechnology.machines.filler;

import io.moonman.emergingtechnology.config.EmergingTechnologyConfig;
import io.moonman.emergingtechnology.handlers.fluid.FluidStorageHandler;
import io.moonman.emergingtechnology.init.Reference;
import io.moonman.emergingtechnology.machines.classes.tile.MachineTileBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class FillerTileEntity extends MachineTileBase {

    public FluidTank fluidHandler = new FluidStorageHandler(Reference.FILLER_FLUID_CAPACITY) {
        @Override
        protected void onContentsChanged() {
            super.onContentsChanged();
            markDirtyClient();
        }

        @Override
        public boolean canFillFluidType(FluidStack fluidStack) {
            return false;
        }
    };

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
            return true;

        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(this.fluidHandler);
        return super.getCapability(capability, facing);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.fluidHandler.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        this.fluidHandler.writeToNBT(compound);
        return compound;
    }



    @Override
    public void cycle() {
        fillAdjacent();
        this.fluidHandler.fillInternal(new FluidStack(FluidRegistry.WATER, Reference.FILLER_FLUID_CAPACITY), true);
    }

    private void fillAdjacent() {
        for (EnumFacing facing : EnumFacing.VALUES) {
            TileEntity neighbour = this.world.getTileEntity(this.pos.offset(facing));

            // Return if no tile entity or another filler
            if (neighbour == null || neighbour instanceof FillerTileEntity) {
                continue;
            }

            IFluidHandler neighbourFluidHandler = neighbour
                    .getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing.getOpposite());

            // Return if neighbour has no fluid tank
            if (neighbourFluidHandler == null) {
                continue;
            }

            // Fill the neighbour
            neighbourFluidHandler.fill(new FluidStack(FluidRegistry.WATER,
                    EmergingTechnologyConfig.HYDROPONICS_MODULE.FILLER.fillerFluidTransferRate), true);
        }
    }
}