package ke4a11.ecc.ac.jp.afururu.English;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;

import ke4a11.ecc.ac.jp.afururu.R;

public class ExpandableListViewActivity extends Activity {

    // 表示する日本語と英語を置いておく
    static String[][] english;
    static LinkedHashMap<String, List<String>> japanese;

    //画面遷移時の_EnglishTopの方で代入済
    ExpandableListView expandableListView1;
    EngAdapter adapter;
    private ExpandableListView expandableListView;
    private int mSelectedItem = -1;

    private int defaultBackgroundcolor;
    private View previousView;

    //押した英語の詳細を表示する
    private void showDetails(int index) {
        if (mSelectedItem != index) {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.details, DetailFragment.newInstance(index));
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            //処理の確定
            ft.commit();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandable_list_view);

        // リストビューを操作できようにしている
        expandableListView1 = (ExpandableListView) findViewById(R.id.expandableListView1);
        expandableListView1.setChoiceMode(ExpandableListView.CHOICE_MODE_SINGLE);

        // 表示するカテゴリーをリストにする
        List<String> category_list = new ArrayList<>(japanese.keySet());

        adapter = new EngAdapter(this, japanese, category_list);
        expandableListView1.setAdapter(adapter);

        // カテゴリーを選択した時に呼び出す処理
        expandableListView1.setOnChildClickListener(new OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                // 選択したカテゴリーの文章を日本語文を表示する
                DetailFragment.detailEngText = english[groupPosition][childPosition];
                showDetails(childPosition);


                //カテゴリの一行の色を変える作業
                defaultBackgroundcolor = v.getDrawingCacheBackgroundColor();
                if (previousView != null) {
                    previousView.setBackgroundColor(defaultBackgroundcolor);

                }
                v.getParent();
                v.setBackgroundColor(Color.MAGENTA);
                previousView = v;
                return false;
            }
        });
        expandableListView1.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                //元の色に戻す
                if (previousView != null) {
                    previousView.setBackgroundColor(defaultBackgroundcolor);
                }

                return false;
            }
        });


    }

}
