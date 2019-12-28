package kr.taewookimmr.element;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import kr.taewookimmr.game.GameDAO;
import kr.taewookimmr.game.GameVO;

public class NibblerPart extends JPanel {
	
	public  final int RIGHT = 0;
	public  final int LEFT 	= 1;
	public  final int UP 	= 2;
	public  final int DOWN 	= 3;

	public int z; 
	public ArrayList<JPanel> coordinate;
	public ArrayList<Unit> body;
	public Unit head;
	
	public boolean runFlag   = true;
	public int applePosition = -1;
	public int appleEatten   = 0;
	public int turnCount     = 0;
	public int distance  	 = 0;
	public double ti;
	public double tn;
	public double delta;
			
	public NibblerPart(int z) {
		
		
		this.setPreferredSize(new Dimension(500, 500));
		
		this.z = z;
		// 硫붿씤�뙣�꼸�쓽 �젅�씠�븘�썐�� 洹몃━�뱶 �젅�씠�븘�썐
		this.setLayout(new GridLayout(z, z));
	

		// 洹몃━�뱶 �쐞�뿉 �럹�꼸�쓣 �삱�젮�꽌 �쐞移섎�� �굹���궡湲�
		coordinate = new ArrayList<>();
		
		for(int i = 0; i < z*z ; i++) {
			JPanel panel = new JPanel();
			panel.setBackground(Color.LIGHT_GRAY);
			panel.setBorder(new LineBorder(Color.WHITE, 1));
			this.add(panel);
			
			coordinate.add(panel);
		}
		
		// head瑜� �젙�쓽�븳�떎.
		head = new Unit();
		
		head.position = new Random().nextInt(z*z);
		head.color 	  = Color.BLUE;
		head.direction = RIGHT;
		
		head.panel 	  = coordinate.get(head.position);
		head.panel.setBackground(head.color);
		
		// body瑜� �젙�쓽�븯怨� head瑜� add�븳�떎.
		body = new ArrayList<>();
		body.add(head);
		
		// head �떎�쓬�쑝濡� 9媛쒖쓽 unit�쓣 body�뿉 �꽔�뒗�떎.
		for(int i = 1; i <= 9; i++) {
			
			int prevPosition  = body.get(i-1).position;
			int row = prevPosition / z;
			int rowMin = z * row;
			int rowMax = rowMin + z - 1;
			
			int presPosition  = 0;
			if(prevPosition != rowMin) {presPosition  = prevPosition - 1;} 
			else {presPosition  = rowMax;}
			
			Unit unit = new Unit();
			unit.position = presPosition;
			unit.color = Color.BLACK;
			unit.direction = RIGHT;
			
			unit.panel = coordinate.get(unit.position);
			unit.panel.setBackground(unit.color);
		
			body.add(unit);
		}		
	
		this.setVisible(true);	

	
	}
	
