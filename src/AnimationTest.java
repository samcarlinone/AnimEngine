import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by CARLINSE1 on 3/29/2017.
 */
public class AnimationTest {
    @Test
    public void parseStringBasic() {
        Segment[] result, expected;

        //Test Comparison
        expected = new Segment[]{
                new Segment(SegmentType.JUMP_SEGMENT, new int[]{4, 3}),
                new Segment(SegmentType.LERP_SEGMENT, new int[]{1, 2}),
                new Segment(SegmentType.WAIT_SEGMENT, new int[]{3})
        };
        result = new Segment[]{
                new Segment(SegmentType.JUMP_SEGMENT, new int[]{4, 3}),
                new Segment(SegmentType.LERP_SEGMENT, new int[]{1, 2}),
                new Segment(SegmentType.WAIT_SEGMENT, new int[]{3})
        };
        Assert.assertArrayEquals(expected, result);

        //Basic Test
        expected = new Segment[]{
                new Segment(SegmentType.JUMP_SEGMENT, new int[]{0, 0}),
                new Segment(SegmentType.WAIT_SEGMENT, new int[]{3})
        };
        result = listToArr(AnimParser.getAnimation(2, "[0, 0]={3}"));
        Assert.assertArrayEquals(expected, result);
    }

    @Test
    public void parseStringAdvanced() {
        Segment[] result, expected;

        //Advanced Test
        expected = new Segment[]{
                new Segment(SegmentType.JUMP_SEGMENT, new int[]{20, 2}),
                new Segment(SegmentType.WAIT_SEGMENT, new int[]{3}),
                new Segment(SegmentType.LERP_SEGMENT, new int[]{-1, 29}),
                new Segment(SegmentType.WAIT_SEGMENT, new int[]{4}),
                new Segment(SegmentType.JUMP_SEGMENT, new int[]{3, -1}),
                new Segment(SegmentType.WAIT_SEGMENT, new int[]{5})
        };
        result = listToArr(AnimParser.getAnimation(2, "[20,2]={3}=(-1,29)={4}=[3,-1]={3}={2}"));
        Assert.assertArrayEquals(expected, result);
    }

    private Segment[] listToArr(ArrayList<Segment> list) {
        Segment[] result = new Segment[list.size()];
        list.toArray(result);
        return result;
    }

    @Test
    public void pack() {
        int[] test = {1, 2, 3};

        Assert.assertEquals("{1,2,3}", AnimParser.pack('{', test));
        Assert.assertEquals("(1,2,3)", AnimParser.pack('(', test));
        Assert.assertEquals("[1,2,3]", AnimParser.pack('[', test));
    }
}
