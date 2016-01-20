package ke4a11.ecc.ac.jp.afururu.Money;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

//Import package

import ke4a11.ecc.ac.jp.afururu.R;


/*
* TODO
*
* titleEngTextの部分にデータベース？として保持している今までの支出データを
* 配列の変数に入れてそれをリストビューに渡す。
* 中身としてはアプリZaimを参照する
* ２次元配列にする？aiueo[i][j]↓
* [i] ここにキーとなる（日付にする？）ものを入れて
* [j] ここにキーにあったデータを入れる。（店名、お金、ジャンルなど）
* これをforで回すならいけそう？要確認
*
* */

/*
*  *****簡単な説明*****
*  _MoneyTopのカレンダーボタンを押した時に起動するアクティビティ
*  リスト表示かカレンダー表示を選択する
*  独自のコードが大量に必要なのでわけた
*
* */

public class MoneyActiviy_ListorCal extends AppCompatActivity implements Money_List.OnFragmentInteractionListener{

    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_activiy_listorcal);

        //spinner用のアダプターを作成
        ArrayAdapter<String> ad = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item);

        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //アイテム追加
        ad.add("カレンダー");
        ad.add("リスト一覧");
        spinner = (Spinner)findViewById(R.id.spinner_listorcal);

        spinner.setAdapter(ad);

        //スピナーのリスナの設定
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (spinner.getSelectedItemPosition() == 0) {
                    //カレンダー表示
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.fragment_listorcal, Money_Calendar.newInstance());
                    ft.commit();
                } else if (spinner.getSelectedItemPosition() == 1) {
                    //リスト表示
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.fragment_listorcal, Money_List.newInstance());
                    ft.commit();
                } else {

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


    }

    @Override
    public void onFragmentInteraction(String id) {
        //implementsしているため必ず必要

        //Intent detailIntent =

    }

}
