package tw.oresplus.core;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import tw.oresplus.blocks.ContainerGrinder;
import tw.oresplus.blocks.OldContainerCracker;
import tw.oresplus.blocks.OldContainerGrinder;
import tw.oresplus.blocks.OldTileEntityCracker;
import tw.oresplus.blocks.OldTileEntityGrinder;
import tw.oresplus.blocks.TileEntityGrinder;
import tw.oresplus.client.GuiCracker;
import tw.oresplus.client.GuiGrinder;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		switch (ID) {
		case 0:
			TileEntityGrinder teGrinder = (TileEntityGrinder) world.getTileEntity(x, y, z);
			return new ContainerGrinder(player.inventory, teGrinder);
		case 1:
			OldTileEntityCracker teCracker = (OldTileEntityCracker)world.getTileEntity(x, y, z);
			return new OldContainerCracker(player.inventory, teCracker);
		default:
			return null;
		}
	}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		switch (ID) {
		case 0:
			TileEntityGrinder teGrinder = (TileEntityGrinder) world.getTileEntity(x, y, z);
			return new GuiGrinder(player.inventory, teGrinder);
		case 1:
			OldTileEntityCracker teCracker = (OldTileEntityCracker)world.getTileEntity(x, y, z);
			return new GuiCracker(new OldContainerCracker(player.inventory, teCracker));
		default:
			return null;
		}
	}
}
