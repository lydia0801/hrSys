package com.yc.ui.emp;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import com.yc.dao.DepartmentDAO;
import com.yc.dao.EmployeeDAO;
import com.yc.ui.MessageUtil;

import org.eclipse.swt.widgets.Combo;

public class FindEmp extends Composite {
	private Text text;
	public static Table table;
	private Combo combo;
	 
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public FindEmp(final Composite parent, int style) {
		 
		super(parent, style);
		setToolTipText("面板1");
		
		Label lblNewLabel = new Label(this, SWT.NONE);
		lblNewLabel.setBounds(267, 39, 61, 17);
		lblNewLabel.setText("员工编号：");
		
		text = new Text(this, SWT.BORDER);
		text.setText("");
		text.setBounds(365, 36, 80, 23);
		
		Button btnNewButton = new Button(this, SWT.NONE);
		//根据条件查询
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				table.removeAll();
				String  departname = combo.getText().trim();
				String  empid = text.getText().trim(); 
				StringBuffer sql = new StringBuffer("select e.*,d.departname from  employee  e " +
						"inner join department d on d.departid = e.departid ");
				List<Object > params = new ArrayList<Object>();
				if(departname!=null&&!"".equals(departname)){
					sql.append(" and d.departname  = ? ");
					params.add(departname);
				}
				if(null!=empid&& !"".equals(empid)){
				       int id = Integer.parseInt(empid);
				       sql.append(" and  e.empid = ? ");
				       params.add(id);
				}
				System.out.println(sql.toString());
				 
				EmployeeDAO  dao = new EmployeeDAO();
				try {
					List<Map<String, String>> list = dao.findMoreEmp(sql.toString(), params);
					if(list.size()>0){
						for(Map<String, String> map: list){
							TableItem tim = new TableItem(table,SWT.NONE);
							tim.setText(new String[]{map.get("EMPID"),map.get("EMPNAME"),map.get("EMPSEX"),
								map.get("ADDRESS"),map.get("DEPARTNAME")});
						}
						
					}else{
						MessageUtil.promt(parent.getShell(), "温馨提示", "对不起，没有您要查询的记录");
					}
					
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setImage(SWTResourceManager.getImage(FindEmp.class, "/img/search.png"));
		btnNewButton.setBounds(512, 32, 80, 27);
		btnNewButton.setText("搜索");
		
		table = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		table.setBounds(61, 122, 563, 262);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn tableColumn = new TableColumn(table, SWT.NONE);
		tableColumn.setWidth(100);
		tableColumn.setText("员工编号");
		
		TableColumn tableColumn_1 = new TableColumn(table, SWT.NONE);
		tableColumn_1.setWidth(100);
		tableColumn_1.setText("员工姓名");
		
		TableColumn tableColumn_2 = new TableColumn(table, SWT.NONE);
		tableColumn_2.setWidth(100);
		tableColumn_2.setText("员工性别");
		
		TableColumn tableColumn_3 = new TableColumn(table, SWT.NONE);
		tableColumn_3.setWidth(158);
		tableColumn_3.setText("员工地址");
		
		TableColumn tableColumn_4 = new TableColumn(table, SWT.NONE);
		tableColumn_4.setWidth(153);
		tableColumn_4.setText("员工部门");
		
		Label label = new Label(this, SWT.NONE);
		label.setBounds(69, 39, 54, 12);
		label.setText("员工部门：");
		
		combo = new Combo(this, SWT.NONE);
		combo.setBounds(150, 36, 87, 20);
		//加载部门信息
		DepartmentDAO dao = new DepartmentDAO();
		try {
			//查看所有部门信息
			List<Map<String ,String>>  list = dao.findAll();
			for(Map<String,String> map:list){
				combo.add(map.get("DEPARTNAME"));//将部门名称加载到下啦列表中
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
