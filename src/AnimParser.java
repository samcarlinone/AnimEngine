import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by CARLINSE1 on 3/29/2017.
 */
public class AnimParser {
    private static HashMap<String, ArrayList<Segment>> segment_cache = new HashMap<>();

    public static String pack(char start, int[] vals) {
        String array = Arrays.toString(vals);
        array = array.replace(" ","");

        switch(start) {
            case '(':
                return '('+array.substring(1, array.length()-1)+')';
            case '{':
                return '{'+array.substring(1, array.length()-1)+'}';
            case '[':
                return '['+array.substring(1, array.length()-1)+']';
        }

        return "";
    }

    public static ArrayList<Segment> getAnimation(int numVals, String data) {
        if(numVals <= 0)
            return null;

        if(!segment_cache.containsKey(data)) {
            segment_cache.put(data, parseStringAnimation(numVals, data));
        }

        return segment_cache.get(data);
    }

    private static ArrayList<Segment> parseStringAnimation(int numVals, String data) {
        data = data.replace(" ", "");
        ArrayList<Segment> result = new ArrayList<>();

        for(int i=0; i<data.length();) {
            Segment s = null;

            switch(data.charAt(i)) {
                case '=':
                    //Space Value Increment i
                    i++;
                    break;
                case '{':
                    s = new Segment(SegmentType.WAIT_SEGMENT, 1);
                    break;
                case '(':
                    s = new Segment(SegmentType.LERP_SEGMENT, numVals);
                    break;
                case '[':
                    s = new Segment(SegmentType.JUMP_SEGMENT, numVals);
                    break;

                default:
                    System.out.println("Error has occurred while parsing animation");
                    break;
            }

            if(s != null) {
                result.add(s);
                i = GenSegment(i+1, data, s);
            }
        }

        for(int i=0; i<result.size()-1; i++) {
            if(result.get(i).getType() == SegmentType.WAIT_SEGMENT && result.get(i+1).getType() == SegmentType.WAIT_SEGMENT) {
                Segment new_wait = new Segment(SegmentType.WAIT_SEGMENT, 1);
                new_wait.setVals(new int[] {result.get(i).getVal(0)+result.get(i+1).getVal(0)});
                result.add(i, new_wait);
                result.remove(i+1);
                result.remove(i+1);
                i--;
            }
        }

        return result;
    }

    private static int GenSegment(int index, String data, Segment seg) {
        int numCommas = 0;
        int lastIndex = index;
        int num = seg.numVals();
        int[] vals = new int[num];

        for(int i=index; i<data.length(); i++) {
            String cur = data.substring(i, i+1);

            if(!cur.matches("[\\d-]")) {
                vals[numCommas] = Integer.parseInt(data.substring(lastIndex, i));

                if(numCommas == num-1) {
                    seg.setVals(vals);
                    return i+1;
                }

                lastIndex = i+1;
                numCommas++;
                i++;
            }
        }

        return data.length();
    }
}
