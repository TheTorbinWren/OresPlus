package tw.oresplus.recipes;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.item.ItemStack;
import tw.oresplus.api.IOreRecipeList;

public class OreRecipeManager implements IOreRecipeList {
	private HashMap<Object, ItemStack> recipeList;
	
	public OreRecipeManager() {
		recipeList = new HashMap();
	}

	@Override
	public void add(Object input, ItemStack output) {
		this.recipeList.put(input, output);
	}

	@Override
	public ItemStack getResult(Object input) {
		return this.recipeList.get(input);
	}
	
}
