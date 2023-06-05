package mcpk;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import mcpk.utils.Arguments;
import mcpk.utils.MathHelper;

public class Player {
	
	//coords
	public double x = 0.0;
	public double z = 0.0;
	public double vx = 0.0;
	public double vz = 0.0;
	
	public double slip = 0.0;
	public double last_slip = 0.0;
	public double multiplier = 0.0;
	
	public double inertia_threshold = 0.005D;
	
	//functions
	
	//walk
	
	public void walk(int duration, float facing, ArrayList<Character> modifiers, HashMap<String,Double> effects) throws DurationException {
		Arguments args = new Arguments();
		args.replace("duration", Math.abs(duration));
		args.replace("facing", (float) Math.toRadians(facing));
		args.replace("facing_raw", (float) Math.toRadians(facing));
		if (duration > 0) args.replace("forward", 1);
		else if (duration < 0) args.replace("forward", -1);
		
		checkModifiers(modifiers, args, duration);
		checkEffects(effects, args, duration);
		
		move(args);
	}
	
	public void walkair(int duration, float facing, ArrayList<Character> modifiers, HashMap<String,Double> effects) throws DurationException {
		Arguments args = new Arguments();
		args.replace("duration", Math.abs(duration));
		args.replace("facing", (float) Math.toRadians(facing));
		args.replace("facing_raw", (float) Math.toRadians(facing));
		if (duration > 0) args.replace("forward", 1);
		else if (duration < 0) args.replace("forward", -1);

		checkModifiers(modifiers, args, duration);
		checkEffects(effects, args, duration);
		
		args.replace("airborne", true);
		move(args);
	}
	
	public void walk45(int duration, float facing, ArrayList<Character> modifiers, HashMap<String,Double> effects) throws DurationException {
		Arguments args = new Arguments();
		args.replace("duration", Math.abs(duration));
		args.replace("facing", (float) Math.toRadians(facing));
		args.replace("facing_raw", (float) Math.toRadians(facing));
		if (duration > 0) args.replace("forward", 1);
		else if (duration < 0) args.replace("forward", -1);

		checkModifiers(modifiers, args, duration);
		checkEffects(effects, args, duration);
		
		args.replace("strafing", true);
		move(args);
	}
	
	public void walk45air(int duration, float facing, ArrayList<Character> modifiers, HashMap<String,Double> effects) throws DurationException {
		Arguments args = new Arguments();
		args.replace("duration", Math.abs(duration));
		args.replace("facing", (float) Math.toRadians(facing));
		args.replace("facing_raw", (float) Math.toRadians(facing));
		if (duration > 0) args.replace("forward", 1);
		else if (duration < 0) args.replace("forward", -1);

		checkModifiers(modifiers, args, duration);
		checkEffects(effects, args, duration);
		
		args.replace("airborne", true);
		args.replace("strafing", true);
		move(args);
	}
	
	public void walkjump(int duration, float facing, ArrayList<Character> modifiers, HashMap<String,Double> effects) throws DurationException {
		Arguments args = new Arguments();
		args.replace("duration", 1);
		args.replace("facing", (float) Math.toRadians(facing));
		args.replace("facing_raw", (float) Math.toRadians(facing));
		if (duration > 0) args.replace("forward", 1);
		else if (duration < 0) args.replace("forward", -1);

		checkModifiers(modifiers, args, duration);
		checkEffects(effects, args, duration);
		
		args.replace("jumping", true);
		move(args);
		
		args.replace("duration", Math.abs(duration) - 1);
		args.replace("jumping", false);
		args.replace("airborne", true);
		move(args);
	}
	
	public void walkjump45(int duration, float facing, ArrayList<Character> modifiers, HashMap<String,Double> effects) throws DurationException {
		Arguments args = new Arguments();
		args.replace("duration", 1);
		args.replace("facing", (float) Math.toRadians(facing));
		args.replace("facing_raw", (float) Math.toRadians(facing));
		if (duration > 0) args.replace("forward", 1);
		else if (duration < 0) args.replace("forward", -1);

		checkModifiers(modifiers, args, duration);
		checkEffects(effects, args, duration);
		
		args.replace("jumping", true);
		args.replace("strafing", true);
		move(args);
		
		args.replace("duration", Math.abs(duration) - 1);
		args.replace("jumping", false);
		args.replace("airborne", true);
		move(args);
	}

