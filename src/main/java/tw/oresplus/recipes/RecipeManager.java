package tw.oresplus.recipes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.GameRegistry.UniqueIdentifier;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import tw.oresplus.OresPlus;
import tw.oresplus.api.Ores;
import tw.oresplus.blocks.Blocks;
import tw.oresplus.core.OreDictHelper;
import tw.oresplus.core.config.ConfigMain;
import tw.oresplus.core.helpers.BCHelper;
import tw.oresplus.core.helpers.Helpers;
import tw.oresplus.items.Items;
import tw.oresplus.items.OreItems;
import tw.oresplus.ores.AdvancedOres;
import tw.oresplus.ores.DustOres;
import tw.oresplus.ores.GemstoneOres;
import tw.oresplus.ores.GeneralOres;
import tw.oresplus.ores.MetallicOres;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class RecipeManager {
	private static ICraftingHandler neiHandler;
	private static ICraftingHandler tmiHandler;
	
	private static ArrayList<ItemStack> maceratorBlackList = new ArrayList();
	private static ArrayList<ItemStack> grinderBlackList = new ArrayList();
	private static ArrayList<ItemStack> rockCrusherBlackList = new ArrayList();
	private static ArrayList<ItemStack> pulverizerBlackList = new ArrayList();
	
	public static void init() {
		Ores.grinderRecipes = new GrinderRecipeManager();
		Ores.crackerRecipes = new CrackerRecipeManager();
		
		neiHandler = new NeiHandler();
		tmiHandler = new TmiHandler();
	}
	
	public static void initRecipes() {
		maceratorBlackList.add(new ItemStack(net.minecraft.init.Items.coal, 1, 0));
		maceratorBlackList.add(new ItemStack(net.minecraft.init.Items.coal, 1, 1));

		OreItemStack coal = new OreItemStack(net.minecraft.init.Items.coal, 0);
		OreItemStack charcoal = new OreItemStack(net.minecraft.init.Items.coal, 1);
	    OreItemStack fertalizer = new OreItemStack(Helpers.Forestry.getItem("fertilizerCompound"));
		OreItemStack flint = new OreItemStack(net.minecraft.init.Items.flint);
		OreItemStack furnace = new OreItemStack(net.minecraft.init.Blocks.furnace);
		OreItemStack glass = new OreItemStack(net.minecraft.init.Blocks.glass);
		OreItemStack glassPane = new OreItemStack(net.minecraft.init.Blocks.glass_pane);
		OreItemStack gunpowder = new OreItemStack(net.minecraft.init.Items.gunpowder);
		OreItemStack oreCoal = new OreItemStack(net.minecraft.init.Blocks.coal_ore);
		OreItemStack oreLapis = new OreItemStack(net.minecraft.init.Blocks.lapis_ore);
	    OreItemStack stick = new OreItemStack(net.minecraft.init.Items.stick);
	    OreItemStack stone = new OreItemStack(net.minecraft.init.Blocks.stone);
		OreItemStack tank = new OreItemStack(Helpers.BuildCraft.getBlock("tankBlock"));
		
		if (OresPlus.config.enableMachines) {
			// machine casing
			addShapedRecipe(OreItems.machineCasing.item.newStack(), "sss", "sis", "sss", 's', stone.newStack(), 'i', MetallicOres.Iron.ingot.newStack());
			// cracker recipe
			if (Helpers.BuildCraft.isLoaded() && tank.source.getItem() != null) {
				addShapedRecipe(Blocks.cracker.newStack(), "t", "c", "F", 't', tank.newStack(), 'c', OreItems.machineCasing.item.newStack(), 'F', furnace.newStack());
			}
			else
				addShapedRecipe(Blocks.cracker.newStack(), "ggg", "gcg", "gFg", 'g', glass.newStack(), 'c', OreItems.machineCasing.item.newStack(), 'F', furnace.newStack());
			
			// grinder recipe
			addShapedRecipe(Blocks.grinder.newStack(), "fff", "fcf", "fFf", 'f', flint.newStack(), 'c', OreItems.machineCasing.item.newStack(), 'F', furnace.newStack());
			
			// centrifuge recipe
			// addShapedRecipe(Blocks.centrifuge.newStack(), "ipi" "ici", "iFi", 'i', MetallicOres.Iron.ingot.newStack(), 'p', glassPane.newStack(), 'c', Items.machineCasing.newStack(), 'F', furnace.newStack());
		}
		
		//gunpowder recipe
		addShapelessRecipe(gunpowder.newStack(4), "dustSaltpeter", "dustSaltpeter", "dustCoal", "dustSulfur");
		
		// misc grinder recipes
		addGrinderRecipe(charcoal.newStack(), OreItems.dustCharcoal.item.newStack());
		addGrinderRecipe(coal.newStack(), OreItems.dustCoal.item.newStack());
		addGrinderRecipe(OreItems.gemUranium.item.newStack(), OreItems.crushedUranium.item.newStack());
		addGrinderRecipe(GeneralOres.Uranium.ore.newStack(), OreItems.crushedUranium.item.newStack(2));
		
		// alloy recipes
		int amount = 4;
		if (OresPlus.difficultAlloys) {
			amount = 2;
		}
		addShapelessRecipe(MetallicOres.Brass.dust.newStack(amount), "dustCopper", "dustCopper", "dustCopper", "dustZinc");
		addShapelessRecipe(MetallicOres.Bronze.dust.newStack(amount), "dustCopper", "dustCopper", "dustCopper", "dustTin");
		addShapelessRecipe(MetallicOres.Electrum.dust.newStack(2), "dustGold", "dustSilver");
		
		// misc nether ore smelting recipes
		addSmelting(GeneralOres.NetherCoal.ore.newStack(), oreCoal.newStack(2), 0.0F);
		addSmelting(GeneralOres.NetherLapis.ore.newStack(), oreLapis.newStack(2), 0.0F);
		addSmelting(GeneralOres.NetherUranium.ore.newStack(), GeneralOres.Uranium.ore.newStack(2), 0.0F);
		
	    if (Helpers.Forestry.isLoaded()) {
	        addShapelessRecipe(fertalizer.newStack(8), new Object[] { DustOres.Saltpeter.dustName, DustOres.Phosphorite.dustName, DustOres.Potash.dustName });
	        addShapelessRecipe(fertalizer.newStack(12), new Object[] { DustOres.Saltpeter.dustName, DustOres.Saltpeter.dustName, DustOres.Phosphorite.dustName, DustOres.Potash.dustName });
	        addShapelessRecipe(fertalizer.newStack(12), new Object[] { DustOres.Saltpeter.dustName, DustOres.Phosphorite.dustName, DustOres.Phosphorite.dustName, DustOres.Potash.dustName });
	        addShapelessRecipe(fertalizer.newStack(12), new Object[] { DustOres.Saltpeter.dustName, DustOres.Phosphorite.dustName, DustOres.Potash.dustName, DustOres.Potash.dustName });
	        addShapelessRecipe(fertalizer.newStack(12), new Object[] { DustOres.Saltpeter.dustName, DustOres.Phosphorite.dustName, DustOres.Potash.dustName, OreItems.dustMagnesium.item.newStack() });
	        addShapelessRecipe(fertalizer.newStack(16), new Object[] { DustOres.Saltpeter.dustName, DustOres.Saltpeter.dustName, DustOres.Phosphorite.dustName, DustOres.Potash.dustName, OreItems.dustMagnesium.item.newStack() });
	        addShapelessRecipe(fertalizer.newStack(16), new Object[] { DustOres.Saltpeter.dustName, DustOres.Phosphorite.dustName, DustOres.Phosphorite.dustName, DustOres.Potash.dustName, OreItems.dustMagnesium.item.newStack() });
	        addShapelessRecipe(fertalizer.newStack(16), new Object[] { DustOres.Saltpeter.dustName, DustOres.Phosphorite.dustName, DustOres.Potash.dustName, DustOres.Potash.dustName, OreItems.dustMagnesium.item.newStack() });
	      }

	    addShapedRecipe(Items.armorAdamantineHelmet.newStack(), "aaa", "a a", 'a', MetallicOres.Adamantine.ingotName );
	    addShapedRecipe(Items.armorAdamantineChestplate.newStack(), "a a", "aaa", "aaa", 'a', MetallicOres.Adamantine.ingotName );
	    addShapedRecipe(Items.armorAdamantineLeggings.newStack(), "aaa", "a a", "a a", 'a', MetallicOres.Adamantine.ingotName );
	    addShapedRecipe(Items.armorAdamantineBoots.newStack(), "a a", "a a", 'a', MetallicOres.Adamantine.ingotName );
	    addShapedRecipe(Items.armorColdironHelmet.newStack(), "ccc", "c c", 'c', MetallicOres.Coldiron.ingotName );
	    addShapedRecipe(Items.armorColdironChestplate.newStack(), "c c", "ccc", "ccc", 'c', MetallicOres.Coldiron.ingotName );
	    addShapedRecipe(Items.armorColdironLeggings.newStack(), "ccc", "c c", "c c", 'c', MetallicOres.Coldiron.ingotName );
	    addShapedRecipe(Items.armorColdironBoots.newStack(), "c c", "c c", 'c', MetallicOres.Coldiron.ingotName );
	    addShapedRecipe(Items.armorMithralHelmet.newStack(), "mmm", "m m", 'm', MetallicOres.Mithral.ingotName );
	    addShapedRecipe(Items.armorMithralChestplate.newStack(), "m m", "mmm", "mmm", 'm', MetallicOres.Mithral.ingotName );
	    addShapedRecipe(Items.armorMithralLeggings.newStack(), "mmm", "m m", "m m", 'm', MetallicOres.Mithral.ingotName );
	    addShapedRecipe(Items.armorMithralBoots.newStack(), "m m", "m m", 'm', MetallicOres.Mithral.ingotName );

	    addShapedRecipe(Items.toolAdamantineAxe.newStack(), " aa", " sa", " s ", 'a', MetallicOres.Adamantine.ingotName, 's', stick.newStack() );
	    addShapedRecipe(Items.toolAdamantineHoe.newStack(), " aa", " s ", " s ", 'a', MetallicOres.Adamantine.ingotName, 's', stick.newStack() );
	    addShapedRecipe(Items.toolAdamantinePickaxe.newStack(), "aaa", " s ", " s ", 'a', MetallicOres.Adamantine.ingotName, 's', stick.newStack() );
	    addShapedRecipe(Items.toolAdamantineSpade.newStack(), "a", "s", "s", 'a', MetallicOres.Adamantine.ingotName, 's', stick.newStack());
	    addShapedRecipe(Items.toolAdamantineSword.newStack(), "a", "a", "s", 'a', MetallicOres.Adamantine.ingotName, 's', stick.newStack() );
	    addShapedRecipe(Items.toolColdironAxe.newStack(), " cc", " sc", " s ", 'c', MetallicOres.Coldiron.ingotName, 's', stick.newStack() );
	    addShapedRecipe(Items.toolColdironHoe.newStack(), " cc", " s ", " s ", 'c', MetallicOres.Coldiron.ingotName, 's', stick.newStack() );
	    addShapedRecipe(Items.toolColdironPickaxe.newStack(), "ccc", " s ", " s ", 'c', MetallicOres.Coldiron.ingotName, 's', stick.newStack() );
	    addShapedRecipe(Items.toolColdironSpade.newStack(), "c", "s", "s", 'c', MetallicOres.Coldiron.ingotName, 's', stick.newStack());
	    addShapedRecipe(Items.toolColdironSword.newStack(), "c", "c", "s", 'c', MetallicOres.Coldiron.ingotName, 's', stick.newStack() );
	    addShapedRecipe(Items.toolMithralAxe.newStack(), " mm", " sm", " s ", 'm', MetallicOres.Mithral.ingotName, 's', stick.newStack() );
	    addShapedRecipe(Items.toolMithralHoe.newStack(), " mm", " s ", " s ", 'm', MetallicOres.Mithral.ingotName, 's', stick.newStack() );
	    addShapedRecipe(Items.toolMithralPickaxe.newStack(), "mmm", " s ", " s ", 'm', MetallicOres.Mithral.ingotName, 's', stick.newStack() );
	    addShapedRecipe(Items.toolMithralSpade.newStack(), "m", "s", "s", 'm', MetallicOres.Mithral.ingotName, 's', stick.newStack());
	    addShapedRecipe(Items.toolMithralSword.newStack(), "m", "m", "s", 'm', MetallicOres.Mithral.ingotName, 's', stick.newStack() );
	    
	    // Misc IC2 recipes
	    NBTTagCompound metadata = new NBTTagCompound();
	    metadata.setInteger("minHeat", 2000);
	    Helpers.IC2.registerRecipe(RecipeType.Centrifuge, OreItems.dustPyrite.item.newStack(3), metadata, MetallicOres.Iron.dust.newStack(), DustOres.Sulfur.dust.newStack(2));
	    Helpers.IC2.registerRecipe(RecipeType.Centrifuge, OreItems.dustSphalerite.item.newStack(2), metadata, MetallicOres.Zinc.dust.newStack(), DustOres.Sulfur.dust.newStack());
	    Helpers.IC2.registerRecipe(RecipeType.Centrifuge, OreItems.dustCinnabar.item.newStack(2), metadata, OreItems.itemMercury.item.newStack(), DustOres.Sulfur.dust.newStack());
	    
		for (MetallicOres ore : MetallicOres.values()) {
			ore.registerRecipes();
		}
		
		for (GemstoneOres ore : GemstoneOres.values()) {
			ore.registerRecipes();
		}
		
		for (DustOres ore : DustOres.values()) {
			ore.registerRecipes();
		}
		
		for (AdvancedOres ore : AdvancedOres.values()) {
			ore.registerRecipes();
		}
		
		for (GeneralOres ore : GeneralOres.values()) {
			ore.registerRecipes();
		}
		
		for (OreItems item : OreItems.values()) {
			item.registerRecipes();
		}
		
		if (OresPlus.iridiumPlateRecipe && Helpers.IC2.isLoaded()) {
			OreItemStack iridiumPlate = new OreItemStack(Helpers.IC2.getItem("itemPartIridium"));
			OreItemStack alloyPlate = new OreItemStack(Helpers.IC2.getItem("itemPartAlloy"));
			addShapedRecipe(iridiumPlate.newStack(), "ipi", "pdp", "ipi", 'i', OreItems.gemIridium.toString(), 'p', alloyPlate.newStack(), 'd', GemstoneOres.Diamond.gem.newStack());
		}
		
		if (Helpers.BigReactors.isLoaded()) {
			OreItemStack graphiteBar = new OreItemStack(Helpers.BigReactors.getItem("BRIngot"), 2);
			OreItemStack fuelRod = new OreItemStack(Helpers.BigReactors.getItem("YelloriumFuelRod"));
			addShapedRecipe(fuelRod.newStack(), "igi", "iyi", "igi", 'i', MetallicOres.Iron.ingot.newStack(), 'g', graphiteBar.newStack(), 'y', MetallicOres.Yellorium.ingot.newStack());
		}
	}

	public static void addShapelessRecipe(ItemStack output, Object... params) {
		CraftingManager.getInstance().getRecipeList().add(new ShapelessOreRecipe(output, params));
	}

	public static void addSmelting(ItemStack input, ItemStack output, Float xp) {
		GameRegistry.addSmelting(input, output, xp);	
	}
	
	public static void addShapedRecipe (ItemStack output, Object... params) {
		CraftingManager.getInstance().getRecipeList().add(new ShapedOreRecipe(output, params));
	}

	public static void addGrinderRecipe(ItemStack input, ItemStack output) {
		// Register Grinder Recipe
		Ores.grinderRecipes.add(input, output);
		OresPlus.log.debug("Registered OresPlus:Grinder recipe for " + input.getUnlocalizedName());
		// Register IC2 Macerator Recipe
		if (Helpers.IC2.isLoaded() && !isBlackListed(maceratorBlackList, input)) {
			Helpers.IC2.registerRecipe(RecipeType.Macerator, input, output);
			OresPlus.log.debug("Registered IC2:Macerator recipe for " + input.getUnlocalizedName());
		}
		// Register AE2 Grinder Recipe
		if (Helpers.AppliedEnergistics.isLoaded() && !isBlackListed(grinderBlackList, input)) {
			NBTTagCompound metadata = new NBTTagCompound();
			metadata.setInteger("cranks", 8);
			Helpers.AppliedEnergistics.registerRecipe(RecipeType.Grinder, input, metadata, output);
			OresPlus.log.debug("Registered AE2:Grinder recipe for " + input.getUnlocalizedName());
		}
		// Register Railcraft Rock Crusher Recipe
		if (Helpers.RailCraft.isLoaded() && !isBlackListed(rockCrusherBlackList , input)) {
			Helpers.RailCraft.registerRecipe(RecipeType.RockCrusher, input, output);
			OresPlus.log.debug("Registered RailCraft:RockCrusher recipe for " + input.getUnlocalizedName());
		}
	}
	
	public static void addUUMatterRecipe(ItemStack source, double cost) {
		if (Helpers.IC2.isLoaded() && source != null && cost != 0.0) {
			NBTTagCompound metadata = new NBTTagCompound();
			metadata.setDouble("cost", cost);
			Helpers.IC2.registerRecipe(RecipeType.Scanner, source, metadata, new ItemStack[] {});
		}
	}

	private static boolean isBlackListed(
			ArrayList<ItemStack> blacklist, ItemStack input) {
		for (ItemStack item : blacklist) {
			if (item.isItemEqual(input)) {
				return true;
			}
		}
		return false;
	}

	public static void replaceRecipeResults() {
		// replace smelting results
		OresPlus.log.info("Replacing smelting results");
		Collection smeltingResultsList = FurnaceRecipes.smelting().getSmeltingList().values();
		for (Object result : smeltingResultsList.toArray()) {
			if (result instanceof ItemStack) {
				Item item = ((ItemStack)result).getItem();
				UniqueIdentifier itemUid = GameRegistry.findUniqueIdentifierFor(item);
				if (itemUid.modId != "minecraft" && itemUid.modId != OresPlus.MOD_ID) {
					OresPlus.log.info("Recipe Result " + itemUid.modId + ":" + itemUid.name);
					ItemStack newItem = new ItemStack(Ores.manager.getOreItem(itemUid.name), ((ItemStack)result).stackSize);
					if (newItem != null) {
						((ItemStack)result).func_150996_a(newItem.getItem());
						net.minecraft.init.Items.apple.setDamage(newItem, net.minecraft.init.Items.apple.getDamage(((ItemStack)result)));
					}
				}
			}
		}
	}
	
	public static void hideItem(OreItemStack item) {
		neiHandler.hideItem(item.source);
		tmiHandler.hideItem(item.source);
	}
	
	public static void addCrucibleRecipe(String key, ItemStack result, String catalyst, AspectList tags) {
		if (Helpers.ThaumCraft.isLoaded()) {
			ThaumcraftApi.addCrucibleRecipe(key, result, catalyst, tags);
		}
	}
	
}
