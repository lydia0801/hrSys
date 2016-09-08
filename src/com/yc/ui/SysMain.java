package com.yc.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import com.yc.ui.emp.AddEmp;
import com.yc.ui.emp.BaseEmpMenu;
import com.yc.ui.emp.FindEmp;

public class SysMain {

	protected Shell shell;
	public static SashForm sashForm;
	public static Composite composite;
	public static StackLayout stackLayout;
	public static Composite addEmpCom;
	public static FindEmp findEmpCom;
	public static Composite updateEmpCom;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			SysMain window = new SysMain();
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
		shell.setSize(900, 643);
		shell.setText("SWT Application");
		shell.setBackgroundMode(SWT.INHERIT_DEFAULT); 
		
		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);
		
		MenuItem mntmNewSubmenu = new MenuItem(menu, SWT.CASCADE);
		mntmNewSubmenu.setImage(SWTResourceManager.getImage(SysMain.class, "/img/hr_1.jpg"));
		mntmNewSubmenu.setText("人事管理");
		
		Menu menu_1 = new Menu(mntmNewSubmenu);
		mntmNewSubmenu.setMenu(menu_1);
		
		MenuItem mntmNewItem = new MenuItem(menu_1, SWT.NONE);
		mntmNewItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			 
//				Control[]cols =sashForm.getChildren();
//				for(Control c:cols){
//					String str =c.getToolTipText();
//					if("面板".equals(str)){
//						c.dispose();
//						BaseEmpMenu emp =new BaseEmpMenu(sashForm, SWT.NONE);
//						emp.setBounds(0, 0, 114, 650);
//					}
//				}
			}
		});
		mntmNewItem.setImage(SWTResourceManager.getImage(SysMain.class, "/img/hr_4.jpg"));
		mntmNewItem.setText("基本信息");
		
		MenuItem mntmNewItem_5 = new MenuItem(menu_1, SWT.NONE);
		mntmNewItem_5.setImage(SWTResourceManager.getImage(SysMain.class, "/img/hr_6.jpg"));
		mntmNewItem_5.setText("合同管理");
		
		MenuItem mntmNewItem_4 = new MenuItem(menu_1, SWT.NONE);
		mntmNewItem_4.setImage(SWTResourceManager.getImage(SysMain.class, "/img/hr_5.jpg"));
		mntmNewItem_4.setText("档案管理");
		
		MenuItem menuItem = new MenuItem(menu, SWT.NONE);
		menuItem.setImage(SWTResourceManager.getImage(SysMain.class, "/img/hr_5.jpg"));
		menuItem.setText("培训管理");
		
		MenuItem mntmNewItem_1 = new MenuItem(menu, SWT.NONE);
		mntmNewItem_1.setImage(SWTResourceManager.getImage(SysMain.class, "/img/hr_6.jpg"));
		mntmNewItem_1.setText("考勤管理");
		
		MenuItem mntmNewItem_2 = new MenuItem(menu, SWT.NONE);
		mntmNewItem_2.setImage(SWTResourceManager.getImage(SysMain.class, "/img/hr_8.jpg"));
		mntmNewItem_2.setText("薪资管理");
		
		MenuItem mntmNewItem_3 = new MenuItem(menu, SWT.NONE);
		mntmNewItem_3.setImage(SWTResourceManager.getImage(SysMain.class, "/img/hr_7.jpg"));
		mntmNewItem_3.setText("用户管理");
		
		MenuItem mntmNewItem_7 = new MenuItem(menu, SWT.NONE);
		mntmNewItem_7.setImage(SWTResourceManager.getImage(SysMain.class, "/img/hr_9.jpg"));
		mntmNewItem_7.setText("退出系统");
		
		sashForm = new SashForm(shell, SWT.NONE|SWT.HORIZONTAL);
		sashForm.setBounds(0, 0, 884, 585);
		
		Composite composite_1 = new Composite(sashForm, SWT.NONE);
		composite_1.setToolTipText("面板");
		
		Composite contractMenu = new Composite(composite_1, SWT.NONE);
		Button button = new Button(contractMenu, SWT.NONE);
		button.setImage(com.swtdesigner.SWTResourceManager.getImage(SysMain.class, "/img/edit_add.png"));
		button.setBounds(0, 50, 105, 22);
		button.setText("录入合同信息");
		
		Button button_1 = new Button(contractMenu, SWT.NONE);
		button_1.setImage(com.swtdesigner.SWTResourceManager.getImage(SysMain.class, "/img/search.png"));
		button_1.setBounds(0, 110, 105, 22);
		button_1.setText("查看合同信息");
		
		Button button_2 = new Button(contractMenu, SWT.NONE);
		button_2.setImage(com.swtdesigner.SWTResourceManager.getImage(SysMain.class, "/img/pencil.png"));
		button_2.setBounds(0, 176, 105, 22);
		button_2.setText("修改合同信息");
		
		composite = new Composite(sashForm, SWT.NONE);
		stackLayout = new StackLayout();
		composite.setLayout(stackLayout);
		composite.setToolTipText("面板1");
		
		addEmpCom= new AddEmp(composite, SWT.NONE);
		findEmpCom= new FindEmp(composite, SWT.NONE);
		updateEmpCom =new AddEmp(composite,SWT.NONE);
		
		Label lblNewLabel = new Label(composite, SWT.CENTER);
		lblNewLabel.setForeground(SWTResourceManager.getColor(255, 204, 255));
		lblNewLabel.setFont(SWTResourceManager.getFont("微软雅黑", 16, SWT.NORMAL));
		lblNewLabel.setBounds(34, 38, 688, 28);
		lblNewLabel.setText("欢迎进入人事管理系统");
		sashForm.setWeights(new int[] {115, 766});

	}
}
