package tw.oresplus.api;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class Ores {
	private static Ores instance = new Ores();
	
	public static IOreManager manager;
	
	public static IOreFluidManager fluidManager;
	
	public static IOreRecipeManager grinderRecipes;
	public static IOreFluidRecipeManager crackerRecipes;

	private Ores () {}
	
	private class BlockInfo {
		public String ownerMod;
		public Block block;
		
		public BlockInfo(String owner, Block newBlock) {
			this.ownerMod = owner;
			this.block = newBlock;
		}

	}
	
	
}
