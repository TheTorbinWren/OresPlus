package tw.oresplus.api;

import java.util.HashMap;

import net.minecraft.item.ItemStack;

public interface IOreRecipeManager {
	public void add(Object input, ItemStack output);
	
	public ItemStack getResult(Object input);

	public HashMap getRecipeList();

	public void debug();
}
