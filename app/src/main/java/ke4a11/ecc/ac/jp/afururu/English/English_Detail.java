package ke4a11.ecc.ac.jp.afururu.English;


import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ke4a11.ecc.ac.jp.afururu.R;

public class English_Detail extends Fragment {

    public static String[] detailEngText;
    public static String[] titleEngText;

    public English_Detail() {
        // Required empty public constructor
    }

    public static English_Detail newInstance(int index) {
        English_Detail f = new English_Detail();
        //フラグメントに、選択したアイテムの位置を持たせる
        Bundle args = new Bundle();
        args.putInt("index", index);
        f.setArguments(args);
        return f;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //fragment_details.xmlをインフレート(膨らませる)してフラグメントに追加
        View view = inflater.inflate(R.layout.fragment_english_detail, container, false);
        TextView text = (TextView) view.findViewById(R.id.text);
        //newInstance内のsetArgumentsで持った値を取得
        int index = getArguments().getInt("index", 0);
        //アイテムの位置より、内容を選択して表示
        text.setText(detailEngText[index] + "\n" + English_Textview_Activity.titleEngText[index]);
        //表示したテキストの色を変える

        //インフレートしたプレビューを返して、フラグメントにビューを持たせる
        return view;
    }


}
