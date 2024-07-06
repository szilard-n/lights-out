package org.lightsout.shared.util;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class TestUtils {

    public static class SolvableGrids {
        public static final byte[][] GRID_3_X_3 = {
                {0, 1, 0},
                {1, 0, 1},
                {0, 1, 0}
        };

        public static final byte[][] GRID_4_X_4 = {
                {1, 0, 0, 1},
                {0, 1, 1, 0},
                {0, 1, 1, 0},
                {1, 0, 0, 1}
        };

        public static final byte[][] GRID_5_X_5 = {
                {0, 1, 0, 1, 0},
                {1, 0, 0, 0, 1},
                {1, 0, 1, 0, 1},
                {0, 0, 0, 1, 0},
                {0, 0, 0, 0, 0}
        };

        public static final byte[][] GRID_6_X_6 = {
                {0, 1, 0, 0, 1, 0},
                {1, 0, 1, 1, 0, 1},
                {1, 1, 1, 1, 1, 1},
                {0, 1, 0, 0, 1, 0},
                {1, 0, 1, 1, 0, 1},
                {1, 1, 1, 1, 1, 1}
        };

        public static final byte[][] GRID_7_X_7 = {
                {0, 1, 0, 1, 0, 1, 0},
                {1, 0, 1, 0, 1, 0, 1},
                {0, 1, 0, 1, 0, 1, 0},
                {1, 0, 1, 0, 1, 0, 1},
                {0, 1, 0, 1, 0, 1, 0},
                {1, 0, 1, 0, 1, 0, 1},
                {0, 1, 0, 1, 0, 1, 0}
        };

        public static final byte[][] GRID_8_X_8 = {
                {0, 1, 0, 1, 0, 1, 0, 1},
                {1, 0, 1, 0, 1, 0, 1, 0},
                {0, 1, 0, 1, 0, 1, 0, 1},
                {1, 0, 1, 0, 1, 0, 1, 0},
                {0, 1, 0, 1, 0, 1, 0, 1},
                {1, 0, 1, 0, 1, 0, 1, 0},
                {0, 1, 0, 1, 0, 1, 0, 1},
                {1, 0, 1, 0, 1, 0, 1, 0}
        };
    }

    public static class UnsolvableGrids {
        public static final byte[][] GRID_4_X_4 = {
                {1, 0, 1, 0},
                {0, 1, 0, 1},
                {1, 0, 1, 0},
                {0, 1, 0, 1}
        };
    }

    public static List<List<Byte>> toList(byte[][] array) {
        List<List<Byte>> list = new ArrayList<>();
        for (byte[] row : array) {
            List<Byte> byteList = new ArrayList<>();
            for (byte b : row) {
                byteList.add(b);
            }
            list.add(byteList);
        }
        return list;
    }
}
