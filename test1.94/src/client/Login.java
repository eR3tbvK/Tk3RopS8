package client;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.*;

import javax.swing.*;


public class Login {

	private JTextField inputUsername;
	private JPanel applet;
	private Client networkStartup;
	
	JPanel mainPanel = new JPanel();
	JPanel linePanel = new JPanel();
	JPanel alertPanel = new JPanel();
	JLabel inputUsernameLabel = new JLabel("inputUsername:");	//Make userinput label 
	JLabel duplicateMsg = new JLabel("This user has been already taken. Please try a different username.");
	//JLabel duplicateMsg = new JLabel("<html><font color='red'>This user has been already taken. Please try a different username.</font></html>");

	public Login(JPanel applet){
		this.applet = applet;
	}

	public void login(){

		//applet = new JFrame("Rebirth of Martial Arts");			//Set title

		/*JPanel mainPanel = new JPanel();
		JPanel linePanel = new JPanel();
		JLabel inputUsernameLabel = new JLabel("inputUsername:");	//Make userinput label 
		JLabel duplicateMsg = new JLabel("This user has been already taken. Please try a different username.");
		*/

		inputUsername = new JTextField(10);							//Make userinput field 
		duplicateMsg.setForeground(Color.RED);						//Make duplicateMsg in RED color

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
		//alertPanel.add(duplicateMsg);
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
	
	public void duplicateUserMsg(String usr){
		// FYI - Pop-up message 
		//JOptionPane.showMessageDialog(null, "My Goodness, this is so concise");
		alertPanel.add(duplicateMsg);
		mainPanel.add(alertPanel);
		applet.validate();
		applet.repaint();
	}
	
	
	public class LoginButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent ev){
			networkStartup.LoginPageLoginButtonListener(inputUsername.getText(),applet);		//Calling LoginPageLoginButtonListener including the userinput and the applet
		}
	}
}