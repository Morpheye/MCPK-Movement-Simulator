package mcpk.functions.sneak;

import java.util.ArrayList;
import java.util.HashMap;

import mcpk.Player;
import mcpk.functions.Function;
import mcpk.utils.Arguments;

public class FunctionSneak45 extends Function {
	
	public FunctionSneak45(Player player) {
		super(player);
	}

	@Override
	public String[] names() {
		return new String[] {"sn45", "c45", "sneak45", "crouch45"};
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
		
		args.replace("sneaking", true);
		args.replace("strafing", true);
		player.move(args);
		
	}

	
	
}
