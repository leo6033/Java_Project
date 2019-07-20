package ¹ş·òÂü;

import java.util.Comparator;

public class Compare implements Comparator<HufTree>{

    @Override
    public int compare(HufTree o1, HufTree o2) {
        if(o1.weight < o2.weight)
            return -1;
        else if(o1.weight > o2.weight)
            return 1;
        return 0;
    }

}
