package sio.groupK;

import java.util.Objects;
import sio.tsp.TspImprovementHeuristic;
import sio.tsp.TspTour;

/**
 * Implementation of the 2-opt best improvement heuristic.
 *
 * @author Loïc Herman
 */
public final class TwoOptBestImprovement implements TspImprovementHeuristic {

    @Override
    public TspTour computeTour(TspTour tspTour) {
        // fail-fast if data integrity isn't respected
        Objects.requireNonNull(tspTour, "tspTour must not be null");

        // make local copies of the data we will need
        int[] tour = tspTour.tour();
        long length = tspTour.length();

        // initialize variables to track the best 2-opt move
        int iBest = -1;
        int jBest = -1;
        int gainBest;

        boolean improved = false;
        while (!improved) {
            // reset the best 2-opt move
            improved = true;
            gainBest = 0;
            // we need to iterate over every segment [i, j] such that (i, i+1) and (j, j+1)
            // are not symmetrical nor subsequent. If reversing the segment [i+1, j] is
            // improving, we store the gain and the indices of the segment.
            for (int i = 0; i < tour.length - 2; ++i) {
                int x1 = tour[i];
                int x2 = tour[(i + 1) % tour.length];

                // iterate over every edge (x1, x2) and (y1, y2)
                // while avoiding symmetrical pairs
                int jMax = i == 0 ? tour.length - 1 : tour.length;
                for (int j = i + 2; j < jMax; ++j) {
                    int y1 = tour[j];
                    int y2 = tour[(j + 1) % tour.length];

                    // compute the gain of the 2-opt move and update the best move if it is better
                    int gain = TwoOptUtils.computeDistanceGain(tspTour.data(), x1, x2, y1, y2);
                    if (gain > gainBest) {
                        iBest = i;
                        jBest = j;
                        gainBest = gain;
                        improved = false;
                    }
                }
            }

            // apply the best 2-opt move if it is improving
            if (!improved) {
                TwoOptUtils.reverseSegment(tour, (iBest + 1) % tour.length, jBest);
                length -= gainBest;
            }
        }

        return new TspTour(tspTour.data(), tour, length);
    }
}
