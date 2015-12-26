package ke4a11.ecc.ac.jp.afururu;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import ke4a11.ecc.ac.jp.afururu.English._EnglishTop;
import ke4a11.ecc.ac.jp.afururu.Money._MoneyTop;

public class fragmentPagerAdapter extends FragmentPagerAdapter {

    //コンストラクタ
    public fragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new _MoneyTop();
            case 1:
                return  new _EnglishTop();
        }
        return null;
    }

    @Override
    public int getCount() {
        //タブの数をセット
        return 2;
    }
    @Override
    public CharSequence getPageTitle(int position){
        return "ページ"+(position+1);
    }
}
