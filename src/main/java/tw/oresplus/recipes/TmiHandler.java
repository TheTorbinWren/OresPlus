package tw.oresplus.recipes;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import tw.oresplus.OresPlus;
import cpw.mods.fml.common.Loader;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class TmiHandler implements ICraftingHandler {

	private Class tmi;
	private Object tmiObject;
	private Method hideItemMethod;
	private boolean isInitialized = false;

	public TmiHandler() {
		if (!this.isLoaded())
			return;
		
		try {
			tmi = Class.forName("TMIItemInfo");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		if (tmi != null) {
			try {
				tmiObject = tmi.newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		
		if (this.tmi != null && this.tmiObject != null) {
			for (Method method : this.tmi.getMethods()) {
				if (method.getName().equals("hideItem"))
					this.hideItemMethod = method;
			}
		}
		
		this.isInitialized  = true;
	}
	
	@Override
	public boolean isLoaded() {
		return Loader.isModLoaded("TooManyItems");
	}

	@Override
	public boolean hideItem(ItemStack item) {
		if (!this.isInitialized)
			return false;
		
		try {
			this.hideItemMethod.invoke(this.tmiObject, Item.getIdFromItem(item.getItem()));
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return true;
	}

}
