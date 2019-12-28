package myNibbler;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

public class Nibbler extends JPanel implements Runnable{
	
	public  Vector startPoint = new Vector(200, 200);
	public  double ballRadius = 20.0;
	public  Vector initialInterval;
	
	public  ArrayList<Ball> ballList = new ArrayList<>();
	public  int  numberOfBalls = 5;
	public  Vector intervals[];
	public  double speedRegulator = 1.0;

	
	public Nibbler() {
		
		initialInterval = new Vector();
		initialInterval.x = 0;
		initialInterval.y = - 2 * ballRadius;
		
	
		Ball head = new Ball();
		head.position = startPoint;
		head.radius   = ballRadius;
		head.color 	  = Color.RED;
		ballList.add(head);
		
		for(int i = 1; i < numberOfBalls; i++) {
			Ball body = new Ball(ballRadius);
			ballList.add(body);
		}
		
		
		for(int i = 1; i < ballList.size(); i++) {
			ballList.get(i).position = Vector.vectorPlus(ballList.get(i-1).position, initialInterval);
		}
		
		
		for(int i = 0; i < ballList.size(); i++) {
			ballList.get(i).speed.x = 0.0;
			ballList.get(i).speed.y = 1.0;
		}
		
		addMouseWheelListener(new MouseWheelListener() {
			
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				switch(e.getWheelRotation()) {
				case 1  :  if(speedRegulator > 0.1) speedRegulator -= 0.1;  break;
				case -1 :  speedRegulator += 0.1;  break;
				}
				
			}
		});
		
	
	}
	
	public void addBall() {
		Ball willBeAdded = new Ball();
		willBeAdded.radius = ballRadius;
		
		Ball tail = ballList.get(ballList.size()-1);
		willBeAdded.position = Vector.vectorPlus(tail.position, initialInterval);
		
		Vector dir = Vector.vectorMinus(tail.position , willBeAdded.position);
		Vector unitDir  = Vector.getUnitVector(dir);
		double distance = Vector.getDistanceBetweenTwoPoints(tail.position, willBeAdded.position);
		double k		= Vector.getAbsOfVector(tail.speed)*distance/(ballRadius*2);
		willBeAdded.speed  = Vector.scalarProduct(unitDir, Math.pow(k, 0.5));
		
		willBeAdded.color  = new Color(new Random().nextInt(2000000000));
		
		ballList.add(willBeAdded);
		
	}
	
    public void move() {
		
		for(int i = 1; i < ballList.size(); i++) {
			Vector dir = Vector.vectorMinus(ballList.get(i).position ,  ballList.get(i-1).position);
			Vector unitDir  = Vector.getUnitVector(dir);
			double distance = Vector.getDistanceBetweenTwoPoints(ballList.get(i).position, ballList.get(i-1).position);
			double k		= Vector.getAbsOfVector(ballList.get(i-1).speed)*distance/(ballRadius*2);
			ballList.get(i).speed  = Vector.scalarProduct(unitDir, Math.pow(k, 1.0));
		}

		for(int i = 0;  i < ballList.size(); i++) {
			ballList.get(i).position.x = ballList.get(i).position.x + ballList.get(i).speed.x * speedRegulator;
			ballList.get(i).position.y = ballList.get(i).position.y + ballList.get(i).speed.y * speedRegulator;
		}
		
		repaint();	
	}

	@Override
	public void paint(Graphics g) {
		
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 1440, 900);
		
		g.setColor(ballList.get(0).color);
		g.fillArc((int)(ballList.get(0).position.x-ballList.get(0).radius),
				  (int)(ballList.get(0).position.y-ballList.get(0).radius),
				  (int)(ballList.get(0).radius * 2),  (int)(ballList.get(0).radius * 2), 0, 360);
		
		for(int i = 1; i < ballList.size(); i++) {
			g.setColor(ballList.get(i).color);
			g.fillArc((int)(ballList.get(i).position.x-ballList.get(i).radius),
					  (int)(ballList.get(i).position.y-ballList.get(i).radius),
					  (int)(ballList.get(i).radius * 2),  (int)(ballList.get(i).radius * 2), 0, 360);
		}
		
	}

	@Override
	public void run() {
		while(true) {
			move();
			try {Thread.sleep(10);} catch (InterruptedException e) {e.printStackTrace();}
		}
		
	}





}
