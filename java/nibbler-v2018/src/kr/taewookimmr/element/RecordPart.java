package kr.taewookimmr.element;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class RecordPart extends JPanel {
	

	public JPanel button_panel;
	public JLabel length_label, time_label, apple_label, turn_label, avg_velocity_label, distance_label;
	public JButton pause_btn, end_btn, rank_btn;
	
	public RecordPart() {
		
		this.setLayout(null);
		this.setPreferredSize(new Dimension(500, 500));
		this.setSize(500, 500);

		int xset   = 10;
		int yintvl = 15;
		
		length_label = new JLabel("지렁이 길이 : ");
		length_label.setSize(400, 50);
		length_label.setLocation(xset, yintvl);
		this.add(length_label);
		
		time_label   = new JLabel("진행 시간(초)    : ");
		time_label.setSize(400, 50); 
		time_label.setLocation(xset, length_label.getLocation().y + length_label.getHeight() + yintvl);
		this.add(time_label);
		
		apple_label   = new JLabel("먹은 사과(개)    : ");
		apple_label.setSize(400, 50);
		apple_label.setLocation(xset, time_label.getLocation().y + time_label.getHeight() + yintvl);
		this.add(apple_label);
		
		turn_label   = new JLabel("꺾은 횟수    : ");
		turn_label.setSize(400, 50);
		turn_label.setLocation(xset, apple_label.getLocation().y + apple_label.getHeight() + yintvl);
		this.add(turn_label);
		
		avg_velocity_label   = new JLabel("평균 속도(칸/초)    : ");
		avg_velocity_label.setSize(400, 50);
		avg_velocity_label.setLocation(xset, turn_label.getLocation().y + turn_label.getHeight() + yintvl);
		this.add(avg_velocity_label);
		
		distance_label   = new JLabel("이동 거리(칸)   : ");
		distance_label.setSize(400, 50);
		distance_label.setLocation(xset, avg_velocity_label.getLocation().y + avg_velocity_label.getHeight() + yintvl);
		this.add(distance_label);

		// 버튼 패널 설정하기
		
		button_panel = new JPanel();
		button_panel.setSize(this.getWidth(), 100);
		button_panel.setLocation(0, this.getHeight()- 100);
		button_panel.setBorder(new LineBorder(Color.RED, 2));
		button_panel.setLayout(new GridLayout(1, 3));
		this.add(button_panel);
		this.setVisible(true);
		
		pause_btn = new JButton("PAUSE");
		button_panel.add(pause_btn);
		end_btn = new JButton("START");
		button_panel.add(end_btn);
		rank_btn = new JButton("RANK");
		button_panel.add(rank_btn);
		
		pause_btn.setEnabled(true);
		end_btn.  setEnabled(true);
		rank_btn. setEnabled(true);
		
		String fontName = "배달의민족 도현";
		
		length_label.			setFont(new Font(fontName, Font.PLAIN, 25));
		time_label.				setFont(new Font(fontName, Font.PLAIN, 25));
		apple_label.			setFont(new Font(fontName, Font.PLAIN, 25));
		turn_label.				setFont(new Font(fontName, Font.PLAIN, 25));
		avg_velocity_label.		setFont(new Font(fontName, Font.PLAIN, 25));
		distance_label.			setFont(new Font(fontName, Font.PLAIN, 25));
		pause_btn.				setFont(new Font(fontName, Font.PLAIN, 25));
		end_btn.				setFont(new Font(fontName, Font.PLAIN, 25));
		rank_btn.				setFont(new Font(fontName, Font.PLAIN, 25));
		
		
	}
	

}
