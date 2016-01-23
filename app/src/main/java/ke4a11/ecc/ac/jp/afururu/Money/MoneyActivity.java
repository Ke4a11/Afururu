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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money);

        if(_MoneyTop.moneyFlg.equals("gra")){
            ToMoney_Graph();
        }else if(_MoneyTop.moneyFlg.equals("exc")){
            ToMoney_Exchange();
        }else if(_MoneyTop.moneyFlg.equals("set")){
            ToMoney_Setting();
        }

    }

    //added
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
}
