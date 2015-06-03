package tw.oresplus.ores;

import java.util.Random;

import mekanism.api.gas.Gas;
import mekanism.api.gas.GasRegistry;
import mekanism.api.gas.GasStack;
import mekanism.api.gas.OreGas;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import tw.oresplus.OresPlus;
import tw.oresplus.api.Ores;
import tw.oresplus.blocks.BlockOre;
import tw.oresplus.core.helpers.Helpers;
import tw.oresplus.fluids.OreFluid;
import tw.oresplus.gases.Gases;
import tw.oresplus.items.ItemCore;
import tw.oresplus.items.Items;
import tw.oresplus.recipes.OreItemStack;
import tw.oresplus.recipes.RecipeManager;
import tw.oresplus.recipes.RecipeType;

public enum MineralOres implements IOreList {
	Bauxite (1, new AspectList().add(Aspect.EXCHANGE, 2), 0.0D),
	Cassiterite (2, new AspectList().add(Aspect.EXCHANGE, 1).add(Aspect.CRYSTAL, 1), 0.0D),
	Cinnabar (2, new AspectList().add(Aspect.FIRE, 1).add(Aspect.POISON, 1), OreDrops.CINNABAR, 0.0D),
	Cooperite (2, new AspectList().add(Aspect.ELDRITCH, 1).add(Aspect.GREED, 1), 0.0D),
	Galena (2, new AspectList().add(Aspect.EXCHANGE, 1).add(Aspect.ORDER, 1), 0.0D),
	Ilmenite(3, new AspectList(), 0.0D),
	Pyrite (2, new AspectList().add(Aspect.FIRE, 1).add(Aspect.METAL, 1), OreDrops.PYRITE, 0.0D),
	Sphalerite (2, new AspectList().add(Aspect.FIRE, 1).add(Aspect.CRYSTAL, 1), OreDrops.SPHALERITE, 0.0D),
	Tetrahedrite (2, new AspectList().add(Aspect.EXCHANGE, 2), 0.0D)
	;
	
	public String oreName;
	public String crushedOreName;
	public String purifiedCrushedOreName;
	public String dustName;
	public String tinyDustName;
	public String dirtyDustName;
	public String clumpName;
	public String shardName;
	public String slurryName;
	public String cleanSlurryName;
	public String fluidSlurryName;
	
	public OreItemStack ore;
	public OreItemStack crushedOre;
	public OreItemStack purifiedCrushedOre;
	public OreItemStack dust;
	public OreItemStack tinyDust;
	public OreItemStack dirtyDust;
	public OreItemStack clump;
	public OreItemStack shard;
	
	public Gas slurry;
	public Gas cleanSlurry;
	
	public Fluid fluidSlurry;
	
	private int _harvestLevel;
	private AspectList _aspects;
	private double _uuCost;
	private OreDrops drops;
	
	private MineralOres(int harvestLevel, double uuCost) {
		this(harvestLevel, new AspectList(), uuCost);
	}
	
	private MineralOres(int harvestLevel, AspectList aspects, double uuCost) {
		this(harvestLevel, aspects, OreDrops.ORE, uuCost);
	}
	
	private MineralOres(int harvestLevel, AspectList aspects, OreDrops drops, double uuCost) {
		this.oreName = "ore" + this.toString();
		this.crushedOreName = "crushed" + this.toString();
		this.purifiedCrushedOreName = "crushedPurified" + this.toString();
		this.dustName = "dust" + this.toString();
		this.tinyDustName = "dustTiny" + this.toString();
		this.dirtyDustName = "dustDirty" + this.toString();
		this.clumpName = "clump" + this.toString();
		this.shardName = "shard" + this.toString();
		this.slurryName = "slurry" + this.toString();
		this.cleanSlurryName = "slurryClean" + this.toString();
		
		this._harvestLevel = harvestLevel;
		this._aspects = aspects;
		this._uuCost = uuCost;
		this.drops = drops;
	}