	//sprint
	
	public void sprint(int duration, float facing, ArrayList<Character> modifiers, HashMap<String,Double> effects) throws DurationException, InvalidKeypressException {
		Arguments args = new Arguments();
		args.replace("duration", Math.abs(duration));
		args.replace("facing", (float) Math.toRadians(facing));
		args.replace("facing_raw", (float) Math.toRadians(facing));
		if (duration > 0) args.replace("forward", 1);
		else if (duration < 0) args.replace("forward", -1);

		checkModifiers(modifiers, args, duration);
		checkEffects(effects, args, duration);
		
		args.replace("sprinting", true);
		move(args);
	}
	
	public void sprintair(int duration, float facing, ArrayList<Character> modifiers, HashMap<String,Double> effects) throws DurationException, InvalidKeypressException {
		Arguments args = new Arguments();
		args.replace("duration", Math.abs(duration));
		args.replace("facing", (float) Math.toRadians(facing));
		args.replace("facing_raw", (float) Math.toRadians(facing));
		if (duration > 0) args.replace("forward", 1);
		else if (duration < 0) args.replace("forward", -1);

		checkModifiers(modifiers, args, duration);
		checkEffects(effects, args, duration);
		
		args.replace("sprinting", true);
		args.replace("airborne", true);
		move(args);
	}
	
	public void sprint45(int duration, float facing, ArrayList<Character> modifiers, HashMap<String,Double> effects) throws DurationException, InvalidKeypressException {
		Arguments args = new Arguments();
		args.replace("duration", Math.abs(duration));
		args.replace("facing", (float) Math.toRadians(facing));
		args.replace("facing_raw", (float) Math.toRadians(facing));
		if (duration > 0) args.replace("forward", 1);
		else if (duration < 0) args.replace("forward", -1);

		checkModifiers(modifiers, args, duration);
		checkEffects(effects, args, duration);
		
		args.replace("sprinting", true);
		args.replace("strafing", true);
		move(args);
	}
	
	public void sprint45air(int duration, float facing, ArrayList<Character> modifiers, HashMap<String,Double> effects) throws DurationException, InvalidKeypressException {
		Arguments args = new Arguments();
		args.replace("duration", Math.abs(duration));
		args.replace("facing", (float) Math.toRadians(facing));
		args.replace("facing_raw", (float) Math.toRadians(facing));
		if (duration > 0) args.replace("forward", 1);
		else if (duration < 0) args.replace("forward", -1);

		checkModifiers(modifiers, args, duration);
		checkEffects(effects, args, duration);
		
		args.replace("sprinting", true);
		args.replace("airborne", true);
		args.replace("strafing", true);
		move(args);
	}
	
	public void sprintjump(int duration, float facing, ArrayList<Character> modifiers, HashMap<String,Double> effects) throws DurationException, InvalidKeypressException {
		Arguments args = new Arguments();
		args.replace("duration", 1);
		args.replace("facing", (float) Math.toRadians(facing));
		args.replace("facing_raw", (float) Math.toRadians(facing));
		if (duration > 0) args.replace("forward", 1);
		else if (duration < 0) args.replace("forward", -1);

		checkModifiers(modifiers, args, duration);
		checkEffects(effects, args, duration);
		
		args.replace("jumping", true);
		args.replace("sprinting", true);
		move(args);
		
		args.replace("duration", Math.abs(duration) - 1);
		args.replace("jumping", false);
		args.replace("airborne", true);
		move(args);
	}
	
	public void sprintjump45(int duration, float facing, ArrayList<Character> modifiers, HashMap<String,Double> effects) throws DurationException, InvalidKeypressException {
		Arguments args = new Arguments();
		args.replace("duration", 1);
		args.replace("facing", (float) Math.toRadians(facing));
		args.replace("facing_raw", (float) Math.toRadians(facing));
		if (duration > 0) args.replace("forward", 1);
		else if (duration < 0) args.replace("forward", -1);
		
		checkModifiers(modifiers, args, duration);
		checkEffects(effects, args, duration);
		
		args.replace("jumping", true);
		args.replace("sprinting", true);
		move(args);
		
		args.replace("duration", Math.abs(duration) - 1);
		args.replace("strafing", true);
		args.replace("jumping", false);
		args.replace("airborne", true);
		move(args);
	}
	
