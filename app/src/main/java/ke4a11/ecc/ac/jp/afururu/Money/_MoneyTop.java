package ke4a11.ecc.ac.jp.afururu.Money;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import ke4a11.ecc.ac.jp.afururu.R;

/**
 ********** 課題
 * setCurrencyメソッドのsetText内で￥と/£の決め打ちをコードで処理する必要がある
 * ￥は以外にいる？
 * /£は 配列に文字列として様々な通貨単位を入れて
 *  moneySpinerの方で値を変えた時に、一緒にintの値を変えてそれを配列の添字にする？
 *
 *  added!!!!!!!!!!!!
 *
 *
 */
public class _MoneyTop extends Fragment {
    //どの画面に飛ばすかのフラグ
    public static String moneyFlg = "cal";
    //各国為替のレート
    private TextView mView;
    //設定で選択された国
    private String moneySpinner ="gbp";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_money_top, container, false);

        //為替のデータ入力先変数
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

        //ボタン作成
        ImageButton calenderButton = (ImageButton)view.findViewById(R.id.calenderButton);
        ImageButton graphButton =(ImageButton)view.findViewById(R.id.graphButton);
        ImageButton exchangeButton =(ImageButton)view.findViewById(R.id.exchangeButton);
        ImageButton settingButton = (ImageButton)view.findViewById(R.id.settingButton);

        //リスナー設定
        calenderButton.setOnClickListener(new ChangeView());
        graphButton.setOnClickListener(new ChangeView());
        exchangeButton.setOnClickListener(new ChangeView());
        settingButton.setOnClickListener(new ChangeView());

        return view;
    }

    //リスナーボタン押下時処理
    class ChangeView implements OnClickListener {
        public void onClick(View v){
            if (v == getView().findViewById(R.id.calenderButton)) {
                moneyFlg = "cal";
            }else if (v == getView().findViewById(R.id.graphButton)){
                moneyFlg = "gra";
            }else if(v == getView().findViewById(R.id.exchangeButton)){
                moneyFlg = "exc";
            }else if(v == getView().findViewById(R.id.settingButton)){
                moneyFlg = "set";
            }

            //カレンダーは別のアクティビティのため
            if(moneyFlg.equals("cal")){
            }else{
                //Intent i = new Intent(getActivity().getApplicationContext(),MoneyActivity.class);
                Intent i = new Intent(getActivity().getApplicationContext(),MoneyActivity.class);
                startActivity(i);
            }
        }
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
