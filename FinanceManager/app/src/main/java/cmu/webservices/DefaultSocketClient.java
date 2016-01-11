package cmu.webservices;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import cmu.exception.WebServiceException;

/*
anthor: qiangwan  ECE Department, Carnegie Mellon University 
email:qiangwan@andrew.cmu.edu
 */
/**
 * 
 * This class is used to interact with server
 *
 */
public class DefaultSocketClient implements SocketClientInterface, SocketClientConstants {
	private Socket socket = null;
	private ObjectInputStream reader = null;
	private ObjectOutputStream writer = null;

	private String strHost;
	private int iPort;
	
	public DefaultSocketClient(String strHost, int iPort) {
		this.strHost = strHost;
		this.iPort = iPort;
	}

	public boolean openConnection() {
		try {
			if(debug) {
				System.out.println("try establish connection with " + strHost + ":" + iPort);
			}
			socket = new Socket(strHost, iPort);
			System.out.println("finish connecting to server " + socket.getPort());
			writer = new ObjectOutputStream(socket.getOutputStream());
			reader = new ObjectInputStream(socket.getInputStream());
			System.out.println("finish getting and sending object...");
		} catch (IOException e) {
			WebServiceException we = new WebServiceException(9);
			we.fix();
			e.printStackTrace();
			System.err.println("unable to obtain stream to/from  " + strHost);
			return false;
		}
		return true;
	}
	


	public void sendCMD(Object cmd) {
		try {
			writer.writeObject(cmd);
			writer.flush();
		} catch (IOException e) {
			System.err.println("error sending cmd to server!!");
			WebServiceException we = new WebServiceException(9);
			we.fix();
		}
	}
	
	public void closeSession() {
		try {
			reader = null;
			writer = null;
			socket.close();
			System.out.println("client socket closed");
		} catch (IOException e) {
			System.err.println("Error clong socket to " + strHost);
			WebServiceException we = new WebServiceException(9);
			we.fix();
		}
	}

	//getters and setters

	public ObjectInputStream getReader() {
		return reader;
	}

	public ObjectOutputStream getWriter() {
		return writer;
	}

}
