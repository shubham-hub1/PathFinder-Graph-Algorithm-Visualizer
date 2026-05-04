public class Main {

    public static void main(String[] args) {
        printBanner();

        // ── Demo Graph (small, visible) ──────────────────────────────
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("  DEMO  —  8 nodes, weighted graph");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        Graph demo = new Graph(8, false);
        demo.addEdge(0, 1, 4);  demo.addEdge(0, 2, 1);
        demo.addEdge(2, 1, 2);  demo.addEdge(1, 3, 1);
        demo.addEdge(2, 3, 5);  demo.addEdge(3, 4, 3);
        demo.addEdge(4, 5, 2);  demo.addEdge(5, 6, 1);
        demo.addEdge(6, 7, 4);  demo.addEdge(4, 7, 6);
        demo.addEdge(1, 5, 8);  demo.addEdge(2, 6, 7);

        demo.printGraph();

        // Dijkstra
        Dijkstra.Result dRes = Dijkstra.compute(demo, 0);
        Dijkstra.print(dRes, 0, demo.getV());

        // BFS
        BFS.Result bfsRes = BFS.compute(demo, 0);
        BFS.print(bfsRes, 0, demo.getV());

        // DFS
        DFS.Result dfsRes = DFS.compute(demo, 0);
        DFS.print(dfsRes, 0);

        // Bellman-Ford (no negative cycle)
        BellmanFord.Result bfRes = BellmanFord.compute(demo, 0);
        BellmanFord.print(bfRes, 0, demo.getV());

        // Floyd-Warshall
        FloydWarshall.Result fwRes = FloydWarshall.compute(demo);
        FloydWarshall.print(fwRes, demo.getV());

        System.out.println("\n  Floyd-Warshall path 0 -> 7 : " + FloydWarshall.getPath(fwRes, 0, 7));

        // ── Bellman-Ford Negative Cycle Test ─────────────────────────
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("  NEGATIVE CYCLE TEST  —  directed graph");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        Graph negGraph = GraphGenerator.withNegativeCycle(6);
        BellmanFord.Result negRes = BellmanFord.compute(negGraph, 0);
        BellmanFord.print(negRes, 0, negGraph.getV());

        // ── Large Graph Benchmark ─────────────────────────────────────
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("  BENCHMARK  —  1,000 nodes  |  5,000 edges");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        Graph large = GraphGenerator.random(1000, 5000, 100);
        System.out.println("  Graph  : " + large.getV() + " nodes, " + large.edgeCount() + " edges");

        Dijkstra.Result  ld  = Dijkstra.compute(large, 0);
        BFS.Result       lb  = BFS.compute(large, 0);
        DFS.Result       ldf = DFS.compute(large, 0);
        BellmanFord.Result lbf = BellmanFord.compute(large, 0);

        System.out.println("\n  Algorithm       Time (ms)   Notes");
        System.out.println("  " + "-".repeat(50));
        System.out.printf("  %-16s %-12d %s%n", "Dijkstra",     ld.timeMs,  "O((V+E) log V)");
        System.out.printf("  %-16s %-12d %s%n", "BFS",          lb.timeMs,  "O(V+E)");
        System.out.printf("  %-16s %-12d %s%n", "DFS",          ldf.timeMs, "O(V+E)");
        System.out.printf("  %-16s %-12d %s%n", "Bellman-Ford", lbf.timeMs, "O(V*E)");

        System.out.println("\n  Dijkstra shortest dist 0->999 : " +
            (ld.dist[999] == Integer.MAX_VALUE ? "unreachable" : ld.dist[999]));
        System.out.println("  BFS hops       0->999         : " +
            (lb.dist[999] == -1 ? "unreachable" : lb.dist[999]));

        System.out.println("\n  Note: Floyd-Warshall skipped on large graph (O(V³) = 10⁹ ops)");

        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
    }

    static void printBanner() {
        System.out.println();
        System.out.println("  ██████╗  █████╗ ████████╗██╗  ██╗███████╗██╗███╗   ██╗██████╗ ███████╗██████╗ ");
        System.out.println("  ██╔══██╗██╔══██╗╚══██╔══╝██║  ██║██╔════╝██║████╗  ██║██╔══██╗██╔════╝██╔══██╗");
        System.out.println("  ██████╔╝███████║   ██║   ███████║█████╗  ██║██╔██╗ ██║██║  ██║█████╗  ██████╔╝");
        System.out.println("  ██╔═══╝ ██╔══██║   ██║   ██╔══██║██╔══╝  ██║██║╚██╗██║██║  ██║██╔══╝  ██╔══██╗");
        System.out.println("  ██║     ██║  ██║   ██║   ██║  ██║██║     ██║██║ ╚████║██████╔╝███████╗██║  ██║");
        System.out.println("  ╚═╝     ╚═╝  ╚═╝   ╚═╝   ╚═╝  ╚═╝╚═╝     ╚═╝╚═╝  ╚═══╝╚═════╝ ╚══════╝╚═╝  ╚═╝");
        System.out.println("\n        Graph Algorithm Visualizer  —  Dijkstra · BFS · DFS · Bellman-Ford · Floyd-Warshall");
        System.out.println();
    }
}
