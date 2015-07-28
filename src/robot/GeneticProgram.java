package robot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import battle.BattleRunner;

public class GeneticProgram {

	private final static String LINE_SEPARATOR = System
			.getProperty("line.separator");
	private final static String FILE_SEPARATOR = System
			.getProperty("file.separator");

	private final static Logger logger = Logger.getLogger("GeneticProgram");

	private static SimpleDateFormat dateFormatter = new SimpleDateFormat(
			"yyyy.MM.dd-HH:mm:ss");
	private static Date STARTING_TIME = new Date();
	private static final String RUN_IDENTIFICATOR = "logs" + FILE_SEPARATOR
			+ dateFormatter.format(STARTING_TIME);

	private static final String LOG_FILE_NAME = getLogFileName();

	private static final String PROJECT_PATH = "/home/arnett/Projects/IA/sigma-star-05";
	private static final String ROBOCODE_ROBOTS_PATH = BattleRunner.ROBOCODE_HOME
			+ FILE_SEPARATOR + "robots";
	private static final String GENERATED_ROBOTS_PATH = ROBOCODE_ROBOTS_PATH
			+ FILE_SEPARATOR + BattleRunner.ROBOTS_PACKAGE;

	public static final String ROBOTS_COMMON_NAME = "GeneticRobot";
	private static final String CODE_TEMPLATE_PATH = "template/TemplateRobot.java";
	private static final String CODE_TEMPLATE = generateCommonTemplate();

	public final static int POPULATION = 256;
	public final static int GENERATIONS = 200;
	public final static int ROUNDS = 25;

	public final static String OPPONENT = "supersample.SuperRamFire*";

	private static int currentGeneration = 0;
	private static GeneticRobot[] robots = new GeneticRobot[POPULATION];

	private static BattleRunner runner = new BattleRunner();

	private static String getLogFileName() {
		new File("logs").mkdir();
		System.out.println(RUN_IDENTIFICATOR);
		new File(RUN_IDENTIFICATOR).mkdir();
		return RUN_IDENTIFICATOR + ".log";
	}

	private static void log(String message) {
		FileWriter robotFile;
		try {
			robotFile = new FileWriter(LOG_FILE_NAME, true);
			PrintWriter writer = new PrintWriter(robotFile);

			writer.println(message);
			robotFile.close();
		} catch (IOException e) {
			System.err.println("Unexpected Error: " + e.getMessage());
		}
	}

	public static void main(String[] args) {
		System.out.println("Populating robots");
		populateRobots();

		while (!terminationCriteriaSatisfied()) {
			System.out.println("Generation " + currentGeneration);

			clearRobots();
			createRobotJavaClasses();

			System.out.println("Beginning to compile robots");
			compileRobotJavaClasses();
			System.out.println("Robots compiled");

			logger.info("Starting to run battle");
			runner.runBattle(robots, OPPONENT, ROUNDS);
			logger.info("Battle ended");

			GeneticRobot bestRobot = getBestRobotByFitness(robots);
			saveRobot(bestRobot);

			log("Best fitness for Generation " + currentGeneration + ": "
					+ bestRobot.getFitness());
			log("Average fitness for Generation " + currentGeneration + ": "
					+ getAvgFitness(robots));

			System.out.println("Beginning to evolute robots");
			BreedingSupraSumo.evolute(robots);
			System.out.println("Evolution ended");
		}
	}

	private static void saveRobot(GeneticRobot bestRobot) {
		GeneticRobot robot = new GeneticRobot(bestRobot);
		robot.setName(bestRobot.getName() + "Gen" + currentGeneration);
		createRobotJavaClass(RUN_IDENTIFICATOR, robot);
	}

	private static double getAvgFitness(GeneticRobot[] robots) {
		double avg = 0;
		for (int i = 0; i < robots.length; i++) {
			avg += robots[i].getFitness();
		}
		return avg / robots.length;
	}

	private static GeneticRobot getBestRobotByFitness(GeneticRobot[] robots) {
		GeneticRobot best = robots[0];
		for (int i = 0; i < robots.length; i++) {
			if (robots[i].compareTo(best) > 0) {
				best = robots[i];
			}
		}
		return best;
	}

