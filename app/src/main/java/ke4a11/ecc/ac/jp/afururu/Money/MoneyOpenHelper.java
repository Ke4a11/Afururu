package ke4a11.ecc.ac.jp.afururu.Money;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by masahirohigashi on 16/01/19.
 */
public class MoneyOpenHelper extends SQLiteOpenHelper {


        public MoneyOpenHelper(Context context) {
            super(context, "ShopMemoDB4", null, 2);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table ecc(" + "id integer primary key," + " shop text not null," + " memo text not null,"
                    +" category text not null," + " date text not null," + "price integer not null" + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }

    }

///data/data/ke4a11.ecc.ac.jp.afururu/databases