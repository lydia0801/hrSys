package com.yc.ui.emp;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import com.yc.dao.EmployeeDAO;
import com.yc.ui.SysMain;

public class BaseEmpMenu extends Composite {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public BaseEmpMenu(Composite parent, int style) {
		super(parent, SWT.BORDER);
		
		Label label_2 = new Label(this, SWT.NONE);
		label_2.setBounds(597, 10, 144, 144);
		
		Button button = new Button(this, SWT.NONE);
		//添加员工信息
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				SysMain.stackLayout.topControl =SysMain.addEmpCom;
				SysMain.composite.layout();
//				Control[] con =SysMain.sashForm.getChildren();
//				for(Control c:con){
//					String str =c.getToolTipText();
//					if("面板1".equals(str)){
//						c.dispose();
//						AddEmp emp =new AddEmp(SysMain.sashForm, SWT.NONE);
//						emp.setBounds(120, 0, 764, 650);
//					}
//					
//				}
			}
		});
		button.setText("录入信息");
		button.setImage(SWTResourceManager.getImage(BaseEmpMenu.class, "/img/edit_add.png"));
		button.setBounds(23, 48, 80, 27);
		
		Button button_1 = new Button(this, SWT.NONE);
		//查看信息（所有员工信息）
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				SysMain.stackLayout.topControl=SysMain.findEmpCom;
				SysMain.composite.layout();
				//查看所有信息
				EmployeeDAO dao = new EmployeeDAO();
				String sql ="select e.*,d.departname from  employee  e inner join department d on d.departid = e.departid ";
				try {
					List<Map<String,String>>  list = dao.findMoreEmp(sql, null);
					if(list.size()>0){
						for(Map<String, String> map:list){
								System.out.println(map.get("EMPID"));
							TableItem tim = new TableItem(SysMain.findEmpCom.table,SWT.NONE);
							tim.setText(new String[]{map.get("EMPID"),map.get("EMPNAME"),map.get("EMPSEX"),map.get("ADDRESS"),map.get("DEPARTNAME")});
							 
						}
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
			
				
//				Control[] con =SysMain.sashForm.getChildren();
//				for(Control c:con){
//					String str =c.getToolTipText();
//					if("面板1".equals(str)){
//						c.dispose();
//					}
//					FindEmp emp =new FindEmp(SysMain.sashForm, SWT.NONE);
//					emp.setBounds(120, 0, 764, 650);
//					//查看所有信息
//					EmployeeDAO dao = new EmployeeDAO();
//					String sql ="select e.*,d.departname from  employee  e inner join department d on d.departid = e.departid ";
//					try {
//						List<Map<String,String>>  list = dao.findMoreEmp(sql, null);
//						if(list.size()>0){
//							for(Map<String, String> map:list){
////								 for(Entry<String,String> m:map.entrySet())
////								 {
////									 System.out.println(m.getKey()+""+m.getValue());
////								 }
//									System.out.println(map.get("EMPID"));
//								TableItem tim = new TableItem(emp.table,SWT.NONE);
//								tim.setText(new String[]{map.get("EMPID"),map.get("EMPNAME"),map.get("EMPSEX"),map.get("ADDRESS"),map.get("DEPARTNAME")});
//								 
//							}
//							
//							
//							
//							
//							
//						}
//					} catch (SQLException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					} catch (IOException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}
//					
//					
//				}
			}
		});
		button_1.setText("查看信息");
		button_1.setImage(SWTResourceManager.getImage(BaseEmpMenu.class, "/img/search.png"));
		button_1.setBounds(23, 110, 80, 27);
		
		Button button_2 = new Button(this, SWT.NONE);
		//修改员工信息
		button_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				SysMain.stackLayout.topControl = SysMain.updateEmpCom;
				SysMain.composite.layout();
//				Control[] con =SysMain.sashForm.getChildren();
//				for(Control c:con){
//					String str =c.getToolTipText();
//					if("面板1".equals(str)){
//						c.dispose();
//						AddEmp emp =new AddEmp(SysMain.sashForm, SWT.NONE);
//						emp.setBounds(120, 0, 764, 650);
//						emp.titLable.setText("修改基本信息");
//					}
//				}
			}
		});
		button_2.setText("修改信息");
		button_2.setImage(SWTResourceManager.getImage(BaseEmpMenu.class, "/img/pencil.png"));
		button_2.setBounds(23, 174, 80, 27);
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
