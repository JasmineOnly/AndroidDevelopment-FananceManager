package cmu.service;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import cmu.adapter.UserManager;
import cmu.adapter.Wrapper;
import cmu.model.User;
/*
anthor: qiangwan  ECE Department, Carnegie Mellon University 
email:qiangwan@andrew.cmu.edu
 */


/**
 * 
 * The thread for server to dealing with the connected client
 *
 */

public class DefaultSocketServer extends Thread implements SocketServerInterface, SocketServerConstants{
	private Socket socket = null;
	private ObjectInputStream reader = null;
	private ObjectOutputStream writer = null;
;
	
	
	public DefaultSocketServer(Socket socket) {
		this.socket = socket;
	}
		
	public void run() {
		if(openConnection()) {
			handleSession();
			closeSession();
		}
	}

	public boolean openConnection() {
		try {
			reader = new ObjectInputStream(socket.getInputStream());
			writer = new ObjectOutputStream(socket.getOutputStream());
			System.out.println("finish getting and sending object...");
		} catch (IOException e) {
			System.err.println("unable to obtain stream to/from  " + socket.getPort());
			return false;
		}
		return true;
	}
	
	public void handleSession() {
		if(debug){
			System.out.println("Create thread dealing with customer request...");
		}
			Object request = null;
			try {
				while((request=reader.readObject())!=null) {
					int i = Integer.parseInt(request.toString());
					System.out.println(i);
					switch(i){
						case 1 :
							System.out.println("recieving upload message...");	
							upload();
							break;
						case 2 :
							System.out.println("recieving restore message...");
							restore();
							break;
						case 3:
							System.out.println("recieving logging message...");
							check();
							break;
						case 5:
							System.out.println("recieving logging message...");
							checkUserName();
							break;	
						
						case 4:
							System.out.println("receiving regestering message...");
							addUser();
							break;
					}
				}
			} catch (Exception e) {
					closeSession();
					System.out.println("a client quits");
			}
	}
	

	

	private void checkUserName() {
		DataBaseService dataBaseService = new DataBaseService();
		UserManager userManager = dataBaseService.getUserManager();
		
			try {
				User user = (User)reader.readObject();
				boolean result = userManager.checkUserName(user);
				System.out.println(result);
				if(result) {
					writer.writeObject(new Integer(1));
					System.out.println("userName exisists");
				}else {
					writer.writeObject(new Integer(0));
					dataBaseService.add(user);
					System.out.println("userName doest exisists");
				}
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}	 
	}
// check the user name 
	private void check() {
		DataBaseService dataBaseService = new DataBaseService();
		UserManager userManager = dataBaseService.getUserManager();
		
			try {
				User user = (User)reader.readObject();
				boolean result = userManager.checkUser(user);
				System.out.println(result);
				if(result) {
					writer.writeObject(new Integer(1));
					System.out.println("user exisists");
				}else {
					writer.writeObject(new Integer(0));
					System.out.println("user doest exisists");
				}
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}	 
	}
	// add user
	private void addUser() {
		DataBaseService dataBaseService = new DataBaseService();
		try {
			Object o= reader.readObject();
			User user = (User)o;
			dataBaseService.add(user);
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//restore data
	private void restore() {
		DataBaseService dataBaseService = new DataBaseService();
		Wrapper wrapper = dataBaseService.restore();
		try {
			writer.writeObject(wrapper);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	// upload data
	private void upload() {
		DataBaseService dataBaseService = new DataBaseService();
		try {
			Object o= reader.readObject();
			System.out.println(o);
			Wrapper wrapper = (Wrapper)o;
			dataBaseService.upload(wrapper);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public void closeSession() {
			try {
				reader = null;
				writer = null;
				socket.close();
			} catch (IOException e) {
				System.err.println("Error clong socket to " + socket.getPort()); 
			}
	}
}