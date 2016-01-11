package cmu.webservices;

import android.content.Context;

import cmu.adapter.DatabaseAdapter;
import cmu.model.User;
import cmu.adapter.Wrapper;

/**
 * Created by Pedro on 11/11/2015.
 * This is the webservice class that will help transfer data
 * between the client device and server.
 */
public class WebServiceHandler {

    public boolean doesExist(User user) {
        return new SocketConnector().doesExist(user);
    }
    public boolean check(User user) {
        return new SocketConnector().check(user);
    }
    public void upload(Context context){
        DatabaseAdapter da = new DatabaseAdapter(context);
        Wrapper wrapper = da.upload();
        new SocketConnector().upload(wrapper);
    }

    public void restore(Context context){
        SocketConnector socketConnector = new SocketConnector();
        Wrapper wrapper = socketConnector.restore();
        DatabaseAdapter da = new DatabaseAdapter(context);
        da.restore(wrapper);
    }
}
