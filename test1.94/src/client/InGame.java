package client;
import java.awt.BorderLayout;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.*;

public class InGame{

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
	private World world;
	


	private long startTime;
	private long URDTimeMillis;
	private long waitTime;
	private long totalTime;
	private int FPS = 30;
	private double averageFPS = 14;
	private int frameCount = 0;
	private int maxFrameCount = 14;
	private long targetTime = 1000 / FPS;

	
	public InGame(PlayerMob plyr){
		player = plyr;
		userInterface = new UI(plyr);
		layeredPane = new JLayeredPane();
		background = new Background();
		layeredPane.add(background,99);
		
		//world = new World();
	}
	
	public InGame(){
		
	}
	
	
	public void chat(JPanel panel){
		this.panel = panel;
		userInterface.chat(panel);	
		
		AddKeyListener keyListener = new AddKeyListener();
		keyListener.setPlayer(player);

		keyListenerLayer = new JLayeredPane();
		keyListenerLayer.add(keyListener, 10);
		
		panel.add(BorderLayout.CENTER, keyListenerLayer);
		keyListener.setFocusable(true);
		keyListener.requestFocusInWindow();
		
		panel.add(BorderLayout.CENTER, layeredPane);
		panel.add(BorderLayout.SOUTH, userInterface.getChatPanel());
		panel.validate();
		panel.repaint();

		
		//initialize
		networkStartup.InGameChatInitialize(userInterface.getOutgoing(),userInterface.getIncoming());

		panel.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				keyListener.setFocusable(true);
				keyListener.requestFocusInWindow();
			}
		});

		userInterface.getOutgoing().addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	networkStartup.InGameChatSendButtonListener(userInterface.getOutgoing(),userInterface.getIncoming());
        }});
		
		startDrawingPanelThread();

	}

	public void startDrawingPanelThread(){
		//System.out.println("Starting Drawing Panel Thread");
		try{
			Thread chatThread = new Thread(new DrawingPanel());
			chatThread.start();
		}
		catch(Exception ex){
			System.err.println("making the thread catch");
			ex.printStackTrace();
		}
		//world.startDrawingPanelThread();
	}

	public void drawFromServer(PlayerMob plyr){
		//System.out.println("Added player to layeredPane");
		layeredPane.add(plyr, 100);
		layeredPane.moveToBack(background);
	}

	public void removeFromServer(PlayerMob plyr){
		layeredPane.remove(plyr);
	}

	public void setPlayers(ArrayList<PlayerMob> allPlayers,int playerIndex){
		players = allPlayers;
		this.playerIndex = playerIndex;

	}
	
	public void setNetObject(Client networkStartup){
		this.networkStartup = networkStartup;
	}
	
	public class SendButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent ev){
			networkStartup.InGameChatSendButtonListener(outgoing,incoming);
		}
	}

	public class DrawingPanel implements Runnable{

		public void run(){
			try{
				//System.out.println("right before the while loop of the thread");
				//layeredPane.add(background,99);
				
				while(true){
					
					startTime = System.nanoTime();
						/*
 
					try {
						//System.out.println("before Sleep");
						Thread.sleep(14);
						//System.out.println("after sleep");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
						*/
					
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
						System.err.println("for loop index catch");
						continue;
					}
					
					delay();
					
				}
			}catch (NullPointerException ed){
				System.err.println("for loop null catch");
				startDrawingPanelThread();
			}
			catch(Exception ev){
				System.err.println("for loop catch");
				ev.printStackTrace();	
			}
		}
		
		
		public void delay(){
			
			URDTimeMillis = (System.nanoTime() - startTime) / 1000000;
			waitTime = targetTime - URDTimeMillis;
			
			try {
				
				//System.out.println("before Sleep");
				Thread.sleep(waitTime);
				//System.out.println("after sleep");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			totalTime += System.nanoTime() - startTime;
			frameCount++;
			if(frameCount == maxFrameCount){
				averageFPS = 1000.0 /((totalTime / frameCount) / 1000000);
				frameCount = 0;
				totalTime = 0;
			}
		}
		
		
	}
}