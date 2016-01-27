package ke4a11.ecc.ac.jp.afururu.Map;


import android.app.FragmentManager;
import android.location.Address;
import android.location.Geocoder;
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


public class _MapTop extends Fragment {


    private static View view;
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    public static _MapTop newInstance() {
        _MapTop fragment = new _MapTop();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    private MapView mview;
    MapController MapCtrl;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_map_top, container, false);
        }

        return view;
    }

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
    }


//    protected boolean isRouteDisplayed() {
//        // TODO Auto-generated method stub
//        return false;
//        }

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

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-33.87365, 151.20689), 14.0f));
    }

    private void setUpMap() {
        mMap.getUiSettings().setZoomControlsEnabled(true);//拡大縮小ボタン表示
        mMap.setMyLocationEnabled(true);//現在地へ飛ぶボタン

        LatLng morioka = new LatLng(39.703531, 141.152667);//盛岡
        LatLng takizawa = new LatLng(39.734694, 141.077056);//滝沢

        CameraPosition.Builder camerapos = new CameraPosition.Builder();//表示位置の作成
        camerapos.target(morioka);//カメラの表示位置の指定
        camerapos.zoom(13.0f);//ズームレベル
        camerapos.bearing(0);//カメラの向きの指定(北向きなので０）
        camerapos.tilt(25.0f);//カメラの傾き設定
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(camerapos.build()));//マップの表示位置変更

        MarkerOptions options = new MarkerOptions();//ピンの設定
        options.position(morioka);//ピンの場所を指定
        options.title("盛岡");//マーカーの吹き出しの設定
        mMap.addMarker(options);//ピンの設置

        options.position(takizawa);
        options.title("滝沢");
        mMap.addMarker(options);









    }

}
