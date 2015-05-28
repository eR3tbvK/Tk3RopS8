package client;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


public class Background extends JPanel implements Serializable {

	private BufferedImage background;
	private static final long serialVersionUID = 3;
	private int xCoordinate = 400;
	private int yCoordinate = 300;
	private ServerObject servObj;
	private int realXMove;
	private int realYMove;
	
	public Background(){

		try {
			background = ImageIO.read(new File("images/Background-Dojo.png"));
			this.setOpaque(false);
			
			
			this.setVisible(false);
			//lets debug just the players for now

			this.setBounds(-400, -300, 1280, 800);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void paintComponent(Graphics g){
		g.drawImage(background, 0,0, null);
		this.setOpaque(false);
		this.setBounds(-xCoordinate, -yCoordinate, 1280, 800);
	}
	
	
	public void readMove(ServerObject servObj, int index){
		
		if(!servObj.getArrayList().isEmpty()){
			//Makes it so only a user with the specific username can move the circle
			if(servObj.getUsername().equals(servObj.getArrayList().get(index))){  
				realXMove = servObj.getXMove();
				realYMove = servObj.getYMove();
			}
		}

	}
	
	public void move(){
		xCoordinate += realXMove;
		yCoordinate += realYMove;
		
	}
}