	public void lsprintjump(int duration, float facing, ArrayList<Character> modifiers, HashMap<String,Double> effects) throws InvalidKeypressException {
		Arguments args = new Arguments();
		args.replace("duration", 1);
		args.replace("facing", (float) Math.toRadians(facing - 45));
		args.replace("facing_raw", (float) Math.toRadians(facing));
		if (duration > 0) args.replace("forward", 1);
		else if (duration < 0) args.replace("forward", -1);

		checkNoModifiers(modifiers);
		checkEffects(effects, args, duration);
		
		args.replace("jumping", true);
		args.replace("sprinting", true);
		args.replace("strafing", true);
		move(args);
		
		args.replace("facing", (float) Math.toRadians(facing));
		args.replace("duration", Math.abs(duration) - 1);
		args.replace("jumping", false);
		args.replace("strafing", false);
		args.replace("airborne", true);
		move(args);
	}
	
	public void rsprintjump(int duration, float facing, ArrayList<Character> modifiers, HashMap<String,Double> effects) throws InvalidKeypressException {
		Arguments args = new Arguments();
		args.replace("duration", 1);
		args.replace("facing", (float) Math.toRadians(facing + 45));
		args.replace("facing_raw", (float) Math.toRadians(facing));
		if (duration > 0) args.replace("forward", 1);
		else if (duration < 0) args.replace("forward", -1);

		checkNoModifiers(modifiers);
		checkEffects(effects, args, duration);
		
		args.replace("jumping", true);
		args.replace("sprinting", true);
		args.replace("strafing", true);
		move(args);
		
		args.replace("facing", (float) Math.toRadians(facing));
		args.replace("duration", Math.abs(duration) - 1);
		args.replace("jumping", false);
		args.replace("strafing", false);
		args.replace("airborne", true);
		move(args);
	}
	
	public void lsprintjump45(int duration, float facing, ArrayList<Character> modifiers, HashMap<String,Double> effects) throws InvalidKeypressException {
		Arguments args = new Arguments();
		args.replace("duration", 1);
		args.replace("facing", (float) Math.toRadians(facing));
		args.replace("facing_raw", (float) Math.toRadians(facing + 45));
		if (duration > 0) args.replace("forward", 1);
		else if (duration < 0) args.replace("forward", -1);

		checkNoModifiers(modifiers);
		checkEffects(effects, args, duration);
		
		args.replace("jumping", true);
		args.replace("sprinting", true);
		args.replace("strafing", true);
		move(args);
		
		args.replace("duration", Math.abs(duration) - 1);
		args.replace("jumping", false);
		args.replace("airborne", true);
		move(args);
	}
	
	public void rsprintjump45(int duration, float facing, ArrayList<Character> modifiers, HashMap<String,Double> effects) throws InvalidKeypressException {
		Arguments args = new Arguments();
		args.replace("duration", 1);
		args.replace("facing", (float) Math.toRadians(facing));
		args.replace("facing_raw", (float) Math.toRadians(facing - 45));
		if (duration > 0) args.replace("forward", 1);
		else if (duration < 0) args.replace("forward", -1);

		checkNoModifiers(modifiers);
		checkEffects(effects, args, duration);
		
		args.replace("jumping", true);
		args.replace("sprinting", true);
		args.replace("strafing", true);
		move(args);
		
		args.replace("duration", Math.abs(duration) - 1);
		args.replace("jumping", false);
		args.replace("airborne", true);
		move(args);
	}

	//sneak
	
	public void sneak(int duration, float facing, ArrayList<Character> modifiers, HashMap<String,Double> effects) throws DurationException {
		Arguments args = new Arguments();
		args.replace("duration", Math.abs(duration));
		args.replace("facing", (float) Math.toRadians(facing));
		args.replace("facing_raw", (float) Math.toRadians(facing));
		if (duration > 0) args.replace("forward", 1);
		else if (duration < 0) args.replace("forward", -1);

		checkModifiers(modifiers, args, duration);
		checkEffects(effects, args, duration);
		
		args.replace("sneaking", true);
		move(args);
	}
	
	public void sneakair(int duration, float facing, ArrayList<Character> modifiers, HashMap<String,Double> effects) throws DurationException {
		Arguments args = new Arguments();
		args.replace("duration", Math.abs(duration));
		args.replace("facing", (float) Math.toRadians(facing));
		args.replace("facing_raw", (float) Math.toRadians(facing));
		if (duration > 0) args.replace("forward", 1);
		else if (duration < 0) args.replace("forward", -1);

		checkModifiers(modifiers, args, duration);
		checkEffects(effects, args, duration);
		
		args.replace("sneaking", true);
		args.replace("airborne", true);
		move(args);
	}
	
