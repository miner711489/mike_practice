package Main;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 *
 * @author Mike
 */
public class decimalFormat {

    public void DFTest() {
        DecimalFormat df = new DecimalFormat("##.00");//取小數點後第二位
        double A = 100;
        double B = 55;
        System.out.print(df.format(100.0000));

    }

    public void DFTest2() {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);//取小數後兩位

        int A = 101;
        int B = 55;

        double p1 = (double) B/ (double) A * 100;
        p1 = Double.valueOf(nf.format(110.00000));

        System.out.print(p1);

    }
}
