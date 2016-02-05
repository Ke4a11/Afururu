package ke4a11.ecc.ac.jp.afururu.Money;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;



import ke4a11.ecc.ac.jp.afururu.R;

public class Money_Exchange extends Fragment {

    //通貨変換で選択された国
    private String kawaseSpinner ="gbp";
    private Spinner spinner;
    //為替のURLに渡すString型の配列
    private final String[] rateName = {"gbp","eur","usd"};
    //結果表示用
    private TextView mView;
    private EditText fromEdit;

    private float money;

    //計算用のint型の為替の値
    private int exint;

    private int result;

    public static Money_Exchange newInstance() {
        Money_Exchange money_exchange = new Money_Exchange();
        return money_exchange;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_money_exchange, container, false);

        fromEdit = (EditText)view.findViewById(R.id.fromEdit);


        //為替ビューの生成
        mView = (TextView)view.findViewById(R.id.resultjpView);

        //計算ボタンよう
        Button exstartButton = (Button) view.findViewById(R.id.exstartButton);
        exstartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getcsv();

              //

             //   exint = Integer.parseInt(strmoney);
             //exint =(int)money;
             //

                //


            }
        });
        //spinner用のアダプターを作成
        ArrayAdapter<String> ad = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //アイテム追加
        ad.add("イギリス(pound)");
        ad.add("ユーロ(euro)");
        ad.add("アメリカドル(usdollar)");
        spinner = (Spinner)view.findViewById(R.id.kawaseSpinner);

        spinner.setAdapter(ad);

        //スピナーのリスナの設定
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //選択されたレートから、為替に渡す配列を決める
                kawaseSpinner = rateName[spinner.getSelectedItemPosition()];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        return view;
    }

    //為替をとってくるためのメソッド
    private void getcsv(){
        AsynCsv asynCsv = new AsynCsv(this,1);
        asynCsv.execute("http://api.aoikujira.com/kawase/csv/" + kawaseSpinner);
    }

    //rate に現在のレートを代入
    void setCurrency(String data){

        //小数第１位のみ表示　２位以下切り捨て
        String d = data;
        Float f = Float.parseFloat(d);
        data = String.format("%.1f", f);

        float money = Float.parseFloat(data);

        float frmmoney = Float.parseFloat(fromEdit.getText().toString());

        float result = money*frmmoney;

        String resulttxt = String.format("%.1f",result);

                mView.setText(resulttxt);
    }

}
