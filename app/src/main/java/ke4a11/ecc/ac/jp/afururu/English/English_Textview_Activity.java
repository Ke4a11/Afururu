package ke4a11.ecc.ac.jp.afururu.English;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ke4a11.ecc.ac.jp.afururu.R;

/*TODO

setcategory内にホテル以外のジャンルを入れていく

押した時のアニメーションのせいで、背景色が反映されなくなった。

 */


public class English_Textview_Activity extends Activity implements AdapterView.OnItemClickListener{

    //画面遷移時の_EnglishTopの方で代入済
    public static String[] titleEngText;

    private ListView listView1;
    private  int mSelectedItem = -1;

    //英語カテゴリー名一覧用変数
    public List<String> category = new ArrayList<>();

    //コンストラクタ
    public English_Textview_Activity() {
        setCategory();
    }

    //押した英語の詳細を表示する
    private void showDetails(int index){
        if (mSelectedItem != index){
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.details,English_Detail.newInstance(index));

            if (mSelectedItem != -1){
                ft.addToBackStack(null);
            }

            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            //処理の確定
            ft.commit();

            mSelectedItem = index;
        }
    }

    //リストのアイテムを押した時の処理
    public void onItemClick(AdapterView<?> parent,View view,int position,long arg3){
        showDetails(position);
    }

    @Override
    public void onResume(){
        super.onResume();

        listView1 = (ListView)findViewById(R.id.listView1);
        listView1.setOnItemClickListener(this);

        listView1.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        EngAdapter adapter = new EngAdapter(this,android.R.layout.simple_list_item_1,titleEngText);

        listView1.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_english_textview);
    }

    @Override
    protected void onSaveInstanceState(Bundle outSate){
        super.onSaveInstanceState(outSate);
        outSate.putInt("index", mSelectedItem);
    }

    //英語カテゴリーセット用メソッド
    private void setCategory(){
        //ホテルカテゴリー
        category.add("チェックイン");
        category.add("滞在中");
        category.add("クレーム");
        category.add("チェックアウト");
        //空港

        //学校
    }

    //リストアイテム用のアダプター
    class EngAdapter extends ArrayAdapter<String> {

        public EngAdapter(Context cn, int textViewResourceId, String[] objects) {
            super(cn, textViewResourceId, objects);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ListView l = (ListView)parent;
            TextView t = new TextView(getBaseContext());

            t.setText(l.getItemAtPosition(position).toString());
            t.setTextSize(28);
            t.setTextColor(Color.BLUE);

            // ヘッダの設定
            if (! category.contains(titleEngText[position])) {
                t.setBackgroundColor(Color.WHITE);
                t.setTextColor(Color.BLACK);
                t.setTextSize(20);
            }

            convertView = t;
            convertView.setBackgroundResource(R.drawable.listitem_color1);

            return convertView;
        }

        @Override
        public boolean isEnabled(int position){
            //ヘッダー部分は選択不可能にする
            if (category.contains(titleEngText[position])) {
                return false;
            }
            return true;
        }
    }
}
