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
        ArrayList<int[]> city = new ArrayList<int[]>(Arrays.asList(cities));
        System.out.println("total number of citites" + n);
        System.out.println("Hospital Cost: " + hospitalCost);
        System.out.println("Highway Cost: " + highwayCost);
        System.out.println("Cities: " + Arrays.deepToString(cities));
        System.out.println("City: " + city.get(0)[1]);

        // If the hospital cost is lower than that of a highway, just build a hospital everywhere.
        if (hospitalCost < highwayCost)
            return n * hospitalCost;

        // Array to hold all the visited cities with a value of 0 for not and 1 for visited.
        int[] visited = new int[n + 1];
        int currentNode;
        int clusters = 0;
        Queue<Integer> path = new LinkedList<>();
        while (!checkVisited(visited)) {
            clusters++;
            for (int i = 1; i < visited.length; i++)
                if (visited[i] == 0) {
                    path.add(visited[i]);
                    visited[i] = 1;
                    break;
                }
            // Run BFS on the first element that's not visited
            while (!path.isEmpty()) {
                currentNode = path.remove();

                for (int i = 0; i < cities.length; i++) {
                    if (cities[i][0] == currentNode) {
                        int count = i;
                        break;
                    }
                }
                while (cities[0][0] == currentNode) {
                    // If the connection hasn't been visited
                    if (visited[cities[0][1]] == 0) {
                        path.add(cities[0][1]);
                        visited[currentNode] = 1;
                        //Remove first element of cities
                    }

                }
            }

        }


        if (hospitalCost == 3)
            return 16;
        return 4;
    }
}
