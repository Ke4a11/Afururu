package ke4a11.ecc.ac.jp.afururu.Money;


import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.beardedhen.androidbootstrap.BootstrapButton;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import ke4a11.ecc.ac.jp.afururu.R;

/**
 * ********* TODO
 */
public class _MoneyTop extends Fragment {
    //どの画面に飛ばすかのフラグ
    public static String moneyFlg = "cal";
    //各国為替のレート 残金ビュー 支出ビュー 名前ビュー
    private TextView mView, balanceView, payoutView, NameView, suggestView;
    //各設定の準備
    private SharedPreferences sp;
    //設定の値を入れる。テーブル名を指定する時に使う
    private String setRateName;
    //設定の値を入れる。貨幣の記号を指定する時に使う
    private int setRatePosition;
    //String型の通貨きごう配列
    private final String[] unitName = {"£", "€", "$"};
    //為替の値で表示する提案の種類
    private final String[] suggestRate = {"本日の為替は今までで一番高いです","本日の為替は比較的に高いです","本日の為替は平均的です","本日の為替は比較的安いです","本日の為替は一番安いです！今日両替しましょう"};

    private MoneyOpenHelper helper;
    private SQLiteDatabase db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_money_top, container, false);

        //残金ビューの生成
        balanceView = (TextView) view.findViewById(R.id.balance);
        //為替ビューの生成
        mView = (TextView) view.findViewById(R.id.rate);
        //支出ビュー作成
        payoutView = (TextView) view.findViewById(R.id.payout);
        //名前ビューの作成
        NameView = (TextView) view.findViewById(R.id.greet);
        //為替情報ビューの作成
        suggestView = (TextView)view.findViewById(R.id.suggest);
        //メソッドで使うのでコード削減のためここで記述する。
        sp = getContext().getSharedPreferences("EnteredBalance", Context.MODE_PRIVATE);
        //持ってくる為替の指定
        setRateName = sp.getString("rate", "gbp");
        setRatePosition = sp.getInt("ratePosition", 0);

        //支出入力ボタン
        Button payoutButton = (Button) view.findViewById(R.id.payoutButton);
        payoutButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), MoneyInputActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(i);
            }
        });

        //ボタン作成
        Button remmoney = (Button) view.findViewById(R.id.remmoney);
        Button settingButton = (Button) view.findViewById(R.id.exchangeButton);

        //リスナー設定
        remmoney.setOnClickListener(new ChangeView());
        settingButton.setOnClickListener(new ChangeView());


        return view;
    }

    //リスナーボタン押下時処理
    class ChangeView implements OnClickListener {
        public void onClick(View v) {
            if (v == getView().findViewById(R.id.remmoney)) {
                moneyFlg = "set";
            } else if (v == getView().findViewById(R.id.exchangeButton)) {
                moneyFlg = "exc";
            }
            //カレンダーは別のアクティビティのため
            if (moneyFlg.equals("cal")) {
                Intent i = new Intent(getActivity().getApplicationContext(), MoneyActiviy_ListorCal.class);
                i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(i);
            } else {
                //Intent i = new Intent(getActivity().getApplicationContext(),MoneyActivity.class);
                Intent i = new Intent(getActivity().getApplicationContext(), MoneyActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(i);
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        //helperの生成
        helper = new MoneyOpenHelper(getContext());
        //dbを操作できるように
        db = helper.getWritableDatabase();

        //名前セット
        NameView.setText("Hello! " + getUser() + " さん");
        //残金ビューの表示,支出があれば支出チェックで計算している。
        balanceView.setText("£" + getBalance());
        //値の更新
        setRateName = sp.getString("rate", "gbp");
        setRatePosition = sp.getInt("ratePosition", 0);

        //ネットに繋がっていたら
        if (netWorkCheck(getContext().getApplicationContext())) {
            getcsv();
        }

        //テーブルの存在チェック、存在していなければ以降の処理も実行されない
        String tableCheck = "SELECT COUNT(*) FROM sqlite_master WHERE type='table' AND name='input';";
        Cursor cursor = db.rawQuery(tableCheck, null);
        cursor.moveToFirst();

        String a = cursor.getString(0);
        //テーブルが存在すれば 1 なければ 0
        if (a.equals("1")) {
            //rawQueryは他の使い方もある
            //String sql = "select * from ecc where id = ? ;"
            //.rawQuery( SQL文 , SQL文内の「?」を任意の値にかえる);
            String sql = "select sum(price) from input;";
            Cursor c = db.rawQuery(sql, null);

            float price;

            //DBの入力があれば支出ビューに金額が表示される
            if (c.moveToFirst()) {
                price = c.getFloat(0);
                payoutView.setText("£" + String.valueOf(price));

                //支出を元に残金ビューの値を計算
                int n = Integer.parseInt(getBalance());
                if (n != -9999) { //使用した金額があれば
                    n -= price;
                    balanceView.setText("£" + String.valueOf(n));
                    balanceView.setTextColor(Color.parseColor("gray"));
                    if (n < 0 && n != -9999) {//マイナスなら文字を赤色で表示
                        balanceView.setTextColor(Color.RED);
                    }
                }
            } else {
                payoutView.setText("支出はまだありません。");
                payoutView.setTextSize(20);
            }

            //レートの提案をするために
            String sqlrate = "select * from currency where rateType = " + setRatePosition + " order by date DESC;";
            Cursor c_rate = db.rawQuery(sqlrate, null);
            //1つ目は現在のレートとして表示されるため
            c_rate.moveToFirst();

            //現在日時を取得する
            Date tdate = new Date();
            DateFormat df1 = new SimpleDateFormat("yyyy/MM/dd");
            //変換
            String today = df1.format(tdate);

            //前回、前々回の部分を表示する
            int i = 1;
            while (i < 4) {
                //textViewのidを取得
                //一度空白にしないと、前表示していたものが残るため
                String tvid = "before" + i;
                int resId = getResources().getIdentifier(tvid, "id", getActivity().getPackageName());
                TextView tv = (TextView) getView().findViewById(resId);
                tv.setText("");
                if (c_rate.moveToNext()){
                    String date = c_rate.getString(0);
                    if (!today.equals(date)) {    //rateTypeが同じかつ今日でないなら表示
                        tv.setText("¥" + c_rate.getString(1));
                    }
                }
                i++;
            }

            cursor.close();
            c_rate.close();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        //Toast.makeText(getContext(),"Top onResume",Toast.LENGTH_SHORT).show();
    }
    /**
     * Activityが「onPause」になった場合や、Fragmentが変更更新されて操作を受け付けなくなった場合に呼び出される
     * DBクローズ
     */
    @Override
    public void onPause() {
        super.onPause();
//        Toast.makeText(getContext(),"Top onPause",Toast.LENGTH_SHORT).show();

    }
    @Override
    public void onStop(){
        super.onStop();
//        Toast.makeText(getContext(),"Top onStop",Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onDestroyView(){
        super.onDestroyView();
//        Toast.makeText(getContext(),"Top onDestroyView",Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
//        Toast.makeText(getContext(),"Top onDestroy",Toast.LENGTH_SHORT).show();
    }

    //Settingで編集した今月の使う金を返す
    public String getBalance() {
        int a = sp.getInt("balance", -9999);
        return String.valueOf(a);
    }

    public String getUser() {
        SharedPreferences sp1 = getContext().getSharedPreferences("Setting", Context.MODE_PRIVATE);
        String a = sp1.getString("name", "ゲストユーザー");
        return a;
    }

    //ネットに繋がっているかチェックするメソッド
    private boolean netWorkCheck(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info != null) {
            return info.isConnected(); //繋がっている
        } else {
            return false; //繋がっていない
        }
    }

    //為替をとってくるためのメソッド
    private void getcsv() {
        AsynCsv asynCsv = new AsynCsv(this, 0);
        asynCsv.execute("http://api.aoikujira.com/kawase/csv/" + setRateName);
    }

    //データが持ってこれた場合に実行される
    void setCurrency(String data) {
        /*
        * 簡単な説明
        * 持ってこれたら、為替ビューに表示。データベースを更新。前回のデータがあれば表示する。
        * 為替の種類はrateType列で区別する。
        *
         */
        //小数第１位のみ表示　２位以下切り捨て
        if (!data.equals("")) {
            String d = data;
            Float f = Float.parseFloat(d);
            data = String.format("%.1f", f);

            //持ってくる貨幣の記号
            String unit = unitName[setRatePosition];

            //現在日時を取得する
            Date tdate = new Date();
            DateFormat df1 = new SimpleDateFormat("yyyy/MM/dd");
            //変換
            String today = df1.format(tdate);

            //一番最後に更新した日にちを持ってくる
            String sqlcheck = "select * from currency order by date DESC;";
            Cursor c = db.rawQuery(sqlcheck, null);
            String tmpdate = null;
            int tmprT = -1;
            if (c.moveToFirst()) { //初回起動時にはデータはないためチェックしないと落ちる
                tmpdate = c.getString(0);
                tmprT = c.getInt(2);
            }

            //同じ日にちでも、貨幣が違うと新しい行が挿入されるので、いっそ全行検索する。日にちで比較するのはちょい面倒
            //一番最後の日にちと今日を比較して同じでなければ挿入
            if (!today.equals(tmpdate)) {
                ContentValues insertValues = new ContentValues();
                insertValues.put("date", today);
                insertValues.put("todayrate", data);
                insertValues.put("rateType", setRatePosition);

                long id = db.insert("currency", today, insertValues);

            } else if (tmprT != setRatePosition) {
                //日にちが同じだが、貨幣の種類が違う場合
                boolean checkInsrt = true;
                while (c.moveToNext() && checkInsrt) { //全件検索
                    tmpdate = c.getString(0);
                    tmprT = c.getInt(2);
                    if (today.equals(tmpdate) && tmprT == setRatePosition) {
                        //同じ日にちかつ、同じレートを一度挿入している場合
                        checkInsrt = false;
                    }
                }

                //一度も挿入していなければ実行される
                if (checkInsrt) {
                    ContentValues insertValues = new ContentValues();
                    insertValues.put("date", today);
                    insertValues.put("todayrate", data);
                    insertValues.put("rateType", setRatePosition);

                    long id = db.insert("currency", today, insertValues);
                }
            }
            mView.setText("1" + unit + " = ¥" + data);


            /*
            * 為替情報の更新   もう少しスマートなやり方はないものか
             */

            //大きい順で並ぶはず
            String rateSql = "select * from currency where rateType = " + setRatePosition + " order by todayrate DESC";
            Cursor rate_cursor = db.rawQuery(rateSql,null);
            rate_cursor.moveToFirst();

            //為替データの格納配列
            String[] tmp_todayrate = new String[rate_cursor.getCount()];

            //今日のレートがどの位置にいるか
            int thisrate = 0;

            //選択した為替情報だけ配列に格納する
            for(int i = 0;i < rate_cursor.getCount(); i++){
                tmp_todayrate[i] = rate_cursor.getString(1);
                rate_cursor.moveToNext();
                if (Float.parseFloat(tmp_todayrate[i]) >= Float.parseFloat(data)) {
                    //金額が、同じかそれ以上ならば
                    thisrate++;
                }
            }

            //為替はどれくらいの金額なのか
            if (thisrate == 0){ //一番高い
                suggestView.setText(suggestRate[0]);
            }else if (thisrate == tmp_todayrate.length){    //一番安い
                suggestView.setText(suggestRate[4]);
            }else if (thisrate <= tmp_todayrate.length/2){   //比較的高い
                suggestView.setText(suggestRate[1]);
            }else if (thisrate >= tmp_todayrate.length/2){   //比較的安い
                suggestView.setText(suggestRate[3]);
            }

            c.close();
            rate_cursor.close();

            db.close();

        }

    }

}
