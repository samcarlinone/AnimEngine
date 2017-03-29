import java.awt.*;
import java.util.ArrayList;

/**
 * Created by CARLINSE1 on 3/28/2017.
 */
public class AnimManager {
    /**
     * This is a singleton that handles rendering and input management
     */
    private static AnimManager instance = null;

    protected AnimManager() {
        sequences = new ArrayList<>();
    }

    public static AnimManager get() {
        if (instance == null) {
            instance = new AnimManager();
        }
        return instance;
    }

    private ArrayList<AnimSequence> sequences;

    public void tick() {
        for(int i=0; i<sequences.size(); i++) {
            if(sequences.get(i).tick()) {
                sequences.remove(i);
                i--;
            }
        }
    }

    public void render(Graphics g) {
        for(AnimSequence anim : sequences) {
            anim.target.render(g);
        }
    }

    public AnimSequence animate(Animatable target, int numVals, String data) {
        AnimSequence anim = new AnimSequence(target, AnimParser.getAnimation(numVals, data));
        sequences.add(anim);
        return anim;
    }
}