package kr.taewookimmr.game;

import java.util.Date;

import javax.swing.JPanel;

public class GameVO extends JPanel{
	
	private int length;
	private int apple;
	private int turn;
	private int distance;
	private double time;
	private double avg_velocity;
	
	private String userName;
	private Date playDate;
	private int  rank;
	
	
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public int getApple() {
		return apple;
	}
	public void setApple(int apple) {
		this.apple = apple;
	}
	public int getTurn() {
		return turn;
	}
	public void setTurn(int turn) {
		this.turn = turn;
	}
	public int getDistance() {
		return distance;
	}
	public void setDistance(int distance) {
		this.distance = distance;
	}
	public double getTime() {
		return time;
	}
	public void setTime(double time) {
		this.time = time;
	}
	public double getAvg_velocity() {
		return avg_velocity;
	}
	public void setAvg_velocity(double avg_velocity) {
		this.avg_velocity = avg_velocity;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Date getPlayDate() {
		return playDate;
	}
	public void setPlayDate(Date playDate) {
		this.playDate = playDate;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	
	
	
	
	
	
	
	
}
