
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

record Graph(Map<Integer, List<Integer>> adjacentList, int numberOfNodes) {
  
  public Graph(int numberOfNodes) {
    this(new HashMap<>(), numberOfNodes);
    for (int node = 0; node < numberOfNodes; node++)
      this.adjacentList.put(node, new ArrayList<>());	    
  }

  public void addEdge(int node1, int node2) {
    this.adjacentList.get(node1).add(node2);
    this.adjacentList.get(node2).add(node1);
  } 

}

record DSU(int[] parent, int[] rank) {

  public DSU(int numberOfNodes) {
    this(new int[numberOfNodes], new int[numberOfNodes]);
    for (int node = 0; node < numberOfNodes; node++)
      this.parent[node] = node; // Note ---> Every node is the parent of itself.	    
  }

  public int findUltimateParent(int node) {
    if (parent[node] != node) {
      return findUltimateParent(parent[node]);
    }
    return node;
  }

  public void makeUnion(int node1, int node2) {
     int rootX = this.findUltimateParent(node1);
     int rootY = this.findUltimateParent(node2);
     if (rootX != rootY) {
       // Note ---> Parents have higher rank than children. 	     
       if (rank[rootX] > rank[rootY]) {
         parent[rootY] = rootX;
       } else if (rank[rootX] < rank[rootY]) {
         parent[rootX] = rootY;
       } else {
         rank[rootX]++;
	 parent[rootY] = rootX;
       }
     }  
  }

}

public class DSU_Algo {

  public static void main(String[] atgs) {
  
    System.out.println("\n--- Disjoint Set Union Algorithm ---\n");
    java.util.Scanner inputScanner = new java.util.Scanner(System.in);
    System.out.println("[NOTE - Entering any negative number at any point of time will close the input.]\n");
    System.out.print("Enter the number of nodes in your graph: ");
    int numberOfNodes = inputScanner.nextInt(); 
    Graph graph = new Graph(numberOfNodes); // Creating a new graph ...
    if(numberOfNodes < 0) return;
    System.out.println("Add edges to the graph::");
    while(true) {
      System.out.print("Enter a new edge for the graph: ");
       int node1 = inputScanner.nextInt();
       if(node1 < 0) break;
       int node2 = inputScanner.nextInt();
       if(node2 < 0) break;
       graph.addEdge(node1, node2); // Adding new edge to the graph ...
    }
    inputScanner.close();
    DSU dsu = new DSU(numberOfNodes); // Creating a new DSU object ...
    // Making union for each node and with it's neighbour nodes ...
    for (int node = 0; node < numberOfNodes; node++) {
      List<Integer> neighbourNodeList = graph.adjacentList().get(node);
      for (int neighbourNode : neighbourNodeList) 
        dsu.makeUnion(node, neighbourNode); // Making union ...   
    }
    // Printing the output
    System.out.println("Output::");
    for (int node = 0; node < numberOfNodes; node++)
	System.out.println("Ultimate parent of Node-'" + node + "' is Node-'" + dsu.parent()[node] + "'.");    
    System.out.println("\n");
    
  }

}
