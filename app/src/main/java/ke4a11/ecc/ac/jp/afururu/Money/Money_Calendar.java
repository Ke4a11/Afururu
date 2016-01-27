package ke4a11.ecc.ac.jp.afururu.Money;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.TextView;

import java.util.Calendar;

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
    Button prevMonth;
    Button nextMonth;

    MCalendar mCalendar;

    public Money_Calendar() {

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

                //ユーザが入力した値があったとした仮定
                String a = "test";

                resId = getResources().getIdentifier(name, "id", getActivity().getPackageName());
                TextView textView1 = (TextView)v.findViewById(resId);

                //テキストビューの背景を白色にして、画面自体の背景色と変えることで枠線があるように見せる
                textView1.setBackgroundColor(Color.WHITE);
                textView1.setHeight(100);

                //日付のケタ数
                len = Integer.toString(calendarMatrix[i][j]).length();

                if (len == 1){
                    textView1.setText(String.valueOf(String.format("%1$2d",calendarMatrix[i][j]))  + "\n" + "\n");
                }else{
                    textView1.setText(String.valueOf(calendarMatrix[i][j])  + "\n" + "\n");
                }

                //testで値を入れている
                if(name.equals("txDay14") && a != null){
                    textView1.setText(String.valueOf(String.format("%1$2d",calendarMatrix[i][j]))  + "\n" + "\n" + a);
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

                        //ユーザが入力した値があったとした仮定
                        String a = "test";

                        resId = getResources().getIdentifier(name, "id", getActivity().getPackageName());
                        TextView textView1 = (TextView)getView().findViewById(resId);

                        //テキストビューの背景を白色にして、画面自体の背景色と変えることで枠線があるように見せる
                        textView1.setBackgroundColor(Color.WHITE);
                        textView1.setHeight(100);

                        //日付のケタ数
                        len = Integer.toString(calendarMatrix[i][j]).length();

                        if (len == 1){
                            textView1.setText(String.valueOf(String.format("%1$2d",calendarMatrix[i][j]))  + "\n" + "\n");
                        }else{
                            textView1.setText(String.valueOf(calendarMatrix[i][j])  + "\n" + "\n");
                        }

                        //testで値を入れている
                        if(name.equals("txDay14") && a != null){
                            textView1.setText(String.valueOf(String.format("%1$2d",calendarMatrix[i][j]))  + "\n" + "\n" + a);
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

                        //日付のケタ数
                        len = Integer.toString(calendarMatrix[i][j]).length();

                        if (len == 1){
                            textView1.setText(String.valueOf(String.format("%1$2d",calendarMatrix[i][j]))  + "\n" + "\n");
                        }else{
                            textView1.setText(String.valueOf(calendarMatrix[i][j])  + "\n" + "\n");
                        }

                        //testで値を入れている
                        if(name.equals("txDay14") && a != null){
                            textView1.setText(String.valueOf(String.format("%1$2d",calendarMatrix[i][j]))  + "\n" + "\n" + a);
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

}
