package ke4a11.ecc.ac.jp.afururu.Money;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ke4a11.ecc.ac.jp.afururu.R;
import ke4a11.ecc.ac.jp.afururu.Money.dummy.DummyContent;

public class Money_ListorCal_Detail extends Fragment {
    /**
     *
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * クラスの指定
     * mItem.id .content .details が参照できる。
     * これを使えば、データベースを読み込んで...などしなくてもいけそう
     */
    private DummyContent.DummyItem mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public Money_ListorCal_Detail() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //受け取った時に、ArgumentsにARG_ITEM_IDが入っているかチェックしてアプリが落ちるのを防ぐ。（なければ実行されない）
        if (getArguments().containsKey(ARG_ITEM_ID)) {

            //ITEM_MAPの添字にあったものを持ってくるので、自動的に一緒に入っている .content と .detail が参照できるようになる
            //DummyContentファイルに実際のデータがどうなのか、簡単な説明 に書いてある
            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.content);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_money_listorcal_detail, container, false);

        //ちなみにxmlファイルはテキストビューのみ
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.item_detail)).setText(mItem.details);
        }

        return rootView;
    }
}
