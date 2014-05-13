package tw.oresplus.client;

import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import tw.oresplus.blocks.OldTileEntityGrinder;
import tw.oresplus.core.IProxy;

public class ClientProxy implements IProxy {

	@Override
	public void registerArmorRenderer(String armorName) {
		RenderingRegistry.addNewArmourRendererPrefix(armorName);
	}


}