	public boolean move() {
		
		int prevHP = head.position;
		int row = prevHP / z;
		int rowMin = z * row;
		int rowMax = rowMin + z - 1;
		int col = prevHP - rowMin;
		int colMin = 0 + col;
		int colMax = z * (z-1) + col;
		int newHP  = 0;
		
		// 癒쇱� 癒몃━�쓽 �떎�쓬 �쐞移섎�� �븣�븘�궦�떎.
		switch (head.direction) {
			
			case RIGHT:
				if(prevHP >= rowMin && prevHP < rowMax) {newHP = head.position + 1;}
				else {newHP = rowMin;}
				break;
			case LEFT:
				if(prevHP > rowMin && prevHP <= rowMax) {newHP = head.position - 1;}
				else {newHP = rowMax;}
				break;
			case UP:
				if(prevHP > colMin && prevHP <= colMax) {newHP = head.position - z;}
				else {newHP = colMax;}
				break;
			case DOWN:
				if(prevHP >= colMin && prevHP < colMax) {newHP = head.position + z;}
				else {newHP = colMin;}
				break;
				
		}	
		
		// 癒몃━�쓽 �쐞移� 蹂��솕濡� 諛붿슫�뜑由щ�� �굹媛붾뒗吏� �솗�씤�븷 �닔 �엳�떎.
		int delta = newHP - prevHP;
		boolean moveYourHead = true;
		
		if(delta == 1 || delta == -1 || delta == z || delta == -z) {moveYourHead = true;}
		else {moveYourHead = false;}
		
		if(moveYourHead) {
			
			// �씠�룞 嫄곕━瑜� �늻�쟻�떆�궓�떎.
			distance++;
			
			// ��吏곸뿬�룄 �릺硫� 癒몃━遺��꽣 ��吏곸씤�떎.
			head.position = newHP;
			head.panel = coordinate.get(newHP);
			head.panel.setBackground(head.color);
		
			// 癒몃━ �떎�쓬�쓽 紐명넻�뱾�쓣 李⑤���濡� ��吏곸씤�떎.
			int beforeMePos  = prevHP;
			
			for(int i = 1; i < body.size(); i++) {
				
				int mePos = body.get(i).position;
				body.get(i).panel.setBackground(Color.LIGHT_GRAY);
				body.get(i).panel.setBorder(new LineBorder(Color.WHITE));
				
				body.get(i).position = beforeMePos;
				body.get(i).panel = coordinate.get(beforeMePos);
				body.get(i).panel.setBackground(body.get(i).color);
				
				beforeMePos = mePos;
			}	
			
			// �떎 ��吏곸��뒗�뜲 �옄湲� 紐몄뿉 �떎 �븯�쑝硫� �옄�궡�뻽�떎怨� �몴�떆�븯怨� flag�뒗 false濡� 諛붽씔�떎.
			if(touchMyBody()) {moveYourHead = false;}
		}else {
			moveYourHead = false;
		}
	
		return moveYourHead;
	}
	
    public boolean turn(int direction) {
		
		int prevHP = head.position;
		int row = prevHP / z;
		int rowMin =  z * row;
		int rowMax = rowMin +  z - 1;
		int col = prevHP - rowMin;
		int colMin = 0 + col;
		int colMax =  z * ( z-1) + col;
		int newHP = 0;
	
		switch (direction) {
			
			case LEFT:
				if(head.direction != LEFT && head.direction != RIGHT ) {
					if(prevHP > rowMin && prevHP <= rowMax) {newHP = head.position - 1;}
					else {newHP = rowMax;}
					head.direction = LEFT;
				} else {newHP = prevHP;}
			break;
			case RIGHT:
				if(head.direction != LEFT && head.direction != RIGHT ) {
					if(prevHP >= rowMin && prevHP < rowMax) {newHP = head.position + 1;}
					else {newHP = rowMin;}
					head.direction = RIGHT;
				} else {newHP = prevHP;}
			break;
			case UP:
				if(head.direction != UP && head.direction != DOWN ) {
					if(prevHP > colMin && prevHP <= colMax) {newHP = head.position - z;}
					else {newHP = colMax;}
					head.direction = UP;
				} else {newHP = prevHP;}
			break;
			case DOWN:
				if(head.direction != UP && head.direction != DOWN ) {
					if(prevHP >= colMin && prevHP < colMax) {newHP = head.position + z;}
					else {newHP = colMin;}
					head.direction = DOWN;
				} else {newHP = prevHP;}
			break;
		}
		
		// 癒몃━�쓽 �쐞移� 蹂��솕濡� 諛붿슫�뜑由щ�� �굹媛붾뒗吏� �솗�씤�븷 �닔 �엳�떎.
		int delta = newHP - prevHP;
		boolean turned = true;
		if(delta == 1 || delta == -1 || delta == z || delta == -z) {turned = true;}
		else {turned = false;}
		
		if(turned) {
			
			// �씠�룞 嫄곕━瑜� �늻�쟻�떆�궓�떎.
			distance++;
			// turn �슏�닔瑜� �늻�쟻�떆�궓�떎.
			turnCount++;
			
			// 諛붿슫�뜑由� �젣�븳�뿉 嫄몃━吏� �븡�쑝硫� 癒몃━瑜� ��吏곸씠湲� �쟾�뿉 �쟾泥섎━
			coordinate.get(prevHP).setBackground(Color.LIGHT_GRAY);;
			coordinate.get(prevHP).setBorder(new LineBorder(Color.WHITE));
			
			// 癒몃━瑜� ��吏곸씤�떎.
			head.position = newHP;
			head.panel = coordinate.get(head.position);
			head.panel.setBackground(head.color);		
			
			// 癒몃━ �떎�쓬�쓽 紐명넻�뱾�쓣 李⑤���濡� ��吏곸씤�떎.
			int beforeMePos  = prevHP;
			
			for(int i = 1; i < body.size(); i++) {
				
				int mePos = body.get(i).position;
				body.get(i).panel.setBackground(Color.LIGHT_GRAY);
				body.get(i).panel.setBorder(new LineBorder(Color.WHITE));
				
				body.get(i).position = beforeMePos;
				body.get(i).panel = coordinate.get(beforeMePos);				
				body.get(i).panel.setBackground(body.get(i).color);
				
				beforeMePos = mePos;
			}
			
			if(touchMyBody()) {
				// turn 吏곹썑 �옄湲곕じ�뿉 遺��뵬�엺 寃쎌슦�씪硫� false瑜� 諛섑솚�븳�떎.
				return false;
			}
			
			// 諛⑺뼢�궎媛� �닃�젮�꽌 �빐�떦 諛⑺뼢�쑝濡� turn �꽦怨듯븯��怨� true瑜� 諛섑솚�븳�떎.
			return true;
			
		} else if(delta == 0) {
			// 諛⑺뼢�궎 �닃�젮議뚯뼱�룄 諛⑺뼢�씠 諛붾�뚯� �븡�뒗 寃쎌슦�뒗 洹몃깷 true留� 諛섑솚�븳�떎.
			return true;
		} else {
			// 諛⑺뼢�궎媛� �닃�젮�꽌 �빐�떦 諛⑺뼢�쑝濡� turn�븷 寃쎌슦 踰쎌뿉 遺��뵬�엳寃� �릺�뼱 ��吏곸씠湲� �쟾�뿉 false瑜� 諛섑솚�븳�떎.
			return false;
		}
	}
	
