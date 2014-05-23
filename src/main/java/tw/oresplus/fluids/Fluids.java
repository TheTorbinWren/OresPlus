package tw.oresplus.fluids;

import tw.oresplus.core.helpers.BCHelper;
import tw.oresplus.core.helpers.Helpers;
import net.minecraftforge.fluids.FluidRegistry;

public class Fluids {
	public static void init() {
		if (!Helpers.BuildCraft.isLoaded())
			new OreFluid("Oil");
	}
}
