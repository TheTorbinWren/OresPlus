package tw.oresplus.gases;

import net.minecraftforge.fluids.Fluid;
import tw.oresplus.fluids.Fluids;
import mekanism.api.gas.Gas;
import mekanism.api.gas.GasRegistry;

public enum Gases {
	CopperSulfide(Fluids.CopperSulfide.fluid),
	Mercury(Fluids.Mercury.fluid),
	oxygen(null),
	slurryCleanAntimony(null),
	slurryCleanTitanium(null),
	sulfurDioxideGas(null);
	
	public Gas gas;
	
	private Fluid fluid;
	
	private Gases(Fluid fluid) {
		this.fluid = fluid;
	}
	
	public void registerGas() {
		if (!GasRegistry.containsGas(this.toString())) {
			if (this.fluid != null) {
				this.gas = GasRegistry.register(new Gas(this.fluid));
			}
			else {
				this.gas = GasRegistry.register(new Gas(this.toString()));
			}
		}
		else {
			this.gas = GasRegistry.getGas(this.toString());
		}
	}
}
