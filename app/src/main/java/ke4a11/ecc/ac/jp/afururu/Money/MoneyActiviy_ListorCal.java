package ke4a11.ecc.ac.jp.afururu.Money;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

//Import package

import ke4a11.ecc.ac.jp.afururu.R;


/*
* TODO
*
* titleEngTextの部分にデータベース？として保持している今までの支出データを
* 配列の変数に入れてそれをリストビューに渡す。
* 中身としてはアプリZaimを参照する
* ２次元配列にする？aiueo[i][j]↓
* [i] ここにキーとなる（日付にする？）ものを入れて
* [j] ここにキーにあったデータを入れる。（店名、お金、ジャンルなど）
* これをforで回すならいけそう？要確認
*
* */

/*
*  *****簡単な説明*****
*  _MoneyTopのカレンダーボタンを押した時に起動するアクティビティ
*  リスト表示かカレンダー表示を選択する
*  独自のコードが大量に必要なのでわけた
*
*
*
*
* */

public class MoneyActiviy_ListorCal extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private ListView listBoutItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_activiy_listorcal);


        //listBoutItem = (ListView)findViewById(R.id.listView1);
        //listBoutItem.setOnItemClickListener(this);

        //listBoutItem.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        //ListItemViewAdapter adapter = new ListItemViewAdapter(this,android.R.layout.simple_list_item_1,titleEngText);

        //listBoutItem.setAdapter(adapter);
    }


    //リストのアイテムを押した時の処理
    public void onItemClick(AdapterView<?> parent,View view,int position,long arg3){

    }

    //リストアイテム用のアダプター
    class ListItemViewAdapter extends ArrayAdapter<String> {

        public ListItemViewAdapter(Context cn, int textViewResourceId, String[] objects) {
            super(cn, textViewResourceId, objects);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ListView l = (ListView)parent;
            TextView t = new TextView(getBaseContext());

            t.setText(l.getItemAtPosition(position).toString());

            convertView = t;
            convertView.setBackgroundResource(R.drawable.listitem_color1);

            return convertView;
        }

        @Override
        public boolean isEnabled(int position){

            return true;
        }
    }

}
