package ke4a11.ecc.ac.jp.afururu.Money;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

//import package

import ke4a11.ecc.ac.jp.afururu.R;

/*
*  *****簡単な説明*****
*  fragmentのreplaceのカレンダーのみR.id.money_calc_screenになっている。
*  これは表示形式をリストとカレンダーにするのだが、その選択をこのスピナーで選択してもらうため
*
*/

//共通変数を扱うクラス
public class MoneyActivity extends AppCompatActivity {


    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money);

        if(_MoneyTop.moneyFlg.equals("cal")){
            ToMoney_Calender();
        }else if(_MoneyTop.moneyFlg.equals("gra")){
            ToMoney_Graph();
        }else if(_MoneyTop.moneyFlg.equals("exc")){
            ToMoney_Exchange();
        }else if(_MoneyTop.moneyFlg.equals("set")){
            ToMoney_Setting();
        }


        //spinner用のアダプターを作成
        ArrayAdapter<String> ad = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //アイテム追加
        ad.add("カレンダー");
        ad.add("リスト一覧");
        spinner = (Spinner)findViewById(R.id.ListorCalSpinner);

        spinner.setAdapter(ad);

        //スピナーのリスナの設定
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //選択された表示形式から、表示するフラグメントを決める
                //selectedSpinner = rateName[spinner.getSelectedItemPosition()];

                if(spinner.getSelectedItemPosition() == 0){
                    //カレンダー表示
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.money_calc_screen, Money_Calendar.newInstance());
                    ft.commit();
                }else if(spinner.getSelectedItemPosition() == 1){
                    //リスト表示

                }else{

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    //added
    public void ToMoney_Calender(){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.money_calc_screen, Money_Calendar.newInstance());
        ft.commit();
    }
    public void ToMoney_Graph(){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.money_top_screen,Money_Graph.newInstance());
        ft.commit();
    }
    public void ToMoney_Exchange(){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.money_top_screen, Money_Exchange.newInstance());
        ft.commit();
    }
    public void ToMoney_Setting(){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.money_top_screen,Money_Setting.newInstance());
        ft.commit();
    }

    //textview のクリックイベントのテスト、xmlにクリックのリスナー？を設定している
    public void testToast(View view){
        Toast.makeText(getApplicationContext(), "Test OK!", Toast.LENGTH_SHORT).show();
    }
}
