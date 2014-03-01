package tw.oresplus.fluids;

import tw.oresplus.core.helpers.BCHelper;
import net.minecraftforge.fluids.FluidRegistry;

public class Fluids {
	public static void init() {
		if (!BCHelper.isLoaded())
			new OreFluid("oil");
	}
}
