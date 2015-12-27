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

        ToMoney_Calender();

    }

    //added
    public void ToMoney_Calender(){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_money,Money_Calendar.newInstance());
        ft.commit();
    }
}
