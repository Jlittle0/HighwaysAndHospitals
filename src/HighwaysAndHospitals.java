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

        // Array to hold all the visited cities with a value of 0 for not and 1 for visited.
        int[] visited = new int[n + 1];
        int currentNode;
        int clusters = 0;
        Queue<Integer> path = new LinkedList<>();
        while (!checkVisited(visited)) {
            clusters++;
            for (int i = 1; i < visited.length; i++)
                if (visited[i] == 0) {
                    path.add(i);
                    visited[i] = 1;
                    break;
                }
            // Run BFS on the first element that's not visited
            while (!path.isEmpty()) {
                boolean found = false;
                boolean foundSecond = false;
                int count = 0;
                int temp = 0;
                Arrays.sort(cities, (a, b) -> Integer.compare(a[0], b[0]));
                currentNode = path.remove();
                visited[currentNode] = 1;
                for (int i = 0; i < cities.length; i++) {
                    if (cities[i][0] == currentNode) {
                        count = i;
                        found = true;
                        break;
                    }
                }
                if (found) {
                    while (cities[count + temp][0] == currentNode) {
                        // If the connection hasn't been visited
                        if (visited[cities[count + temp][1]] == 0) {
                            path.add(cities[count + temp][1]);
                            visited[cities[count + temp][1]] = 1;
                        }
                        if (count + temp == cities.length - 1)
                            break;
                        temp++;
                    }
                }
                Arrays.sort(cities, (a, b) -> Integer.compare(a[1], b[1]));
                for (int i = 0; i < cities.length; i++) {
                    if (cities[i][1] == currentNode) {
                        count = i;
                        foundSecond = true;
                        break;
                    }
                }
                if (foundSecond) {
                    temp = 0;
                    while (cities[count + temp][1] == currentNode) {
                        // If the connection hasn't been visited
                        if (visited[cities[count + temp][0]] == 0) {
                            path.add(cities[count + temp][0]);
                            visited[cities[count + temp][0]] = 1;
                        }
                        if (count + temp == cities.length - 1)
                            break;
                        temp++;
                    }
                }
            }
        }
        // The current problem is that the system only takes into account the first connection
        // between two cities rather than the second one so if 4 connected to 6 and 5 to 6,
        // five is considered a new cluster as it's not connected to anything else in the 2nd
        // connection of an edge despite actually being connected to 6 which is connected to 4
        return clusters * hospitalCost + ((n - clusters) * highwayCost);
    }
}
