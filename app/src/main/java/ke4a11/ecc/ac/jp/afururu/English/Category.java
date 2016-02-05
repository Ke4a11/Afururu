package ke4a11.ecc.ac.jp.afururu.English;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by it on 2016/02/02.
 */
public class Category {


    //ホテルのカテゴリ
    public static LinkedHashMap<String, List<String>> getHotelInfo() {

        LinkedHashMap<String, List<String>> HotelsDetail = new LinkedHashMap<>();

        // チェックインの和訳文
        List<String> Hotel_Checkin = new ArrayList<String>();
        Hotel_Checkin.add("チェックインをお願いします。名前はアフルルです。");
        Hotel_Checkin.add("これが予約確認書です。（予約してある場合）");
        Hotel_Checkin.add("予約してません。空いている部屋はありますか？（予約していない場合）");
        Hotel_Checkin.add("シングル１部屋で、２泊です。");
        Hotel_Checkin.add("バスルーム/シャワーつきの部屋ですか？");
        Hotel_Checkin.add("ご予約頂いている_______様ですね。");
        Hotel_Checkin.add("空いている部屋はございます。どうぞ、お泊まり下さい。");
        Hotel_Checkin.add("あいにく、空いている部屋はございません。");
        Hotel_Checkin.add("こちらのフォームにご記入下さい。");
        Hotel_Checkin.add("お支払はクレジットカードですか？");
        Hotel_Checkin.add("お部屋の番号は＿＿＿になります。こちらが鍵です。");


        // 滞在中の和訳文
        List<String> Hotel_Visit = new ArrayList<String>();
        Hotel_Visit.add("（今から外出します。）鍵を預かって下さい。");
        Hotel_Visit.add("（外出から帰り、）鍵を下さい。部屋の番号は________です。");
        Hotel_Visit.add("タクシーを呼んでもらえますか？");
        Hotel_Visit.add("この近くにおすすめのレストランありませんか？");
        Hotel_Visit.add("明日朝、モーニングコールを______時にお願いします。部屋番号___の＿＿＿＿です。");


        // クレームの和訳文
        List<String> Hotel_Claim = new ArrayList<String>();
        Hotel_Claim.add("部屋に鍵を置いたままドアを閉めてしまいました。");
        Hotel_Claim.add("部屋に困った事があるのですが。");
        Hotel_Claim.add("エアコンが効いていません。");
        Hotel_Claim.add("冷蔵庫の調子が悪いようです。");
        Hotel_Claim.add("トイレがよく流れません。");
        Hotel_Claim.add("バスタブの水が流れません。");
        Hotel_Claim.add("直していただけますか？");
        Hotel_Claim.add("隣の部屋の人がとても騒がしいのですが、別の部屋に替えていただけますか？");
        Hotel_Claim.add("部屋がまだ掃除されていません。");
        Hotel_Claim.add("ベッドメイクができていません。すぐにベッドメイクしてください。");
        Hotel_Claim.add("部屋にタオルがありません。持ってきてください。");
        Hotel_Claim.add("ドアの鍵がかかりません。");


        // チェックアウトの和訳文
        List<String> Hotel_Checkout = new ArrayList<String>();
        Hotel_Checkout.add("チェックアウトお願いします。");
        Hotel_Checkout.add("有難うございます。少々お待ち下さい。");
        Hotel_Checkout.add("お支払いはクレジットカードでしょうか?");
        Hotel_Checkout.add("料金は200ドルとなります。\n明細書はこちらです");
        Hotel_Checkout.add("料金に計算違いがある様なんですが。");
        Hotel_Checkout.add("これは何の料金ですか？（明細を見せながら）");
        Hotel_Checkout.add("電話は使いませんでしたよ。");
        Hotel_Checkout.add("映画は見ていないです。");
        Hotel_Checkout.add("ミニバーのコーラをお飲みになりましたので、その料金です。");
        Hotel_Checkout.add("それでは、こちらにサインをお願いします。");
        Hotel_Checkout.add("ご利用誠に有難うございました。");
        Hotel_Checkout.add("私/私達にとって良い滞在となりました。お世話になりました。");


        // 日本語訳をリストにする
//        List<String> 新しいカテゴリ = new ArrayList<String>();
//        Hotel_Claim.add("");
//        Hotel_Claim.add("");
//        Hotel_Claim.add("");
//        Hotel_Claim.add("");
//        Hotel_Claim.add("");
//        Hotel_Claim.add("");

        // 作成した和訳文をタイトル付きで追加する
        HotelsDetail.put("チェックイン", Hotel_Checkin);
        HotelsDetail.put("滞在中", Hotel_Visit);
        HotelsDetail.put("クレーム", Hotel_Claim);
        HotelsDetail.put("チェックアウト", Hotel_Checkout);
        // HotelsDetail.put("新しいカテゴリ名", 新しいカテゴリ);

        return HotelsDetail;
    }

