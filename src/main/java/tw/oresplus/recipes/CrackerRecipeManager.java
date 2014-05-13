package tw.oresplus.recipes;

import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import tw.oresplus.OresPlus;
import tw.oresplus.api.IOreFluidRecipeManager;
import tw.oresplus.core.OreDictHelper;

public class CrackerRecipeManager implements IOreFluidRecipeManager {
	private static final String UNKNOWN = "Unknown";
	
	private HashMap<Object, FluidStack> recipeList = new HashMap();

	@Override
	public void add(Object input, FluidStack output) {
		if (input instanceof String) {
			this.recipeList.put(input, output);
		}
		else {
			ItemStack inputStack = null;
			if (input instanceof ItemStack) {
				inputStack = (ItemStack)input;
			}
			else if (input instanceof Block) {
				inputStack = new ItemStack((Block)input, 1);
			}
			else if (input instanceof Item) {
				inputStack = new ItemStack((Item)input, 1);
			}
			if (inputStack != null) {
				String dictName = OreDictHelper.getName(inputStack);
				if (dictName == UNKNOWN) {
					this.recipeList.put(inputStack, output);
				}
				else {
					this.recipeList.put(dictName, output);
				}
			}
			else {
				OresPlus.log.info("Invalid input type for Cracker recipe");
			}
		}
	}

	@Override
	public FluidStack getResult(Object input) {
		if (input instanceof String) {
			return this.recipeList.get(input);
		}
		else if (input instanceof ItemStack) {
			String dictName = OreDictHelper.getName((ItemStack)input);
			if (dictName == UNKNOWN) {
				return this.recipeList.get(input);
			}
			else {
				return this.recipeList.get(dictName);
			}
		}
		return null;
	}

	@Override
	public HashMap getRecipeList() {
		return this.recipeList;
	}

	@Override
	public void debug() {}

}
