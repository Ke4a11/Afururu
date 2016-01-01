package ke4a11.ecc.ac.jp.afururu.Money;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ke4a11.ecc.ac.jp.afururu.R;

public class Money_Exchange extends Fragment {

    public static Money_Exchange newInstance() {
        Money_Exchange money_exchange = new Money_Exchange();
        return money_exchange;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_money_exchange, container, false);
    }

}
