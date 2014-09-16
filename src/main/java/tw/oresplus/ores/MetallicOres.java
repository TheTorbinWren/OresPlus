package tw.oresplus.ores;

import java.util.ArrayList;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mekanism.api.gas.Gas;
import mekanism.api.gas.GasRegistry;
import mekanism.api.gas.GasStack;
import mekanism.api.gas.OreGas;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import tw.oresplus.OresPlus;
import tw.oresplus.api.Ores;
import tw.oresplus.blocks.BlockCore;
import tw.oresplus.blocks.BlockOre;
import tw.oresplus.blocks.BlockOreFluid;
import tw.oresplus.core.BucketHandler;
import tw.oresplus.core.config.ConfigCore;
import tw.oresplus.core.helpers.Helpers;
import tw.oresplus.fluids.OreFluid;
import tw.oresplus.items.ItemCore;
import tw.oresplus.items.Items;
import tw.oresplus.items.OreBucket;
import tw.oresplus.recipes.GrinderRecipe;
import tw.oresplus.recipes.OreItemStack;
import tw.oresplus.recipes.RecipeManager;
import tw.oresplus.recipes.RecipeType;

public enum MetallicOres implements IOreList {
	Adamantine(3, Aspect.ARMOR, 3500.0D),
	Aluminium(2, Aspect.EXCHANGE, 650.0D),
	Ardite(4, Aspect.VOID, 2200.0D),
	Brass(Aspect.MECHANISM, true),
	Bronze(Aspect.MECHANISM, true),
	Cobalt(4, Aspect.CRAFT, 1500.0D),
	Coldiron(2, Aspect.COLD, 2000.0D),
	Copper(1, Aspect.EXCHANGE, 165.32555827439467D),
	Electrum(Aspect.EXCHANGE, true),
	Gold(2, Aspect.GREED, 0.0D), // 1888.8470535343595
	Iron(1, Aspect.METAL, 0.0D), // 200.16909378760914
	Lead(1, Aspect.ORDER, 1673.377568334827D),
	Manganese(2, Aspect.ORDER, 800.0D),
	Mithral(3, Aspect.MAGIC, 3200.0D),
	Nickel(2, Aspect.ORDER, 1200.0D),
	Osmium(1, Aspect.ARMOR, 1000.D),
	Platinum(2, Aspect.GREED, 2500.0D),
	Silver(2, Aspect.GREED, 1750.0D),
	Tin(1, Aspect.CRYSTAL, 199.90120532935603D),
	Yellorium(2, Aspect.ENERGY, 2608.8982568638758D),
	Zinc(2, Aspect.CRYSTAL, 1100.0D);
	
	public static ArrayList<MetallicOres> mekanismOres = new ArrayList();

	public String oreName;
	public String netherOreName;
	public String blockName;
	public String ingotName;
	public String nuggetName;
	public String dustName;
	public String tinyDustName;
	public String crushedOreName;
	public String purifiedCrushedOreName;
	public String clusterName;
	public String clumpName;
	public String dirtyDustName;
	public String shardName;
	public String slurryName;
	public String cleanSlurryName;
	public String crystalName;
	public String moltenFluidName;
	public String moltenFluidBlockName;
	public String bucketName;
	
	public OreItemStack ore;
	public OreItemStack netherOre;
	public OreItemStack block;
	public OreItemStack ingot;
	public OreItemStack nugget;
	public OreItemStack dust;
	public OreItemStack tinyDust;
	public OreItemStack crushedOre;
	public OreItemStack purifiedCrushedOre;
	public OreItemStack cluster;
	public OreItemStack clump;
	public OreItemStack dirtyDust;
	public OreItemStack shard;
	public OreItemStack crystal;
	public OreItemStack bucket;
	
	public OreGas slurry;
	public OreGas cleanSlurry;
	
	public Fluid moltenFluid;
	public Block moltenFluidBlock;
	
	public boolean isAlloy;

	private int _harvestLevel;
	private int _xpLow;
	private int _xpHigh;
	private OreDrops _drops;
	private OreSources _source;
	private float _smeltXP;
	private Aspect _secondaryAspect;
	private int _tradeToAmount;
	private int _tradeFromAmount;
	private double _uuCost;
	
