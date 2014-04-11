package tw.oresplus.recipes;

import net.minecraft.item.ItemStack;

public interface ICraftingHandler {
	public boolean isLoaded();
	
	public boolean hideItem(ItemStack item);
}
