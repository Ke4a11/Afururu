package ke4a11.ecc.ac.jp.afururu.Money;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ke4a11.ecc.ac.jp.afururu.Money.dummy.DummyContent;
import ke4a11.ecc.ac.jp.afururu.R;

/*TODO
* レイアウトファイルで、日曜日のところを赤色にしているので、入力した値も全て赤色になってしまう
*
* テキストビューが１つなので文字の位置（gravity）の設定が変更すると
* 全ての値もそれに準じて変更されてしまうのでこのまま行こうと思う
*
* リスナの中のコードでfindViewByIdの前の v の変わりにgetView()を使うと動いた。
* 多分これ良くない。コードも汚いが動いてるのでよし。
*
* 来月、先月を押した時に
* 借りで入れた値が次の月にも適用されるのでなんとかするのと、
* その月の日にちが見にくい
*
*
* ＊＊＊＊＊文字列比較＊＊＊＊＊
* http://www.itmedia.co.jp/enterprise/articles/0407/12/news003.html
*
* データベースで取り出して配列に比較をかけ、あったものだけ自分の配列に入れて表示時にそれを使う
* カレンダーと同じ形の配列を用意してお金の入力があった時だけ同じ値に要素を入れる。
*コード例）
* String[][] matchesDB = new String[6][7];
*
*
*/

/*
* *****簡単な説明*****
* コードの書き方が悪いのか、表示の部分をメソッド化できず
* 先月・来月ボタンを押すと同じコードを描いてある。
*
* ArrayListの使い方はMoneyActivity_ListorCal周辺をみるといい
*
 */

public class Money_Calendar extends Fragment {

    //フィールど
    int[][] calendarMatrix = new int [6][7];
    int len;
    String name;
    int resId;
    int num1=0;

    int showyear=0;
    int showmonth=0;

    TextView txYM;
    Button prevMonth,nextMonth;

    //DateItemクラスが持っているカレンダーの情報がある
    List<DateItem> DATEITEMS = new ArrayList<DateItem>();

    //今月だけ表示のためのフラグ
    Boolean calFlg = true;

    MCalendar mCalendar;

    public Money_Calendar() {
        set_cal_info();
    }

    public static Money_Calendar newInstance() {
        Money_Calendar money_calendar = new Money_Calendar();

        return money_calendar;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_money_calendar, container, false);

        //ボタン作成とリスナ
        prevMonth = (Button)v.findViewById(R.id.PrevMonth);
        nextMonth = (Button)v.findViewById(R.id.NextMonth);

        prevMonth.setOnClickListener(new Changemonth());
        nextMonth.setOnClickListener(new Changemonth());

        //初期設定
        Calendar cal = Calendar.getInstance();
        showyear = cal.get(Calendar.YEAR);//現在の年を取得
        showmonth = cal.get(Calendar.MONTH)+1;//現在の月を取得

        //年月の表示
        txYM = (TextView)v.findViewById(R.id.txYM);
        txYM.setText(showyear + "年" + showmonth + "月");

        mCalendar = new MCalendar(this);
        mCalendar.makeCalendar(showyear,showmonth);

