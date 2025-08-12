
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

record Graph (Map<Integer, List<Integer>> adjacentList, int numberOfNodes) {

  public Graph (int numberOfNodes) {
    this(new HashMap<>(), numberOfNodes);
    for (int node = 0; node < numberOfNodes; node++) 
      this.adjacentList.put(node, new ArrayList<>());
  }

  public void addEdge (int node1, int node2) {
    this.adjacentList.get(node1).add(node2);
    this.adjacentList.get(node2).add(node1);
  }

}

record DSU (int[] parent, int[] rank, int numberOfNodes) {

  public DSU (int numberOfNodes) {
    this(new int[numberOfNodes], new int[numberOfNodes], numberOfNodes);
    for (int node = 0; node < numberOfNodes; node++) // Every node is the parent of itself.
	 this.parent[node] = node;    
  }

  public int findUltimateParent (int node) {
    if (this.parent[node] != node) {
      this.parent[node] = this.findUltimateParent(this.parent[node]); // Path compression ...
    }
    return this.parent[node];
  }

  public void makeUnion (int node1, int node2) {
    int rootX = this.findUltimateParent(node1);
    int rootY = this.findUltimateParent(node2);
   
    if (rootX == rootY) return;

    // NOTE - Parent nodes always have higher rank thank child nodes.

    if (this.rank[rootX] > this.rank[rootY]) {
      this.parent[rootY] = rootX;
    } else if (this.rank[rootX] < this.rank[rootY]) {
      this.parent[rootX] = rootY;
    } else {
      this.rank[rootX]++;	    
      this.parent[rootY] = rootX;
    }
  }

}

public class DsuAlgo {

  public static void main (String[] args) {
  
    System.out.println("\n--- Disjoint Set Union Algorithm ---\n");
    System.out.println("[ NOTE - Entering any negative integer at any point of time while taking input will close the input. ]");

    Scanner keyboardInput = new Scanner(System.in); // Opening the keyboard input ...

    System.out.print("\nEnter the number of nodes: ");
    int numberOfNodes = keyboardInput.nextInt();
    if (numberOfNodes < 0) return;

    System.out.println("\nEnter the edge(s) of the graph in the format \"node_1 node_2\" respectively ->");
    List<List<Integer>> edgeList = new ArrayList<>();
    while (true) {
      int node1 = keyboardInput.nextInt(); 
      if (node1 < 0) break;
      int node2 = keyboardInput.nextInt(); 
      if (node2 < 0) break;
      edgeList.add(List.of(node1, node2));
    }

    keyboardInput.close(); // Closing the keyboard input ...

    Graph graph = new Graph(numberOfNodes); // Creating a new graph object ...

    for (List<Integer> edge : edgeList) graph.addEdge(edge.get(0), edge.get(1)); // Adding edge(s) to the graph object ...

    DSU dsu = new DSU(numberOfNodes); // Creating a new Disjoint Set Union object ...

    // Making union of each node with it's neighbour node(s) ...
    for (int node = 0; node < numberOfNodes; node++) {
      for (int neighbourNode : graph.adjacentList().get(node)) {
        dsu.makeUnion(node, neighbourNode);
      }
    }

    // Printing the ultimate parent of each node ...
    System.out.println("\nUltimate parent of each node ->");
    for (int node = 0; node < numberOfNodes; node++) {
      System.out.println("Ultimate parent of Node-'" + node + "': Node-'" + dsu.parent()[node] + "'");
    }
    System.out.println("\n");
  
  }

}
