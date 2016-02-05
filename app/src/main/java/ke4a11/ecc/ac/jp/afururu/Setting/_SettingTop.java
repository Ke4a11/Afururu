package ke4a11.ecc.ac.jp.afururu.Setting;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ke4a11.ecc.ac.jp.afururu.Money._MoneyTop;
import ke4a11.ecc.ac.jp.afururu.R;

public class _SettingTop extends Fragment {
    //更新ボタン
    Button updatebtn;
    //住所のテキスト
    private EditText address_et;
    private EditText name_et;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting_top, container, false);
        //保存
        address_et = (EditText)view.findViewById(R.id.editAge);
        name_et = (EditText)view.findViewById(R.id.editName);

        //更新ボタン
        updatebtn = (Button)view.findViewById(R.id.update);
        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //address_etに何も入力されていない場合
                if (name_et.getText().toString().equals("")){
                    name_et.setText("");
                    Toast.makeText(getActivity().getApplicationContext(),"名前を入力してください。",Toast.LENGTH_SHORT).show();
                    //name_etに何も入力されていない場合
                }else if (address_et.getText().toString().equals("")){
                    address_et.setText("");
                    Toast.makeText(getActivity().getApplicationContext(),"住所を入力してください。",Toast.LENGTH_SHORT).show();
                }else{
                    //コードが長くなるため、一時的にbalanceの値を保持する変数
                    String tmp_address = address_et.getText().toString();
                    String tmp_name = name_et.getText().toString();

                    //EnteredBalance とい名前のテキスト(xml)ファイルを作成 key-valueで保存される
                    SharedPreferences sp = getContext().getSharedPreferences("Setting", Context.MODE_PRIVATE);
                    // プリファレンスに書き込むためのEditorオブジェクト取得
                    SharedPreferences.Editor editor = sp.edit();
                    //"name"というキーで名前を登録
                    // "address" というキーで名前を登録
                    editor.putString("name", tmp_name);
                    editor.putString("address", tmp_address);



                    // 書き込みの確定（実際にファイルに書き込む）
                    editor.commit();


                    Toast.makeText(getActivity(),"更新完了しました！" + tmp_name + "さん", Toast.LENGTH_LONG).show();

                }
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        //設定した残金の表示
        SharedPreferences sp = getContext().getSharedPreferences("Setting", Context.MODE_PRIVATE);
        String a = sp.getString("name", "");
        String b = sp.getString("address", "");
        if (a.equals(0) ){
            name_et.setText("");
        //}else if (a == 0) {
            //name_et.setText("名前を入力してください。");
        }else {
            //address_et.setText(String.valueOf(a));
            name_et.setText(String.valueOf(a));
        }
        if (b.equals(0)){
            address_et.setText("");
        }else{
            address_et.setText(String.valueOf(b));
        }
    }


    public static _SettingTop newInstance() {
        _SettingTop _settingtop = new _SettingTop();
        return _settingtop;
    }

}