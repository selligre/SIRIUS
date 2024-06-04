package edu.cgl.sirius.client.commons;

import java.util.ArrayList;
import java.util.Arrays;

public class UtilsManager {

    public static void reorderWithDefaultOnTop(String[] array, String target) {
        ArrayList<String> list = new ArrayList<String>(Arrays.asList(array));
        int posT = list.indexOf(target);
        String pos0 = array[0];
        array[0] = target;
        array[posT] = pos0;
    }

}
