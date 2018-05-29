package com.example.kjs11.dbproject;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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

import static com.example.kjs11.dbproject.R.id.map;


public class MapActivity extends Activity implements GoogleMap.OnInfoWindowClickListener,OnMapReadyCallback {
    TextView place_info;

    String temp_place;
    double init_lati;
    double init_longti;
    String init_place;
    String init_address;

    String place;
    String address;
    String tel;
    private GoogleMap mGoogleMap;

    double lati;
    double longti;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activty);
        place_info = findViewById(R.id.place_info);

        FragmentManager fragmentManager = getFragmentManager();
        MapFragment mapFragment = (MapFragment) fragmentManager
                .findFragmentById(map);
        mapFragment.getMapAsync(this);



        String temp_place = getIntent().getStringExtra("school");

    }


        @Override
        public void onMapReady(final GoogleMap map) {


            mGoogleMap = map;
            mGoogleMap.setOnInfoWindowClickListener(this);
            GooglePlayServicesUtil.isGooglePlayServicesAvailable(MapActivity.this);

            MarkerOptions markerOptions2 = new MarkerOptions();
            lati =  getIntent().getDoubleExtra("lati",0);
            longti = getIntent().getDoubleExtra("longti",0);
            place = getIntent().getStringExtra("place");
            address = getIntent().getStringExtra("addr");
            tel = getIntent().getStringExtra("tel");






            LatLng search_place = new LatLng(lati, longti);
            markerOptions2.icon(BitmapDescriptorFactory.fromResource(R.drawable.fsafezonemarker));
            markerOptions2.position(search_place);
            markerOptions2.title(place);
            markerOptions2.snippet(tel);
            mGoogleMap.addMarker(markerOptions2);
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(search_place));
            mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(17));
        }

        @Override
        public void onInfoWindowClick(Marker marker) {
            place_info.setText("보호소 명 : " + place + '\n' + "주소 : " + address + '\n' + "전화번호 : " + tel);
            place_info.setTextSize(15);
            String text = "tel:" + tel;
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(text));
            startActivity(intent);
       }

        public void listBtnClicked(View v) {
            Intent intent = new Intent(getApplicationContext(), ListViewActivity.class);
            startActivity(intent);
        }
      public void gpsBtnClicked(View v) {
        Intent intent = new Intent(getApplicationContext(), GpsAcitivity.class);
        startActivity(intent);
     }
    public void schoolBtnClicked(View v) {
        Intent intent = new Intent(getApplicationContext(), MapActivity2.class);
        intent.putExtra("school",temp_place);
        startActivity(intent);
    }

    protected void attachBaseContext(Context newBase){
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }


}


