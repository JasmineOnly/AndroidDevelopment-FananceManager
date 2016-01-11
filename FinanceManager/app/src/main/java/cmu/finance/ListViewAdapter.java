package cmu.finance;

import java.util.ArrayList;
import java.util.HashMap;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/* Created by Pedro
 * ListViewAdapter class used to build the list view tables.
 * There is a single warning in this class. This warning is negligible since is desired
 * functionality to inflate a generic layout.
 * */
public class ListViewAdapter extends BaseAdapter{

    public ArrayList<HashMap<String, String>> list;
    Activity activity;
    TextView txtFirst;
    TextView txtSecond;
    TextView txtThird;
    TextView txtFourth;
    TextView txtFifth;
    public ListViewAdapter(Activity activity,ArrayList<HashMap<String, String>> list){
        super();
        this.activity=activity;
        this.list=list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
       return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater=activity.getLayoutInflater();
        if(convertView == null){
            convertView=inflater.inflate(R.layout.list_view_record_table, null); // null
            txtFirst=(TextView) convertView.findViewById(R.id.listViewDate);
            txtSecond=(TextView) convertView.findViewById(R.id.listViewAmount);
            txtThird=(TextView) convertView.findViewById(R.id.listViewAccount);
            txtFourth=(TextView) convertView.findViewById(R.id.listViewDescription);
            txtFifth=(TextView) convertView.findViewById(R.id.listViewExpenseType);

        }

        HashMap<String, String> map=list.get(position);
        txtFirst.setText(map.get("First"));
        txtSecond.setText(map.get("Second"));
        txtThird.setText(map.get("Third"));
        txtFourth.setText(map.get("Fourth"));
        txtFifth.setText(map.get("Fifth"));

        return convertView;
    }

}
