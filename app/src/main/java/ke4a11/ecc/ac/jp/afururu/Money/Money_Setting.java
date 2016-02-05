package ke4a11.ecc.ac.jp.afururu.Money;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
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
import android.widget.Toast;

import ke4a11.ecc.ac.jp.afururu.R;

/*
* *****簡単な説明*****
* preferenceを使っているが、値は上書き更新される
*
*
 */

public class Money_Setting extends Fragment {

    public static String selectedSpinner = "gbp";

    private Spinner spinner;

    //今月使う金額の入力
    private EditText edit_balance;

    //為替のURLに渡すString型の配列
    private final String[] rateName = {"gbp","eur","usd"};

    //更新ボタン
    private Button updatebtn;

    public Money_Setting() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_money_setting, container, false);

        //保存
        edit_balance = (EditText)view.findViewById(R.id.edit_balanceMoney);

        //更新ボタンを押した時内容が変わっていれば更新する
        updatebtn = (Button)view.findViewById(R.id.updatebtn);
        updatebtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                //edit_balanceに何も入力されていない場合
                if (edit_balance.getText().toString().equals("")){
                    edit_balance.setText("0");
                    Toast.makeText(getActivity().getApplicationContext(),"残金に値を入力してください。",Toast.LENGTH_SHORT).show();
                }else if (edit_balance.getText().toString().equals("残金に値を入力してください。")){
                    edit_balance.setText("0");
                    Toast.makeText(getActivity().getApplicationContext(),"値を入力してください。",Toast.LENGTH_SHORT).show();
                }else {

                    //コードが長くなるため、一時的にbalanceの値を保持する変数
                    int tmp_balance = Integer.parseInt(edit_balance.getText().toString());

                    //EnteredBalance とい名前のテキスト(xml)ファイルを作成 key-valueで保存される
                    SharedPreferences sp = getContext().getSharedPreferences("EnteredBalance", Context.MODE_PRIVATE);
                    // プリファレンスに書き込むためのEditorオブジェクト取得
                    Editor editor = sp.edit();
                    // "balance" というキーで名前を登録
                    editor.putInt("balance", tmp_balance);

                /*
                他に追加 の設定があればここに記述
                 */

                    // 書き込みの確定（実際にファイルに書き込む）
                    editor.commit();

                    Toast.makeText(getContext(),"更新完了しました！",Toast.LENGTH_SHORT).show();
                }

            }
        });

        //preference の値を全て解放する
        Button resetbtn = (Button)view.findViewById(R.id.resetbtn);
        resetbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //EnteredBalance とい名前のテキスト(xml)ファイルを作成 key-valueで保存される
                SharedPreferences sp = getContext().getSharedPreferences("EnteredBalance", Context.MODE_PRIVATE);
                sp.edit().clear().commit();
                Toast.makeText(getContext(), "初期化完了しました！", Toast.LENGTH_SHORT).show();

            }
        });



        //spinner用のアダプターを作成
        ArrayAdapter<String> ad = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //アイテム追加

        ad.add("イギリス(pound)");
        ad.add("ユーロ(euro)");
        ad.add("アメリカドル(usdollar)");
        spinner = (Spinner)view.findViewById(R.id.MoneySpinner);

        spinner.setAdapter(ad);

        //スピナーのリスナの設定
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //選択されたレートから、為替に渡す配列を決める
                selectedSpinner = rateName[spinner.getSelectedItemPosition()];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
    });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        //設定した残金の表示
        SharedPreferences sp = getContext().getSharedPreferences("EnteredBalance", Context.MODE_PRIVATE);
        int a = sp.getInt("balance", -9999);
        if (a == -9999){
            edit_balance.setText("金額を入力してください");
        }else{
            edit_balance.setText(String.valueOf(a));
        }
    }

    public static Money_Setting newInstance() {
        Money_Setting money_setting = new Money_Setting();
        return money_setting;
    }
}
