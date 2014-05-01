package tw.oresplus.ores;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
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
import tw.oresplus.recipes.GrinderRecipe;
import tw.oresplus.recipes.OreItemStack;
import tw.oresplus.recipes.RecipeManager;

public enum MetallicOres implements IOreList {
	Adamantine(3, Aspect.ARMOR),
	Aluminium(2, Aspect.EXCHANGE),
	Ardite(4, Aspect.VOID),
	Brass(Aspect.MECHANISM, true),
	Bronze(Aspect.MECHANISM, true),
	Cobalt(4, Aspect.CRAFT),
	Coldiron(2, Aspect.COLD),
	Copper(1, Aspect.EXCHANGE),
	Electrum(Aspect.EXCHANGE, true),
	Gold(2, Aspect.GREED),
	Iron(1, Aspect.METAL),
	Lead(1, Aspect.ORDER),
	Manganese(2, Aspect.ORDER),
	Mithral(3, Aspect.MAGIC),
	Nickel(2, Aspect.ORDER),
	Osmium(1, Aspect.ARMOR),
	Platinum(2, Aspect.GREED),
	Silver(2, Aspect.GREED),
	Tin(1, Aspect.CRYSTAL),
	Zinc(2, Aspect.CRYSTAL);
	
	public String oreName;
	public String netherOreName;
	public String blockName;
	public String ingotName;
	public String nuggetName;
	public String dustName;
	public String tinyDustName;
	public String crushedOreName;
	public String purifiedCrushedOreName;
	
	public OreItemStack ore;
	public OreItemStack netherOre;
	public OreItemStack block;
	public OreItemStack ingot;
	public OreItemStack nugget;
	public OreItemStack dust;
	public OreItemStack tinyDust;
	public OreItemStack crushedOre;
	public OreItemStack purifiedCrushedOre;
	
	private int _harvestLevel;
	private int _xpLow;
	private int _xpHigh;
	private OreDrops _drops;
	private OreSources _source;
	private boolean _isAlloy;
	private float _smeltXP;
	private Aspect _secondaryAspect;
	private int _tradeToAmount;
	private int _tradeFromAmount;
	
	public boolean enabled;
	
	private MetallicOres(Aspect secondaryAspect, boolean isAlloy) {
		this(0, secondaryAspect, isAlloy);
	}
	
	private MetallicOres(int harvestLevel, Aspect secondaryAspect) {
		this(harvestLevel, secondaryAspect, false);
	}
		
	private MetallicOres(int harvestLevel, Aspect secondaryAspect, boolean isAlloy) {
		this(harvestLevel, secondaryAspect, isAlloy, 0, 0);
	}
	
	private MetallicOres(int harvestLevel, Aspect secondaryAspect, boolean isAlloy, int tradeToAmount, int tradeFromAmount) {
		this.oreName = "ore" + this.toString();
		this.netherOreName = "oreNether" + this.toString();
		this.blockName = "block" + this.toString();

		this.ingotName = "ingot" + this.toString();
		this.nuggetName = "nugget" + this.toString();
		this.dustName = "dust" + this.toString();
		this.tinyDustName = "dustTiny" + this.toString(); 
		this.crushedOreName = "crushed" + this.toString();
		this.purifiedCrushedOreName = "crushedPurified" + this.toString(); 
		
		this.enabled = true;

		this._harvestLevel = harvestLevel;
		this._xpLow = 0;
		this._xpHigh = 0;
		this._source = OreSources.DEFAULT;
		this._isAlloy = isAlloy;
		this._drops = OreDrops.ORE;
		this._secondaryAspect = secondaryAspect;
	}

	@Override
	public OreClass getDefaultConfig() {
		return new OreClass(this.oreName, this.enabled, this._harvestLevel, 
				this._xpLow, this._xpHigh, this._drops, this._source);
	}

	@Override
	public OreClass getDefaultConfigNether() {
		return new OreClass(this.netherOreName, this.enabled, this._harvestLevel, 
				this._xpLow, this._xpHigh, this._drops, this._source);
	}
	
	private boolean isVanilla() {
		return (this == Iron || this == Gold);
	}

