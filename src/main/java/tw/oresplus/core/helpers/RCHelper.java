package tw.oresplus.core.helpers;

import java.lang.reflect.Field;
import java.util.Random;

import tw.oresplus.OresPlus;
import tw.oresplus.recipes.RecipeType;
import mods.railcraft.api.crafting.IRockCrusherCraftingManager;
import mods.railcraft.api.crafting.IRockCrusherRecipe;
import mods.railcraft.api.crafting.RailcraftCraftingManager;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import cpw.mods.fml.common.Loader;

public class RCHelper extends OresHelper {
	private IRockCrusherCraftingManager rockCrusherManager;

	public RCHelper() {
		super("Railcraft");
	}

	@Override
	public void preInit() {
		if (!this.isLoaded()) {
			OresPlus.log.info("Railcraft not found, integration helper disabled");
			return;
		}
		
		OresPlus.log.info("RailCraft found, integration helper initialized");
	}

	@Override
	public void init() {
		if (!this.isLoaded()) 
			return;
	}

	@Override
	public void postInit() {
		if (!this.isLoaded()) 
			return;
	}
	
	@Override
	public void generate(World world, Random rand, int chunkX, int chunkZ) { }

	@Override
	public void registerRecipe(RecipeType recipeType, ItemStack input,
			NBTTagCompound metadata, ItemStack... outputs) {
		switch (recipeType) {
		case RockCrusher:
			if (this.rockCrusherManager == null) {
				this.rockCrusherManager = RailcraftCraftingManager.rockCrusher;
			}
			IRockCrusherRecipe recipe = this.rockCrusherManager.createNewRecipe(input, true, true);
			recipe.addOutput(outputs[0], 1.0F);
			break;
		default:
			break;
		}
	}

	@Override
	public void registerGasRecipe(RecipeType recipeType, Object input,
			NBTTagCompound metadata, Object output, Object secondaryOutput) { }

}
