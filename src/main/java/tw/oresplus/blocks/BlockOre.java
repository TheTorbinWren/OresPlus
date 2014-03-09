package tw.oresplus.blocks;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import tw.oresplus.OresPlus;
import tw.oresplus.api.OresPlusAPI;
import tw.oresplus.core.OreClass;
import tw.oresplus.core.OreGenClass;
import tw.oresplus.core.helpers.AppEngHelper;
import tw.oresplus.core.helpers.Helpers;
import tw.oresplus.enums.OreDrops;
import tw.oresplus.items.Items;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class BlockOre extends BlockCore {
	private OreDrops drops;
	private int xpDropLow;
	private int xpDropHigh;
	
	public BlockOre(OreClass ore) {
		super (Material.rock, ore.name);
		this.drops = ore.drops;
		this.setCreativeTab(CreativeTabs.tabBlock);
		OreDictionary.registerOre(ore.name, this);
		this.setHardness(3.0F);
		this.setHarvestLevel("pickaxe", ore.harvestLevel);
		this.xpDropLow = ore.xpDropLow;
		this.xpDropHigh = ore.xpDropHigh;
	}

	private Random rand = new Random();
    @Override
    public int getExpDrop(IBlockAccess var1, int var2, int var3) {
    	if (this.xpDropLow == 0 && this.xpDropHigh == 0)
    		return 0;
    	else
    		return this.xpDropLow + rand.nextInt(this.xpDropHigh - this.xpDropLow + 1);
    }
    
	@Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		ArrayList rList = new ArrayList();
		switch (this.drops)	{
		case AMETHYST:
			rList.add(new ItemStack(OresPlusAPI.getItem("gemAmethyst"), this.fortuneHelper(world, 1, fortune)));
			break;
		case APATITE:
			rList.add(new ItemStack(OresPlusAPI.getItem("gemApatite"), this.fortuneHelper(world, 1 + world.rand.nextInt(4), fortune)));
			break;
		case BITUMEN:
			rList.add(new ItemStack(OresPlusAPI.getItem("itemBitumen"), this.fortuneHelper(world, 2 + world.rand.nextInt(4), fortune)));
			break;
		case CERTUSQUARTZ:
			if (Helpers.AppliedEnergistics.isLoaded()) {
				// TODO: drop app eng drops here.
				rList.add(new ItemStack(this.getItemDropped(metadata, world.rand, fortune), 1));
			} 
			else {
				rList.add(new ItemStack(this.getItemDropped(metadata, world.rand, fortune), 1));
			}
			break;
		case CINNABAR: // cinnabar = 1 cinnabar & 1/4 redstone (gregstech)
			rList.add(new ItemStack(OresPlusAPI.getItem("dustCinnabar"), this.fortuneHelper(world, 1, fortune)));
			if (world.rand.nextInt(4) == 0)	{
				rList.add(new ItemStack(net.minecraft.init.Items.redstone, this.fortuneHelper(world, 1, fortune)));
			}
			break;
		case GREENSAPPHIRE:
			rList.add(new ItemStack(OresPlusAPI.getItem("gemGreenSapphire"), this.fortuneHelper(world, 1, fortune)));
			break;
		case IRIDIUM: // iridium = 1 (half fortune) iridium (gregstech)
			rList.add(new ItemStack(OresPlusAPI.getItem("gemIridium"), this.fortuneHelper(world, 1, fortune)));
			break;
		case MAGNESIUM: // magnesium = 2-5 magnesium (metallurgy)
			rList.add(new ItemStack(OresPlusAPI.getItem("dustMagnesium"), this.fortuneHelper(world, 2 + world.rand.nextInt(4), fortune)));
			break;
		case NIKOLITE: // nikolite = 4-5 nikolite (rp2)
			rList.add(new ItemStack(OresPlusAPI.getItem("dustNikolite"), this.fortuneHelper(world, 4 + world.rand.nextInt(2), fortune)));
			break;
		case OLIVINE:
			rList.add(new ItemStack(OresPlusAPI.getItem("gemOlivine"), this.fortuneHelper(world, 1, fortune)));
			break;
		case PHOSPHORITE: // phosphorite = 2-5 phosphorite (metallurgy)
			rList.add(new ItemStack(OresPlusAPI.getItem("dustPhosphorite"), this.fortuneHelper(world, 2 + world.rand.nextInt(4), fortune)));
			break;
		case POTASH: // potash = 2-5 potash (metallurgy)
			rList.add(new ItemStack(OresPlusAPI.getItem("dustPotash"), this.fortuneHelper(world, 2 + world.rand.nextInt(4), fortune)));
			break;
		case PYRITE: // pyrite = 1 pyrite (gregstech)
			rList.add(new ItemStack(OresPlusAPI.getItem("dustPyrite"), this.fortuneHelper(world, 1, fortune)));
			break;
		case RUBY: // ruby = 1 ruby (twstuff/rp2) | 1 ruby & 1/32 red garnet (gregstech)
			rList.add(new ItemStack(OresPlusAPI.getItem("gemRuby"), this.fortuneHelper(world, 1, fortune)));
			if (world.rand.nextInt(32) == 0) {
				rList.add(new ItemStack(OresPlusAPI.getItem("gemRedGarnet"), this.fortuneHelper(world, 1, fortune)));
			}
			break;
		case SALTPETER: // saltpeter = 1-2 saltpeter (railcraft) | 2-5 saltpeter (metallurgy)
			rList.add(new ItemStack(OresPlusAPI.getItem("dustSaltpeter"), this.fortuneHelper(world, 1 + world.rand.nextInt(2), fortune)));
			break;
		case SAPPHIRE: // sapphire = 1 sapphire (twstuff/rp2) | 1 sapphiree & 1/64 green sapphire
			rList.add(new ItemStack(OresPlusAPI.getItem("gemSapphire"), this.fortuneHelper(world, 1, fortune)));
			if (world.rand.nextInt(64) == 0) {
				rList.add(new ItemStack(OresPlusAPI.getItem("gemGreenSapphire"), this.fortuneHelper(world, 1, fortune)));
			}
			break;
		case SPHALERITE: // sphalerite = 1 sphalerite & 1/4 zinc & 1/32 yellow garnet
			rList.add(new ItemStack(OresPlusAPI.getItem("dustSphalerite"), this.fortuneHelper(world, 1, fortune)));
			if (world.rand.nextInt(4) == 0)	{
				rList.add(new ItemStack(OresPlusAPI.getItem("dustZinc"), this.fortuneHelper(world, 1, fortune)));
			}
			if (world.rand.nextInt(32) == 0) {
				rList.add(new ItemStack(OresPlusAPI.getItem("gemYellowGarnet"), this.fortuneHelper(world, 1, fortune)));
			}
			break;
		case SULFUR: // sulfur = 2-5 sulfur (railcraft/metallurgy)
			rList.add(new ItemStack(OresPlusAPI.getItem("dustSulfur"), this.fortuneHelper(world, 2 + world.rand.nextInt(4), fortune)));
			break;
		case TOPAZ: // topaz = 1 topaz (twstuff)
			rList.add(new ItemStack(OresPlusAPI.getItem("gemTopaz"), this.fortuneHelper(world, 1, fortune)));
			break;
		case URANIUM: // uranium = 1 uranium (ic2)
			rList.add(new ItemStack(OresPlusAPI.getItem("gemUranium"), this.fortuneHelper(world, 1, fortune)));
			break;
		case SODALITE: // sodalite = 6 sodalite & 1/4 aluminium (gregstech)
			rList.add(new ItemStack(OresPlusAPI.getItem("dustSodalite"), this.fortuneHelper(world, 6, fortune)));
			if (world.rand.nextInt(4) == 0)	{
				rList.add(new ItemStack(OresPlusAPI.getItem("dustAluminium"), this.fortuneHelper(world, 1, fortune)));
			}
			break;
		default:
			rList.add(new ItemStack(this.getItemDropped(metadata, world.rand, fortune), 1));
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
		case CERTUSQUARTZ:
		case MAGNESIUM:
		case NIKOLITE:
		case OLIVINE:
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
