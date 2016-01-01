package ke4a11.ecc.ac.jp.afururu.Money;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ke4a11.ecc.ac.jp.afururu.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Money_Graph extends Fragment {


    public Money_Graph() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_money_graph, container, false);
    }

    public static Money_Graph newInstance() {
        Money_Graph money_graph = new Money_Graph();
        return money_graph;
    }


}
