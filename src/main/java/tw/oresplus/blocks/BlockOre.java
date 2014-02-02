package tw.oresplus.blocks;

import java.util.ArrayList;
import java.util.Collection;

import tw.oresplus.enums.OreDrops;
import tw.oresplus.init.Blocks;
import tw.oresplus.init.Items;
import tw.oresplus.init.OreConfig;
import tw.oresplus.init.OreGenConfig;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class BlockOre extends BlockCore {
	private OreDrops drops;
	
	public BlockOre(OreConfig ore) {
		super (Material.field_151576_e, ore.name);
		this.drops = ore.drops;
		this.func_149647_a(CreativeTabs.tabBlock);
		OreDictionary.registerOre(ore.name, this);
		this.func_149711_c(3.0F);
		this.setHarvestLevel("pickaxe", ore.harvestLevel);
	}

	@Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
    {
		ArrayList rList = new ArrayList();
		switch (this.drops)
		{
		case AMETHYST:
			rList.add(new ItemStack(Items.get("gemAmethyst"), this.fortuneHelper(world, 1, fortune)));
			break;
		case APATITE:
			rList.add(new ItemStack(Items.get("gemApatite"), this.fortuneHelper(world, 1 + world.rand.nextInt(4), fortune)));
			break;
		case BITUMEN:
			rList.add(new ItemStack(Items.get("itemBitumen"), this.fortuneHelper(world, 2 + world.rand.nextInt(4), fortune)));
			break;
		case CINNABAR: // cinnabar = 1 cinnabar & 1/4 redstone (gregstech)
			rList.add(new ItemStack(Items.get("dustCinnabar"), this.fortuneHelper(world, 1, fortune)));
			if (world.rand.nextInt(4) == 0)
			{
				rList.add(new ItemStack(net.minecraft.init.Items.redstone, this.fortuneHelper(world, 1, fortune)));
			}
			break;
		case GREENSAPPHIRE:
			rList.add(new ItemStack(Items.get("gemGreenSapphire"), this.fortuneHelper(world, 1, fortune)));
			break;
		case IRIDIUM: // iridium = 1 (half fortune) iridium (gregstech)
			rList.add(new ItemStack(Items.get("gemIridium"), this.fortuneHelper(world, 1, fortune)));
			break;
		case MAGNESIUM: // magnesium = 2-5 magnesium (metallurgy)
			rList.add(new ItemStack(Items.get("dustMagnesium"), this.fortuneHelper(world, 2 + world.rand.nextInt(4), fortune)));
			break;
		case NIKOLITE: // nikolite = 4-5 nikolite (rp2)
			rList.add(new ItemStack(Items.get("dustNikolite"), this.fortuneHelper(world, 4 + world.rand.nextInt(2), fortune)));
			break;
		case PHOSPHORITE: // phosphorite = 2-5 phosphorite (metallurgy)
			rList.add(new ItemStack(Items.get("dustPhosphorite"), this.fortuneHelper(world, 2 + world.rand.nextInt(4), fortune)));
			break;
		case POTASH: // potash = 2-5 potash (metallurgy)
			rList.add(new ItemStack(Items.get("dustPotash"), this.fortuneHelper(world, 2 + world.rand.nextInt(4), fortune)));
			break;
		case PYRITE: // pyrite = 1 pyrite (gregstech)
			rList.add(new ItemStack(Items.get("dustPyrite"), this.fortuneHelper(world, 1, fortune)));
			break;
		case RUBY: // ruby = 1 ruby (twstuff/rp2) | 1 ruby & 1/32 red garnet (gregstech)
			rList.add(new ItemStack(Items.get("gemRuby"), this.fortuneHelper(world, 1, fortune)));
			if (world.rand.nextInt(32) == 0)
			{
				rList.add(new ItemStack(Items.get("gemRedGarnet"), this.fortuneHelper(world, 1, fortune)));
			}
			break;
		case SALTPETER: // saltpeter = 1-2 saltpeter (railcraft) | 2-5 saltpeter (metallurgy)
			rList.add(new ItemStack(Items.get("dustSaltpeter"), this.fortuneHelper(world, 1 + world.rand.nextInt(2), fortune)));
			break;
		case SAPPHIRE: // sapphire = 1 sapphire (twstuff/rp2) | 1 sapphiree & 1/64 green sapphire
			rList.add(new ItemStack(Items.get("gemSapphire"), this.fortuneHelper(world, 1, fortune)));
			if (world.rand.nextInt(64) == 0)
			{
				rList.add(new ItemStack(Items.get("gemGreenSapphire"), this.fortuneHelper(world, 1, fortune)));
			}
			break;
		case SPHALERITE: // sphalerite = 1 sphalerite & 1/4 zinc & 1/32 yellow garnet
			rList.add(new ItemStack(Items.get("dustSphalerite"), this.fortuneHelper(world, 1, fortune)));
			if (world.rand.nextInt(4) == 0)
			{
				rList.add(new ItemStack(Items.get("dustZinc"), this.fortuneHelper(world, 1, fortune)));
			}
			if (world.rand.nextInt(32) == 0)
			{
				rList.add(new ItemStack(Items.get("gemYellowGarnet"), this.fortuneHelper(world, 1, fortune)));
			}
			break;
		case SULFUR: // sulfur = 2-5 sulfur (railcraft/metallurgy)
			rList.add(new ItemStack(Items.get("dustSulfur"), this.fortuneHelper(world, 2 + world.rand.nextInt(4), fortune)));
			break;
		case TOPAZ: // topaz = 1 topaz (twstuff)
			rList.add(new ItemStack(Items.get("gemTopaz"), this.fortuneHelper(world, 1, fortune)));
			break;
		case URANIUM: // uranium = 1 uranium (ic2)
			rList.add(new ItemStack(Items.get("gemUranium"), this.fortuneHelper(world, 1, fortune)));
			break;
		case SODALITE: // sodalite = 6 sodalite & 1/4 aluminium (gregstech)
			rList.add(new ItemStack(Items.get("dustSodalite"), this.fortuneHelper(world, 6, fortune)));
			if (world.rand.nextInt(4) == 0)
			{
				rList.add(new ItemStack(Items.get("dustAluminium"), this.fortuneHelper(world, 1, fortune)));
			}
			break;
		default:
			rList.add(new ItemStack(func_149650_a(metadata, world.rand, fortune), 1));
		}
		return rList;
    }
	
	private int fortuneHelper(World world, int stackSize, int fortune) {
		switch (this.drops){
		case AMETHYST:
		case CINNABAR:
		case GREENSAPPHIRE:
		case IRIDIUM:
		case PYRITE:
		case RUBY:
		case SAPPHIRE:
		case SPHALERITE:
		case TOPAZ:
		case URANIUM:
			switch (fortune) {
			case 1:
				if (world.rand.nextInt(3) == 0)
					stackSize = stackSize * 2;
				break;
			case 2:
				switch (world.rand.nextInt(4)) {
				case 0:
					stackSize = stackSize * 2;
					break;
				case 1: 
					stackSize = stackSize * 3;
					break;
				}
				break;
			case 3:
				switch (world.rand.nextInt(5)) {
				case 0:
					stackSize = stackSize * 2;
					break;
				case 1:
					stackSize = stackSize * 3;
					break;
				case 2:
					stackSize = stackSize * 4;
					break;
				}
				break;
			}
			break;
		case APATITE:
		case BITUMEN:
		case MAGNESIUM:
		case NIKOLITE:
		case PHOSPHORITE:
		case POTASH:
		case SALTPETER:
		case SULFUR:
		case SODALITE:
			if (fortune >= 1)
				stackSize++;
			if (fortune >= 2)
				stackSize++;
			if (fortune == 3)
				stackSize++;
			break;
		default:
		}
		return stackSize;
	}

}
