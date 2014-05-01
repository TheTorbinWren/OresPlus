package tw.oresplus.blocks;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import tw.oresplus.OresPlus;
import tw.oresplus.api.Ores;
import tw.oresplus.core.OreClass;
import tw.oresplus.core.helpers.AppEngHelper;
import tw.oresplus.core.helpers.Helpers;
import tw.oresplus.items.Items;
import tw.oresplus.items.OreItems;
import tw.oresplus.ores.DustOres;
import tw.oresplus.ores.GemstoneOres;
import tw.oresplus.ores.MetallicOres;
import tw.oresplus.ores.OreDrops;
import tw.oresplus.recipes.OreItemStack;
import tw.oresplus.worldgen.OreGenClass;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class BlockOre extends BlockCore {
	private OreDrops drops;
	private int xpDropLow;
	private int xpDropHigh;
	private boolean _isNether;
	
	public BlockOre(OreClass ore, boolean isNether) {
		super (Material.rock, ore.name);
		this.drops = ore.drops;
		this.setCreativeTab(CreativeTabs.tabBlock);
		OreDictionary.registerOre(ore.name, this);
		this.setHardness(3.0F);
		this.setHarvestLevel("pickaxe", ore.harvestLevel);
		this.xpDropLow = ore.xpDropLow;
		this.xpDropHigh = ore.xpDropHigh;
		this._isNether = isNether;
		Ores.manager.registerOre(ore.name, this);
	}
	
	public BlockOre(OreClass ore) {
		this(ore, false);
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
			rList.add(GemstoneOres.Amethyst.gem.newStack(this.fortuneHelper(world, 1, fortune)));
			break;
		case APATITE:
			rList.add(GemstoneOres.Apatite.gem.newStack(this.fortuneHelper(world, 1 + world.rand.nextInt(4), fortune)));
			break;
		case BITUMEN:
			rList.add(OreItems.itemBitumen.item.newStack(this.fortuneHelper(world, 2 + world.rand.nextInt(4), fortune)));
			break;
		case CERTUSQUARTZ:
			Item certusQuartz = Helpers.AppliedEnergistics.getItem("item.ItemMaterial");
			if (Helpers.AppliedEnergistics.isLoaded() && certusQuartz != null) {
				OreItemStack cqStack = world.rand.nextFloat() > 0.92F ? 
					new OreItemStack(new ItemStack(certusQuartz, 1, 1)) :
					new OreItemStack(new ItemStack(certusQuartz, 1, 0));
					rList.add(cqStack.newStack(fortuneHelper(world, 1 + world.rand.nextInt(2), fortune)));
			}
			else {
				rList.add(new ItemStack(this.getItemDropped(metadata, world.rand, fortune), 1));
			}
			break;
		case CINNABAR: // cinnabar = 1 cinnabar & 1/4 redstone (gregstech)
			rList.add(OreItems.dustCinnabar.item.newStack(this.fortuneHelper(world, 1, fortune)));
			if (world.rand.nextInt(4) == 0)	{
				rList.add(new ItemStack(net.minecraft.init.Items.redstone, this.fortuneHelper(world, 1, fortune)));
			}
			break;
		case GREENSAPPHIRE:
			rList.add(GemstoneOres.GreenSapphire.gem.newStack(this.fortuneHelper(world, 1, fortune)));
			break;
		case IRIDIUM: // iridium = 1 (half fortune) iridium (gregstech)
			rList.add(OreItems.gemIridium.item.newStack(this.fortuneHelper(world, 1, fortune)));
			break;
		case MAGNESIUM: // magnesium = 2-5 magnesium (metallurgy)
			rList.add(OreItems.dustMagnesium.item.newStack(this.fortuneHelper(world, 2 + world.rand.nextInt(4), fortune)));
			break;
		case NIKOLITE: // nikolite = 4-5 nikolite (rp2)
			rList.add(DustOres.Nikolite.dust.newStack(this.fortuneHelper(world, 4 + world.rand.nextInt(2), fortune)));
			break;
		case OLIVINE:
			rList.add(OreItems.gemOlivine.item.newStack(this.fortuneHelper(world, 1, fortune)));
			break;
		case PHOSPHORITE: // phosphorite = 2-5 phosphorite (metallurgy)
			rList.add(DustOres.Phosphorite.dust.newStack(this.fortuneHelper(world, 2 + world.rand.nextInt(4), fortune)));
			break;
		case POTASH: // potash = 2-5 potash (metallurgy)
			rList.add(DustOres.Potash.dust.newStack(this.fortuneHelper(world, 2 + world.rand.nextInt(4), fortune)));
			break;
		case PYRITE: // pyrite = 1 pyrite (gregstech)
			rList.add(OreItems.dustPyrite.item.newStack(this.fortuneHelper(world, 1, fortune)));
			break;
		case RUBY: // ruby = 1 ruby (twstuff/rp2) | 1 ruby & 1/32 red garnet (gregstech)
			rList.add(GemstoneOres.Ruby.gem.newStack(this.fortuneHelper(world, 1, fortune)));
			if (world.rand.nextInt(32) == 0) {
				rList.add(OreItems.gemRedGarnet.item.newStack(this.fortuneHelper(world, 1, fortune)));
			}
			break;
		case SALTPETER: // saltpeter = 1-2 saltpeter (railcraft) | 2-5 saltpeter (metallurgy)
			rList.add(DustOres.Saltpeter.dust.newStack(this.fortuneHelper(world, 1 + world.rand.nextInt(2), fortune)));
			break;
		case SAPPHIRE: // sapphire = 1 sapphire (twstuff/rp2) | 1 sapphiree & 1/64 green sapphire
			rList.add(GemstoneOres.Sapphire.gem.newStack(this.fortuneHelper(world, 1, fortune)));
			if (world.rand.nextInt(64) == 0) {
				rList.add(GemstoneOres.GreenSapphire.gem.newStack(this.fortuneHelper(world, 1, fortune)));
			}
			break;
		case SPHALERITE: // sphalerite = 1 sphalerite & 1/4 zinc & 1/32 yellow garnet
			rList.add(OreItems.dustSphalerite.item.newStack(this.fortuneHelper(world, 1, fortune)));
			if (world.rand.nextInt(4) == 0)	{
				rList.add(MetallicOres.Zinc.dust.newStack(this.fortuneHelper(world, 1, fortune)));
			}
			if (world.rand.nextInt(32) == 0) {
				rList.add(OreItems.gemYellowGarnet.item.newStack(this.fortuneHelper(world, 1, fortune)));
			}
			break;
		case SULFUR: // sulfur = 2-5 sulfur (railcraft/metallurgy)
			rList.add(DustOres.Sulfur.dust.newStack(this.fortuneHelper(world, 2 + world.rand.nextInt(4), fortune)));
			break;
		case TOPAZ: // topaz = 1 topaz (twstuff)
			rList.add(GemstoneOres.Topaz.gem.newStack(this.fortuneHelper(world, 1, fortune)));
			break;
		case SODALITE: // sodalite = 6 sodalite & 1/4 aluminium (gregstech)
			rList.add(OreItems.dustSodalite.item.newStack(this.fortuneHelper(world, 6, fortune)));
			if (world.rand.nextInt(4) == 0)	{
				rList.add(MetallicOres.Aluminium.dust.newStack(this.fortuneHelper(world, 1, fortune)));
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
		case CERTUSQUARTZ:
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

	@Override
    public void harvestBlock(World world, EntityPlayer player, int x, int y, int z, int meta) {
		super.harvestBlock(world, player, x, y, z, meta);
		if (OresPlus.angryPigmen & this._isNether)
			upsetThePigs(player, world, x, y, z);
	}

	private static void upsetThePigs(EntityPlayer player, World world, int x, int y, int z) {
		int range = 32;
		List entities = world.getEntitiesWithinAABB(net.minecraft.entity.monster.EntityPigZombie.class, AxisAlignedBB.getBoundingBox(x - range, y - range, z - range, x + range + 1, y  + range + 1, z + range + 1));
		
		for (Object entity : entities) {
			if (entity instanceof EntityPigZombie) {
				((EntityPigZombie)entity).attackEntityFrom(DamageSource.causePlayerDamage(player), 0);
			}
		}
	}
}