        //TextViewに日付をセット
        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 7; j++) {
                //name = "txDay" + String.valueOf(i) + String.valueOf(j);
                if(num1 < 10){
                    name = "txDay0" +String.valueOf(num1);
                }else{
                    name = "txDay" +String.valueOf(num1);
                }
                num1++;

                resId = getResources().getIdentifier(name, "id", getActivity().getPackageName());
                TextView textView1 = (TextView)v.findViewById(resId);

                //テキストビューの背景を白色にして、画面自体の背景色と変えることで枠線があるように見せる
                textView1.setBackgroundColor(Color.WHITE);
                textView1.setHeight(100);
                //textView1.setTextColor(Color.BLACK);

                //日付のケタ数
                len = Integer.toString(calendarMatrix[i][j]).length();

                if (len == 1){
                    textView1.setText(String.valueOf(String.format("%1$2d",calendarMatrix[i][j]))  + "\n" + "\n");
                    calFlg = false;//始めの一桁
                }else{
                    textView1.setText(String.valueOf(calendarMatrix[i][j])  + "\n" + "\n");
                    calFlg = true;
                }

                //確実に31日以降ならば白色にする
                if (i == 5 && j > 2){
                    textView1.setTextColor(Color.WHITE);
                }else if(i == 0 && calFlg){
                    //始めの１日が出るまでは真っ白
                    textView1.setTextColor(Color.WHITE);
                }else if (i > 3 && !calFlg){
                    //来月の１日からは真っ白
                    textView1.setTextColor(Color.WHITE);
                }

                /*
                入力された値があった場合にtextviewに表示する
                 */

                String testdata = null;
                String date = String.valueOf(calendarMatrix[i][j]); //一度変数に入れないとif文で使えないため

                for (int n =0; n < DATEITEMS.size(); n++){
                    //一度変数に入れないとifを全く受け付けなかったため
                    int year = DATEITEMS.get(n).year;
                    int month = DATEITEMS.get(n).month;
                    String day = DATEITEMS.get(n).day;
                    //最初のifに日にちまで入れると一行が長くなる上、読みにくくなる。何より変わらないと思うが、二つ目のifである日にちの比較に時間がかかりそう（適当）
                    if (year ==showyear && month == showmonth){
                        if (day.equals(date)){
                            //同じ日にちの値をどうするか検討しなくてはいけない
                            testdata = DummyContent.ITEMS.get(n).id;
                        }
                    }
                }


                if(testdata != null){
                    textView1.setText(String.valueOf(String.format("%1$2d",calendarMatrix[i][j]))  + "\n" + "\n" + testdata);
                    textView1.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(getContext(),MoneyInputActivity.class);
                            startActivity(i);
                        }
                    });
                    //textView1.setTextSize(10);
                }
            }
        }
        return v;
    }

    //ボタンクリック時の動作
    class Changemonth implements OnClickListener{
        public void onClick(View v) {
            if (v == getView().findViewById(R.id.PrevMonth)) {
                showmonth -= 1;

                //1月の状態で押した場合に去年の12月に戻る
                if(showmonth == 0){
                    showmonth = 12;
                    showyear -= 1;
                }
                mCalendar.makeCalendar(showyear,showmonth);

                int num1 = 0;

                txYM.setText(showyear + "年" + showmonth + "月");

                //TextViewに日付をセット
                for(int i = 0; i < 6; i++){
                    for(int j = 0; j < 7; j++) {
                        //name = "txDay" + String.valueOf(i) + String.valueOf(j);
                        if(num1 < 10){
                            name = "txDay0" +String.valueOf(num1);
                        }else{
                            name = "txDay" +String.valueOf(num1);
                        }
                        num1++;

                        resId = getResources().getIdentifier(name, "id", getActivity().getPackageName());
                        TextView textView1 = (TextView)getView().findViewById(resId);

                        //テキストビューの背景を白色にして、画面自体の背景色と変えることで枠線があるように見せる
                        textView1.setBackgroundColor(Color.WHITE);
                        textView1.setHeight(100);
                        textView1.setTextColor(Color.BLACK);

                        //日付のケタ数
                        len = Integer.toString(calendarMatrix[i][j]).length();

                        if (len == 1){
                            textView1.setText(String.valueOf(String.format("%1$2d",calendarMatrix[i][j]))  + "\n" + "\n");
                            calFlg = false;
                        }else{
                            textView1.setText(String.valueOf(calendarMatrix[i][j])  + "\n" + "\n");
                            calFlg = true;
                        }



                        //確実に31日以降ならば白色にする
                        if (i == 5 && j > 2){
                            textView1.setTextColor(Color.WHITE);
                        }else if(i == 0 && calFlg){
                            //始めの１日が出るまでは真っ白
                            textView1.setTextColor(Color.WHITE);
                        }else if (i > 3 && !calFlg){
                            //来月の１日からは真っ白
                            textView1.setTextColor(Color.WHITE);
                        }

                        /*
                        入力された値があった場合にtextviewに表示する
                        */

                        String testdata = null;

                        for (int n =0; n < DATEITEMS.size(); n++){
                            //一度変数に入れないとifを全く受け付けなかったため
                            int year = DATEITEMS.get(n).year;
                            int month = DATEITEMS.get(n).month;
                            String day = DATEITEMS.get(n).day;
                            String date = String.valueOf(calendarMatrix[i][j]);
                            //最初のifに日にちまで入れると一行が長くなる上、読みにくくなる。何より変わらないと思うが、二つ目のifである日にちの比較に時間がかかりそう（適当）
                            if (year ==showyear && month == showmonth){
                                if (day.equals(date)){
                                    //同じ日にちの値をどうするか検討しなくてはいけない
                                    testdata = DummyContent.ITEMS.get(n).id;
                                }
                            }
                        }


                        if(testdata != null){
                            textView1.setText(String.valueOf(String.format("%1$2d",calendarMatrix[i][j]))  + "\n" + "\n" + testdata);
                            //textView1.setTextSize(10);
                        }
                    }
                }
            } else if (v == getView().findViewById(R.id.NextMonth)) {
                showmonth += 1;

                //12月の状態で押した場合に来年の1月に進む
                if(showmonth == 13){
                    showmonth = 1;
                    showyear += 1;
                }
                mCalendar.makeCalendar(showyear,showmonth);

                int num1 = 0;

                txYM.setText(showyear + "年" + showmonth + "月");

                //TextViewに日付をセット
                for(int i = 0; i < 6; i++){
                    for(int j = 0; j < 7; j++) {
                        //name = "txDay" + String.valueOf(i) + String.valueOf(j);
                        if(num1 < 10){
                            name = "txDay0" +String.valueOf(num1);
                        }else{
                            name = "txDay" +String.valueOf(num1);
                        }
                        num1++;

                        //ユーザが入力した値があったとした仮定
                        String a = "test";

                        resId = getResources().getIdentifier(name, "id", getActivity().getPackageName());
                        TextView textView1 = (TextView)getView().findViewById(resId);

                        //テキストビューの背景を白色にして、画面自体の背景色と変えることで枠線があるように見せる
                        textView1.setBackgroundColor(Color.WHITE);
                        textView1.setHeight(100);
                        textView1.setTextColor(Color.BLACK);


                        //日付のケタ数
                        len = Integer.toString(calendarMatrix[i][j]).length();

                        if (len == 1){
                            textView1.setText(String.valueOf(String.format("%1$2d",calendarMatrix[i][j]))  + "\n" + "\n");
                            calFlg = false;
                        }else{
                            textView1.setText(String.valueOf(calendarMatrix[i][j])  + "\n" + "\n");
                            calFlg = true;
                        }

                        //確実に31日以降ならば白色にする
                        if (i == 5 && j > 2){
                            textView1.setTextColor(Color.WHITE);
                        }else if(i == 0 && calFlg){
                            //始めの１日が出るまでは真っ白
                            textView1.setTextColor(Color.WHITE);
                        }else if (i > 3 && !calFlg){
                            //来月の１日からは真っ白
                            textView1.setTextColor(Color.WHITE);
                        }

                        /*
                        入力された値があった場合にtextviewに表示する
                         */

                        String testdata = null;

                        for (int n =0; n < DATEITEMS.size(); n++){
                            //一度変数に入れないとifを全く受け付けなかったため
                            int year = DATEITEMS.get(n).year;
                            int month = DATEITEMS.get(n).month;
                            String day = DATEITEMS.get(n).day;
                            String date = String.valueOf(calendarMatrix[i][j]);
                            //最初のifに日にちまで入れると一行が長くなる上、読みにくくなる。何より変わらないと思うが、二つ目のifである日にちの比較に時間がかかりそう（適当）
                            if (year ==showyear && month == showmonth){
                                if (day.equals(date)){
                                    //同じ日にちの値をどうするか検討しなくてはいけない
                                    testdata = DummyContent.ITEMS.get(n).id;
                                }
                            }
                        }


                        if(testdata != null){
                            textView1.setText(String.valueOf(String.format("%1$2d",calendarMatrix[i][j]))  + "\n" + "\n" + testdata);
                            //textView1.setTextSize(10);
                        }
                    }
                }
            }
        }
    }

    //MCalendar.javaでカレンダーの情報を持ったcalendarMatrixを使えるようにフィールドに値を入れている
    void setcal(int[][] data){
        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 7; j++) {
                calendarMatrix[i][j] = data[i][j];
            }
        }
    }

    //DummyContentの持っているカレンダーの情報を取り出す
    void set_cal_info(){
        String[] tmp_date = new String[DummyContent.ITEMS.size()];

        String[] tmp_year = new String[DummyContent.ITEMS.size()];
        String[] tmp_month = new String[DummyContent.ITEMS.size()];
        String[] tmp_day = new String[DummyContent.ITEMS.size()];

        for(int i=0; i < DummyContent.ITEMS.size(); i++){
            tmp_date[i] = DummyContent.ITEMS.get(i).date; //日付だけ取得
            //ここにsplite。 / で切ったものを年月日で分けてtmp_~~ の各種変数に入れていく
            String[] tmp_splite = tmp_date[i].split("/");
            tmp_year[i] = tmp_splite[0];
            tmp_month[i] = tmp_splite[1];
            tmp_day[i] = tmp_splite[2];
        }

        for (int i=0; i < tmp_date.length; i++){
            DATEITEMS.add(new DateItem(tmp_date[i],tmp_year[i],tmp_month[i],tmp_day[i]));
        }

    }

    //ArrayList用クラス cal_info()にある
    public static class DateItem {
        public String date; //年月日全てのデータ
        public int year;
        public int month;
        public String day; //textViewとの比較に使うため

        public DateItem(String date,String year, String month, String day) {
            this.date = date;
            this.year = Integer.valueOf(year);
            this.month = Integer.valueOf(month);
            this.day = day;
        }

        @Override
        public String toString() {
            return date;
        }
    }

}
