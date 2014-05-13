package tw.oresplus.core.helpers;

import java.util.Random;

import appeng.api.AEApi;
import appeng.api.IAppEngApi;
import tw.oresplus.OresPlus;
import tw.oresplus.recipes.RecipeType;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import cpw.mods.fml.common.Loader;

public class AppEngHelper extends OresHelper {
	private static IAppEngApi api = null;
	
	public AppEngHelper() {
		super("appliedenergistics2");
	}
	
	@Override
	public void preInit() {
		if (!this.isLoaded()) {
			OresPlus.log.info("Applied Energistics 2 not found, integration helper disabled");
			return;
		}
	    try 
	    {
	        api = AEApi.instance();
	    }
	    catch (Exception e) {
	        OresPlus.log.info("Error initializing Applied Energistics 2");
	        return;
	    }
	    OresPlus.log.info("Applied Energistics 2 found, integration helper Initialized");
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
		if (!this.isLoaded())
			return;
		
		switch (recipeType) {
		case Grinder:
	        try {
	        	api.registries().grinder().addRecipe(input, outputs[0], metadata.getInteger("cranks"));
	        }
	        catch (Exception e) {
	        	OresPlus.log.info("Error registering AppEng2 grinder recipe");
	        }
	        break;
		default:
			break;
		}
	}

}
