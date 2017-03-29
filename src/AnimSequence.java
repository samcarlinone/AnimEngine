import java.util.ArrayList;

/**
 * Created by CARLINSE1 on 3/28/2017.
 */
public class AnimSequence {
    private final double TIMESTEP = 1d / 60d;

    private double time;

    private int last_segment=0;
    private double last_time=0;

    public Animatable target;
    private ArrayList<Segment> segments;

    public AnimSequence(Animatable target, ArrayList<Segment> path) {
        this.target = target;
        segments = path;

        time = 0;
    }

    public boolean tick() {
        time += TIMESTEP;

        double current_time = last_time;

        for(int i=last_segment; i<segments.size(); i++) {
            if(segments.get(i).getType() == SegmentType.WAIT_SEGMENT) {
                if(last_segment != i)
                    current_time += segments.get(i).getVal(0);

                if(current_time > time) {
                    if(i>last_segment) {
                        last_segment = i;
                        last_time = current_time;
                    }

                    Segment prev = segments.get(i-1);
                    int[] vals = new int[prev.numVals()];

                    switch(prev.getType()) {
                        case LERP_SEGMENT:
                            if(i+1 == segments.size())
                                return false;

                            Segment next = segments.get(i+1);

                            if(next.getType() != SegmentType.LERP_SEGMENT && next.getType() == SegmentType.JUMP_SEGMENT)
                                return false;

                            double p = 1-(current_time-time)/segments.get(i).getVal(0);

                            for(int j=0; j<prev.numVals(); j++) {
                                vals[j] = lerp(prev.getVal(j), next.getVal(j), p);
                            }

                            target.setVals(vals);

                            return false;

                        case JUMP_SEGMENT:
                            for(int j=0; j<prev.numVals(); j++) {
                                vals[j] = prev.getVal(i);
                            }

                            target.setVals(vals);
                            return false;
                    }
                }
            }
        }

        return true;
    }

    private int lerp(int begin, int end, double percent) {
        return (int) Math.round((end-begin)*percent+begin);
    }
}
