
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
    for (int node = 0; node < numberOfNodes; node++)
      this.parent[node] = node; // Every node is the parent of itself.	    
  }

  public int findUltimateParent (int node) {
    if (this.parent[node] != node)
      this.parent[node] = this.findUltimateParent(this.parent[node]);
    return this.parent[node];
  }

  public void makeUnion (int node1, int node2) {
    int rootX = this.findUltimateParent(node1);
    int rootY = this.findUltimateParent(node2);

    if (rootX != rootY) {
      // NOTE - Parent node(s) always have higher rank than child node(s).
      if (this.rank[rootX] > this.rank[rootY]) {
        this.parent[rootY] = rootX;
      } else if (this.rank[rootX] < this.rank[rootY]) {
        this.parent[rootX] = rootY; 
      } else {
         this.parent[rootY] = rootX;
	 this.rank[rootX]++;
      }
    }
  }

}

public class DsuAlgo {

  public static void main (String[] args) {
  
    System.out.println("\n--- Disjoint Set Union Algorithm ---\n");
    System.out.println("[ NOTE - Entering any negative number at any point of time while taking input will close the input. ]\n");
    System.out.println("Eneter the number of nodes first and then enter the edge(s) in the following format 'node_1 node_2' respectively ->");

    Scanner keyboardInput = new Scanner(System.in);
    
    int numberOfNodes = keyboardInput.nextInt();
    if (numberOfNodes < 0) return;

    List<List<Integer>> edgeList = new ArrayList<>();
    while (true) {
      int node1 = keyboardInput.nextInt();
      if (node1 < 0) break;
      int node2 = keyboardInput.nextInt();
      if (node2 < 0) break;
      edgeList.add(List.of(node1, node2));
    }

    keyboardInput.close();

    Graph graph = new Graph(numberOfNodes);  // Creating a new graph object ...
 
    // Adding edge(s) to the graph ...
    for (List<Integer> edge : edgeList) 
      graph.addEdge(edge.get(0), edge.get(1));

    DSU dsu = new DSU(numberOfNodes);  // Creating a new DSU object ...

    // Making union of each node with it's neighbour / child node(s) ... 
    for (int node = 0; node < numberOfNodes; node++) {
      List<Integer> neighbourNodeList = graph.adjacentList().get(node);
      for (int neighbourNode : neighbourNodeList) 
        dsu.makeUnion(node, neighbourNode);  
    }
    
    // Printing the ultimate parent of each node ...	    
    System.out.println("\nUltimate parent of each node ->");
    for (int node = 0; node < numberOfNodes; node++) 
      System.out.println("Node: '" + node + "' \t\t -----> \t\t Ultimate Parent Node: '" + dsu.parent()[node] + "'");
    System.out.println("\n");
    
  }

}
