package cmu.finance;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cmu.adapter.BuildRecord;
import cmu.adapter.CameraAdapter;
import cmu.adapter.DatabaseAdapter;

/**
 * Created by Pedro on 20/11/2015.
 * Fragment to add an income record
 * Warnings seen are necessary warnings given our implementations and necessities.
 */
public class RecordIncomeFragment extends Fragment{

    private static final int CAM_REQUEST = 1313;
    private LocationManager locationManager;
    private String provider;
    public static final int SHOW_LOCATION = 0;

    public RecordIncomeFragment(){

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        final View rootView = inflater.inflate(R.layout.record_income_fragment, container, false);

        // Populate spinners using db adapter

        DatabaseAdapter da = new DatabaseAdapter(getContext());
        // Populate accounts using adapter
        ArrayList<String> accountList = da.getAllAccounts();
        Spinner accountSpinner = (Spinner) rootView.findViewById(R.id.incomeAccountSpinner);
        ArrayAdapter<String> accountAdapter = new ArrayAdapter<>(getActivity(),
                R.layout.spinner_layout, R.id.txt, accountList);
        accountSpinner.setAdapter(accountAdapter);

        Button cancel = (Button) rootView.findViewById(R.id.incomeCancelButton);
        Button save = (Button) rootView.findViewById(R.id.incomeSaveButton);
        Button camera = (Button) rootView.findViewById(R.id.cameraButton);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FinanceMainPageActivity.class);
                startActivity(intent);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Get values of UI
                EditText eAmount = (EditText) getActivity().findViewById(R.id.incomeAmount);
                Spinner eAccount = (Spinner) getActivity().findViewById(R.id.incomeAccountSpinner);
                EditText eDescription = (EditText) getActivity().findViewById(R.id.incomeDescription);

                // Date is current date
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date dateO = new Date();

                try {
                    String date = dateFormat.format(dateO);
                    double amount = Double.parseDouble(eAmount.getText().toString());
                    String account = eAccount.getSelectedItem().toString();
                    String expenseType = "Income";
                    String description = eDescription.getText().toString();

                    // Validate if all values are input, if not, send a message warning the user
                    if ((account != null)) {

                        // Insert using adapter
                        BuildRecord buildRecord = new BuildRecord();
                        buildRecord.insertNewRecord(date, amount, account, expenseType, description, getContext());

                        // Toast message
                        Toast.makeText(getActivity(), "Income added", Toast.LENGTH_SHORT).show();

                        // Go back to Main activity (or previous activity, maybe)
                        Intent intent = new Intent(getActivity(), FinanceMainPageActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getActivity(), "All values should be input", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e){
                    Toast.makeText(getActivity(), "All values should be input", Toast.LENGTH_SHORT).show();
                }
            }
        });

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getContext(),
                        Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, CAM_REQUEST);
                } else {
                    Toast.makeText(getContext(), "No camera permission", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // GPS Server
        Button location = (Button) rootView.findViewById(R.id.locationButton);
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> providerList = locationManager.getProviders(true);
                if (providerList.contains(LocationManager.GPS_PROVIDER)) {
                    provider = LocationManager.GPS_PROVIDER;
                } else if (providerList.contains(LocationManager.NETWORK_PROVIDER)) {
                    provider = LocationManager.NETWORK_PROVIDER;
                } else {
                    Toast.makeText(getContext(), "No location provider to use", Toast.LENGTH_SHORT).show();
                    return ;
                }


                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }

                Location location = locationManager.getLastKnownLocation(provider);
                if (location != null) {
                    showLocation(location);
                } else{
                    Toast.makeText(getContext(), "There is no location to provide", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        if(locationManager != null){
            locationManager.removeUpdates(locationListener);
        }
    }

    private void showLocation(final Location location){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{

                    StringBuilder url = new StringBuilder();
                    url.append("http://maps.googleapis.com/maps/api/geocode/json?latlng=");
                    url.append(location.getLatitude()).append(",");
                    url.append(location.getLongitude());
                    url.append("&sensor=false");
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpGet httpGet = new HttpGet(url.toString());
                    HttpResponse httpResponse = httpClient.execute(httpGet);
                    if(httpResponse.getStatusLine().getStatusCode() == 200){
                        HttpEntity entity = httpResponse.getEntity();
                        String response = EntityUtils.toString(entity, "utf-8");
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray resultArray = jsonObject.getJSONArray("results");

                        if(resultArray.length() >0){
                            JSONObject subObject =  resultArray.getJSONObject(0);
                            String address = subObject.getString("formatted_address");
                            Message message = new Message();
                            message.what = SHOW_LOCATION;
                            message.obj = address;
                            handler.sendMessage(message);
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();




    }

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            showLocation(location);

        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {


        }
    };

    private Handler handler = new Handler() {

        public void handleMessage(Message msg){
            switch (msg.what){
                case SHOW_LOCATION:
                    String currentPosition = (String) msg.obj;
                    EditText eDescription = (EditText) getActivity().findViewById(R.id.incomeDescription);
                    String description = eDescription.getText().toString() +  "\n @ " + currentPosition;
                    eDescription.setText(description);
                    break;
                default:
                    break;

            }
        }

    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if((requestCode == CAM_REQUEST) && (resultCode == Activity.RESULT_OK)){
            // Save image on phone.
            CameraAdapter ca = new CameraAdapter();
            ca.savePhoto(getContext(), data);
        }
    }

}
