package ke4a11.ecc.ac.jp.afururu;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

//import package
import com.astuetz.PagerSlidingTabStrip; //固定タブのためだけのライブラリ
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import ke4a11.ecc.ac.jp.afururu.Map._MapTop;
import ke4a11.ecc.ac.jp.afururu.Money.*;
import ke4a11.ecc.ac.jp.afururu.English.*;

//AppCompatActivityはFragmentActivityを継承している
public class TopActivity extends AppCompatActivity {

    //フィールド
    ViewPager mViewPager;
    //MAPで使う
    public static String posinfo = "";
    public static String info_A = "";
    public static String info_B = "";
    ArrayList<LatLng> markerPoints;
    private Button btnFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top);

        setViews();

        //タブストリップの作成
        mViewPager = (ViewPager)findViewById(R.id.viewPager);
        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setViewPager(mViewPager);
        tabs.setIndicatorHeight(10);
        tabs.setIndicatorColor(0xff2196F3);

    }

    //タブ作成メソッド
    private void setViews(){
        FragmentManager manager = getSupportFragmentManager();
        ViewPager viewPager = (ViewPager)findViewById(R.id.viewPager);
        fragmentPagerAdapter adapter = new fragmentPagerAdapter(manager);
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_top, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    //moneysetting に移動する
    public void toMoneySetting(View view){
        Intent i = new Intent(getApplicationContext(),MoneyActivity.class);
        i.putExtra("moneyFlg","set");
        startActivity(i);
    }

    public void testToast2(View view){
        Intent it = new Intent(getApplicationContext(),MoneyActiviy_ListorCal.class);
        startActivity(it);
    }

    //moneyexchange
    public void testToast3(View view){
        Intent it = new Intent(getApplicationContext(),MoneyActivity.class);
        it.putExtra("moneyFlg","exc");
        startActivity(it);
    }


    @Override
    protected void onResume() {
        super.onResume();
//        _MapTop.flg = true;
    }

}
