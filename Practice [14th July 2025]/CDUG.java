
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

record UndirectedGraph(Map<Integer, List<Integer>> adjacentList, int numberOfNodes) {

  public UndirectedGraph(int numberOfNodes) {
    this(new HashMap<>(), numberOfNodes);
    for (int node = 0; node < numberOfNodes; node++)
      this.adjacentList.put(node, new ArrayList<>());	    
  }
 
  public void addEdge(int node1, int node2) {
    this.adjacentList.get(node1).add(node2);
    this.adjacentList.get(node2).add(node1);
  } 

  public boolean hasCycle(int node, int parentNode, boolean[] visited) {
    visited[node] = true;
    List<Integer> neighbourNodeList = this.adjacentList.get(node);
    for (int neighbourNode : neighbourNodeList) {
      if (visited[neighbourNode]) {
        if (neighbourNode != parentNode) return true;
      }	
      else {
        if (hasCycle(neighbourNode, node, visited)) return true;	   
      } 	
    }  
    return false;
  }

  public List<?> detectCycle() {
    for (int node = 0; node < this.numberOfNodes; node++) {
      boolean[] visited = new boolean[this.numberOfNodes];
      if (hasCycle(node, -1, visited))
        return List.of(true, "Cycle detected!!! ---> The given graph is a cyclic graph.");	      
    }	    
    return List.of(false, "Cycle not found!!! ---> The given graph is an acyclic graph."); 
  }

}

public class CDUG {
 
  public static void main(String[] args) {

    System.out.println("\n--- Cycle Detection In Undirected Graph ---\n");
    
    java.util.Scanner inputScanner = new java.util.Scanner(System.in);

    System.out.println("[NOTE - Entering any negative number at any point of time will close the input.]\n");

    System.out.print("Enter the number of nodes in the given graph: ");
    int numberOfNodes = inputScanner.nextInt();
    if (numberOfNodes < 0) return;
    UndirectedGraph graph = new UndirectedGraph(numberOfNodes);  // Creating a new undirected graph ...

    System.out.println("Add new edges to the graph::");
      while(true) {
      System.out.print("Enter a new edge [node1, node2]: ");  
      int node1 = inputScanner.nextInt();
      if (node1 < 0) break;
      int node2 = inputScanner.nextInt();
      if (node2 < 0) break;
      graph.addEdge(node1, node2);  // Adding a new edge to the graph ...
    }

    inputScanner.close();

    // Computing and printing result ...
    System.out.println("\n" + graph.detectCycle()  + "\n");
	  
  }

}
