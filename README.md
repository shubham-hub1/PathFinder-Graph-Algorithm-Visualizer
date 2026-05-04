# PathFinder 🗺️
### Graph Algorithm Visualizer

A Java console application implementing **5 core graph algorithms** on weighted and unweighted graphs of up to 1,000 nodes and 5,000+ edges — with benchmarking, path reconstruction, and negative cycle detection.

---

## Algorithms Implemented

| Algorithm | Type | Time Complexity | Use Case |
|---|---|---|---|
| Dijkstra | Single-source shortest path | O((V+E) log V) | Weighted graphs, no negative edges |
| BFS | Traversal + shortest hops | O(V+E) | Unweighted graphs |
| DFS | Traversal | O(V+E) | Connectivity, cycle detection |
| Bellman-Ford | Single-source shortest path | O(V·E) | Negative edge weights, cycle detection |
| Floyd-Warshall | All-pairs shortest path | O(V³) | Small dense graphs, all distances |

---

## Benchmark Results (1,000 nodes · 5,000 edges)

| Algorithm | Time |
|---|---|
| Dijkstra | < 8 ms |
| BFS | < 2 ms |
| DFS | < 2 ms |
| Bellman-Ford | ~90 ms |

---

## Features

- **Path reconstruction** — prints full route from source to any destination
- **Negative cycle detection** — Bellman-Ford flags affected nodes on the V-th relaxation
- **All-pairs distance matrix** — Floyd-Warshall shows shortest distances between every pair
- **Random graph generator** — seeded generator creates reproducible test graphs
- **Benchmarking** — execution time printed for every algorithm run

---

## Project Structure

```
PathFinder/
├── src/
│   ├── Main.java            # entry point, demo, benchmark
│   ├── Graph.java           # adjacency list using HashMap
│   ├── Dijkstra.java        # Dijkstra + path reconstruction
│   ├── BFS.java             # BFS + hop distances
│   ├── DFS.java             # recursive DFS
│   ├── BellmanFord.java     # Bellman-Ford + negative cycle detection
│   ├── FloydWarshall.java   # Floyd-Warshall + distance matrix
│   └── GraphGenerator.java  # random graph + negative cycle test graph
└── README.md
```

---

## How to Run

**Compile:**
```bash
cd src
javac *.java
```

**Run:**
```bash
java Main
```

**Run with more memory (large graphs):**
```bash
java -Xmx512m Main
```

---

## Sample Output

```
=== Dijkstra's Shortest Path (source: 0) ===
  Node     Distance     Path
  ---------------------------------------------
  0        0            0
  1        3            0 -> 2 -> 1
  2        1            0 -> 2
  3        4            0 -> 2 -> 1 -> 3
  ...
  Execution time: 5 ms

=== Bellman-Ford (negative cycle test) ===
  WARNING: Negative weight cycle DETECTED
  Affected nodes: [3]
```

---

## Tech Stack

- **Language:** Java 17+
- **Data Structures:** `HashMap`, `PriorityQueue`, `LinkedList`, `ArrayList` (Collections Framework)
- **Algorithm paradigms:** Greedy (Dijkstra), Dynamic Programming (Floyd-Warshall), Relaxation (Bellman-Ford)

---

## Key Concepts Demonstrated

- Graph representation via adjacency list (`HashMap<Integer, List<int[]>>`)
- Min-heap priority queue for Dijkstra
- Parent array for path reconstruction
- Negative weight cycle detection via V-th relaxation
- All-pairs shortest path via DP
- Time complexity analysis and empirical benchmarking

---

## About

Built as part of a Java Full Stack learning path, focusing on Data Structures, Algorithms, and the Java Collections Framework.
