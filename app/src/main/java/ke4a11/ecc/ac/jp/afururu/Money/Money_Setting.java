package ke4a11.ecc.ac.jp.afururu.Money;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


import ke4a11.ecc.ac.jp.afururu.R;

public class Money_Setting extends Fragment {

    public static String selectedSpinner = "gbp";

    private Spinner spinner;

    //為替のURLに渡すString型の配列
    private final String[] rateName = {"gbp","eur","usd"};

    public static String set_balance = "0";

    public Money_Setting() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_money_setting, container, false);

        //今月の残金を代入
        TextView edit_balance = (TextView)view.findViewById(R.id.editText);
        set_balance = edit_balance.getText().toString();

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

                //selectedSpinner = (String)spinner.getSelectedItem();
                //Toast.makeText(getContext(),Integer.toString(spinner.getSelectedItemPosition()),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        return view;
    }

    public static Money_Setting newInstance() {
        Money_Setting money_setting = new Money_Setting();
        return money_setting;
    }
}
