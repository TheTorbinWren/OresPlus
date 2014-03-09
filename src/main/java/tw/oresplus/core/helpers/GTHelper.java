package tw.oresplus.core.helpers;

import java.util.Random;

import tw.oresplus.OresPlus;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import cpw.mods.fml.common.Loader;

public class GTHelper extends OresHelper {
	public GTHelper() {
		super("gregtech_addon");
	}
	
	@Override
	public void init() {
		if (!this.isLoaded()) {
			OresPlus.log.info("gregtech_addon not found, helper disabled");
			return;
		}
	}

	@Override
	public void generate(World world, Random rand, int chunkX, int chunkZ) {
		// TODO Auto-generated method stub
		
	}

}
