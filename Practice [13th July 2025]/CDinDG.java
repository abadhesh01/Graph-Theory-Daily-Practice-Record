
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

record DirectedGraph(Map<Integer, List<Integer>> adjacentList, int numberOfNodes) {

  public DirectedGraph(int numberOfNodes) {
    this(new HashMap<>(), numberOfNodes);
    for (int node = 0; node < numberOfNodes; node++)
	 this.adjacentList.put(node, new ArrayList<>());    
  }

  public void addEdge(int node1, int node2) {
    this.adjacentList.get(node1).add(node2);
    //this.adjacentList.get(node2).add(node1);  // Error Prone -> Directed graphs have only one direction.
  }

  public boolean hasCycle(int node, boolean[] visited, boolean[] recurrence) {
    visited[node] = true;
    recurrence[node] = true;

    List<Integer> neighbourNodeList = this.adjacentList.get(node);

    for (int neighbourNode : neighbourNodeList) 
         if ((!visited[neighbourNode] && hasCycle(neighbourNode, visited, recurrence)) 
              || recurrence[neighbourNode]) return true;	 
   
    recurrence[node] = false; // Error Prone -> Remember to unmark the recurrence.
    return false;     
  }

  public List<?> detectCycle() {
    boolean[] visited = new boolean[this.numberOfNodes]; // It keeps the track of visited nodes.
    boolean[] recurrence = new boolean[this.numberOfNodes]; // It keeps the track of recurrence of a node.
    for (int node = 0; node < this.numberOfNodes; node++) {
         if (hasCycle(node, visited, recurrence)) return List.of(true, "Cycle Detected. This graph is a cyclic graph.");
    }	  
    return List.of(false, "Cycle not found! This graph is an acyclic graph.");		 
  }

}


public class CDinDG {

  public static void main(String[] args) {
    
    System.out.println("\n --- Cycle Detection In Directed Graph --- \n");
   
    Scanner inputScanner = new Scanner(System.in);
   
    System.out.print("Enter the number of nodes: ");
    int numberOfNodes = inputScanner.nextInt();
    System.out.println("Add the edges (Entering any negative number at any point of time will close the input.)::");
    DirectedGraph graph = new DirectedGraph(numberOfNodes); // Creating a new undirected graph .....
    while (true) {
      System.out.print("Enter a new edge [node1, node2]: ");	    
      int node1 = inputScanner.nextInt();
      if (node1 < 0) break;
      int node2 = inputScanner.nextInt();
      if (node2 < 0) break;
      graph.addEdge(node1, node2); // Adding a new edge to the graph .....
    }

    inputScanner.close(); 

    System.out.println("\nOutput -----> " + graph.detectCycle() + "\n");   
  
  }	  
}
