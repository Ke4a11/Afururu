package ke4a11.ecc.ac.jp.afururu.Money;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MoneyOpenHelper extends SQLiteOpenHelper {

    static final String CREATE_TABLE_INPUT =
            "create table input(" + "id integer primary key autoincrement," + " date text not null," + " category integer not null,"
                                            +" shop text not null," + " memo text not null," + "price text not null" + ");";

    static final String CREATE_TABLE_CURRENCY =
            "create table currency("+ "date text not null,"+" todayrate text not null,"+ " rateType integer not null" +");";

        public MoneyOpenHelper(Context context) {
            super(context, "Afrdatabase", null, 2);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(CREATE_TABLE_INPUT);
            db.execSQL(CREATE_TABLE_CURRENCY);

            // db.execSQL("create table ecc(" + "id integer primary key autoincrement," + " shop text not null," + " memo text not null,"
           // +" category integer not null," + " date text not null," + "price text not null" + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }

    }

///data/data/ke4a11.ecc.ac.jp.afururu/databases