package tw.oresplus.core.helpers;

import java.util.Random;

import tw.oresplus.OresPlus;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class MekanismHelper extends OresHelper {

	MekanismHelper() {
		super("Mekanism");
	}

	@Override
	public void init() {
		if (!this.isLoaded()) {
			OresPlus.log.info("Mekanism not found, helper disabled");
			return;
		}
		OresPlus.log.info("Mekanism found, helper initialized");
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
