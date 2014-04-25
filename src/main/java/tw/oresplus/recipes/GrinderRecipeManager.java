package tw.oresplus.recipes;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import tw.oresplus.OresPlus;
import tw.oresplus.api.IOreRecipeManager;
import tw.oresplus.api.Ores;
import tw.oresplus.core.OreDictHelper;

public class GrinderRecipeManager implements IOreRecipeManager {
	private static final String UNKNOWN = "Unknown";
	
	private HashMap<Object, ItemStack> recipeList;
	
	public GrinderRecipeManager() {
		recipeList = new HashMap();
	}

	@Override
	public void add(Object input, ItemStack output) {
		if (input instanceof String) {
			this.add((String)input, output); 
		}
		else if (input instanceof ItemStack) {
			String dictionaryName = OreDictHelper.getName((ItemStack)input);
			if (dictionaryName.equals(UNKNOWN)) {
				this.add((ItemStack)input, output);
			}
			else {
				this.add(dictionaryName, output);
			}
		}
		else if (input instanceof Item) {
			ItemStack inputStack = new ItemStack((Item)input, 1);
			String dictionaryName = OreDictHelper.getName(inputStack);
			if (dictionaryName.equals(UNKNOWN)) {
				this.add(inputStack, output);
			}
			else {
				this.add(dictionaryName, output);
			}
		}
		else if (input instanceof Block) {
			ItemStack inputStack = new ItemStack((Block)input, 1);
			String dictionaryName = OreDictHelper.getName(inputStack);
			if (dictionaryName.equals(UNKNOWN)) {
				this.add(inputStack, output);
			}
			else {
				this.add(dictionaryName, output);
			}
		}
		else {
			OresPlus.log.info("Invalid input type for Grinder recipe");
		}
	}
	
	private void add(String input, ItemStack output) {
		if (this.getResult(input) != null) {
			OresPlus.log.info("Falied to register Grinder recipe, type OreDictionary, source: " + input + ", recipe already exists");
			return;
		}
		this.recipeList.put(input, output);
		OresPlus.log.info("Added Grinder recipe, type OreDictionary, source: " + input);
	}
	
	private void add(ItemStack input, ItemStack output) {
		if (this.getResult(input) != null) {
			OresPlus.log.info("Failed to register Grinder recipe, type ItemStack, source: " + input.toString() + ", recipe already exists");
			return;
		}
		this.recipeList.put(input, output);
		OresPlus.log.info("Added Grinder recipe, type ItemStack, source: " + input.toString());
	}

	@Override
	public ItemStack getResult(Object input) {
		if (input instanceof String) {
			return this.recipeList.get(input);
		}
		else if (input instanceof ItemStack) {
			String dictionaryName = OreDictHelper.getName((ItemStack)input);
			if (dictionaryName == UNKNOWN) {
				return this.recipeList.get(input);
			}
			else {
				return this.recipeList.get(dictionaryName);
			}
		}
		else {
			return null;
		}
	}

	@Override
	public HashMap getRecipeList() {
		return this.recipeList;
	}

	@Override
	public void debug() {
		OresPlus.log.debug("Listing grinder recipe sources");
		for (Object source : this.recipeList.keySet().toArray()) {
			if (source instanceof String) {
				OresPlus.log.debug("Found OreDictionary Grinder recipe for " + source);
			}
			else if (source instanceof ItemStack) {
				OresPlus.log.debug("Found ItemStack Grinder recipe for " + source.toString());
			}
			else {
				OresPlus.log.debug("Found unknown Grinder recipe type");
			}
		}
	}
	
}
