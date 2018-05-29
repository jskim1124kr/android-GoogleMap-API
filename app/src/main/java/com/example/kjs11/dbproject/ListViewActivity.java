package com.example.kjs11.dbproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.tsengvn.typekit.TypekitContextWrapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.content.ContentValues.TAG;

/**
 * Created by kjs11 on 2017-11-23.
 */

public class ListViewActivity extends Activity {
    ListView listview  = null;
    int size = 0;
    String[] place;
    String[] address;
    String[] tel;
    String[] lati;
    String[] longti;

    String errorString = null;
    private String mJsonString;
    private static final String TAG_JSON = "webnautes";
    private static final String TAG_PLACE = "place";
    private static final String TAG_ADDRESS = "addr";
    private static final String TAG_TELNUM = "tel";
    private static final String TAG_LATITUDE = "lati";
    private static final String TAG_LONGTITUDE = "longti";
    ListViewAdapter adapter = new ListViewAdapter() ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_activity);

        // Adapter 생성

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.listview1);
        listview.setAdapter(adapter);
        GetData task = new GetData();


        task.execute("http://172.20.10.4/data.php");
        // 첫 번째 아이템 추가.

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                // get item
                ListViewItem item = (ListViewItem) parent.getItemAtPosition(position) ;
                String my_place = item.getPlace() ;
                String my_addr = item.getAddr() ;
                String my_tel = item.getTel();
                double my_lati = Double.valueOf(item.getLati()).doubleValue();
                double my_longti = Double.valueOf(item.getLongti()).doubleValue();
                Intent intent = new Intent(ListViewActivity.this, MapActivity.class);
                intent.putExtra("lati",my_lati);
                intent.putExtra("longti",my_longti);
                intent.putExtra("place",my_place);
                intent.putExtra("addr",my_addr);
                intent.putExtra("tel",my_tel);

                startActivity(intent);
                finish();

                // TODO : use item data.
            }
        }) ;

        EditText editTextFilter = (EditText)findViewById(R.id.editTextFilter) ;
        editTextFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable edit) {
                String filterText = edit.toString() ;
                ((ListViewAdapter)listview.getAdapter()).getFilter().filter(filterText) ;
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        }) ;


    }
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
            place = new String[size];
            address = new String[size];
            tel = new String[size];
            lati = new String[size];
            longti = new String[size];
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject item = jsonArray.getJSONObject(i);

                place[i] = item.getString(TAG_PLACE);
                address[i] = item.getString(TAG_ADDRESS);
                tel[i] = item.getString(TAG_TELNUM);
                lati[i] = item.getString(TAG_LATITUDE);
                longti[i] = item.getString(TAG_LONGTITUDE);
                adapter.addItem(ContextCompat.getDrawable(this, R.drawable.protector),
                        place[i], address[i], tel[i],lati[i],longti[i]);

            }
        } catch (JSONException e) {
            Log.e(TAG, "showResult : ", e);
        }
    }
    protected void attachBaseContext(Context newBase){
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }


}
