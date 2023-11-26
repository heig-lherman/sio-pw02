package sio.groupK.gui.model;

import sio.groupK.TwoOptBestImprovement;
import sio.groupK.TwoOptFirstImprovement;
import sio.tsp.TspImprovementHeuristic;

/**
 * Heuristics for the TSP used in the visualiser.
 */
public enum ImprovementHeuristic {
    NONE {
        @Override
        public TspImprovementHeuristic getHeuristicInstance() {
            return tour -> tour;
        }
    },
    TWO_OPT_FIRST {
        @Override
        public TspImprovementHeuristic getHeuristicInstance() {
            return new TwoOptFirstImprovement();
        }
    },
    TWO_OPT_BEST {
        @Override
        public TspImprovementHeuristic getHeuristicInstance() {
            return new TwoOptBestImprovement();
        }
    };

    /**
     * @return Instance of the current heuristic
     */
    public abstract TspImprovementHeuristic getHeuristicInstance();
}
