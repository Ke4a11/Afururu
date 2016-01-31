package ke4a11.ecc.ac.jp.afururu;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import ke4a11.ecc.ac.jp.afururu.English._EnglishTop;
import ke4a11.ecc.ac.jp.afururu.Map._MapTop;
import ke4a11.ecc.ac.jp.afururu.Money._MoneyTop;
import ke4a11.ecc.ac.jp.afururu.Setting._SettingTop;

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
            case 2:
                return new _MapTop();
            case 3:
                return new _SettingTop();
        }
        return null;
    }

    //タブの数をセット
    @Override
    public int getCount() {
        return 4;
    }

    //タブ名セット
    @Override
    public CharSequence getPageTitle(int position){
        return tabname[position];
    }
}
