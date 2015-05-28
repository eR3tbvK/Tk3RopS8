package client;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.*;
import javax.swing.text.DefaultCaret;

import client.InGame.SendButtonListener;

public class World extends InGame implements Runnable{

	private JTextArea incoming;
	private JTextField outgoing;
	private JPanel panel;
	private Client networkStartup;
	private PlayerMob player;
	public Background background;
	private JLayeredPane layeredPane;
	private JLayeredPane keyListenerLayer;
	private ArrayList<PlayerMob> players;
	private int playerIndex;
	private UI userInterface;
	
	public void run(){
		try{
			//System.out.println("right before the while loop of the thread");
			//layeredPane.add(background,99);
			
			while(true){

				try {
					
					//System.out.println("before Sleep");
					Thread.sleep(14);
					//System.out.println("after sleep");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				panel.remove(layeredPane);
				Iterator<PlayerMob> allPlayers = players.iterator();
				PlayerMob aPlayer = null;
				while(allPlayers.hasNext()){
					aPlayer = (PlayerMob) allPlayers.next();
					//System.out.println("INTHELOOP:info.getUsername =" + info.getUsername() + " myChat.getUsername =" + myChat.getUsername());
					aPlayer.move();
				}
				background.move();

				panel.add(BorderLayout.CENTER,layeredPane);
				panel.validate();
				panel.repaint();

				try{
					for(int i=0;  i< players.size(); i++){
						if (playerIndex != i && players.get(playerIndex).getBounds().intersects(players.get(i).getBounds())){
							//System.out.println("A COLLISION HAPPENED with player " + i);
							if(players.get(playerIndex).getCross()){
								players.get(i).setKnockedOut(true);
							}
						}
					}
				}catch(IndexOutOfBoundsException ex){
					continue;
				}
			}
		}catch (NullPointerException ed){
			//System.out.println("daaaamn");
			startDrawingPanelThread();
		}
		catch(Exception ev){
			ev.printStackTrace();	
		}
	}
	
	public void startDrawingPanelThread(){
		//System.out.println("Starting Drawing Panel Thread");
		
		try{
			Thread chatThread = new Thread(new World());
			chatThread.start();
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	
	}
	
	
}
