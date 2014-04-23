package tw.oresplus.worldgen;

import java.util.Random;

import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;
import tw.oresplus.items.OreItems;
import tw.oresplus.ores.GemstoneOres;
import tw.oresplus.ores.MetallicOres;
import tw.oresplus.recipes.OreItemStack;

public class OreChestLoot {
	  private static String[] randomChests = { "dungeonChest", "mineshaftCorridor", "pyramidDesertyChest", "pyramidJungleChest", "strongholdCorridor", "strongholdCrossing", "strongholdLibrary" };

	  private static OreItemStack[] commonItems = { MetallicOres.Copper.ingot, MetallicOres.Lead.ingot, MetallicOres.Nickel.ingot, MetallicOres.Silver.ingot, MetallicOres.Tin.ingot, MetallicOres.Zinc.ingot };

	  private static OreItemStack[] uncommonItems = { MetallicOres.Brass.ingot, MetallicOres.Bronze.ingot, MetallicOres.Electrum.ingot, MetallicOres.Manganese.ingot, MetallicOres.Osmium.ingot, GemstoneOres.Amethyst.gem };

	  private static OreItemStack[] rareItems = { MetallicOres.Coldiron.ingot, MetallicOres.Platinum.ingot, GemstoneOres.GreenSapphire.gem, GemstoneOres.Ruby.gem, GemstoneOres.Sapphire.gem, GemstoneOres.Topaz.gem };

	  private static OreItemStack[] ultraRareItems = { MetallicOres.Adamantine.ingot, MetallicOres.Mithral.ingot, OreItems.gemRedGarnet.item, OreItems.gemYellowGarnet.item };

	  public static void registerChestLoot()
	  {
	    Random rand = new Random();

	    for (String chest : randomChests) {
	      for (OreItemStack item : commonItems) {
	        ChestGenHooks.addItem(chest, new WeightedRandomChestContent(item.newStack(2 + rand.nextInt(4)), 1, 3, 5));
	      }

	      for (OreItemStack item : uncommonItems) {
	        ChestGenHooks.addItem(chest, new WeightedRandomChestContent(item.newStack(1 + rand.nextInt(4)), 1, 2, 4));
	      }

	      for (OreItemStack item : rareItems) {
	        ChestGenHooks.addItem(chest, new WeightedRandomChestContent(item.newStack(), 1, 1, 2));
	      }

	      for (OreItemStack item : ultraRareItems)
	        ChestGenHooks.addItem(chest, new WeightedRandomChestContent(item.newStack(), 1, 1, 1));
	    }
	  }
}
