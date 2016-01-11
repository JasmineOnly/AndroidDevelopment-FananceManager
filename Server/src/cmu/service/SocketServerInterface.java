package cmu.service;

/*
anthor: qiangwan  ECE Department, Carnegie Mellon University 
email:qiangwan@andrew.cmu.edu
 */
/**
 * this class provides the api for socketclient
 * 
 *
 */
public interface SocketServerInterface {
	public boolean openConnection();
	public void handleSession();
	public void closeSession();
}