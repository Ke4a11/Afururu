package ke4a11.ecc.ac.jp.afururu.Money;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import ke4a11.ecc.ac.jp.afururu.R;
//import ke4a11.ecc.ac.jp.afururu.Setting.MyOpenHelper;

/**
 * Created by masahirohigashi on 16/01/19.
 */
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

        // queryメソッドの実行例
        Cursor c = db.query("ecc", new String[] { "date","shop", "category","memo"}, null,
                null, null, null, null);
        boolean mov = c.moveToFirst();

        while (mov) {
            TextView textView = new TextView(this);
            textView.setText(String.format("%s : %s : %s : %s", c.getString(0), c.getString(1) ,c.getString(2), c.getString(3)));
            mov = c.moveToNext();
            layout.addView(textView);

        }

        c.close();
        db.close();
    }


}
