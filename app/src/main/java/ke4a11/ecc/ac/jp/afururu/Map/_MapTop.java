package ke4a11.ecc.ac.jp.afururu.Map;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONObject;
import android.app.AlertDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.common.GooglePlayServicesUtil;
import android.app.ProgressDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import ke4a11.ecc.ac.jp.afururu.R;
import ke4a11.ecc.ac.jp.afururu.TopActivity;


public class _MapTop extends Fragment {


    private static View view;
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private MapView mview;
    MapController MapCtrl;
    GoogleMap gMap;
    private static final int MENU_A = 0;
    private static final int MENU_B = 1;
    private static final int MENU_c = 2;
    public static String posinfo = "";
    public static String info_A = "";
    public static String info_B = "";
    ArrayList<LatLng> markerPoints;
    public static MarkerOptions options;
    public ProgressDialog progressDialog;
    public String travelMode = "driving";//default

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_map_top, container, false);
        }
        //プログレス
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("検索中です......");
        progressDialog.hide();

        //初期化
        markerPoints = new ArrayList<LatLng>();
        SupportMapFragment mapfragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.fragment_map);
        gMap = mapfragment.getMap();

        //初期位置
        LatLng location = new LatLng(34.802556297454004,135.53884506225586);
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 17));
        if(gMap!=null){
            gMap.setMyLocationEnabled(true);
            //クリックリスナー
            gMap.setOnMapClickListener(new OnMapClickListener() {
                @Override
                public void onMapClick(LatLng point) {
                //３度目クリックでスタート地点を再設定
                    if(markerPoints.size()>1){
                        markerPoints.clear();
                        gMap.clear();
                    }
                    markerPoints.add(point);
                    options = new MarkerOptions();
                    options.position(point);
                    if(markerPoints.size()==1){
                        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_attach_money_black_24dp));
                        options.title("A");
                    }else if(markerPoints.size()==2){
                        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_attach_money_black_24dp));
                        options.title("B");
                    }
                    gMap.addMarker(options);
                    gMap.setOnMarkerClickListener(new OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(Marker marker) {
                            // TODO Auto-generated method stub
                            String title = marker.getTitle();
                            if (title.equals("A")){
                                marker.setSnippet(info_A);
                            }else if (title.equals("B")){
                                marker.setSnippet(info_B);
                            }
                            return false;
                        }
                    });
                    if(markerPoints.size() >= 2){
                        //ルート検索
                        routeSearch();
                    }
                }
            });
        }
        return view;
    }

    private void routeSearch(){
        progressDialog.show();
        LatLng origin = markerPoints.get(0);
        LatLng dest = markerPoints.get(1);
        String url = getDirectionsUrl(origin, dest);
        DownloadTask downloadTask = new DownloadTask();
        downloadTask.execute(url);
    }

    private String getDirectionsUrl(LatLng origin,LatLng dest){
        String str_origin = "origin="+origin.latitude+","+origin.longitude;
        String str_dest = "destination="+dest.latitude+","+dest.longitude;
        String sensor = "sensor=false";
        //パラメータ
        String parameters = str_origin+"&"+str_dest+"&"+sensor + "&language=ja" + "&mode=" + travelMode;
        //JSON指定
        String output = "json";
        String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;
        return url;
    }

    private String downloadUrl(String strUrl) throws IOException{
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            iStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
            StringBuffer sb = new StringBuffer();
            String line = "";
            while( ( line = br.readLine()) != null){
                sb.append(line);
            }
            data = sb.toString();
            br.close();
        }catch(Exception e){
            //Log.d("Exception while downloading url", e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    private class DownloadTask extends AsyncTask<String, Void, String>{
        //非同期で取得
        @Override
        protected String doInBackground(String... url) {
            String data = "";
            try{
                // Fetching the data from web service
                data = downloadUrl(url[0]);
            }catch(Exception e){
                Log.d("Background Task",e.toString());
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
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> >{

        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {
            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;
            try{
                jObject = new JSONObject(jsonData[0]);
                parseJsonpOfDirectionAPI parser = new parseJsonpOfDirectionAPI();
                routes = parser.parse(jObject);
            }catch(Exception e){
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
            if(result.size() != 0){
                for(int i=0;i<result.size();i++){
                    points = new ArrayList<LatLng>();
                    lineOptions = new PolylineOptions();
                    List<HashMap<String, String>> path = result.get(i);
                    for(int j=0;j<path.size();j++){
                        HashMap<String,String> point = path.get(j);
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
                gMap.addPolyline(lineOptions);
            }else{
                gMap.clear();
                Toast.makeText(getActivity(), "ルート情報を取得できませんでした", Toast.LENGTH_LONG).show();
            }
            progressDialog.hide();
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_top, menu);
        menu.add(0, MENU_A,   0, "Info");
        menu.add(0, MENU_B,   0, "Legal Notices");
        menu.add(0, MENU_c, 0, "Mode");
    }

//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch ( item.getItemId() )
//        {
//            case MENU_A:
//                //show_mapInfo();
//                return true;
//            case MENU_B:
//                //Legal Notices(免責事項)
//                String LicenseInfo = GooglePlayServicesUtil.getOpenSourceSoftwareLicenseInfo(getActivity());
//                AlertDialog.Builder LicenseDialog = new AlertDialog.Builder(getActivity());
//                LicenseDialog.setTitle("Legal Notices");
//                LicenseDialog.setMessage(LicenseInfo);
//                LicenseDialog.show();
//                return true;
//            case MENU_c:
//                //show_settings();
//                return true;
//
//        }
//        return false;
//    }
    //リ･ルート検索
    private void re_routeSearch(){
        progressDialog.show();
        LatLng origin = markerPoints.get(0);
        LatLng dest = markerPoints.get(1);
        //
        gMap.clear();
        //マーカー
        //A
        options = new MarkerOptions();
        options.position(origin);
        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_cast_light));
        options.title("A");
        options.draggable(true);
        gMap.addMarker(options);
        //B
        options = new MarkerOptions();
        options.position(dest);
        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_play_light));
        options.title("B");
        options.draggable(true);
        gMap.addMarker(options);
        String url = getDirectionsUrl(origin, dest);
        DownloadTask downloadTask = new DownloadTask();
        downloadTask.execute(url);
    }




    //ここより上は試作中
    @Override
    public void onStart() {
        super.onStart();
        mview = (MapView)getActivity().findViewById(R.id.mapview);
        Button searchButton = (Button)getActivity().findViewById(R.id.btnLocate);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onGetLocation(v);
            }
        });

