
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

record DirectedGraph(Map<Integer, List<Integer>> adjacentList, int numberOfNodes) {

  public DirectedGraph(int numberOfNodes) {
    this(new HashMap<>(), numberOfNodes);
    for (int node = 0; node < numberOfNodes; node++) 
      this.adjacentList.put(node, new ArrayList<>());
  }

  public void addEdge(int node1, int node2) {
    this.adjacentList.get(node1).add(node2);
    // NOTE - Vice versa of above statement is error prone for directed graph.
  }

  public boolean hasCycle(int node, boolean[] visited, boolean[] recurrence) {
    visited[node] = true;
    recurrence[node] = true;
    List<Integer> neighbourNodeList = this.adjacentList.get(node);
    for (int neighbourNode : neighbourNodeList) 
      if ((!visited[neighbourNode] && hasCycle(neighbourNode, visited, recurrence)) || recurrence[neighbourNode])
        return true;
    recurrence[node] = false;
    return false;
  }

  public List<?> detectCycle() {
    boolean[] visited = new boolean[this.numberOfNodes];
    boolean[] recurrence = new boolean[this.numberOfNodes];
    for (int node = 0; node < this.numberOfNodes; node++) 
      if (hasCycle(node, visited, recurrence)) 
        return List.of(true, "Cycle detected!!! ---> The given graph is a cyclic graph.");	      
    return List.of(false, "Cycle not found!!! ---> The given graph is an acyclic graph.");	  
  }

}

public class CDDG {
 
  public static void main(String[] args) {

    System.out.println("\n--- Cycle Detection In Directed Graph ---\n");
    
    java.util.Scanner inputScanner = new java.util.Scanner(System.in);

    System.out.println("[NOTE - Entering any negative number at any point of time will close the input.]\n");

    System.out.print("Enter the number of nodes in the given graph: ");
    int numberOfNodes = inputScanner.nextInt();
    if (numberOfNodes < 0) return;
   
    DirectedGraph graph = new DirectedGraph(numberOfNodes);  // Creating a new directed graph ...

    System.out.println("Add new edges to the graph::");
      while(true) {
      System.out.print("Enter a new edge [node1, node2]: ");  
      int node1 = inputScanner.nextInt();
      if (node1 < 0) break;
      int node2 = inputScanner.nextInt();
      if (node2 < 0) break;
      graph.addEdge(node1, node2); // Adding a new edge to the graph ...
    }

    inputScanner.close();

    // Computing and printing the result ...
    System.out.println("\n" + graph.detectCycle() + "\n");
	  
  }

}
