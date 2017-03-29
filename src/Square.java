import java.awt.*;

/**
 * Created by CARLINSE1 on 3/28/2017.
 */
public class Square implements Animatable {
    private int x;
    private int y;
    private int width;
    private int height;

    public Square(int x, int y) {
        this.x = x;
        this.y = y;
        width = height = 10;
    }

    @Override
    public int[] getVals() {
        int[] vals = {this.x, this.y};
        return vals;
    }

    @Override
    public void setVals(int[] vals) {
        this.x = vals[0];
        this.y = vals[1];
        this.width = vals[2];
        this.height = vals[3];
    }

    @Override
    public void destroy() {
        //TODO: Stop Rendering
    }

    @Override
    public void render(Graphics g) {
        g.drawRect(x, y, width, height);
    }
}
