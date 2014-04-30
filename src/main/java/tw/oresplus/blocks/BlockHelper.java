package tw.oresplus.blocks;

import tw.oresplus.recipes.OreItemStack;
import net.minecraft.block.Block;

public class BlockHelper {
	public static Block getBlock(OreItemStack itemStack) {
		return Block.getBlockFromItem(itemStack.source.getItem());
	}
}
