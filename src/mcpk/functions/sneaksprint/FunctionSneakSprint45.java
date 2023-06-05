package mcpk.functions.sneaksprint;

import java.util.ArrayList;
import java.util.HashMap;

import mcpk.Player;
import mcpk.functions.Function;
import mcpk.utils.Arguments;

public class FunctionSneakSprint45 extends Function {

	@Override
	public String[] names() {
		// TODO Auto-generated method stub
		return new String[] {"sneaksprint45", "sns45", "crouchsprint45", "cs45"};
	}

	@Override
	public void run(Player player, int duration, float facing, ArrayList<Character> modifiers, HashMap<String, Double> effects)
			throws DurationException {
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
		player.move(args);
		
	}

}