	@Override
	public void registerBlocks() {
		// Register Ore
		if (!this.isVanilla() && !this._isAlloy) {
			OreClass oreConfig = OresPlus.config.getOre(this.getDefaultConfig());
			if (oreConfig.enabled) {
				this.ore = new OreItemStack(new BlockOre(oreConfig));
			}
		}
		else if (this == Gold) {
			this.ore = new OreItemStack(net.minecraft.init.Blocks.gold_ore);
		}
		else if (this == Iron) {
			this.ore = new OreItemStack(net.minecraft.init.Blocks.iron_ore);
		}
		
		// Register Nether Ore
		if (!this._isAlloy)	{
			OreClass oreConfig = OresPlus.config.getOre(this.getDefaultConfigNether());
			if (oreConfig.enabled) {
				this.netherOre = new OreItemStack(new BlockOre(oreConfig, true));
			}
		}
		
		// Register Storage Block
		if (!this.isVanilla()) {
			this.block = new OreItemStack(new BlockCore(Material.iron, this.blockName)
				.setCreativeTab(CreativeTabs.tabBlock)
				.setHardness(5.0F)
				.setResistance(10.0F)
				.setStepSound(Block.soundTypeMetal));
		}
		else if (this == Gold) {
			this.block = new OreItemStack(net.minecraft.init.Blocks.gold_block);
		}
		else if (this == Iron) {
			this.block = new OreItemStack(net.minecraft.init.Blocks.iron_block);
		}
	}

	@Override
	public void registerItems() {
		// Register Ingot
		if (!this.isVanilla()) {
			this.ingot = new OreItemStack(new ItemCore(this.ingotName).setCreativeTab(CreativeTabs.tabMaterials));
		}
		else if (this == Gold) {
			this.ingot = new OreItemStack(net.minecraft.init.Items.gold_ingot);
		}
		else if (this == Iron) {
			this.ingot = new OreItemStack(net.minecraft.init.Items.iron_ingot);
		}
		
		// Register Nugget
		if (this != Gold) {
			this.nugget = new OreItemStack(new ItemCore(this.nuggetName).setCreativeTab(CreativeTabs.tabMaterials));
		}
		else {
			this.nugget = new OreItemStack(net.minecraft.init.Items.gold_nugget);
		}
		
		// Register Dust & Tiny Dust
		this.dust = new OreItemStack(new ItemCore(this.dustName).setCreativeTab(CreativeTabs.tabMaterials));
		this.tinyDust = new OreItemStack(new ItemCore(this.tinyDustName).setCreativeTab(CreativeTabs.tabMaterials));

		// Register Crushed & Purified Crushed Ores
		if (!this._isAlloy) {
			this.crushedOre = new OreItemStack(new ItemCore(this.crushedOreName).setCreativeTab(CreativeTabs.tabMaterials));
			this.purifiedCrushedOre = new OreItemStack(new ItemCore(this.purifiedCrushedOreName).setCreativeTab(CreativeTabs.tabMaterials));
		}
	}

