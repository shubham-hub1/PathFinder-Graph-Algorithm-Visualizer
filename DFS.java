import java.util.*;

public class DFS {

    private static boolean[] visited;
    private static int[] parent;
    private static List<Integer> order;

    public static Result compute(Graph g, int src) {
        int V    = g.getV();
        visited  = new boolean[V];
        parent   = new int[V];
        order    = new ArrayList<>();
        Arrays.fill(parent, -1);

        long start = System.nanoTime();
        dfs(g, src);
        long timeMs = (System.nanoTime() - start) / 1_000_000;

        return new Result(new ArrayList<>(order), parent.clone(), timeMs);
    }

    private static void dfs(Graph g, int u) {
        visited[u] = true;
        order.add(u);
        for (int[] edge : g.getNeighbors(u)) {
            int v = edge[0];
            if (!visited[v]) {
                parent[v] = u;
                dfs(g, v);
            }
        }
    }

    public static void print(Result r, int src) {
        System.out.println("\n=== DFS Traversal (source: " + src + ") ===");
        System.out.print("  Order: ");
        for (int i = 0; i < r.order.size(); i++) {
            System.out.print(r.order.get(i));
            if (i < r.order.size() - 1) System.out.print(" -> ");
        }
        System.out.println();
        System.out.println("  Nodes visited  : " + r.order.size());
        System.out.println("  Execution time : " + r.timeMs + " ms");
    }

    public static class Result {
        public List<Integer> order;
        public int[] parent;
        public long timeMs;
        public Result(List<Integer> order, int[] parent, long timeMs) {
            this.order  = order;
            this.parent = parent;
            this.timeMs = timeMs;
        }
    }
}