    public boolean turn_with_fast_go(int direction) {
		
		int prevHP = head.position;
		int row = prevHP / z;
		int rowMin =  z * row;
		int rowMax = rowMin +  z - 1;
		int col = prevHP - rowMin;
		int colMin = 0 + col;
		int colMax =  z * ( z-1) + col;
		int newHP = 0;
	
		switch (direction) {
			
			case LEFT:
				if(head.direction != RIGHT) {
					if(prevHP > rowMin && prevHP <= rowMax) {newHP = head.position - 1;}
					else {newHP = rowMax;}
					head.direction = LEFT;
				} else {newHP = prevHP;}
			break;
			case RIGHT:
				if(head.direction != LEFT) {
					if(prevHP >= rowMin && prevHP < rowMax) {newHP = head.position + 1;}
					else {newHP = rowMin;}
					head.direction = RIGHT;
				} else {newHP = prevHP;}
			break;
			case UP:
				if(head.direction != DOWN ) {
					if(prevHP > colMin && prevHP <= colMax) {newHP = head.position - z;}
					else {newHP = colMax;}
					head.direction = UP;
				} else {newHP = prevHP;}
			break;
			case DOWN:
				if(head.direction != UP) {
					if(prevHP >= colMin && prevHP < colMax) {newHP = head.position + z;}
					else {newHP = colMin;}
					head.direction = DOWN;
				} else {newHP = prevHP;}
			break;
		}
		
		// 癒몃━�쓽 �쐞移� 蹂��솕濡� 諛붿슫�뜑由щ�� �굹媛붾뒗吏� �솗�씤�븷 �닔 �엳�떎.
		int delta = newHP - prevHP;
		boolean turned = true;
		if(delta == 1 || delta == -1 || delta == z || delta == -z) {turned = true;}
		else {turned = false;}
		
		if(turned) {
			
			// �씠�룞 嫄곕━瑜� �늻�쟻�떆�궓�떎.
			distance++;
			// turn �슏�닔瑜� �늻�쟻�떆�궓�떎.
			turnCount++;
			
			// 諛붿슫�뜑由� �젣�븳�뿉 嫄몃━吏� �븡�쑝硫� 癒몃━瑜� ��吏곸씠湲� �쟾�뿉 �쟾泥섎━
			coordinate.get(prevHP).setBackground(Color.LIGHT_GRAY);;
			coordinate.get(prevHP).setBorder(new LineBorder(Color.WHITE));
			
			// 癒몃━瑜� ��吏곸씤�떎.
			head.position = newHP;
			head.panel = coordinate.get(head.position);
			head.panel.setBackground(head.color);		
			
			// 癒몃━ �떎�쓬�쓽 紐명넻�뱾�쓣 李⑤���濡� ��吏곸씤�떎.
			int beforeMePos  = prevHP;
			
			for(int i = 1; i < body.size(); i++) {
				
				int mePos = body.get(i).position;
				body.get(i).panel.setBackground(Color.LIGHT_GRAY);
				body.get(i).panel.setBorder(new LineBorder(Color.WHITE));
				
				body.get(i).position = beforeMePos;
				body.get(i).panel = coordinate.get(beforeMePos);				
				body.get(i).panel.setBackground(body.get(i).color);
				
				beforeMePos = mePos;
			}
			
			if(touchMyBody()) {
				// turn 吏곹썑 �옄湲곕じ�뿉 遺��뵬�엺 寃쎌슦�씪硫� false瑜� 諛섑솚�븳�떎.
				return false;
			}
			
			// 諛⑺뼢�궎媛� �닃�젮�꽌 �빐�떦 諛⑺뼢�쑝濡� turn �꽦怨듯븯��怨� true瑜� 諛섑솚�븳�떎.
			return true;
			
		} else if(delta == 0) {
			// 諛⑺뼢�궎 �닃�젮議뚯뼱�룄 諛⑺뼢�씠 諛붾�뚯� �븡�뒗 寃쎌슦�뒗 洹몃깷 true留� 諛섑솚�븳�떎.
			return true;
		} else {
			// 諛⑺뼢�궎媛� �닃�젮�꽌 �빐�떦 諛⑺뼢�쑝濡� turn�븷 寃쎌슦 踰쎌뿉 遺��뵬�엳寃� �릺�뼱 ��吏곸씠湲� �쟾�뿉 false瑜� 諛섑솚�븳�떎.
			return false;
		}
		
		// �젙�긽�쟻�쑝濡� turn �뻽�쑝硫� true, 踰쎌뿉 遺��뵬�삍�쑝硫� false 諛섑솚


	}
    
