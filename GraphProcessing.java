import java.util.*;

class GraphTraversal {
    private Map<Integer, List<Integer>> nodeConnections;

    public GraphTraversal() {
        nodeConnections = new HashMap<>();
    }

    public void addLink(int source, int target) {
        nodeConnections.computeIfAbsent(source, k -> new ArrayList<>()).add(target);
    }

    public void displayGraph() {
        for (Map.Entry<Integer, List<Integer>> entry : nodeConnections.entrySet()) {
            System.out.print(entry.getKey() + ": ");
            for (int neighbor : entry.getValue()) {
                System.out.print(neighbor + " ");
            }
            System.out.println();
        }
    }

    public void performDFS(int start) {
        System.out.println("Depth-First Search (DFS):");
        Set<Integer> visitedNodes = new HashSet<>();
        DFSHelper(start, visitedNodes);
        System.out.println();
    }

    private void DFSHelper(int current, Set<Integer> visited) {
        System.out.print(current + " ");
        visited.add(current);

        List<Integer> neighbors = nodeConnections.getOrDefault(current, new ArrayList<>());
        for (int neighbor : neighbors) {
            if (!visited.contains(neighbor)) {
                DFSHelper(neighbor, visited);
            }
        }
    }

    public void performBFS(int start) {
        System.out.println("\nBreadth-First Search (BFS):");
        Set<Integer> visitedNodes = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();

        visitedNodes.add(start);
        queue.add(start);

        while (!queue.isEmpty()) {
            int current = queue.poll();
            System.out.print(current + " ");

            List<Integer> neighbors = nodeConnections.getOrDefault(current, new ArrayList<>());
            for (int neighbor : neighbors) {
                if (!visitedNodes.contains(neighbor)) {
                    visitedNodes.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }
        System.out.println();
    }

    public void identifyCycles() {
        System.out.println("\nCycles in the graph:");
        Set<Integer> visitedNodes = new HashSet<>();
        for (int node : nodeConnections.keySet()) {
            if (!visitedNodes.contains(node)) {
                List<Integer> cyclePath = new ArrayList<>();
                if (detectCycle(node, -1, visitedNodes, cyclePath)) {
                    printCycle(cyclePath);
                }
            }
        }
    }

    private boolean detectCycle(int current, int parent, Set<Integer> visited, List<Integer> cyclePath) {
        visited.add(current);
        cyclePath.add(current);

        List<Integer> neighbors = nodeConnections.getOrDefault(current, new ArrayList<>());
        for (int neighbor : neighbors) {
            if (!visited.contains(neighbor)) {
                if (detectCycle(neighbor, current, visited, cyclePath)) {
                    return true;
                }
            } else if (neighbor != parent) {
                cyclePath.add(neighbor);
                printCycle(cyclePath);
                return true;
            }
        }

        cyclePath.remove(cyclePath.size() - 1);
        return false;
    }

    private void printCycle(List<Integer> cyclePath) {
        for (int node : cyclePath) {
            System.out.print(node + " ");
        }
        System.out.println();
    }

    public boolean isBipartite() {
        Set<Integer> visitedNodes = new HashSet<>();
        Map<Integer, Integer> colorMap = new HashMap<>();

        for (int node : nodeConnections.keySet()) {
            if (!visitedNodes.contains(node) && !isBipartiteBFS(node, visitedNodes, colorMap)) {
                return false;
            }
        }

        return true;
    }

    private boolean isBipartiteBFS(int start, Set<Integer> visited, Map<Integer, Integer> colorMap) {
        Queue<Integer> queue = new LinkedList<>();

        visited.add(start);
        colorMap.put(start, 0);
        queue.add(start);

        while (!queue.isEmpty()) {
            int current = queue.poll();

            List<Integer> neighbors = nodeConnections.getOrDefault(current, new ArrayList<>());
            for (int neighbor : neighbors) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    colorMap.put(neighbor, 1 - colorMap.get(current)); // Alternate colors

                    queue.add(neighbor);
                } else if (colorMap.get(neighbor).equals(colorMap.get(current))) {
                    // Conflicting colors, not bipartite
                    return false;
                }
            }
        }

        return true;
    }
}

public class GraphProcessing {
    public static void main(String[] args) {
        GraphTraversal graphTraversal = new GraphTraversal();

        // Add links to the graph
        graphTraversal.addLink(1, 3);
        graphTraversal.addLink(1, 4);
        graphTraversal.addLink(2, 1);
        graphTraversal.addLink(2, 3);
        graphTraversal.addLink(3, 4);
        graphTraversal.addLink(4, 1);
        graphTraversal.addLink(4, 2);

        // Display the adjacency list representation of the graph
        System.out.println("Graph Representation:");
        graphTraversal.displayGraph();

        // Perform DFS and BFS
        graphTraversal.performDFS(1);
        graphTraversal.performBFS(1);

        // Identify cycles in the graph
        graphTraversal.identifyCycles();

        // Check if the graph is bipartite
        boolean isBipartite = graphTraversal.isBipartite();
        System.out.println("\nIs the graph bipartite? " + isBipartite);
    }
}
