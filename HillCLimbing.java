import java.util.Scanner;

public class HillCLimbing {
    
    Scanner sc = new Scanner(System.in);
    int start, goal, nodes, closedcount = 0, breakflag=0;
    int graph[][] = new int[10][10];   
    int heuristic[] = new int[10];
    int closed[] = new int[10];
    
    void input(){
        System.out.println("Enter the number of nodes: ");
        nodes = sc.nextInt();
        System.out.println("Enter the adjacency matrix: ");
        for(int i=1;i<=nodes;i++){
            for(int j=1;j<=nodes;j++){
                graph[i][j] = sc.nextInt();
            }
        }
        System.out.println("Enter the start node");
        start = sc.nextInt();
        System.out.println("Enter the goal node");
        goal = sc.nextInt();
        System.out.println("Enter the heuristic values of the graph");
        for(int j=1;j<=nodes;j++){
            heuristic[j] = sc.nextInt();
        }
    }
    
    boolean closedListCheck(int key){
        for(int i=0;i<closedcount;i++){
            if(closed[i]==key){return true;}
        }
        return false;
    }
    
    void hillClimb(){
        boolean flag = true;
        int currentnodekey = start;
        while(flag){      
            if(currentnodekey == goal){
                // breakflag = 1 to show that that algorithm is complete in printPath()
                breakflag = 1;
                closed[closedcount] = currentnodekey;                
                break;
            }else{                
                for(int i=1;i<=nodes;i++){
                    if(graph[currentnodekey][i]==1){                  
                        if(!closedListCheck(i)){                               
                            if(heuristic[i] > heuristic[currentnodekey]){
                                closed[closedcount] = currentnodekey;
                                System.out.print(currentnodekey+" => ");
                                closedcount++;                        
                                currentnodekey = i;                              
                                breakflag =1;
                                break;                                  
                            }                        
                        }
                    }
                }
                if(breakflag==0){break;}
                breakflag = 0;
            }            
        }       
    }
    
    void printPath(){
        if(breakflag==0){
            System.out.println("The hill climbing algorithm is not complete");
        }else{
            System.out.println("The path from the start node to the goal node is as follows: ");
            for(int i=0;i<=closedcount;i++){
                System.out.print(closed[i]+" =>");
            }     
        } 
    }
    
     public static void main(String args[]){
        HillCLimbing hc = new HillCLimbing();
        hc.input();
        hc.hillClimb();
        hc.printPath();
    }    
}
