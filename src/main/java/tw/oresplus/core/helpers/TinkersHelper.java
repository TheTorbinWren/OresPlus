package tw.oresplus.core.helpers;

import java.util.Random;

import tw.oresplus.OresPlus;
import tw.oresplus.recipes.RecipeType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class TinkersHelper extends OresHelper {

	TinkersHelper() {
		super("TConstruct");
	}

	@Override
	public void preInit() {
		if (!this.isLoaded()) {
			OresPlus.log.info("Tinkers Construct not found, integration helper disabled");
			return;
		}
		OresPlus.log.info("Tinkers Construct found, integration helper initialized");
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postInit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerGasRecipe(RecipeType recipeType, Object input,
			NBTTagCompound metadata, Object output, Object secondaryOutput) {
		// TODO Auto-generated method stub
		
	}

}
