package ke4a11.ecc.ac.jp.afururu.Money;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import ke4a11.ecc.ac.jp.afururu.R;

/**
 ********** 課題
 * setCurrencyメソッドのsetText内で￥と/£の決め打ちをコードで処理する必要がある
 * ￥は以外にいる？
 * /£は 配列に文字列として様々な通貨単位を入れて
 *  moneySpinerの方で値を変えた時に、一緒にintの値を変えてそれを配列の添字にする？
 */
public class _MoneyTop extends Fragment {

    private MoneyActivity parent;
    private TextView mView;
    private String moneySpinner ="gbp";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_money_top, container, false);

        //為替のデータ入力先変数
        mView = (TextView)view.findViewById(R.id.rate);

        //ネットに繋がっているなら為替を持ってくる
        if(netWorkCheck(getContext().getApplicationContext())){
            getcsv();
        }

        return view;
    }
/*
    //リスナーボタン押下時処理
    class ChangeView implements OnClickListener {
        public void onClick(View v){
            if (v == getView().findViewById(R.id.calenderButton)){
                parent.ToMoney_Calender();
                //}else if(v == getView().findViewById(R.id.graphButton)){
                //    parent.ToMoney_Graph();
                //}else if(v == getView().findViewById(R.id.exchangeButton)){
                //    parent.ToMoney_Exchange();
            }else if (v == getView().findViewById(R.id.settingButton)){
                parent.ToMoney_Set();
            }
        }
    }*/

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
