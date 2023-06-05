package mcpk;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import mcpk.utils.Arguments;
import mcpk.utils.MathHelper;

public class Player {
	
	//momentum calculation
	public int tick = 0;
	public double xOf = 0.0;
	public double zOf = 0.0;
	public double highestZ = 0, lowestZ = 0;
	public double highestX = 0, lowestX = 0;
	
	//coords
	public double x = 0.0;
	public double z = 0.0;
	public double vx = 0.0;
	public double vz = 0.0;
	
	public double slip = 0.0;
	public double last_slip = 0.0;
	public double multiplier = 0.0;
	
	public double inertia_threshold = 0.005D;
		
	//general move function
	
	public void move(Arguments args) {
		//defining
		int duration = (int) args.get("duration");
		boolean airborne = (boolean) args.get("airborne");
		int forward = (int) args.get("forward");
		boolean strafing = (boolean) args.get("strafing");
		boolean sprinting = (boolean) args.get("sprinting");
		boolean sneaking =  (boolean) args.get("sneaking");
		boolean jumping = (boolean) args.get("jumping");
		float facing = (float) args.get("facing");
		float facing_raw = (float) args.get("facing_raw");
		
		//modifiers
		boolean blocking = ((double) args.get("blocking") == 1.0) ? true : false;
		boolean soulsand = ((double) args.get("soulsand") == 1.0) ? true : false;
		
		//potions
		int swiftness = (int) (double) args.get("swiftness");
		int slowness = (int) (double) args.get("slowness");
		
		double effectMult = (1 + 0.2*swiftness) * (1 - 0.15*slowness);
		if (effectMult <= 0) effectMult = 0;
		
		//stuff starts here
		for (int i=1;i<=Math.abs(duration);i++) {
			//start of tick
			tick++;
			slip = 0F;
			multiplier = 1;
			
			//movement multipliers
			if (forward == 0) multiplier = 0;
			if (sprinting) multiplier *= 1.3;
			if (sneaking) multiplier *= 0.3;
			if (blocking) multiplier *= 0.2;
			
			//check for 45 strafing
			if (strafing) {
				if (sneaking) multiplier *= 0.98F * Math.sqrt(2);
			} else multiplier *= 0.98F;
			
			//slipperiness
			if (airborne) slip = 1F;
			else slip = (double) args.get("slip");
			if (last_slip == 0F) last_slip = slip;
			
			//movement
			this.z += this.vz;
			this.x += this.vx;
			this.zOf += this.vz;
			this.xOf += this.vx;
			
			//inertia threshold
			if (Math.abs(this.vz * last_slip * 0.91F) < inertia_threshold) this.vz = 0;
			if (Math.abs(this.vx * last_slip * 0.91F) < inertia_threshold) this.vx = 0;

			//speed calculations
			if (airborne) { //air velocity
				this.vz = (this.vz * last_slip * 0.91F) + (forward * 0.02F * multiplier * MathHelper.cos(facing));
				this.vx = (this.vx * last_slip * 0.91F) + (forward * 0.02F * multiplier * -MathHelper.sin(facing));
			}
			else if (jumping) { //jump velocity
				this.vz = (this.vz * last_slip * 0.91F) + (forward * 0.1F * multiplier * effectMult * (0.216F / (slip * slip * slip)) * MathHelper.cos(facing));
				this.vx = (this.vx * last_slip * 0.91F) + (forward * 0.1F * multiplier * effectMult * (0.216F / (slip * slip * slip)) * -MathHelper.sin(facing));
				if (sprinting) { 
					this.vz = this.vz + (forward * 0.2F * MathHelper.cos(facing_raw));
					this.vx = this.vx + (forward * 0.2F * -MathHelper.sin(facing_raw));
				}
			} else { //ground velocity
				this.vz = (this.vz * last_slip * 0.91F) + (forward * 0.1F * multiplier * effectMult * (0.216F / (slip * slip * slip)) * MathHelper.cos(facing));
				this.vx = (this.vx * last_slip * 0.91F) + (forward * 0.1F * multiplier * effectMult * (0.216F / (slip * slip * slip)) * -MathHelper.sin(facing));
			}
			
			last_slip = slip;
			this.updateMM(airborne || jumping);
		}
		
	}
	
	//invalid key presses
	
	@SuppressWarnings("serial")
	class InvalidKeypressException extends Exception {
		public InvalidKeypressException(String message) {
			super(message);
		}
	}
	
	@SuppressWarnings("serial")
	class DurationException extends Exception {
		public DurationException() {
			super("Duration cannot be negative when key modifiers are present.");
		}
	}

	void updateMM(boolean airborne) {
		if (!airborne) {
			if (zOf > highestZ && vz > 0) highestZ = zOf;
			if (zOf < lowestZ && vz < 0) lowestZ = zOf;
			if (xOf > highestX && vx > 0) highestX = xOf;
			if (xOf < lowestX && vx < 0) lowestX = xOf;
		}
	}
	
	public static byte df = 6;
	
	//non-calculation methods
	public void print() {
		DecimalFormat formatting = new DecimalFormat("#");
		formatting.setMaximumFractionDigits(df);
		System.out.println("vz: " + formatting.format(this.vz));
		System.out.println("z: " + formatting.format(this.z));
		System.out.println("vx: " + formatting.format(this.vx));
		System.out.println("x: " + formatting.format(this.x));
		
		System.out.println("mm: " + formatting.format(highestZ - lowestZ));
	}

}
