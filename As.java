import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class As {
    
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
            if(key == closed[i]){
                return true;
            }
        }
        return false;
    }
    
    boolean openListCheck(int key){        
        for(Map.Entry<Integer, Integer> entry: openList.entrySet()){
            if(entry.getKey()==key){
                return true;
            }
        }
        return false;
    }
    
    int returnTheSmallestValueKey(){
        Map.Entry<Integer, Integer> minvalue = openList.firstEntry();
        for(Map.Entry<Integer, Integer> entry: openList.entrySet()){
            if(minvalue.getValue() > entry.getValue()){
                minvalue = entry;
            }
        }
        return minvalue.getKey();
    }
    
    void process(){
        openList.put(start, heuristic[start]);
        int currentkey = start;
        distancearray[currentkey] = 0;
        while(!openList.isEmpty()){
            if(currentkey == goal){
                closed[closedCount] = currentkey;
                closedCount++;
                break;
            }else{
                for(int j=1;j<=nodes;j++){
                    if(graph[currentkey][j]!=0){
                        if(!closedListCheck(j)){
                            if(!openListCheck(j)){
                                int value = heuristic[j]+distancearray[currentkey]+graph[currentkey][j];                               
                                openList.put(j, value);
                                parent[j] = currentkey;
                                distancearray[j] = distancearray[currentkey]+graph[currentkey][j];                                
                            }else{
                                int value = heuristic[j]+distancearray[currentkey]+graph[currentkey][j];
                                int hvalue = openList.get(j);
                                if(hvalue > value){
                                    openList.remove(j);
                                    openList.put(j, value);
                                    parent[j] = currentkey;
                                    distancearray[j] = distancearray[currentkey]+graph[currentkey][j];
                                }
                            }
                        }
                    }
                }
            }
            closed[closedCount] = currentkey;
            closedCount++;
            openList.remove(currentkey);
            currentkey = returnTheSmallestValueKey();
        }
    }
    
    void printPath(){
        System.out.println("Parent of 3 is: "+parent[3]);
        System.out.println("The nodes selected are as follows: ");
        for(int i=0;i<closedCount;i++){
            System.out.print(closed[i]+" ");
        }
        System.out.println("The path is as follows: ");
        int currentid = closed[closedCount-1];
        while(true){
            System.out.print(currentid+" <=");
            currentid = parent[currentid];
            if(currentid ==-999){break;}
        }
    }
    
    public static void main(String args[]){
        As a = new As();
        a.input();
        a.process();
        a.printPath();
    }    
}
