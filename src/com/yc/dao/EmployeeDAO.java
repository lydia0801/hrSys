package com.yc.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.yc.util.DBHelper;


public class EmployeeDAO {

	DBHelper db =new DBHelper();
 
	/**
	 * 查询多条
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	public List<Map<String, String>>  findMoreEmp(String sql,List<Object>params) throws SQLException, IOException{
		return db.findMultiObject(sql, params);
	}
	
	/**
	 * 查询单条
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	public Map<String, String> findSingleEmp(String sql,List<Object>params) throws SQLException, IOException{
		return db.findSingleObject(sql, params);
	}
	
	/**
	 * 添加员工信息
	 * @param sqls
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int addEmployee(List<List<Object>>params) throws Exception{
		List<String> sqls = new ArrayList<String>();
		String sql = "insert into employee (empid,empName,empsex,address,departid) values(?,?,?,?,?)";
		String sql1="update employee set empimg = ?  where  empid = ?";
		sqls.add(sql);
		sqls.add(sql1);
		return db.loadImage(sqls, params);
	}
}
