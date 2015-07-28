package robot;

public class GeneticRobot implements Comparable<GeneticRobot> {

	public static final int TREES = 3;

	private String name;
	private double fitness;
	private Node[] trees = new Node[3];

	public GeneticRobot(String name, double fitness, Node[] trees) {
		this.name = name;
		this.fitness = fitness;
		this.trees = trees;
	}

	public GeneticRobot(GeneticRobot robot) {
		this.name = robot.getName();
		this.fitness = robot.getFitness();
		this.trees = robot.getTrees();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getFitness() {
		return fitness;
	}

	public void setFitness(double fitness) {
		this.fitness = fitness;
	}

	public Node[] getTrees() {
		return trees;
	}

	public void setTrees(Node[] trees) {
		this.trees = trees;
	}

	@Override
	public int compareTo(GeneticRobot o) {
		return new Double(fitness).compareTo(new Double(o.getFitness()));
	}

}
