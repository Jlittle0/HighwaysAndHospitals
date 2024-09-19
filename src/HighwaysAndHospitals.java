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

    public static long cost(int n, int hospitalCost, int highwayCost, int cities[][]) {
        // Unneccesary but wanted to keep this sorting bc it was part of original solution
        // and is a helpful visualization whenever I need to print out the cities array for testing.
//        Arrays.sort(cities, (a, b) -> Integer.compare(a[0],b[0]));

        // If the hospital cost is lower than that of a highway, just build a hospital everywhere.
        if (hospitalCost < highwayCost)
            return Long.valueOf(n) * hospitalCost;

        // Array to hold the root of each city and a zero if it's its own root
        int[] roots = new int[n + 1];
        // Variables to store the first and second city connected between an edge as well as
        // copies of said variables to rerun through the path and implement path compression.
        int firstCity, firstCityCopy, secondCity, secondCityCopy, temp;
        // For every edge...
        for (int i = 0; i < cities.length; i++) {
            // Assign varaibles to actual city values
            firstCity = cities[i][0];
            secondCity = cities[i][1];
            firstCityCopy = cities[i][0];
            secondCityCopy = cities[i][1];
            // While the first element of the edge's parent isn't a root, keep traversing up tree
            while (roots[firstCity] > 0) {
                firstCity = roots[firstCity];
            }
            // After finding the root, then go back through until everything in the previous path
            // is pointing to it
            while (roots[firstCityCopy] != firstCity && firstCity <= 0) {
                temp = roots[firstCityCopy];
                roots[firstCityCopy] = firstCity;
                firstCityCopy = temp;
            }
            // Repeat the above processes on the second element connected by the current edge
            while (roots[secondCity] > 0) {
                secondCity = roots[secondCity];
            }
            // Same as above
            while (roots[secondCityCopy] != secondCity && secondCity <= 0) {
                temp = roots[secondCityCopy];
                roots[secondCityCopy] = secondCity;
                secondCityCopy = temp;
            }
            // After all paths are compressed and roots found, check which is greater or if the
            // two elements of the edge share the same root. If they do share, then do nothing.
            // If the first is bigger, have it inheret the second and vice versa.
            if (firstCity == secondCity)
               continue;
            // Subtracting an extra -1 to include the root that isn't held in its own value in map.
            else if (roots[firstCity] <= roots[secondCity]) {
                // Since the orders are stored as negative values, subtract one to include root
                roots[firstCity] += roots[secondCity] - 1;
                roots[secondCity] = firstCity;
            }
            else {
                roots[secondCity] = roots[firstCity] - 1;
                roots[firstCity] = secondCity;
            }
        }

        // Find the number of independent graphs by looking for the # of roots (negative or 0)
        int clusters = 0;
        for (int i = 1; i < roots.length; i++) {
            if (roots[i] <= 0)
                clusters++;
        }

        // Return the price of everything!
        return Long.valueOf(clusters) * hospitalCost + ((n - clusters) * highwayCost);
    }
}
