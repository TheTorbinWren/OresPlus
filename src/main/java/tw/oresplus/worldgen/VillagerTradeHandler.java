package tw.oresplus.worldgen;

import java.util.Random;

import tw.oresplus.items.ItemManager;
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
			recipeList.add(this.getMerchantRecipeFor(ItemManager.armorAdamantineBoots, random));
			recipeList.add(this.getMerchantRecipeFor(ItemManager.armorAdamantineChestplate, random));
			recipeList.add(this.getMerchantRecipeFor(ItemManager.armorAdamantineHelmet, random));
			recipeList.add(this.getMerchantRecipeFor(ItemManager.armorAdamantineLeggings, random));
			recipeList.add(this.getMerchantRecipeFor(ItemManager.toolAdamantineAxe, random));
			recipeList.add(this.getMerchantRecipeFor(ItemManager.toolAdamantineHoe, random));
			recipeList.add(this.getMerchantRecipeFor(ItemManager.toolAdamantinePickaxe, random));
			recipeList.add(this.getMerchantRecipeFor(ItemManager.toolAdamantineSpade, random));
			recipeList.add(this.getMerchantRecipeFor(ItemManager.toolAdamantineSword, random));
			recipeList.add(this.getMerchantRecipeFor(ItemManager.armorColdironBoots, random));
			recipeList.add(this.getMerchantRecipeFor(ItemManager.armorColdironChestplate, random));
			recipeList.add(this.getMerchantRecipeFor(ItemManager.armorColdironHelmet, random));
			recipeList.add(this.getMerchantRecipeFor(ItemManager.armorColdironLeggings, random));
			recipeList.add(this.getMerchantRecipeFor(ItemManager.toolColdironAxe, random));
			recipeList.add(this.getMerchantRecipeFor(ItemManager.toolColdironHoe, random));
			recipeList.add(this.getMerchantRecipeFor(ItemManager.toolColdironPickaxe, random));
			recipeList.add(this.getMerchantRecipeFor(ItemManager.toolColdironSpade, random));
			recipeList.add(this.getMerchantRecipeFor(ItemManager.toolColdironSword, random));
			recipeList.add(this.getMerchantRecipeFor(ItemManager.armorMithralBoots, random));
			recipeList.add(this.getMerchantRecipeFor(ItemManager.armorMithralChestplate, random));
			recipeList.add(this.getMerchantRecipeFor(ItemManager.armorMithralHelmet, random));
			recipeList.add(this.getMerchantRecipeFor(ItemManager.armorMithralLeggings, random));
			recipeList.add(this.getMerchantRecipeFor(ItemManager.toolMithralAxe, random));
			recipeList.add(this.getMerchantRecipeFor(ItemManager.toolMithralHoe, random));
			recipeList.add(this.getMerchantRecipeFor(ItemManager.toolMithralPickaxe, random));
			recipeList.add(this.getMerchantRecipeFor(ItemManager.toolMithralSpade, random));
			recipeList.add(this.getMerchantRecipeFor(ItemManager.toolMithralSword, random));
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
			if (material == ItemManager.armorAdamantine) {
				materialCost = 5;
			} 
			else if (material == ItemManager.armorColdiron) {
				materialCost = 1;
			}
			else if (material == ItemManager.armorMithral) {
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
			if (material == ItemManager.toolAdamantine) {
				materialCost = 5;
			}
			else if (material == ItemManager.toolColdiron) {
				materialCost = 1;
			}
			else if (material == ItemManager.toolMithral) {
				materialCost = 3;
			}
		}
		
		if (itemTypeCost == 0 || materialCost == 0)
			return null;
		
		int tradeAmount = itemTypeCost + materialCost;
		return new MerchantRecipe(GemstoneOres.Emerald.gem.newStack(tradeAmount + random.nextInt(tradeAmount) / 3), item.newStack());
	}
}
