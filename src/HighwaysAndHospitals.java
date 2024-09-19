import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Highways & Hospitals
 * A puzzle created by Zach Blick
 * for Adventures in Algorithms
 * at Menlo School in Atherton, CA
 *
 * Completed by: Josh Little
 *
 */

public class HighwaysAndHospitals {

    /**
     * TODO: Complete this function, cost(), to return the minimum cost to provide
     *  hospital access for all citizens in Menlo County.
     */

    // Idea: To calculate the final price, all you need to do is find the number of indepedent
    // clusters that don't connect to eachother, and then perform the math of
    // (clusters * hospital price) + (cities-clusters*road price) = Final price.
    // TO do this, I'm thinking of running BFS on the first in the 2D cities array and then creating
    // another array that holds a number corresponding to each of the cities where I can
    // mark something as visited and not visited. If something is not visited by the time
    // BFS no longer has any elements to add, start a new search with that element as
    // the first element in the queue.

    public static boolean checkVisited(int[] visited) {
        for (int i = 1; i < visited.length; i++)
            if (visited[i] == 0)
                return false;
        return true;
    }


    public static long cost(int n, int hospitalCost, int highwayCost, int cities[][]) {

        // Print Statements
        Arrays.sort(cities, (a, b) -> Integer.compare(a[0],b[0]));

        // If the hospital cost is lower than that of a highway, just build a hospital everywhere.
        if (hospitalCost < highwayCost)
            return Long.valueOf(n) * hospitalCost;

        // Array to hold the root of each city and a zero if it's its own root
        int[] roots = new int[n + 1];
        int currentNode;
        int firstCity, firstCityCopy, secondCity, secondCityCopy, temp;
        for (int i = 0; i < cities.length; i++) {
            firstCity = cities[i][0];
            secondCity = cities[i][1];
            firstCityCopy = cities[i][0];
            secondCityCopy = cities[i][1];
            while (roots[firstCity] > 0) {
                firstCity = roots[firstCity];
            }
            while (roots[firstCityCopy] != firstCity && firstCity <= 0) {
                temp = roots[firstCityCopy];
                roots[firstCityCopy] = firstCity;
                firstCityCopy = temp;
            }
            while (roots[secondCity] > 0) {
                secondCity = roots[secondCity];
            }
            while (roots[secondCityCopy] != secondCity && secondCity <= 0) {
                temp = roots[secondCityCopy];
                roots[secondCityCopy] = secondCity;
                secondCityCopy = temp;
            }
            if (firstCity == secondCity)
               continue;
            else if (roots[firstCity] <= roots[secondCity]) {
                roots[firstCity] += roots[secondCity] - 1;
                roots[secondCity] = firstCity;
            }
            else {
                roots[secondCity] = roots[firstCity] - 1;
                roots[firstCity] = secondCity;
            }
        }

        int clusters = 0;
        for (int i = 1; i < roots.length; i++) {
            if (roots[i] <= 0)
                clusters++;
        }
        // The current problem is that the system only takes into account the first connection
        // between two cities rather than the second one so if 4 connected to 6 and 5 to 6,
        // five is considered a new cluster as it's not connected to anything else in the 2nd
        // connection of an edge despite actually being connected to 6 which is connected to 4
        return Long.valueOf(clusters) * hospitalCost + ((n - clusters) * highwayCost);
    }
}
