package tw.oresplus.core.helpers;

import java.util.Random;

import tw.oresplus.OresPlus;
import tw.oresplus.recipes.RecipeType;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import cpw.mods.fml.common.Loader;

public class TEHelper extends OresHelper {
	public TEHelper() {
		super("ThermalExpansion");
	}

	@Override
	public void preInit() {
		if (!this.isLoaded()) {
			OresPlus.log.info("ThermalExpansion not found, integration helper disabled");
			return;
		}
		OresPlus.log.info("ThermalExpeansion found, integration helper initialized");
	}

	@Override
	public void generate(World world, Random rand, int chunkX, int chunkZ) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerRecipe(RecipeType recipeType, ItemStack input,
			NBTTagCompound metadata, ItemStack... outputs) {
		// TODO Auto-generated method stub
		
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

}
