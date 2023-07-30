package mcpk;

import java.text.DecimalFormat;
import java.util.ArrayList;

import mcpk.utils.Arguments;
import mcpk.utils.MathHelper;

public class Player {
	
	public static byte df = 16;
	
	//momentum calculation
	public int tick = 0;
	public double xOf = 0.0;
	public double zOf = 0.0;
	public double highestZ = 0, lowestZ = 0;
	public double highestX = 0, lowestX = 0;
	public double finalX, finalZ;
	public ArrayList<Double> xCoords = new ArrayList<Double>();
	public ArrayList<Double> zCoords = new ArrayList<Double>();
	
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
			//check previous tick
			if (jumping) {
				if (!xCoords.contains(this.xOf)) {
					xCoords.add(this.xOf);
					xCoords.add(this.xOf);
				}
				if (!zCoords.contains(this.zOf)) {
					zCoords.add(this.zOf);
					zCoords.add(this.zOf);
				}
			}
			
			//start of tick
			tick++;
			slip = 0F;
			multiplier = 1;
			
			//movement multipliers
			if (forward == 0) multiplier = 0;
			if (sprinting) multiplier *= 1.3; //0.30000001192092896D
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
			final float groundSpeed = 0.1F;
			final float airSpeed = 0.02F;
			
			if (airborne) { //air velocity
				this.vz = (this.vz * last_slip * 0.91F) + (forward * airSpeed * multiplier * MathHelper.cos(facing));
				this.vx = (this.vx * last_slip * 0.91F) + (forward * airSpeed * multiplier * -MathHelper.sin(facing));
			}
			else if (jumping) { //jump velocity
				this.vz = (this.vz * last_slip * 0.91F) + (forward * groundSpeed * multiplier * effectMult 
						* (0.216F / (slip * slip * slip)) * MathHelper.cos(facing));
				this.vx = (this.vx * last_slip * 0.91F) + (forward * groundSpeed * multiplier * effectMult 
						* (0.216F / (slip * slip * slip)) * -MathHelper.sin(facing));
				if (sprinting) { 
					this.vz = this.vz + (forward * 0.2F * MathHelper.cos(facing_raw));
					this.vx = this.vx + (forward * 0.2F * -MathHelper.sin(facing_raw));
				}
			} else { //ground velocity
				this.vz = (this.vz * last_slip * 0.91F) + (forward * groundSpeed * multiplier * effectMult 
						* (0.216F / (slip * slip * slip)) * MathHelper.cos(facing));
				this.vx = (this.vx * last_slip * 0.91F) + (forward * groundSpeed * multiplier * effectMult 
						* (0.216F / (slip * slip * slip)) * -MathHelper.sin(facing));
			}
			
			last_slip = slip;
			
			if (!airborne) { //update momentum if airborne
				this.updateMM();
			}
			
		}
		
	}

	void updateMM() {
		xCoords.add(xOf);
		zCoords.add(zOf);
	}
	
	public void updateBounds() {
		xCoords.sort((c1, c2) -> Double.compare(c1, c2));
		zCoords.sort((c1, c2) -> Double.compare(c1, c2));
		
		xCoords.remove(xCoords.size()-1);
		zCoords.remove(zCoords.size()-1);
		zCoords.remove(0);
		xCoords.remove(0);
		
		if (!xCoords.contains(finalX)) xCoords.add(finalX);
		if (!zCoords.contains(finalZ)) zCoords.add(finalZ);
		
		xCoords.sort((c1, c2) -> Double.compare(c1, c2));
		zCoords.sort((c1, c2) -> Double.compare(c1, c2));
		lowestZ = zCoords.get(0);
		highestZ = zCoords.get(zCoords.size()-1);
		lowestX = xCoords.get(0);
		highestX = xCoords.get(xCoords.size()-1);
		
	}
	
	//non-calculation methods
	public void print() {
		DecimalFormat formatting = new DecimalFormat("#");
		formatting.setMaximumFractionDigits(df);
		this.updateBounds();

		System.out.println("z: " + formatting.format(this.z));
		System.out.println("vz: " + formatting.format(this.vz));
		System.out.println("zmm: " + formatting.format(highestZ - lowestZ));
		

		System.out.println("x: " + formatting.format(this.x));
		System.out.println("vx: " + formatting.format(this.vx));
		System.out.println("xmm: " + formatting.format(highestX - lowestX));
		
		System.out.println("vector: " + formatting.format(Math.hypot(this.vz, this.vx)));
		System.out.println("angle: " + Math.atan2(-this.vx,this.vz) * 180d/Math.PI);
		



	}

}
