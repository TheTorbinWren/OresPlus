package tw.oresplus.network;

import net.minecraft.tileentity.TileEntity;
import tw.oresplus.blocks.TileEntityCracker;
import tw.oresplus.blocks.TileEntityMachine;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketCracker extends PacketMachine implements IMessageHandler<PacketCracker, IMessage> {

	public PacketCracker(TileEntityCracker te) {
		super(te);
	}
	
	public PacketCracker() { }
	
	@Override
	public void fromBytes(ByteBuf bytes) {
		super.fromBytes(bytes);
	}

	@Override
	public void toBytes(ByteBuf bytes) {
		super.toBytes(bytes);
	}

	@Override
	public IMessage onMessage(PacketCracker msg, MessageContext ctx) {
		super.onMessage(msg, ctx);
		
		TileEntity te = FMLClientHandler.instance().getClient().theWorld.getTileEntity(msg.x, msg.y, msg.z);
		if (te instanceof TileEntityCracker) {
		}
		
		return null;
	}

}