    //空港のカテゴリ
    public static LinkedHashMap<String, List<String>> getairInfo() {

        LinkedHashMap<String, List<String>> AirsDetail = new LinkedHashMap<>();

        List<String> Air_inspection = new ArrayList<String>();
        //カテゴリリスト
        Air_inspection.add("パスポートを見せてください。");
        Air_inspection.add("どうぞ");
        Air_inspection.add("訪れた目的はなんですか？");
        Air_inspection.add("観光です。");
        Air_inspection.add("休暇です。");
        Air_inspection.add("仕事です");
        Air_inspection.add("留学です");
        Air_inspection.add("この国にどのぐらい滞在しますか？");
        Air_inspection.add("6日間です。／3週間ぐらいです。");
        Air_inspection.add("乗り継ぎをするだけです。");
        Air_inspection.add("トランジットだけです。");
        Air_inspection.add("どこに滞在する予定ですか？");
        Air_inspection.add("オスカーホテルです。");
        Air_inspection.add("これから探す予定です。");
        Air_inspection.add("帰りのチケットはお持ちですか？");
        Air_inspection.add("はい、これです。");
        Air_inspection.add("いいえ、帰りにニューヨークで買うつもりです。");
        Air_inspection.add("職業はなんですか？");
        Air_inspection.add("会社員です。");
        Air_inspection.add("英語がよくわかりません。");
        Air_inspection.add("日本語が話せる人はいますか？");

        //機内
        List<String> Air_airport = new ArrayList<String>();

        Air_airport.add("お飲み物はいかがですか？");
        Air_airport.add("どんな飲み物がありますか？");
        Air_airport.add("水をお願いします");
        Air_airport.add("コーヒー（お茶）をお願いします。");
        Air_airport.add("赤ワインをお願いします。");

        List<String> Air_electrictrain = new ArrayList<String>();

        Air_electrictrain.add("～までいきたいのですが。");
        Air_electrictrain.add("までの料金はいくらですか");
        Air_electrictrain.add("○駅までの往復キップをください.");
        Air_electrictrain.add("発車時刻は何時ですか？");
        Air_electrictrain.add("時刻表はありますか？");
        Air_electrictrain.add("乗換えが必要ですか？");
        Air_electrictrain.add("始発（終電）は何時ですか？");
        Air_electrictrain.add("～行きは何番線ですか？");
        Air_electrictrain.add("3番線ホームはどこですか？");
        Air_electrictrain.add("この電車は○○に停まりますか");
        Air_electrictrain.add("ここに座ってもいいですか？");
        Air_electrictrain.add("ここは私の席だと思うのですが。");
        Air_electrictrain.add("次の駅はなんですか？");


        //カテゴリタイトル
        AirsDetail.put("検問", Air_inspection);
        AirsDetail.put("機内", Air_airport);
        AirsDetail.put("地下鉄", Air_electrictrain);


        return AirsDetail;

    }

