import java.util.*;

public class BFS {

    public static Result compute(Graph g, int src) {
        int V = g.getV();
        int[] dist   = new int[V];
        int[] parent = new int[V];
        boolean[] visited = new boolean[V];

        Arrays.fill(dist, -1);
        Arrays.fill(parent, -1);

        Queue<Integer> queue = new LinkedList<>();
        visited[src] = true;
        dist[src]    = 0;
        queue.offer(src);

        List<Integer> order = new ArrayList<>();

        long start = System.nanoTime();

        while (!queue.isEmpty()) {
            int u = queue.poll();
            order.add(u);

            for (int[] edge : g.getNeighbors(u)) {
                int v = edge[0];
                if (!visited[v]) {
                    visited[v] = true;
                    dist[v]    = dist[u] + 1;
                    parent[v]  = u;
                    queue.offer(v);
                }
            }
        }

        long timeMs = (System.nanoTime() - start) / 1_000_000;
        return new Result(order, dist, parent, timeMs);
    }

    public static void print(Result r, int src, int V) {
        System.out.println("\n=== BFS Traversal (source: " + src + ") ===");
        System.out.print("  Order: ");
        for (int i = 0; i < r.order.size(); i++) {
            System.out.print(r.order.get(i));
            if (i < r.order.size() - 1) System.out.print(" -> ");
        }
        System.out.println();
        System.out.println("  Nodes visited   : " + r.order.size());
        System.out.println("  Execution time  : " + r.timeMs + " ms");

        System.out.println("\n  Hop distances from source " + src + ":");
        System.out.printf("  %-8s %s%n", "Node", "Hops");
        System.out.println("  " + "-".repeat(20));
        for (int i = 0; i < V; i++) {
            String d = r.dist[i] == -1 ? "unreachable" : String.valueOf(r.dist[i]);
            System.out.printf("  %-8d %s%n", i, d);
        }
    }

    public static class Result {
        public List<Integer> order;
        public int[] dist, parent;
        public long timeMs;
        public Result(List<Integer> order, int[] dist, int[] parent, long timeMs) {
            this.order  = order;
            this.dist   = dist;
            this.parent = parent;
            this.timeMs = timeMs;
        }
    }
}
