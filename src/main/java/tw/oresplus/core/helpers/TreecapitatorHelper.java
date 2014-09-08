package tw.oresplus.core.helpers;

import java.util.ArrayList;
import java.util.Random;

import cpw.mods.fml.common.event.FMLInterModComms;
import tw.oresplus.OresPlus;
import tw.oresplus.core.References;
import tw.oresplus.items.OreAxe;
import tw.oresplus.recipes.RecipeType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class TreecapitatorHelper extends OresHelper {

	TreecapitatorHelper() {
		super("Treecapitator");
	}

	@Override
	public void preInit() {
		if (!this.isLoaded()) {
			OresPlus.log.info("Treecapitator not found, integration helper disabled");
			return;
		}
		OresPlus.log.info("Treecapitator found, integration helper initialized");
	}

	@Override
	public void init() {
		if (!this.isLoaded())
			return;
		
		ArrayList<String> axeList = OreAxe.getAxeList();
		if (!axeList.isEmpty())
		{
			String axes = "";
			for (String axeName : axeList) {
				axes += ";" + References.MOD_ID + ":" + axeName;
			}
			NBTTagCompound msg = new NBTTagCompound();
			msg.setString("modID", References.MOD_ID);
			msg.setString("axeIDList", axes);
			FMLInterModComms.sendMessage(this._modID, "ThirdPartyModConfig", msg);
			OresPlus.log.debug("Sent axe registration for " + axes);
		}
	}

	@Override
	public void postInit() {
		if (!this.isLoaded())
			return;
	}

	@Override
	public void generate(World world, Random rand, int chunkX, int chunkZ) {}

	@Override
	public void registerRecipe(RecipeType recipeType, ItemStack input,
			NBTTagCompound metadata, ItemStack... outputs) {}

	@Override
	public void registerGasRecipe(RecipeType recipeType, Object input,
			NBTTagCompound metadata, Object output, Object secondaryOutput) { }

}
