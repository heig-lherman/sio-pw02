package sio.tsp;

/**
 * Improvement heuristic for the TSP
 */
@FunctionalInterface
public interface TspImprovementHeuristic {
  /**
   * <p>Computes a tour for the travelling salesman problem by improving the given tour.</p>
   *
   * <p>No guarantee is given as to the optimality of the resulting tour.</p>
   *
   * @param tour Existing
   *
   * @return Solution found by the heuristic
   * @throws NullPointerException if {@code tour} is null
   */
  TspTour computeTour(TspTour tour);
}
