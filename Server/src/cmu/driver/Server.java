package cmu.driver;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import cmu.service.DefaultSocketServer;



/*
anthor: qiangwan  ECE Department, Carnegie Mellon University 
email:qiangwan@andrew.cmu.edu
 */
/**
 * 
 * This is the  Server driver
 *
 */
public class Server {
	boolean started = false;
	ServerSocket serverSocket = null;
	DefaultSocketServer clientSocket = null;
	
	public static void main(String [] args) {
		new Server().start();
	}
	
	// start the server 
	public void start() {	
		try {
			serverSocket = new ServerSocket(7777);
			started = true;
			System.out.println("The server has been started...");
		} catch (IOException e) {
			System.err.println("Could not listen on port:" + serverSocket.getLocalPort());
			System.exit(-1);
		}
		
			while(started) {
				try {
					System.out.println("The server is listenning...");
					Socket socket= serverSocket.accept();
					System.out.println("accepted socket: "+socket.getPort());
					clientSocket = new DefaultSocketServer(socket);
					clientSocket.start();
				} catch (IOException e) {
					System.err.println("Accpet fails!");
				}
			}
	}
}
