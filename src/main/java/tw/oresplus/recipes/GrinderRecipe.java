package tw.oresplus.recipes;

import java.util.ArrayList;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class GrinderRecipe {
	private ArrayList<Object> _sourceList = new ArrayList();
	private ItemStack _result;
	
	public GrinderRecipe(Object source, ItemStack result) {
		this._sourceList.add(source);
		this._result = result;
		RecipeManager.grinderRecipes.add(this);
	}

	public boolean hasSource(Object source) {
		boolean ret = false;
		
		for (Object recipeSource : this._sourceList) {
			if (recipeSource instanceof ItemStack && source instanceof ItemStack) {
				if (((ItemStack)recipeSource).isItemEqual((ItemStack)source))
					ret = true;
			}
			else if (recipeSource instanceof String && source instanceof String) {
				if (((String)recipeSource).equals((String)source))
					ret = true;
			}
		}
		
		return ret;
	}

	public ItemStack getResult() {
		return this._result;
	}
	
}
