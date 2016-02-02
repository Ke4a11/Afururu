package ke4a11.ecc.ac.jp.afururu.Map;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;

import ke4a11.ecc.ac.jp.afururu.R;

public class MapActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(50.833, -0.772)));
        mMap.setMyLocationEnabled(true); //位置情報
        //mMap.setTrafficEnabled(true); //渋滞状況
        //mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE); //衛生写真モード
        //mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        //mMap.setMapType(GoogleMap.MAP_TYPE_NONE);
        //mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        //mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
    }

}