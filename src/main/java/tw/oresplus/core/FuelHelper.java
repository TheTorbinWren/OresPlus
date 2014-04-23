package tw.oresplus.core;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;

public class FuelHelper {
    public static int getItemBurnTime(ItemStack itemStack) {
    	if (itemStack == null)
    		return 0;	
    	
    	Item item = itemStack.getItem();
    	if (item instanceof ItemBlock && Block.getBlockFromItem(item) != Blocks.air) {
    		Block block = Block.getBlockFromItem(item);
            if (block == Blocks.wooden_slab)
                return 150;
            if (block.getMaterial() == Material.wood)
                return 300;
            if (block == Blocks.coal_block)
                return 16000;
    	}
        if (item instanceof ItemTool && ((ItemTool)item).getToolMaterialName().equals("WOOD")) 
        	return 200;
        if (item instanceof ItemSword && ((ItemSword)item).getToolMaterialName().equals("WOOD")) 
        	return 200;
        if (item instanceof ItemHoe && ((ItemHoe)item).getToolMaterialName().equals("WOOD")) 
        	return 200;
        if (item == Items.stick) 
        	return 100;
        if (item == Items.coal) 
        	return 1600;
        if (item == Items.lava_bucket) 
        	return 20000;
        if (item == Item.getItemFromBlock(Blocks.sapling)) 
        	return 100;
        if (item == Items.blaze_rod) 
        	return 2400;
    	return GameRegistry.getFuelValue(itemStack);
    }

    public static boolean isItemFuel(ItemStack item) {
    	return getItemBurnTime(item) > 0;
    }

}
