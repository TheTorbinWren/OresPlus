package tw.oresplus.items;

import java.util.ArrayList;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import tw.oresplus.OresPlus;
import tw.oresplus.api.Ores;
import tw.oresplus.core.OreLog;

public class ItemCore extends Item{
	protected int itemId;
	
	public ItemCore(String itemName) {
		super();
		setUnlocalizedName(itemName);
		setTextureName(OresPlus.MOD_ID + ":" + itemName);
		Ores.manager.registerOreItem(itemName, this);
		GameRegistry.registerItem(this, itemName);
		OreDictionary.registerOre(itemName, this);
	}

	public ItemCore addOreDictionaryName(String name) {
		OreDictionary.registerOre(name, this);
		return this;
	}    
}
