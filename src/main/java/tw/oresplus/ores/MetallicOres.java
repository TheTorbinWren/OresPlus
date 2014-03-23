package tw.oresplus.ores;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import tw.oresplus.OresPlus;
import tw.oresplus.api.OresPlusAPI;
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
	Mithral(3),
	Nickel(2),
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
	public String getOreName() {
		return this.oreName;
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
				new BlockOre(oreConfig);
			}
		}
		
		if (!this._isAlloy)	{
			OreClass oreConfig = Config.getOre(this.getDefaultConfigNether());
			if (oreConfig.enabled) {
				new BlockOre(oreConfig);
			}
		}
		
		if (!this.isVanilla()) {
			new BlockCore(Material.iron, this.oreBlockName)
				.setCreativeTab(CreativeTabs.tabBlock)
				.setHardness(5.0F)
				.setResistance(10.0F)
				.setStepSound(Block.soundTypeMetal);
		}
	}

	@Override
	public void registerItems() {
		if (!this.isVanilla())
			new ItemCore(this.ingotName).setCreativeTab(CreativeTabs.tabMaterials);
		if (!this.toString().equals("Gold"))
			new ItemCore(this.nuggetName).setCreativeTab(CreativeTabs.tabMaterials);
		new ItemCore(this.dustName).setCreativeTab(CreativeTabs.tabMaterials);
		new ItemCore(this.tinyDustName).setCreativeTab(CreativeTabs.tabMaterials);
		if (!this._isAlloy) {
			new ItemCore(this.crushedOreName).setCreativeTab(CreativeTabs.tabMaterials);
			new ItemCore(this.purifiedCrushedOreName).setCreativeTab(CreativeTabs.tabMaterials);
		}
	}

	@Override
	public void registerRecipes() {
		RecipeItemStack ingot = new RecipeItemStack();
		RecipeItemStack block = new RecipeItemStack();
		RecipeItemStack ore = new RecipeItemStack();
		if (this.toString().equals("Gold")) {
			ingot.setSource(new ItemStack(net.minecraft.init.Items.gold_ingot, 1));
			block.setSource(new ItemStack(net.minecraft.init.Blocks.gold_block, 1));
			ore.setSource(new ItemStack(net.minecraft.init.Blocks.gold_ore, 1));
		}
		else if (this.toString().equals("Iron")) {
			ingot.setSource(new ItemStack(net.minecraft.init.Items.iron_ingot, 1));
			block.setSource(new ItemStack(net.minecraft.init.Blocks.iron_block, 1));
			ore.setSource(new ItemStack(net.minecraft.init.Blocks.iron_ore, 1));
		}
		else {
			ingot.setSource(new ItemStack(OresPlusAPI.getItem(this.ingotName), 1));
			block.setSource(new ItemStack(OresPlusAPI.getBlock(this.oreBlockName), 1));
			ore.setSource(new ItemStack(OresPlusAPI.getBlock(this.oreName), 1));
		}
		RecipeItemStack dust = new RecipeItemStack(OresPlusAPI.getItem(this.dustName), 1);
		RecipeItemStack crushedOre = new RecipeItemStack(OresPlusAPI.getItem(this.crushedOreName), 1);
		RecipeItemStack nugget = new RecipeItemStack(OresPlusAPI.getItem(this.nuggetName), 1);
		RecipeItemStack dustTiny = new RecipeItemStack(OresPlusAPI.getItem(this.tinyDustName), 1);
		RecipeItemStack purifiedCrushedOre = new RecipeItemStack(OresPlusAPI.getItem(this.purifiedCrushedOreName), 1);

		if (!this.isVanilla()) {
			// add ingot -> oreBlock recipe
			block.setStackSize(1);
			RecipeManager.addShapedRecipe(block.getSource(), "iii", "iii", "iii", 'i', ingotName);
			
			// add oreBlock -> ingot recipe
			ingot.setStackSize(9);
			block.setStackSize(1);
			RecipeManager.addShapelessRecipe(ingot.getSource(), block.getSource());
			
			// add ore smelting recipe
			ingot.setStackSize(1);
			if (!this._isAlloy) {
				RecipeManager.addSmelting(new ItemStack(OresPlusAPI.getBlock(oreName), 1), ingot.getSource(), this._smeltXP);
			}
		}
		
		if (!this.toString().equals("Gold")) {
			// add ingot -> nugget recipe
			nugget.setStackSize(9);
			ingot.setStackSize(1);
			if (this.toString().equals("Iron")) {
				RecipeManager.addShapelessRecipe(nugget.getSource(), ingot.getSource());
			}
			else {
				RecipeManager.addShapelessRecipe(nugget.getSource(), ingotName);
			}
			
			// add nugget -> ingot recipe
			ingot.setStackSize(1);
			RecipeManager.addShapedRecipe(ingot.getSource(), "nnn", "nnn", "nnn", 'n', this.nuggetName);
		}
		
		// add ingot Grinder recipe
		
		ingot.setStackSize(1);
		dust.setStackSize(1);
		if (this.isVanilla())
			RecipeManager.addGrinderRecipe(ingot.getSource(), dust.getSource());
		else
			RecipeManager.addGrinderRecipe(this.ingotName, dust.getSource());
		
		//add ingot macerator recipe
		if (Helpers.IC2.isLoaded()) {
			ingot.setStackSize(1);
			dust.setStackSize(1);
			Helpers.IC2.registerRecipe("Macerator", ingot.getSource(), dust.getSource());
		}
		
		if (!this._isAlloy) {
			// add nether ore -> ore smelting recipe
			ore.setStackSize(2);
			RecipeManager.addSmelting(new ItemStack(OresPlusAPI.getBlock(this.netherOreName), 1), ore.getSource(), 0.0F);
			
			// add crushed ore smelting recipe
			crushedOre.setStackSize(1);
			ingot.setStackSize(1);
			RecipeManager.addSmelting(crushedOre.getSource(), ingot.getSource(), this._smeltXP);
			
			// add purified crushed ore smelting recipe
			purifiedCrushedOre.setStackSize(1);
			ingot.setStackSize(1);
			RecipeManager.addSmelting(purifiedCrushedOre.getSource(), ingot.getSource(), this._smeltXP);
			
			// add ore grinder recipe
			crushedOre.setStackSize(2);
			RecipeManager.addGrinderRecipe(this.oreName, crushedOre.getSource());
			
			if (Helpers.IC2.isLoaded()) {
				// add ore macerator recipe
				ore.setStackSize(1);
				crushedOre.setStackSize(2);
				Helpers.IC2.registerRecipe("Macerator", ore.getSource(), crushedOre.getSource());
				
				// add ore washing recipe
				crushedOre.setStackSize(1);
				dustTiny.setStackSize(2);
				purifiedCrushedOre.setStackSize(1);
				NBTTagCompound metadata = new NBTTagCompound();
				metadata.setInteger("amount", 1000);
				Helpers.IC2.registerRecipe("OreWasher", crushedOre.getSource(), metadata, new ItemStack[] {
					purifiedCrushedOre.getSource(),
					dustTiny.getSource(),
					new ItemStack(Helpers.IC2.getItem("itemDust"), 1, 9)	});
			}
		}
		
		// add dust smelting recipe
		ingot.setStackSize(1);
		dust.setStackSize(1);
		RecipeManager.addSmelting(dust.getSource(), ingot.getSource(), this._smeltXP);
		
		// add tiny dust -> dust recipe
		dust.setStackSize(1);
		RecipeManager.addShapedRecipe(dust.getSource(), "ttt", "ttt", "ttt", 't', this.tinyDustName);
		
		// add dust -> tiny dust recipe
		dust.setStackSize(1);
		dustTiny.setStackSize(9);
		RecipeManager.addShapelessRecipe(dustTiny.getSource(), dust.getSource());
	}
}