	public void sneak45(int duration, float facing, ArrayList<Character> modifiers, HashMap<String,Double> effects) throws DurationException {
		Arguments args = new Arguments();
		args.replace("duration", Math.abs(duration));
		args.replace("facing", (float) Math.toRadians(facing));
		args.replace("facing_raw", (float) Math.toRadians(facing));
		if (duration > 0) args.replace("forward", 1);
		else if (duration < 0) args.replace("forward", -1);

		checkModifiers(modifiers, args, duration);
		checkEffects(effects, args, duration);
		
		args.replace("sneaking", true);
		args.replace("strafing", true);
		move(args);
	}
	
	public void sneak45air(int duration, float facing, ArrayList<Character> modifiers, HashMap<String,Double> effects) throws DurationException {
		Arguments args = new Arguments();
		args.replace("duration", Math.abs(duration));
		args.replace("facing", (float) Math.toRadians(facing));
		args.replace("facing_raw", (float) Math.toRadians(facing));
		if (duration > 0) args.replace("forward", 1);
		else if (duration < 0) args.replace("forward", -1);

		checkModifiers(modifiers, args, duration);
		checkEffects(effects, args, duration);
		
		args.replace("sneaking", true);
		args.replace("airborne", true);
		args.replace("strafing", true);
		move(args);
	}
	
	public void sneakjump(int duration, float facing, ArrayList<Character> modifiers, HashMap<String,Double> effects) throws DurationException {
		Arguments args = new Arguments();
		args.replace("duration", 1);
		args.replace("facing", (float) Math.toRadians(facing));
		args.replace("facing_raw", (float) Math.toRadians(facing));
		if (duration > 0) args.replace("forward", 1);
		else if (duration < 0) args.replace("forward", -1);

		checkModifiers(modifiers, args, duration);
		checkEffects(effects, args, duration);
		
		args.replace("jumping", true);
		args.replace("sneaking", true);
		move(args);
		
		args.replace("duration", Math.abs(duration) - 1);
		args.replace("jumping", false);
		args.replace("airborne", true);
		move(args);
	}
	
	public void sneakjump45(int duration, float facing, ArrayList<Character> modifiers, HashMap<String,Double> effects) throws DurationException {
		Arguments args = new Arguments();
		args.replace("duration", 1);
		args.replace("facing", (float) Math.toRadians(facing));
		args.replace("facing_raw", (float) Math.toRadians(facing));
		if (duration > 0) args.replace("forward", 1);
		else if (duration < 0) args.replace("forward", -1);

		checkModifiers(modifiers, args, duration);
		checkEffects(effects, args, duration);
		
		args.replace("jumping", true);
		args.replace("sneaking", true);
		args.replace("strafing", true);
		move(args);
		
		args.replace("duration", Math.abs(duration) - 1);
		args.replace("jumping", false);
		args.replace("airborne", true);
		move(args);
	}

	//sneaksprint
	
	public void sneaksprint(int duration, float facing, ArrayList<Character> modifiers, HashMap<String,Double> effects) throws DurationException, InvalidKeypressException {
		Arguments args = new Arguments();
		args.replace("duration", Math.abs(duration));
		args.replace("facing", (float) Math.toRadians(facing));
		args.replace("facing_raw", (float) Math.toRadians(facing));
		if (duration > 0) args.replace("forward", 1);
		else if (duration < 0) args.replace("forward", -1);
		
		checkModifiers(modifiers, args, duration);
		checkEffects(effects, args, duration);
		
		args.replace("sneaking", true);
		args.replace("sprinting", true);
		move(args);
	}
	
	public void sneaksprintair(int duration, float facing, ArrayList<Character> modifiers, HashMap<String,Double> effects) throws DurationException, InvalidKeypressException {
		Arguments args = new Arguments();
		args.replace("duration", Math.abs(duration));
		args.replace("facing", (float) Math.toRadians(facing));
		args.replace("facing_raw", (float) Math.toRadians(facing));
		if (duration > 0) args.replace("forward", 1);
		else if (duration < 0) args.replace("forward", -1);
		
		checkModifiers(modifiers, args, duration);
		checkEffects(effects, args, duration);
		
		args.replace("sneaking", true);
		args.replace("sprinting", true);
		args.replace("airborne", true);
		move(args);
	}
	
