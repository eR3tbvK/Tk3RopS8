package client;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;
import javax.swing.*;

public class PlayerMob extends JPanel implements Serializable {
	private static final long serialVersionUID = 2;
	private int xCoordinate;
	private int yCoordinate;

	private Boolean faceDown = false;
	private Boolean faceUp = false;
	private Boolean faceLeft = true;
	private Boolean faceRight = false;
	private Boolean cross = false;
	private Boolean knockedOut = false;

	private int xMove = 0;
	private int yMove = 0;
	private int realXMove = 0;
	private int realYMove = 0;
	private Boolean pressedLeft = false;
	private Boolean pressedRight = false;
	private Boolean pressedUp = false;
	private Boolean pressedDown = false;
	private Client networkStartup;
	private Boolean horVert;

	private BufferedImage standRight;
	private BufferedImage standLeft;
	private BufferedImage standUp;
	private BufferedImage standDown;
	private BufferedImage rCross;
	private BufferedImage lCross;
	private BufferedImage uCross;
	private BufferedImage dCross;
	private String username = "NEW";
	public String clientUsername;
	private int index;
	private ServerObject info;
	private boolean user;

	public PlayerMob(Client netStartup) {
		networkStartup = netStartup;
		try {
			standRight = ImageIO.read(new File("images/character/standRight.png"));
			standLeft = ImageIO.read(new File("images/character/standLeft.png"));
			standUp = ImageIO.read(new File("images/character/standUp.png"));
			standDown = ImageIO.read(new File("images/character/standDown.png"));
			rCross = ImageIO.read(new File("images/character/rCross.png"));
			lCross = ImageIO.read(new File("images/character/lCross.png"));
			uCross = ImageIO.read(new File("images/character/uCross.png"));
			dCross = ImageIO.read(new File("images/character/dCross.png"));
			///InputStream image = this.getClass().getResourceAsStream("images/character/stand.png");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setOpaque(false);
        this.setBounds(300, 150, 134, 150);
	}

	public void setXCoordinate(int xCoordinate){
		this.xCoordinate = xCoordinate;
	}

	public int getXCoordinate(){
		return xCoordinate;
	}

	public void setYCoordinate(int yCoordinate){
		this.yCoordinate = yCoordinate;
	}

	public int getYCoordinate(){
		return yCoordinate;
	}
	
	public void keyPressedR(){
		networkStartup.keyPressedR(true);
	}
	public void keyReleasedR(){
		networkStartup.keyPressedR(false);
	}
	
	public Boolean getCross(){
		return cross;
	}


	public Boolean getKnockedOut(){
		return knockedOut;
	}
	public void setKnockedOut(Boolean knockedOut){
		this.knockedOut = knockedOut;
	}

	public void keyPressedUp(){
		if (!pressedUp){
			yMove = -1;
			pressedUp = true;
			horVert = true;
			networkStartup.keyPressed(horVert, yMove,yCoordinate);
		}
	}

	public void keyPressedDown(){
		if (!pressedDown){
			yMove = 1;
			pressedDown = true;
			horVert = false;
			networkStartup.keyPressed(horVert, yMove,yCoordinate);
		}
	}

	public void keyPressedRight(){
		if (!pressedRight){
			xMove = 1;
			pressedRight = true;
			horVert = true;
			networkStartup.keyPressed(xMove, horVert,xCoordinate);
		}
	}

	public void keyPressedLeft(){
		if (!pressedLeft){
			xMove = -1;
			pressedLeft = true;
			horVert = false;
			networkStartup.keyPressed(xMove, horVert,xCoordinate);
		}
	}
	
	public void keyPressedEscape(){
		//System.out.println("yep");
		System.exit(0);
	}

	public void keyReleasedUp(){
		if (pressedUp  && pressedDown){
			pressedUp = false;
		}
		else if(pressedUp){
			yMove = 0;
			pressedUp = false;
			networkStartup.keyReleased(horVert, yMove,yCoordinate);
		}
		if(pressedDown){
			yMove = 1;
			networkStartup.keyReleased(horVert, yMove,yCoordinate);
		}
	}

	public void keyReleasedDown(){
		if (pressedDown && pressedUp){
			pressedDown = false;
		}
		else if(pressedDown){
			yMove = 0;
			pressedDown = false;
			networkStartup.keyReleased(horVert,yMove,yCoordinate);
		}
		if(pressedUp){
			yMove = -1;
			networkStartup.keyReleased(horVert,yMove,yCoordinate);
		}
	}

	public void keyReleasedRight(){
		if(pressedRight && pressedLeft){
			pressedRight = false;
		}
		else if(pressedRight){
			xMove = 0;
			pressedRight = false;
			networkStartup.keyReleased(xMove, horVert,xCoordinate);
		}
		if(pressedLeft){
			xMove = -1;
			networkStartup.keyReleased(xMove, horVert,xCoordinate);
		}
	}

	public void keyReleasedLeft(){

		if (pressedLeft && pressedRight){
			pressedLeft = false;
		}
		else if(pressedLeft){
			xMove = 0;
			pressedLeft = false;
			networkStartup.keyReleased(xMove, horVert,xCoordinate);
		}
		if(pressedRight){
			xMove = 1;
			networkStartup.keyReleased(xMove, horVert,xCoordinate);
		}
	}

	/*public void setThisPlayer (ServerObject clientObj, ServerObject servObj){
		if(clientObj == servObj){
			user = true;
		}else{
			user = false;
		}
	}
	*/
	public void standStill(){
		realXMove = 0;
		realYMove = 0;
	}
	
	public void readMove(ServerObject servObj, int index){
		info = servObj;
		this.index = index;
		if(!servObj.getArrayList().isEmpty()){
			this.username = servObj.getUsername();
			//Makes it so only a user with the specific username can move the circle
			if(servObj.getUsername().equals(servObj.getArrayList().get(index))){  
				realXMove = servObj.getXMove();
				realYMove = servObj.getYMove();
			}
		}

	}
	
	public void worldMove(ServerObject servObj, int index){
		if(!servObj.getArrayList().isEmpty()){
			
			//Makes it so only a user with the specific username can move the circle
			if(servObj.getUsername().equals(servObj.getArrayList().get(index))){ 
				realXMove = -servObj.getXMove();
				realYMove = -servObj.getYMove();
			}
		}
	}

	public void updateCoordinatesAndFace(ServerObject servObj){
		//to do the weird movement is here
		if(realXMove == 0 && realYMove == 0){
			xCoordinate = servObj.getXCoordinate();
		}
		if(realXMove == 0 && realYMove == 0){
			yCoordinate = servObj.getYCoordinate();
		}
		
		faceDown = servObj.getFaceDown();
		faceUp = servObj.getFaceUp();
		faceLeft = servObj.getFaceLeft();
		faceRight = servObj.getFaceRight();
		cross = servObj.getCross();

	}
	
	public void setClientServUsername(ServerObject servObj, ServerObject clientObject){
		if(!servObj.getArrayList().isEmpty()){
			clientUsername = clientObject.getUsername();
			username = servObj.getUsername();
		}
	}

	public void move(){
		xCoordinate += realXMove;
		yCoordinate += realYMove;
		
	}
	


	public void paintComponent(Graphics g){
		//g.setColor(Color.ORANGE);
		//g.fillOval(0, 0, 100, 100);
		super.paintComponent(g);
		if(knockedOut){
			//System.out.println("You just got knocked out!!!");
			xCoordinate = -50;
			yCoordinate = 200;
			knockedOut = false;
		}
		else{
			if(faceRight){
				if(cross){
					g.drawImage(rCross, 0,16, null);
				}else{
					g.drawImage(standRight,0,16, null);
				}
			}else if(faceLeft) {
				if(cross){
					g.drawImage(lCross, 0,16, null);
				}else{
					g.drawImage(standLeft,0,16, null);
				}
			}else if(faceUp) {
				if(cross){
					g.drawImage(uCross, 0,16, null);
				}else{
					g.drawImage(standUp,0,16, null);
				}
			}else if(faceDown) {
				if(cross){
					g.drawImage(dCross, 0,16, null);
				}else{
					g.drawImage(standDown,0,16, null);
				}
			}else{
				g.drawImage(standRight,0,16, null);
			}
			
			
			if(username.equals(clientUsername)){
				//this.setBounds(xCoordinate, yCoordinate, 134, 150);
				this.setBounds(300, 150, 134, 150);
				
				networkStartup.moveBackground(info, index);
			}else{
				//System.out.println(username + "is moving");
				this.setBounds(xCoordinate, yCoordinate, 134, 150);
			}
			

		}
		
		g.drawString(username,134/3 - 2*username.length(), 10);
		
	}
	
	public Rectangle getBounds() {
		return new Rectangle(xCoordinate,yCoordinate,80,80);
	}
	
}