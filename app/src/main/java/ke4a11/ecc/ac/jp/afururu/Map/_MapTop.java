package ke4a11.ecc.ac.jp.afururu.Map;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.maps.MapController;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import ke4a11.ecc.ac.jp.afururu.R;
import ke4a11.ecc.ac.jp.afururu.TopActivity;
import ke4a11.ecc.ac.jp.afururu.Money._MoneyTop;

public class _MapTop extends Fragment {


    private static View view;
    private static GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private MapView mview;
    MapController MapCtrl;
    //    GoogleMap mMap;
    private static final int MENU_A = 0;
    private static final int MENU_B = 1;
    private static final int MENU_c = 2;
    public static String posinfo = "";
    public static String info_A = "";
    public static String info_B = "";
    public static ArrayList<LatLng> markerPoints;
    public static MarkerOptions options;
    public ProgressDialog progressDialog;
    // public String travelMode = "driving";//default
    LocationManager locationManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


//        if(netWorkCheck(getActivity())){
//
//        }else{
//            view = inflater.inflate(R.layout.default_map, container, false);
//            return view;
//        }
//        ここあしたいじる
//
        try{
//            ここ
            if(netWorkCheck(getActivity())){

            }else{
                view = inflater.inflate(R.layout.default_map, container, false);
                return view;
            }
//            ここまで

            view = inflater.inflate(R.layout.fragment_map_top, container, false);

        }catch(Exception ex){

           // Log.d("IllegalArgumentException", ".IllegalArgumentException");
        }

//
        //プログレス
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("検索中です......");
        progressDialog.hide();


        //初期化
        markerPoints = new ArrayList<LatLng>();
        SupportMapFragment mapfragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fragment_map1);
        mMap = mapfragment.getMap();
