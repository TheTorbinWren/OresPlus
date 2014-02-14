package tw.oresplus.worldgen;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import tw.oresplus.OresPlus;
import tw.oresplus.enums.OreGenType;
import tw.oresplus.init.Blocks;
import tw.oresplus.init.OreGenConfig;
import net.minecraft.block.Block;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenOre {
	private OreGenConfig ore;
	boolean enabled = true;	// generation of ore enabled
	Block block;		// block to generate
	Block target;
	public boolean doRegen;
	public Collection<String> biomeList;

	public WorldGenOre(OreGenConfig genOre) {
		this.ore = genOre;
		this.block = (Block) Blocks.blockList.get(genOre.oreName);
		this.doRegen = genOre.doRegen;
		switch(genOre.dimension)
		{
		case -1:
			target = net.minecraft.init.Blocks.netherrack;
			WorldGenCore.oreGenNether.add(this);
			break;
		case 1:
			target = net.minecraft.init.Blocks.end_stone;
			WorldGenCore.oreGenEnd.add(this);
			break;
		default:
			target = net.minecraft.init.Blocks.stone;
			WorldGenCore.oreGenOverworld.add(this);
		}
		
		if (ore.oreName.equals("oreBauxite"))
			this.biomeList = WorldGenCore.biomeListBauxite;
		else if (ore.oreName.equals("oreCassiterite"))
			this.biomeList = WorldGenCore.biomeListCassiterite;
		else if (ore.oreName.equals("oreColdiron"))
			this.biomeList = WorldGenCore.biomeListColdiron;
		else if (ore.oreName.equals("oreMithral"))
			this.biomeList = WorldGenCore.biomeListMithral;
		else if (ore.oreName.equals("oreNickel"))
			this.biomeList = WorldGenCore.biomeListNickel;
		else if (ore.oreName.equals("oreRuby"))
			this.biomeList = WorldGenCore.biomeListRuby;
		else if (ore.oreName.equals("oreSapphire"))
			this.biomeList = WorldGenCore.biomeListSapphire;
		else if (ore.oreName.equals("oreSaltpeter"))
			this.biomeList = WorldGenCore.biomeListSaltpeter;
		else if (ore.oreName.equals("oreTetrahedrite"))
			this.biomeList = WorldGenCore.biomeListTetrahedrite;
		else
			this.biomeList = new ArrayList();
	}

	public boolean generate(World world, Random random, int chunkX, int chunkZ){
		int biomeX = chunkX * 16 + 8;
		int biomeZ = chunkZ * 16 + 8;
		String biomeName = world.getBiomeGenForCoords(biomeX, biomeZ).biomeName;
		if (!ore.enabled)
			return false;
		if (this.biomeList.isEmpty() || this.biomeList.contains(biomeName)) {
			OresPlus.log.info("Generating " + ore.oreName + " in biome " + biomeName + " @" + chunkX + "," + chunkZ);
			if (this.biomeList.isEmpty())
				OresPlus.log.info("Biome List is empty");
			int actualVeins = this.ore.numVeins * this.ore.density / 100;
			for (int a=0; a<actualVeins; a++){
				int x = chunkX + random.nextInt(16);
				int y = random.nextInt(this.ore.maxY - this.ore.minY) + this.ore.minY;
				int z = chunkZ + random.nextInt(16);
				
				switch (ore.veinSize) {
				case 1:
					if (world.getBlock(x, y, z).isReplaceableOreGen(world, x, y, z, this.target)) {
						switch (ore.genType) {
						case NEAR_LAVA:
							if (isNearLava(world, x, y, z))
								world.setBlock(x, y, z, this.block, 0, 2);
							break;
						case UNDER_LAVA:
							if (world.getBlock(x, y + 1, z).equals(net.minecraft.init.Blocks.lava))
								world.setBlock(x, y, z, this.block, 0, 2);
							break;
						default:
							world.setBlock(x, y, z, this.block, 0, 2);
						}
					}
					break;
				default:
					float f = random.nextFloat() * (float)Math.PI;
					double d0 = (double)((float)(x + 8) + MathHelper.sin(f) * (float)this.ore.veinSize / 8.0F);
					double d1 = (double)((float)(x + 8) - MathHelper.sin(f) * (float)this.ore.veinSize / 8.0F);
					double d2 = (double)((float)(z + 8) + MathHelper.cos(f) * (float)this.ore.veinSize / 8.0F);
					double d3 = (double)((float)(z + 8) - MathHelper.cos(f) * (float)this.ore.veinSize / 8.0F);
					double d4 = (double)(y + random.nextInt(3) - 2);
					double d5 = (double)(y + random.nextInt(3) - 2);
					
					for (int l = 0; l <= this.ore.veinSize; ++l) {
						double d6 = d0 + (d1 - d0) * (double)l / (double)this.ore.veinSize;
						double d7 = d4 + (d5 - d4) * (double)l / (double)this.ore.veinSize;
						double d8 = d2 + (d3 - d2) * (double)l / (double)this.ore.veinSize;
						double d9 = random.nextDouble() * (double)this.ore.veinSize / 16.0D;
						double d10 = (double)(MathHelper.sin((float)l * (float)Math.PI / (float)this.ore.veinSize) + 1.0F) * d9 + 1.0D;
						double d11 = (double)(MathHelper.sin((float)l * (float)Math.PI / (float)this.ore.veinSize) + 1.0F) * d9 + 1.0D;
						int i1 = MathHelper.floor_double(d6 - d10 / 2.0D);
						int j1 = MathHelper.floor_double(d7 - d11 / 2.0D);
						int k1 = MathHelper.floor_double(d8 - d10 / 2.0D);
						int l1 = MathHelper.floor_double(d6 + d10 / 2.0D);
						int i2 = MathHelper.floor_double(d7 + d11 / 2.0D);
						int j2 = MathHelper.floor_double(d8 + d10 / 2.0D);
						
						for (int k2 = i1; k2 <= l1; ++k2) {
							double d12 = ((double)k2 + 0.5D - d6) / (d10 / 2.0D);
							if (d12 * d12 < 1.0D) {
								for (int l2 = j1; l2 <= i2; ++l2) {
									double d13 = ((double)l2 + 0.5D - d7) / (d11 / 2.0D);
									if (d12 * d12 + d13 * d13 < 1.0D) {
										for (int i3 = k1; i3 <= j2; ++i3) {
											double d14 = ((double)i3 + 0.5D - d8) / (d10 / 2.0D);
											if (d12 * d12 + d13 * d13 + d14 * d14 < 1.0F && world.getBlock(k2,  l2,  i3).isReplaceableOreGen(world, k2,  l2,  i3, this.target)) {
												switch(this.ore.genType) {
												case NEAR_LAVA:
													if (isNearLava(world, k2, l2, i3))
														world.setBlock(k2, l2, i3, this.block, 0, 2);
													break;
												case UNDER_LAVA:
													if (world.getBlock(k2, l2 + 1, i3).equals(net.minecraft.init.Blocks.lava))
														world.setBlock(k2, l2, i3, this.block, 0, 2);
													break;
												default:
													world.setBlock(k2, l2, i3, this.block, 0, 2);
												}
											}
										}
									}
								}
							}
						}
					}
				}
				
			}
			return true;
		}
		else
			return false;
	}

	public String getOreName() {
		return this.ore.name;
	}
		
	private boolean isNearLava(World world, int nearX, int nearY, int nearZ) {
		for (int k = 1; k <= 4; k++) {
			if (world.getBlock(nearX, nearY - k, nearZ).equals(net.minecraft.init.Blocks.lava))
				return true;
			if (!world.getBlock(nearX, nearY - k, nearZ).isAir(world, nearX, nearY - k, nearZ))
				return false;
		}
		return false;
	}
}
