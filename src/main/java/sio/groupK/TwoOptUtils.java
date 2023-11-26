package sio.groupK;

import sio.tsp.TspData;

/**
 * Utility class for the 2-opt exchange.
 *
 * @author Loïc Herman
 */
final class TwoOptUtils {

    private TwoOptUtils() {
    }

    /**
     * Check if the 2-opt exchange is improving and return the gain.
     *
     * @param data TSP data
     * @param x1   first node of the first edge
     * @param x2   second node of the first edge
     * @param y1   first node of the second edge
     * @param y2   second node of the second edge
     * @return positive integer if the exchange is improving with the distance
     */
    static int computeDistanceGain(TspData data, int x1, int x2, int y1, int y2) {
        // compute the distance that is removed
        int delDist = data.getDistance(x1, x2) + data.getDistance(y1, y2);
        // compute the distance that is added
        int addDist = data.getDistance(x1, y1) + data.getDistance(x2, y2);
        return delDist - addDist;
    }

    /**
     * Reverse the segment [i, j] of the tour.
     *
     * @param tour the tour
     * @param i    the first index
     * @param j    the last index
     */
    static void reverseSegment(int[] tour, int i, int j) {
        // reverse the segment [i, j] of the tour
        while (i < j) {
            int tmp = tour[i];
            tour[i] = tour[j];
            tour[j] = tmp;
            ++i;
            --j;
        }
    }
}
