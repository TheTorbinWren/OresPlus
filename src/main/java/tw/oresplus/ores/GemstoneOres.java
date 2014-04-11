package tw.oresplus.ores;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import scala.reflect.internal.Trees.This;
import tw.oresplus.api.Ores;
import tw.oresplus.blocks.BlockCore;
import tw.oresplus.blocks.BlockOre;
import tw.oresplus.core.Config;
import tw.oresplus.core.OreClass;
import tw.oresplus.core.helpers.Helpers;
import tw.oresplus.items.ItemCore;
import tw.oresplus.recipes.RecipeItemStack;
import tw.oresplus.recipes.RecipeManager;

public enum GemstoneOres implements IOres {
		Amethyst (2, OreDrops.AMETHYST),
		Apatite (1, OreDrops.APATITE),
		Diamond (2, 3, 7, OreDrops.DIAMOND),
		Emerald (2, 3, 7, OreDrops.EMERALD),
		GreenSapphire (2, 3, 7, OreDrops.GREENSAPPHIRE),
		Ruby (2, 3, 7, OreDrops.RUBY),
		Sapphire (2, 3, 7, OreDrops.SAPPHIRE),
		Topaz (2, 3, 7, OreDrops.TOPAZ)
	;
		
	public String oreName;
	public String netherOreName;
	public String gemName;
	public String oreBlockName;
	
	public ItemStack ore;
	public ItemStack netherOre;
	public ItemStack gem;
	public ItemStack oreBlock;
	
	public boolean enabled;
	
	private int _harvestLevel;
	private int _xpLow;
	private int _xpHigh;
	private OreDrops _drops;
	private OreSources _source;
	
	GemstoneOres (int harvestLevel, int xpLow, int xpHigh, OreDrops drops) {
		this.oreName = "ore" + this.toString();
		this.netherOreName = "oreNether" + this.toString();
		this.gemName = "gem" + this.toString();
		this.oreBlockName = "oreBlock" + this.toString();
		
		this.enabled = true;
		
		this._harvestLevel = harvestLevel;
		this._xpLow = xpLow;
		this._xpHigh = xpHigh;
		this._drops = drops;
		this._source = OreSources.DEFAULT;
	}
	
	GemstoneOres (int harvestLevel, OreDrops drops) {
		this(harvestLevel, 0, 0, drops);
	}
	
	private boolean isVanilla() {
		return (this.toString().equals("Diamond") || this.toString().equals("Emerald"));
	}

	@Override
	public OreClass getDefaultConfig() {
		return new OreClass(this.oreName, this.enabled, this._harvestLevel, this._xpLow, 
				this._xpHigh, this._drops, this._source);
	}

	@Override
	public OreClass getDefaultConfigNether() {
		return new OreClass(this.netherOreName, this.enabled, this._harvestLevel, this._xpLow,
				this._xpHigh, OreDrops.ORE, this._source);
	}

	@Override
	public void registerBlocks() {
		if (!this.isVanilla()) {
			OreClass oreConfig = Config.getOre(this.getDefaultConfig());
			if (oreConfig.enabled)
				this.ore = new ItemStack(new BlockOre(oreConfig), 1);
		}
		else if (this.toString().equals("Diamond"))
			this.ore = new ItemStack(net.minecraft.init.Blocks.diamond_ore);
		else if (this.toString().equals("Emerald"))
			this.ore = new ItemStack(net.minecraft.init.Blocks.emerald_ore);
		
		OreClass oreConfig = Config.getOre(this.getDefaultConfigNether());
		if (oreConfig.enabled)
			this.netherOre = new ItemStack(new BlockOre(oreConfig), 1);
		
		if (!this.isVanilla()) {
			this.oreBlock = new ItemStack(new BlockCore(Material.iron, this.oreBlockName)
				.setCreativeTab(CreativeTabs.tabBlock)
				.setHardness(5.0F)
				.setResistance(10.0F)
			.	setStepSound(Block.soundTypeMetal), 1);
		}
		else if (this.toString().equals("Diamond"))
			this.oreBlock = new ItemStack(net.minecraft.init.Blocks.diamond_block);
		else if (this.toString().equals("Emerald"))
			this.oreBlock = new ItemStack(net.minecraft.init.Blocks.emerald_block);
	}

	@Override
	public void registerItems() {
		if (!this.isVanilla()) {
			this.gem = new ItemStack(new ItemCore(this.gemName).setCreativeTab(CreativeTabs.tabMaterials), 1);
		}
		else if (this.toString().equals("Diamond"))
			this.gem = new ItemStack(net.minecraft.init.Items.diamond, 1);
		else if (this.toString().equals("Emerald"))
			this.gem = new ItemStack(net.minecraft.init.Items.emerald, 1);
	}

	@Override
	public void registerRecipes() {
		RecipeItemStack rOre = new RecipeItemStack(this.ore);
		RecipeItemStack rGem = new RecipeItemStack(this.gem);
		RecipeItemStack rNetherOre = new RecipeItemStack(this.netherOre);
		RecipeItemStack rOreBlock = new RecipeItemStack(this.oreBlock);
		
		// register ore smelt & grinds
		switch (this) {
		case Diamond:
		case Emerald:
			RecipeManager.addGrinderRecipe(this.oreName, rGem.getSource(2));
			if (Helpers.IC2.isLoaded()) {
				Helpers.IC2.registerRecipe("Macerator", rOre.getSource(), rGem.getSource(2));
			}
			break;
		case Apatite:
			RecipeManager.addSmelting(rOre.getSource(6), rGem.getSource(), 0.0F);
			RecipeManager.addGrinderRecipe(this.oreName, rGem.getSource(6));
			if (Helpers.IC2.isLoaded()) {
				Helpers.IC2.registerRecipe("Macerator", rOre.getSource(), rGem.getSource(6));
			}
			break;
		default:
			RecipeManager.addSmelting(rOre.getSource(), rGem.getSource(), 0.0F);
			RecipeManager.addGrinderRecipe(this.oreName, rGem.getSource(2));
			if (Helpers.IC2.isLoaded()) {
				Helpers.IC2.registerRecipe("Macerator", rOre.getSource(), rGem.getSource(2));
			}
		}
		
		
		//register nether ore smelt
		RecipeManager.addSmelting(rNetherOre.getSource(), rOre.getSource(2), 0.0F);
		
		// register gem -> oreblock
		if (!this.isVanilla()) {
			RecipeManager.addShapedRecipe(rOreBlock.getSource(), "ggg", "ggg", "ggg", 'g', this.gemName);
		}
		// register oreblock -> gem
		if (!this.isVanilla()) {
			RecipeManager.addShapelessRecipe(rGem.getSource(9), rOreBlock.getSource());
		}
	}

}
