import java.awt.*;

/**
 * Created by CARLINSE1 on 3/28/2017.
 */
public interface Animatable {
    int[] getVals();
    void setVals(int[] vals);

    void destroy();

    void render(Graphics g);
}
