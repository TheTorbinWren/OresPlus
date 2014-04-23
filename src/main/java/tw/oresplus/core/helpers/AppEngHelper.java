package tw.oresplus.core.helpers;

import java.util.Random;

import tw.oresplus.OresPlus;
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
	public void init() {
		if (!this.isLoaded()) {
			OresPlus.log.info("Applied Energistics 2 not found, helper disabled");
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
	    OresPlus.log.info("Applied Energistics 2 found, helper Initialized");
	}

	@Override
	public void generate(World world, Random rand, int chunkX, int chunkZ) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerRecipe(String recipeType, ItemStack input,
			NBTTagCompound metadata, ItemStack... outputs) {
		// TODO Auto-generated method stub
		
	}

}
