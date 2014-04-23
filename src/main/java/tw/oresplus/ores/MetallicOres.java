package tw.oresplus.ores;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import tw.oresplus.OresPlus;
import tw.oresplus.api.Ores;
import tw.oresplus.blocks.BlockCore;
import tw.oresplus.blocks.BlockOre;
import tw.oresplus.core.Config;
import tw.oresplus.core.OreClass;
import tw.oresplus.core.helpers.Helpers;
import tw.oresplus.items.ItemCore;
import tw.oresplus.recipes.GrinderRecipe;
import tw.oresplus.recipes.OreItemStack;
import tw.oresplus.recipes.RecipeManager;

public enum MetallicOres implements IOres {
	Adamantine(3),
	Aluminium(2),
	Ardite(4),
	Brass(true),
	Bronze(true),
	Cobalt(4),
	Coldiron(2),
	Copper(1),
	Electrum(true),
	Gold(2),
	Iron(1),
	Lead(1),
	Manganese(2),
	Mithral(3),
	Nickel(2),
	Osmium(1),
	Platinum(2),
	Silver(2),
	Tin(1),
	Zinc(2);
	
	public String oreName;
	public String netherOreName;
	public String ingotName;
	public String oreBlockName;
	public String nuggetName;
	public String dustName;
	public String tinyDustName;
	public String crushedOreName;
	public String purifiedCrushedOreName;
	
	public OreItemStack ore;
	public OreItemStack netherOre;
	public OreItemStack oreBlock;
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
	
	public boolean enabled;
	
	private MetallicOres(boolean isAlloy) {
		this(0, isAlloy);
	}
	
	private MetallicOres(int harvestLevel) {
		this(harvestLevel, false);
	}
		
	private MetallicOres(int harvestLevel, boolean isAlloy) {
		this.oreName = "ore" + this.toString();
		this.netherOreName = "oreNether" + this.toString();
		this.oreBlockName = "oreBlock" + this.toString();

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
			OreClass oreConfig = Config.getOre(this.getDefaultConfig());
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
			OreClass oreConfig = Config.getOre(this.getDefaultConfigNether());
			if (oreConfig.enabled) {
				this.netherOre = new OreItemStack(new BlockOre(oreConfig));
			}
		}
		
		// Register Storage Block
		if (!this.isVanilla()) {
			this.oreBlock = new OreItemStack(new BlockCore(Material.iron, this.oreBlockName)
				.setCreativeTab(CreativeTabs.tabBlock)
				.setHardness(5.0F)
				.setResistance(10.0F)
				.setStepSound(Block.soundTypeMetal));
		}
		else if (this == Gold) {
			this.oreBlock = new OreItemStack(net.minecraft.init.Blocks.gold_block);
		}
		else if (this == Iron) {
			this.oreBlock = new OreItemStack(net.minecraft.init.Blocks.iron_block);
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
			RecipeManager.addShapedRecipe(this.oreBlock.newStack(), "iii", "iii", "iii", 'i', ingotName);
			
			// add oreBlock -> ingot recipe
			RecipeManager.addShapelessRecipe(this.ingot.newStack(9), this.oreBlock.newStack());
			
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
						new ItemStack(DustOres.Sulfur.tinyDust.getItem(), 3),
						stoneDust.newStack()	});
					break;
				default:
					Helpers.IC2.registerRecipe("OreWasher", this.crushedOre.newStack(), metadata, new ItemStack[] {
						this.purifiedCrushedOre.newStack(),
						this.tinyDust.newStack(2),
						stoneDust.newStack()	});
				}
			}
		}
		
		// add dust smelting recipe
		RecipeManager.addSmelting(this.dust.newStack(), this.ingot.newStack(), this._smeltXP);
		
		// add tiny dust -> dust recipe
		RecipeManager.addShapedRecipe(this.dust.newStack(), "ttt", "ttt", "ttt", 't', this.tinyDustName);
		
		// add dust -> tiny dust recipe
		RecipeManager.addShapelessRecipe(this.tinyDust.newStack(9), this.dust.newStack());
	}
}
