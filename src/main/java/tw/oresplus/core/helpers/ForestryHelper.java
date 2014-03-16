package tw.oresplus.core.helpers;

import java.util.Random;

import tw.oresplus.OresPlus;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cpw.mods.fml.common.Loader;

public class ForestryHelper extends OresHelper {
	public ForestryHelper() {
		super("Forestry");
	}

	@Override
	public void init() {
		if (!this.isLoaded()) {
			OresPlus.log.info("Forestry not found, helper disabled");
			return;
		}
	}

	@Override
	public void generate(World world, Random rand, int chunkX, int chunkZ) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerGrind(ItemStack input, ItemStack output) {
		// TODO Auto-generated method stub
		
	}
}
