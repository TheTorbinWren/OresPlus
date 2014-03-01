package tw.oresplus.fluids;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class OreFluid extends Fluid {

	public OreFluid(String fluidName) {
		super(fluidName);
		FluidRegistry.registerFluid(this);
	}

}
