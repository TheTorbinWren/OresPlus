package tw.oresplus.core.helpers;

import java.util.Random;

import tw.oresplus.OresPlus;
import tw.oresplus.recipes.RecipeType;
import mekanism.api.AdvancedInput;
import mekanism.api.RecipeHelper;
import mekanism.api.gas.GasRegistry;
import mekanism.api.gas.GasStack;
import mekanism.api.gas.OreGas;
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
		case Crusher:
			if (this.isLoaded() && input != null && outputs[0] != null) {
				RecipeHelper.addCrusherRecipe(input, outputs[0]);
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

	@Override
	public void registerGasRecipe(RecipeType recipeType, Object input,
			NBTTagCompound metadata, Object output, Object secondaryOutput) {
		if (!this.isLoaded())
			return;
		switch (recipeType) {
		case ChemicalDissolver:
			if (input != null && input instanceof ItemStack && output != null && output instanceof GasStack) {
				RecipeHelper.addChemicalDissolutionChamberRecipe((ItemStack)input, (GasStack)output);
			}
			break;
		case ChemicalWasher:
			if (input != null && input instanceof GasStack && output != null && output instanceof GasStack) {
				RecipeHelper.addChemicalWasherRecipe((GasStack)input, (GasStack)output);
			}
			break;
		case ChemicalCrystalizer:
			if (input != null & input instanceof GasStack && output != null && output instanceof ItemStack) {
				RecipeHelper.addChemicalCrystallizerRecipe((GasStack)input, (ItemStack)output);
			}
			break;
		default:
			break;
		}
	}

}
