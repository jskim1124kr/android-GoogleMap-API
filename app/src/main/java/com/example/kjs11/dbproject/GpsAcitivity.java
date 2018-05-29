package com.example.kjs11.dbproject;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tsengvn.typekit.TypekitContextWrapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;
import static com.example.kjs11.dbproject.R.id.map;


public class GpsAcitivity extends Activity implements OnMapReadyCallback {
    ToggleButton my_place_btn;



    double my_lati;
    double my_longti;
    private GoogleMap mGoogleMap;

    int size = 0;
    String[] latiArr;
    String[] longArr;
    String[] placeArr;
    String[] telArr;
    String[] addrArr;


    ArrayList<Double> surround_lati = new ArrayList<Double>();
    ArrayList<Double> surround_longti = new ArrayList<Double>();
    ArrayList<String> surround_place = new ArrayList<String>();
    ArrayList<String> surround_addr = new ArrayList<String>();
    ArrayList<String> surround_tel = new ArrayList<String>();

    String errorString = null;
    private String mJsonString;
    private static final String TAG_JSON = "webnautes";
    private static final String TAG_PLACE = "place";
    private static final String TAG_ADDRESS = "addr";
    private static final String TAG_TELNUM = "tel";
    private static final String TAG_LATITUDE = "lati";
    private static final String TAG_LONGTITUDE = "longti";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gps_activty);
        my_place_btn = findViewById(R.id.my_place_btn);

        FragmentManager fragmentManager = getFragmentManager();
        MapFragment mapFragment = (MapFragment) fragmentManager
                .findFragmentById(map);
        mapFragment.getMapAsync(this);

        final LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        GetData task = new GetData();

        task.execute("http://172.20.10.4/data.php");

        my_place_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (my_place_btn.isChecked()) {
                        my_place_btn.setText("위치정보 수신중..");
                        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, // 등록할 위치제공자
                                100, // 통지사이의 최소 시간간격 (miliSecond)
                                1, // 통지사이의 최소 변경거리 (m)
                                mLocationListener);
                        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, // 등록할 위치제공자
                                100, // 통지사이의 최소 시간간격 (miliSecond)
                                1, // 통지사이의 최소 변경거리 (m)
                                mLocationListener);
                    } else {
                        my_place_btn.setText("위치정보 수신 불가능");
                        lm.removeUpdates(mLocationListener);  //  미수신할때는 반드시 자원해체를 해주어야 한다.
                    }
                } catch (SecurityException ex) {
                }
            }
        });
    }

    private final LocationListener mLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {

            my_longti = location.getLongitude(); //경도
            my_lati = location.getLatitude();   //위도

            MarkerOptions markerOptions = new MarkerOptions();

            Toast.makeText(GpsAcitivity.this, "현재 나의 위치입니다.", Toast.LENGTH_SHORT).show();
            LatLng my_place = new LatLng(my_lati, my_longti);
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.fmymarker));
            markerOptions.position(my_place);
            markerOptions.title("현재 나의 위치입니다.");

            Log.i("jisu", "my lat" + my_lati+"" + "  ,  " + my_longti+""+"!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

            mGoogleMap.addMarker(markerOptions);
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(my_place));
            mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(15));

            double[] d_pLati = new double[size];
            double[] d_pLongti = new double[size];

            for(int i = 0; i<size; i++)
            {
                d_pLati[i] = Double.valueOf(latiArr[i]);
                d_pLongti[i] = Double.valueOf(longArr[i]);

            }


            double latiMin = my_lati - 0.007;
            double longtiMin = my_longti - 0.004;
            double latiMax = my_lati + 0.007;
            double longtiMax = my_longti + 0.004;

            for(int i = 0; i<size; i++)
            {
                if(d_pLati[i] > latiMin && d_pLati[i] < latiMax ){
                    if(d_pLongti[i] > longtiMin && d_pLongti[i] < longtiMax){
                        surround_addr.add(addrArr[i]);
                        surround_place.add(placeArr[i]);
                        surround_tel.add(telArr[i]);
                        surround_lati.add(d_pLati[i]);
                        surround_longti.add(d_pLongti[i]);
                        Log.i("jisu", " surround : " + d_pLati[i]+"" + "  ,  " + d_pLongti[i]+""+"!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

                    }
                }

            }

            LatLng[] surround_marker = new LatLng[surround_lati.size()];
            MarkerOptions markerOptions3 = new MarkerOptions();
            if(surround_lati.isEmpty() != true && surround_longti.isEmpty() != true) {
                for (int i = 0; i < surround_lati.size(); i++) {
                    surround_marker[i] = new LatLng(surround_lati.get(i), surround_longti.get(i));
                    markerOptions3.position(surround_marker[i]);
                    markerOptions3.title(surround_place.get(i));
                    markerOptions3.snippet(surround_addr.get(i));
                    mGoogleMap.addMarker(markerOptions3);
                }
            }
        }

        public void onProviderDisabled(String provider) {
            // Disabled시
            Log.d("test", "onProviderDisabled, provider:" + provider);
        }

        public void onProviderEnabled(String provider) {
            // Enabled시
            Log.d("test", "onProviderEnabled, provider:" + provider);
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
            // 변경시
            Log.d("test", "onStatusChanged, provider:" + provider + ", status:" + status + " ,Bundle:" + extras);
        }
    };

    public class GetData extends AsyncTask<String, Void, String> {
        // ProgressDialog progressDialog;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result == null) {
            } else {
                mJsonString = result;
                setInfo();
            }
        }

        @Override
        protected String doInBackground(String... params) {

            String serverURL = params[0];
            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.connect();


                int responseStatusCode = httpURLConnection.getResponseCode();

                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                bufferedReader.close();
                return sb.toString().trim();

            } catch (Exception e) {

                errorString = e.toString();

                return null;
            }

        }
    }

    public void setInfo() {
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);
            size = jsonArray.length();
            placeArr = new String[size];
            addrArr= new String[size];
            telArr = new String[size];
            latiArr = new String[size];
            longArr = new String[size];
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject item = jsonArray.getJSONObject(i);

                placeArr[i] = item.getString(TAG_PLACE);
                addrArr[i] = item.getString(TAG_ADDRESS);
                telArr[i] = item.getString(TAG_TELNUM);
                latiArr[i] = item.getString(TAG_LATITUDE);
                longArr[i] = item.getString(TAG_LONGTITUDE);

            }
        } catch (JSONException e) {
            Log.e(TAG, "showResult : ", e);
        }
    }

        @Override
        public void onMapReady(final GoogleMap map) {
            mGoogleMap = map;
            GooglePlayServicesUtil.isGooglePlayServicesAvailable(GpsAcitivity.this);

            MarkerOptions markerOptions2 = new MarkerOptions();
//            lati =  getIntent().getDoubleExtra("lati",0);
//            longti = getIntent().getDoubleExtra("longti",0);
//            place = getIntent().getStringExtra("place");
//            address = getIntent().getStringExtra("addr");
//            tel = getIntent().getStringExtra("tel");
//            LatLng search_place = new LatLng(lati, longti);
//
//            markerOptions2.position(search_place);
//            markerOptions2.title(place);
//            markerOptions2.snippet(tel);
//            mGoogleMap.addMarker(markerOptions2);
//            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(search_place));
//            mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(17));
        }
    protected void attachBaseContext(Context newBase){
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}


