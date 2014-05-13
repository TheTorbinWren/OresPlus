package tw.oresplus.ores;

import java.util.Random;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import tw.oresplus.api.Ores;
import tw.oresplus.blocks.BlockOre;
import tw.oresplus.core.helpers.Helpers;
import tw.oresplus.items.ItemCore;
import tw.oresplus.items.OreItems;
import tw.oresplus.recipes.OreItemStack;
import tw.oresplus.recipes.RecipeManager;
import tw.oresplus.recipes.RecipeType;

public enum AdvancedOres implements IOreList {
	Bauxite (1, new AspectList().add(Aspect.EXCHANGE, 2)),
	Cassiterite (2, new AspectList().add(Aspect.EXCHANGE, 1).add(Aspect.CRYSTAL, 1)),
	Galena (2, new AspectList().add(Aspect.EXCHANGE, 1).add(Aspect.ORDER, 1)),
	Tetrahedrite (2, new AspectList().add(Aspect.EXCHANGE, 2))
	;
	
	public String oreName;
	public String crushedOreName;
	public String purifiedCrushedOreName;
	public String dustName;
	public String tinyDustName;
	
	public OreItemStack ore;
	public OreItemStack crushedOre;
	public OreItemStack purifiedCrushedOre;
	public OreItemStack dust;
	public OreItemStack tinyDust;
	
	private int _harvestLevel;
	private AspectList _aspects;
	
	private AdvancedOres(int harvestLevel) {
		this(harvestLevel, new AspectList());
	}
	
	private AdvancedOres(int harvestLevel, AspectList aspects) {
		this.oreName = "ore" + this.toString();
		this.crushedOreName = "crushed" + this.toString();
		this.purifiedCrushedOreName = "crushedPurified" + this.toString();
		this.dustName = "dust" + this.toString();
		this.tinyDustName = "dustTiny" + this.toString();
		this._harvestLevel = harvestLevel;
		this._aspects = aspects;
	}

	@Override
	public OreClass getDefaultConfig() {
		return new OreClass(this.oreName, true, this._harvestLevel, 0, 0, OreDrops.ORE, OreSources.DEFAULT);
	}

	@Override
	public OreClass getDefaultConfigNether() {
		return null;
	}

	@Override
	public void registerBlocks() {
		this.ore = new OreItemStack(new BlockOre(this.getDefaultConfig()));
	}

	@Override
	public void registerItems() {
		this.crushedOre = new OreItemStack(new ItemCore(this.crushedOreName).setCreativeTab(CreativeTabs.tabMaterials));
		this.purifiedCrushedOre = new OreItemStack(new ItemCore(this.purifiedCrushedOreName).setCreativeTab(CreativeTabs.tabMaterials));
		this.dust = new OreItemStack(new ItemCore(this.dustName).setCreativeTab(CreativeTabs.tabMaterials));
		this.tinyDust = new OreItemStack(new ItemCore(this.tinyDustName).setCreativeTab(CreativeTabs.tabMaterials));
	}

	@Override
	public void registerRecipes() {
		// Dust -> Tiny Dust Recipe
		RecipeManager.addShapelessRecipe(this.tinyDust.newStack(9), this.dustName);
		// Tiny Dust -> Dust Recipe
		RecipeManager.addShapedRecipe(this.dust.newStack(), "ttt", "ttt", "ttt", 't', this.tinyDustName);
		// Grinding Recipes
		RecipeManager.addGrinderRecipe(this.ore.newStack(), this.crushedOre.newStack(2));
		// Ore Washing Recipe
		if (Helpers.IC2.isLoaded()) {
			OreItemStack stoneDust = new OreItemStack(new ItemStack(Helpers.IC2.getItem("itemDust"), 1, 9));
			NBTTagCompound metadata = new NBTTagCompound();
			metadata.setInteger("amount", 1000);
			Helpers.IC2.registerRecipe(RecipeType.OreWasher, this.crushedOre.newStack(), (NBTTagCompound)(metadata.copy()), this.purifiedCrushedOre.newStack(), stoneDust.newStack(2), this.tinyDust.newStack());
		}
		// Thermal Centrifuge Recipe
		if (Helpers.IC2.isLoaded()) {
			NBTTagCompound metadata = new NBTTagCompound();
			metadata.setInteger("minHeat", 1500);
			Helpers.IC2.registerRecipe(RecipeType.Centrifuge, this.purifiedCrushedOre.newStack(), (NBTTagCompound)(metadata.copy()), this.dust.newStack(2));
			metadata.setInteger("minHeat", 2500);
			switch (this) {
			case Bauxite:
				Helpers.IC2.registerRecipe(RecipeType.Centrifuge, this.dust.newStack(), (NBTTagCompound)(metadata.copy()), MetallicOres.Aluminium.dust.newStack());
				break;
			case Cassiterite:
				Helpers.IC2.registerRecipe(RecipeType.Centrifuge, this.dust.newStack(), (NBTTagCompound)(metadata.copy()), MetallicOres.Tin.dust.newStack());
				break;
			case Galena:
				Helpers.IC2.registerRecipe(RecipeType.Centrifuge, this.dust.newStack(2), (NBTTagCompound)(metadata.copy()), MetallicOres.Lead.dust.newStack(), DustOres.Sulfur.dust.newStack());
				break;
			case Tetrahedrite:
				Helpers.IC2.registerRecipe(RecipeType.Centrifuge, this.dust.newStack(7), (NBTTagCompound)(metadata.copy()), MetallicOres.Copper.dust.newStack(3), OreItems.dustAntimony.item.newStack(), DustOres.Sulfur.dust.newStack(3));
				break;
			default:
			}
		}
	}

	@Override
	public void registerAspects() {
		if (!Helpers.ThaumCraft.isLoaded())
			return;
		ThaumcraftApi.registerObjectTag(this.oreName, this._aspects.add(Aspect.EARTH, 1).add(Aspect.METAL, 2));
		ThaumcraftApi.registerObjectTag(this.crushedOreName, this._aspects.add(Aspect.EARTH, 1).add(Aspect.METAL, 2));
		ThaumcraftApi.registerObjectTag(this.purifiedCrushedOreName, this._aspects.add(Aspect.METAL, 3));
		ThaumcraftApi.registerObjectTag(this.dustName, this._aspects.add(Aspect.METAL, 2).add(Aspect.ENTROPY, 1));
		ThaumcraftApi.registerObjectTag(this.tinyDustName, this._aspects.add(Aspect.METAL, 1).add(Aspect.ENTROPY, 1));
	}

	@Override
	public int getTradeToAmount(Random random) {
		return 0;
	}

	@Override
	public int getTradeFromAmount(Random random) {
		return 0;
	}

}
