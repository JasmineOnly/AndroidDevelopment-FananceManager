package cmu.webservices;
import cmu.exception.WebServiceException;
import cmu.model.User;
import cmu.adapter.Wrapper;

/*
author: qiangwan  ECE Department, Carnegie Mellon University
email:qiangwan@andrew.cmu.edu
 */
/**
 *
 * This is the client driver
 *
 */
public class SocketConnector {

    private  static  final String port = "128.237.202.164";
    DefaultSocketClient client = null;

    public boolean doesExist(User user) {
        int answer = 0;
        try {
            client = new DefaultSocketClient(port, 7777);
            client.openConnection();
            client.sendCMD(5);
            client.getWriter().writeObject(user);
            answer= (Integer)client.getReader().readObject();
        }catch (Exception e){
            WebServiceException we = new WebServiceException(9);
            we.fix();
        }
        return answer != 0;
    }

    public boolean check(User user) {
        int answer = 0;
        try {
            client = new DefaultSocketClient(port, 7777);
            client.openConnection();
            client.sendCMD(3);
            client.getWriter().writeObject(user);
            answer = (Integer)client.getReader().readObject();
        }catch (Exception e){
            e.printStackTrace();
        }

       return answer != 0;
    }

    public void upload(Wrapper wrapper) {
        try {
            client = new DefaultSocketClient(port, 7777);
            client.openConnection();
            client.sendCMD(1);
            client.getWriter().writeObject(wrapper);
            client.closeSession();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Wrapper restore() {
        Wrapper wrapper = null;
        try {
            client = new DefaultSocketClient(port, 7777);
            client.openConnection();
            client.sendCMD(2);
            wrapper = (Wrapper)client.getReader().readObject();
            client.closeSession();
        }catch (Exception e){
            e.printStackTrace();
        }
        return wrapper;
    }
}