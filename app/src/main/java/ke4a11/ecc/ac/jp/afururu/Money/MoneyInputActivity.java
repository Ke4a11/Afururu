package ke4a11.ecc.ac.jp.afururu.Money;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
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
import ke4a11.ecc.ac.jp.afururu.Setting.ShowDataBase;
import ke4a11.ecc.ac.jp.afururu.TopActivity;

public class MoneyInputActivity extends AppCompatActivity {
    //初期の値
     public static String selectedSpinner = "外食費";

    //カテゴリ別のString型配列
    private final String[] rateName = {"食費","外食費","交通費"};

    public MoneyInputActivity() {
        // Required empty public constructor
    }

    public void onStart(){
        super.onStart();

        MoneyOpenHelper helper = new MoneyOpenHelper(this);
        final SQLiteDatabase db = helper.getWritableDatabase();
        final EditText shopText = (EditText) findViewById(R.id.shopEdit);
        final EditText memoText = (EditText) findViewById(R.id.memoEdit);
        final TextView dateText = (TextView)findViewById(R.id.dateText);
        final EditText priceText = (EditText)findViewById(R.id.priceEdit);
        Button entryButton = (Button) findViewById(R.id.addButton);
        final Spinner spinner = (Spinner)findViewById(R.id.categorySpinner);


        entryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String shop = shopText.getText().toString();
                String memo = memoText.getText().toString();
                String date = dateText.getText().toString();
                //金額入力で小数点２位以下切り捨て
                String stringprice = null;
                if (!priceText.getText().toString().equals("")){
                    Float f = Float.parseFloat(priceText.getText().toString());
                    stringprice = String.format("%.2f",f);
                }
                // 選択されているアイテムのIndexを取得
                //int idx = spinner.getSelectedItemPosition();
                //入力してくださいメッセージの連続
                if(stringprice == null||stringprice.equals("")) {
                    Toast.makeText(MoneyInputActivity.this, "金額を入力してください", Toast.LENGTH_SHORT).show();
                }else if (shop == null || shop.equals("")) {
                    Toast.makeText(MoneyInputActivity.this, "SHOPを入力してください", Toast.LENGTH_SHORT).show();
                }else if(memo==null||memo.equals("")){
                    Toast.makeText(MoneyInputActivity.this,"MEMOを入力してください",Toast.LENGTH_SHORT).show();
                }else {
                     // 選択されているアイテムを取得
                     int category = spinner.getSelectedItemPosition();
                     ContentValues insertValues = new ContentValues();
                     insertValues.put("date", date);
                     insertValues.put("shop", shop);
                     insertValues.put("category", category);
                     insertValues.put("memo", memo);
                     insertValues.put("price", stringprice);

                     long id = db.insert("ecc", date, insertValues);

                     //一つ目の引数をthisではなくContextにする　登録かんりょうのToast表示
                     Toast.makeText(getApplicationContext(), "登録完了", Toast.LENGTH_SHORT).show();
                     Intent i = new Intent(getApplicationContext(), TopActivity.class);
                     startActivity(i);
                 }
            }
        });

        //DB確認
        Button showButton = (Button) findViewById(R.id.showButton);
        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dbIntent = new Intent(getApplicationContext(),MoneyShowDatabase.class);
                startActivity(dbIntent);
            }
        });

        //spinner用のアダプターを作成
        ArrayAdapter<String> ad = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //アイテム追加
        ad.add("食費");
        ad.add("外食費");
        ad.add("交通費");
       // spinner = (Spinner)findViewById(R.id.categorySpinner);

        spinner.setAdapter(ad);

        //スピナーのリスナの設定
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectedSpinner = rateName[spinner.getSelectedItemPosition()];


                //selectedSpinner = (String)spinner.getSelectedItem();
                //Toast.makeText(getContext(),Integer.toString(spinner.getSelectedItemPosition()),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

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
        return inflater.inflate(R.layout.activity_money_input, container, false);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //日付を表示する処理
        setContentView(R.layout.activity_money_input);
        final TextView dateText = (TextView)findViewById(R.id.dateText);
        Time time = new Time("Asia/Tokyo");
        time.setToNow();
        String date;
        if (time.monthDay < 10){
            date = time.year + "/" + (time.month+1) + "/0" + time.monthDay;
        }else{
            date = time.year + "/" + (time.month+1) + "/" + time.monthDay;
        }
        dateText.setText(date);
        //押したときカレンダー表示
        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();

                DatePickerDialog dialog = new DatePickerDialog(MoneyInputActivity.this,new DatePickerDialog.OnDateSetListener() {
                    	public void onDateSet(DatePicker picker,	int year, int month,int day) {
                            if(day < 10){
                                dateText.setText(year + "/" + (month+1) + "/0" + day);
                            }else{
                                dateText.setText(year + "/" + (month+1) + "/" + day);
                            }
                     }
               }
                      ,cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)
                );
                dialog.show();
            }
        });

/*
        Button btn=(Button)findViewById(R.id.addButton);
        //btn.setOnClickListener(this);
        btn.setText("kokoko");
        btn.setVisibility(View.GONE);
*/

    }
}
