package tw.oresplus.core;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import tw.oresplus.blocks.ContainerCracker;
import tw.oresplus.blocks.ContainerGrinder;
import tw.oresplus.blocks.TileEntityCracker;
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
			TileEntityCracker teCracker = (TileEntityCracker)world.getTileEntity(x, y, z);
			return new ContainerCracker(player.inventory, teCracker);
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
			TileEntityCracker teCracker = (TileEntityCracker)world.getTileEntity(x, y, z);
			return new GuiCracker(new ContainerCracker(player.inventory, teCracker));
		default:
			return null;
		}
	}
}
