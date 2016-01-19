package ke4a11.ecc.ac.jp.afururu.Map;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ke4a11.ecc.ac.jp.afururu.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class _MapTop extends Fragment {


    private static View view;
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

//    public static _MapTop newInstance() {
//        _MapTop fragment = new _MapTop();
//        Bundle args = new Bundle();
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_map_top, container, false);
        }
        return view;
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