	public boolean enabled;
	
	private MetallicOres(Aspect secondaryAspect, boolean isAlloy) {
		this(0, secondaryAspect, isAlloy, 0.0D);
	}
	
	private MetallicOres(int harvestLevel, Aspect secondaryAspect, double uuCost) {
		this(harvestLevel, secondaryAspect, false, uuCost);
	}
		
	private MetallicOres(int harvestLevel, Aspect secondaryAspect, boolean isAlloy, double uuCost) {
		this(harvestLevel, secondaryAspect, isAlloy, 0, 0, uuCost);
	}
	
	private MetallicOres(int harvestLevel, Aspect secondaryAspect, boolean isAlloy, int tradeToAmount, int tradeFromAmount, double uuCost) {
		this.oreName = "ore" + this.toString();
		this.netherOreName = "oreNether" + this.toString();
		this.blockName = "block" + this.toString();

		this.ingotName = "ingot" + this.toString();
		this.nuggetName = "nugget" + this.toString();
		this.dustName = "dust" + this.toString();
		this.tinyDustName = "dustTiny" + this.toString(); 
		this.crushedOreName = "crushed" + this.toString();
		this.purifiedCrushedOreName = "crushedPurified" + this.toString(); 
		this.clusterName = "cluster" + this.toString();
		this.clumpName = "clump" + this.toString();
		this.dirtyDustName = "dustDirty" + this.toString();
		this.shardName = "shard" + this.toString();
		this.crystalName = "crystal" + this.toString();
		this.slurryName = "slurry" + this.toString();
		this.cleanSlurryName = "slurryClean" + this.toString();
		this.moltenFluidName = this.toString().toLowerCase() + ".molten";
		this.moltenFluidBlockName = "fluid.molten." + this.toString().toLowerCase();
		this.bucketName = "bucketMolten" + this.toString();
		
		this.enabled = true;

		this._harvestLevel = harvestLevel;
		this._xpLow = 0;
		this._xpHigh = 0;
		this._source = OreSources.DEFAULT;
		this.isAlloy = isAlloy;
		this._drops = OreDrops.ORE;
		this._secondaryAspect = secondaryAspect;
		this._uuCost = uuCost;
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
		if (!this.isVanilla() && !this.isAlloy) {
			this.ore = new OreItemStack(new BlockOre(this.getDefaultConfig()));
		}
		else if (this == Gold) {
			this.ore = new OreItemStack(net.minecraft.init.Blocks.gold_ore);
		}
		else if (this == Iron) {
			this.ore = new OreItemStack(net.minecraft.init.Blocks.iron_ore);
		}
		
		// Register Nether Ore
		if (!this.isAlloy)	{
			this.netherOre = new OreItemStack(new BlockOre(this.getDefaultConfigNether(), true));
		}
		
		// Register Storage Block
		if (!this.isVanilla()) {
			this.block = new OreItemStack(new BlockCore(Material.iron, this.blockName)
				.setCreativeTab(CreativeTabs.tabBlock)
				.setHardness(5.0F)
				.setResistance(10.0F)
				.setStepSound(Block.soundTypeMetal));
			OreDictionary.registerOre(this.blockName, this.block.source);
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
		if (!this.isAlloy) {
			this.crushedOre = new OreItemStack(new ItemCore(this.crushedOreName).setCreativeTab(CreativeTabs.tabMaterials));
			this.purifiedCrushedOre = new OreItemStack(new ItemCore(this.purifiedCrushedOreName).setCreativeTab(CreativeTabs.tabMaterials));
		}
		
		// register native clusters
		if (!this.isAlloy) {
			this.cluster = new OreItemStack(new ItemCore(this.clusterName).setCreativeTab(CreativeTabs.tabMaterials));
		}
		
		// register clumps & dirty dusts
		if (!this.isAlloy) {
			this.clump = new OreItemStack(new ItemCore(this.clumpName).setCreativeTab(CreativeTabs.tabMaterials));
			this.dirtyDust = new OreItemStack(new ItemCore(this.dirtyDustName).setCreativeTab(CreativeTabs.tabMaterials));
			this.shard = new OreItemStack(new ItemCore(this.shardName).setCreativeTab(CreativeTabs.tabMaterials));
			this.crystal = new OreItemStack(new ItemCore(this.crystalName).setCreativeTab(CreativeTabs.tabMaterials));
		}
		
		// register bucket of molten ore
		this.bucket = new OreItemStack(new OreBucket(this.bucketName, this.moltenFluidBlock).setCreativeTab(CreativeTabs.tabMisc));
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
			if (!this.isAlloy) {
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
		
		// add ingot Grinder recipes
		RecipeManager.addGrinderRecipe(this.ingot.newStack(), this.dust.newStack());
		
		// add ingot pulverizer (TE) recipes
		if (Helpers.ThermalExpansion.isLoaded()) {
			NBTTagCompound metadata = new NBTTagCompound();
			metadata.setInteger("energy", 2400);
			Helpers.ThermalExpansion.registerRecipe(RecipeType.Grinder, this.ingot.newStack(), metadata, this.dust.newStack());
		}
		
		if (!this.isAlloy) {
			// add nether ore -> ore smelting recipe
			RecipeManager.addSmelting(this.netherOre.newStack(), this.ore.newStack(2), 0.0F);
			
			// add crushed ore smelting recipe
			RecipeManager.addSmelting(this.crushedOre.newStack(), this.ingot.newStack(), this._smeltXP);
			
			// add purified crushed ore smelting recipe
			RecipeManager.addSmelting(this.purifiedCrushedOre.newStack(), this.ingot.newStack(), this._smeltXP);
			
			// add ore grinder recipe
			RecipeManager.addGrinderRecipe(this.ore.newStack(), this.crushedOre.newStack(2));
			
			// Register Thermal Expansion Pulverizer recipe
			if (Helpers.ThermalExpansion.isLoaded()) {
				NBTTagCompound metadata = new NBTTagCompound();
				metadata.setInteger("energy", 4000);
				Helpers.ThermalExpansion.registerRecipe(RecipeType.Grinder, this.ore.newStack(), metadata, this.crushedOre.newStack(2));
			}
			
			if (Helpers.Mekanism.isLoaded() && !mekanismOres.contains(this)) {
				// register slurrys
				this.cleanSlurry = (OreGas)GasRegistry.register(new OreGas(this.cleanSlurryName, this.cleanSlurryName).setVisible(false));
				this.slurry = (OreGas)GasRegistry.register(new OreGas(this.slurryName, this.slurryName).setCleanGas(this.cleanSlurry).setVisible(false));
				
				NBTTagCompound metadata = new NBTTagCompound();
				metadata.setString("gas", "hydrogenChloride");
				
				for (ItemStack dictionaryOre : OreDictionary.getOres(this.oreName)) {
					OreItemStack oreStack = new OreItemStack(dictionaryOre);
					
					// register Mekanism ore->dust enrichment chamber recipe
					Helpers.Mekanism.registerRecipe(RecipeType.EnrichmentChamber, oreStack.newStack(), this.dust.newStack(2));
					
					// register Mekanism ore->clump purification chamber recipe
					Helpers.Mekanism.registerRecipe(RecipeType.PurificationChamber, oreStack.newStack(), this.clump.newStack(3));
					
					// register Mekanism ore->shard chemical injector recipe
					Helpers.Mekanism.registerRecipe(RecipeType.ChemicalInjector, oreStack.newStack(), metadata, this.shard.newStack(4));
					
					// register Mekanism chemical dissolution chamber recipe
					Helpers.Mekanism.registerGasRecipe(RecipeType.ChemicalDissolver, oreStack.newStack(), null, new GasStack(this.slurry, 1000));
					
				}
				
				// register Mekanism dirty dust to dust
				Helpers.Mekanism.registerRecipe(RecipeType.EnrichmentChamber, this.dirtyDust.newStack(), this.dust.newStack());
				
				// register Mekanism shard to clump recipe
				Helpers.Mekanism.registerRecipe(RecipeType.PurificationChamber, this.shard.newStack(), this.clump.newStack());
				
				// register Mekanism crystal to shard recipe
				Helpers.Mekanism.registerRecipe(RecipeType.ChemicalInjector, this.crystal.newStack(), metadata, this.shard.newStack());
				
				// register Mekanism clump to dirty dust recipe
				Helpers.Mekanism.registerRecipe(RecipeType.Crusher, this.clump.newStack(), this.dirtyDust.newStack());
				
				// register Mekanism chemical washer recipe
				Helpers.Mekanism.registerGasRecipe(RecipeType.ChemicalWasher, new GasStack(this.slurry, 1), null, new GasStack(this.cleanSlurry, 1));
				
				// register Mekanism chemical crystallizer recipe
				Helpers.Mekanism.registerGasRecipe(RecipeType.ChemicalCrystalizer, new GasStack(this.cleanSlurry, 200), null, this.crystal.newStack());
			}
			
			if (Helpers.IC2.isLoaded()) {
				// add ore washing recipe
				NBTTagCompound metadata = new NBTTagCompound();
				metadata.setInteger("amount", 1000);
				switch(this) {
				case Lead:
					Helpers.IC2.registerRecipe(RecipeType.OreWasher, this.crushedOre.newStack(), metadata, new ItemStack[] {
						this.purifiedCrushedOre.newStack(),
						DustOres.Sulfur.tinyDust.newStack(3),
						stoneDust.newStack()	});
					break;
				default:
					Helpers.IC2.registerRecipe(RecipeType.OreWasher, this.crushedOre.newStack(), metadata, new ItemStack[] {
						this.purifiedCrushedOre.newStack(),
						this.tinyDust.newStack(2),
						stoneDust.newStack()	});
				}
			}
		}
		
		// add centrifuge recipes
		if (Helpers.IC2.isLoaded()) {
			NBTTagCompound metadata = new NBTTagCompound();
			switch (this) {
			case Adamantine:
				this.registerIC2Centrifuge(4000, MetallicOres.Iron.tinyDust.newStack());
				break;
			case Aluminium:
				this.registerIC2Centrifuge(1000, MetallicOres.Iron.tinyDust.newStack());
				break;
			case Ardite:
				this.registerIC2Centrifuge(4000, Items.dustTinyTungsten.item.newStack());
				break;
			case Brass:
				metadata.setInteger("minHeat", 2000);
				Helpers.IC2.registerRecipe(RecipeType.Centrifuge, this.dust.newStack(4), metadata, new ItemStack[] {
					MetallicOres.Copper.dust.newStack(3),
					MetallicOres.Zinc.dust.newStack()		});
				break;
			case Bronze:
				metadata.setInteger("minHeat", 2000);
				Helpers.IC2.registerRecipe(RecipeType.Centrifuge, this.dust.newStack(4), metadata, new ItemStack[] {
					MetallicOres.Copper.dust.newStack(3),
					MetallicOres.Tin.dust.newStack()		});
				break;
			case Cobalt:
				this.registerIC2Centrifuge(3000, MetallicOres.Copper.tinyDust.newStack());
				break;
			case Coldiron:
				this.registerIC2Centrifuge(1500, MetallicOres.Iron.tinyDust.newStack());
				break;
			case Electrum:
				metadata.setInteger("minHeat", 2500);
				Helpers.IC2.registerRecipe(RecipeType.Centrifuge, this.dust.newStack(2), metadata, new ItemStack[] {
					MetallicOres.Gold.dust.newStack(),
					MetallicOres.Silver.dust.newStack()		});
				break;
			case Manganese:
				this.registerIC2Centrifuge(1500, MetallicOres.Iron.tinyDust.newStack());
				break;
			case Mithral:
				this.registerIC2Centrifuge(2500, MetallicOres.Silver.tinyDust.newStack());
				break;
			case Nickel:
				this.registerIC2Centrifuge(2000, DustOres.Sulfur.tinyDust.newStack());
				break;
			case Osmium:
				this.registerIC2Centrifuge(4000, DustOres.Sulfur.tinyDust.newStack());
				break;
			case Platinum:
				this.registerIC2Centrifuge(3000, DustOres.Sulfur.tinyDust.newStack());
				break;
			case Yellorium:
				this.registerIC2Centrifuge(3000, MetallicOres.Iron.tinyDust.newStack());
				break;
			case Zinc:
				this.registerIC2Centrifuge(500, DustOres.Sulfur.tinyDust.newStack());
				break;
			default:
			}

		}
		
		// add uuMatter Scanner costs
		if (this._uuCost != 0.0D) {
			RecipeManager.addUUMatterRecipe(this.ore.source, this._uuCost);
		}
		
		// add dust smelting recipe
		RecipeManager.addSmelting(this.dust.newStack(), this.ingot.newStack(), this._smeltXP);
		
		// add tiny dust -> dust recipe
		RecipeManager.addShapedRecipe(this.dust.newStack(), "ttt", "ttt", "ttt", 't', this.tinyDustName);
		
		// add dust -> tiny dust recipe
		RecipeManager.addShapelessRecipe(this.tinyDust.newStack(9), this.dust.newStack());
		
		if (!this.isAlloy) {
			// add thaumcraft ore -> native cluster crucible recipes
			switch (this) {
			case Iron:
			case Gold:
			case Tin:
			case Copper:
			case Lead:
			case Silver:
				break;
			default:
				RecipeManager.addCrucibleRecipe("PUREIRON", this.cluster.newStack(), this.oreName, new AspectList().add(Aspect.METAL, 1).add(Aspect.ORDER, 1));
				break;
			}
			
			// add native cluster smelting
			RecipeManager.addSmelting(this.cluster.newStack(), this.ingot.newStack(2), this._smeltXP);
		}
	}
	
	private void registerIC2Centrifuge(int minHeat, ItemStack secondary) {
		OreItemStack stoneDust = new OreItemStack(new ItemStack(Helpers.IC2.getItem("itemDust"), 1, 9));
		NBTTagCompound metadata = new NBTTagCompound();
		metadata.setInteger("minHeat", minHeat);
		Helpers.IC2.registerRecipe(RecipeType.Centrifuge, this.crushedOre.newStack(), metadata, new ItemStack[] {
			this.dust.newStack(),
			secondary,
			stoneDust.newStack()
		});
		Helpers.IC2.registerRecipe(RecipeType.Centrifuge, this.purifiedCrushedOre.newStack(), metadata, new ItemStack[] {
			this.dust.newStack(),
			secondary
		});
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

	@Override
	public void registerFluids() {
		if (FluidRegistry.isFluidRegistered(this.moltenFluidName)) {
			this.moltenFluid = FluidRegistry.getFluid(this.moltenFluidName);
			this.moltenFluidBlock = this.moltenFluid.getBlock();
			if (this.moltenFluidBlock == null) {
				this.moltenFluidBlock = new BlockOreFluid(this.moltenFluid, this.moltenFluidBlockName);
			}
			
		}
		else {
			this.moltenFluid = new OreFluid(this.moltenFluidName);
			this.moltenFluidBlock = new BlockOreFluid(this.moltenFluid, this.moltenFluidBlockName).setCreativeTab(CreativeTabs.tabMaterials);
		}
		if (FluidContainerRegistry.fillFluidContainer(new FluidStack(this.moltenFluid, 1000), new ItemStack(net.minecraft.init.Items.bucket)) == null) {
			FluidContainerRegistry.registerFluidContainer(
					new FluidStack(this.moltenFluid, 1000), 
					this.bucket.newStack(), 
					new ItemStack(net.minecraft.init.Items.bucket));
		}

	}

	@Override
	public int getLaserWeight() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	static {
		mekanismOres.add(Iron);
		mekanismOres.add(Gold);
		mekanismOres.add(Osmium);
		mekanismOres.add(Copper);
		mekanismOres.add(Tin);
		mekanismOres.add(Lead);
	}
	
}

