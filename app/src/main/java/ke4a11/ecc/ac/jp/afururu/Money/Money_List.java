package ke4a11.ecc.ac.jp.afururu.Money;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.Toast;

import ke4a11.ecc.ac.jp.afururu.R;
import ke4a11.ecc.ac.jp.afururu.Money.dummy.DummyContent;

/*TODO
*
* onCreateメソッド内のmAdapterにセットしているところを
* showdatabase内の
* Cursor c = db.query(...)
* のしたのwhileの中の処理で適当な配列を作って、そこに入れて
* mAdapterのメソッドの引数にする。
* ＊＊＊＊＊セレクトである値だけを持ってこれるのか、降順昇順などにできるのか
* ＊＊＊＊＊実際のテーブルはどこにあるのか
*
*
* リストを押してDetailの表示は
* 押した場所でOnFragmentInteractionListenerの引数の
* String id を使ってsqlの指定はできないか試す
*
 */

/*
* *****簡単な説明*****
* File>new>Fragment(List)選択で初期メソッドをほぼそのまま使用
*DummyContentのDummyItemクラスでreturn dateをしているのでITEMフィールドにdateが保持されている。それをArrayListで表示してるため日付が出てくる
*
* */

public class Money_List extends Fragment implements AbsListView.OnItemClickListener {

    private OnFragmentInteractionListener mListener;
    private AbsListView mListView;
    private ListAdapter mAdapter;


    public static Money_List newInstance() {
        Money_List fragment = new Money_List();
        return fragment;
    }

    public Money_List() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //new ArrayAdapter<DummyContent.DummyItem>(getActivity(),android.R.layout.simple_list_item_1, ここに配列);

        mAdapter = new ArrayAdapter<DummyContent.DummyItem>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, DummyContent.ITEMS);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item, container, false);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /*
    * リストのクリックリスナー
    */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            //Activityにあるメソッドの呼び出し。引数に押した場所のid(ITEM_MAPの添字に使う)を渡している。
            mListener.onFragmentInteraction(DummyContent.ITEMS.get(position).id);

            //Toast.makeText(getActivity(),position+1 + "を押しました。",Toast.LENGTH_SHORT).show();
        }
    }


    /*
    * Activityで実装するinterface
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String id);
    }

}
