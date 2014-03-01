package tw.oresplus.core;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tw.oresplus.OresPlus;

public class OreLog {
	public static Logger log;
	
	private static boolean configured = false;
	
	public static void init(){
		log = LogManager.getLogger(OresPlus.MOD_ID);
		configured = true;
		//log.setParent(LogManager.getLogger("FML"));
	}
	
	public static void log(Level level, String message){
		if (!configured){
			init();
		}
		log.log(level, message, new Object[0]);
	}
	
	public static void info(String message) {
		log(Level.INFO, message);
	}

}
