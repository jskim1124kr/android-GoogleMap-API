package com.example.kjs11.dbproject;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.util.ArrayList;


public class MapActivity2 extends Activity implements GoogleMap.OnInfoWindowClickListener,OnMapReadyCallback {
    TextView place_info;
    ToggleButton my_place_btn;



    String name;
    String address;
    double lati;
    double longti;

    String[] nameArr;
    String[] addrArr;
    int size;

    double[] latiArr2 ;
    double[] longArr2 ;



//data3.php/
    //http://localhost/data3.php?schoolName=

    private GoogleMap mGoogleMap2;
    String temp_data2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activty2);
        place_info = findViewById(R.id.place_info);
        my_place_btn = findViewById(R.id.my_place_btn);
        FragmentManager fragmentManager = getFragmentManager();
        MapFragment mapFragment2 = (MapFragment) fragmentManager
                .findFragmentById(R.id.map2);
        mapFragment2.getMapAsync(MapActivity2.this);
        temp_data2 = getIntent().getStringExtra("school");

    }

        @Override
        public void onMapReady(final GoogleMap map)
        {

            ArrayList<Double> surround_lati = new ArrayList<Double>();
            ArrayList<Double> surround_longti = new ArrayList<Double>();
            ArrayList<String> surround_name = new ArrayList<String>();
            ArrayList<String> surround_addr = new ArrayList<String>();

            mGoogleMap2 = map;
            mGoogleMap2.setOnInfoWindowClickListener(MapActivity2.this);
            GooglePlayServicesUtil.isGooglePlayServicesAvailable(MapActivity2.this);
            size = getIntent().getIntExtra("size",0);


            MarkerOptions markerOptions2 = new MarkerOptions();
            lati =  getIntent().getDoubleExtra("lati",0);
            longti = getIntent().getDoubleExtra("longti",0);
            name = getIntent().getStringExtra("name");
            address = getIntent().getStringExtra("addr");

            int size2 = 0;

            size2 = getIntent().getIntExtra("psize",0);
            nameArr = new String[size2];
            addrArr= new String[size2];
            latiArr2 = new double[size2];
            longArr2 = new double[size2];

            nameArr = getIntent().getStringArrayExtra("pname");
            addrArr = getIntent().getStringArrayExtra("paddr");
            latiArr2 = getIntent().getDoubleArrayExtra("plati");
            longArr2 = getIntent().getDoubleArrayExtra("plongti");

            /////
            LatLng search_place = new LatLng(lati, longti);
            markerOptions2.position(search_place);
            markerOptions2.icon(BitmapDescriptorFactory.fromResource(R.drawable.fschoolmarker));
            markerOptions2.title(name);
            markerOptions2.snippet(address);
            mGoogleMap2.addMarker(markerOptions2);
            mGoogleMap2.moveCamera(CameraUpdateFactory.newLatLng(search_place));
            mGoogleMap2.animateCamera(CameraUpdateFactory.zoomTo(15));



            double latiMin = lati - 0.007;
            double longtiMin = longti - 0.004;
            double latiMax = lati + 0.007;
            double longtiMax = longti + 0.004;
            for(int i = 0; i<size2; i++)
            {
                if((latiArr2[i] > latiMin && latiArr2[i] < latiMax) ) {
                    if ((longArr2[i] > longtiMin && longArr2[i] < longtiMax)) {
                        surround_lati.add(latiArr2[i]);
                        surround_longti.add(longArr2[i]);

                        surround_name.add(nameArr[i]);
                        surround_addr.add(addrArr[i]);
                        Log.i("Jisu", latiArr2[i] + "" + "  ,  " + longArr2[i] + "" + "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    }
                }
            }

            LatLng[] surround_place = new LatLng[surround_lati.size()];
            MarkerOptions markerOptions3 = new MarkerOptions();

            for(int i = 0; i<surround_lati.size(); i++)
            {
                surround_place[i] = new LatLng(surround_lati.get(i),surround_longti.get(i));
                markerOptions3.icon(BitmapDescriptorFactory.fromResource(R.drawable.fsafezonemarker));

                markerOptions3.position(surround_place[i]);
                markerOptions3.title(surround_name.get(i));
                markerOptions3.snippet(surround_addr.get(i));
                mGoogleMap2.addMarker(markerOptions3);
            }
        }

        @Override
        public void onInfoWindowClick(Marker marker) {
            place_info.setText("초등학교 명 : " + name + '\n' + "주소 : " + address + '\n');
            place_info.setTextSize(20);
        }


        public void listBtnClicked2(View v) {
            Intent intent = new Intent(getApplicationContext(), ListViewActivity2.class);
            intent.putExtra("school",temp_data2);
            startActivity(intent);
        }

        public void onBackPressed()
        {
            this.finish();
        }


    protected void attachBaseContext(Context newBase){
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}


