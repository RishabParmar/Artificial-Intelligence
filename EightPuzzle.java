import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class EightPuzzle {
    
    Scanner input = new Scanner(System.in);
    int processid=0, iofzero=0, jofzero=0, closedCount =0, finalparentposition,first=0;
    int moves[] = new int[4];
    int closed[][] = new int[20][10];
    int puzzle[][] = new int[30][10];
    int finalpuzzle[][] = new int[3][3];
    int configuration[][] = new int[30][13];
    int parent[] = new int[10];
    int dummy[][] = new int[3][3];
    TreeMap<Integer, Integer>  openList= new TreeMap<>();        
    
    void input(){
        System.out.println("Enter the initial state of the eight puzzle problem");
        for(int i=0,k=0;i<=2;i++){
           for(int j=0;j<=2;j++,k++){
               puzzle[i][j] = input.nextInt();
               configuration[0][k] = puzzle[i][j];
           }
        }
        System.out.println("Enter the final state of the eight puzzle problem");
        for(int i=0;i<=2;i++){
           for(int j=0;j<=2;j++){
               finalpuzzle[i][j] = input.nextInt();
           }
        }
    }
    
    void returnPositionOfZero(){
        for(int i=0;i<=2;i++){
           for(int j=0;j<=2;j++){
               if(puzzle[i][j]==0){
                   iofzero = i;
                   jofzero = j;
               }
           }
        }     
    }
    
    void copyCurrentNodeElementsInDummy(){     
        for(int i=0;i<=2;i++){
           for(int j=0;j<=2;j++){
               dummy[i][j] = puzzle[i][j];
           }
        }
    }    
    
    int[] getPossibleMoves(){
        // L R U D : 0 1 2 3
        int possiblemoves[] = new int[4];        
        if(jofzero-1>=0){possiblemoves[0] = 1;}
        if(jofzero+1<=2){possiblemoves[1] = 1;}
        if(iofzero-1>=0){possiblemoves[2] = 1;}
        if(iofzero+1<=2){possiblemoves[3] = 1;}
        return possiblemoves;
    }
    
    int finalHeuristicValue(){
       int matches = 0;
       for(int i=0;i<=2;i++){
           for(int j=0;j<=2;j++){              
               if(puzzle[i][j]!=finalpuzzle[i][j]){
                   matches++;
               }
           }     
        }
       return matches;
    }
    
    int heuristicValue(){
       int matches = 0;
       for(int i=0;i<=2;i++){
           for(int j=0;j<=2;j++){              
               if(dummy[i][j]!=finalpuzzle[i][j]){
                   matches++;
               }
           }     
        }
       return matches;
    }
   
    boolean closedListCheck(){        
        int flag=0;
        if(closedCount==0){return false;}
        for(int i=0;i<closedCount;i++){   
            flag=0;
            for(int j=0,n=0;j<=2;j++){
                for(int k=0;k<=2;k++,n++){
                    if(closed[i][n]==dummy[j][k]){
                        flag++;
                    }
                }
            }
            if(flag==9){
                return true;
            }
        }        
        return false;
    }
    
    boolean openListCheck(){ 
        int flag=0; 
        if(first==0){
            return false;
        }
        for(int i=0;i<=29;i++){
            flag=0;            
            for(int j=0,n=0;j<=2;j++){
                for(int k=0;k<=2;k++,n++){
                    if(configuration[i][n]==dummy[j][k]){
                        flag++;
                    }
                }
            }
            if(flag==9){
                return true;
            }
        }                    
        return false;
    }
    
    void copyElementsInConfiguration(int id){      
        for(int k=0,i=0;i<=2;i++){
           for(int j=0;j<=2;j++,k++){
               configuration[id][k] = dummy[i][j];
           }
        }
    }
    
    void copyElementsInClosed(){
        for(int i=0,k=0;i<=2;i++){
           for(int j=0;j<=2;j++,k++){
               closed[closedCount][k] = puzzle[i][j];
           }
        }
        closedCount++;    
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
    
    void selectTheCurrentPuzzle(int pid){
        for(int k=0,i=0;i<=2;i++){
           for(int j=0;j<=2;j++,k++){
               puzzle[i][j] = configuration[pid][k];
           }
        }
    }
    
    void eightPuzzleProcessing(){   
        int distance=1,previousstep=-1;                
        int currentNodeKey = processid;      
        // Storing the step at the 10th position, the parent at the 11th and the distance at the 12th (g)
        configuration[currentNodeKey][10] = -1;
        configuration[currentNodeKey][11] = -1;
        configuration[currentNodeKey][12] = distance;
        openList.put(currentNodeKey, 0);
        while(!openList.isEmpty()){
            // Will first find the current position of the zero
            returnPositionOfZero();             
            if(finalHeuristicValue()==0){ 
                System.out.println(currentNodeKey);
                finalparentposition = currentNodeKey;
                break;
            }else{                 
                // Cleaning the values from the moves list
                Arrays.fill(moves,0);
                // Getting the list of all posible moves.
                moves = getPossibleMoves();
                for(int i=0;i<=3;i++){
                    int temp;
                    copyCurrentNodeElementsInDummy();                     
                    if(moves[i]==1){
                        if(i==0){           
                            if(previousstep!=1){
                                temp = dummy[iofzero][jofzero];
                                dummy[iofzero][jofzero] = dummy[iofzero][jofzero-1];
                                dummy[iofzero][jofzero-1] = temp;
                            }else{continue;}                            
                        }else if(i==1){  
                            if(previousstep!=0){
                                temp = dummy[iofzero][jofzero];
                                dummy[iofzero][jofzero] = dummy[iofzero][jofzero+1];
                                dummy[iofzero][jofzero+1] = temp;
                            }else{continue;}
                        }else if(i==2){                            
                            if(previousstep!=3){
                                temp = dummy[iofzero][jofzero];
                                dummy[iofzero][jofzero] = dummy[iofzero-1][jofzero];
                                dummy[iofzero-1][jofzero] = temp;
                            }else{continue;}
                        }else if(i==3){ 
                            if(previousstep!=2){
                                temp = dummy[iofzero][jofzero];
                                dummy[iofzero][jofzero] = dummy[iofzero+1][jofzero];
                                dummy[iofzero+1][jofzero] = temp;
                            }else{continue;}
                        }
                    }else{
                        continue;
                    }                                   
                    // Checking whether the current sequence is repeated in the open or closed list                   
                    if(!closedListCheck()){                    
                        if(!openListCheck()){ 
                            processid++;
                            int value = heuristicValue()+configuration[currentNodeKey][12];
                            openList.put(processid, value);   
                            System.out.println("Process: "+processid+" Value: "+value);
                            copyElementsInConfiguration(processid);
                            // Stores the direction or the step of the 8 puzzle problem in the 10th position of the configuration
                            configuration[processid][10] = i;
                            configuration[processid][11] = currentNodeKey; 
                            configuration[processid][12] = configuration[currentNodeKey][12]+1;
                        }
                    }
                }            
            }
            // Copying the elements of the current state in the corresponding closed list
            // Storing the parent of the node at the last position        
            copyElementsInClosed();            
            openList.remove(currentNodeKey);
            currentNodeKey = returnTheSmallestValueKey();  
            System.out.println("Key selected: "+currentNodeKey);
            selectTheCurrentPuzzle(currentNodeKey);            
            // Cuz the value of g is 1 in 8 puzzle problem     
            previousstep = configuration[currentNodeKey][10];                    
            first=1;
        }              
    }
    
    void printPath(){
        System.out.println("The path travelled is as follows: ");
        System.out.print(finalparentposition+"<= ");
        int pid = configuration[finalparentposition][11];      
        while(true){ 
            System.out.print(pid+"<= ");
            pid = configuration[pid][11];                         
            if(pid==-1){break;}
        }
        System.out.println("The list of actions is as follows: ");
        System.out.print(configuration[finalparentposition][10]+"<= ");
        pid = configuration[finalparentposition][11];      
        while(true){ 
            System.out.print(configuration[pid][10]+"<= ");
            pid = configuration[pid][11];                         
            if(pid==-1){break;}
        }
    }
    
    public static void main(String args[]){
        EightPuzzle ep = new EightPuzzle();
        ep.input();
        ep.eightPuzzleProcessing();
        ep.printPath();
    }    
}
