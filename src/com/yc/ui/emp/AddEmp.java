package com.yc.ui.emp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Image;

import com.sun.org.apache.bcel.internal.classfile.Field;
import com.yc.dao.DepartmentDAO;
import com.yc.dao.EmployeeDAO;
import com.yc.ui.MessageUtil;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class AddEmp extends Composite {
	private Text text;
	private Text text_1;
	private Text text_2;
	private Text text_3;
	static Label titLable;
	private Label imgLabel ;
	private Combo combo;
	private Combo combo_1;
	private File file ;
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public AddEmp(final Composite parent, int style) {
		super(parent, style);
		setToolTipText("面板1");
		
		titLable = new Label(this, SWT.CENTER);
		titLable.setFont(SWTResourceManager.getFont("微软雅黑", 14, SWT.NORMAL));
		titLable.setBounds(10, 21, 742, 35);
		titLable.setText("录入员工信息");
		
		Label lblNewLabel_1 = new Label(this, SWT.NONE);
		lblNewLabel_1.setBounds(68, 103, 61, 17);
		lblNewLabel_1.setText("员工编号：");
		
		text = new Text(this, SWT.BORDER);
		text.setBounds(148, 97, 165, 23);
		
		Label lblNewLabel_2 = new Label(this, SWT.NONE);
		lblNewLabel_2.setBounds(68, 152, 61, 17);
		lblNewLabel_2.setText("员工姓名：");
		
		text_1 = new Text(this, SWT.BORDER);
		text_1.setBounds(148, 146, 165, 23);
		
		Label lblNewLabel_3 = new Label(this, SWT.NONE);
		lblNewLabel_3.setBounds(68, 202, 61, 17);
		lblNewLabel_3.setText("部门：");
		
		 combo = new Combo(this, SWT.NONE);
		combo.setBounds(148, 188, 88, 25);
		//加载部门信息
		DepartmentDAO dao = new DepartmentDAO();
		try {
			//查看所有部门信息
			List<Map<String ,String>>  list = dao.findAll();
			for(Map<String,String> map:list){
				combo.add(map.get("DEPARTID")+"-"+map.get("DEPARTNAME"));//将部门名称加载到下啦列表中
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Label lblNewLabel_4 = new Label(this, SWT.NONE);
		lblNewLabel_4.setBounds(68, 254, 61, 17);
		lblNewLabel_4.setText("性别：");
		
		Label lblNewLabel_5 = new Label(this, SWT.NONE);
		lblNewLabel_5.setBounds(68, 303, 61, 17);
		lblNewLabel_5.setText("家庭住址：");
		
		text_2 = new Text(this, SWT.BORDER);
		text_2.setBounds(148, 297, 412, 23);
		
		imgLabel = new Label(this, SWT.CENTER);
		
		imgLabel.setBounds(378, 90, 182, 139);
		imgLabel.setText("相片");
		
		text_3 = new Text(this, SWT.BORDER);
		text_3.setText("选择上传图片");
		text_3.addMouseListener(new MouseAdapter() {
			/* (non-Javadoc)
			 * @see org.eclipse.swt.events.MouseAdapter#mouseDown(org.eclipse.swt.events.MouseEvent)
			 */
			@Override
			public void mouseDown(MouseEvent e) {
				FileDialog  fd = new FileDialog(parent.getShell(),SWT.SINGLE);
				fd.setText("请选择您要上传的图片");
				fd.setFilterPath("SystemRoot");
				fd.setFilterExtensions(new String[]{"*.jpg","*.png","*.*"});
				String selected =fd.open();
				if(selected ==null){
					return;
				}
				file = new File(selected);
				FileInputStream in =null;
				try {
					in = new FileInputStream(file);
					Image image = new Image(Display.getDefault(),in);
					//将图片设置到label中
					imgLabel.setImage(image);
					text_3.setText(selected);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				//imgLabel.setImage()
			}
		});
		text_3.setBounds(377, 254, 375, 23);
		
		Button btnNewButton_2 = new Button(this, SWT.NONE);
		//录入员工信息
		btnNewButton_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//建议员工编号自动生成
				String  empid = text.getText();
				String empname = text_1.getText();
				String departID =combo.getText().trim().split("-")[0];
				String sex = combo_1.getText().trim();
				String address = text_2.getText().trim();
				String path = text_3.getText().trim();
				List<List<Object>> params = new ArrayList<List<Object>>();
				List<Object> param1 = new ArrayList<Object>();
				param1.add(empid);
				param1.add(empname);
				param1.add(sex);
				param1.add(address);
				param1.add(departID);
				params.add(param1);
				List<Object> param2 = new ArrayList<Object>();
				param2.add( path);
				param2.add(empid);
				params.add(param2);
				EmployeeDAO  dao =  new EmployeeDAO();
				try {
					if(dao.addEmployee(params)==1){
						MessageUtil.promt(parent.getShell(), "温馨提示", "信息录入成功！！");
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				
			}
		});
		btnNewButton_2.setImage(SWTResourceManager.getImage(AddEmp.class, "/img/edit_add.png"));
		btnNewButton_2.setBounds(286, 396, 80, 27);
		btnNewButton_2.setText("录入");
		
		 combo_1 = new Combo(this, SWT.NONE);
		combo_1.setItems(new String[] {"男", "女"});
		combo_1.setToolTipText("");
		combo_1.setBounds(149, 251, 87, 20);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	 
	 
}
