package tw.oresplus.blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import tw.oresplus.OresPlus;
import tw.oresplus.api.OresPlusAPI;
import tw.oresplus.core.OreLog;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemBlockWithMetadata;

public class BlockCore extends net.minecraft.block.Block {
	public BlockCore(Material material, String blockName, boolean isMeta) {
		super(material);
		this.setBlockName(blockName); // name
		this.setBlockTextureName(OresPlus.MOD_ID +":" + blockName); // texture
		OresPlusAPI.registerBlock(blockName, OresPlus.MOD_ID, this);
		if (isMeta)
			GameRegistry.registerBlock(this, ItemBlockWithMetadata.class, blockName);
		else
			GameRegistry.registerBlock(this, blockName);
	}

	public BlockCore(Material material, String blockName) {
		this(material, blockName, false);
	}
}
