package com.yc.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.yc.util.DBHelper;

public class EmployeeDAOTest {

	public static void main(String[] args) throws SQLException, IOException {
		DBHelper db = new DBHelper();
//		File file = new File("D:\\emp_3.jpg");
//		String sql = "update employee set empImg =  ? where empId = ? ";
//		try {
//			System.out.println(db.updateImg(sql, 11, file));
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		Connection  conn= db.getConn();
		PreparedStatement  pstmt = null;
		File file = new File("D:\\emp_3.jpg");
		FileInputStream  in = new FileInputStream(file);
		String sql = "insert into test1 values(?,?)";
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, 1001);
		pstmt.setBinaryStream(2, in, (int)file.length());
		pstmt.executeUpdate();
	}
}
