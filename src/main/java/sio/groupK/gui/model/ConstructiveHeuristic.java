package sio.groupK.gui.model;

import sio.groupK.DoubleEndsNearestNeighbor;
import sio.groupK.NearestNeighbor;
import sio.tsp.RandomTour;
import sio.tsp.TspConstructiveHeuristic;

/**
 * Heuristics for the TSP used in the visualiser.
 */
public enum ConstructiveHeuristic {
    NN {
        @Override
        public TspConstructiveHeuristic getHeuristicInstance() {
            return new NearestNeighbor();
        }
    },
    DENN {
        @Override
        public TspConstructiveHeuristic getHeuristicInstance() {
            return new DoubleEndsNearestNeighbor();
        }
    },
    RANDOM {
        @Override
        public TspConstructiveHeuristic getHeuristicInstance() {
            return new RandomTour();
        }
    };

    /**
     * @return Instance of the current heuristic
     */
    public abstract TspConstructiveHeuristic getHeuristicInstance();
}
