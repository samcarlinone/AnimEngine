/**
 * Created by CARLINSE1 on 3/28/2017.
 */
public class Segment {
    private SegmentType type;

    private int[] vals;
    private boolean valsSet = false;
    private int numVals;

    public Segment(SegmentType type, int numVals) {
        this.type = type;
        this.vals = new int[numVals];
        this.numVals = numVals;
    }

    /**
     * This should only be called once, calling multiple times does nothing
     */
    public void setVals(int[] vals) {
        if(valsSet)
            return;

        for(int i=0; i<this.vals.length; i++) {
            this.vals[i] = vals[i];
        }

        valsSet = true;
    }

    public int getVal(int index) {
        return vals[index];
    }

    public SegmentType getType() {
        return type;
    }

    public int numVals() {
        return this.numVals;
    }
}