	public void sneaksprint45(int duration, float facing, ArrayList<Character> modifiers, HashMap<String,Double> effects) throws DurationException, InvalidKeypressException {
		Arguments args = new Arguments();
		args.replace("duration", Math.abs(duration));
		args.replace("facing", (float) Math.toRadians(facing));
		args.replace("facing_raw", (float) Math.toRadians(facing));
		if (duration > 0) args.replace("forward", 1);
		else if (duration < 0) args.replace("forward", -1);
		
		checkModifiers(modifiers, args, duration);
		checkEffects(effects, args, duration);
		
		args.replace("sneaking", true);
		args.replace("sprinting", true);
		args.replace("strafing", true);
		move(args);
	}
	
	public void sneaksprint45air(int duration, float facing, ArrayList<Character> modifiers, HashMap<String,Double> effects) throws DurationException, InvalidKeypressException {
		Arguments args = new Arguments();
		args.replace("duration", Math.abs(duration));
		args.replace("facing", (float) Math.toRadians(facing));
		args.replace("facing_raw", (float) Math.toRadians(facing));
		if (duration > 0) args.replace("forward", 1);
		else if (duration < 0) args.replace("forward", -1);
		
		checkModifiers(modifiers, args, duration);
		checkEffects(effects, args, duration);
		
		args.replace("sneaking", true);
		args.replace("sprinting", true);
		args.replace("airborne", true);
		args.replace("strafing", true);
		move(args);
	}
	
	public void sneaksprintjump(int duration, float facing, ArrayList<Character> modifiers, HashMap<String,Double> effects) throws DurationException, InvalidKeypressException {
		Arguments args = new Arguments();
		args.replace("duration", 1);
		args.replace("facing", (float) Math.toRadians(facing));
		args.replace("facing_raw", (float) Math.toRadians(facing));
		if (duration > 0) args.replace("forward", 1);
		else if (duration < 0) args.replace("forward", -1);
		
		checkModifiers(modifiers, args, duration);
		checkEffects(effects, args, duration);
		
		args.replace("jumping", true);
		args.replace("sprinting", true);
		args.replace("sneaking", true);
		move(args);
		
		args.replace("duration", Math.abs(duration) - 1);
		args.replace("jumping", false);
		args.replace("airborne", true);
		move(args);
	}
	
	public void sneaksprintjump45(int duration, float facing, ArrayList<Character> modifiers, HashMap<String,Double> effects) throws DurationException, InvalidKeypressException {
		Arguments args = new Arguments();
		args.replace("duration", 1);
		args.replace("facing", (float) Math.toRadians(facing));
		args.replace("facing_raw", (float) Math.toRadians(facing));
		if (duration > 0) args.replace("forward", 1);
		else if (duration < 0) args.replace("forward", -1);
		
		checkModifiers(modifiers, args, duration);
		checkEffects(effects, args, duration);
		
		args.replace("jumping", true);
		args.replace("sneaking", true);
		args.replace("sprinting", true);
		move(args);
		
		args.replace("duration", Math.abs(duration) - 1);
		args.replace("strafing", true);
		args.replace("jumping", false);
		args.replace("airborne", true);
		move(args);
	}
	
	public void lsneaksprintjump(int duration, float facing, ArrayList<Character> modifiers, HashMap<String,Double> effects) throws InvalidKeypressException {
		Arguments args = new Arguments();
		args.replace("duration", 1);
		args.replace("facing", (float) Math.toRadians(facing - 45));
		args.replace("facing_raw", (float) Math.toRadians(facing));
		if (duration > 0) args.replace("forward", 1);
		else if (duration < 0) args.replace("forward", -1);
		
		checkNoModifiers(modifiers);
		checkEffects(effects, args, duration);
		
		args.replace("jumping", true);
		args.replace("sneaking", true);
		args.replace("sprinting", true);
		args.replace("strafing", true);
		move(args);
		
		args.replace("facing", (float) Math.toRadians(facing));
		args.replace("duration", Math.abs(duration) - 1);
		args.replace("jumping", false);
		args.replace("strafing", false);
		args.replace("airborne", true);
		move(args);
	}
	
