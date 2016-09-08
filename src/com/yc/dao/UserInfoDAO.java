package com.yc.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.yc.util.DBHelper;

public class UserInfoDAO {
	DBHelper db =new DBHelper();
	
	/**
	 * 用户登录
	 * @param name
	 * @param pwd
	 * @param role
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	public Map<String,String > login(String name,String pwd,String role) throws SQLException, IOException{
		List<Object> params =new ArrayList<Object>();
		String sql ="select  *  from userinfo where username =? and pwd =? and userrole=?";
		params.add(name);
		params.add(pwd);
		params.add(role);
		return db.findSingleObject(sql, params);
		 
	}
}
