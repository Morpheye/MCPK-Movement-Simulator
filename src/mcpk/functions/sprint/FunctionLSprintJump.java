package mcpk.functions.sprint;

import java.util.ArrayList;
import java.util.HashMap;

import mcpk.Player;
import mcpk.functions.Function;
import mcpk.utils.Arguments;

public class FunctionLSprintJump extends Function {

	public FunctionLSprintJump(Player player) {
		super(player);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String[] names() {
		// TODO Auto-generated method stub
		return new String[] {"lsprintjump", "lsj"};
	}

	@Override
	public void run(int duration, float facing, ArrayList<Character> modifiers, HashMap<String, Double> effects)
			throws DurationException, InvalidKeypressException {
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
		player.move(args);
		
		args.replace("facing", (float) Math.toRadians(facing));
		args.replace("duration", Math.abs(duration) - 1);
		args.replace("jumping", false);
		args.replace("strafing", false);
		args.replace("airborne", true);
		player.move(args);
		
	}

}
