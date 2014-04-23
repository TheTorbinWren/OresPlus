package tw.oresplus.triggers;

import tw.oresplus.OresPlus;
import buildcraft.api.gates.ActionManager;

public class OresTrigger {
	public static Trigger hasWork;
	public static Trigger workDone;
	
	public static void registerTriggers() {
		ActionManager.registerTrigger(hasWork = new TriggerMachine(true));
		ActionManager.registerTrigger(workDone = new TriggerMachine(false));
	}
}
