package ke4a11.ecc.ac.jp.afururu.Money;

/**
 * Created by iwasakiyoshinobu on 2015/12/29.
 */
public class Calculate {

    //入力中の文字列
    public StringBuilder mInputNumber = new StringBuilder();
    //入力中の演算子
    String mOperator;
    //計算結果
    public int mResult = 0;

    //パラメータのKeyが数値ならTRUEを返却
    private boolean isNumber(String key) {
        try {
            Integer.parseInt(key);
            return true;
        } catch (NumberFormatException e) {
        }
        return false;
    }

    //パラメーターのKeyがサポートしている演算子ならTRUEを返却
    private boolean isSupportedOperator(String key){
        if(key.equals("+")){
            return true;
        }else if (key.equals("-")){
            return true;
        }else if (key.equals("*")){
            return true;
        }else if (key.equals("/")){
            return true;
        }else if (key.equals("=")){
            return true;
        }
        return false;
    }

    //演算を実施
    private void doCalculation(String ope){
        if(ope.equals("+")){
            mResult = mResult + Integer.parseInt(mInputNumber.toString());
        }else if (ope.equals("-")){
            mResult = mResult - Integer.parseInt(mInputNumber.toString());
        }else if(ope.equals("*")){
            mResult = mResult * Integer.parseInt(mInputNumber.toString());
        }else if (ope.equals("/")){
            mResult = mResult / Integer.parseInt(mInputNumber.toString());
        }
        mInputNumber = new StringBuilder();
    }

    //ｸﾘｱ処理
    public  void reset(){
        mOperator = null;
        mResult=0;
        mInputNumber = new StringBuilder();
    }

    //入力された文字を元に処理を行い、ﾃﾞｨｽﾌﾟﾚｲに表示する結果を返却
    public String putInput(String key) {
        if (isNumber(key)) {
            //数値の場合次の入力を待つ
            mInputNumber.append(key);
            return mInputNumber.toString();
        } else if (isSupportedOperator(key)) {
            //サポートしている演算子の場合、入力中の数値を元に演算を行う
            if (key.equals("=")){
                //=なら演算を行い結果を返却する
                if (mOperator !=null){
                    doCalculation((mOperator));
                    mOperator = null;
                }
                return Integer.toString(mResult);
            } else {
                if (mOperator != null) {
                    //入力中の演算子があるなら前回の結果を用いて演算を行う
                    doCalculation(mOperator);
                    mOperator = null;
                } else if (mInputNumber.length() > 0) {
                    //初めての演算子なら入力中の数値を設定する
                    mResult = Integer.parseInt(mInputNumber.toString());
                    mInputNumber = new StringBuilder();
                }
                mOperator = key;
                return mOperator;
            }
        }else{
            return null;
        }
    }
}
