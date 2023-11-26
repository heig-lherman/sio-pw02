package sio.groupK;

import java.util.Objects;
import sio.tsp.TspImprovementHeuristic;
import sio.tsp.TspTour;

/**
 * Implementation of the 2-opt first improvement heuristic.
 *
 * @author Loïc Herman
 */
public final class TwoOptFirstImprovement implements TspImprovementHeuristic {

    @Override
    public TspTour computeTour(TspTour tspTour) {
        // fail-fast if data integrity isn't respected
        Objects.requireNonNull(tspTour, "tspTour must not be null");

        // make local copies of the data we will need
        int[] tour = tspTour.tour();
        long length = tspTour.length();

        boolean improved = false;
        while (!improved) {
            improved = true;
            // we need to iterate over every segment [i, j] such that (i, i+1) and (j, j+1)
            // are not symmetrical nor subsequent. If reversing the segment [i+1, j] is
            // improving, we store the gain and the indices of the segment.
            for (int i = 0; i < tour.length - 2; ++i) {
                int segmentStartIndex = (i + 1) % tour.length;
                int x1 = tour[i];
                int x2 = tour[segmentStartIndex];

                // avoid the case where j+1 == i
                int jMax = i == 0 ? tour.length - 1 : tour.length;
                for (int j = i + 2; j < jMax; ++j) {
                    int y1 = tour[j];
                    int y2 = tour[(j + 1) % tour.length];

                    // compute the gain of the 2-opt move and apply it right away if it is positive
                    int gain = TwoOptUtils.computeDistanceGain(tspTour.data(), x1, x2, y1, y2);
                    if (gain > 0) {
                        TwoOptUtils.reverseSegment(tour, segmentStartIndex, j);
                        improved = false;
                        length -= gain;

                        // update x2 so that the next iteration starts from the right place
                        x2 = tour[segmentStartIndex];
                    }
                }
            }
        }

        return new TspTour(tspTour.data(), tour, length);
    }
}
