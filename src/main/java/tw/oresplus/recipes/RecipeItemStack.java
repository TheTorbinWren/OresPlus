package tw.oresplus.recipes;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import ic2.api.recipe.IRecipeInput;

public class RecipeItemStack implements IRecipeInput {
	private ItemStack source;
	
	public RecipeItemStack(ItemStack input) {
		this.source = input.copy();
		this.source.stackSize = input.stackSize;
	}
	
	public RecipeItemStack(Item input, int amount) {
		this.source = new ItemStack(input, amount);
	}
	
	public RecipeItemStack(Block input, int amount) {
		this.source = new ItemStack(input, amount);
	}
	
	public RecipeItemStack() {
		this.source = null;
	}

	@Override
	public boolean matches(ItemStack subject) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getAmount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<ItemStack> getInputs() {
		// TODO Auto-generated method stub
		return null;
	}

	public ItemStack getSource() {
		return this.source.copy();
	}
	
	public void setStackSize(int size) {
		this.source.stackSize = size;
	}
	
	public void setSource(ItemStack stack) {
		this.source = stack;
		this.source.stackSize = stack.stackSize;
	}
}
