package ke4a11.ecc.ac.jp.afururu.Money;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.Time;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import ke4a11.ecc.ac.jp.afururu.R;

public class MoneyUpdateActivity extends AppCompatActivity {
    //日付が表示されるテキストビュー
    private TextView dateText;
    //入力されるテキスト
    EditText shopText, memoText, priceText;
    //カテゴリ選択のスピナー
    private Spinner spinner;

    public MoneyUpdateActivity() {
    }

    public void onStart() {
        super.onStart();

        MoneyOpenHelper helper = new MoneyOpenHelper(this);
        final SQLiteDatabase db = helper.getWritableDatabase();

        Intent i = getIntent();
        int id = i.getIntExtra("position", 1);

        //update文を発行する場合に変数を定数指定する必要があるため
        final String ids = String.valueOf(id);

        //String空文字追加
        String shop = "";
        String memo = "";
        String date = "";
        String price = "";
        int category = 0;

        String sqlstr = "select id,shop,memo,category,date,price from input where id =" + ids + ";";
        Cursor c = db.rawQuery(sqlstr, null);

        //削除した後、戻るボタンで編集の画面に戻り更新ボタンを押すと落ちるためその対処
        if (c.moveToFirst()) {
            shop += String.format("%s", c.getString(1));
            memo = c.getString(2);
            category = c.getInt(3);
            date = c.getString(4);
            price = c.getString(5);

            spinner.setSelection(category);

            shopText.setText(shop);
            memoText.setText(memo);
            dateText.setText(date);
            priceText.setText(price);

            //UPDATEでの処理
            final Button updateButton = (Button) findViewById(R.id.updateButton);
            updateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String shop = shopText.getText().toString();
                    String memo = memoText.getText().toString();
                    String date = dateText.getText().toString();

                    String stringprice = null;
                    if (!priceText.getText().toString().equals("")) {
                        //小数点２桁以下を入力されると切り捨て
                        Float f = Float.parseFloat(priceText.getText().toString());
                        stringprice = String.format("%.2f", f);
                    }
                    //入力内容のチェック
                    if (stringprice == null || stringprice.equals("")) {
                        Toast.makeText(MoneyUpdateActivity.this, "金額を入力してください", Toast.LENGTH_SHORT).show();
                    } else if (shop == null || shop.equals("")) {
                        Toast.makeText(MoneyUpdateActivity.this, "SHOPを入力してください", Toast.LENGTH_SHORT).show();
                    } else if (memo == null || memo.equals("")) {
                        Toast.makeText(MoneyUpdateActivity.this, "MEMOを入力してください", Toast.LENGTH_SHORT).show();
                    } else {
                        //問題なければ、データベースに登録
                        //選択されているアイテムを取得
                        int cat = spinner.getSelectedItemPosition();
                        ContentValues updateValues = new ContentValues();
                        updateValues.put("shop", shop);
                        updateValues.put("memo", memo);
                        updateValues.put("date", date);
                        updateValues.put("price", stringprice);
                        updateValues.put("category", cat);
                        // String whereClause = "id = ?";
                        db.update("input", updateValues, "id=?", new String[]{ids});
                        Intent i = new Intent(getApplicationContext(), MoneyActiviy_ListorCal.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(i);
                    }
                }
            });
        }
        //
        memoText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //EnterKeyが押されたかを判定
                if (event.getAction() == KeyEvent.ACTION_DOWN
                        && keyCode == KeyEvent.KEYCODE_ENTER) {

                    //ソフトキーボードを閉じる
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);

                    return true;
                }
                return false;
            }
        });
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_money_update, container, false);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //日付を表示する処理
        setContentView(R.layout.activity_money_update);

                /*
                フィールドの値を使えるように
                 */
        shopText = (EditText) findViewById(R.id.shopEdit);
        memoText = (EditText) findViewById(R.id.memoEdit);
        priceText = (EditText) findViewById(R.id.priceEdit);

                /*
                Spinnerの設定
                */
        spinner = (Spinner) findViewById(R.id.categorySpinner);
        ArrayAdapter<String> ad = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //アイテム追加
        ad.add("食費");
        ad.add("外食費");
        ad.add("交通費");

        spinner.setAdapter(ad);

                /*
                日付を表示する処理
                */
        dateText = (TextView) findViewById(R.id.dateText);
        Time time = new Time("Asia/Tokyo");
        time.setToNow();
        String date;
        if (time.monthDay < 10) {
            date = time.year + "/" + (time.month + 1) + "/0" + time.monthDay;
        } else {
            date = time.year + "/" + (time.month + 1) + "/" + time.monthDay;
        }
        dateText.setText(date);
        //押したときカレンダー表示
        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                DatePickerDialog dialog = new DatePickerDialog(MoneyUpdateActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker picker, int year, int month, int day) {
                        if (day < 10) { //日付が一桁ならば、頭に０をつける。sql対策のため
                            dateText.setText(year + "/" + (month + 1) + "/0" + day);
                        } else {
                            dateText.setText(year + "/" + (month + 1) + "/" + day);
                        }
                    }
                }
                        , cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)
                );
                dialog.show();
            }
        });
    }
}
