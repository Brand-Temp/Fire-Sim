/**
 * info1103 - assignment 3
 * Brandon Temple
 * BTEM3257
 */

import java.util.Scanner;
import java.text.DecimalFormat;

public class Firebot {

	//
	// TODO
	//

    private static Scanner scan;
    private static Simulation sim = new Simulation();

  /**********************
  *                     *
  *   COMMAND METHODS   *
  *                     *
  **********************/
  public static void commandBye () {
    System.out.println("bye");
    System.exit(0);
  }

  public static void commandHelp () {
    System.out.println("BYE");
    System.out.println("HELP\n");
    System.out.println("DATA");
    System.out.println("STATUS\n");
    System.out.println("NEXT <days>");
    System.out.println("SHOW <attribute>\n");
    System.out.println("FIRE <region>");
    System.out.println("WIND <direction>");
    System.out.println("EXTINGUISH <region>");
    return;
  }

  public static void commandStatus () {
    System.out.println("Day: " + sim.getDay());
    System.out.println("Wind: " + sim.getWind());
    return;
  }

  public static void commandData () {
    DecimalFormat df = new DecimalFormat("#0.00");
    System.out.println("Damage: " + df.format(sim.getDamage()) + "%");
    System.out.println("Pollution: " + sim.getPollution());
    return;
  }

  public static void commandNext (String[] args) {
    int days = 0;
    if (args.length == 1) {
      days = 1;
    } else {
      try {
        days = Integer.parseInt(args[1]);
      } catch (NumberFormatException e) {
        System.out.println("Invalid command");
      }
    }
    sim.elapseTime(days);
    commandStatus();
    return;
  }

  public static void commandShow (String arg) {
    if (arg.equals("fire")) {
      sim.draw("f");
    } else if (arg.equals("height")) {
      sim.draw("h");
    } else {
      System.out.println("Invalid command");
    }
    return;
  }

  public static void commandWind (String arg) {
    switch(arg) {
      case "all": break;
      case "none": break;
      case "north": break;
      case "south": break;
      case "east": break;
      case "west": break;
      default:
        System.out.println("Invalid command");
        return;
    }
    sim.setWind(arg);
    System.out.println("Set wind to " + arg);
    return;
  }

  public static void commandFire (String[] args) {
    if (args.length == 3) {
      sim.burnTree(args[1], args[2]);
    } else if (args.length == 5) {
      sim.burnRegion(args[1], args[2], args[3], args[4]);
    } else {
      System.out.println("Invalid command");
    }
    return;
  }

  public static void commandExtinguish (String[] args) {
    if (args.length == 3) {
      sim.extinguish(args[1], args[2]);
    } else if (args.length == 5) {
      sim.extinguishRegion(args[1], args[2], args[3], args[4]);
    } else {
      System.out.println("Invalid command");
    }
    return;
  }

  /****************
  *               *
  *   Functions   *
  *               *
  ****************/

	public static void main(String[] args) {
    initialise(args);
    runEngine();
	}

  public static void initialise (String[] args) {
    if (args.length != 3) {
      System.out.println("Usage: java Firebot <seed> <width> <height>");
      System.exit(0);
    }

    int seed = 0;
    int width = 0;
    int height = 0;

    try {
      seed = Integer.parseInt(args[0]);
      width = Integer.parseInt(args[1]);
      height = Integer.parseInt(args[2]);
    } catch (NumberFormatException e) {
      System.out.println("Usage: java Firebot <seed> <width> <height>");
      System.exit(0);
    }

    if (width < 1 || height < 1) {
      System.out.println("Usage: java Firebot <seed> <width> <height>");
      System.exit(0);
    }

    sim.init(height, width, seed);
    System.out.println("Day: " + sim.getDay());
    System.out.println("Wind: " + sim.getWind() + "\n");
  }

  public static void runEngine() {
    scan = new Scanner(System.in);

    System.out.print("> ");
    while (scan.hasNextLine()) {
      String input = scan.nextLine();
      String[] commands = input.split(" ");
      String command = commands[0].toLowerCase();

      switch (command) {
        case "bye":
          commandBye();
          break;
        case "help":
          commandHelp();
          break;
        case "status":
          commandStatus();
          break;
        case "data":
          commandData();
          break;
        case "next":
          commandNext(commands);
          break;
        case "show":
          commandShow(commands[1].toLowerCase());
          break;
        case "wind":
          commandWind(commands[1].toLowerCase());
          break;
        case "fire":
          commandFire(commands);
          break;
        case "extinguish":
          commandExtinguish(commands);
          break;
        default:
          System.out.println("Invalid command");
          break;
      }

      System.out.print("\n> ");
    }
  }
}

