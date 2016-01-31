package ke4a11.ecc.ac.jp.afururu.Money.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*TODO
private static final int COUNT = 10;
public DummyItem
の２つに実装予定の内容をコメントしている
 */

/**
 * *****簡単な説明*****
 * 一番初めの読み込み時のみ実行されるので、ボタンを押した時の処理などはない。
 * Money_ListのmAdapterの引数であるDummyContent.Itemからこのjavaが実行される。
 *
 * DummyContentクラスの中にDummyItemのクラスを持っている。
 *
 * DummyContentのフィールドのITEMS は DummyItemクラスのフィールドの値を入れている。
 *
 * （予想）
 * createDummyItemメソッドの中で実行されるたびに
 * new DummyItem をしているので添字（item.id）さえ分かれば他の変数 content と detail は自動的に分かる
 */
public class DummyContent {

    public static String[] dummy_payout;
    public static String[] dummy_date;
    public static String[] dummy_shop;
    public static String[] dummy_category;
    public static String[] dummy_memo;

    //コンストラクタ
    public DummyContent(String[] a, String[] b, String[] c, String[] d){
        ITEM_MAP.clear();   //値重複を防ぐため
        ITEMS.clear();

        dummy_date = a;
        dummy_shop = b;
        dummy_category = c;
        dummy_memo = d;

        for (int i = 0; i <= dummy_date.length -1; i++) {
            addItem(createDummyItem(i));
        }

        dummy_date = null;  //値重複を防ぐため
        dummy_shop = null;
        dummy_category = null;
        dummy_memo = null;

    }

    //Genericsという技List<ここのこと> 初めからその型でデータを持つという宣言
    public static List<DummyItem> ITEMS = new ArrayList<DummyItem>();

    //HashMapは連想配列 添字が文字列扱いになる。
    public static Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

    //追加するサンプル数
    /*
    実際には、showDatabaseで行っているqueryメソッド実行の例を
    メソッド化して、結果を配列に格納。それをなんとかして、このカウントに入れる。（配列名[].length）
     */
    //private static final int COUNT = dummy_date.length;

    //まずアイテムの表示コード、addItem.createDummy,makeDetailメソッド全て使う
    static {
        // Add some sample items.
        //for (int i = 1; i <= dummy_date.length; i++) {
        //    addItem(createDummyItem(i));
        // }
    }

    private static void addItem(DummyItem item) {
        ITEMS.add(item);  //ここにはコンテントの Item + position の値が入る
        ITEM_MAP.put(item.id, item); //item.id は position だけなので、数字のみ
    }

    //DummyItem()メソッドの引数 と 一番下のDummyItemクラスのコンストラクタの引数に対応している
    private static DummyItem createDummyItem(int position) {
        //return new DummyItem(String.valueOf(position), "Item " + position, makeDetails(position));
        return new DummyItem(String.valueOf(position), dummy_date[position],dummy_shop[position],dummy_category[position],dummy_memo[position]);

    }

    /**
     * A dummy item representing a piece of content.
     * 他のクラスで使うためにある
     */
    public static class DummyItem {
        public String id;
        public String content;
        public String details;


        public String date;
        public String shop;
        public String category;
        public String memo;

        //この引数を家計簿に必要なものと合わせる。
        //メモ,店名,カテゴリ：String 金額：int
        public DummyItem(String id, String content, String details) {
            this.id = id;
            this.content = content;
            this.details = details;
        }

        public DummyItem(String id, String date, String shop, String category, String memo){
            this.id = id; //主キーみたいな役割
            this.date = date;
            this.shop = shop;
            this.category = category;
            this.memo = memo;
        }

        @Override
        public String toString() {
            return date;
        }
    }
}
