package tw.oresplus.items;

import tw.oresplus.recipes.OreItemStack;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public enum OreItems {
	crushedUranium,
	dustAluminium,
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
	
	public void registerItems() {
		item = new OreItemStack(new ItemCore(this.toString()).setCreativeTab(CreativeTabs.tabMaterials));
	}
}
