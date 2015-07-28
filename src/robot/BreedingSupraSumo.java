package robot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class BreedingSupraSumo {

	private static final double MUTATION_PROBABILITY = 0.05;
	private static final int TOURNAMENT_SIZE = 5;
	private static final int ELITISM = 2;

	private static Random generator = new Random();

	private static GeneticRobot tournament(GeneticRobot[] currentGeneration) {
		List<Integer> randomIndexes = nextRandomIndexes(
				currentGeneration.length, TOURNAMENT_SIZE);
		List<GeneticRobot> randomRobots = new ArrayList<GeneticRobot>();

		for (int i = 0; i < randomIndexes.size(); i++) {
			randomRobots.add(currentGeneration[randomIndexes.get(i)]);
		}
		Collections.sort(randomRobots);
		return randomRobots.get(randomRobots.size() - 1);
	}

	private static List<GeneticRobot> breed(GeneticRobot[] currentGeneration,
			int generatedRobots) {
		List<GeneticRobot> randomRobots = new ArrayList<GeneticRobot>();

		double randomNumber = generator.nextDouble();
		if (randomNumber < MUTATION_PROBABILITY) {
			GeneticRobot randomRobot = new GeneticRobot(
					tournament(currentGeneration));
			TreeManipulatorTabajara.mutation(randomRobot.getTrees());
			randomRobots.add(randomRobot);
		} else {
			GeneticRobot randomRobot1 = new GeneticRobot(
					tournament(currentGeneration));
			GeneticRobot randomRobot2 = new GeneticRobot(
					tournament(currentGeneration));
			TreeManipulatorTabajara.crossover(randomRobot1.getTrees(),
					randomRobot2.getTrees());
			randomRobots.add(randomRobot1);
			randomRobots.add(randomRobot2);
			if (generatedRobots == GeneticProgram.POPULATION - 1) {
				randomRobots.remove(0);
			}
		}

		return randomRobots;
	}

	/**
	 * Returns five different random indexes.
	 * 
	 * @param maxValue
	 * @param length
	 * @return
	 */
	private static List<Integer> nextRandomIndexes(int maxValue, int length) {
		List<Integer> randomIndexes = new ArrayList<Integer>();
		while (randomIndexes.size() < length) {
			int randomIndex = generator.nextInt(maxValue);
			while (randomIndexes.contains(randomIndex)) {
				randomIndex = generator.nextInt(maxValue);
			}
			randomIndexes.add(randomIndex);
		}
		return randomIndexes;
	}

	public static void evolute(GeneticRobot[] robots) {
		List<GeneticRobot> newPopulation = new ArrayList<GeneticRobot>();
		Arrays.sort(robots);

		System.out.println("Elitism");
		for (int i = 1; i <= ELITISM; i++) {
			newPopulation.add(new GeneticRobot(robots[robots.length - i]));
		}

		while (newPopulation.size() < GeneticProgram.POPULATION) {
			newPopulation.addAll(breed(robots, newPopulation.size()));
		}

		for (int i = 0; i < robots.length; i++) {
			robots[i] = newPopulation.get(i);
		}

		fixupRobotNames(robots);
		System.out.println("Evoluted population of size " + newPopulation.size());
	}

	private static void fixupRobotNames(GeneticRobot[] robots) {
		for (int i = 0; i < robots.length; i++) {
			robots[i].setName(GeneticProgram.ROBOTS_COMMON_NAME + i);
		}
	}

}
