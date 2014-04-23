package tw.oresplus.recipes;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import ic2.api.recipe.IRecipeInput;

public class OreItemStack implements IRecipeInput {
	public ItemStack source;
	
	public OreItemStack(ItemStack input) {
		if (input == null) {
			this.source = null;
		}
		else {
			this.source = input.copy();
			this.source.stackSize = 1;
		}
	}
	
	public OreItemStack(Item input) {
		this(new ItemStack(input, 1));
	}
	
	public OreItemStack(Item input, int damageValue) {
		this(new ItemStack(input, 1, damageValue));
	}
	
	public OreItemStack(Block input) {
		this(new ItemStack(input, 1));
	}
	
	public OreItemStack(Block input, int meta) {
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

	public ItemStack newStack() {
		return this.newStack(1);
	}
	
	public ItemStack newStack(int size) {
		if (this.source == null) {
			return null;
		}
		this.source.stackSize = size;
		return this.source.copy();
	}
	
}
