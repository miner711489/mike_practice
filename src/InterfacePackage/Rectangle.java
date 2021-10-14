package InterfacePackage;

/**
 * 矩形
 *
 * @author Mike
 */
public class Rectangle implements IShape {

    int width, height;

    public Rectangle(int w, int h) {
        this.width = w;
        this.height = h;
    }

    @Override
    public void show() {
        System.out.println("面積=" + width * height);

    }

}
