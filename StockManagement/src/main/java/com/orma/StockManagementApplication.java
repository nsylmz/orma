package com.orma;

import java.io.File;

import com.orma.ui.StockManagementMenuLayout;
import com.vaadin.Application;
import com.vaadin.terminal.FileResource;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.LoginForm;
import com.vaadin.ui.LoginForm.LoginEvent;
import com.vaadin.ui.LoginForm.LoginListener;
import com.vaadin.ui.Window;

@SuppressWarnings("serial")
public class StockManagementApplication extends Application
{
    private Window mainWindow;
    
    private HorizontalSplitPanel horizontalSplitPanel;
    
    private GridLayout applicationLayout;
    
    @Override
    public void init() {
//    	setTheme(ChameleonTheme.THEME_NAME);
//    	setTheme(Runo.THEME_NAME);
//    	setTheme(Reindeer.THEME_NAME);
    	
    	mainWindow = new Window("Kantin Başkanlığı Programı Ana Ekran");
        setMainWindow(mainWindow);
        mainWindow.setSizeFull();
        
        String basepath = this.getContext().getBaseDirectory().getAbsolutePath();
        System.out.println(basepath);
        FileResource resource = new FileResource(new File(basepath + "\\WEB-INF\\classes\\images\\ordu.jpg"), this);
        Embedded image = new Embedded("", resource);
        image.setWidth("450");
        image.setHeight("450");
        
        applicationLayout = new GridLayout();
        applicationLayout.setSizeFull();
        applicationLayout.addComponent(image);
        applicationLayout.setComponentAlignment(image, Alignment.MIDDLE_CENTER);
        horizontalSplitPanel = new HorizontalSplitPanel();
        horizontalSplitPanel.setStyleName("small");
        horizontalSplitPanel.setSplitPosition(20, Sizeable.UNITS_PERCENTAGE);
        horizontalSplitPanel.setHeight("725px");
        horizontalSplitPanel.setWidth("100%");
        horizontalSplitPanel.setLocked(true);
        
        final GridLayout menu = new StockManagementMenuLayout(horizontalSplitPanel);
        
        LoginForm login = new LoginForm();
    	login.setPasswordCaption("Şifre");
    	login.setUsernameCaption("Kullanıcı Adı");
    	login.setLoginButtonCaption("Giriş");
    	login.addListener(new LoginListener() {

            @Override
            public void onLogin(LoginEvent event) {
                String username = event.getLoginParameter("username");
                String password = event.getLoginParameter("password");
                
                if (username.equals("orma") && password.equals("depo1234")) {
					setUser("orma");
					horizontalSplitPanel.setFirstComponent(menu);
				}
            }
        });
        
//        horizontalSplitPanel.addComponent(new StockManagementMenuLayout(horizontalSplitPanel));
        horizontalSplitPanel.addComponent(login);
        horizontalSplitPanel.addComponent(applicationLayout);
        
        mainWindow.addComponent(horizontalSplitPanel);
    }
    
}
