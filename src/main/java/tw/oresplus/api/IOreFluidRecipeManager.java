package tw.oresplus.api;

import java.util.HashMap;

import net.minecraftforge.fluids.FluidStack;

public interface IOreFluidRecipeManager {

	public void add(Object input, FluidStack output);
	
	public FluidStack getResult(Object input);
	
	public HashMap getRecipeList();

	public void debug();
}
