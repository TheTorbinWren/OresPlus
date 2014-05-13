package tw.oresplus.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.util.EnumMap;
import java.util.LinkedList;

import tw.oresplus.OresPlus;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetHandler;
import net.minecraft.network.NetHandlerPlayServer;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.FMLEmbeddedChannel;
import cpw.mods.fml.common.network.FMLIndexedMessageToMessageCodec;
import cpw.mods.fml.common.network.FMLOutboundHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class NetHandler extends FMLIndexedMessageToMessageCodec<IPacket> {
	
	private static final String CHANNEL = "OresPlus";
	private int discriminators = 0;
	
	private EnumMap<Side, FMLEmbeddedChannel> channels;

	public boolean registerPacket(Class<? extends IPacket> clazz) {
		if (this.discriminators > 256) {
			return false;
		}
		this.addDiscriminator(this.discriminators++, clazz);
		return true;
	}
	
	public void preInit() {
		this.channels = NetworkRegistry.INSTANCE.newChannel(CHANNEL, OresPlus.netHandler);
	}

	public void init() {
		this.registerPacket(PacketUpdateOldCracker.class);
		this.registerPacket(PacketUpdateGrinder.class);
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, IPacket msg, ByteBuf target) throws Exception {
		msg.writeBytes(target);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf source, IPacket msg) {
		msg.readBytes(source);
		
		EntityPlayer player;
		switch (FMLCommonHandler.instance().getEffectiveSide()) {
		case CLIENT:
			player = this.getClientPlayer();
			msg.executeClient(player);
			break;
		case SERVER:
			INetHandler netHandler = ctx.channel().attr(NetworkRegistry.NET_HANDLER).get();
			player = ((NetHandlerPlayServer)netHandler).playerEntity;
			msg.executeServer(player);
			break;
		}
	}
	
	@SideOnly(Side.CLIENT)
	private EntityPlayer getClientPlayer() {
		return Minecraft.getMinecraft().thePlayer;
	}
	
	public void sendToPlayers(IPacket packet) {
		this.channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALL);
		this.channels.get(Side.SERVER).writeOutbound(packet);
	}
}
