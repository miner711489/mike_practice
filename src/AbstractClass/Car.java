/*
無法instance(實體化)；不然就是要在實體化時，實作所有抽象類方法(abstract class)
子類別一定要實做抽象類別

1.只能繼承一個類別
2.子類別只能繼承一個抽象類別
3.可以含非抽象方法
4.定義相似的類別，可用來當作範本使用
5.一定是父類別
 */
package AbstractClass;

/**
 *
 * @author Mike
 */
public abstract class Car {

    public int Speed;
    String CountryName;

    //Car加速
    abstract public void accelerate(int acc);

    //Car減速
    abstract public void slowdown(int sw);

    //取得目前速度
    public int showSpeed() {
        return Speed;
    }

    //取得生產國名稱
    public String getCountryName() {
        return CountryName;
    }

    //設定生產國名稱
    public void setCountryName(String CountryName) {
        this.CountryName = CountryName;
    }

}
