package tw.oresplus.core.helpers;

import java.util.Random;

import cpw.mods.fml.common.event.FMLInterModComms;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import tw.oresplus.OresPlus;
import tw.oresplus.ores.MineralOres;
import tw.oresplus.ores.DustyOres;
import tw.oresplus.ores.GemstoneOres;
import tw.oresplus.ores.GeneralOres;
import tw.oresplus.ores.MetallicOres;
import tw.oresplus.recipes.RecipeType;

public class MFRHelper extends OresHelper {

	MFRHelper() {
		super("MineFactoryReloaded");
	}

	@Override
	public void preInit() {		
		if (!this.isLoaded()) {
			OresPlus.log.info("MineFactoryReloaded not found, integration helper disabled");
			return;
		}
		OresPlus.log.info("MineFactoryReloaded found, integration helper initialized");
	}

	@Override
	public void init() {
		if (!this.isLoaded()) {
			return;
		}
		// register laser ores
		for (MetallicOres ore : MetallicOres.values()) {
			if (!ore.isAlloy) {
				this.registerLaserOre(ore.ore.newStack(), ore.getLaserWeight());
			}
		}
		
		for (GemstoneOres ore : GemstoneOres.values()) {
			this.registerLaserOre(ore.ore.newStack(), ore.getLaserWeight());
		}
		
		for (DustyOres ore : DustyOres.values()) {
			this.registerLaserOre(ore.ore.newStack(), ore.getLaserWeight());
		}
		
		for (MineralOres ore : MineralOres.values()) {
			this.registerLaserOre(ore.ore.newStack(), ore.getLaserWeight());
		}
		
		for (GeneralOres ore : GeneralOres.values()) {
			this.registerLaserOre(ore.ore.newStack(), ore.getLaserWeight());
		}
	}

	private void registerLaserOre(ItemStack ore, int laserWeight) {
		if (laserWeight == 0) {
			return;
		}
		NBTTagCompound msg = new NBTTagCompound();
		msg.setInteger("value", laserWeight);
		ore.writeToNBT(msg);
		FMLInterModComms.sendMessage(this._modID, "addLaserPreferredOre", msg);
	}

	@Override
	public void postInit() { }

	@Override
	public void generate(World world, Random rand, int chunkX, int chunkZ) { }

	@Override
	public void registerRecipe(RecipeType recipeType, ItemStack input, NBTTagCompound metadata, ItemStack... outputs) { }

	@Override
	public void registerGasRecipe(RecipeType recipeType, Object input,
			NBTTagCompound metadata, Object output, Object secondaryOutput) { }

}
