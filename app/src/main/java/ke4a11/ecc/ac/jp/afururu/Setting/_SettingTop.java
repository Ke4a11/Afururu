package ke4a11.ecc.ac.jp.afururu.Setting;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ke4a11.ecc.ac.jp.afururu.R;

public class _SettingTop extends Fragment {
    //更新ボタン
    Button updatebtn;
    //住所のテキスト
    EditText address_et;
    EditText name_et;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting_top, container, false);


        address_et = (EditText)view.findViewById(R.id.editAge);
        name_et = (EditText)view.findViewById(R.id.editName);

        //更新ボタン
        updatebtn = (Button)view.findViewById(R.id.update);
        updatebtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //コードが長くなるため、一時的にbalanceの値を保持する変数
                String tmp_address = address_et.getText().toString();

                //EnteredBalance とい名前のテキスト(xml)ファイルを作成 key-valueで保存される
                SharedPreferences sp = getContext().getSharedPreferences("Setting", Context.MODE_PRIVATE);
                // プリファレンスに書き込むためのEditorオブジェクト取得
                SharedPreferences.Editor editor = sp.edit();
                // "address" というキーで名前を登録
                editor.putString("name", name_et.getText().toString());
                editor.putString("address", tmp_address);

                /*
                他に追加 の設定があればここに記述
                 */

                // 書き込みの確定（実際にファイルに書き込む）
                editor.commit();

            }
        });

        return view;

    }

}