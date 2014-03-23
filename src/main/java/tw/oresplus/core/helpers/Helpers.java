package tw.oresplus.core.helpers;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public enum Helpers {
	AppliedEnergistics(new AppEngHelper()),
	BuildCraft(new BCHelper()),
	Forestry(new ForestryHelper()),
	GregTech(new GTHelper()),
	IC2(new IC2Helper()),
	NetherOres(new NOHelper()),
	RailCraft(new RCHelper()),
	ThaumCraft(new TCHelper()),
	ThermalExpansion(new TEHelper());
	
	private OresHelper _oresHelper;
	
	private Helpers(OresHelper oresHelper) {
		_oresHelper = oresHelper;
	}
	
	public void init() {
		this._oresHelper.init();
	}
	
	public boolean isLoaded() {
		return this._oresHelper.isLoaded();
	}
	
	public void generate(World world, Random rand, int chunkX, int chunkZ) {
		this._oresHelper.generate(world, rand, chunkX, chunkZ);
	}
	
	public Block getBlock(String blockName) {
		return this._oresHelper.getBlock(blockName);
	}
	
	public Item getItem(String itemName) {
		return this._oresHelper.getItem(itemName);
	}
	
	public void registerRecipe(String recipeType, ItemStack input, ItemStack... outputs) {
		this.registerRecipe(recipeType, input, null, outputs);
	}
	
	public void registerRecipe(String recipeType, ItemStack input, NBTTagCompound metadata, ItemStack... outputs) {
		this._oresHelper.registerRecipe(recipeType, input, metadata, outputs);
	}
	
}

