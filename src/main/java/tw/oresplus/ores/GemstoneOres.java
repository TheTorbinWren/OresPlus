package tw.oresplus.ores;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import scala.reflect.internal.Trees.This;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import tw.oresplus.OresPlus;
import tw.oresplus.api.Ores;
import tw.oresplus.blocks.BlockCore;
import tw.oresplus.blocks.BlockOre;
import tw.oresplus.core.config.ConfigCore;
import tw.oresplus.core.helpers.Helpers;
import tw.oresplus.items.ItemCore;
import tw.oresplus.recipes.OreItemStack;
import tw.oresplus.recipes.RecipeManager;

public enum GemstoneOres implements IOreList {
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
	public String blockName;
	public String gemName;
	
	public OreItemStack ore;
	public OreItemStack netherOre;
	public OreItemStack block;
	public OreItemStack gem;
	
	public boolean enabled;
	
	private int _harvestLevel;
	private int _xpLow;
	private int _xpHigh;
	private OreDrops _drops;
	private OreSources _source;
	
	GemstoneOres (int harvestLevel, int xpLow, int xpHigh, OreDrops drops) {
		this.oreName = "ore" + this.toString();
		this.netherOreName = "oreNether" + this.toString();
		this.blockName = "block" + this.toString();
		this.gemName = "gem" + this.toString();
		
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
		return (this == Diamond || this == Emerald);
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
		//Register Ore
		if (!this.isVanilla()) {
			OreClass oreConfig = OresPlus.config.getOre(this.getDefaultConfig());
			if (oreConfig.enabled)
				this.ore = new OreItemStack(new BlockOre(oreConfig));
		}
		else if (this == Diamond) {
			this.ore = new OreItemStack(net.minecraft.init.Blocks.diamond_ore);
		}
		else if (this == Emerald) {
			this.ore = new OreItemStack(net.minecraft.init.Blocks.emerald_ore);
		}
		
		// Register Nether Ore
		OreClass oreConfig = OresPlus.config.getOre(this.getDefaultConfigNether());
		if (oreConfig.enabled) {
			this.netherOre = new OreItemStack(new BlockOre(oreConfig, true));
		}
		
		// Register Storage Block
		if (!this.isVanilla()) {
			this.block = new OreItemStack(new BlockCore(Material.iron, this.blockName)
				.setCreativeTab(CreativeTabs.tabBlock)
				.setHardness(5.0F)
				.setResistance(10.0F)
			.	setStepSound(Block.soundTypeMetal));
		}
		else if (this == Diamond) {
			this.block = new OreItemStack(net.minecraft.init.Blocks.diamond_block);
		}
		else if (this == Emerald) {
			this.block = new OreItemStack(net.minecraft.init.Blocks.emerald_block);
		}
	}

	@Override
	public void registerItems() {
		// Register Gem
		if (!this.isVanilla()) {
			this.gem = new OreItemStack(new ItemCore(this.gemName).setCreativeTab(CreativeTabs.tabMaterials));
		}
		else if (this == Diamond) {
			this.gem = new OreItemStack(net.minecraft.init.Items.diamond);
		}
		else if (this == Emerald) {
			this.gem = new OreItemStack(net.minecraft.init.Items.emerald);
		}
	}

	@Override
	public void registerRecipes() {
		// register ore smelt & grinds
		switch (this) {
		case Diamond:
		case Emerald:
			RecipeManager.addGrinderRecipe(this.oreName, this.gem.newStack(2));
			if (Helpers.IC2.isLoaded()) {
				Helpers.IC2.registerRecipe("Macerator", this.ore.newStack(), this.gem.newStack(2));
			}
			if (Helpers.RailCraft.isLoaded()) {
				Helpers.RailCraft.registerRecipe("rockCrusher", this.ore.newStack(), this.gem.newStack(2));
			}
			break;
		case Apatite:
			RecipeManager.addSmelting(this.ore.newStack(), this.gem.newStack(6), 0.0F);
			RecipeManager.addGrinderRecipe(this.oreName, this.gem.newStack(6));
			if (Helpers.IC2.isLoaded()) {
				Helpers.IC2.registerRecipe("Macerator", this.ore.newStack(), this.gem.newStack(6));
			}
			if (Helpers.RailCraft.isLoaded()) {
				Helpers.RailCraft.registerRecipe("rockCrucher", this.ore.newStack(), this.gem.newStack(6));
			}
			break;
		default:
			RecipeManager.addSmelting(this.ore.newStack(), this.gem.newStack(), 0.0F);
			RecipeManager.addGrinderRecipe(this.oreName, this.gem.newStack(2));
			if (Helpers.IC2.isLoaded()) {
				Helpers.IC2.registerRecipe("Macerator", this.ore.newStack(), this.gem.newStack(2));
			}
			if (Helpers.RailCraft.isLoaded()) {
				Helpers.RailCraft.registerRecipe("rockCrusher", this.ore.newStack(), this.gem.newStack(2));
			}
		}
		
		//register nether ore smelt
		RecipeManager.addSmelting(this.netherOre.newStack(), this.ore.newStack(2), 0.0F);
		
		// register gem -> oreblock
		if (!this.isVanilla()) {
			RecipeManager.addShapedRecipe(this.block.newStack(), "ggg", "ggg", "ggg", 'g', this.gemName);
		}
		// register oreblock -> gem
		if (!this.isVanilla()) {
			RecipeManager.addShapelessRecipe(this.gem.newStack(9), this.block.newStack());
		}
	}

	public void registerAspects() {
		if (!Helpers.ThaumCraft.isLoaded())
			return;
		
	    switch (this) {
	    case Apatite:
	      ThaumcraftApi.registerObjectTag(this.gemName, new AspectList().add(Aspect.CRYSTAL, 3).add(Aspect.CROP, 1));
	      ThaumcraftApi.registerObjectTag(this.oreName, new AspectList().add(Aspect.CRYSTAL, 2).add(Aspect.EARTH, 1));
	      ThaumcraftApi.registerObjectTag(this.blockName, new AspectList().add(Aspect.CRYSTAL, 6).add(Aspect.CROP, 4));
	      ThaumcraftApi.registerObjectTag(this.netherOreName, new AspectList().add(Aspect.CRYSTAL, 2).add(Aspect.ENERGY, 1).add(Aspect.EARTH, 1).add(Aspect.FIRE, 1));
	      break;
	    default:
	      if (!isVanilla()) {
	        ThaumcraftApi.registerObjectTag(this.gemName, new AspectList().add(Aspect.CRYSTAL, 4).add(Aspect.GREED, 4));
	        ThaumcraftApi.registerObjectTag(this.oreName, new AspectList().add(Aspect.EARTH, 1).add(Aspect.GREED, 3).add(Aspect.CRYSTAL, 3));
	        ThaumcraftApi.registerObjectTag(this.blockName, new AspectList().add(Aspect.CRYSTAL, 6).add(Aspect.GREED, 6));
	      }
	      ThaumcraftApi.registerObjectTag(this.netherOreName, new AspectList().add(Aspect.EARTH, 1).add(Aspect.FIRE, 1).add(Aspect.GREED, 3).add(Aspect.CRYSTAL, 3));
	    }
	}

	@Override
	public int getTradeToAmount(Random random) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTradeFromAmount(Random random) {
		// TODO Auto-generated method stub
		return 0;
	}

}