//        setUpMapIfNeeded();

        //初期位置
        LatLng location = new LatLng(34.802556297454004, 135.53884506225586);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 17));

        if (mMap != null) {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setZoomControlsEnabled(true);//拡大縮小ボタン表示
            //クリックリスナー
            mMap.setOnMapClickListener(new OnMapClickListener() {
                @Override
                public void onMapClick(LatLng point) {
                    //３度目クリックでスタート地点を再設定
                    if (markerPoints.size() > 1) {
                        markerPoints.clear();
                        mMap.clear();
                    }
                    markerPoints.add(point);
                    options = new MarkerOptions();
                    options.position(point);
                    if (markerPoints.size() == 1) {
                        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.sss1));
                        options.title("Start");
                    } else if (markerPoints.size() == 2) {
                        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.ggg2));
                        options.title("Goal");
                    }
                    mMap.addMarker(options);
                    mMap.setOnMarkerClickListener(new OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(Marker marker) {
                            // TODO Auto-generated method stub
                            String title = marker.getTitle();
                            if (title.equals("Start")) {
                                marker.setSnippet(info_A);
                            } else if (title.equals("Goal")) {
                                marker.setSnippet(info_B);
                            }
                            return false;
                        }
                    });
                    if (markerPoints.size() >= 2) {
                        //ルート検索
                        routeSearch();
                    }
                }
            });
        }
        return view;
    }


    private void routeSearch() {
        progressDialog.show();
        LatLng origin = markerPoints.get(0);
        LatLng dest = markerPoints.get(1);
        String url = getDirectionsUrl(origin, dest);
        DownloadTask downloadTask = new DownloadTask();
        downloadTask.execute(url);
    }

    private String getDirectionsUrl(LatLng origin, LatLng dest) {
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        String sensor = "sensor=false";
        //パラメータ
        String parameters = str_origin + "&" + str_dest + "&" + sensor + "&language=ja" + "&mode=" + "walking";
        //JSON指定
        String output = "json";
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;
        return url;
    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            iStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
            StringBuffer sb = new StringBuffer();
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            data = sb.toString();
            br.close();
        } catch (Exception e) {
            //Log.d("Exception while downloading url", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    private class DownloadTask extends AsyncTask<String, Void, String> {
        //非同期で取得
        @Override
        protected String doInBackground(String... url) {
            String data = "";
            try {
                // Fetching the data from web service
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }


        // doInBackground()
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            ParserTask parserTask = new ParserTask();
            parserTask.execute(result);
        }
    }

    /*parse the Google Places in JSON format */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {
            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;
            try {
                jObject = new JSONObject(jsonData[0]);
                parseJsonpOfDirectionAPI parser = new parseJsonpOfDirectionAPI();
                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        //ルート検索で得た座標を使って経路表示
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;
            MarkerOptions markerOptions = new MarkerOptions();
            if (result.size() != 0) {
                for (int i = 0; i < result.size(); i++) {
                    points = new ArrayList<LatLng>();
                    lineOptions = new PolylineOptions();
                    List<HashMap<String, String>> path = result.get(i);
                    for (int j = 0; j < path.size(); j++) {
                        HashMap<String, String> point = path.get(j);
                        double lat = Double.parseDouble(point.get("lat"));
                        double lng = Double.parseDouble(point.get("lng"));
                        LatLng position = new LatLng(lat, lng);
                        points.add(position);
                    }
                    //ポリライン
                    lineOptions.addAll(points);
                    lineOptions.width(10);
                    lineOptions.color(0x550000ff);
                }
                //描画
                mMap.addPolyline(lineOptions);

                // ピンを置く
                MarkerOptions options = new MarkerOptions();
//                // 緯度経度を取得
//                LatLng latLng;
//                options.position()
//                mMap.addMarker();

            } else {
                mMap.clear();
                Toast.makeText(getActivity(), "ルート情報を取得できませんでした", Toast.LENGTH_LONG).show();
            }
            progressDialog.hide();
        }
    }


    //リ･ルート検索
    private void re_routeSearch() {
        progressDialog.show();
        LatLng origin = markerPoints.get(0);
        LatLng dest = markerPoints.get(1);
        //
        mMap.clear();
        //マーカー
        //A
        options = new MarkerOptions();
        options.position(origin);
        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_cast_light));
        options.title("Start");
        options.draggable(true);
        mMap.addMarker(options);
        //B
        options = new MarkerOptions();
        options.position(dest);
        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_play_light));
        options.title("Goal");
        options.draggable(true);
        mMap.addMarker(options);
        String url = getDirectionsUrl(origin, dest);
        DownloadTask downloadTask = new DownloadTask();
        downloadTask.execute(url);
    }


    //ここより上は試作中
    @Override
    public void onStart() {
        super.onStart();
        mview = (MapView) getActivity().findViewById(R.id.mapview);
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        Button searchButton = (Button) getActivity().findViewById(R.id.btnLocate);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onGetLocation(v);

            }
        });

        Button gohomebutton = (Button) getActivity().findViewById(R.id.gohomebutton);
        gohomebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                onGetLocation(v);
                try {

                    mMap.clear();
                    markerPoints.clear();
                    Location myLocation = locationManager
                            .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    //locationManager.getLastKnownLocation("gps");
                    if (myLocation != null) {
                        Double a = myLocation.getLatitude();
                        Double b = myLocation.getLongitude();

                        //現在地の追加
                        markerPoints.add(new LatLng(a, b));
                        Geocoder gcoder = new Geocoder(getActivity(), Locale.getDefault());
                        Toast.makeText(getActivity().getApplicationContext(), "" + markerPoints.get(0), Toast.LENGTH_LONG).show();
                        setMarker(new LatLng(a, b), R.drawable.sss1);

//                        Log.d("test", String.valueOf(markerPoints.get(0)));
//                    Log.d("test", String.valueOf(markerPoints.get(1)));


                        List<Address> address = gcoder.getFromLocationName(getaddress(), 1);
                        LatLng homeLocation = new LatLng(address.get(0).getLatitude(), address.get(0).getLongitude());
                        markerPoints.add(homeLocation);
                        setMarker(homeLocation, TopActivity.homeIcon);
//                    markerPoints.add(pref.getString("address", "1,1"));
//                    Toast.makeText(getActivity().getApplicationContext(), "位置\n緯度:" + markerPoints.get(1) + "\n経度:" + markerPoints.get(2), Toast.LENGTH_LONG).show();
                        routeSearch();
                    } else {
                        Toast.makeText(getActivity(), "現在地の取得に失敗しました。", Toast.LENGTH_LONG).show();

                        if (locationManager == null) {
                            Toast.makeText(getActivity(), "locationManagerもnull", Toast.LENGTH_LONG).show();
                        }
                    }

                } catch (SecurityException e) {

                } catch (IOException e) {

                }
            }


        });


    }

    private void setMarker(LatLng location, Bitmap bitmap) {

        MarkerOptions options = new MarkerOptions();
        options.position(location);
        options.icon(BitmapDescriptorFactory.fromBitmap(bitmap));

        mMap.addMarker(options);
    }

    private void setMarker(LatLng location, int drawable) {

        MarkerOptions options = new MarkerOptions();
        options.position(location);
        options.icon(BitmapDescriptorFactory.fromResource(drawable));

        mMap.addMarker(options);
    }

    public String getaddress() {
        SharedPreferences sp = getContext().getSharedPreferences("Setting", Context.MODE_PRIVATE);
        String a = sp.getString("address", "大阪市北区中崎西");
        return a;
    }

    static public String getAddress(Context context) {
        SharedPreferences sp = context.getSharedPreferences("Setting", Context.MODE_PRIVATE);
        String a = sp.getString("address", "大阪市北区中崎西");
        return a;
    }


    public void onGetLocation(View view) {
        Geocoder gcoder = new Geocoder(getActivity(), Locale.getDefault());
        List<Address> lstAddr;
        EditText name = (EditText) getActivity().findViewById(R.id.editAddr);
        //EditText name = null;
        try {
            // 位置情報の取得
            lstAddr = gcoder.getFromLocationName(name.getText().toString(), 1);
            if (lstAddr != null && lstAddr.size() > 0) {
                // 緯度/経度取得
                Address addr = lstAddr.get(0);
                int latitude = (int) (addr.getLatitude() * 1E6);
                int longitude = (int) (addr.getLongitude() * 1E6);
                Toast.makeText(getActivity(), "位置\n緯度:" + latitude + "\n経度:" + longitude, Toast.LENGTH_LONG).show();
                LatLng search = new LatLng(latitude / 1E6, longitude / 1E6);
                CameraPosition.Builder camerapos = new CameraPosition.Builder();//表示位置の作成
                camerapos.target(search);//カメラの表示位置の指定
                camerapos.zoom(13.0f);//ズームレベル
                camerapos.bearing(0);//カメラの向きの指定(北向きなので０）
                camerapos.tilt(25.0f);//カメラの傾き設定
                mMap.moveCamera(CameraUpdateFactory.newCameraPosition(camerapos.build()));//マップの表示位置変更
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setUpMapIfNeeded();
        try {
            MarkerOptions options = new MarkerOptions();
            Geocoder gcoder = new Geocoder(getActivity(), Locale.getDefault());
            List<Address> address = gcoder.getFromLocationName(getaddress(), 1);
            LatLng location = new LatLng(address.get(0).getLatitude(), address.get(0).getLongitude());
            markerPoints.add(location);
            options.position(location);
            options.icon(BitmapDescriptorFactory.fromResource(R.drawable.ggg2));
            mMap.addMarker(options);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fragment_map1))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(34.7, 135.504), 14.0f));
    }

    private void setUpMap() {
        Log.d("method", "setUpMap");
//        mMap.getUiSettings().setZoomControlsEnabled(true);//拡大縮小ボタン表示
//        mMap.setMyLocationEnabled(true);//現在地へ飛ぶボタン
        LatLng osaka = new LatLng(34.7, 135.504);//大阪
        CameraPosition.Builder camerapos = new CameraPosition.Builder();//表示位置の作成
        camerapos.target(osaka);//カメラの表示位置の指定
        camerapos.zoom(13.0f);//ズームレベル
        camerapos.bearing(0);//カメラの向きの指定(北向きなので０）
        camerapos.tilt(25.0f);//カメラの傾き設定
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(camerapos.build()));//マップの表示位置変更
        MarkerOptions options = new MarkerOptions();//ピンの設定

        //セッティングに登録している地点を呼び出す　なかったら中崎町
        String search_key = getaddress();
        Geocoder gcoder = new Geocoder(getActivity(), Locale.getDefault());
        int maxResults = 1;
        List<Address> lstAddr;
        try {
            // 位置情報の取得
            lstAddr = gcoder.getFromLocationName(search_key, maxResults);

//            Log.v("size", Integer.toString(lstAddr.size()));

            if (lstAddr != null && lstAddr.size() > 0) {
                // 緯度/経度取得
                Address setaddr = lstAddr.get(0);
                double setlatitude = setaddr.getLatitude();
                double setlongitude = setaddr.getLongitude();

//                Log.v("lati", Double.toString(setlatitude));
//                Log.v("longi", Double.toString(setlongitude));

                LatLng setaddress = new LatLng(setlatitude, setlongitude);
                options.position(setaddress);//ピンの場所を指定
                options.title(getaddress());//ピンのタイトル
                mMap.addMarker(options);//ピンの設置
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //ネットに繋がっているかチェックするメソッド
    private boolean netWorkCheck(Context context){
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info != null){
            return info.isConnected(); //繋がっている
        }else{
            return false; //繋がっていない
        }
    }

    public static GoogleMap getMap() {
        return mMap;
    }

}
