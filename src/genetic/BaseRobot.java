package genetic;
import java.util.Random;

import robocode.AdvancedRobot;
import robocode.ScannedRobotEvent;

public class BaseRobot extends AdvancedRobot {

	private static final boolean DEBUG = false;
	private Random generator = new Random(System.currentTimeMillis());

//	/**
//	 * Returns a random number between -1 and 1.
//	 * 
//	 * @return
//	 */
//	protected double getRandomNumber() {
//		return generator.nextDouble() * 2 - 1;
//	}
//	
//	/**
//	 * If x is positive, executes a fire command with x being the power and
//	 * returns 1; otherwise, does nothing and returns 0
//	 */
//	protected double fireIfPositive(double x) {
//		if (x < 0) {
//			return 0;
//		} else {
//			fire(x);
//			return 1;
//		}
//	}
//	
//	// TERMINAL
//	protected double getEnemyBearing(ScannedRobotEvent e) {
//		return e.getBearing();
//	}
//	
//	// TERMINAL
//	protected double getEnemyDistance(ScannedRobotEvent e) {
//		return e.getDistance();
//	}
//	
//	// TERMINAL
//	protected double getEnemyVelocity(ScannedRobotEvent e) {
//		return e.getVelocity();
//	}
//	
//	// TERMINAL
//	protected double getEnemyHeading(ScannedRobotEvent e) {
//		return e.getHeading();
//	}
//	
//	// TERMINAL
//	protected double getEnemyEnergy(ScannedRobotEvent e) {
//		return e.getEnergy();
//	}
//	
//	protected double abs(double argument) {
//		return Math.abs(argument);
//	}
//	
//	protected double opposite(double argument) {
//		return -argument;
//	}
//	
//	protected double sin(double argument) {
//		return Math.sin(argument);
//	}
//	
//	protected double cos(double argument) {
//		return Math.cos(argument);
//	}
//	
//	protected double asin(double argument) {
//		return Math.asin(argument);
//	}
//	
//	protected double acos(double argument) {
//		return Math.acos(argument);
//	}
//	
//	protected double add(double firstArgument, double secondArgument) {
//		return firstArgument + secondArgument;
//	}
//	
//	protected double subtract(double firstArgument, double secondArgument) {
//		return firstArgument - secondArgument;
//	}
//	
//	protected double multiply(double firstArgument, double secondArgument) {
//		return firstArgument * secondArgument;
//	}
//	
//	protected double divide(double firstArgument, double secondArgument) {
//		return firstArgument / secondArgument;
//	}
//	
//	// TODO Change to ifNegative (makes more sense)
//	protected double ifPositive(double x, double ifTrue, double ifFalse) {
//		return x > 0 ? ifTrue : ifFalse;
//	}
//	
//	protected double ifGreater(double x, double y, double ifTrue, double ifFalse) {
//		return x > y ? ifTrue : ifFalse;
//	}

	protected void log(String message) {
		if (DEBUG) {
			System.out.println(message);
		}
	}
	/**
	 * Returns a random number between -1 and 1.
	 * 
	 * @return
	 */
	protected double getRandomNumber() {
		double d = generator.nextDouble() * 2 - 1;
		log("getRandomNumber() -> " + d);
		return d;
	}

	/**
	 * If x is positive, executes a fire command with x being the power and
	 * returns 1; otherwise, does nothing and returns 0
	 */
	protected double fireIfPositive(double x) {
		if (x < 0) {
			log("fireIfPositive(" + x + ") ->" + 0);
			return 0;
		} else {
			fire(x);
			log("fireIfPositive(" + x + ") ->" + 1);
			return 1;
		}
	}

	// TERMINAL
	protected double getEnemyBearing(ScannedRobotEvent e) {
		double bearing = e.getBearing();
		log("getEnemyBearing() -> " + bearing);
		return bearing;
	}

	// TERMINAL
	protected double getEnemyDistance(ScannedRobotEvent e) {
		double distance = e.getDistance();
		log("getEnemyDistance() -> " + distance);
		return distance;
	}

	// TERMINAL
	protected double getEnemyVelocity(ScannedRobotEvent e) {
		double velocity = e.getVelocity();
		log("getEnemyVelocity() -> " + velocity);
		return velocity;
	}

	// TERMINAL
	protected double getEnemyHeading(ScannedRobotEvent e) {
		double heading = e.getHeading();
		log("getEnemyHeading() -> " + heading);
		return heading;
	}

	// TERMINAL
	protected double getEnemyEnergy(ScannedRobotEvent e) {
		double energy = e.getEnergy();
		log("getEnemyEnergy() -> " + energy);
		return energy;
	}

	protected double abs(double argument) {
		double abs = Math.abs(argument);
		log("abs(" + argument + ") -> " + abs);
		return abs;
	}

	protected double opposite(double argument) {
		double d = -argument;
		log("opposite(" + argument + ") -> " + d);
		return d;
	}

	protected double sin(double argument) {
		double sin = Math.sin(argument);
		log("sin(" + argument + ") -> " + sin);
		return sin;
	}

	protected double cos(double argument) {
		double cos = Math.cos(argument);
		log("cos(" + argument + ") -> " + cos);
		return cos;
	}

	protected double asin(double argument) {
		double asin;
		if (argument > 1 || argument < 0) {
			asin = argument;
		} else {
			asin = Math.asin(argument);
		}
		log("asin(" + argument + ") -> " + asin);
		return asin;
	}

	protected double acos(double argument) {
		double acos;
		if (argument > 1 || argument < 0) {
			acos = argument;
		} else {
			acos = Math.acos(argument);
		}
		log("acos(" + argument + ") -> " + acos);
		return acos;
	}

	protected double add(double firstArgument, double secondArgument) {
		double d = firstArgument + secondArgument;
		log("add(" + firstArgument + ", " + secondArgument + ")" + " -> " + d);
		return d;
	}

	protected double subtract(double firstArgument, double secondArgument) {
		double d = firstArgument - secondArgument;
		log("subtract(" + firstArgument + ", " + secondArgument + ")" + " -> " + d);
		return d;
	}

	protected double multiply(double firstArgument, double secondArgument) {
		double d = firstArgument * secondArgument;
		log("multiply(" + firstArgument + ", " + secondArgument + ")" + " -> " + d);
		return d;
	}

	protected double divide(double firstArgument, double secondArgument) {
		double d = firstArgument / secondArgument;
		log("divide(" + firstArgument + ", " + secondArgument + ")" + " -> " + d);
		return d;
	}
	
	// TODO Change to ifNegative (makes more sense)
	protected double ifPositive(double x, double ifTrue, double ifFalse) {
		double d = x > 0 ? ifTrue : ifFalse;
		log("ifPositive(" + x + ", " + ifTrue + ", " + ifFalse + ") -> " + d);
		return d;
	}

	protected double ifGreater(double x, double y, double ifTrue, double ifFalse) {
		double d = x > y ? ifTrue : ifFalse;
		log("ifGreater(" + x + ", " + y + ", " + ifTrue + ", " + ifFalse + ") -> " + d);
		return d;
	}

}
