package tw.oresplus.worldgen;

import java.util.Random;

import tw.oresplus.items.Items;
import tw.oresplus.items.OreArmor;
import tw.oresplus.items.OreAxe;
import tw.oresplus.items.OreHoe;
import tw.oresplus.items.OrePickaxe;
import tw.oresplus.items.OreSpade;
import tw.oresplus.items.OreSword;
import tw.oresplus.ores.GemstoneOres;
import tw.oresplus.ores.MetallicOres;
import tw.oresplus.recipes.OreItemStack;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemTool;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import cpw.mods.fml.common.registry.VillagerRegistry;
import cpw.mods.fml.common.registry.VillagerRegistry.IVillageTradeHandler;

public class VillagerTradeHandler implements IVillageTradeHandler {
	public static final int VILLAGER_FARMER = 0;
	public static final int VILLAGER_LIBRARIAN = 1;
	public static final int VILLAGER_PRIEST = 2;
	public static final int VILLAGER_BLACKSMITH = 3;
	public static final int VILLAGER_BUTCHER = 4;

	private int _villagerID;
	
	public VillagerTradeHandler(int villagerID) {
		this._villagerID = villagerID;
	}
	
	@Override
	public void manipulateTradesForVillager(EntityVillager villager,
			MerchantRecipeList recipeList, Random random) {
		switch (this._villagerID) {
		case VILLAGER_BLACKSMITH:
			recipeList.add(this.getMerchantRecipeFor(Items.armorAdamantineBoots, random));
			recipeList.add(this.getMerchantRecipeFor(Items.armorAdamantineChestplate, random));
			recipeList.add(this.getMerchantRecipeFor(Items.armorAdamantineHelmet, random));
			recipeList.add(this.getMerchantRecipeFor(Items.armorAdamantineLeggings, random));
			recipeList.add(this.getMerchantRecipeFor(Items.toolAdamantineAxe, random));
			recipeList.add(this.getMerchantRecipeFor(Items.toolAdamantineHoe, random));
			recipeList.add(this.getMerchantRecipeFor(Items.toolAdamantinePickaxe, random));
			recipeList.add(this.getMerchantRecipeFor(Items.toolAdamantineSpade, random));
			recipeList.add(this.getMerchantRecipeFor(Items.toolAdamantineSword, random));
			recipeList.add(this.getMerchantRecipeFor(Items.armorColdironBoots, random));
			recipeList.add(this.getMerchantRecipeFor(Items.armorColdironChestplate, random));
			recipeList.add(this.getMerchantRecipeFor(Items.armorColdironHelmet, random));
			recipeList.add(this.getMerchantRecipeFor(Items.armorColdironLeggings, random));
			recipeList.add(this.getMerchantRecipeFor(Items.toolColdironAxe, random));
			recipeList.add(this.getMerchantRecipeFor(Items.toolColdironHoe, random));
			recipeList.add(this.getMerchantRecipeFor(Items.toolColdironPickaxe, random));
			recipeList.add(this.getMerchantRecipeFor(Items.toolColdironSpade, random));
			recipeList.add(this.getMerchantRecipeFor(Items.toolColdironSword, random));
			recipeList.add(this.getMerchantRecipeFor(Items.armorMithralBoots, random));
			recipeList.add(this.getMerchantRecipeFor(Items.armorMithralChestplate, random));
			recipeList.add(this.getMerchantRecipeFor(Items.armorMithralHelmet, random));
			recipeList.add(this.getMerchantRecipeFor(Items.armorMithralLeggings, random));
			recipeList.add(this.getMerchantRecipeFor(Items.toolMithralAxe, random));
			recipeList.add(this.getMerchantRecipeFor(Items.toolMithralHoe, random));
			recipeList.add(this.getMerchantRecipeFor(Items.toolMithralPickaxe, random));
			recipeList.add(this.getMerchantRecipeFor(Items.toolMithralSpade, random));
			recipeList.add(this.getMerchantRecipeFor(Items.toolMithralSword, random));
			int tradeAmount;
			for (MetallicOres ore : MetallicOres.values()) {
				tradeAmount = ore.getTradeToAmount(random);
				if (tradeAmount > 0) {
					recipeList.add(new MerchantRecipe(ore.ingot.newStack(tradeAmount), GemstoneOres.Emerald.gem.newStack()));
				}
				tradeAmount = ore.getTradeFromAmount(random);
				if (tradeAmount > 0) {
					recipeList.add(new MerchantRecipe(GemstoneOres.Emerald.gem.newStack(), ore.ingot.newStack(tradeAmount)));
				}
			}
			break;
		default:
		}
	}
	
	private MerchantRecipe getMerchantRecipeFor(OreItemStack item, Random random) {
		int itemTypeCost = 0;
		int materialCost = 0;
		Item source = item.source.getItem();
		if (source instanceof OreArmor) {
			switch (((OreArmor)source).armorType) {
			case 1:
				materialCost = 5;
				break;
			case 2:
				materialCost = 4;
				break;
			default:
				materialCost = 3;
			}
			itemTypeCost = 5;
			ArmorMaterial material = ((OreArmor)source).getArmorMaterial();
			if (material == Items.armorAdamantine) {
				materialCost = 5;
			} 
			else if (material == Items.armorColdiron) {
				materialCost = 1;
			}
			else if (material == Items.armorMithral) {
				materialCost = 3;
			}
		}
		else if (source instanceof ItemTool) {
			if (source instanceof OreAxe) {
				itemTypeCost = 4;
			} else if (source instanceof OreHoe) {
				itemTypeCost = 3;
			} else if (source instanceof OrePickaxe) {
				itemTypeCost = 4;
			} else if (source instanceof OreSpade) {
				itemTypeCost = 4;
			} else if (source instanceof OreSword) {
				itemTypeCost = 5;
			}
			ToolMaterial material = ((ItemTool)source).func_150913_i();
			if (material == Items.toolAdamantine) {
				materialCost = 5;
			}
			else if (material == Items.toolColdiron) {
				materialCost = 1;
			}
			else if (material == Items.toolMithral) {
				materialCost = 3;
			}
		}
		
		if (itemTypeCost == 0 || materialCost == 0)
			return null;
		
		int tradeAmount = itemTypeCost + materialCost;
		return new MerchantRecipe(GemstoneOres.Emerald.gem.newStack(tradeAmount + random.nextInt(tradeAmount) / 3), item.newStack());
	}
}
