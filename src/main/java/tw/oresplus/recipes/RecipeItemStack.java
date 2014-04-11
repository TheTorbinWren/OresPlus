package tw.oresplus.recipes;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import ic2.api.recipe.IRecipeInput;

public class RecipeItemStack implements IRecipeInput {
	private ItemStack source;
	
	public RecipeItemStack(ItemStack input) {
		if (input == null) {
			this.source = null;
		}
		else {
			this.source = input.copy();
			this.source.stackSize = 1;
		}
	}
	
	public RecipeItemStack(Item input) {
		this(new ItemStack(input, 1));
	}
	
	public RecipeItemStack(Item input, int damageValue) {
		this(new ItemStack(input, 1, damageValue));
	}
	
	public RecipeItemStack(Block input) {
		this(new ItemStack(input, 1));
	}
	
	public RecipeItemStack(Block input, int meta) {
		this(new ItemStack(input, 1, meta));
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
		return this.getSource(1);
	}
	
	public ItemStack getSource(int size) {
		if (this.source == null) {
			return null;
		}
		this.source.stackSize = size;
		return this.source.copy();
	}
	
}
