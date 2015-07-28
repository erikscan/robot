package robot;

import genetic.BaseRobot;
import java.io.File;
import robocode.ScannedRobotEvent;

public class FirstRobot extends BaseRobot {

	@Override
	public void run() {
		while (true) {
			setTurnLeft(360);
			execute();
		}
	}

	@Override
	public void onScannedRobot(ScannedRobotEvent e) {
		File dataDir = getDataDirectory();
	}
}
