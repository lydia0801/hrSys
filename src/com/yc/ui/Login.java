package com.yc.ui;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import com.yc.dao.UserInfoDAO;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;

public class Login {

	protected Shell shell;
	private Text nameText;
	private Text pwdText;
	private Combo combo ;
	private Label labelName ;
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Login window = new Login();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setBackground(SWTResourceManager.getColor(153, 204, 255));
		shell.setSize(549, 379);
		shell.setText("用户登录");
		shell.setBackgroundMode(SWT.INHERIT_DEFAULT); 
		Label lblNewLabel = new Label(shell, SWT.CENTER);
		lblNewLabel.setFont(SWTResourceManager.getFont("微软雅黑", 16, SWT.NORMAL));
		lblNewLabel.setBounds(10, 23, 513, 28);
		lblNewLabel.setText("用户登录");
		
		Label nameLabel = new Label(shell, SWT.NONE);
		nameLabel.setBounds(106, 90, 61, 17);
		nameLabel.setText("用户名：");
		
		Label lblNewLabel_2 = new Label(shell, SWT.NONE);
		lblNewLabel_2.setBounds(106, 125, 61, 17);
		lblNewLabel_2.setText("密码：");
		
		Label lblNewLabel_3 = new Label(shell, SWT.NONE);
		lblNewLabel_3.setBounds(106, 165, 61, 17);
		lblNewLabel_3.setText("用户类型：");
		combo = new Combo(shell, SWT.NONE);
		combo.setItems(new String[] {"管理员", "部门经理", "普通用户"});
		combo.setBounds(194, 157, 88, 25);
		Button btnNewButton = new Button(shell, SWT.NONE);
		//用户登录
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//从界面获取用户名，密码，用户类型
				String username = nameText.getText().trim();
				String pwd = pwdText.getText().trim();
				String roleType = combo.getText().trim();
				System.out.println(username+"---"+pwd+"---"+roleType);
				//登录
				UserInfoDAO  dao = new UserInfoDAO();
				try {
					Map<String, String>  map = dao.login(username, pwd, roleType);
					if(null!=map&&map.size()>0){
						//登录成功
						MessageBox  mg = new MessageBox(shell,SWT.NONE);
						mg.setText("信息提示");
						mg.setMessage("用户登录成功！！");
						//LogUtil.logger.info(username+"登陆成功"+new Date());
						mg.open();
						shell.dispose();
						SysMain  main = new SysMain();
						main.open();
					}else{
						MessageBox  mg = new MessageBox(shell,SWT.NONE);
						mg.setText("错误提示");
						mg.setMessage("用户登录失败！！");
						mg.open();
						//清除界面数据
						nameText.setText("");
						pwdText.setText("");
						combo.setText("");
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
		btnNewButton.setBounds(232, 232, 80, 27);
		btnNewButton.setText("登录");
		labelName = new Label(shell, SWT.NONE);
		labelName.setForeground(com.swtdesigner.SWTResourceManager.getColor(SWT.COLOR_RED));
		labelName.setBounds(389, 90, 118, 17);
		nameText = new Text(shell, SWT.BORDER);
		//失去焦点  
		nameText.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String  name =nameText.getText().trim();
				if("".equals(name)||null==name){//正则表达式
					labelName.setText("用户名不能为空");
				}else{
					labelName.setText("");
				}
			}
		});
		nameText.setBounds(194, 87, 189, 23);
		
		pwdText = new Text(shell, SWT.BORDER | SWT.PASSWORD);
		pwdText.setBounds(194, 122, 189, 23);
		
		
		
		
		

	}
}
