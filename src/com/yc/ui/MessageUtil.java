package com.yc.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public class MessageUtil {

	public static void promt(Shell shell,String title,String content){
		MessageBox  mg =new MessageBox(shell,SWT.NONE);
		mg.setText(title);
		mg.setMessage(content);
		mg.open();
	}
}
