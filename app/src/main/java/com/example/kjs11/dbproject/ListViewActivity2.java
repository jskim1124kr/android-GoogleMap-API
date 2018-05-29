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
 * Created by kjs11 on 2017-11-27.
 */
public class ListViewActivity2 extends Activity {
    ListView listview  = null;
    ListViewAdapter2 adapter = new ListViewAdapter2() ;

    EditText init;
    int size = 0;
    int size2 = 0;
    String[] sname;
    String[] saddr;
    String[] sLati;
    String[] sLongti;

    String[] pname;
    String[] paddr;
    String[] pLati;
    String[] pLongti;

    String destination ;

    String errorString = null;
    private String mJsonString;
    private static final String TAG_JSON = "webnautes";
    private static final String TAG_NAME = "name";
    private static final String TAG_ADDRESS = "addr";
    private static final String TAG_LATITUDE = "lati";
    private static final String TAG_LONGTITUDE = "longti";
    private static final String TAG_PLACE = "place";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_activity2);

        destination = getIntent().getStringExtra("school");
        init = findViewById(R.id.editTextFilter);
        init.setText(destination);
        listview = (ListView) findViewById(R.id.listview2);
        listview.setAdapter(adapter);
        GetData task = new GetData();
        task.execute("http://172.20.10.4/s_data.php");

        GetData2 task2 = new GetData2();

        task2.execute("http://172.20.10.4/data.php");

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView parent, View v, int position, long id) {
                    ListViewItem2 item = (ListViewItem2) parent.getItemAtPosition(position) ;
                    String my_name = item.getName() ;
                    String my_addr = item.getAddr() ;
                    double my_lati = Double.valueOf(item.getLati()).doubleValue();
                    double my_longti = Double.valueOf(item.getLongti()).doubleValue();
                    Intent intent = new Intent(ListViewActivity2.this, MapActivity2.class);
                    intent.putExtra("lati",my_lati);
                    intent.putExtra("longti",my_longti);
                    intent.putExtra("name",my_name);
                    intent.putExtra("addr",my_addr);

                    double[] d_pLati = new double[size2];
                    double[] d_pLongti = new double[size2];

                    for(int i = 0; i<size2; i++)
                    {
                        d_pLati[i] = Double.valueOf(pLati[i]);
                        d_pLongti[i] = Double.valueOf(pLongti[i]);

                    }

                    intent.putExtra("psize",size2);
                    intent.putExtra("pname",pname);
                    intent.putExtra("paddr",pname);
                    intent.putExtra("plati",d_pLati);
                    intent.putExtra("plongti",d_pLongti);

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
                ((ListViewAdapter2)listview.getAdapter()).getFilter().filter(filterText) ;
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
            sname = new String[size];
            saddr = new String[size];
            sLati = new String[size];
            sLongti = new String[size];
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject item = jsonArray.getJSONObject(i);

                sname[i] = item.getString(TAG_NAME);
                saddr[i] = item.getString(TAG_ADDRESS);
                sLati[i] = item.getString(TAG_LATITUDE);
                sLongti[i] = item.getString(TAG_LONGTITUDE);
                adapter.addItem(ContextCompat.getDrawable(this, R.drawable.protector),
                        sname[i], saddr[i],sLati[i],sLongti[i]);

            }
        } catch (JSONException e) {
            Log.e(TAG, "showResult : ", e);
        }
    }


    public class GetData2 extends AsyncTask<String, Void, String> {


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
                setInfo2();
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


        public void setInfo2() {
            try {
                JSONObject jsonObject = new JSONObject(mJsonString);
                JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);
                size2 = jsonArray.length();

                pname = new String[size];
                paddr = new String[size];
                pLati = new String[size];
                pLongti = new String[size];
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject item = jsonArray.getJSONObject(i);

                    pname[i] = item.getString(TAG_PLACE);
                    paddr[i] = item.getString(TAG_ADDRESS);
                    pLati[i] = item.getString(TAG_LATITUDE);
                    pLongti[i] = item.getString(TAG_LONGTITUDE);
                }

            } catch (JSONException e) {
                Log.e(TAG, "showResult : ", e);
            }
        }
    }

    protected void attachBaseContext(Context newBase){
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

}