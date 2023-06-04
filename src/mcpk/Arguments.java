package mcpk;

import java.util.HashMap;

@SuppressWarnings("serial")
public class Arguments extends HashMap<String,Object>{

	public Arguments() {
		this.put("duration", 1);
		this.put("airborne", false);
		this.put("forward", 0);
		this.put("strafing", false);
		this.put("sprinting", false);
		this.put("sneaking", false);
		this.put("jumping", false);
		this.put("facing", 0f);
		this.put("facing_raw", 0f);
		
		this.put("blocking", 0.0);
		this.put("swiftness", 0);
		this.put("slowness", 0);
		this.put("slip", 0.6D);
		this.put("soulsand", false);
		
	}

}