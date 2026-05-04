import java.util.*;

public class BellmanFord {

    static final int INF = Integer.MAX_VALUE;

    public static Result compute(Graph g, int src) {
        int V = g.getV();
        int[] dist   = new int[V];
        int[] parent = new int[V];
        Arrays.fill(dist, INF);
        Arrays.fill(parent, -1);
        dist[src] = 0;

        // collect all edges
        List<int[]> edges = new ArrayList<>(); // {u, v, w}
        for (int u = 0; u < V; u++)
            for (int[] edge : g.getNeighbors(u))
                edges.add(new int[]{u, edge[0], edge[1]});

        long start = System.nanoTime();

        // relax V-1 times
        for (int i = 0; i < V - 1; i++) {
            for (int[] e : edges) {
                int u = e[0], v = e[1], w = e[2];
                if (dist[u] != INF && dist[u] + w < dist[v]) {
                    dist[v]   = dist[u] + w;
                    parent[v] = u;
                }
            }
        }

        // check for negative weight cycles (V-th relaxation)
        boolean hasNegCycle = false;
        List<Integer> negCycleNodes = new ArrayList<>();
        for (int[] e : edges) {
            int u = e[0], v = e[1], w = e[2];
            if (dist[u] != INF && dist[u] + w < dist[v]) {
                hasNegCycle = true;
                negCycleNodes.add(v);
            }
        }

        long timeMs = (System.nanoTime() - start) / 1_000_000;
        return new Result(dist, parent, hasNegCycle, negCycleNodes, timeMs);
    }

    public static void print(Result r, int src, int V) {
        System.out.println("\n=== Bellman-Ford Shortest Path (source: " + src + ") ===");
        if (r.hasNegativeCycle) {
            System.out.println("  ⚠ Negative weight cycle DETECTED");
            System.out.println("  Affected nodes: " + r.negCycleNodes);
        } else {
            System.out.println("  ✓ No negative weight cycle detected");
        }
        System.out.printf("  %-8s %-12s %s%n", "Node", "Distance", "Path");
        System.out.println("  " + "-".repeat(45));
        for (int i = 0; i < V; i++) {
            String dist = r.dist[i] == INF ? "∞" : String.valueOf(r.dist[i]);
            System.out.printf("  %-8d %-12s %s%n", i, dist, Dijkstra.getPath(r.parent, src, i));
        }
        System.out.println("  Execution time: " + r.timeMs + " ms");
    }

    public static class Result {
        public int[] dist, parent;
        public boolean hasNegativeCycle;
        public List<Integer> negCycleNodes;
        public long timeMs;
        public Result(int[] dist, int[] parent, boolean hasNegCycle,
                      List<Integer> negCycleNodes, long timeMs) {
            this.dist            = dist;
            this.parent          = parent;
            this.hasNegativeCycle = hasNegCycle;
            this.negCycleNodes   = negCycleNodes;
            this.timeMs          = timeMs;
        }
    }
}