	public void rsneaksprintjump(int duration, float facing, ArrayList<Character> modifiers, HashMap<String,Double> effects) throws InvalidKeypressException {
		Arguments args = new Arguments();
		args.replace("duration", 1);
		args.replace("facing", (float) Math.toRadians(facing + 45));
		args.replace("facing_raw", (float) Math.toRadians(facing));
		if (duration > 0) args.replace("forward", 1);
		else if (duration < 0) args.replace("forward", -1);
		
		checkNoModifiers(modifiers);
		checkEffects(effects, args, duration);
		
		args.replace("jumping", true);
		args.replace("sneaking", true);
		args.replace("sprinting", true);
		args.replace("strafing", true);
		move(args);
		
		args.replace("facing", (float) Math.toRadians(facing));
		args.replace("duration", Math.abs(duration) - 1);
		args.replace("jumping", false);
		args.replace("strafing", false);
		args.replace("airborne", true);
		move(args);
	}
	
	public void lsneaksprintjump45(int duration, float facing, ArrayList<Character> modifiers, HashMap<String,Double> effects) throws InvalidKeypressException {
		Arguments args = new Arguments();
		args.replace("duration", 1);
		args.replace("facing", (float) Math.toRadians(facing));
		args.replace("facing_raw", (float) Math.toRadians(facing + 45));
		if (duration > 0) args.replace("forward", 1);
		else if (duration < 0) args.replace("forward", -1);
		
		checkNoModifiers(modifiers);
		checkEffects(effects, args, duration);
		
		args.replace("jumping", true);
		args.replace("sneaking", true);
		args.replace("sprinting", true);
		args.replace("strafing", true);
		move(args);
		
		args.replace("duration", Math.abs(duration) - 1);
		args.replace("jumping", false);
		args.replace("airborne", true);
		move(args);
	}
	
	public void rsneaksprintjump45(int duration, float facing, ArrayList<Character> modifiers, HashMap<String,Double> effects) throws InvalidKeypressException {
		Arguments args = new Arguments();
		args.replace("duration", 1);
		args.replace("facing", (float) Math.toRadians(facing));
		args.replace("facing_raw", (float) Math.toRadians(facing - 45));
		if (duration > 0) args.replace("forward", 1);
		else if (duration < 0) args.replace("forward", -1);
		
		checkNoModifiers(modifiers);
		checkEffects(effects, args, duration);
		
		args.replace("jumping", true);
		args.replace("sneaking", true);
		args.replace("sprinting", true);
		args.replace("strafing", true);
		move(args);
		
		args.replace("duration", Math.abs(duration) - 1);
		args.replace("jumping", false);
		args.replace("airborne", true);
		move(args);
	}
	
	//stop functions
	
	public void stop(int duration, float facing, ArrayList<Character> modifiers, HashMap<String,Double> effects) {
		Arguments args = new Arguments();
		args.replace("duration", Math.abs(duration));
		args.replace("facing", (float) Math.toRadians(facing));
		args.replace("facing_raw", (float) Math.toRadians(facing));
		
		checkEffects(effects, args, duration);
		
		move(args);
	}
	
	public void stopair(int duration, float facing, ArrayList<Character> modifiers, HashMap<String,Double> effects) {
		Arguments args = new Arguments();
		args.replace("duration", Math.abs(duration));
		args.replace("facing", (float) Math.toRadians(facing));
		args.replace("facing_raw", (float) Math.toRadians(facing));
		
		checkEffects(effects, args, duration);
		
		args.replace("airborne", true);
		move(args);
	}
	
	public void jump(int duration, float facing, ArrayList<Character> modifiers, HashMap<String,Double> effects) {
		Arguments args = new Arguments();
		args.replace("duration", Math.abs(duration));
		args.replace("facing", (float) Math.toRadians(facing));
		args.replace("facing_raw", (float) Math.toRadians(facing));
		
		checkEffects(effects, args, duration);
		
		args.replace("jumping", true);
		move(args);
	}
	
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
			slip = 0F;
			multiplier = 1;
			
			//movement multipliers
			if (forward == 0) multiplier = 0;
			if (sprinting) multiplier *= 1.3;
			if (sneaking) multiplier *= 0.3;
			if (blocking) multiplier *= 0.2;
			
