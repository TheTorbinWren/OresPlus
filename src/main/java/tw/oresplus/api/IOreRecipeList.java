package tw.oresplus.api;

import net.minecraft.item.ItemStack;

public interface IOreRecipeList {
	public void add(Object input, ItemStack output);
	
	public ItemStack getResult(Object input);
}
