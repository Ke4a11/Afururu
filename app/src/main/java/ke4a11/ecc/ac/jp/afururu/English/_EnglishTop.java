package ke4a11.ecc.ac.jp.afururu.English;


import android.content.Intent;
import android.os.Bundle;
//FragmentPagerAdapter で使うために supportlibrary の fragment を使用
//
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;

import ke4a11.ecc.ac.jp.afururu.R;

public class _EnglishTop extends Fragment {


    public _EnglishTop() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_english_top, container, false);

        //ボタン作成
        ImageButton engresButton = (ImageButton)view.findViewById(R.id.engresButton);
        ImageButton enghotButton = (ImageButton)view.findViewById(R.id.enghotButton);
        ImageButton engairButton = (ImageButton)view.findViewById(R.id.engairButton);
        ImageButton engschButton = (ImageButton)view.findViewById(R.id.engschButton);

        //リスナの設定
        engresButton.setOnClickListener(new ChangeView());
        enghotButton.setOnClickListener(new ChangeView());
        engairButton.setOnClickListener(new ChangeView());
        engschButton.setOnClickListener(new ChangeView());

        return view;
    }


    //リスナの内容
    class ChangeView implements OnClickListener{

        public void onClick(View v){

            if(v == getView().findViewById(R.id.engresButton)){
                //押したボタンに対応したフレーズを代入する
                English_Textview_Activity.titleEngText = Eng_Text.TITLES_hotel;
                English_Detail.detailEngText = Eng_Text.DIALOG_hotel;

                Intent intent = getActivity().getIntent();
                intent.setClass(getActivity(), English_Textview_Activity.class);
                //これがないと初回起動時以外反応しない
                //Activityが破棄されていないのでstartされない（たぶｎ）
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }else if(v == getView().findViewById(R.id.enghotButton)){
                English_Textview_Activity.titleEngText = Eng_Text.TITLES_airport;
                English_Detail.detailEngText = Eng_Text.DIALOG_airport;

                Intent intent = getActivity().getIntent();
                intent.setClass(getActivity(), English_Textview_Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }else if(v == getView().findViewById(R.id.engairButton)){
                English_Textview_Activity.titleEngText = Eng_Text.TITLES_res;
                English_Detail.detailEngText = Eng_Text.DIALOG_res;

                Intent intent = getActivity().getIntent();
                intent.setClass(getActivity(), English_Textview_Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }else if(v == getView().findViewById(R.id.engschButton)){
                English_Textview_Activity.titleEngText = Eng_Text.TITLES_life;
                English_Detail.detailEngText = Eng_Text.DIALOG_life;

                Intent intent = getActivity().getIntent();
                intent.setClass(getActivity(), English_Textview_Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }

        }

    }


}
