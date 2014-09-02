package tw.oresplus.core.helpers;

import java.util.Random;

import tw.oresplus.OresPlus;
import tw.oresplus.recipes.RecipeType;
import mekanism.api.AdvancedInput;
import mekanism.api.RecipeHelper;
import mekanism.api.gas.GasRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class MekanismHelper extends OresHelper {

	MekanismHelper() {
		super("Mekanism");
	}

	@Override
	public void preInit() {
		if (!this.isLoaded()) {
			OresPlus.log.info("Mekanism not found, integration helper disabled");
			return;
		}
		OresPlus.log.info("Mekanism found, integration helper initialized");
	}

	@Override
	public void generate(World world, Random rand, int chunkX, int chunkZ) { }

	@Override
	public void registerRecipe(RecipeType recipeType, ItemStack input, NBTTagCompound metadata, ItemStack... outputs) {
		switch (recipeType) {
		case EnrichmentChamber:
			if (this.isLoaded() && input != null && outputs[0] != null) {
				RecipeHelper.addEnrichmentChamberRecipe(input, outputs[0]);
			}
			break;
		case PurificationChamber:
			if (this.isLoaded() && input != null && outputs[0] != null) {
				RecipeHelper.addPurificationChamberRecipe(input, outputs[0]);
			}
			break;
		case ChemicalInjector:
			if (this.isLoaded() && input != null && outputs[0] != null) {
				RecipeHelper.addChemicalInjectionChamberRecipe(new AdvancedInput(input, GasRegistry.getGas(metadata.getString("gas"))), outputs[0]);
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void init() { }

	@Override
	public void postInit() { }

}
