package battle;

import robocode.BattleResults;
import robocode.control.BattleSpecification;
import robocode.control.BattlefieldSpecification;
import robocode.control.RobocodeEngine;
import robocode.control.RobotSpecification;
import robocode.control.events.BattleAdaptor;
import robocode.control.events.BattleCompletedEvent;
import robocode.control.events.BattleErrorEvent;
import robot.GeneticRobot;

public class BattleRunner {

	private RobocodeEngine engine;
	private BattlefieldSpecification battlefield;
	private GeneticBattleObserver battleObserver;

	private final static int HANDICAP = 20; // TODO Adapt this value

	public static final String ROBOCODE_HOME = "/home/arnett/robocode";
	public static final String ROBOTS_PACKAGE = "genetic";

	public BattleRunner() {
		RobocodeEngine.setLogMessagesEnabled(false);
		engine = new RobocodeEngine(new java.io.File(ROBOCODE_HOME));
		battleObserver = new GeneticBattleObserver();
		engine.addBattleListener(battleObserver);
//		engine.setVisible(true);
		battlefield = new BattlefieldSpecification(800, 600);
	}

	public void runBattle(GeneticRobot[] robots, String opponent, int rounds) {
		for (GeneticRobot robot : robots) {
			String robotClassName = ROBOTS_PACKAGE + "." + robot.getName() + "*";

			RobotSpecification[] selectedBots = engine
					.getLocalRepository(robotClassName + ", " + opponent);
			BattleSpecification battleSpecification = new BattleSpecification(
					rounds, battlefield, selectedBots);
			engine.runBattle(battleSpecification, true);

			BattleResults[] results = battleObserver.getResults();
			int myBot = (results[0].getTeamLeaderName().equals(robotClassName) ? 0
					: 1);
			int opBot = (myBot == 1 ? 0 : 1);
			int botScore = results[myBot].getScore();
			int opScore = results[opBot].getScore();

			double totalScore = botScore + opScore;
			double geneticRobotFitness = (botScore + HANDICAP)
					/ (totalScore + HANDICAP);

			robot.setFitness(geneticRobotFitness);
		}
	}

	public static void main(String[] args) {
		BattleRunner runner = new BattleRunner();
		GeneticRobot geneticRobot = new GeneticRobot("GeneticRobot12", -1, null);
		runner.runBattle(new GeneticRobot[] {geneticRobot}, "sample.SpinBot", 100);
	}
}

class GeneticBattleObserver extends BattleAdaptor {

	robocode.BattleResults[] results;

	public void onBattleCompleted(BattleCompletedEvent e) {
		results = e.getIndexedResults();
	}

	public void onBattleError(BattleErrorEvent e) {
		System.out.println("Error running battle: " + e.getError());
	}

	public BattleResults[] getResults() {
		return results;
	}

}