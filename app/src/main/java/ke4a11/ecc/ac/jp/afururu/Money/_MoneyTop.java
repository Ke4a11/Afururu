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
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.beardedhen.androidbootstrap.BootstrapButton;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import ke4a11.ecc.ac.jp.afururu.R;

/**
 ********** TODO
 * setCurrencyメソッドのsetText内で￥と/£の決め打ちをコードで処理する必要がある
 * ￥は以外にいる？
 * /£は 配列に文字列として様々な通貨単位を入れて
 *  moneySpinerの方で値を変えた時に、一緒にintの値を変えてそれを配列の添字にする？
 *
 *  画面遷移時にカレンダーが出てくる。レイアウトファイルがおかしい？
 *
 */
public class _MoneyTop extends Fragment {
    //どの画面に飛ばすかのフラグ
    public static String moneyFlg = "cal";
    //各国為替のレート
    private TextView mView;
    //設定で選択された国
    private String moneySpinner ="gbp";
    //残金ビュー
    private TextView balanceView;
    //支出ビュー
    private TextView payoutView;
    //名前ビュー
    private TextView NameView;

    //為替のURLに渡すString型の配列
    private final String[] rateName = {"gbp","eur","usd"};

    //String型の通貨きごう配列
    private final String[] unitName = {"£","€","$"};

    //為替の通貨単位
    private String currencyunit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_money_top, container, false);

        //残金ビューの生成
        balanceView = (TextView) view.findViewById(R.id.balance);
        //為替ビューの生成
        mView = (TextView)view.findViewById(R.id.rate);
        //支出ビュー作成
        payoutView = (TextView)view.findViewById(R.id.payout);
        //名前ビューの作成
        NameView = (TextView)view.findViewById(R.id.greet);

        //持ってくる為替の指定
        if(moneySpinner != null){
            //なければ押されたものをとってくる
            moneySpinner = Money_Setting.selectedSpinner;
        }

        //ネットに繋がっているなら為替を持ってくる
        if(netWorkCheck(getContext().getApplicationContext())){
            getcsv();
        }

        Button payoutButton = (Button)view.findViewById(R.id.payoutButton);
        payoutButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),MoneyInputActivity.class);
                startActivity(i);
            }
        });

        //ボタン作成
       Button remmoney = (Button)view.findViewById(R.id.remmoney);
       Button settingButton =(Button)view.findViewById(R.id.exchangeButton);
    //    Button settingButton = (Button)view.findViewById(R.id.settingButton);

        //リスナー設定
        remmoney.setOnClickListener(new ChangeView());
        settingButton.setOnClickListener(new ChangeView());
