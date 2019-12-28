package kr.taewookimmr.element;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import kr.taewookimmr.game.GameDAO;
import kr.taewookimmr.game.GameVO;

public class Nibbler extends JPanel  {
	
	int z;
	public NibblerPart nibblerPart;
	public RecordPart	recordPart;

	public Nibbler(int z) {
		
		this.z = z;
		this.setLayout(new GridLayout(1, 2));
		
		nibblerPart = new NibblerPart(z);
		recordPart = new RecordPart();
		this.add(nibblerPart);
		this.add(recordPart);
		
		recordPart.pause_btn.setEnabled(false);
		recordPart.end_btn.setEnabled(true);
		recordPart.rank_btn.setEnabled(false);

		nibblerPart.setFocusable(true); // �빑�떖
		nibblerPart.addKeyListener(new KeyListener() {
			
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
			
				switch (e.getKeyCode()) {
					
				
					case KeyEvent.VK_RIGHT:
//						if(!nibblerPart.turn(nibblerPart.RIGHT)) nibblerPart.runFlag = false;
						if(!nibblerPart.turn_with_fast_go(nibblerPart.RIGHT)) nibblerPart.runFlag = false;
					break;
				
					case KeyEvent.VK_LEFT:
//						if(!nibblerPart.turn(nibblerPart.LEFT))  nibblerPart.runFlag = false;
						if(!nibblerPart.turn_with_fast_go(nibblerPart.LEFT)) nibblerPart.runFlag = false;
					break;
					
					case KeyEvent.VK_UP:
//						if(!nibblerPart.turn(nibblerPart.UP))    nibblerPart.runFlag = false;
						if(!nibblerPart.turn_with_fast_go(nibblerPart.UP)) nibblerPart.runFlag = false;
					break;
					
					case KeyEvent.VK_DOWN:
//						if(!nibblerPart.turn(nibblerPart.DOWN))  nibblerPart.runFlag = false;
						if(!nibblerPart.turn_with_fast_go(nibblerPart.DOWN)) nibblerPart.runFlag = false;
					break;
				}
				
		
			}
			
		});
	}
	
    public void renewal_info() {
    	
    	recordPart.length_label.setText("吏��쟻�씠 湲몄씠 : " + nibblerPart.body.size());
    	
    	nibblerPart.tn = System.currentTimeMillis();
    	nibblerPart.delta = nibblerPart.tn - nibblerPart.ti;
    	recordPart.time_label.setText("吏꾪뻾 �떆媛�(珥�)    : " + String.format("%.1f", nibblerPart.delta/1000));
    	
    	recordPart.apple_label.setText("癒뱀� �궗怨�(媛�)    : " + nibblerPart.appleEatten);
    	recordPart.turn_label.setText("爰얠� �슏�닔    : " + nibblerPart.turnCount);
    	recordPart.avg_velocity_label.setText("�룊洹� �냽�룄(移�/珥�)    : " + String.format("%.1f", nibblerPart.distance/nibblerPart.delta*1000));
    	recordPart.distance_label.setText("�씠�룞 嫄곕━(移�)   : " + nibblerPart.distance);
    	
    }


	




}
