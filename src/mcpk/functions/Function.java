package mcpk.functions;

import java.util.ArrayList;
import java.util.HashMap;

import mcpk.Player;
import mcpk.utils.Arguments;

public abstract class Function {
	
	protected Player player;
	
	public Function(Player player) {
		this.player = player;
	}
	
	public abstract String[] names();
	
	public abstract void run(int duration, float facing, ArrayList<Character> modifiers, HashMap<String,Double> effects) throws DurationException, InvalidKeypressException;
	
	@SuppressWarnings("serial")
	public static class InvalidKeypressException extends Exception {
		public InvalidKeypressException(String message) {
			super(message);
		}
	}
	
	@SuppressWarnings("serial")
	public static class DurationException extends Exception {
		public DurationException() {
			super("Duration cannot be negative when key modifiers are present.");
		}
	}
	
	//effect checking
	static protected void checkModifiers(ArrayList<Character> modifiers, Arguments args, int duration) throws DurationException {
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

	static protected void checkNoModifiers(ArrayList<Character> modifiers) throws InvalidKeypressException {
		if (!modifiers.isEmpty()) {
			throw new InvalidKeypressException("This function does not allow key modifiers.");
		}
	}

	static protected void checkEffects(HashMap<String,Double> effects, Arguments args, int duration) {
		if (effects.containsKey("slip")) args.replace("slip", effects.get("slip"));
		if (effects.containsKey("blocking")) args.replace("blocking", effects.get("blocking"));
		if (effects.containsKey("speed")) args.replace("speed", effects.get("speed"));
		
		if (effects.containsKey("swiftness")) args.replace("swiftness", effects.get("swiftness"));
		if (effects.containsKey("slowness")) args.replace("slowness", effects.get("slowness"));
		
	}
	
}
