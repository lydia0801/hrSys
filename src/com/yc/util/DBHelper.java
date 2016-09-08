package com.yc.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.org.apache.regexp.internal.recompile;
/**
 * 使用数据库时，涉及数据库的资源都是非常奇缺的，
 * 我们在使用的过程中务必保证我们将使用过的资源释放，
 * 供别人再次使用或者自己下次再次使用，
 * 还有，创建数据库连接时可能存在各种各样的问题导致数据库连接获取失败，
 * 这个时候你的应用应该有义务告知上一层使用者到底出现了什么问题，这样就需要一个异常传递的过程   try{}finally{}
 * @author Lydia
 *
 */
public class DBHelper {
	

	//加载静态块
	static{
		try {
			Class.forName(MyPropertities.getInstance().getProperty("driverClass"));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//获取数据库连接对象
	public Connection getConn() throws SQLException, IOException{
		Connection conn =null;
		//getConnection(String url,Properties info);
		conn=DriverManager.getConnection(MyPropertities.getInstance().getProperty("url"), MyPropertities.getInstance());
		return conn;
	}
	
	//关闭数据库连接
	public void closeAll(PreparedStatement pstmt ,Connection conn,ResultSet   rs){
		//关闭结果集
		if(rs!=null){
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		//关闭预处理对象
		if(pstmt!=null){
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		//关闭数据库连接
		if(conn!=null){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * 查询: 返回一个Map对象, 只查一个对象 如果有多个对象，则不能用这个方法 select * from 表名 where id=1;
	 */
	public List<Map<String,String>> findMultiObject(String sql,List<Object> params) throws SQLException, IOException{
		List<Map<String,String>> list  =new ArrayList<Map<String,String>>();
		Map<String, String> map =null;
		Connection conn =null;
		PreparedStatement pstmt =null;
		ResultSet rs =null;
		try {
			conn=this.getConn();
			pstmt=conn.prepareStatement(sql);
			setParams(pstmt, params);
			rs=pstmt.executeQuery();
			List<String> columnNames=this.getAllColumnNames(rs);
			while(rs.next()){
				map=new HashMap<String, String>();
				for(String cn:columnNames){
					map.put(cn, rs.getString(cn));
				}
				list.add(map);
			}
		} finally{
			this.closeAll(pstmt, conn, rs);
		}
		return list;
	}
	
	/**
	 * 查询: 返回一个Map对象, 只查一个对象 如果有多个对象，则不能用这个方法 select * from 表名 where id=1;
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	public Map<String, String> findSingleObject(String sql,List<Object> params) throws SQLException, IOException{
		Map<String,String> map = null;
		Connection conn =null;
		PreparedStatement pstmt =null;
		ResultSet rs =null;
		try{
			conn = this.getConn();
			pstmt = conn.prepareStatement(sql);
			setParams(pstmt, params);
			rs = pstmt.executeQuery();
			List<String> columnNames =getAllColumnNames(rs);
			if(rs.next()){
				map =new HashMap<String, String>();
				for(String cn:columnNames){
					map.put(cn, rs.getString(cn));
				}
			}
		}finally{
			closeAll(pstmt, conn, rs);
		}
		return map;
	}
	
	/**
	 * 增删改的操作 单条语句 sql: 语句, 有可能有?,也有可能没有 params: 参数值的集合
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	public int doUpdate(String sql,List<Object> params) throws SQLException, IOException{
		Connection conn =null;
		PreparedStatement pstmt =null;
		int result = -1;
		try{
			conn = this.getConn();
			pstmt = conn.prepareStatement(sql);
			setParams(pstmt, params);
			result = pstmt.executeUpdate();
		}finally{
			closeAll(pstmt, conn, null);
		}
		return result;
	}
	
	/**
	 * 增删改的操作 多条语句 sql: 语句, 有可能有?,也有可能没有 params: 参数值的集合
	 * @param sqls
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	public  int doUpdate(List<String> sqls,List<List<Object>> params) throws Exception{
		Connection conn =null;
		PreparedStatement pstmt =null;
		int result = -1;
		try{
			conn = this.getConn();
			conn.setAutoCommit(false);//关闭隐式事务
			if(sqls!=null&&sqls.size()>0){
				for(int i=0;i<sqls.size();i++){
					String sql =sqls.get(i);
					pstmt = conn.prepareStatement(sql);
					setParams(pstmt, params.get(i));
					result = pstmt.executeUpdate();
				}
			}
			conn.commit();//提交事务
		}catch(Exception e){
			conn.rollback();//事务回滚
			throw e;
		}finally{
			conn.setAutoCommit(true);//恢复现场
			closeAll(pstmt, conn, null);
		}
		
		return result;
	}
	
	
	
	/**
	 * 给pstmt对象设置参数的方法
	 * @param pstmt
	 * @param params
	 * @throws SQLException
	 */
	private  void setParams(PreparedStatement pstmt,List<Object> params) throws SQLException{
		if(params!=null&&params.size()>0){
			for(int i=0;i<params.size();i++){
				pstmt.setString(i+1, params.get(i).toString());
			}
		}
	}
	
	/**
	 * 从结果集中取出所有的列名，存到一个集合list中 : 技术点: jdbc2.0取元数据
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private List<String> getAllColumnNames(ResultSet rs) throws SQLException{
		List<String> columnNames =new ArrayList<String>();
		if(rs!=null){
			for(int i=0;i<rs.getMetaData().getColumnCount();i++){
				columnNames.add(rs.getMetaData().getColumnName(i+1));
			}
		}
		
		return columnNames;
	}
	
	/**
	 * 聚合函数查询
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	public double getCount(String sql,List<Object> params) throws SQLException, IOException{
		double result =0;
		Connection conn =null;
		PreparedStatement  pstmt =null;
		ResultSet  rs = null;
		conn =  this.getConn();
		try{
			pstmt = conn.prepareStatement(sql);
			setParams(pstmt, params);
			rs= pstmt.executeQuery();
			if(rs.next()){
				result = rs.getDouble(1);
			}
		}finally{
			this.closeAll(pstmt, conn, rs);
		}
		return result;
	}
	
	/**
	 * 修改图片信息
	 * @param sql
	 * @param params
	 * @return
	 * @throws FileNotFoundException
	 */
	public int updateImg(String sql,List<Object> params) throws FileNotFoundException{
		File file = new File(params.get(0).toString());
		FileInputStream  in = new FileInputStream(file);
		Connection conn =null;
		PreparedStatement  pstmt =null;
		int result=0;
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setBinaryStream(1, in,(int)file.length() );
			pstmt.setInt(2, Integer.parseInt(params.get(1).toString()));
			result = pstmt.executeUpdate();
		}catch(Exception  e1){
			
		}finally{
			this.closeAll(pstmt, conn, null);
		}
		return result;
	}
	
	/**
	 * 录入信息时上传图片
	 * @param sqls
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int loadImage(List<String> sqls,List<List<Object>>params) throws Exception{
		FileInputStream  in =null;
		Connection conn =null;
		PreparedStatement  pstmt =null;
		int result=0;
		try{
			conn =getConn();
			conn.setAutoCommit(false);
			if(sqls.size()!=2&& params.size()!=2){
				return result;
				
			}
			String sql1 =sqls.get(0);
			pstmt =conn.prepareStatement(sqls.get(0));
			setParams(pstmt, params.get(0));
			result =pstmt.executeUpdate();
			if(result==1){
				File file =new File(params.get(1).get(0).toString());
				in = new FileInputStream(file);
				pstmt = conn.prepareStatement(sqls.get(1));
				pstmt.setBinaryStream(1, in,(int)file.length() );
				pstmt.setInt(2, Integer.parseInt(params.get(1).get(1).toString()));
				result = pstmt.executeUpdate();
			}
			conn.commit();
		}catch(Exception  e1){
			conn.rollback();
			throw e1;
		}finally{
			conn.setAutoCommit(true);//恢复现场
			this.closeAll(pstmt, conn, null);
		}
		return result;
		
		 
	}
}
