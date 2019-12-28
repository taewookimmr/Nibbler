package kr.taewookimmr.playground;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import kr.taewookimmr.element.Nibbler;
import kr.taewookimmr.game.GameDAO;
import kr.taewookimmr.game.GameVO;

public class Playground extends JFrame implements Runnable, ActionListener{
	
	Nibbler nibbler;
	
	public Playground() {
		
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Nibbler The Slither");
		this.setLocation(100, 100);		
		
		nibbler = new Nibbler(20);
		this.add(nibbler);
		this.pack();
		
		nibbler.recordPart.end_btn. addActionListener(this);
		nibbler.recordPart.rank_btn.addActionListener(this);
		
		this.setVisible(true);
		
	}

	public static void main(String[] args) {
		Playground pg = new Playground();
		Thread thread = new Thread(pg);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		switch(e.getActionCommand()) {
		
		case "START" :
			nibbler.recordPart.end_btn.setEnabled(false);
			Thread thread = new Thread(this);
			thread.setDaemon(true);
			thread.start();
		break;
		
		case "RANK" : 
			
			GameVO gvo = new GameVO();
			gvo.setName("源��깭�슦");
			gvo.setLength(nibbler.nibblerPart.body.size());
			gvo.setTurn(nibbler.nibblerPart.turnCount);
			gvo.setApple(nibbler.nibblerPart.appleEatten);
			gvo.setDistance(nibbler.nibblerPart.distance);
			gvo.setTime(nibbler.nibblerPart.delta/1000);
			gvo.setAvg_velocity(nibbler.nibblerPart.distance/nibbler.nibblerPart.delta*1000);
			
			GameDAO.record_insert(gvo);
			
			break;
			
		}
	}
	
	@Override
	public void run() {
		

		nibbler.nibblerPart.ti = System.currentTimeMillis();
		
		while(nibbler.nibblerPart.runFlag) { 
			
			if(nibbler.nibblerPart.applePosition < 0) {nibbler.nibblerPart.createApple();}
			if(!nibbler.nibblerPart.move()) {break;}
			if(nibbler.nibblerPart.isAppleEaten())   {nibbler.nibblerPart.addUnitToBody(); nibbler.nibblerPart.applePosition = -1; nibbler.nibblerPart.appleEatten++; }
			
			nibbler.renewal_info();
			
			try {Thread.sleep(200);} catch (InterruptedException e) {e.printStackTrace();}
		}
		nibbler.recordPart.rank_btn.setEnabled(true);
		JOptionPane.showMessageDialog(null, "GAME OVER", "GAME OVER", JOptionPane.ERROR_MESSAGE);

		
	
		
	}

	
	
}