	@Override
	public OreClass getDefaultConfig() {
		return new OreClass(this.oreName, true, this._harvestLevel, 0, 0, this.drops, OreSources.DEFAULT);
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
		this.dirtyDust = new OreItemStack(new ItemCore(this.dirtyDustName).setCreativeTab(CreativeTabs.tabMaterials));
		this.clump = new OreItemStack(new ItemCore(this.clumpName).setCreativeTab(CreativeTabs.tabMaterials));
		this.shard = new OreItemStack(new ItemCore(this.shardName).setCreativeTab(CreativeTabs.tabMaterials));
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
			Helpers.IC2.registerRecipe(RecipeType.OreWasher, this.crushedOre.newStack(), (NBTTagCompound)(metadata.copy()), this.purifiedCrushedOre.newStack(), stoneDust.newStack(), this.tinyDust.newStack(2));
		}
		// Thermal Centrifuge Recipe
		if (Helpers.IC2.isLoaded()) {
			NBTTagCompound metadata = new NBTTagCompound();
			metadata.setInteger("minHeat", 1500);
			Helpers.IC2.registerRecipe(RecipeType.Centrifuge, this.purifiedCrushedOre.newStack(), (NBTTagCompound)(metadata.copy()), this.dust.newStack(2));
			metadata.setInteger("minHeat", 2500);
			switch (this) {
			case Bauxite:
				Helpers.IC2.registerRecipe(RecipeType.Centrifuge, this.dust.newStack(), (NBTTagCompound)(metadata.copy()), MetallicOres.Aluminium.dust.newStack(), MetallicOres.Iron.tinyDust.newStack());				
				break;
			case Cassiterite:
				Helpers.IC2.registerRecipe(RecipeType.Centrifuge, this.dust.newStack(), (NBTTagCompound)(metadata.copy()), MetallicOres.Tin.dust.newStack());
				break;
			case Cinnabar:
			    Helpers.IC2.registerRecipe(RecipeType.Centrifuge, this.dust.newStack(2), (NBTTagCompound)(metadata.copy()), Items.itemMercury.item.newStack(), DustyOres.Sulfur.dust.newStack());
			    break;
			case Cooperite:
				Helpers.IC2.registerRecipe(RecipeType.Centrifuge, this.dust.newStack(2), (NBTTagCompound)(metadata.copy()), MetallicOres.Platinum.dust.newStack(), DustyOres.Sulfur.dust.newStack());
				break;
			case Galena:
				Helpers.IC2.registerRecipe(RecipeType.Centrifuge, this.dust.newStack(2), (NBTTagCompound)(metadata.copy()), MetallicOres.Lead.dust.newStack(), DustyOres.Sulfur.dust.newStack());
				break;
			case Ilmenite:
				Helpers.IC2.registerRecipe(RecipeType.Centrifuge, this.dust.newStack(2), (NBTTagCompound)(metadata.copy()), MetallicOres.Iron.dust.newStack(), Items.dustTitanium.item.newStack(), MetallicOres.Manganese.tinyDust.newStack());
				break;
			case Pyrite:
			    Helpers.IC2.registerRecipe(RecipeType.Centrifuge, this.dust.newStack(3), (NBTTagCompound)(metadata.copy()), MetallicOres.Iron.dust.newStack(), DustyOres.Sulfur.dust.newStack(2));
				break;
			case Sphalerite:
			    Helpers.IC2.registerRecipe(RecipeType.Centrifuge, this.dust.newStack(2), (NBTTagCompound)(metadata.copy()), MetallicOres.Zinc.dust.newStack(), DustyOres.Sulfur.dust.newStack());
				break;
			case Tetrahedrite:
				Helpers.IC2.registerRecipe(RecipeType.Centrifuge, this.dust.newStack(7), (NBTTagCompound)(metadata.copy()), MetallicOres.Copper.dust.newStack(3), Items.dustAntimony.item.newStack(), DustyOres.Sulfur.dust.newStack(3));
				break;
			default:
			}
		}
		// Smelting Recipes
		if (OresPlus.easyMineralSmelt) {
			ItemStack smeltResult = null;
			switch (this) {
			case Bauxite:
				smeltResult = MetallicOres.Aluminium.ingot.newStack();
				break;
			case Cassiterite:
				smeltResult = MetallicOres.Tin.ingot.newStack();
				break;
			case Cinnabar:
				smeltResult = Items.itemMercury.item.newStack();
				break;
			case Cooperite:
				smeltResult = MetallicOres.Platinum.ingot.newStack();
				break;
			case Galena:
				smeltResult = MetallicOres.Lead.ingot.newStack();
				break;
			case Ilmenite:
				smeltResult = MetallicOres.Iron.ingot.newStack();
				break;
			case Pyrite:
				smeltResult = MetallicOres.Iron.ingot.newStack();
				break;
			case Sphalerite:
				smeltResult = MetallicOres.Zinc.ingot.newStack();
				break;
			case Tetrahedrite:
				smeltResult = MetallicOres.Copper.ingot.newStack();
				break;
			}
			if (smeltResult != null) {
				RecipeManager.addSmelting(this.ore.newStack(), smeltResult, 0.0f);
				RecipeManager.addSmelting(this.crushedOre.newStack(), smeltResult, 0.0f);
				RecipeManager.addSmelting(this.purifiedCrushedOre.newStack(), smeltResult, 0.0f);
				RecipeManager.addSmelting(this.dust.newStack(), smeltResult, 0.0f);
			}
		}
		if (Helpers.Mekanism.isLoaded()) {
			//register Slurries
			this.slurry = GasRegistry.register(new Gas(this.slurryName));
			this.cleanSlurry = GasRegistry.register(new Gas(this.fluidSlurry).setUnlocalizedName(this.fluidSlurryName));
			
			// enrichment chamber recipes
			Helpers.Mekanism.registerRecipe(RecipeType.EnrichmentChamber, this.ore.newStack(), this.dust.newStack(2));
			Helpers.Mekanism.registerRecipe(RecipeType.EnrichmentChamber, this.dirtyDust.newStack(), this.dust.newStack());
			// crusher recipes
			Helpers.Mekanism.registerRecipe(RecipeType.Crusher, this.clump.newStack(), this.dirtyDust.newStack());
			// purification chamber recipes
			Helpers.Mekanism.registerRecipe(RecipeType.PurificationChamber, this.ore.newStack(), this.clump.newStack(3));
			Helpers.Mekanism.registerRecipe(RecipeType.PurificationChamber, this.shard.newStack(), this.clump.newStack());
			// chemical injection chamber recipes
			Helpers.Mekanism.registerRecipe(RecipeType.ChemicalInjector, this.ore.newStack(), this.shard.newStack(4));
			// chemical dissolution chamber recipes
			Helpers.Mekanism.registerGasRecipe(RecipeType.ChemicalDissolver, this.ore.newStack(), null, new GasStack(this.slurry, 1000));
			// chemical washer recipes
			Helpers.Mekanism.registerGasRecipe(RecipeType.ChemicalWasher, new GasStack(this.slurry, 1), null, new GasStack(this.cleanSlurry, 1));
			// note there are no chemical crystalizer recipes as the clean slurries will need seperation into more base elements
			// Electrolytic Seperator Recipes
			switch (this) {
			case Bauxite:
				Helpers.Mekanism.registerGasRecipe(RecipeType.ElectrolyticSeperator, new FluidStack(this.fluidSlurry, 10), null, new GasStack(MetallicOres.Aluminium.cleanSlurry, 9), new GasStack(MetallicOres.Iron.cleanSlurry, 1));
				break;
			case Cassiterite:
				Helpers.Mekanism.registerGasRecipe(RecipeType.ElectrolyticSeperator, new FluidStack(this.fluidSlurry, 1), null, new GasStack(MetallicOres.Tin.cleanSlurry, 1), new GasStack(Gases.oxygen.gas, 1));
				break;
			case Cinnabar:
				Helpers.Mekanism.registerGasRecipe(RecipeType.ElectrolyticSeperator, new FluidStack(this.fluidSlurry, 2), null, new GasStack(Gases.Mercury.gas, 1), new GasStack(Gases.sulfurDioxideGas.gas, 1));
				break;
			case Cooperite:
				Helpers.Mekanism.registerGasRecipe(RecipeType.ElectrolyticSeperator, new FluidStack(this.fluidSlurry, 2), null, new GasStack(MetallicOres.Platinum.cleanSlurry, 1), new GasStack(Gases.sulfurDioxideGas.gas, 1));
				break;
			case Galena:
				Helpers.Mekanism.registerGasRecipe(RecipeType.ElectrolyticSeperator, new FluidStack(this.fluidSlurry, 2), null, new GasStack(MetallicOres.Lead.cleanSlurry, 1), new GasStack(Gases.sulfurDioxideGas.gas, 1));
				break;
			case Ilmenite:
				Helpers.Mekanism.registerGasRecipe(RecipeType.ElectrolyticSeperator, new FluidStack(this.fluidSlurry, 2), null, new GasStack(MetallicOres.Iron.cleanSlurry, 1), new GasStack(Gases.slurryCleanTitanium.gas, 1));
				break;
			case Pyrite:
				Helpers.Mekanism.registerGasRecipe(RecipeType.ElectrolyticSeperator, new FluidStack(this.fluidSlurry, 3), null, new GasStack(MetallicOres.Iron.cleanSlurry, 1), new GasStack(Gases.sulfurDioxideGas.gas, 2));
				break;
			case Sphalerite:
				Helpers.Mekanism.registerGasRecipe(RecipeType.ElectrolyticSeperator, new FluidStack(this.fluidSlurry, 2), null, new GasStack(MetallicOres.Zinc.cleanSlurry, 1), new GasStack(Gases.sulfurDioxideGas.gas, 1));
				break;
			case Tetrahedrite:
				Helpers.Mekanism.registerGasRecipe(RecipeType.ElectrolyticSeperator, new FluidStack(this.fluidSlurry, 7), null, new GasStack(Gases.CopperSulfide.gas, 6), new GasStack(Gases.slurryCleanAntimony.gas, 1));
				break;
			default:
				break;
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

	@Override
	public void registerFluids() {
		// TODO Auto-generated method stub
		if (FluidRegistry.isFluidRegistered(this.fluidSlurryName)) {
			this.fluidSlurry = FluidRegistry.getFluid(this.fluidSlurryName);
		}
		else {
			this.fluidSlurry = new OreFluid(this.fluidSlurryName);
		}
	}

	@Override
	public int getLaserWeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void registerGases() {
		// TODO Auto-generated method stub
		
	}

}
