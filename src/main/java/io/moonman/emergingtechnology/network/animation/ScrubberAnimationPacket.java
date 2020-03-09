package io.moonman.emergingtechnology.network.animation;

import io.moonman.emergingtechnology.EmergingTechnology;
import io.moonman.emergingtechnology.helpers.machines.enums.TurbineSpeedEnum;
import io.moonman.emergingtechnology.machines.scrubber.ScrubberTileEntity;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ScrubberAnimationPacket implements IMessage {
    boolean messageValid;

    private BlockPos pos;
    private int speed;

    @Override
    public void fromBytes(ByteBuf buf) {
        pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
        speed = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(pos.getX());
        buf.writeInt(pos.getY());
        buf.writeInt(pos.getZ());
        buf.writeInt(speed);
    }

    public ScrubberAnimationPacket() {
    }

    public ScrubberAnimationPacket(BlockPos pos, TurbineSpeedEnum speed) {
        this.speed = TurbineSpeedEnum.getId(speed);
        this.pos = pos;
        messageValid = true;
    }

    public static class Handler implements IMessageHandler<ScrubberAnimationPacket, IMessage> {
        @Override
        public IMessage onMessage(ScrubberAnimationPacket message, MessageContext ctx) {
            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
            return null;
        }

        private void handle(ScrubberAnimationPacket message, MessageContext ctx) {

            ScrubberTileEntity tileEntity = getTileEntity(EmergingTechnology.proxy.getWorld(ctx), message.pos);

            if (tileEntity == null)
                return;
            tileEntity.setTurbineStateClient(TurbineSpeedEnum.getById(message.speed));

        }

        private ScrubberTileEntity getTileEntity(World world, BlockPos pos) {

            if (world == null)
                return null;
            if (!world.isBlockLoaded(pos))
                return null;
            if (world.getTileEntity(pos) == null)
                return null;
            if (world.getTileEntity(pos) instanceof ScrubberTileEntity == false)
                return null;

            return (ScrubberTileEntity) world.getTileEntity(pos);
        }
    }
}