    public boolean touchMyBody() {
    	
    	boolean result = false;
    	int hp = body.get(0).position;
    	
    	for(int i = 1; i < body.size(); i++) {
    		if(hp == body.get(i).position) {
    			result = true;
    			break;
    		}
    	}
    	return result;
    }
    
    public void createApple() {
    
	    	int applePosition = 0;
	    	boolean flag = true;
	    	while(flag) {
		    	applePosition = new Random().nextInt(z*z);
		    	for(Unit unit : body) {
		    		if(applePosition == unit.position) {break;}
		    		else {flag = false;}
		    	}
	    	}  	
	    	this.applePosition = applePosition;
	    	coordinate.get(applePosition).setBackground(Color.RED);
    }
    
    public boolean isAppleEaten() {
    	
    	boolean result = false;
    	for(Unit unit : body) {
    		if(unit.position == applePosition) {result = true; break;}
    		else {result = false;}
    	}
    	return result;
    
    }
    
    public void addUnitToBody() {
    	Unit unit = new Unit();
    	Unit temp = body.get(body.size()-1);
    	unit.position = temp.position;
    	unit.color    = Color.MAGENTA;
    	
    	unit.panel = coordinate.get(unit.position);
    	unit.panel.setBackground(unit.color);
    	unit.panel.setBorder(new LineBorder(Color.WHITE));
    	body.add(unit);
    }
    
  
}
