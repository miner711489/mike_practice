package InterfacePackage;

/**
 * 圓
 *
 * @author Mike
 */
public class Circle implements IShape, IColor {

    double radius;
    String color;

    public Circle(double r) {
        this.radius = r;
    }

    @Override
    public void show() {
        System.out.println("面積=" + PI * radius * radius);
    }

    @Override
    public void getColor() {
        System.out.println("顏色是" + this.color);
    }

    @Override
    public void setColor(String color) {
        this.color = color;
    }

}
