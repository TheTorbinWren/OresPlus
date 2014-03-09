package tw.oresplus.core.helpers;

import java.util.Random;

import tw.oresplus.OresPlus;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import cpw.mods.fml.common.Loader;

public class IC2Helper extends OresHelper {
	public IC2Helper() {
		super("IC2");
	}
	
	@Override
	public void init() {
		if (!this.isLoaded()) {
			OresPlus.log.info("IC2 not found, helper disabled");
			return;
		}
	}

	@Override
	public void generate(World world, Random rand, int chunkX, int chunkZ) {
		// TODO Auto-generated method stub
		
	}
}
