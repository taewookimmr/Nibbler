package kr.taewookimmr.game;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import kr.taewookimmr.db.DBUtil;


public class GameDAO {
	
	static public boolean record_insert(GameVO gvo) {
		boolean flag = true;

		if(flag) {
			try {
				Connection conn = DBUtil.getMysqlConnection();
				
				String sql = "insert into gvo(length, apple, turn, distance, time, avgvelocity, username) values (?,?,?,?,?,?,?)";
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, gvo.getLength());
				pstmt.setInt(2, gvo.getApple());
				pstmt.setInt(3, gvo.getTurn());
				pstmt.setInt(4, gvo.getDistance());
				pstmt.setDouble(5, gvo.getTime());
				pstmt.setDouble(6, gvo.getAvg_velocity());
				pstmt.setString(7, gvo.getName());
				pstmt.executeUpdate();
				
				JOptionPane.showMessageDialog(null, "WHAT", "WHAT", JOptionPane.INFORMATION_MESSAGE);
				
				DBUtil.close(conn);
				DBUtil.close(pstmt);
				
			} catch (SQLException e) {
				e.printStackTrace();
			} 
		}
		return flag;
	}
}
