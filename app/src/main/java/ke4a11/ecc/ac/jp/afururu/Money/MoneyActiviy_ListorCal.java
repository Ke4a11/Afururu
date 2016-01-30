package ke4a11.ecc.ac.jp.afururu.Money;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

//Import package

import ke4a11.ecc.ac.jp.afururu.R;

import ke4a11.ecc.ac.jp.afururu.Money.dummy.DummyContent;


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

    //追加予定のやつ
    //private String[] DB_SELECTED_payout;
    public String[] DB_SELECTED_date;
    public String[] DB_SELECTED_shop;
    public String[] DB_SELECTED_category;
    public String[] DB_SELECTED_memo;

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

                    //クラスロードのためインスタンス生成している
                    //DummyContentで使用できるように設定する
                    DummyContent dummyContent = new DummyContent(DB_SELECTED_date,DB_SELECTED_shop,DB_SELECTED_category,DB_SELECTED_memo);

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
    protected void onStart(){
        super.onStart();

        MoneyOpenHelper helper = new MoneyOpenHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();

        // queryメソッドの実行例
        Cursor c = db.query("ecc", new String[] {"id","date","shop", "category","memo","price"}, null,
                null, null, null, null);
        boolean mov = c.moveToFirst();

        //クッションの役割 詳しくはフィールドにセッティング７るとこを参照
        String[] date = new String[c.getCount()];
        String[] shop = new String[c.getCount()];
        String[] category = new String[c.getCount()];
        String[] memo = new String[c.getCount()];


        //selectで得た結果を配列に入れて、それをDummyContentのstaticに打ち込む準備
        int i = 0;
        while (mov) {

            date[i] = c.getString(1);
            shop[i] = c.getString(2);
            category[i] = c.getString(3);
            memo[i] = c.getString(4);

            i++;

            mov = c.moveToNext();
        }

        //String[] DB_SELECTED_date 初期値を設定していないとnull array? になり値を代入できないので、こういう形になっている。
        DB_SELECTED_date = date;
        DB_SELECTED_shop = shop;
        DB_SELECTED_category = category;
        DB_SELECTED_memo = memo;

        c.close();
        db.close();
    }

    //Money_Listのアイテムのクリックをリスナーが感知したら？
    @Override
    public void onFragmentInteraction(String id) {
        //implementsしているため必ず必要

        Intent detailIntent = new Intent(this,MoneyActivity_ListorCal_Detail.class);
        //Money_ListorCal_Detail の初めのif文で受け取って処理しているため必ず必要。
        //具体的な処理は、Money_ListorCal_Detailの変数である、mItemにデータを入れるため
        //DummyContent中のITEM_MAPのキーを特定させるために String id を使う。
        //ARG_ITEM_IDはここをいじると、他のクラスでも値の変更をしないといけないので定数にしている。
        detailIntent.putExtra(Money_ListorCal_Detail.ARG_ITEM_ID, id);
        startActivity(detailIntent);
    }

    //textview のクリックイベントのテスト、xmlにクリックのリスナー？を設定している
    public void testToast(View view){
        Toast.makeText(getApplicationContext(), "Test OK!", Toast.LENGTH_SHORT).show();
    }

}
