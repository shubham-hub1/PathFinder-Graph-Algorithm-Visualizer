import java.util.*;

public class FloydWarshall {

    static final int INF = 99999999;

    public static Result compute(Graph g) {
        int V      = g.getV();
        int[][] dist = new int[V][V];
        int[][] next = new int[V][V]; // for path reconstruction

        // init
        for (int i = 0; i < V; i++) {
            Arrays.fill(dist[i], INF);
            Arrays.fill(next[i], -1);
            dist[i][i] = 0;
        }

        for (int u = 0; u < V; u++) {
            for (int[] edge : g.getNeighbors(u)) {
                int v = edge[0], w = edge[1];
                if (w < dist[u][v]) {
                    dist[u][v] = w;
                    next[u][v] = v;
                }
            }
        }

        long start = System.nanoTime();

        // Floyd-Warshall: try each vertex as intermediate
        for (int k = 0; k < V; k++) {
            for (int i = 0; i < V; i++) {
                for (int j = 0; j < V; j++) {
                    if (dist[i][k] != INF && dist[k][j] != INF
                            && dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                        next[i][j] = next[i][k];
                    }
                }
            }
        }

        // detect negative cycle: dist[i][i] < 0
        boolean hasNegCycle = false;
        for (int i = 0; i < V; i++)
            if (dist[i][i] < 0) { hasNegCycle = true; break; }

        long timeMs = (System.nanoTime() - start) / 1_000_000;
        return new Result(dist, next, hasNegCycle, timeMs);
    }

    public static void print(Result r, int V) {
        System.out.println("\n=== Floyd-Warshall All-Pairs Shortest Path ===");
        if (r.hasNegativeCycle)
            System.out.println("  ⚠ Negative weight cycle detected!");
        else
            System.out.println("  ✓ No negative cycle");

        System.out.println("\n  Distance Matrix (showing first 10 nodes):");
        int show = Math.min(V, 10);

        System.out.print("       ");
        for (int j = 0; j < show; j++) System.out.printf("%6d", j);
        System.out.println();
        System.out.println("       " + "-".repeat(show * 6));

        for (int i = 0; i < show; i++) {
            System.out.printf("  %3d | ", i);
            for (int j = 0; j < show; j++) {
                if (r.dist[i][j] >= INF) System.out.printf("%6s", "∞");
                else System.out.printf("%6d", r.dist[i][j]);
            }
            System.out.println();
        }
        System.out.println("  Execution time: " + r.timeMs + " ms");
    }

    public static String getPath(Result r, int src, int dest) {
        if (r.next[src][dest] == -1) return "no path";
        StringBuilder sb = new StringBuilder();
        sb.append(src);
        int cur = src;
        while (cur != dest) {
            cur = r.next[cur][dest];
            sb.append(" -> ").append(cur);
        }
        return sb.toString();
    }

    public static class Result {
        public int[][] dist, next;
        public boolean hasNegativeCycle;
        public long timeMs;
        public Result(int[][] dist, int[][] next, boolean hasNegCycle, long timeMs) {
            this.dist             = dist;
            this.next             = next;
            this.hasNegativeCycle = hasNegCycle;
            this.timeMs           = timeMs;
        }
    }
}
