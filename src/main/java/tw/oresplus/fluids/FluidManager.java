package tw.oresplus.fluids;

import tw.oresplus.api.Ores;
import tw.oresplus.core.helpers.BCHelper;
import tw.oresplus.core.helpers.Helpers;
import tw.oresplus.ores.MetallicOres;
import net.minecraftforge.fluids.FluidRegistry;

public class FluidManager {
	public static void init() {
		Ores.fluidManager = new OreFluidManager();
		
		for (Fluids fluid : Fluids.values()) {
			fluid.registerFluid();
		}
		
		for (MetallicOres ore : MetallicOres.values()) {
			ore.registerFluids();
		}
	}
}
