package concept.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

public class StudentMain {//생성자 밖으로 빼도 상관 없음
	StudentMain() {
		String url = "jdbc:mysql://localhost:3306/bbs?serverTimezone=UTC";
		String id = "root";
		String password = "root";
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String menu = "1. 학생 등록\n"
				+ "2. 학생 수정\n"
				+ "3. 학생 삭제\n"
				+ "4. 학생 검색\n"
				+ "5. 이름 순 조회\n"
				+ "6. 점수 순 조회\n"
				+ "7. 종료 하기\n";
		String select; //메뉴 선택용
		String name; // 이름 입력용
		String tel; // 연락처 입력용
		String sKr, sEn, sMa;//국영수 입력용(String)
		int kr, en, ma;//국영수 입력용(int)
		double avg;
		String sql;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, id, password);
			while (true) {
				select = JOptionPane.showInputDialog(menu);
				if(select.equals("1")) {
					sql = "INSERT INTO student VALUES(st_seq.NEXTVAL, ?,?,?,?,?,?,SYSDATE)";
					name = JOptionPane.showInputDialog("새 학생 이름");
					tel = JOptionPane.showInputDialog("새 학생 연락처");
					sKr = JOptionPane.showInputDialog(name +"의 국어 점수");
					sEn = JOptionPane.showInputDialog(name +"의 영어 점수");
					sMa = JOptionPane.showInputDialog(name +"의 수학 점수");
					kr = Integer.parseInt(sKr);
					en = Integer.parseInt(sEn);
					ma = Integer.parseInt(sMa);
					avg = (kr+en+ma)/3.0;
					
					ps = con.prepareStatement(sql);
					ps.setString(1, name);
					ps.setString(2, tel);
					ps.setInt(3, kr);
					ps.setInt(4, en);
					ps.setInt(5, ma);
					ps.setDouble(6, avg);
					if (ps.executeUpdate() > 0) {
						JOptionPane.showMessageDialog(null, "등록 완료!");
					}else {
						JOptionPane.showMessageDialog(null, "등록 실패...");
					}
					ps.close();
					
					//이름, 연락처, 국영수, 평균 계산, SQL 실행, 결과 보여줌
				}else if(select.equals("2")) {
					String oldName;//수정 전 이름
					sql = "UPDATE student SET st_name = ?, st_tel = ?, st_kr = ?, st_en = ?, st_ma = ?, st_avg = ?"
							+ "WHERE st_name = ?";
					oldName = JOptionPane.showInputDialog("수정할 학생의 이름");
					name = JOptionPane.showInputDialog(oldName+"의 새 이름");
					tel = JOptionPane.showInputDialog(name+"의 연락처");
					sKr = JOptionPane.showInputDialog(name+"의 국어 점수");
					sEn = JOptionPane.showInputDialog(name+"의 영어 점수");
					sMa = JOptionPane.showInputDialog(name+"의 수학 점수");
					kr = Integer.parseInt(sKr);
					en = Integer.parseInt(sEn);
					ma = Integer.parseInt(sMa);
					ps = con.prepareStatement(sql);
					ps.setString(1, name);
					ps.setString(2, tel);
					ps.setInt(3, kr);
					ps.setInt(4, en);
					ps.setInt(5, ma);
					ps.setDouble(6, (kr+en+ma)/3.0);
					ps.setString(7, oldName);
					if (ps.executeUpdate() > 0) {
						JOptionPane.showMessageDialog(null, "학생 수정 완료!");
					}else {
						JOptionPane.showMessageDialog(null, "수정할 학생이 없습니다.");
					}
					
				}else if(select.equals("3")) {
					sql = "DELETE FROM student WHERE st_name = ?";
					name = JOptionPane.showInputDialog("삭제할 학생 이름");
					ps = con.prepareStatement(sql);
					ps.setString(1, name);
					if (ps.executeUpdate() > 0) {
						JOptionPane.showMessageDialog(null, "학생 삭제 완료!");
					}else {
						JOptionPane.showMessageDialog(null, "삭제할 학생이 없습니다.");
					}
					ps.close();
				}else if(select.equals("4")) {
					sql = "SELECT * FROM student WHERE st_name = ?";
					name = JOptionPane.showInputDialog("검색할 학생 이름");
					ps = con.prepareStatement(sql);
					ps.setString(1, name);
					rs = ps.executeQuery();
					if (rs.next()) {
						name = rs.getString("st_name");
						tel = rs.getString("st_tel");
						kr = rs.getInt("st_kr");
						en = rs.getInt("st_en");
						ma = rs.getInt("st_ma");
						avg = rs.getDouble("st_avg");
						int no = rs.getInt("st_no");
						String regdate = rs.getString("st_regdate");
						String message = "이름 : "+name+"\n"+"국/영/수 : "+kr+","+en+","+ma+"\n"
								+ "평균 : " + avg + "\n"
								+ "학번 : " + no + "\n"
								+ "등록일자 : " + regdate + "\n"
								+ "연락처 : "+ tel;
						JOptionPane.showMessageDialog(null, message);
					} else {
						JOptionPane.showMessageDialog(null, "미등록 학생");
					}
							
				}else if(select.equals("5")) {
					sql = "SELECT * FROM student ORDER BY st_name ASC";
					String message = "----이름 순 조회----\n";
					ps = con.prepareStatement(sql);
					rs = ps.executeQuery();
					int n = 1;
					while (rs.next()) {
						name = rs.getString("st_name");
						tel = rs.getString("st_tel");
						kr = rs.getInt("st_kr");
						en = rs.getInt("st_en");
						ma = rs.getInt("st_ma");
						avg = rs.getDouble("st_avg");
						int no = rs.getInt("st_no");
						String regdate = rs.getString("st_regdate");
						message += "이름 : "+name+"\n"+"국/영/수 : "+kr+","+en+","+ma+"\n"
								+ "평균 : " + avg + "\n"
								+ "학번 : " + no + "\n"
								+ "등록일자 : " + regdate + "\n"
								+ "연락처 : "+ tel+"\n"
								+ "-----------------\n";
					}
					JOptionPane.showMessageDialog(null, message);
					
				}else if(select.equals("6")) {
					sql = "SELECT * FROM student ORDER BY st_avg DESC";
					String message = "----점수 순 조회----\n";
					ps = con.prepareStatement(sql);
					rs = ps.executeQuery();
					int n = 1;
					while (rs.next()) {
						name = rs.getString("st_name");
						tel = rs.getString("st_tel");
						kr = rs.getInt("st_kr");
						en = rs.getInt("st_en");
						ma = rs.getInt("st_ma");
						avg = rs.getDouble("st_avg");
						int no = rs.getInt("st_no");
						String regdate = rs.getString("st_regdate");
						message +=  "--------" + n + "등--------\n"
								+ "이름 : "+name+"\n"+"국/영/수 : "+kr+","+en+","+ma+"\n"
								+ "평균 : " + avg + "\n"
								+ "학번 : " + no + "\n"
								+ "등록일자 : " + regdate + "\n"
								+ "연락처 : "+ tel+"\n";
						n++;
					}
					JOptionPane.showMessageDialog(null, message);
				}else if(select.equals("7")) {
					JOptionPane.showMessageDialog(null, "프로그램을 종료합니다.");
					break;
				}else {
					JOptionPane.showMessageDialog(null, "잘못 입력하셨습니다.");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(ps != null) ps.close();
				if(con != null) con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args) {
		new StudentMain();
	}
}
