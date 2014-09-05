package tw.oresplus.core.helpers;

import java.util.Random;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import tw.oresplus.recipes.RecipeType;

public class MFRHelper extends OresHelper {

	MFRHelper() {
		super("MineFactoryReloaded");
	}

	@Override
	public void preInit() { }

	@Override
	public void init() { }

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
