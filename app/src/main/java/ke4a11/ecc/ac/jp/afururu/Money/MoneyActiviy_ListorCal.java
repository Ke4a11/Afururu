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

import ke4a11.ecc.ac.jp.afururu.Money.dummy.DummyContent;
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

public class MoneyActiviy_ListorCal extends AppCompatActivity implements Money_List.OnFragmentInteractionListener {

  private Spinner spinner;

  //追加予定のやつ
  //private String[] DB_SELECTED_payout;
  public String[] DB_SELECTED_primary;
  public String[] DB_SELECTED_date;
  public String[] DB_SELECTED_shop;
  public int[] DB_SELECTED_category;
  public String[] DB_SELECTED_memo;
  public String[] DB_SELECTED_price;

  private Cursor c;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_money_activiy_listorcal);

    //spinner用のアダプターを作成
    ArrayAdapter<String> ad = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);

    ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

    //アイテム追加
    ad.add("カレンダー");
    ad.add("リスト一覧");
    spinner = (Spinner) findViewById(R.id.spinner_listorcal);

    spinner.setAdapter(ad);

    //スピナーのリスナの設定
    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if (spinner.getSelectedItemPosition() == 0) {

          DB_Select(true);

          //カレンダー表示
          FragmentManager fm = getSupportFragmentManager();
          FragmentTransaction ft = fm.beginTransaction();
          ft.replace(R.id.fragment_listorcal, Money_Calendar.newInstance());
          //ft.addToBackStack(null);    //戻るボタン対応
          ft.commit();
        } else if (spinner.getSelectedItemPosition() == 1) {

          DB_Select(false);

          //リスト表示
          FragmentManager fm = getSupportFragmentManager();
          FragmentTransaction ft = fm.beginTransaction();
          ft.replace(R.id.fragment_listorcal, Money_List.newInstance());
          //ft.addToBackStack(null);    //戻るボタン対応
          ft.commit();
        } else {

        }
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {
      }
    });

  }

  //Money_Listのアイテムのクリックをリスナーが感知したら？
  @Override
  public void onFragmentInteraction(String id) {
    //implementsしているため必ず必要

    Intent detailIntent = new Intent(this, MoneyActivity_ListorCal_Detail.class);
    //Money_ListorCal_Detail の初めのif文で受け取って処理しているため必ず必要。
    //具体的な処理は、Money_ListorCal_Detailの変数である、mItemにデータを入れるため
    //DummyContent中のITEM_MAPのキーを特定させるために String id を使う。
    //ARG_ITEM_IDはここをいじると、他のクラスでも値の変更をしないといけないので定数にしている。
    detailIntent.putExtra(Money_ListorCal_Detail.ARG_ITEM_ID, id);
    startActivity(detailIntent);
  }

  //引数によってカレンダーかリスト表示用のsqlを変える
  void DB_Select(boolean flg){

    MoneyOpenHelper helper = new MoneyOpenHelper(getApplicationContext());
    SQLiteDatabase db = helper.getReadableDatabase();

    if (flg){
      //カレンダー
      String sql = "select id,date,shop,category,memo,sum(price) from input group by date;";
      c = db.rawQuery(sql,null);
    }else{
      //リスト
      String sql = "select id,date,shop,category,memo,price from input order by date DESC;";
//      c = db.query(input, new String[]{"id", "date", "shop", "category", "memo", "price"}, null,
//              null, null, null, "date ASC");
      c = db.rawQuery(sql,null);
    }


    boolean mov = c.moveToFirst();

    //クッションの役割 詳しくはフィールドにセッティング７るとこを参照
    String[] primary = new String[c.getCount()];
    String[] date = new String[c.getCount()];
    String[] shop = new String[c.getCount()];
    int[] category = new int[c.getCount()];
    String[] memo = new String[c.getCount()];
    String[] price = new String[c.getCount()];

    //selectで得た結果を配列に入れて、それをDummyContentのstaticに打ち込む準備
    int i = 0;
    while (mov) {

      primary[i] = c.getString(0);
      date[i] = c.getString(1);
      shop[i] = c.getString(2);
      category[i] = c.getInt(3);
      memo[i] = c.getString(4);
      price[i] = c.getString(5);

      i++;

      mov = c.moveToNext();
    }

    //String[] DB_SELECTED_date 初期値を設定していないとnull array? になり値を代入できないので、こういう形になっている。
    DB_SELECTED_primary = primary;
    DB_SELECTED_date = date;
    DB_SELECTED_shop = shop;
    DB_SELECTED_category = category;
    DB_SELECTED_memo = memo;
    DB_SELECTED_price = price;

    c.close();
    db.close();

    //クラスロードのためインスタンス生成している
    //DummyContentで使用できるように設定する
    DummyContent dummyContent = new DummyContent(DB_SELECTED_primary, DB_SELECTED_date, DB_SELECTED_shop, DB_SELECTED_category, DB_SELECTED_memo, DB_SELECTED_price);

  }

}
