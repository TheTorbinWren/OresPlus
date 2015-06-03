package tw.oresplus.fluids;

import tw.oresplus.core.helpers.Helpers;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public enum Fluids {
	CopperSulfide,
	Mercury,
	Oil;
	
	public Fluid fluid;
	
	public void registerFluid() {
		switch (this) {
		case Oil:
			if (Helpers.BuildCraft.isLoaded()) {
				this.fluid = FluidRegistry.getFluid(this.toString());
			}
			else {
				this.fluid = new OreFluid(this.toString());
			}
			break;
		default:
			this.fluid = new OreFluid(this.toString());
			break;
		}
	}
}