	private static boolean terminationCriteriaSatisfied() {
		currentGeneration++;
		return currentGeneration >= GENERATIONS;
	}

	private static void compileRobotJavaClasses() {
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(
				diagnostics, null, null);
		Iterable<? extends JavaFileObject> compilationUnits = fileManager
				.getJavaFileObjectsFromStrings(getAllRobotJavaClassesLocation());

		Iterable<String> options = Arrays.asList(new String[] { "-d",
				ROBOCODE_ROBOTS_PATH });
		JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager,
				diagnostics, options, null, compilationUnits);
		boolean success = task.call();

		try {
			fileManager.close();
		} catch (IOException e) {
			System.err.println("Unexpected Error: " + e.getMessage());
		}

		if (!success) {
			System.err.println("Robot classes were not compiled successfully");
		}
	}

	private static List<String> getAllRobotJavaClassesLocation() {
		List<String> robotClasses = new ArrayList<String>();
		File genDir = new File(GENERATED_ROBOTS_PATH);
		if (genDir.exists()) {
			for (File geneticRobotClass : genDir.listFiles()) {
				robotClasses.add(GENERATED_ROBOTS_PATH + FILE_SEPARATOR
						+ geneticRobotClass.getName());
			}
		}
		robotClasses.add(GENERATED_ROBOTS_PATH + FILE_SEPARATOR + "BaseRobot.java");
		return robotClasses;
	}

	private static void createRobotJavaClasses() {
		new File(GENERATED_ROBOTS_PATH).mkdir();

		try {
			File source = new File(PROJECT_PATH + FILE_SEPARATOR + "/src/genetic/BaseRobot.java");
			File destiny = new File(GENERATED_ROBOTS_PATH + FILE_SEPARATOR + "BaseRobot.java");
			Files.copy(source.toPath(), destiny.toPath());
		} catch (IOException e) {
			System.err.println("Unable to copy BaseRobot class to Robocode path");
		}

		for (int i = 0; i < robots.length; i++) {
			createRobotJavaClass(GENERATED_ROBOTS_PATH, robots[i]);
		}
	}

	private static void createRobotJavaClass(String robotPath,
			GeneticRobot robot) {
		String path = robotPath + FILE_SEPARATOR
				+ String.format("%s.java", robot.getName());
		FileWriter robotFile;
		try {
			robotFile = new FileWriter(path);
			PrintWriter writer = new PrintWriter(robotFile);

			writer.printf(applyCodeTemplate(robot));
			robotFile.close();
		} catch (IOException e) {
			System.err.println("Unexpected Error: " + e.getMessage());
		}
	}

	private static String applyCodeTemplate(GeneticRobot robot) {
		String[] splittedTemplate = CODE_TEMPLATE.split("#");
		return splittedTemplate[0] + robot.getName() + splittedTemplate[1]
				+ robot.getTrees()[0].composite() + splittedTemplate[2]
				+ robot.getTrees()[1].composite() + splittedTemplate[3]
				+ robot.getTrees()[2].composite() + splittedTemplate[4];
	}

	private static void clearRobots() {
		File genDir = new File(GENERATED_ROBOTS_PATH);
		if (genDir.exists()) {
			for (File geneticRobotClass : genDir.listFiles()) {
				geneticRobotClass.delete();
			}
			genDir.delete();
		}
	}

	private static String generateCommonTemplate() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(
					CODE_TEMPLATE_PATH));
			String fileContent = "";
			String line = null;
			while ((line = reader.readLine()) != null) {
				fileContent += line;
				fileContent += LINE_SEPARATOR;
			}
			reader.close();
			return fileContent;
		} catch (FileNotFoundException e) {
			System.err.println("File " + CODE_TEMPLATE_PATH + " not found.");
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		System.exit(1);
		return null;
	}

	private static void populateRobots() {
		for (int i = 0; i < robots.length; i++) {
			Node[] trees = new Node[GeneticRobot.TREES];
			for (int j = 0; j < trees.length; j++) {
				trees[j] = TreeManipulatorTabajara.generateRandomTree();
			}
			robots[i] = new GeneticRobot(ROBOTS_COMMON_NAME + i, -1, trees);
		}
	}

}
