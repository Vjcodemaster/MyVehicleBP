package app_utility;

import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;

class RandomColorPalette {
    private Stack<Integer> recycle, colors;

    public RandomColorPalette() {
        colors = new Stack<>();
        recycle =new Stack<>();
        recycle.addAll(Arrays.asList(
                0xfff44336,0xffe91e63,0xff9c27b0,0xff673ab7,
                0xff3f51b5,0xff2196f3,0xff03a9f4,0xff00bcd4,
                0xffd6e920,0xffab3be0,
                0xffffc107,0xffff9800,0xffff5722,
                0xff795548,0xff607d8b,0xff333333,
                0xff31afc6,0xff49d593,0xfff4a10c,0xfff35d0b
                )
        );
    }

    public int getColor() {
        if (colors.size()==0) {
            while(!recycle.isEmpty())
                colors.push(recycle.pop());
            Collections.shuffle(colors);
        }
        Integer c= colors.pop();
        recycle.push(c);
        return c;
    }
}
