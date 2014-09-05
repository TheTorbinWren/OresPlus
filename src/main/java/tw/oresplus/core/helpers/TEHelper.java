package tw.oresplus.core.helpers;

import java.util.Random;

import tw.oresplus.OresPlus;
import tw.oresplus.recipes.RecipeType;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLInterModComms;

public class TEHelper extends OresHelper {
	public TEHelper() {
		super("ThermalExpansion");
	}

	@Override
	public void preInit() {
		if (!this.isLoaded()) {
			OresPlus.log.info("ThermalExpansion not found, integration helper disabled");
			return;
		}
		OresPlus.log.info("ThermalExpeansion found, integration helper initialized");
	}

	@Override
	public void generate(World world, Random rand, int chunkX, int chunkZ) { }

	@Override
	public void registerRecipe(RecipeType recipeType, ItemStack input, NBTTagCompound metadata, ItemStack... outputs) {
		switch (recipeType) {
		case Grinder:
			if (this.isLoaded() && input != null & outputs[0] != null) {
				int energy = metadata.getInteger("energy");
				if (energy == 0)
					energy = 4000;
				
				NBTTagCompound msg = new NBTTagCompound();
				
				msg.setInteger("energy", energy);
				msg.setTag("input", new NBTTagCompound());
				msg.setTag("primaryOutput", new NBTTagCompound());
				
				if (outputs.length > 1 && outputs[1] != null) {
					msg.setTag("secondaryOutput", new NBTTagCompound());
				}
				
				input.writeToNBT(msg.getCompoundTag("input"));
				outputs[0].writeToNBT(msg.getCompoundTag("primaryOutput"));
				
				if (outputs.length > 1 && outputs[1] != null) {
					outputs[1].writeToNBT(msg.getCompoundTag("secondaryOutput"));
				}
				FMLInterModComms.sendMessage("ThermalExpansion", "PulverizerRecipe", msg);
			}
			break;
		default:
			break;
		}
		
	}

	@Override
	public void init() {
		if (!this.isLoaded()) 
			return;
	}

	@Override
	public void postInit() {
		if (!this.isLoaded()) 
			return;
	}

	@Override
	public void registerGasRecipe(RecipeType recipeType, Object input,
			NBTTagCompound metadata, Object output, Object secondaryOutput) { }

}
