package tw.oresplus.core.helpers;

import java.util.Random;

import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.AspectList;
import tw.oresplus.OresPlus;
import tw.oresplus.blocks.Blocks;
import tw.oresplus.items.Items;
import tw.oresplus.ores.DustOres;
import tw.oresplus.ores.GemstoneOres;
import tw.oresplus.ores.GeneralOres;
import tw.oresplus.ores.MetallicOres;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import cpw.mods.fml.common.Loader;

public class TCHelper extends OresHelper {
	public TCHelper() {
		super("Thaumcraft");
	}

	@Override
	public void init() {
		if (!this.isLoaded()) {
			OresPlus.log.info("Thaumcraft not found, helper disabled");
			return;
		}
	    for (MetallicOres ore : MetallicOres.values()) {
	        ore.registerAspects();
	    }

	    for (GemstoneOres ore : GemstoneOres.values()) {
	        ore.registerAspects();
	    }

	    for (DustOres ore : DustOres.values()) {
	        ore.registerAspects();
	    }

	    for (GeneralOres ore : GeneralOres.values()) {
	        ore.registerAspects();
	    }

	    Blocks.registerAspects();
	    Items.registerAspects();

	    OresPlus.log.info("Thaumcraft found, helper Initialized");
	}

	@Override
	public void generate(World world, Random rand, int chunkX, int chunkZ) { }

	@Override
	public void registerRecipe(String recipeType, ItemStack input,
			NBTTagCompound metadata, ItemStack... outputs) { }
}
