package ke4a11.ecc.ac.jp.afururu.Money;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import ke4a11.ecc.ac.jp.afururu.R;
import ke4a11.ecc.ac.jp.afururu.Setting.ShowDataBase;

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
        Button entryButton = (Button) findViewById(R.id.addButton);
       final Spinner spinner = (Spinner)findViewById(R.id.categorySpinner);

       // ((Spinner) findViewById(R.id.categorySpinner)).setSelection(2);

        entryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String shop = shopText.getText().toString();
                String memo = memoText.getText().toString();
                 // 選択されているアイテムのIndexを取得
                //int idx = spinner.getSelectedItemPosition();

                // 選択されているアイテムを取得
                String category = (String)spinner.getSelectedItem();

                ContentValues insertValues = new ContentValues();
                insertValues.put("shop", shop);
               insertValues.put("memo", memo);
               insertValues.put("category",category);
                long id = db.insert("ecc", shop, insertValues);
            }
        });
        Button showButton = (Button) findViewById(R.id.showButton);
        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dbIntent = new Intent(getApplicationContext(),MoneyShowDatabase.class);
                startActivity(dbIntent);
            }
        });

       /* Button dropButton = (Button) findViewById(R.id.dropButton);
        dropButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sql = "drop table person;";
                    db.execSQL(sql);

            }
        });*/

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
        TextView dateText = (TextView)findViewById(R.id.dateText);
        Time time = new Time("Asia/Tokyo");
        time.setToNow();
        String date = time.year + "年" + (time.month+1) + "月" + time.monthDay + "日　";
        dateText.setText(date);




    }
}
