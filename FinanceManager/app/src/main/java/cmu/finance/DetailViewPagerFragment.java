package cmu.finance;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import cmu.exception.UiException;
import cmu.adapter.DatabaseAdapter;
import cmu.webservices.WebServiceHandler;

/**
 * Created by Pedro on 22/11/2015.
 * Fragment class where three fragments are populated and shown as a view pager.
 */
public class DetailViewPagerFragment extends Fragment {



    public DetailViewPagerFragment(){

    }

    public static DetailViewPagerFragment newInstance(int position){
        DetailViewPagerFragment myFrag = new DetailViewPagerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("pagePosition", position);
        myFrag.setArguments(bundle);

        return myFrag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.detail_view_pager_fragment, container, false);

        ListView list = (ListView) rootView.findViewById(R.id.detailMonthListView);

        Button upload = (Button) rootView.findViewById(R.id.uploadButton);
        Button restore = (Button) rootView.findViewById(R.id.restoreButton);

        upload.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                new MyTask1().execute();
                Toast.makeText(getContext(), "Data saved on server", Toast.LENGTH_SHORT).show();
            }
        });

        restore.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                try{
                    MyTask2 task = new MyTask2();
                    task.execute();
                    task.get(5000, TimeUnit.MILLISECONDS);
                    Toast.makeText(getContext(), "Data restored from server", Toast.LENGTH_SHORT).show();

                    // Send to Main activity
                    Intent startMain = new Intent(getContext(), FinanceMainPageActivity.class);
                    startActivity(startMain);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });

        // Get position
        int position = getArguments().getInt("pagePosition");

        // Populate Text View depending on position
        TextView title = (TextView) rootView.findViewById(R.id.detailViewPagerText);
        switch (position){
            case 0: title.setText("Yearly Report");
                break;
            case 1: title.setText("Monthly Report");
                break;
            case 2: title.setText("Daily Report");
                break;
            default: title.setText("Monthly Report");
        }

        // Populate table
        ArrayList<HashMap<String, String>> tableList;
        DatabaseAdapter da = new DatabaseAdapter(getContext());
        tableList = da.populateTableViewByPosition(position);

        try {
            ListViewAdapter adapter = new ListViewAdapter(getActivity(), tableList);
            list.setAdapter(adapter);
            // Set on click item action
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });
        } catch (Exception e){
            UiException ue = new UiException(7);
            ue.fix();
        }

        return rootView;
    }


    /* Tasks to communicate to the server */
    private class MyTask1 extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            WebServiceHandler webServiceHandler = new WebServiceHandler();
            webServiceHandler.upload(getContext());
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
        }
    }

    private class MyTask2 extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            WebServiceHandler webServiceHandler = new WebServiceHandler();
            webServiceHandler.restore(getContext());
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
        }
    }
}
