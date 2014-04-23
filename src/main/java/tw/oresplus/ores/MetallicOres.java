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
import tw.oresplus.recipes.RecipeItemStack;
import tw.oresplus.recipes.RecipeManager;

public enum MetallicOres implements IOres {
	Adamantine(3),
	Brass(true),
	Bronze(true),
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
	
	public ItemStack ore;
	public ItemStack netherOre;
	public ItemStack ingot;
	public ItemStack oreBlock;
	public ItemStack nugget;
	public ItemStack dust;
	public ItemStack tinyDust;
	public ItemStack crushedOre;
	public ItemStack purifiedCrushedOre;
	
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
		return (this.toString().equals("Iron") || this.toString().equals("Gold"));
	}

	@Override
	public void registerBlocks() {
		if (!this.isVanilla() && !this._isAlloy) {
			OreClass oreConfig = Config.getOre(this.getDefaultConfig());
			if (oreConfig.enabled) {
				this.ore = new ItemStack(new BlockOre(oreConfig), 1);
			}
		}
		else {
			if (this.toString().equals("Gold"))
				this.ore = new ItemStack(net.minecraft.init.Blocks.gold_ore, 1);
			else if (this.toString().equals("Iron"))
				this.ore = new ItemStack(net.minecraft.init.Blocks.iron_ore, 1);
		}
		
		if (!this._isAlloy)	{
			OreClass oreConfig = Config.getOre(this.getDefaultConfigNether());
			if (oreConfig.enabled) {
				this.netherOre = new ItemStack(new BlockOre(oreConfig), 1);
			}
		}
		
		if (!this.isVanilla()) {
			this.oreBlock = new ItemStack(new BlockCore(Material.iron, this.oreBlockName)
				.setCreativeTab(CreativeTabs.tabBlock)
				.setHardness(5.0F)
				.setResistance(10.0F)
				.setStepSound(Block.soundTypeMetal), 1);
		}
		else {
			if (this.toString().equals("Gold"))
				this.oreBlock = new ItemStack(net.minecraft.init.Blocks.gold_block, 1);
			else if (this.toString().equals("Iron"))
				this.oreBlock = new ItemStack(net.minecraft.init.Blocks.iron_block, 1);
		}
	}

	@Override
	public void registerItems() {
		if (!this.isVanilla()) {
			this.ingot = new ItemStack(new ItemCore(this.ingotName).setCreativeTab(CreativeTabs.tabMaterials), 1);
		}
		else {
			if (this.toString().equals("Gold"))
				this.ingot = new ItemStack(net.minecraft.init.Items.gold_ingot, 1);
			else if (this.toString().equals("Iron"))
				this.ingot = new ItemStack(net.minecraft.init.Items.iron_ingot, 1);
		}
		if (!this.toString().equals("Gold"))
			this.nugget = new ItemStack(new ItemCore(this.nuggetName).setCreativeTab(CreativeTabs.tabMaterials), 1);
		else
			this.nugget = new ItemStack(net.minecraft.init.Items.gold_nugget, 1);
		this.dust = new ItemStack(new ItemCore(this.dustName).setCreativeTab(CreativeTabs.tabMaterials), 1);
		this.tinyDust = new ItemStack(new ItemCore(this.tinyDustName).setCreativeTab(CreativeTabs.tabMaterials), 1);
		if (!this._isAlloy) {
			this.crushedOre = new ItemStack(new ItemCore(this.crushedOreName).setCreativeTab(CreativeTabs.tabMaterials), 1);
			this.purifiedCrushedOre = new ItemStack(new ItemCore(this.purifiedCrushedOreName).setCreativeTab(CreativeTabs.tabMaterials), 1);
		}
	}

	@Override
	public void registerRecipes() {
		RecipeItemStack rIngot = new RecipeItemStack(this.ingot);
		RecipeItemStack rBlock = new RecipeItemStack(this.oreBlock);
		RecipeItemStack rOre = new RecipeItemStack(this.ore);
		RecipeItemStack rDust = new RecipeItemStack(this.dust);
		RecipeItemStack rCrushedOre = new RecipeItemStack(this.crushedOre);
		RecipeItemStack rNugget = new RecipeItemStack(this.nugget);
		RecipeItemStack rDustTiny = new RecipeItemStack(this.tinyDust);
		RecipeItemStack rPurifiedCrushedOre = new RecipeItemStack(this.purifiedCrushedOre);
		RecipeItemStack rNetherOre = new RecipeItemStack(this.netherOre);
		RecipeItemStack stoneDust = new RecipeItemStack(new ItemStack(Helpers.IC2.getItem("itemDust"), 1, 9));

		if (!this.isVanilla()) {
			// add ingot -> oreBlock recipe
			RecipeManager.addShapedRecipe(rBlock.getSource(), "iii", "iii", "iii", 'i', ingotName);
			
			// add oreBlock -> ingot recipe
			RecipeManager.addShapelessRecipe(rIngot.getSource(9), rBlock.getSource());
			
			// add ore smelting recipe
			if (!this._isAlloy) {
				RecipeManager.addSmelting(rOre.getSource(), rIngot.getSource(), this._smeltXP);
			}
		}
		
		if (!this.toString().equals("Gold")) {
			// add ingot -> nugget recipe
			if (this.toString().equals("Iron")) {
				RecipeManager.addShapelessRecipe(rNugget.getSource(9), rIngot.getSource());
			}
			else {
				RecipeManager.addShapelessRecipe(rNugget.getSource(9), ingotName);
			}
			
			// add nugget -> ingot recipe
			RecipeManager.addShapedRecipe(rIngot.getSource(), "nnn", "nnn", "nnn", 'n', this.nuggetName);
		}
		
		// add ingot Grinder recipe
		
		if (this.isVanilla())
			RecipeManager.addGrinderRecipe(rIngot.getSource(), rDust.getSource());
		else
			RecipeManager.addGrinderRecipe(this.ingotName, rDust.getSource());
		
		//add ingot macerator recipe
		if (Helpers.IC2.isLoaded()) {
			Helpers.IC2.registerRecipe("Macerator", rIngot.getSource(), rDust.getSource());
		}
		
		if (!this._isAlloy) {
			// add nether ore -> ore smelting recipe
			RecipeManager.addSmelting(rNetherOre.getSource(), rOre.getSource(2), 0.0F);
			
			// add crushed ore smelting recipe
			RecipeManager.addSmelting(rCrushedOre.getSource(), rIngot.getSource(), this._smeltXP);
			
			// add purified crushed ore smelting recipe
			RecipeManager.addSmelting(rPurifiedCrushedOre.getSource(), rIngot.getSource(), this._smeltXP);
			
			// add ore grinder recipe
			RecipeManager.addGrinderRecipe(this.oreName, rCrushedOre.getSource(2));
			
			if (Helpers.IC2.isLoaded()) {
				// add ore macerator recipe
				Helpers.IC2.registerRecipe("Macerator", rOre.getSource(), rCrushedOre.getSource(2));
				
				// add ore washing recipe
				NBTTagCompound metadata = new NBTTagCompound();
				metadata.setInteger("amount", 1000);
				switch(this) {
				case Lead:
					Helpers.IC2.registerRecipe("OreWasher", rCrushedOre.getSource(), metadata, new ItemStack[] {
						rPurifiedCrushedOre.getSource(),
						new ItemStack(DustOres.Sulfur.tinyDust.getItem(), 3),
						stoneDust.getSource()	});
					break;
				default:
					Helpers.IC2.registerRecipe("OreWasher", rCrushedOre.getSource(), metadata, new ItemStack[] {
						rPurifiedCrushedOre.getSource(),
						rDustTiny.getSource(2),
						stoneDust.getSource()	});
				}
			}
		}
		
		// add dust smelting recipe
		RecipeManager.addSmelting(rDust.getSource(), rIngot.getSource(), this._smeltXP);
		
		// add tiny dust -> dust recipe
		RecipeManager.addShapedRecipe(rDust.getSource(), "ttt", "ttt", "ttt", 't', this.tinyDustName);
		
		// add dust -> tiny dust recipe
		RecipeManager.addShapelessRecipe(rDustTiny.getSource(9), rDust.getSource());
	}
}
