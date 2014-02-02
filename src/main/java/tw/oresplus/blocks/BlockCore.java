package tw.oresplus.blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import tw.oresplus.OresPlus;
import tw.oresplus.core.OreLog;
import tw.oresplus.init.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;

public class BlockCore extends net.minecraft.block.Block {
	public BlockCore(Material material, String blockName) {
		super(material);
		this.func_149663_c(blockName); // name
		this.func_149658_d(OresPlus.MOD_ID +":" + blockName); // texture
		Blocks.blockList.put(blockName, this);
		GameRegistry.registerBlock(this, blockName);
	}
}