	@Override
	public void registerRecipes() {
		OreItemStack stoneDust = new OreItemStack(new ItemStack(Helpers.IC2.getItem("itemDust"), 1, 9));

		if (!this.isVanilla()) {
			// add ingot -> oreBlock recipe
			RecipeManager.addShapedRecipe(this.block.newStack(), "iii", "iii", "iii", 'i', ingotName);
			
			// add oreBlock -> ingot recipe
			RecipeManager.addShapelessRecipe(this.ingot.newStack(9), this.block.newStack());
			
			// add ore smelting recipe
			if (!this._isAlloy) {
				RecipeManager.addSmelting(this.ore.newStack(), this.ingot.newStack(), this._smeltXP);
			}
		}
		
		if (!this.toString().equals("Gold")) {
			// add ingot -> nugget recipe
			if (this.toString().equals("Iron")) {
				RecipeManager.addShapelessRecipe(this.nugget.newStack(9), this.ingot.newStack());
			}
			else {
				RecipeManager.addShapelessRecipe(this.nugget.newStack(9), ingotName);
			}
			
			// add nugget -> ingot recipe
			RecipeManager.addShapedRecipe(this.ingot.newStack(), "nnn", "nnn", "nnn", 'n', this.nuggetName);
		}
		
		// add ingot Grinder recipe
		if (this.isVanilla()) {
			Ores.grinderRecipes.add(this.ingot.newStack(), this.dust.newStack());
		}
		else {
			Ores.grinderRecipes.add(this.ingotName, this.dust.newStack());
		}
		
		
		//add ingot macerator recipe
		if (Helpers.IC2.isLoaded()) {
			Helpers.IC2.registerRecipe("Macerator", this.ingot.newStack(), this.dust.newStack());
		}
		
		if (!this._isAlloy) {
			// add nether ore -> ore smelting recipe
			RecipeManager.addSmelting(this.netherOre.newStack(), this.ore.newStack(2), 0.0F);
			
			// add crushed ore smelting recipe
			RecipeManager.addSmelting(this.crushedOre.newStack(), this.ingot.newStack(), this._smeltXP);
			
			// add purified crushed ore smelting recipe
			RecipeManager.addSmelting(this.purifiedCrushedOre.newStack(), this.ingot.newStack(), this._smeltXP);
			
			// add ore grinder recipe
			RecipeManager.addGrinderRecipe(this.oreName, this.crushedOre.newStack(2));
			
			if (Helpers.IC2.isLoaded()) {
				// add ore macerator recipe
				Helpers.IC2.registerRecipe("Macerator", this.ore.newStack(), this.crushedOre.newStack(2));
				
				// add ore washing recipe
				NBTTagCompound metadata = new NBTTagCompound();
				metadata.setInteger("amount", 1000);
				switch(this) {
				case Lead:
					Helpers.IC2.registerRecipe("OreWasher", this.crushedOre.newStack(), metadata, new ItemStack[] {
						this.purifiedCrushedOre.newStack(),
						DustOres.Sulfur.tinyDust.newStack(3),
						stoneDust.newStack()	});
					break;
				default:
					Helpers.IC2.registerRecipe("OreWasher", this.crushedOre.newStack(), metadata, new ItemStack[] {
						this.purifiedCrushedOre.newStack(),
						this.tinyDust.newStack(2),
						stoneDust.newStack()	});
				}
			}
			
			if (Helpers.RailCraft.isLoaded()) {
				// add RC grinder recipe
				Helpers.RailCraft.registerRecipe("rockCrusher", this.ore.newStack(), this.crushedOre.newStack(2));
			}
		}
		
		// add dust smelting recipe
		RecipeManager.addSmelting(this.dust.newStack(), this.ingot.newStack(), this._smeltXP);
		
		// add tiny dust -> dust recipe
		RecipeManager.addShapedRecipe(this.dust.newStack(), "ttt", "ttt", "ttt", 't', this.tinyDustName);
		
		// add dust -> tiny dust recipe
		RecipeManager.addShapelessRecipe(this.tinyDust.newStack(9), this.dust.newStack());
	}

	public void registerAspects() {
	    if (!Helpers.ThaumCraft.isLoaded()) {
	        return;
	      }
	      if ((!isVanilla()) && (this != Copper) && (this != Tin) && (this != Silver) && (this != Lead)) {
	        ThaumcraftApi.registerObjectTag(this.nuggetName, new AspectList().add(Aspect.METAL, 1));
	        ThaumcraftApi.registerObjectTag(this.ingotName, new AspectList().add(Aspect.METAL, 3).add(this._secondaryAspect, 1));
	        ThaumcraftApi.registerObjectTag(this.dustName, new AspectList().add(Aspect.METAL, 2).add(Aspect.ENTROPY, 1).add(this._secondaryAspect, 1));
	        ThaumcraftApi.registerObjectTag(this.oreName, new AspectList().add(Aspect.METAL, 2).add(Aspect.EARTH, 1).add(this._secondaryAspect, 1));
	        ThaumcraftApi.registerObjectTag(this.blockName, new AspectList().add(Aspect.METAL, 5).add(this._secondaryAspect, 3));
	      }
	      ThaumcraftApi.registerObjectTag(this.netherOreName, new AspectList().add(Aspect.METAL, 2).add(this._secondaryAspect, 2).add(Aspect.EARTH, 1).add(Aspect.FIRE, 1));
	}

	@Override
	public int getTradeToAmount(Random random) {
		if (this._tradeToAmount <= 0) {
			return 0;
		}
		return this._tradeToAmount + random.nextInt(this._tradeToAmount / 4);
	}

	@Override
	public int getTradeFromAmount(Random random) {
		if (this._tradeFromAmount <= 0) {
			return 0;
		}
		return this._tradeFromAmount + random.nextInt(this._tradeFromAmount / 4);
	}
}
