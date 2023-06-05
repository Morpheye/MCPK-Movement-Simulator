package mcpk.functions.walk;

import java.util.ArrayList;
import java.util.HashMap;

import mcpk.Player;
import mcpk.functions.Function;
import mcpk.utils.Arguments;

public class FunctionWalk45Air extends Function {
	
	public FunctionWalk45Air(Player player) {
		super(player);
	}

	@Override
	public String[] names() {
		return new String[] {"w45a", "wa45", "walk45air", "walkair45"};
	}

	@Override
	public void run(int duration, float facing, ArrayList<Character> modifiers, HashMap<String, Double> effects) throws DurationException {
		Arguments args = new Arguments();
		args.replace("duration", Math.abs(duration));
		args.replace("facing", (float) Math.toRadians(facing));
		args.replace("facing_raw", (float) Math.toRadians(facing));
		if (duration > 0) args.replace("forward", 1);
		else if (duration < 0) args.replace("forward", -1);
		
		checkModifiers(modifiers, args, duration);
		checkEffects(effects, args, duration);
		
		args.replace("strafing", true);
		args.replace("airborne", true);
		player.move(args);
		
	}

	
	
}
