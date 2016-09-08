package com.yc.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.yc.util.DBHelper;

public class DepartmentDAO {

	public List<Map<String,String>>  findAll() throws SQLException, IOException{
		DBHelper  db = new DBHelper();
		String sql = "select *  from  department ";
		return db.findMultiObject(sql, null);
	}
}
