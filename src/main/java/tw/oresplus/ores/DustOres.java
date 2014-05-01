package tw.oresplus.ores;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
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

public enum DustOres implements IOreList {
		Nikolite(2, Aspect.ENERGY, Aspect.MECHANISM, OreDrops.NIKOLITE),
		Phosphorite(1, Aspect.ENERGY, Aspect.HARVEST, OreDrops.PHOSPHORITE),
		Potash(1, Aspect.ENERGY, Aspect.HARVEST, OreDrops.POTASH),
		Redstone(2, Aspect.ENERGY, Aspect.MECHANISM),
		Saltpeter(1, Aspect.FIRE, Aspect.HARVEST, OreDrops.SALTPETER),
		Sulfur(1, Aspect.FIRE, Aspect.ENTROPY, OreDrops.SULFUR)
	;
		
	public String oreName;
	public String netherOreName;
	public String blockName;
	public String dustName;
	public String tinyDustName;
	
	public OreItemStack ore;
	public OreItemStack netherOre;
	public OreItemStack block;
	public OreItemStack dust;
	public OreItemStack tinyDust;
	
	private int _harvestLevel;
	private OreDrops _drops;
	private Aspect _aspect;
	private Aspect _secondaryAspect;
		
	private DustOres (int harvestLevel, Aspect aspect, Aspect secondaryAspect) {
		this(harvestLevel, aspect, secondaryAspect, OreDrops.ORE);
	}
	
	private DustOres (int harvestLevel, Aspect aspect, Aspect secondaryAspect, OreDrops drops) {
		this.oreName = "ore" + this.toString();
		this.netherOreName = "oreNether" + this.toString();
		this.blockName = "block" + this.toString();
		this.dustName = "dust" + this.toString();
		this.tinyDustName = "dustTiny" + this.toString();
		this._harvestLevel = harvestLevel;
		this._drops = drops;
		this._aspect = aspect;
		this._secondaryAspect = secondaryAspect;
	}

	@Override
	public OreClass getDefaultConfig() {
		return new OreClass(this.oreName, true, this._harvestLevel, 0, 0,
				this._drops, OreSources.DEFAULT);
	}

	@Override
	public OreClass getDefaultConfigNether() {
		return new OreClass(this.netherOreName, true, this._harvestLevel, 0, 0,
				OreDrops.ORE, OreSources.DEFAULT);
	}

	@Override
	public void registerBlocks() {
		// Register ore & storage block
		OreClass oreClass;
		switch (this) {
		case Redstone:
			this.ore = new OreItemStack(net.minecraft.init.Blocks.redstone_ore);
			Ores.manager.registerOre(this.oreName, net.minecraft.init.Blocks.redstone_ore);
			this.block = new OreItemStack(net.minecraft.init.Blocks.redstone_block);
			Ores.manager.registerOre(this.blockName, net.minecraft.init.Blocks.redstone_block);
			break;
		default:
			oreClass = OresPlus.config.getOre(this.getDefaultConfig());
			if (oreClass.enabled)
				this.ore = new OreItemStack(new BlockOre(oreClass));
			this.block = new OreItemStack(new BlockCore(Material.iron, this.blockName)
				.setCreativeTab(CreativeTabs.tabBlock)
				.setHardness(5.0F)
				.setResistance(10.0F)
				.setStepSound(Block.soundTypeMetal));
		}
		
		// Register Nether Ore
		oreClass = OresPlus.config.getOre(this.getDefaultConfigNether());
		if (oreClass.enabled)
			this.netherOre = new OreItemStack(new BlockOre(oreClass, true));
		
	}

	@Override
	public void registerItems() {
		switch (this) {
		case Redstone:
			this.dust = new OreItemStack(net.minecraft.init.Items.redstone);
			break;
		default:
			this.dust = new OreItemStack(new ItemCore(this.dustName).setCreativeTab(CreativeTabs.tabMaterials));
		}
		this.tinyDust = new OreItemStack(new ItemCore(this.tinyDustName).setCreativeTab(CreativeTabs.tabMaterials));
	}

	@Override
	public void registerRecipes() {
		//Add tiny dust -> dust recipes
		RecipeManager.addShapedRecipe(this.dust.newStack(), "ttt", "ttt", "ttt", 't', this.tinyDustName);
		
		//Add dust -> tiny dust recipes
		RecipeManager.addShapelessRecipe(this.tinyDust.newStack(9), this.dustName);
		
		//Add nether ore smelting
		RecipeManager.addSmelting(this.netherOre.newStack(), this.ore.newStack(2), 0.0F);
		
		//Add dust -> ore block
		RecipeManager.addShapedRecipe(this.block.newStack(), "ddd", "ddd", "ddd", 'd', this.dustName);
		
		//Add ore block -> dust
		RecipeManager.addShapelessRecipe(this.dust.newStack(9), this.block.newStack());
	}

	@Override
	public void registerAspects() {
		if (!Helpers.ThaumCraft.isLoaded())
			return;
		
	    if (this != Redstone) {
	        ThaumcraftApi.registerObjectTag(this.oreName, new AspectList().add(Aspect.EARTH, 1).add(this._aspect, 2).add(this._secondaryAspect, 2));
	        ThaumcraftApi.registerObjectTag(this.dustName, new AspectList().add(this._aspect, 2).add(this._secondaryAspect, 1));
	        ThaumcraftApi.registerObjectTag(this.blockName, new AspectList().add(this._aspect, 3).add(this._secondaryAspect, 4));
	      }
	      ThaumcraftApi.registerObjectTag(this.netherOreName, new AspectList().add(Aspect.EARTH, 1).add(Aspect.FIRE, 1).add(this._aspect, 2).add(this._secondaryAspect, 2));
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
