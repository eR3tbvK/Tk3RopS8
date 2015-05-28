package client;
import java.io.Serializable;
import java.util.ArrayList;

public class ServerObject implements Serializable {
	private static final long serialVersionUID = 3;

	private int xCoordinate = 300;
	private int yCoordinate = 150;
	private int xMove;
	private int yMove;

	private Boolean faceDown = true;
	private Boolean faceUp = false;
	private Boolean faceLeft = false;
	private Boolean faceRight = false;
	private Boolean cross = false;

	private ArrayList<String> usernames;
	private String message;
	private String username = "undefined";
	private Boolean newPlayer = false;
	private Boolean refreshCoordinates = false;
	private Boolean garbageCollection = false;

	public void setGarbageCollection(Boolean garbageCollection){
		this.garbageCollection = garbageCollection;
	}
	public Boolean getGarbageCollection(){
		return garbageCollection;
	}
	
	public void setRefreshCoordinates(Boolean refreshCoordinates){
		this.refreshCoordinates = refreshCoordinates;
	}
	
	public Boolean getRefreshCoordinates(){
		return refreshCoordinates;
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
	
	public void setNewPlayer(Boolean newPlayer){
		this.newPlayer = newPlayer;
	}
	
	public Boolean getNewPlayer(){
		return newPlayer;
	}
	
	public void setArrayList(ArrayList<String> usernames){
		this.usernames = usernames;
		
	}
	
	public void addArrayList(){
		if(username != "undefined" && usernames.indexOf(username) < 0){
			usernames.add(username);
		}
	}
	
	public ArrayList<String> getArrayList(){
		return usernames;
	}
	
	public void setXMove(int xMove){
		this.xMove = xMove;
	}
	
	public int getXMove(){
		return xMove;
	}
	
	public void setYMove(int yMove){
		this.yMove = yMove;
	}
	
	public int getYMove(){
		return yMove;
	}
	
	public void setUsername(String username){
		this.username = username;
	}
	
	public String getUsername(){
		return username;
	}
	
	public void setMessage(String message){
		this.message = message;
	}
	public String getMessage(){
		return message;
	}
	
	public void setCross(Boolean cross){
		this.cross = cross;
	}
	public Boolean getCross(){
		return cross;
	}
	
	public void setFaceRight(Boolean faceRight){
		this.faceRight = faceRight;
	}
	public Boolean getFaceRight(){
		return faceRight;
	}
	
	public void setFaceLeft(Boolean faceLeft){
		this.faceLeft = faceLeft;
	}
	public Boolean getFaceLeft(){
		return faceLeft;
	}
	
	public void setFaceUp(Boolean faceUp){
		this.faceUp = faceUp;
	}
	public Boolean getFaceUp(){
		return faceUp;
	}
	
	public void setFaceDown(Boolean faceDown){
		this.faceDown = faceDown;
	}
	public Boolean getFaceDown(){
		return faceDown;
	}
}