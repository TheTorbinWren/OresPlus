package tw.oresplus.items;

import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.AspectList;
import tw.oresplus.core.helpers.Helpers;
import tw.oresplus.recipes.OreItemStack;
import tw.oresplus.recipes.RecipeManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public enum Items {
	crushedUranium,
	dustAntimony,
	dustCharcoal (108.6D), 
	dustCinnabar,
	dustCoal (108.6D), 
	dustMagnesium,
	dustPyrite,
	dustSheldonite, // TODO: Update texture
	dustSodalite,
	dustSphalerite,
	dustTinyTungsten,
	dustTungstate, // TODO: Update texture
	dustTungsten, // TODO: Update texture
	gemIridium (12000.0),
	gemOlivine,
	gemRedGarnet,
	gemUranium,
	gemYellowGarnet,
	machineCasing,
	itemBitumen,
	itemMercury;
	
	public OreItemStack item;
	private AspectList _aspects;
	private double _uuCost;
	
	private Items(AspectList aspects, double uuCost) {
		this._aspects = aspects;
		this._uuCost = uuCost;
	}
	
	private Items() {
		this(new AspectList(), 0.0D);
	}
	
	private Items(double uuCost) {
		this(new AspectList(), uuCost);
	}
	
	public void registerItems() {
		item = new OreItemStack(new ItemCore(this.toString()).setCreativeTab(CreativeTabs.tabMaterials));
	}

	public void registerAspects() {
		if (Helpers.ThaumCraft.isLoaded() && this._aspects.size() > 0)
			ThaumcraftApi.registerObjectTag(this.item.source, this._aspects);
	}
	
	public void registerRecipes() {
		if (this._uuCost != 0.0D) {
			RecipeManager.addUUMatterRecipe(this.item.source, this._uuCost);
		}
	}
}