//        settingButton.setOnClickListener(new ChangeView());


        return view;
    }

    //リスナーボタン押下時処理
    class ChangeView implements OnClickListener {
        public void onClick(View v){
           if (v == getView().findViewById(R.id.remmoney)) {
                moneyFlg = "set";
            }else if(v == getView().findViewById(R.id.exchangeButton)){
                moneyFlg = "exc";
            }
//
            //カレンダーは別のアクティビティのため
            if(moneyFlg.equals("cal")){
                Intent i = new Intent(getActivity().getApplicationContext(),MoneyActiviy_ListorCal.class);
                startActivity(i);
            }else{
                //Intent i = new Intent(getActivity().getApplicationContext(),MoneyActivity.class);
                Intent i = new Intent(getActivity().getApplicationContext(),MoneyActivity.class);
                startActivity(i);
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        //Toast.makeText(getContext(),"Top onStart",Toast.LENGTH_SHORT).show();

        //名前セット
        NameView.setText("Hello! " + getUser() + " さん");
        //残金ビューの表示,支出があれば支出チェックで計算している。
        balanceView.setText("£" + getBalance());

        //為替の再読みこみ
        if(moneySpinner != null){
            moneySpinner = Money_Setting.selectedSpinner;
            //ネットに繋がっていたら
            if(netWorkCheck(getContext().getApplicationContext())){
                getcsv();
            }
        }

        //支出の計算
        MoneyOpenHelper helper = new MoneyOpenHelper(getActivity().getApplicationContext());
        SQLiteDatabase db = helper.getReadableDatabase();

        //テーブルの存在チェック、存在していなければ以降の処理も実行されない
        String tableCheck = "SELECT COUNT(*) FROM sqlite_master WHERE type='table' AND name='ecc';";
        Cursor cursor = db.rawQuery(tableCheck, null);
        cursor.moveToFirst();

        String a = cursor.getString(0);
        //テーブルが存在すれば 1 なければ 0
        if (a.equals("1")){
            //rawQueryは他の使い方もある
            //String sql = "select * from ecc where id = ? ;"
            //.rawQuery( SQL文 , SQL文内の「?」を任意の値にかえる);
            String sql = "select sum(price) from ecc;";
            Cursor c = db.rawQuery(sql,null);

            boolean isEof = c.moveToFirst();

            float price;

            //DBの入力があれば支出ビューに金額が表示される
            if (isEof) {
                price = c.getFloat(0);
                payoutView.setText("£" + String.valueOf(price));

                //支出を元に残金ビューの値を計算
                int n = Integer.parseInt(getBalance());
                if (n != -9999){
                    n -= price;
                    balanceView.setText("£" + String.valueOf(n));
                    balanceView.setTextColor(Color.parseColor("gray"));
                    if (n < 0 && n != -9999){//マイナスなら文字を赤色で表示
                        balanceView.setTextColor(Color.RED);
                    }
                }
            }else{
                payoutView.setText("支出はまだありません。");
                payoutView.setTextSize(20);
            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        //Toast.makeText(getContext(),"Top onResume",Toast.LENGTH_SHORT).show();
    }

    /***
     * Activityが「onPause」になった場合や、Fragmentが変更更新されて操作を受け付けなくなった場合に呼び出される
     */
    @Override
    public void onPause() {
        super.onPause();
        //Toast.makeText(getContext(),"Top onPause",Toast.LENGTH_SHORT).show();
    }

    //Settingで編集した今月の使う金を返す
    public String getBalance(){
        SharedPreferences sp = getContext().getSharedPreferences("EnteredBalance", Context.MODE_PRIVATE );
        int a = sp.getInt("balance", -9999);
        return String.valueOf(a);
    }

    public String getUser(){
        SharedPreferences sp = getContext().getSharedPreferences("Setting",Context.MODE_PRIVATE);
        String a = sp.getString("name","ゲストユーザー");
        return a;
    }

    //ネットに繋がっているかチェックするメソッド
    private boolean netWorkCheck(Context context){
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info != null){
            return info.isConnected(); //繋がっている
        }else{
            return false; //繋がっていない
        }
    }

    //為替をとってくるためのメソッド
    private void getcsv(){
        AsynCsv asynCsv = new AsynCsv(this,0);
        asynCsv.execute("http://api.aoikujira.com/kawase/csv/" + moneySpinner);
    }

    //rate に現在のレートを代入
    void setCurrency(String data){
        //小数第１位のみ表示　２位以下切り捨て
        String d = data;
        Float f = Float.parseFloat(d);
        data = String.format("%.1f",f);


        //日付をスラッシュ区切りでプライマリーキーとdataをDBに登録
        //OpenHelper準備
        MoneyOpenHelper helper = new MoneyOpenHelper(getContext());
        final SQLiteDatabase db = helper.getWritableDatabase();
        //現在日時を取得する
        Calendar c = Calendar.getInstance();
        //Dateを生成
        Date tdate = new Date();

        DateFormat df1 = new SimpleDateFormat("yyyy/MM/dd");
        // 変換
        String sDate = df1.format(tdate);

        ContentValues insertValues = new ContentValues();
        insertValues.put("date", String.valueOf(sDate));
        insertValues.put("todayrate", data);

        long id = db.insert("currency",data, insertValues);

        //持ってくる為替の指定
            moneySpinner = Money_Setting.selectedSpinner;

        String a = "";
        for(int i = 0; i < rateName.length; i++){
            if (rateName[i].equals(moneySpinner)){
                a = unitName[i];
            }
        }

//        mView.setText("¥" + data + "/" + a)

        mView.setText("1"+a+" = ¥" + data );
    }

}
