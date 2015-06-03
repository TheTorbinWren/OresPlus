package tw.oresplus.core.helpers;

import java.util.Random;

import cpw.mods.fml.common.event.FMLInterModComms;
import tw.oresplus.OresPlus;
import tw.oresplus.recipes.RecipeType;
import mekanism.api.recipe.RecipeHelper;
import mekanism.api.gas.Gas;
import mekanism.api.gas.GasRegistry;
import mekanism.api.gas.GasStack;
import mekanism.api.gas.OreGas;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

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
				//RecipeHelper.addEnrichmentChamberRecipe(input, outputs[0]);
				this.sendSimpleImcRecipe("EnrichmentChamberRecipe", input, outputs[0]);
			}
			break;
		case PurificationChamber:
			if (this.isLoaded() && input != null && outputs[0] != null) {
				//RecipeHelper.addPurificationChamberRecipe(input, outputs[0]);
				Gas gasType = GasRegistry.getGas("oxygen");
				this.sendSimpleImcRecipe("PurificationChamberRecipe", input, gasType, outputs[0]);
			}
			break;
		case ChemicalInjector:
			if (this.isLoaded() && input != null && outputs[0] != null) {
				//RecipeHelper.addChemicalInjectionChamberRecipe(input, metadata.getString("gas"), outputs[0]);
				Gas gasType = GasRegistry.getGas("hydrogenChloride");
				this.sendSimpleImcRecipe("ChemicalInjectionChamberRecipe", input, gasType, outputs[0]);
			}
			break;
		case Crusher:
			if (this.isLoaded() && input != null && outputs[0] != null) {
				//RecipeHelper.addCrusherRecipe(input, outputs[0]);
				this.sendSimpleImcRecipe("CrusherRecipe", input, outputs[0]);
			}
			break;
		default:
			break;
		}
	}
	
	private void sendSimpleImcRecipe(String recipeKey, ItemStack input, Gas gasType, ItemStack output) {
		NBTTagCompound msg = new NBTTagCompound();
		
		NBTTagCompound nbtInput = new NBTTagCompound();
		input.writeToNBT(nbtInput);
		msg.setTag("input", nbtInput);
		
		if (gasType != null) {
			NBTTagCompound nbtGasType = new NBTTagCompound();
			gasType.write(nbtGasType);
			msg.setTag("gasType", nbtGasType);
		}
		
		NBTTagCompound nbtOutput = new NBTTagCompound();
		output.writeToNBT(nbtOutput);
		msg.setTag("output", nbtOutput);
		
		FMLInterModComms.sendMessage(this._modID, recipeKey, msg);
	}

	private void sendSimpleImcRecipe(String recipeKey, ItemStack input, ItemStack output) {
		sendSimpleImcRecipe(recipeKey, input, null, output);
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
		
		String recipeKey = "";
		NBTTagCompound msg = new NBTTagCompound();
		NBTTagCompound nbtInput = new NBTTagCompound();
		
		switch (recipeType) {
		case ChemicalCrystalizer:
			if (input != null & input instanceof GasStack && output != null && output instanceof ItemStack) {
				//RecipeHelper.addChemicalCrystallizerRecipe((GasStack)input, (ItemStack)output);
				recipeKey = "ChemicalCrystallizerRecipe";
				
				((GasStack)input).write(nbtInput);
				
				NBTTagCompound nbtOutput = new NBTTagCompound();
				((ItemStack)output).writeToNBT(nbtOutput);
				msg.setTag("output", nbtOutput);
			}
			break;
		case ChemicalDissolver:
			if (input != null && input instanceof ItemStack && output != null && output instanceof GasStack) {
				//RecipeHelper.addChemicalDissolutionChamberRecipe((ItemStack)input, (GasStack)output);
				recipeKey = "ChemicalDissolutionChamberRecipe";
				
				((ItemStack)input).writeToNBT(nbtInput);
				
				NBTTagCompound nbtOutput = new NBTTagCompound();
				((GasStack)output).write(nbtOutput);
				msg.setTag("output", nbtOutput);
			}
			break;
		case ChemicalWasher:
			if (input != null && input instanceof GasStack && output != null && output instanceof GasStack) {
				//RecipeHelper.addChemicalWasherRecipe((GasStack)input, (GasStack)output);
				recipeKey = "ChemicalWasherRecipe";
				
				((GasStack)input).write(nbtInput);
				
				NBTTagCompound nbtOutput = new NBTTagCompound();
				((GasStack)output).write(nbtOutput);
				msg.setTag("output", nbtOutput);
			}
			break;
		case ElectrolyticSeperator:
			if (input != null && input instanceof FluidStack 
					&& output != null && output instanceof GasStack 
					&& secondaryOutput != null && secondaryOutput instanceof GasStack) {
				
				recipeKey = "ElectrolyticSeparatorRecipe";
			
				((FluidStack)input).writeToNBT(nbtInput);
				
				NBTTagCompound leftOutput = new NBTTagCompound();
				((GasStack)output).write(leftOutput);
				msg.setTag("leftOutput", leftOutput);
				
				NBTTagCompound rightOutput = new NBTTagCompound();
				((GasStack)secondaryOutput).write(rightOutput);
				msg.setTag("rightOutput", rightOutput);
			}
			break;
		default:
			break;
		}
		
		if (recipeKey != "") {
			msg.setTag("input", nbtInput);
			FMLInterModComms.sendMessage(this._modID, recipeKey, msg);
		}
	}

}
