package robot;

public enum Component {

	GET_RANDOM_NUMBER("getRandomNumber()", 0),
	GET_ENEMY_BEARING("getEnemyBearing(e)", 0),
	GET_ENEMY_DISTANCE("getEnemyDistance(e)", 0),
	GET_ENEMY_VELOCITY("getEnemyVelocity(e)", 0),
	GET_ENEMY_HEADING("getEnemyHeading(e)", 0),
	GET_ENEMY_ENERGY("getEnemyEnergy(e)", 0),
	FIRE_IF_POSITIVE("fireIfPositive", 1),
	ABS("abs", 1),
	OPPOSITE("opposite", 1),
	SIN("sin", 1),
	COS("cos", 1),
	ASIN("asin", 1),
	ACOS("acos", 1),
	ADD("add", 2),
	SUBTRACT("subtract", 2),
	MULTIPLY("multiply", 2),
	DIVIDE("divide", 2),
	IF_POSITIVE("ifPositive", 3),
	IF_GREATER("ifGreater", 4);

	private final String representation;
	private final int arity;

	Component(String representation, int arity) {
		this.representation = representation;
		this.arity = arity;
	}

	public String toString() {
		return representation;
	}

	public int getArity() {
		return arity;
	}

	public static Component[] terminals() {
		return new Component[] {GET_RANDOM_NUMBER, GET_ENEMY_BEARING, GET_ENEMY_DISTANCE,
				GET_ENEMY_VELOCITY, GET_ENEMY_HEADING, GET_ENEMY_ENERGY};
	}

	public static Component[] functions() {
		return new Component[] {FIRE_IF_POSITIVE, ABS, OPPOSITE, SIN, COS, ASIN, ACOS,
				ADD, SUBTRACT, MULTIPLY, DIVIDE, IF_POSITIVE, IF_GREATER};
	}
}