package q_learning_hierarchical;

final class Point {
	private final double x;
	private final double y;
	
	Point(double _x, double _y) {
		this.x = _x;
		this.y = _y;
	}
	
	Point() {
		this(0, 0);
	}
	
	final double getX() {
		return this.x;
	}
	
	final double getY() {
		return this.y;
	}
}