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
    //初期の値
    public static String selectedSpinner = "外食費";

    //カテゴリ別のString型配列
    private final String[] rateName = {"食費","外食費","交通費"};

    public MoneyUpdateActivity() {
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
        final Spinner spinner = (Spinner)findViewById(R.id.categorySpinner);

        Intent i = getIntent();
        int id =i.getIntExtra("position",1);
        id +=1;

        Toast.makeText(getApplicationContext(),"idは"+id+"desu", Toast.LENGTH_SHORT).show();

        String shop ="";
        String memo="";
        String date="";
        String price = "";
         String category="";


        String sqlstr = "select * from ecc where id =" + id + ";";
        Cursor c=db.rawQuery(sqlstr,null);

        boolean isEof = c.moveToFirst();

        while (isEof) {
          //  price += String.format("%d : %s : %s : %s : %s : %d", c.getInt(0), c.getString(1) ,c.getString(2), c.getString(3),c.getString(4),c.getInt(5));
            shop +=String.format("%s",c.getString(1));
            memo +=String.format("%s",c.getString(2));
            category +=String.format("%d",c.getInt(3));
            date +=String.format("%s",c.getString(4));
           //お金は五番目の引数
            price +=String.format("%d",c.getInt(5));
            isEof = c.moveToNext();
        }
        shopText.setText(shop);
        memoText.setText(memo);
        dateText.setText(date);
        priceText.setText(price);
///カテゴリの表示をStringに変換
        String categorychange = category;
        int categorychanged = Integer.parseInt(categorychange);

        Toast.makeText(getApplicationContext(),"", Toast.LENGTH_SHORT).show();


        Button showButton = (Button) findViewById(R.id.showButton);
        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dbIntent = new Intent(getApplicationContext(),MoneyShowDatabase.class);
                startActivity(dbIntent);
            }
        });


        //UPDATEでの処理
        final Button updateButton = (Button) findViewById(R.id.updateButton);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String shop = shopText.getText().toString();
                String memo = memoText.getText().toString();
                String date = dateText.getText().toString();
                Integer price = Integer.parseInt(priceText.getText().toString());
                int category = spinner.getSelectedItemPosition();
                if (shop.equals("")) {

                    Toast.makeText(getApplicationContext(), "名前を入力してください。", Toast.LENGTH_SHORT).show();
                } else {
                    ContentValues updateValues = new ContentValues();
                    updateValues.put("shop", shop);
                    updateValues.put("memo", memo);
                    updateValues.put("date", date);
                    updateValues.put("price", price);
                    updateValues.put("category",category);
                    db.update("ecc", updateValues, "date=?", new String[]{date});
                }
                Intent i = new Intent(getApplicationContext(),MoneyActiviy_ListorCal.class);
                startActivity(i);
            }
        });

                //DROPテーブル
                Button dropButton = (Button) findViewById(R.id.dropButton);
                dropButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        db.execSQL("DROP TABLE ECC");
                    }
                });


                //spinner用のアダプターを作成
                //??コンテキストに変更
                ArrayAdapter<String> ad = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item);
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

                    /*登録押したときの処理の内容
                    String shop = shopText.getText().toString();
                    String memo = memoText.getText().toString();
                    String date = dateText.getText().toString();
                    // 選択されているアイテムのIndexを取得
                    //int idx = spinner.getSelectedItemPosition();

                    // 選択されているアイテムを取得
                    String category = (String)spinner.getSelectedItem();

                    ContentValues insertValues = new ContentValues();
                    insertValues.put("date",date);
                    insertValues.put("shop", shop);
                    insertValues.put("category",category);
                    insertValues.put("memo", memo);
                    long id = db.insert("ecc", date, insertValues);*/

                    return true;
                }
                return false;
            }
        });

        spinner.setSelection(categorychanged);

    }


     public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                     Bundle savedInstanceState) {
                return inflater.inflate(R.layout.activity_money_update, container, false);
            }

            @Override
            protected void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);

                Intent i = getIntent();
                int id =i.getIntExtra("position",1);

                Toast.makeText(getApplicationContext(),"idは"+id+"desu", Toast.LENGTH_SHORT).show();


                //日付を表示する処理
                setContentView(R.layout.activity_money_update);
                final TextView dateText = (TextView) findViewById(R.id.dateText);
                Time time = new Time("Asia/Tokyo");
                time.setToNow();
                String date = time.year + "年" + (time.month + 1) + "月" + time.monthDay + "日　";
                dateText.setText(date);
                //押したときカレンダー表示
                dateText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar cal = Calendar.getInstance();

                        DatePickerDialog dialog = new DatePickerDialog(MoneyUpdateActivity.this, new DatePickerDialog.OnDateSetListener() {
                            public void onDateSet(DatePicker picker, int year, int month, int day) {
                                dateText.setText(year + "年" + (month + 1) + "月" + day + "日");
                            }
                        }
                                , cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)
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
