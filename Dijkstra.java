import java.util.*;

public class Dijkstra {

    static final int INF = Integer.MAX_VALUE;

    public static Result compute(Graph g, int src) {
        int V = g.getV();
        int[] dist   = new int[V];
        int[] parent = new int[V];
        Arrays.fill(dist, INF);
        Arrays.fill(parent, -1);

        dist[src] = 0;

        // min-heap: {distance, vertex}
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        pq.offer(new int[]{0, src});

        boolean[] visited = new boolean[V];

        long start = System.nanoTime();

        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int d = curr[0], u = curr[1];

            if (visited[u]) continue;
            visited[u] = true;

            for (int[] edge : g.getNeighbors(u)) {
                int v = edge[0], w = edge[1];
                if (dist[u] != INF && dist[u] + w < dist[v]) {
                    dist[v]   = dist[u] + w;
                    parent[v] = u;
                    pq.offer(new int[]{dist[v], v});
                }
            }
        }

        long timeMs = (System.nanoTime() - start) / 1_000_000;
        return new Result(dist, parent, timeMs);
    }

    public static void print(Result r, int src, int V) {
        System.out.println("\n=== Dijkstra's Shortest Path (source: " + src + ") ===");
        System.out.printf("  %-8s %-12s %s%n", "Node", "Distance", "Path");
        System.out.println("  " + "-".repeat(45));
        for (int i = 0; i < V; i++) {
            String dist = r.dist[i] == INF ? "∞" : String.valueOf(r.dist[i]);
            System.out.printf("  %-8d %-12s %s%n", i, dist, getPath(r.parent, src, i));
        }
        System.out.println("  Execution time: " + r.timeMs + " ms");
    }

    static String getPath(int[] parent, int src, int dest) {
        if (parent[dest] == -1 && dest != src) return "unreachable";
        List<Integer> path = new ArrayList<>();
        Set<Integer> seen  = new HashSet<>();
        for (int v = dest; v != -1; v = parent[v]) {
            if (seen.contains(v)) return "cycle";
            seen.add(v);
            path.add(v);
        }
        Collections.reverse(path);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < path.size(); i++) {
            sb.append(path.get(i));
            if (i < path.size() - 1) sb.append(" -> ");
        }
        return sb.toString();
    }

    public static class Result {
        public int[] dist, parent;
        public long timeMs;
        public Result(int[] dist, int[] parent, long timeMs) {
            this.dist   = dist;
            this.parent = parent;
            this.timeMs = timeMs;
        }
    }
}
