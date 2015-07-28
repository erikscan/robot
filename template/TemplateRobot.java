package genetic;

import robocode.ScannedRobotEvent;

public class # extends BaseRobot {

	@Override
	public void run() {
		while (true) {
			turnRadarLeft(360);
		}
	}

	@Override
	public void onScannedRobot(ScannedRobotEvent e) {
		double aheadParameter = #;
		double turnRightParameter = #;
		double turnGunRightParameter = #;

		System.out.println("ahead parameter: " + aheadParameter);
		System.out.println("turnRight parameter: " + turnRightParameter);
		System.out.println("turnGunRight parameter: " + turnGunRightParameter);

		ahead(aheadParameter);
		turnRight(turnRightParameter);
		turnGunRight(turnGunRightParameter);
	}
}
