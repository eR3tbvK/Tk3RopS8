package client;
import java.awt.BorderLayout;
import java.awt.event.*;

import javax.swing.*;


public class Login {

	private JTextField inputUsername;
	private JPanel applet;
	private Client networkStartup;

	public Login(JPanel applet){
		this.applet = applet;
	}

	public void login(){

		//applet = new JFrame("Rebirth of Martial Arts");			//Set title

		JPanel mainPanel = new JPanel();
		JPanel linePanel = new JPanel();
		JLabel inputUsernameLabel = new JLabel("inputUsername:");	//Make userinput label 

		inputUsername = new JTextField(10);							//Make userinput field 

		JButton loginButton = new JButton("login"); 				//Make button
		loginButton.addActionListener(new LoginButtonListener());	//Listener for the login button

		// Listener for ENTER key
		inputUsername.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	networkStartup.LoginPageLoginButtonListener(inputUsername.getText(),applet);
            }
        });

		mainPanel.setLayout( new BoxLayout( mainPanel, BoxLayout.Y_AXIS ) );	//Set layout

		//Add the interface 
		linePanel.add(inputUsernameLabel);
		linePanel.add(inputUsername);
		linePanel.add(loginButton);
		mainPanel.add(linePanel);

		applet.add(BorderLayout.CENTER, mainPanel);
		//applet.getContentPane().remove(mainPanel); //.remove(BorderLayout.CENTER, mainPanel);


		//applet.getContentPane().add(BorderLayout.SOUTH,yo2);
		applet.setSize(600, 400);
		applet.repaint();
		applet.setVisible(true);

	}
	
	public void setNetObject(Client netObj){
		networkStartup = netObj;		//Set networkStartup to the original ChatClient object
	}
	
	public class LoginButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent ev){
			networkStartup.LoginPageLoginButtonListener(inputUsername.getText(),applet);		//Calling LoginPageLoginButtonListener including the userinput and the applet
		}
	}
}