import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Astar {
    
    Scanner sc = new Scanner(System.in);
    int nodes,start,goal,closedCount=0;
    int graph[][] = new int[100][100];
    int heuristic[] = new int[100];    
    int closed[] = new int[10];    
    int parent[] = new int[10];
    int distancearray[] = new int[10];
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
        Map.Entry<Integer,Integer> minValue = openList.firstEntry();        
        for(Map.Entry<Integer,Integer> entry: openList.entrySet()){
            if(minValue.getValue() > entry.getValue()){
                minValue=entry ;
            }
        }               
        return minValue.getKey();
    }
    
    void processBFS(){
        int distance=0;
        distancearray[start] =distance;
        openList.put(start, heuristic[start]);
        int currentNodeKey = start;
        while(!openList.isEmpty()){                                   
            if(currentNodeKey == goal){                
                closed[closedCount] = currentNodeKey; 
                distancearray[currentNodeKey] = distancearray[currentNodeKey];
                closedCount++;                        
                break;
            }else{             
                for(int j=1;j<=nodes;j++){
                    if(graph[currentNodeKey][j]!=-1){
                        if(!closedListCheck(j)){ 
                            if(!openListCheck(j)){                               
                                openList.put(j,heuristic[j]+graph[currentNodeKey][j]+distancearray[currentNodeKey]); 
                                distancearray[j] = graph[currentNodeKey][j]+distancearray[currentNodeKey];
                                parent[j] = currentNodeKey;                                
                            }else{
                                /* Comparing the value of the node from the openlist and updating the parent if the current
                                cost of reaching the node is less than what is previously present */
                                // Gives the value of the node with key = j
                                int value = openList.get(j);
                                if(value > heuristic[j]+graph[currentNodeKey][j]+distancearray[currentNodeKey]){
                                    parent[j] = currentNodeKey;
                                    openList.replace(j, value, heuristic[j]+graph[currentNodeKey][j]+distancearray[currentNodeKey]);
                                    distancearray[j] = graph[currentNodeKey][j]+distancearray[currentNodeKey];
                                }
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
        System.out.println("\nThe actual path from the start to the goal state is as follows:");    
        int currentId = closed[closedCount-1];  
        while(true){
            System.out.print(currentId+"<= ");       
            currentId = parent[currentId];
            if(currentId == -999){break;}
        }
        System.out.println("The distances are as follows: ");
        for(int i=1;i<8;i++){
            System.out.print(distancearray[i]+" ");
        }              
    }
    
    public static void main(String args[]){
        Astar astar = new Astar();
        astar.input();
        astar.processBFS();
        astar.printClosedList();
    } 
}
