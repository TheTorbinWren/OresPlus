package tw.oresplus.triggers;

import buildcraft.api.gates.ActionManager;

public class OresTrigger {
	public OresTrigger instance = new OresTrigger();
	
	public static Trigger hasWork;
	
	private OresTrigger() {
		ActionManager.registerTrigger(hasWork = new TriggerHasWork());
	}
}
