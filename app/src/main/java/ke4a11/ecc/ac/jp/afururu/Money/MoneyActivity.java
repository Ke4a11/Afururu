package ke4a11.ecc.ac.jp.afururu.Money;

import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

//import package
import java.util.Calendar;

import ke4a11.ecc.ac.jp.afururu.R;

//共通変数を扱うクラス
public class MoneyActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money);

        if(_MoneyTop.moneyFlg.equals("cal")){
            ToMoney_Calender();
        }else if(_MoneyTop.moneyFlg.equals("gra")){
            ToMoney_Graph();
        }else if(_MoneyTop.moneyFlg.equals("exc")){
            ToMoney_Exchange();
        }else if(_MoneyTop.moneyFlg.equals("set")){
            ToMoney_Setting();
        }

    }

    //added
    public void ToMoney_Calender(){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_money, Money_Calendar.newInstance());
        ft.commit();
    }
    public void ToMoney_Graph(){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_money,Money_Graph.newInstance());
        ft.commit();
    }
    public void ToMoney_Exchange(){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_money, Money_Exchange.newInstance());
        ft.commit();
    }
    public void ToMoney_Setting(){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_money,Money_Setting.newInstance());
        ft.commit();
    }

    //textview のクリックイベントのテスト、xmlにクリックのリスナー？を設定している
    public void testToast(View view){
        Toast.makeText(getApplicationContext(), "Test OK!", Toast.LENGTH_SHORT).show();
    }


    public void calendar(){
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


        //初期設定
        Calendar cal = Calendar.getInstance();
        showyear = cal.get(Calendar.YEAR);//現在の年を取得
        showmonth = cal.get(Calendar.MONTH)+1;//現在の月を取得

        int num1 = 0;

        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        //年月表示
        TextView textView = (TextView)findViewById(R.id.txYM);

        textView.setText(showyear + "年" + showmonth + "月");

        //月の初めの曜日を求める
        calendar.set(showyear, showmonth -1,1); // 引数: 1月: 0, 2月: 1, ...
        startDay = calendar.get(Calendar.DAY_OF_WEEK);//曜日を取得

        //今月末の日付を求める
        calendar.add(Calendar.MONTH,1);
        calendar.add(Calendar.DATE,-1);
        lastDate1 = calendar.get(Calendar.DATE);//日を取得
        //lastDate1 = calendar.get(Calendar.DAY_OF_MONTH); でも同じ結果になる
        dayCount=1;

        //前月末の日付を求める
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DATE,-1);
        lastDate2 = calendar.get(Calendar.DATE);//日を取得

        //初期値ｾｯﾄ
        isStart = false;

        //前月末の日付用
        int x = 0;
        int y = 0;

        //今月の日にちを挿入
        for(int i=0 ; i < 6; i++){
            for(int j = 0; j < 7; j++){
                //先頭曜日確認
                //startDay:日曜日=1, 月曜日=2...
                if(isStart == false && (startDay -1) ==j){
                    //日にちのセット開始
                    isStart = true;
                    //今月のカレンダーに前月末表示用
                    y = i;
                    x = j;
                    lastDate2 = lastDate2 - (startDay -2);
                }
                if(isStart){
                    //終了日までいったか
                    calendarMatrix[i][j] = dayCount;
                    //カウント+1
                    dayCount++;
                    //終了確認
                    if(dayCount > lastDate1){
                        //来月初をセット
                        dayCount = 1;
                    }
                }
            }
        }
        //前月末の日にちを挿入
        for(int i = 0; i <= y; i++){
            for(int j = 0; j < x; j++){
                calendarMatrix[i][j] = lastDate2;
                lastDate2++;
            }
        }

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

                resId = getResources().getIdentifier(name, "id", getPackageName());
                TextView textView1 = (TextView)findViewById(resId);

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
    }

}
