package tw.oresplus.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.util.EnumMap;
import java.util.LinkedList;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.FMLEmbeddedChannel;
import cpw.mods.fml.common.network.FMLIndexedMessageToMessageCodec;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;

public class NetHandler extends FMLIndexedMessageToMessageCodec<IPacket> {
	public static NetHandler instance = new NetHandler();
	
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
		channels = NetworkRegistry.INSTANCE.newChannel(CHANNEL, instance);
	}

	public void init() {
		this.registerPacket(GuiPacket.class);
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, IPacket msg,
			ByteBuf target) throws Exception {
		msg.writeBytes(target);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf source,
			IPacket msg) {
		msg.readBytes(source);
	}
}
