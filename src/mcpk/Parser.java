package mcpk;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;

import mcpk.functions.Function;
import mcpk.functions.walk.*;
import mcpk.functions.sprint.*;
import mcpk.functions.sneaksprint.*;
import mcpk.functions.sneak.*;
import mcpk.functions.stop.*;

public class Parser {
	
	public static ArrayList<Function> functions = functionInit();
	private static ArrayList<Function> functionInit() {
		ArrayList<Function> functions;
		functions = new ArrayList<Function>();
		functions.addAll(Arrays.asList(new Function[] {
				new FunctionWalk(), new FunctionWalk45(), new FunctionWalkAir(),
				new FunctionWalk45Air(), new FunctionWalkJump(), new FunctionWalkJump45(),
				new FunctionSprint(), new FunctionSprint45(), new FunctionSprintAir(), new FunctionSprint45Air(),
				new FunctionLSprintJump(), new FunctionRSprintJump(), new FunctionLSprintJump45(), new FunctionRSprintJump45(),
				new FunctionSprintJump(), new FunctionSprintJump45(),
				new FunctionSneak(), new FunctionSneak45(), new FunctionSneakAir(),
				new FunctionSneak45Air(), new FunctionSneakJump(), new FunctionSneakJump45(),
				new FunctionSneakSprint(), new FunctionSneakSprint45(), new FunctionSneakSprintAir(), new FunctionSneakSprint45Air(),
				new FunctionLSneakSprintJump(), new FunctionRSneakSprintJump(), new FunctionLSneakSprintJump45(), new FunctionRSneakSprintJump45(),
				new FunctionSneakSprintJump(), new FunctionSneakSprintJump45(),
				new FunctionStop(), new FunctionStopAir(), new FunctionStopJump()
		}));
		return functions;
	}
	