			//check for 45 strafing
			if (strafing == true) {
				if (sneaking == true) multiplier *= 0.98F * Math.sqrt(2);
			} else multiplier *= 0.98F;
			
			//slipperiness
			if (airborne == true) slip = 1F;
			else slip = (double) args.get("slip");
			if (last_slip == 0F) last_slip = slip;
			
			//movement
			this.z += this.vz;
			this.x += this.vx;
			
			//inertia threshold
			if (Math.abs(this.vz * last_slip * 0.91F) < inertia_threshold) this.vz = 0;
			if (Math.abs(this.vx * last_slip * 0.91F) < inertia_threshold) this.vx = 0;

			//speed calculations
			if (airborne == true) { //air velocity
				this.vz = (this.vz * last_slip * 0.91F) + (forward * 0.02F * multiplier * MathHelper.cos(facing));
				this.vx = (this.vx * last_slip * 0.91F) + (forward * 0.02F * multiplier * -MathHelper.sin(facing));
			}
			else if (jumping == true) { //jump velocity
				this.vz = (this.vz * last_slip * 0.91F) + (forward * 0.1F * multiplier * effectMult * (0.216F / (slip * slip * slip)) * MathHelper.cos(facing));
				this.vx = (this.vx * last_slip * 0.91F) + (forward * 0.1F * multiplier * effectMult * (0.216F / (slip * slip * slip)) * -MathHelper.sin(facing));
				if (sprinting == true) { 
					this.vz = this.vz + (forward * 0.2F * MathHelper.cos(facing_raw));
					this.vx = this.vx + (forward * 0.2F * -MathHelper.sin(facing_raw));
				}
			} else { //ground velocity
				this.vz = (this.vz * last_slip * 0.91F) + (forward * 0.1F * multiplier * effectMult * (0.216F / (slip * slip * slip)) * MathHelper.cos(facing));
				this.vx = (this.vx * last_slip * 0.91F) + (forward * 0.1F * multiplier * effectMult * (0.216F / (slip * slip * slip)) * -MathHelper.sin(facing));
			}
			
			last_slip = slip;
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
	
	//functions
	
	void checkModifiers(ArrayList<Character> modifiers, Arguments args, int duration) throws DurationException {
		if (!modifiers.isEmpty()) { //modifiers
			if (duration < 0) throw new DurationException();
			int facingChange = 0;
			
			if (modifiers.contains('a')) { //strafing?
				facingChange = facingChange - 90;
			}
			if (modifiers.contains('d')) {
				facingChange = facingChange + 90;
			}
			
			if (modifiers.contains('w')); //w + potential strafing, no change
			else if (modifiers.contains('s')) args.replace("forward", (int) args.get("forward") * -1); //s + potential strafing
			else args.replace("forward", 1); //only strafing
			if (modifiers.contains('w') && modifiers.contains('s')) { //WS
				if (modifiers.contains('a') && modifiers.contains('d')) args.replace("forward", 0); // WASD user is trolling
				else if (modifiers.contains('a') || modifiers.contains('d')) args.replace("forward", 1); // strafe only
				else args.replace("forward", 0); //WS user is trolling
			}
			//both forward movement and strafing?
			if ((modifiers.contains('s') || modifiers.contains('w')) && (modifiers.contains('a') || modifiers.contains('d')) && !(modifiers.contains('s') && modifiers.contains('w'))) {
				args.replace("strafing", true); 
				facingChange = facingChange / 2 * (int) args.get("forward");
			} //apply facing change
			args.replace("facing", (float) args.get("facing") + (float) Math.toRadians(facingChange));
			
		} //end modifying
		
	}

	void checkNoModifiers(ArrayList<Character> modifiers) throws InvalidKeypressException {
		if (!modifiers.isEmpty()) {
			throw new InvalidKeypressException("This function does not allow key modifiers.");
		}
	}

	void checkEffects(HashMap<String,Double> effects, Arguments args, int duration) {
		if (effects.containsKey("slip")) args.replace("slip", effects.get("slip"));
		if (effects.containsKey("blocking")) args.replace("blocking", effects.get("blocking"));
		if (effects.containsKey("speed")) args.replace("speed", effects.get("speed"));
		
		if (effects.containsKey("swiftness")) args.replace("swiftness", effects.get("swiftness"));
		if (effects.containsKey("slowness")) args.replace("slowness", effects.get("slowness"));
		
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
	}

}