//        Button testButton = (Button)getActivity().findViewById((R.id.testbtn));
//        testButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String start = "東京駅";
//                String destination = "スカイツリー";
//
//                // 電車:r
//                String dir = "r";
//                // 車:d
//                //String dir = "d";
//                // 歩き:w
//                //String dir = "w";
//
//                Intent intent = new Intent();
//                intent.setAction(Intent.ACTION_VIEW);
//                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
//                intent.setData(Uri.parse("http://maps.google.com/maps?saddr=" + start + "&daddr=" + destination + "&dirflg=" + dir));
//                startActivity(intent);
//
//
//            }
//        });

    }

    public String getaddress(){
        SharedPreferences sp = getContext().getSharedPreferences("Setting", Context.MODE_PRIVATE );
        String a = sp.getString("address","大阪市北区中崎西");
        return a;
    }


    public void onGetLocation(View view) {
        Geocoder gcoder = new Geocoder(getActivity(), Locale.getDefault());
        List<Address> lstAddr;
        EditText name = (EditText)getActivity().findViewById(R.id.editAddr);
        //EditText name = null;
        try {
            // 位置情報の取得
            lstAddr = gcoder.getFromLocationName(name.getText().toString(), 1);
            if (lstAddr != null && lstAddr.size() > 0) {
                // 緯度/経度取得
                Address addr = lstAddr.get(0);
                int latitude = (int)(addr.getLatitude() * 1E6);
                int longitude = (int)(addr.getLongitude() * 1E6);
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
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fragment_map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(34.7, 135.504), 14.0f));
    }

    private void setUpMap() {
        mMap.getUiSettings().setZoomControlsEnabled(true);//拡大縮小ボタン表示
        mMap.setMyLocationEnabled(true);//現在地へ飛ぶボタン
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
            lstAddr = gcoder.getFromLocationName(search_key,maxResults);
            if (lstAddr != null && lstAddr.size() > 0) {
                // 緯度/経度取得
                Address setaddr = lstAddr.get(0);
                double setlatitude = setaddr.getLatitude();
                double setlongitude = setaddr.getLongitude();
                LatLng setaddress = new LatLng(setlatitude,setlongitude);
                options.position(setaddress);//ピンの場所を指定
                options.title(getaddress());//ピンのタイトル
                mMap.addMarker(options);//ピンの設置
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
