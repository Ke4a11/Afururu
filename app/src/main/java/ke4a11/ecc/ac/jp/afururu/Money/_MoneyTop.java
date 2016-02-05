package ke4a11.ecc.ac.jp.afururu.Money;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
    //名前ビュー
    private TextView NameView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_money_top, container, false);

        //残金ビューの生成
        balanceView = (TextView) view.findViewById(R.id.balance);
        //名前ビューの生成
        NameView = (TextView)view.findViewById(R.id.greet);

        //為替ビューの生成
        mView = (TextView)view.findViewById(R.id.rate);

        //持ってくる為替の指定
        if(moneySpinner != null){
            //なければ押されたものをとってくる
            moneySpinner = Money_Setting.selectedSpinner;
        }

        //ネットに繋がっているなら為替を持ってくる
        if(netWorkCheck(getContext().getApplicationContext())){
            getcsv();
        }

        BootstrapButton payoutButton = (BootstrapButton)view.findViewById(R.id.payoutButton);
        payoutButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),MoneyInputActivity.class);
                startActivity(i);
            }
        });

        //ボタン作成
//        Button calenderButton = (Button)view.findViewById(R.id.calendarButton);
//        Button exchangeButton =(Button)view.findViewById(R.id.exchangeButton);
//        Button settingButton = (Button)view.findViewById(R.id.settingButton);

        //リスナー設定
//        calenderButton.setOnClickListener(new ChangeView());
//        exchangeButton.setOnClickListener(new ChangeView());
//        settingButton.setOnClickListener(new ChangeView());


        return view;
    }

    //リスナーボタン押下時処理
    class ChangeView implements OnClickListener {
        public void onClick(View v){
//            if (v == getView().findViewById(R.id.calendarButton)) {
//                moneyFlg = "cal";
//            }else if(v == getView().findViewById(R.id.exchangeButton)){
//                moneyFlg = "exc";
//            }else if(v == getView().findViewById(R.id.settingButton)){
//                moneyFlg = "set";
//            }
//
//            //カレンダーは別のアクティビティのため
//            if(moneyFlg.equals("cal")){
//                Intent i = new Intent(getActivity().getApplicationContext(),MoneyActiviy_ListorCal.class);
//                startActivity(i);
//            }else{
//                //Intent i = new Intent(getActivity().getApplicationContext(),MoneyActivity.class);
//                Intent i = new Intent(getActivity().getApplicationContext(),MoneyActivity.class);
//                startActivity(i);
//            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        //Toast.makeText(getContext(),"Top onStart",Toast.LENGTH_SHORT).show();

        //残金読み込み
        balanceView.setText("£" + getBalance());

        //為替の再読みこみ
        if(moneySpinner != null){
            moneySpinner = Money_Setting.selectedSpinner;
            //ネットに繋がっていたら
            if(netWorkCheck(getContext().getApplicationContext())){
                getcsv();
            }
        }

        //ユーザー名取得
        NameView.setText("こんにちは " + getUser() + " さん!");
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
        int a = sp.getInt("balance", -1);
        return String.valueOf(a);
    }

    //Settingで編集した名前を返す
    public String getUser(){
        SharedPreferences sp = getContext().getSharedPreferences("Setting", Context.MODE_PRIVATE );
        String a = sp.getString("name", "ゲストユーザー" + "");
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
        AsynCsv asynCsv = new AsynCsv(this);
        asynCsv.execute("http://api.aoikujira.com/kawase/csv/" + moneySpinner);
    }

    //rate に現在のレートを代入
    void setCurrency(String data){
        //小数第１位のみ表示　２位以下切り捨て
        String d = data;
        Float f = Float.parseFloat(d);
        data = String.format("%.1f",f);
        mView.setText("¥" + data + "/£");
    }

}
