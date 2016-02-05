package ke4a11.ecc.ac.jp.afururu.English;

import java.util.Locale;
import java.util.Queue;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;

import ke4a11.ecc.ac.jp.afururu.R;


public class _EnglishTop extends Fragment {

    // ボタンを管理する変数

    private ImageButton enghotButton;
    private ImageButton engresButton;
    private ImageButton engairButton;
    private ImageButton engschButton;
    private ImageButton emeButton;
    private ImageButton teiButton;
    // private ImageButton 新しいボタン;

    //HashMapのリストを出す
    private LinkedHashMap<String, List<String>> eng_category;

    @Override     // 必要な手続きを書いておく
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_english_top, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        //ボタン作成

        enghotButton = (ImageButton) getActivity().findViewById(R.id.enghotButton);
        engresButton = (ImageButton) getActivity().findViewById(R.id.engresButton);
        engairButton = (ImageButton) getActivity().findViewById(R.id.engairButton);
        engschButton = (ImageButton) getActivity().findViewById(R.id.engschButton);
        emeButton = (ImageButton) getActivity().findViewById(R.id.emeButton);
        teiButton = (ImageButton) getActivity().findViewById(R.id.teiButton);

        //新しいボタン = (ImageButton) getActivity().findViewById(R.id.enghotButton);

        //top画像
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[][] english;
                LinkedHashMap<String, List<String>> japanese;

                switch (v.getId()) {

                    case R.id.enghotButton: // ホテル
                        english = Eng_Text.DIALOG_hotel;
                        japanese = Category.getHotelInfo();
                        break;

                    case R.id.engresButton: // レストラン
                        english = Eng_Text.DIALOG_res;
                        japanese = Category.getrestInfo();
                        break;

                    case R.id.engairButton: // 空港
                        english = Eng_Text.DIALOG_airport;
                        japanese = Category.getairInfo();
                        break;

                    case R.id.engschButton: // 学校
                        english = Eng_Text.DIALOG_life;
                        japanese = Category.getschoolInfo();
                        break;

                    case R.id.emeButton: // 緊急
                        english = Eng_Text.DIALOG_emergency;
                        japanese = Category.getemergencyInfo();
                        break;

                    case R.id.teiButton: // 丁寧
                        english = Eng_Text.DIALOG_airport;
                        japanese = Category.getairInfo();
                        break;






//                    case R.id.enghotButton: // 新しいシーン
//                        english = Eng_Text.DIALOG_hotel;
//                        japanese = Category.getHotelInfo();
//                        break;
                    default:
                        english = Eng_Text.DIALOG_hotel;
                        japanese = Category.getHotelInfo();
                }

                // 次の画面にデータを送る
                ExpandableListViewActivity.english = english;
                ExpandableListViewActivity.japanese = japanese;

                // 次の画面に遷移する
                Intent intent = getActivity().getIntent();
                intent.setClass(getActivity(), ExpandableListViewActivity.class);

                //これがないと初回起動時以外反応しない
                //Activityが破棄されていないのでstartされない（たぶｎ）
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        };

        //リスナの設定
        enghotButton.setOnClickListener(onClickListener);
        engresButton.setOnClickListener(onClickListener);
        engairButton.setOnClickListener(onClickListener);
        engschButton.setOnClickListener(onClickListener);
        emeButton.setOnClickListener(onClickListener);
        teiButton.setOnClickListener(onClickListener);
    }
}




