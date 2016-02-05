package ke4a11.ecc.ac.jp.afururu.English;

import android.app.Fragment;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

import ke4a11.ecc.ac.jp.afururu.R;

public class DetailFragment extends Fragment implements TextToSpeech.OnInitListener {

    public static String detailEngText;


    public static DetailFragment newInstance(int index) {
        DetailFragment f = new DetailFragment();
        //フラグメントに、選択したアイテムの位置を持たせる
        Bundle args = new Bundle();
        args.putInt("index", index);
        f.setArguments(args);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_english_detail, container, false);
        final TextView text = (TextView) view.findViewById(R.id.text);
        //newInstance内のsetArgumentsで持った値を取得
        int index = getArguments().getInt("index", 0);
        //アイテムの位置より、内容を選択して表示
        text.setText(detailEngText);
        //インフレートしたプレビューを返して、フラグメントにビューを持たせる
        TextView et = (TextView) view.findViewById(R.id.text);
        Button bt = (Button) view.findViewById(R.id.speechbtn);

        final TextToSpeech tts = new TextToSpeech(getActivity(), this);
        tts.setLanguage(Locale.ENGLISH);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tts.speak(text.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        // ボタンのClickListenerの登録
        return view;

    }

    @Override
    public void onInit(int status) {

    }
}
