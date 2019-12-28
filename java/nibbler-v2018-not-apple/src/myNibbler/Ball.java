package myNibbler;

import java.awt.Color;

public class Ball {
	
	public Vector position;
	public Vector speed;
	public double radius;
	public Color  color;
	
	
	public Ball() {
		this(1.0);
	}
	
	public Ball(double radius) {
		this.position = new Vector(0, 0);
		this.speed    = new Vector(0, 0);
		this.radius   = radius;
		this.color    = Color.BLACK;
	}
}
