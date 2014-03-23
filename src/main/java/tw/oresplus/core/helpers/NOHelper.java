package tw.oresplus.core.helpers;

import java.util.Random;

import tw.oresplus.OresPlus;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import cpw.mods.fml.common.Loader;

public class NOHelper extends OresHelper {
	public NOHelper() {
		super("NetherOres");
	}
	
	@Override
	public void init() {
		if (!this.isLoaded()) {
			OresPlus.log.info("NetherOres not found, helper disabled");
			return;
		}
	}

	@Override
	public void generate(World world, Random rand, int chunkX, int chunkZ) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerRecipe(String recipeType, ItemStack input,
			NBTTagCompound metadata, ItemStack... outputs) { }

}
