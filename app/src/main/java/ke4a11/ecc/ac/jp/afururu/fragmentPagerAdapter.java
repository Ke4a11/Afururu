package ke4a11.ecc.ac.jp.afururu;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import ke4a11.ecc.ac.jp.afururu.English._EnglishTop;
import ke4a11.ecc.ac.jp.afururu.Money._MoneyTop;

public class fragmentPagerAdapter extends FragmentPagerAdapter {
    //タブ名配列
    private final String[] tabname = {"Money","English","Map","Setting"};

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

    //タブの数をセット
    @Override
    public int getCount() {
        return 2;
    }

    //タブ名セット
    @Override
    public CharSequence getPageTitle(int position){
        return tabname[position];
    }
}
