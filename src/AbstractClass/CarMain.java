
package AbstractClass;

/**
 *
 * @author Mike
 */
public class CarMain {

    public void main() {

        try {
            //直接instance 抽象類別，就需要定義裡面的抽象方法
            Car car = new Car() {
                @Override
                public void accelerate(int acc) {
                    this.Speed += acc;
                }

                @Override
                public void slowdown(int sw) {
                    if (this.Speed < sw) {
                        this.Speed = 0;
                    } else {
                        this.Speed -= sw;
                    }
                }
            };

            car.accelerate(5);
            car.accelerate(10);
            System.out.println(car.showSpeed());

            BMW bmw = new BMW();
            System.out.println(bmw.getCountryName());
            System.out.println(bmw.showSpeed());

            BMW bmw_jp01 = new BMW();
            bmw_jp01.setCountryName("日本");
            System.out.println(bmw_jp01.getCountryName());
            System.out.println(bmw_jp01.showSpeed());

        } catch (Exception e) {
            System.out.println("Error Message:" + e.getMessage());
        } finally {
        }

    }

}
