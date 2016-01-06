package ke4a11.ecc.ac.jp.afururu.Money;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

//import package
import ke4a11.ecc.ac.jp.afururu.R;

//共通変数を扱うクラス
public class MoneyActivity extends AppCompatActivity {


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

    }

    //added
    public void ToMoney_Calender(){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_money,Money_Calendar.newInstance());
        ft.commit();
    }
    public void ToMoney_Graph(){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_money,Money_Graph.newInstance());
        ft.commit();
    }
    public void ToMoney_Exchange(){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_money, Money_Exchange.newInstance());
        ft.commit();
    }
    public void ToMoney_Setting(){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_money,Money_Setting.newInstance());
        ft.commit();
    }

}
