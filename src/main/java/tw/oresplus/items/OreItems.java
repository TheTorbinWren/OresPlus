package tw.oresplus.items;

import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.AspectList;
import tw.oresplus.core.helpers.Helpers;
import tw.oresplus.recipes.OreItemStack;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public enum OreItems {
	crushedUranium,
	dustBauxite, // TODO: Update texture
	dustCassiterite, // TODO: Update texture
	dustCharcoal, 
	dustCinnabar,
	dustCoal, 
	dustGalena, // TODO: Update texture
	dustMagnesium,
	dustPyrite,
	dustSheldonite, // TODO: Update texture
	dustSodalite,
	dustSphalerite,
	dustTetrahedrite, // TODO: Update texture
	dustTungstate, // TODO: Update texture
	dustTungsten, // TODO: Update texture
	gemIridium,
	gemOlivine,
	gemRedGarnet,
	gemUranium,
	gemYellowGarnet,
	itemBitumen;
	
	public OreItemStack item;
	private AspectList _aspects;
	
	private OreItems(AspectList aspects) {
		this._aspects = aspects;
	}
	
	private OreItems() {
		this(new AspectList());
	}
	
	public void registerItems() {
		item = new OreItemStack(new ItemCore(this.toString()).setCreativeTab(CreativeTabs.tabMaterials));
	}

	public void registerAspects() {
		if (Helpers.ThaumCraft.isLoaded() && this._aspects.size() > 0)
			ThaumcraftApi.registerObjectTag(this.item.source, this._aspects);
	}
}
