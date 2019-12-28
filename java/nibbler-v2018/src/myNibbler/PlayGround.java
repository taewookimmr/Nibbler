package myNibbler;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;


public class PlayGround extends JFrame implements KeyListener{
	

	Nibbler nib ;

	public PlayGround() {
		
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		setTitle("Nibbler Test");
		setBounds(0, 0, d.width/2, d.height/2);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		nib   = new Nibbler();
		Thread thread = new Thread(nib);
		
		add(nib);
		thread.start();

		addKeyListener(this);
		
		setVisible(true);
		
	}


//////////////////////////////////////////////////////////////// main ½ÃÀÛ
	public static void main(String[] args) {
		PlayGround test = new PlayGround();
		
	}
//////////////////////////////////////////////////////////////// main ³¡


	@Override
	public void keyPressed(KeyEvent e) {
		nib.addBall();
		double temp = 0.0;
		switch(e.getKeyCode()) {
		
		case KeyEvent.VK_RIGHT : 
			if(this.nib.ballList.get(0).speed.x == 0) {
				temp = this.nib.ballList.get(0).speed.y ;
				this.nib.ballList.get(0).speed.y = 0;
				this.nib.ballList.get(0).speed.x = temp >= 0 ? temp : -temp; 
			}
		break;
		
		
		case KeyEvent.VK_LEFT : 
			if(this.nib.ballList.get(0).speed.x == 0) {
				temp = this.nib.ballList.get(0).speed.y ;
				this.nib.ballList.get(0).speed.y = 0;
				this.nib.ballList.get(0).speed.x = temp <= 0 ? temp : -temp; 
			}
		break;
		
		case KeyEvent.VK_UP : 
			if(this.nib.ballList.get(0).speed.y == 0) {
				temp = this.nib.ballList.get(0).speed.x ;
				this.nib.ballList.get(0).speed.x = 0;
				this.nib.ballList.get(0).speed.y = temp >= 0 ? -temp : temp; 
			}
		break;
			
		case KeyEvent.VK_DOWN: 
			if(this.nib.ballList.get(0).speed.y == 0) {
				temp = this.nib.ballList.get(0).speed.x ;
				this.nib.ballList.get(0).speed.x = 0;
				this.nib.ballList.get(0).speed.y = temp <= 0 ? -temp : temp; 
			}
		break;
		
		}
		
	}


	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}






}
