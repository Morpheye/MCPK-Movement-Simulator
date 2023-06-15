package mcpk;

import java.util.Scanner;

final class Test {

	public static void main(String[] args) {
		String input = "face(45) w sj sa.wa(8)";
		Player player = new Player();
		
		try {
			(new Parser()).parse(player, input);
			player.print();
			
		} catch (Exception e) {}
		
		//Optimizing shit
		try {

	    
		} catch (Exception e) {}

	}

}
