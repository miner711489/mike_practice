package Main;

/**
 *
 * @author Mike
 */
public class MikeTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {
            //ValidEmailFormat Valid = new ValidEmailFormat();
            //System.out.println(Valid.Test());

            ImportXml importxml = new ImportXml();
            //System.out.println(importxml.Import());

            String input = "D.RpsDepNam,D.RpsDivNam,D.RpsUserNam,Count(D.DocNO) DocNONum,sum(D.UseDays) UseDays";
            input = "Select *,convert(decimal(3,0),RANK() over(order by AvgUseDay Desc)) OrderSeqNO";
            input = "AvgUseDay";
            LowerCase LC = new LowerCase();
            System.out.println(LC.getLowerCase(input));

        } catch (Exception e) {
            System.out.println("Error Message:" + e.getMessage());
        }

    }

}
