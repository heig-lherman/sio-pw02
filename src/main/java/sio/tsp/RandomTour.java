package sio.tsp;

import java.util.Random;

/**
 * <p>Builds a random tour for the TSP.</p>
 *
 * <p>All tours computed with a given instance use the same pseudorandom number generator instance. Thus, if a seed
 * is set, two consecutive calls to {@link RandomTour#computeTour} won't give the same tour, but two
 * instances initialized with the same seed will give the same tours (for the same number of cities, of course).</p>
 *
 * <p>Space and time complexity is linear with the number of cities.</p>
 */
public final class RandomTour implements TspConstructiveHeuristic {
  private final Random rnd;

  /**
   * Builds a new instance with no seed.
   */
  public RandomTour() {
    this.rnd = new Random();
  }

  /**
   * Builds a new instance with the given seed for the pseudorandom generator.
   * @param seed A seed
   */
  public RandomTour(long seed) {
    this();
    rnd.setSeed(seed);
  }

  @Override
  public TspTour computeTour(TspData data, int startCityIndex) {
    int[] tour = new int[data.getNumberOfCities()];

    // Initialization
    for (int i = 0; i < tour.length; ++i) {
      tour[i] = i;
    }

    // Shuffle
    for (int i = tour.length; i > 1; --i) {
      int j = rnd.nextInt(i);
      int tmp = tour[i-1];
      tour[i-1] = tour[j];
      tour[j] = tmp;
    }

    // Compute tour length
    int length = data.getDistance(tour[0], tour[tour.length - 1]);
    for (int i = 1; i < tour.length; ++i) {
      length += data.getDistance(tour[i - 1], tour[i]);
    }

    return new TspTour(data, tour, length);
  }
}
