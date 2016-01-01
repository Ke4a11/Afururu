package ke4a11.ecc.ac.jp.afururu.Money;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ke4a11.ecc.ac.jp.afururu.R;

public class Money_Calc extends Activity {

    //結果表示用のテキストView
    TextView mTextView1;

    //計算用オブジェクト
    Calculate mCalculator = new Calculate();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_calc);

        mTextView1 = (TextView)findViewById(R.id.display);
        mTextView1.setText("0");

        setView(R.id.button0);  //0ﾎﾞﾀﾝ
        setView(R.id.button00); //00ﾎﾞﾀﾝ

        //1-9までのﾎﾞﾀﾝにOnClickListenerを設定する
        setView(R.id.button1);
        setView(R.id.button2);
        setView(R.id.button3);
        setView(R.id.button4);
        setView(R.id.button5);
        setView(R.id.button6);
        setView(R.id.button7);
        setView(R.id.button8);
        setView(R.id.button9);

        //"+","-","*","/"各ボタンにOnClickListener押された時の処理を設定する
        setCalc(R.id.Plusbutton);   //"+"
        setCalc(R.id.Minusbutton);  //"-"
        setCalc(R.id.Multibutton);  //"*"
        setCalc(R.id.Divinebutton); //"/"

        findViewById(R.id.Equalbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button btn = (Button) v;
                String input = btn.getText().toString();
                String textvalue = mTextView1.getText().toString();

                mCalculator.putInput(textvalue);
                mTextView1.setText(mCalculator.putInput(input));
            }
        });

        //CLRが押されたら表示を"0"にする
        findViewById(R.id.Clearbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextView1.setText("0");
                mCalculator.reset();
            }
        });

    }

    //buttonIDを引数にして各ボタンにOnClickListenerを設定する独自メソッド
    public void setView(final int buttonID){
        findViewById(buttonID).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button btn = (Button) v;
                String input = btn.getText().toString();
                String textvalue = mTextView1.getText().toString();

                if (textvalue.length() < 12) {
                    if (textvalue.equals("0") || textvalue.equals("00")) {
                        //始めが0であれば入力した数字の未表示
                        mTextView1.setText(input);
                    } else {
                        //押したボタンの表示を右端に追加する
                        mTextView1.setText(textvalue + input);
                    }
                }
            }
        });
    }

    //数字意外が押された時の処理
    public void setCalc(final int buttonID){
        findViewById(buttonID).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button btn = (Button) v;
                //押されたボタンの文字列取得
                String input = btn.getText().toString();
                //現在表示されている文字
                String textvalue = mTextView1.getText().toString();

                mCalculator.putInput(textvalue); //演算前の入力中の数値をセットする
                mCalculator.putInput(input); //演算する演算記号をセットする
            }
        });
    }
}
