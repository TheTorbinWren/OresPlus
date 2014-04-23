package tw.oresplus.ores;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import tw.oresplus.api.Ores;
import tw.oresplus.blocks.BlockCore;
import tw.oresplus.blocks.BlockOre;
import tw.oresplus.core.Config;
import tw.oresplus.core.OreClass;
import tw.oresplus.core.helpers.Helpers;
import tw.oresplus.items.ItemCore;
import tw.oresplus.recipes.OreItemStack;
import tw.oresplus.recipes.RecipeManager;

public enum DustOres implements IOres {
		Nikolite(2, Aspect.ENERGY, Aspect.MECHANISM, OreDrops.NIKOLITE),
		Phosphorite(1, Aspect.ENERGY, Aspect.HARVEST, OreDrops.PHOSPHORITE),
		Potash(1, Aspect.ENERGY, Aspect.HARVEST, OreDrops.POTASH),
		Redstone(2, Aspect.ENERGY, Aspect.MECHANISM),
		Saltpeter(1, Aspect.FIRE, Aspect.HARVEST, OreDrops.SALTPETER),
		Sulfur(1, Aspect.FIRE, Aspect.ENTROPY, OreDrops.SULFUR)
	;
		
	public String oreName;
	public String netherOreName;
	public String oreBlockName;
	public String dustName;
	public String tinyDustName;
	
	public ItemStack ore;
	public ItemStack netherOre;
	public ItemStack oreBlock;
	public ItemStack dust;
	public ItemStack tinyDust;
	
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
		this.oreBlockName = "oreBlock" + this.toString();
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
		OreClass oreClass;
		switch (this) {
		case Redstone:
			this.ore = new ItemStack(net.minecraft.init.Blocks.redstone_ore, 1);
			Ores.manager.registerOre(this.oreName, net.minecraft.init.Blocks.redstone_ore);
			this.oreBlock = new ItemStack(net.minecraft.init.Blocks.redstone_block, 1);
			Ores.manager.registerOre(this.oreBlockName, net.minecraft.init.Blocks.redstone_block);
			break;
		default:
			oreClass = Config.getOre(this.getDefaultConfig());
			if (oreClass.enabled)
				this.ore = new ItemStack(new BlockOre(oreClass), 1);
			this.oreBlock = new ItemStack(new BlockCore(Material.iron, this.oreBlockName)
				.setCreativeTab(CreativeTabs.tabBlock)
				.setHardness(5.0F)
				.setResistance(10.0F)
				.setStepSound(Block.soundTypeMetal), 1);
		}
		oreClass = Config.getOre(this.getDefaultConfigNether());
		if (oreClass.enabled)
			this.netherOre = new ItemStack(new BlockOre(oreClass), 1);
		
	}

	@Override
	public void registerItems() {
		switch (this) {
		case Redstone:
			this.dust = new ItemStack(net.minecraft.init.Items.redstone, 1);
			break;
		default:
			this.dust = new ItemStack(new ItemCore(this.dustName).setCreativeTab(CreativeTabs.tabMaterials), 1);
		}
		this.tinyDust = new ItemStack(new ItemCore(this.tinyDustName).setCreativeTab(CreativeTabs.tabMaterials));
	}

	@Override
	public void registerRecipes() {
		OreItemStack dustStack = new OreItemStack(this.dust);
		OreItemStack tinyDustStack = new OreItemStack(this.tinyDust);
		OreItemStack oreStack = new OreItemStack(this.ore);
		OreItemStack netherOreStack = new OreItemStack(this.netherOre);
		OreItemStack oreBlockStack = new OreItemStack(this.oreBlock);
		
		//Add tiny dust -> dust recipes
		RecipeManager.addShapedRecipe(dustStack.newStack(), "ttt", "ttt", "ttt", 't', this.tinyDustName);
		
		//Add dust -> tiny dust recipes
		RecipeManager.addShapelessRecipe(tinyDustStack.newStack(9), this.dustName);
		
		//Add nether ore smelting
		RecipeManager.addSmelting(netherOreStack.newStack(), oreStack.newStack(2), 0.0F);
		
		//Add dust -> ore block
		RecipeManager.addShapedRecipe(oreBlockStack.newStack(), "ddd", "ddd", "ddd", 'd', this.dustName);
		
		//Add ore block -> dust
		RecipeManager.addShapelessRecipe(dustStack.newStack(9), oreBlockStack.newStack());
	}

	@Override
	public void registerAspects() {
		if (!Helpers.ThaumCraft.isLoaded())
			return;
		
	    if (this != Redstone) {
	        ThaumcraftApi.registerObjectTag(this.oreName, new AspectList().add(Aspect.EARTH, 1).add(this._aspect, 2).add(this._secondaryAspect, 2));
	        ThaumcraftApi.registerObjectTag(this.dustName, new AspectList().add(this._aspect, 2).add(this._secondaryAspect, 1));
	        ThaumcraftApi.registerObjectTag(this.oreBlockName, new AspectList().add(this._aspect, 3).add(this._secondaryAspect, 4));
	      }
	      ThaumcraftApi.registerObjectTag(this.netherOreName, new AspectList().add(Aspect.EARTH, 1).add(Aspect.FIRE, 1).add(this._aspect, 2).add(this._secondaryAspect, 2));
	}

}
