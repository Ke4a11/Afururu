package ke4a11.ecc.ac.jp.afururu.English;


import android.os.Bundle;
//FragmentPagerAdapter で使うために supportlibrary の fragment を使用
//
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ke4a11.ecc.ac.jp.afururu.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class _EnglishTop extends Fragment {


    public _EnglishTop() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_english_top, container, false);
    }


}
