package ke4a11.ecc.ac.jp.afururu.Money;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ke4a11.ecc.ac.jp.afururu.R;

public class Money_Graph extends Fragment {

    TextView t1,t2,t3;
    String[] avg = new String[3];

    public Money_Graph() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_money_graph, container, false);

        t1 = (TextView)view.findViewById(R.id.category1);
        t2 = (TextView)view.findViewById(R.id.category2);
        t3 = (TextView)view.findViewById(R.id.category3);

        getAVG();

        t1.setText("しょくひ" + avg[0]);
        t2.setText("がくしょく" + avg[1]);
        t3.setText("交通費" + avg[2]);

        return view;
    }

    public static Money_Graph newInstance() {
        Money_Graph money_graph = new Money_Graph();
        return money_graph;
    }


    void getAVG(){

        //支出の計算
        MoneyOpenHelper helper = new MoneyOpenHelper(getActivity().getApplicationContext());
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "select sum(price) from ecc group by category;";
        Cursor c = db.rawQuery(sql,null);

        boolean isEof = c.moveToFirst();

        int i = 0;
        while (isEof){
            avg[i] = c.getString(0);
            i++;
            isEof = c.moveToNext();
        }

    }


}
