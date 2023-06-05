package mcpk.functions.stop;

import java.util.ArrayList;
import java.util.HashMap;

import mcpk.Player;
import mcpk.functions.Function;
import mcpk.utils.Arguments;

public class FunctionStopAir extends Function {
	
	public FunctionStopAir(Player player) {
		super(player);
	}

	@Override
	public String[] names() {
		return new String[] {"stopair", "sta"};
	}

	@Override
	public void run(int duration, float facing, ArrayList<Character> modifiers, HashMap<String, Double> effects) throws DurationException {
		Arguments args = new Arguments();
		args.replace("duration", Math.abs(duration));
		args.replace("facing", (float) Math.toRadians(facing));
		args.replace("facing_raw", (float) Math.toRadians(facing));
		
		checkEffects(effects, args, duration);
		
		args.replace("airborne", true);
		player.move(args);
		
	}

	
	
}
