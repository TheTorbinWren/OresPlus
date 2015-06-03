package tw.oresplus.network;

import tw.oresplus.core.References;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class NetHandler {
	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(References.MOD_ID.toLowerCase());
	
	public static void init() {
		INSTANCE.registerMessage(PacketGrinder.class, PacketGrinder.class, 0, Side.CLIENT);
		INSTANCE.registerMessage(PacketCracker.class, PacketCracker.class, 1, Side.CLIENT);
	}
}
