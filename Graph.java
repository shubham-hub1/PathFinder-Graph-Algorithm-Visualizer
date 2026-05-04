import java.util.*;

public class Graph {
    private final int V;
    private final boolean directed;
    private final HashMap<Integer, List<int[]>> adj; // int[] = {neighbor, weight}

    public Graph(int vertices, boolean directed) {
        this.V        = vertices;
        this.directed = directed;
        this.adj      = new HashMap<>();
        for (int i = 0; i < V; i++)
            adj.put(i, new ArrayList<>());
    }

    public void addEdge(int u, int v, int weight) {
        adj.get(u).add(new int[]{v, weight});
        if (!directed)
            adj.get(v).add(new int[]{u, weight});
    }

    public void addEdge(int u, int v) {
        addEdge(u, v, 1);
    }

    public List<int[]> getNeighbors(int u) {
        return adj.getOrDefault(u, new ArrayList<>());
    }

    public int getV() { return V; }
    public boolean isDirected() { return directed; }

    public int edgeCount() {
        int total = 0;
        for (List<int[]> neighbors : adj.values())
            total += neighbors.size();
        return directed ? total : total / 2;
    }

    public void printGraph() {
        System.out.println("\n--- Adjacency List ---");
        for (int i = 0; i < V; i++) {
            System.out.print("  " + i + " -> ");
            for (int[] edge : adj.get(i))
                System.out.print("[" + edge[0] + ", w=" + edge[1] + "] ");
            System.out.println();
        }
    }
}
