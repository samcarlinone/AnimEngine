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

    public Segment(SegmentType type, int[] vals) {
        this.type = type;
        numVals = vals.length;
        this.vals = new int[numVals];

        setVals(vals);
    }

    /**
     * This should only be called once, calling multiple times does nothing
     */
    public void setVals(int[] vals) {
        if(valsSet)
            return;

        for(int i=0; i<numVals; i++) {
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

    @Override
    public boolean equals(Object o) {
        if(this == o)
            return true;

        if(o instanceof Segment) {
            Segment other = (Segment)o;

            if(other.numVals != numVals) {
                return false;
            }

            for(int i=0; i<numVals; i++) {
                if(other.vals[i] != vals[i])
                    return false;
            }

            return true;
        }

        return false;
    }

    @Override
    public int hashCode() {
        int sum = 0;

        for(int i=0; i<numVals; i++) {
            sum += vals[i]*i;
        }

        return sum;
    }
}
