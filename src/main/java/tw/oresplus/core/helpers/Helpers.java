package tw.oresplus.core.helpers;

import java.util.Random;

import tw.oresplus.recipes.RecipeType;
import mekanism.api.gas.OreGas;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public enum Helpers {
	AppliedEnergistics(new AppEngHelper()),
	BuildCraft(new BCHelper()),
	Forestry(new ForestryHelper()),
	IC2(new IC2Helper()),
	Mekanism(new MekanismHelper()),
	RailCraft(new RCHelper()),
	ThaumCraft(new TCHelper()),
	ThermalExpansion(new TEHelper()),
	Tinkers(new TinkersHelper()),
	Treecapitator(new TreecapitatorHelper()), 
	BigReactors(new BigReactorsHelper()), 
	MineFactoryReloaded(new MFRHelper());
	
	private OresHelper _oresHelper;
	
	private Helpers(OresHelper oresHelper) {
		_oresHelper = oresHelper;
	}
	
	public void preInit() {
		this._oresHelper.preInit();
	}
	
	public void init() {
		this._oresHelper.init();
	}
	
	public void postInit() {
		this._oresHelper.postInit();
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
	
	public void registerRecipe(RecipeType recipeType, ItemStack input, ItemStack... outputs) {
		this.registerRecipe(recipeType, input, null, outputs);
	}
	
	public void registerRecipe(RecipeType recipeType, ItemStack input, NBTTagCompound metadata, ItemStack... outputs) {
		this._oresHelper.registerRecipe(recipeType, input, metadata, outputs);
	}

	public void registerGasRecipe(RecipeType recipeType,
			Object input, NBTTagCompound metadata, Object output) {
		this.registerGasRecipe(recipeType, input, metadata, output, null);
	}
	
	public void registerGasRecipe(RecipeType recipeType,
			Object input, NBTTagCompound metadata, Object output, Object secondaryOutput) {
		// TODO Auto-generated method stub
		this._oresHelper.registerGasRecipe(recipeType, input, metadata, output, secondaryOutput);
	}
	
}