    public static LinkedHashMap<String, List<String>> getemergencyInfo() {

        LinkedHashMap<String, List<String>> emergencyDetail = new LinkedHashMap<>();

        List<String> emer_sic = new ArrayList<String>();
        emer_sic.add("具合が悪いんです");
        emer_sic.add("おなかの調子が悪いんです");
        emer_sic.add("熱が高いんです");
        emer_sic.add("食中毒なんです");
        emer_sic.add("彼は車にひかれたんです");
        emer_sic.add("車が自転車にぶつかりました");
        emer_sic.add("彼はひどいけがをしています");
        emer_sic.add("彼女は意識がありません");
        emer_sic.add("どうも左足を骨折したようなんです");
        emer_sic.add("肘の骨が折れました");
        emer_sic.add("彼女は右手にひどくやけどしました");

        List<String> emer_emergency = new ArrayList<String>();
        emer_emergency.add("緊急です");
        emer_emergency.add("警察を呼んでください");
        emer_emergency.add("医者を呼んでください");
        emer_emergency.add("救急車を呼んでください");
        emer_emergency.add("日本語の話せる人はいますか");
        emer_emergency.add("遺失物係はどこですか");
        emer_emergency.add("日本大使館はどこですか");
        emer_emergency.add("助けて!");
        emer_emergency.add("どうしたんですか？");
        emer_emergency.add("泥棒！");
        emer_emergency.add("強盗です！");
        emer_emergency.add("昨日、バッグが盗まれました");
        emer_emergency.add("パスポートをなくしました");
        emer_emergency.add("カードを盗まれました。カードを無効にしてください");


        emergencyDetail.put("緊急", emer_emergency);
        emergencyDetail.put("病気", emer_sic);

        return emergencyDetail;
    }

    public static LinkedHashMap<String, List<String>> getschoolInfo() {

        LinkedHashMap<String, List<String>> shoolDetail = new LinkedHashMap<>();

        List<String> school_life = new ArrayList<String>();


        school_life.add("今日は予定があります");
        school_life.add("今日は遊園地に行きます（行く予定です）");
        school_life.add("午後６時くらいに家に戻ります");
        school_life.add("午後７時までに家に戻ります");
        school_life.add("まっすぐ進んでください");
        school_life.add("～で左・右に曲がってください");
        school_life.add("二つ目の信号を右に曲がってください");
        school_life.add("～が見えます");
        school_life.add("～の隣です");
        school_life.add("銀行の隣です");
        school_life.add("~の向かいです");
        school_life.add("銀行の向かいです");
        school_life.add("AとBとの間にあります");
        school_life.add("学校と銀行の間にあります");
        school_life.add("～の角にあります");
        school_life.add("角に病院があります");
        school_life.add("道の終わりにあります。");
        school_life.add("～に突き当たります");


        shoolDetail.put("日常", school_life);

        return shoolDetail;

    }

    //レストラン
    public static LinkedHashMap<String, List<String>> getrestInfo() {

        LinkedHashMap<String, List<String>> restDetail = new LinkedHashMap<>();

        List<String> rest_open = new ArrayList<String>();

        rest_open.add("開いてますか？（今、営業中ですか？）");
        rest_open.add("禁煙席をお願いします。");
        rest_open.add("喫煙席をお願いします。");
        rest_open.add("あのテーブル（席）に座っていいですか？（指差しながら）");
        rest_open.add("待ち時間はどれくらいですかね？");
        rest_open.add("それじゃ、、待ちます。");
        rest_open.add("残念ですね。それじゃあまたにします．");

        List<String> rest_order = new ArrayList<String>();

        rest_order.add("メニューをください。（私に/ 私たちに。）");
        rest_order.add("オーダーお願いします。");
        rest_order.add("ラストオーダーは何時ですか");
        rest_order.add("決まったらお呼びしますので");
        rest_order.add("おすすめはどの料理ですか？");
        rest_order.add("これ下さい。\n(メニュー内の料理名を指差しなが）");
        rest_order.add("甘い（味付け）ですか？");
        rest_order.add("辛いですか？");
        rest_order.add("酸っぱいですか？");
        rest_order.add("塩味ですか？");


        List<String> rest_check = new ArrayList<String>();

        rest_order.add("勘定お願いします");
        rest_order.add("クレジットカードで支払えますか？");
        rest_order.add("レシート（領収書）を下さい。");


        restDetail.put("入店時", rest_open);
        restDetail.put("注文", rest_order);
        restDetail.put("支払い", rest_check);

        return restDetail;
    }


}
