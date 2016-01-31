//package ke4a11.ecc.ac.jp.afururu.Money;
//
//import android.app.Activity;
//import android.graphics.Color;
//import android.net.Uri;
//import android.os.Bundle;
//import android.app.Fragment;
//import android.support.design.widget.FloatingActionButton;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.Spinner;
//
//import ke4a11.ecc.ac.jp.afururu.R;
//
//public class Money_Input extends Fragment {
//    public static String selectedSpinner = "食費";
//
//    private Spinner spinner;
//
//    //カテゴリ別のString型の配列
//    private final String[] rateName = {"食費","外食費","交通費"};
//
//    public Money_Input() {
//        // Required empty public constructor
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_money_input, container, false);
//
//
//        //spinner用のアダプターを作成
//        ArrayAdapter<String> ad = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item);
//        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        //アイテム追加
//        ad.add("食費");
//        ad.add("外食費");
//        ad.add("交通費");
//        spinner = (Spinner)view.findViewById(R.id.MoneySpinner);
//
//        spinner.setAdapter(ad);
//
//        //スピナーのリスナの設定
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                //選択されたレートから、為替に渡す配列を決める
//                selectedSpinner = rateName[spinner.getSelectedItemPosition()];
//
//                //selectedSpinner = (String)spinner.getSelectedItem();
//                //Toast.makeText(getContext(),Integer.toString(spinner.getSelectedItemPosition()),Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });
//
//        return view;
//    }
//
//    public static Money_Input newInstance() {
//        Money_Input money_input = new Money_Input();
//        return money_input;
//    }
//}
