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
import tw.oresplus.OresPlus;
import tw.oresplus.api.Ores;
import tw.oresplus.blocks.Blocks;
import tw.oresplus.core.OreDictHelper;
import tw.oresplus.core.helpers.BCHelper;
import tw.oresplus.core.helpers.Helpers;
import tw.oresplus.items.Items;
import tw.oresplus.items.OreItems;
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
	
	public static void init() {
		Ores.grinderRecipes = new GrinderRecipeManager();
		
		neiHandler = new NeiHandler();
		tmiHandler = new TmiHandler();
	}
	
	public static void initRecipes() {
		OreItemStack coal = new OreItemStack(net.minecraft.init.Items.coal, 0);
		OreItemStack charcoal = new OreItemStack(net.minecraft.init.Items.coal, 1);
	    OreItemStack fertalizer = new OreItemStack(Helpers.Forestry.getItem("fertilizerCompound"));
		OreItemStack flint = new OreItemStack(net.minecraft.init.Items.flint);
		OreItemStack furnace = new OreItemStack(net.minecraft.init.Blocks.furnace);
		OreItemStack glass = new OreItemStack(net.minecraft.init.Blocks.glass);
		OreItemStack gunpowder = new OreItemStack(net.minecraft.init.Items.gunpowder);
		OreItemStack oreCoal = new OreItemStack(net.minecraft.init.Blocks.coal_ore);
		OreItemStack oreLapis = new OreItemStack(net.minecraft.init.Blocks.lapis_ore);
	    OreItemStack stick = new OreItemStack(net.minecraft.init.Items.stick);
		OreItemStack tank = new OreItemStack(Helpers.BuildCraft.getBlock("tankBlock"));
		
		// cracker recipe
		if (Helpers.BuildCraft.isLoaded() && tank.source.getItem() != null) {
			addShapedRecipe(Blocks.cracker.newStack(), "t", "F", 't', tank.newStack(), 'F', furnace.newStack());
		}
		else
			addShapedRecipe(Blocks.cracker.newStack(), "ggg", "gFg", "ggg", 'g', glass.newStack(), 'F', furnace.newStack());
		
		// grinder recipe
		addShapedRecipe(Blocks.grinder.newStack(), "fff", "fFf", "fff", 'f', flint.newStack(), 'F', furnace.newStack());
		
		//gunpowder recipes
		addShapedRecipe(gunpowder.newStack(4), "sSs", "csc", "sSs", 's', "dustSaltpeter", 'c', "dustCoal", 'S', "dustSulfur");
		addShapedRecipe(gunpowder.newStack(4), "scs", "SsS", "scs", 's', "dustSaltpeter", 'c', "dustCoal", 'S', "dustSulfur");
		addShapedRecipe(gunpowder.newStack(4), "sSs", "csc", "sSs", 's', "dustSaltpeter", 'c', "dustCharcoal", 'S', "dustSulfur");
		addShapedRecipe(gunpowder.newStack(4), "scs", "SsS", "scs", 's', "dustSaltpeter", 'c', "dustCharcoal", 'S', "dustSulfur");
		
		// misc grinder recipes
		addGrinderRecipe(charcoal.newStack(), OreItems.dustCharcoal.item.newStack());
		addGrinderRecipe(coal.newStack(), OreItems.dustCoal.item.newStack());
		addGrinderRecipe(OreItems.gemUranium.item.newStack(), OreItems.crushedUranium.item.newStack());
		Helpers.IC2.registerRecipe("Macerator", OreItems.gemUranium.item.newStack(), OreItems.crushedUranium.item.newStack());
		addGrinderRecipe(GeneralOres.Uranium.ore.newStack(), OreItems.crushedUranium.item.newStack(2));
		NBTTagCompound cranks = new NBTTagCompound();
		cranks.setInteger("cranks", 8);
		Helpers.AppliedEnergistics.registerRecipe("grinder", GeneralOres.Uranium.ore.newStack(), cranks, OreItems.crushedUranium.item.newStack());
		
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
		
	    if (fertalizer.source != null) {
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

		for (MetallicOres ore : MetallicOres.values()) {
			ore.registerRecipes();
		}
		
		for (GemstoneOres ore : GemstoneOres.values()) {
			ore.registerRecipes();
		}
		
		for (DustOres ore : DustOres.values()) {
			ore.registerRecipes();
		}
		
		if (OresPlus.iridiumPlateRecipe && Helpers.IC2.isLoaded()) {
			OreItemStack iridiumPlate = new OreItemStack(Helpers.IC2.getItem("itemPartIridium"));
			OreItemStack alloyPlate = new OreItemStack(Helpers.IC2.getItem("itemPartAlloy"));
			addShapedRecipe(iridiumPlate.newStack(), "ipi", "pdp", "ipi", 'i', OreItems.gemIridium.toString(), 'p', alloyPlate.newStack(), 'd', GemstoneOres.Diamond.gem.newStack());
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
	
	@Deprecated
	public static ItemStack getGrinderRecipeResult(Object input) {
		return Ores.grinderRecipes.getResult(input);
	}
	
	@Deprecated
	public static boolean isIC2MaceratorRecipe(String oreName) {
		if (oreName.equals("Bronze") || oreName.equals("Copper") || oreName.equals("Lead") || oreName.equals("Silver") || oreName.equals("Tin"))
			return true;
		else
			return false;
	}

	public static void addGrinderRecipe(Object input, ItemStack output) {
		if (!(input instanceof String) && !(input instanceof ItemStack))
			return;
		Ores.grinderRecipes.add(input, output);
		
	}

	public static void replaceRecipeResults() {
		// replace smelting results
		OresPlus.log.info("Replacing smelting results");
		Collection smeltingResultsList = FurnaceRecipes.smelting().getSmeltingList().values();
		for (Object result : smeltingResultsList.toArray()) {
			if (result instanceof ItemStack) {
				Item item = ((ItemStack)result).getItem();
				UniqueIdentifier itemUid = GameRegistry.findUniqueIdentifierFor(item);
				OresPlus.log.info("Recipe Result " + itemUid.modId + ":" + itemUid.name);
			}
		}
		//ItemStack test = smeltingResultsList.
	}
	
	public static void hideItem(OreItemStack item) {
		neiHandler.hideItem(item.source);
		tmiHandler.hideItem(item.source);
	}
}
