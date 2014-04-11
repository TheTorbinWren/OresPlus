package tw.oresplus.recipes;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.Loader;

public class NeiHandler implements ICraftingHandler {
	private boolean initialized = false;
	private Class api;
	private Object apiObject;
	private Method hideItemMethod;
	
	public NeiHandler() {
		if (!this.isLoaded())
			return;
		
		try {
			this.api = Class.forName("codechicken.nei.api");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			this.apiObject = this.api.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		if (this.api != null && this.apiObject != null) {
			for (Method method : this.api.getDeclaredMethods()) {
				if (method.toString().equals("hideItem"))
					this.hideItemMethod = method;
			}
		}
		
		this.initialized = true;
	}
	
	@Override
	public boolean isLoaded() {
		if (Loader.isModLoaded("NotEnoughItems"))
			return true;
		return false;
	}

	@Override
	public boolean hideItem(ItemStack item) {
		if (!this.initialized) 
			return false;
		
		try {
			this.hideItemMethod.invoke(apiObject, item);
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
