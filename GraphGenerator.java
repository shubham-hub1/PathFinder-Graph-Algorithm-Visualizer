import java.util.Random;

public class GraphGenerator {

    private static final Random rand = new Random(42);

    // random weighted undirected graph
    public static Graph random(int V, int E, int maxWeight) {
        Graph g = new Graph(V, false);
        // ensure connectivity: chain first
        for (int i = 0; i < V - 1; i++)
            g.addEdge(i, i + 1, rand.nextInt(maxWeight) + 1);
        // add remaining random edges
        int added = V - 1;
        while (added < E) {
            int u = rand.nextInt(V);
            int v = rand.nextInt(V);
            if (u != v) {
                g.addEdge(u, v, rand.nextInt(maxWeight) + 1);
                added++;
            }
        }
        return g;
    }

    // graph with a negative weight cycle for Bellman-Ford testing
    public static Graph withNegativeCycle(int V) {
        Graph g = new Graph(V, true);
        for (int i = 0; i < V - 1; i++)
            g.addEdge(i, i + 1, rand.nextInt(10) + 1);
        // create negative cycle: V-1 -> 2 -> 3 -> V-1 with negative total
        if (V >= 4) {
            g.addEdge(V - 1, 2, 1);
            g.addEdge(2, 3, -5);
            g.addEdge(3, V - 1, 1);  // cycle total: 1 - 5 + 1 = -3
        }
        return g;
    }
}
