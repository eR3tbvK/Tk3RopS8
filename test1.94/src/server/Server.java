package server;

import client.ServerObject;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.JPanel;



public class Server {

	ArrayList<ObjectOutputStream> clientOutputStreams;
	ArrayList<String> usernames;
	ArrayList<ServerObject> clientObjects;
	ArrayList<Integer> xCoordinates;
	ArrayList<Integer> yCoordinates;
	ObjectOutputStream outStream;
	static JPanel serverPanel;
	
	public static void main(String[] args){
		//new ChatServer().go();  //Old way of doing it.
		Server server = new Server();	//New and improved way
		server.go();
	}

	public class ClientHandler implements Runnable {
		ObjectInputStream inStream;
		public ClientHandler(Socket clientSocket){
			try{
				Socket sock = clientSocket;
				inStream = new ObjectInputStream(sock.getInputStream());
			}
			catch(Exception ex){
				ex.printStackTrace();
			}
		}
		
		//The object comes in from a client
		public void run(){
			Object o1 = null;
			try{
				while ((o1 = inStream.readUnshared()) != null){
					System.out.println(System.currentTimeMillis());
					ServerObject info = (ServerObject) o1;
					//the casted fressh new object that came in
					info.setArrayList(usernames);
					if(info.getUsername().equals("undefined")){
						tellThisGuy(clientObjects,usernames.size());
					}
					if(!info.getUsername().equals("undefined") && info.getArrayList().indexOf(info.getUsername()) < 0){
						System.out.println("New User Logged in: " + info.getUsername());

						usernames.add(info.getUsername());
						clientObjects.add(info);
						tellThisGuy(clientObjects,usernames.indexOf(info.getUsername()));
						xCoordinates.add(info.getXCoordinate());
						yCoordinates.add(info.getYCoordinate());
					}
					else if(!info.getUsername().equals("undefined") && usernames.indexOf(info.getUsername()) >= 0){
						clientObjects.set(usernames.indexOf(info.getUsername()), info);
						xCoordinates.set(usernames.indexOf(info.getUsername()),info.getXCoordinate());
						yCoordinates.set(usernames.indexOf(info.getUsername()),info.getYCoordinate());
					}
					info.setArrayList(usernames);

					tellEveryone(info);
					System.out.println(System.currentTimeMillis());
				}
			}
			catch(SocketException e){
				System.err.println("User Logged out");
				removeLoggedOutUsers();

			}
			catch(IndexOutOfBoundsException ex){
				System.err.println("IndexOutOfBoundsException caught");
			}
			catch(Exception ex){
				ex.printStackTrace();
			}
		}
	}


		public void go(){
			clientOutputStreams = new ArrayList<ObjectOutputStream>();
			usernames = new ArrayList<String>();
			clientObjects = new ArrayList<ServerObject>();
			xCoordinates = new ArrayList<Integer>();
			yCoordinates = new ArrayList<Integer>();

			try{
				@SuppressWarnings("resource")
				ServerSocket serverSock = new ServerSocket(5000);

				System.out.println("Server Up");

				while(true){
					Socket clientSocket = serverSock.accept();
		            outStream = new ObjectOutputStream(clientSocket.getOutputStream());
		            clientOutputStreams.add(outStream);
					Thread t = new Thread(new ClientHandler(clientSocket));
					t.start();
				}
			}
			catch(Exception ex){
				ex.printStackTrace();
			}
		}

		public void removeLoggedOutUsers(){
				Iterator<ObjectOutputStream> it = clientOutputStreams.iterator();
				ObjectOutputStream out = null;
				ServerObject conTest = new ServerObject();

					try{

							while(it.hasNext()){
								out = (ObjectOutputStream) it.next();
								
								conTest.setArrayList(usernames);
								conTest.setUsername(usernames.get(clientOutputStreams.indexOf(out)));
								conTest.setXCoordinate(xCoordinates.get(clientOutputStreams.indexOf(out)));
								conTest.setYCoordinate(yCoordinates.get(clientOutputStreams.indexOf(out)));
								
								try{
									synchronized(out){out.writeUnshared(conTest);}
								}catch(SocketException e){
									System.err.println("Removing terminated user from clientOutputStreams the index is:" + clientOutputStreams.indexOf(out));

									int removedIndex = clientOutputStreams.indexOf(out);

									usernames.remove(removedIndex);;
									clientObjects.remove(removedIndex);;
									xCoordinates.remove(removedIndex);;
									yCoordinates.remove(removedIndex);;
									clientOutputStreams.remove(out);

								}
								out.reset();
							}
						
					}
					catch(SocketException e){

					}
					catch(Exception e){
						e.printStackTrace();
					}
		}
		
		//The object gets sent to the ONE client it has One Job Oneee Job
		public void tellThisGuy(ArrayList<ServerObject> clientObjects, int thisInt){
			
			Iterator<ServerObject> clientObject = clientObjects.iterator();
			ObjectOutputStream thisGuy = null;
			
			thisGuy = clientOutputStreams.get(thisInt);
				try{
							
							while(clientObject.hasNext()){
								synchronized(thisGuy){	thisGuy.writeUnshared(clientObject.next());}
								thisGuy.reset();
							}
						
				}
				catch(Exception e){
					e.printStackTrace();
				}
		}
		
		//The object gets sent out to every client
		public void tellEveryone(Object one){
			Iterator<ObjectOutputStream> it = clientOutputStreams.iterator();
			ObjectOutputStream out = null;

				try{
						while(it.hasNext()){
							out = (ObjectOutputStream) it.next();
							synchronized(out){out.writeUnshared(one);}
							out.reset();
						}
				}
				catch(SocketException ex){
					System.err.println("SocketException caught");
				}
				catch(Exception ex){
					ex.printStackTrace();
				}
		}

		public void setJPanel(JPanel serverPane) {
			// TODO Auto-generated method stub
			serverPanel = serverPane;
		}
}