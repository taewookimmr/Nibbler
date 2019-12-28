package kr.taewookimmr.element;

import java.awt.Color;

import javax.swing.JPanel;

public class Unit {
	
	public JPanel panel;
	public Color  color;
	public int	   position;
	public int    direction;
	
	public Unit() {
		panel = new JPanel();
		color = Color.BLACK;
		position = 0;
		direction = 0;
	}
}
