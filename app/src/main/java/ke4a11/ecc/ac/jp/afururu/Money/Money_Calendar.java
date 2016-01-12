package ke4a11.ecc.ac.jp.afururu.Money;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;

import ke4a11.ecc.ac.jp.afururu.R;

/*TODO
* レイアウトファイルで、日曜日のところを赤色にしているので、入力した値も全て赤色になってしまう
*
* テキストビューが１つなので文字の位置（gravity）の設定が変更すると
* 全ての値もそれに準じて変更されてしまうのでこのまま行こうと思う
*/

public class Money_Calendar extends Fragment {

    int showyear;
    int showmonth;
    int startDay;
    int lastDate1;
    int dayCount;
    int lastDate2;
    boolean isStart;
    int[][] calendarMatrix = new int [6][7];
    int len;
    String name;
    int resId;
    int num1=0;



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


        MCalendar mCalendar = new MCalendar(this);
        mCalendar.makeCalendar();

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

                //if(len == 1 && a != null) これが実装予定



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

    //MCalendar.javaのcalendarMatrixのデータを使えるようにフィールドに値を入れている
    void setcal(int[][] data){
        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 7; j++) {
                calendarMatrix[i][j] = data[i][j];
            }
        }
    }

}
