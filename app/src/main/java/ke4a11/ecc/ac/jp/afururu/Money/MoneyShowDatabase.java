package ke4a11.ecc.ac.jp.afururu.Money;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import ke4a11.ecc.ac.jp.afururu.R;

public class MoneyShowDatabase extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.money_show_database);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        setContentView(layout);
        MoneyOpenHelper helper = new MoneyOpenHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c;

        Intent i = getIntent();
        if (i != null){
            int a = i.getIntExtra("flg",-1);
            if (a != 1){   //rate確認ならば
                SharedPreferences sp = this.getSharedPreferences("EnteredBalance", this.MODE_PRIVATE);
                String rate = sp.getString("rate","gbp");
                int rateType = sp.getInt("ratePosition",0);

                //どのテーブルのなのか
                TextView tv = new TextView(this);
                tv.setText("このテーブルは" + rate + "です。");
                layout.addView(tv);

                String sql = "select * from currency where rateType = " + rateType + ";";
                c = db.rawQuery(sql,null);

                boolean mov = c.moveToFirst();

                while (mov){
                    TextView textView = new TextView(this);
                    textView.setText(String.format("%s : %s : %d", c.getString(0), c.getString(1), c.getInt(2)));
                    mov = c.moveToNext();
                    layout.addView(textView);
                }

                c.close();
                db.close();

            }else{          //MoneyInputActivity の場合
                // queryメソッドの実行例
                c = db.query("input", new String[]{"id", "date", "shop", "category", "memo", "price"}, null,
                        null, null, null, null);
                boolean mov = c.moveToFirst();

                while (mov) {
                    TextView textView = new TextView(this);
                    textView.setText(String.format("%d : %s : %s : %d : %s : %s", c.getInt(0), c.getString(1) ,c.getString(2), c.getInt(3),c.getString(4),c.getString(5)));
                    mov = c.moveToNext();
                    layout.addView(textView);
                }

                c.close();
                db.close();
            }
        }else {
            Toast.makeText(getApplicationContext(),"getIntentできませんでした。",Toast.LENGTH_SHORT).show();
        }
    }


}
