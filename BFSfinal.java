import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;

public class BFSfinal {
    
    // graph, heuristic, parent arrays store values starting at index 1
    Scanner sc = new Scanner(System.in);
    int nodes,start,goal,closedCount=0;
    int graph[][] = new int[100][100];
    int heuristic[] = new int[100];    
    int closed[] = new int[10];    
    int parent[] = new int[10];
    TreeMap<Integer, Integer>  openList= new TreeMap<>();        
    
    void input(){
        System.out.println("Enter the number of elements of the graph");
        nodes = sc.nextInt();
        System.out.println("Enter the graph adjacency values");
        for(int i=1;i<=nodes;i++){
            for(int j=1;j<=nodes;j++){
                graph[i][j] = sc.nextInt();
            }
        }
        System.out.println("Enter the input graph elements heuristic values");
        for(int i=1;i<=nodes;i++){
            heuristic[i] = sc.nextInt();
        }
        System.out.println("Enter the start state and the goal state");
        start = sc.nextInt();
        goal = sc.nextInt();
        // Declaring the value of start node parernt to -999 for printing path purpose
        parent[start] = -999;
    }
    
    boolean closedListCheck(int key){
        for(int i=0;i<closedCount;i++){
            if(closed[i]==key){
                return true;
            }
        }
        return false;
    }
    
    boolean openListCheck(int key){     
        for(Map.Entry<Integer,Integer> entry:openList.entrySet()){
            if(entry.getKey()==key){
                return true;
            }
        }
        return false;
    } 
    
    int returnTheSmallestValueKey(){
        Entry<Integer,Integer> minValue = openList.firstEntry();        
        for(Map.Entry<Integer,Integer> entry: openList.entrySet()){
            if(minValue.getValue() > entry.getValue()){
                minValue=entry ;
            }
        }               
        return minValue.getKey();
    }
    
    void processBFS(){    
        openList.put(start, heuristic[start]);
        int currentNodeKey = start;
        while(!openList.isEmpty()){                                   
            if(currentNodeKey == goal){
                // Storing the goal node in the closed list to give the path from the start to the goal.
                closed[closedCount] = currentNodeKey; 
                closedCount++;                        
                break;
            }else{
                // Creating the succesors of the current object
                for(int j=1;j<=nodes;j++){
                    if(graph[currentNodeKey][j]==1){
                        if(!closedListCheck(j)){ 
                            if(!openListCheck(j)){                               
                                openList.put(j,heuristic[j]);
                                parent[j] = currentNodeKey;
                            }                            
                        }                        
                    }                                        
                }
            }
            closed[closedCount] = currentNodeKey;           
            closedCount++;
            openList.remove(currentNodeKey);
            currentNodeKey = returnTheSmallestValueKey();             
        }      
    }
    
    void printClosedList(){        
        System.out.println("The nodes selected from BFS are as follows: ");
        for(int i=0;i<closedCount;i++){
            System.out.print(closed[i]+" ");
        }
        // Printing the actual path from the start to the goal state       
        System.out.println("\nThe actual path from the start to the goal state is as follows:");
        // Storing the goal nodes id in currentId as the goal was the last state to be added to the closed list
        int currentId = closed[closedCount-1];  
        while(true){
            System.out.print(currentId+"<= ");
            // The following line will give us the parent of the currentId i.e. the parent of current node
            currentId = parent[currentId];
            if(currentId == -999){break;}
        }
    }
    
    public static void main(String args[]){
        BFSfinal bfs = new BFSfinal();
        bfs.input();
        bfs.processBFS();
        bfs.printClosedList();
    }    
}