	public static void parse(Player player, String text) throws Exception {
		int state = 0;
		
		String current_function = "";
		String current_argument = "";
		String current_argument_value = "";

		ArrayList<Character> modifiers = new ArrayList<Character>();
		HashMap<String,Double> effects = new HashMap<String,Double>();
		
		int argument_num = 1;
		
		int duration = 1;
		float facing = 0;
		
		String variable_name = "";
		float variable_value = 0;

		Hashtable<String,Float> variables = new Hashtable<String,Float>();
		
		//start scanning
		for (int i = 0; i < text.length(); i++) {
			if (state == 0) { //searching for function
				if (text.charAt(i) == ' ') { //space, skip
					continue;
					
				} else if (text.charAt(i) == '|') { //reset
					player.x = 0;
					player.z = 0;
					
				} else if (text.charAt(i) == 'b') { //distance
					if (player.z < 0) {
						player.z = player.z - 0.6;
					} else {
						player.z = player.z + 0.6;
					}
					
					if (player.x < 0) {
						player.x = player.x - 0.6;
					} else {
						player.x = player.x + 0.6;
					}
					
				} else { //function found
					current_function = current_function + text.charAt(i);
					state = 1;
					
				}
				
			} else if (state == 1) { //currently reading function, looking for opening parenthesis
				if (text.charAt(i) == '(') { //start arguments
					state = 2;
					
				} else if (text.charAt(i) == ' ') { //execute and end function
					//run function
					run_function(player, current_function, duration, facing, modifiers, effects);
					
					modifiers = new ArrayList<Character>();
					effects = new HashMap<String,Double>();
					current_argument = "";
					current_function = "";
					argument_num = 1;
					duration = 1;
					facing = 0;
					state = 0;
				} else if (text.charAt(i) == '.') { //special modifiers
					state = 3;
					
				} else {
					current_function = current_function + text.charAt(i);
					
				}
				
			} else if (state == 2) { //currently reading function arguments

				if (text.charAt(i) == '(') { //SYNTAX ERROR
					throw new ParserException("Error: Unexpected (");
				}

				if (current_function.equals("var") || current_function.equals("variable")) { //special case, defining variable
					if (text.charAt(i) == ' ') {
						throw new ParserException("Error: Space in variable name");
						
					} else if (text.charAt(i) == ',') { //next argument
						if (argument_num == 1) { //variable name
							variable_name = current_argument;
						} else if (argument_num == 2) { //variable value
							variable_value = Float.parseFloat(current_argument);
						}
						current_argument = "";
						argument_num++;
						
					} else if (text.charAt(i) == ')') { //execute and end function
						if (text.charAt(i-1) != ',' && text.charAt(i-1) != '(') { //if prev is not ( or , 
							if (argument_num == 1) { //variable name
								variable_name = current_argument;
							} else if (argument_num == 2) { //variable value
								variable_value = Float.parseFloat(current_argument);
							}
						}
						
						try { //make sure variable is not a float
							float variable = Float.parseFloat(variable_name);
							throw new ParserException("Error: invalid variable name");
						} catch (NumberFormatException e) {
							
						}
						
						//run function
						if (variables.containsKey(variable_name)) { //check if value already exists
							variables.replace(variable_name, variable_value);
						} else {
							variables.put(variable_name, variable_value);
						}
						
						current_argument = "";
						current_function = "";
						argument_num = 1;
						state = 0;
						
					} else { //count arguments
						current_argument = current_argument + text.charAt(i);
						
					}		
					continue;
				}
				
				//otherwise proceed normally
				
				if (text.charAt(i) == ' ') {
					continue;
					
				} else if (text.charAt(i) == ',') { //next argument
					if (argument_num == 1) { //set duration
						if (variables.containsKey(current_argument)) {
							duration = Math.round(variables.get(current_argument));
						} else {
							duration = Integer.parseInt(current_argument);
						}
					} else if (argument_num == 2) { //set facing
						if (variables.containsKey(current_argument)) {
						facing = variables.get(current_argument);
						} else {
						facing = Float.parseFloat(current_argument);
						}
					}
					current_argument = "";
					argument_num++;
					
				} else if (text.charAt(i) == ')') { //execute and end function
					if (text.charAt(i-1) != ',' && text.charAt(i-1) != '(') { //if prev is not ( or , 
						if (argument_num == 1) { //set duration
							if (variables.containsKey(current_argument)) {
								duration = Math.round(variables.get(current_argument));
							} else {
								duration = Integer.parseInt(current_argument);
							}
						} else if (argument_num == 2) { //set facing
							if (variables.containsKey(current_argument)) {
								facing = variables.get(current_argument);
							} else {
								facing = Float.parseFloat(current_argument);
							}
						}
					}
					
					//run function
					run_function(player, current_function, duration, facing, modifiers, effects);
					
					modifiers = new ArrayList<Character>();
					effects = new HashMap<String,Double>();
					current_argument = "";
					current_function = "";
					argument_num = 1;
					duration = 1;
					facing = 0;
					state = 0;

				} else if (text.charAt(i) == '=') {
					state = 4;				
					
				} else { //count arguments
					current_argument = current_argument + text.charAt(i);
					
				}
				
			} else if (state == 3) { //adding key modifiers
				if (text.charAt(i) == '(') { //start arguments
					state = 2;
					
				} else if (text.charAt(i) == ' ') { //execute and end function
					//run function
					run_function(player, current_function, duration, facing, modifiers, effects);
					
					modifiers = new ArrayList<Character>();
					current_argument = "";
					current_function = "";
					argument_num = 1;
					duration = 1;
					facing = 0;
					state = 0;
				} else if (text.charAt(i) == '.') { //SYNTAX ERROR
					throw new ParserException("Error: Unexpected .");
					
				} else { //add the modifier
					char x = text.charAt(i);
					if (x == 'a' || x == 'd' || x == 'w' || x == 's') {
					modifiers.add(text.charAt(i));					
					} else { //modifier doesn't exist
						throw new ParserException("Error: Invalid modifier.");
					}
				}
				
				
				
			} else if (state == 4) { //checking for extra modifiers
				if (text.charAt(i) == ')') { //execute and end function
					if (text.charAt(i-1) != ',' && text.charAt(i-1) != '(') { //if prev is not ( or , 
							if (variables.containsKey(current_argument_value)) { //put argument
								effects.put(current_argument.toLowerCase(), (double) variables.get(current_argument_value));
							} else {
								try { //check if the thing is a double
									effects.put(current_argument.toLowerCase(), Double.parseDouble(current_argument_value));	
								} catch (Exception e) { //if it is not a double it is a boolean
									boolean bool = Boolean.parseBoolean(current_argument_value.toLowerCase());
									if (bool) effects.put(current_argument.toLowerCase(), 1.0);
									else effects.put(current_argument.toLowerCase(), 0.0);
								}


						}
						}
					
					//run function
					run_function(player, current_function, duration, facing, modifiers, effects);
					
					modifiers = new ArrayList<Character>();
					effects = new HashMap<String,Double>();
					current_argument = "";
					current_argument_value = "";
					current_function = "";
					argument_num = 1;
					duration = 1;
					facing = 0;
					state = 0;

				} else if (text.charAt(i) == ',') {
					if (text.charAt(i-1) != ',' && text.charAt(i-1) != '(') { //if prev is not ( or , 
						if (variables.containsKey(current_argument_value)) {
							effects.put(current_argument.toLowerCase(), (double) variables.get(current_argument_value));
						} else {
							try { //check if the thing is a double
								effects.put(current_argument.toLowerCase(), Double.parseDouble(current_argument_value));	
							} catch (Exception e) { //if it is not a double it is a boolean
								boolean bool = Boolean.parseBoolean(current_argument_value.toLowerCase());
								if (bool) effects.put(current_argument.toLowerCase(), 1.0);
								else effects.put(current_argument.toLowerCase(), 0.0);
							}

						}
						
						current_argument = "";
						current_argument_value = "";
						argument_num++;
						state = 2;
						
					}
					
				} else { //count arguments
					current_argument_value = current_argument_value + text.charAt(i);
					
				}
			}
			
		} //end scanning

		if (state == 1 || state == 3) {
			run_function(player, current_function, duration, facing, modifiers, effects);
		}
		
		player.updateMM(false);
		
		return;
	}
	
	//identify and run command
	static void run_function(Player player, String function, int duration, float facing, ArrayList<Character> modifiers, HashMap<String,Double> effects) throws Exception {
		for (Function f : functions) {
			for (String name : f.names()) {
				if (name.equals(function.toLowerCase())) {
					f.run(player, duration, facing, modifiers, effects);
					return;
				}
		}}
		
		throw new ParserException("Unrecognized function");
	}

	@SuppressWarnings("serial")
	static class ParserException extends Exception {
		public ParserException(String message) {
			super(message);
		}
	}
	
}