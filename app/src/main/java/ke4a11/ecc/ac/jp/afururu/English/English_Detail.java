package ke4a11.ecc.ac.jp.afururu.English;

import java.util.*;

import java.util.Queue;

import android.widget.AdapterView;
import android.widget.Button;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.speech.tts.TextToSpeech;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;

import ke4a11.ecc.ac.jp.afururu.R;



public class English_Detail extends Fragment implements AdapterView.OnItemClickListener{

    public static String[] detailEngText;
    private String string;
    TextToSpeech tts;
    Button bt;
    TextView et;



    //extToSpeech tts = new TextToSpeech(getContext(),this);

    class SampleClickListenr implements View.OnClickListener
    {

        public void onClick(View v){
            String str = et.getText().toString();
            if(str != null)
            // アラートダイアログを表示します
            {
                tts.speak(str, TextToSpeech.QUEUE_FLUSH, null);
            }
        }
    }
    class SampleInitListner implements TextToSpeech.OnInitListener
    {
        public void onInit(int status){}

    }
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        // アラートダイアログのタイトルを設定します
        //alertDialogBuilder.setTitle("再生中...");
        // アラートダイアログのメッセージを設定します
       // alertDialogBuilder.setMessage("メッセージ");
        //alertDialogBuilder.setCancelable(true);
        //AlertDialog alertDialog = alertDialogBuilder.create();
       // alertDialog.show();


        //fragment_details.xmlをインフレート(膨らませる)してフラグメントに追加
        View view = inflater.inflate(R.layout.fragment_english_detail, container, false);
        TextView text = (TextView) view.findViewById(R.id.text);
        //newInstance内のsetArgumentsで持った値を取得
        int index = getArguments().getInt("index", 0);
        //アイテムの位置より、内容を選択して表示
        text.setText(detailEngText[index]);
        //インフレートしたプレビューを返して、フラグメントにビューを持たせる
        et = (TextView)view.findViewById(R.id.text);
        bt = (Button)view.findViewById(R.id.speechbtn);

        tts = new TextToSpeech(getActivity().getApplicationContext(),new SampleInitListner());
        tts.setLanguage(Locale.ENGLISH);
        bt.setOnClickListener(new SampleClickListenr());

        // ボタンのClickListenerの登録
        return view;


    }


}
