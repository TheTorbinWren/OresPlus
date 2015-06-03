package tw.oresplus.network;

import tw.oresplus.blocks.TileEntityGrinder;
import tw.oresplus.blocks.TileEntityMachine;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketGrinder extends PacketMachine implements IMessageHandler<PacketGrinder, IMessage> {

	public PacketGrinder() { }
	
	public PacketGrinder(TileEntityGrinder te) {
		super(te);
	}
	
	@Override
	public void fromBytes(ByteBuf bytes) {
		super.fromBytes(bytes);
		
	}

	@Override
	public void toBytes(ByteBuf bytes) {
		super.toBytes(bytes);
	}

	@Override
	public IMessage onMessage(PacketGrinder msg, MessageContext ctx) {
		super.onMessage(msg, ctx);
		TileEntity te = FMLClientHandler.instance().getClient().theWorld.getTileEntity(msg.x, msg.y, msg.z);
		
		if (te instanceof TileEntityGrinder) {
		}
		
		return null;
	}

}
