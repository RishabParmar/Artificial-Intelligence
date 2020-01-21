import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Ep {
    
    Scanner sc = new Scanner(System.in);
    int processid=0, iofzero=0,jofzero=0, previousstep=-1, closedcount=0, first=0, finalparentposition=0;
    int puzzle[][] = new int[3][3];
    int goal_state[][] = new int[3][3];
    int configuration[][] = new int[30][30];    
    int dummy[][] = new int[3][3];
    int moves[] = new int[4];
    int closed[][] = new int[30][9];
    TreeMap<Integer, Integer> openlist = new TreeMap<>();
    
    void input(){
        System.out.println("Enter the initial state of the 8 puzzle problem: ");
        for(int i=0,k=0;i<3;i++){
            for(int j=0;j<3;j++,k++){
                puzzle[i][j] = sc.nextInt();
                configuration[processid][k] = puzzle[i][j];
            }
        }
        System.out.println("Enter the goal state of the 8 puzzle problem: ");
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                goal_state[i][j] = sc.nextInt();
            }
        }        
    }
    
    void returnPositionOfZero(){
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(puzzle[i][j]==0){
                    iofzero = i;
                    jofzero = j;
                    break;
                }
            }
        }
    }
    
    int returnTheSmallestValueKey(){
        Map.Entry<Integer, Integer> minvalue = openlist.firstEntry();
        for(Map.Entry<Integer, Integer> entry: openlist.entrySet()){
            if(minvalue.getValue() > entry.getValue()){
                minvalue = entry;
            }
        }        
        return minvalue.getKey();        
    }
    
    int[] getPossibleMoves(){
        // L R U D: 0 1 2 3        
        int possiblemoves[] = new int[4];
        if(jofzero-1>=0){possiblemoves[0]=1;}
        if(jofzero+1<=2){possiblemoves[1]=1;}
        if(iofzero-1>=0){possiblemoves[2]=1;}
        if(iofzero+1<=2){possiblemoves[3]=1;}
        return possiblemoves;
    }
    
    void copyElementsInDummy(){
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                dummy[i][j] = puzzle[i][j];
            }
        }
    }
    
    int getHeuristicValue(){
        int matches=0;
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(goal_state[i][j]!=dummy[i][j]){matches++;}
            }
        }       
        return matches;
    }
    
    boolean closedListCheck(){
        int flag=0;
        if(closedcount==0){return false;}
        for(int i=0;i<closedcount;i++){
            flag = 0;
            for(int j=0,k=0;j<3;j++){
                for(int m=0;m<3;m++,k++){
                    if(closed[i][k]==dummy[j][m]){flag++;}
                }
            }
            if(flag>=9){return true;}           
        }
        return false;
    }
    
    boolean openListCheck(){
        int flag=0;
        if(first==0){return false;}
        for(int i=0;i<29;i++){
            flag = 0;
            for(int j=0,k=0;j<3;j++){
                for(int m=0;m<3;m++,k++){
                    if(configuration[i][k]==dummy[j][m]){flag++;}
                }
            }
            if(flag>=9){return true;}        
        }
        return false;
    }
    
    void copyElementsInConfiguration(int pid){
        for(int i=0,k=0;i<3;i++){
            for(int j=0;j<3;j++,k++){
                configuration[pid][k] = dummy[i][j];
            }
        }        
    }
    
    void copyElementsInPuzzle(int id){
        for(int i=0,k=0;i<3;i++){
            for(int j=0;j<3;j++,k++){
                puzzle[i][j] = configuration[id][k];
            }
        }     
    }
    
    void copyElementsInClosed(){
        for(int i=0,k=0;i<3;i++){
            for(int j=0;j<3;j++,k++){
                closed[closedcount][k] = puzzle[i][j];
            }
        }
        closedcount++;
    }
    
    int finalHeuristicValue(){
       int matches = 0;
       for(int i=0;i<=2;i++){
           for(int j=0;j<=2;j++){              
               if(puzzle[i][j]!=goal_state[i][j]){
                   matches++;
               }
           }     
        }
       return matches;
    }
    
    void processEp(){
        int currentkey = processid;
        openlist.put(processid, 0);
        configuration[currentkey][9] = 0;
        configuration[currentkey][10] = 0;
        configuration[currentkey][11] = 1;
        while(!openlist.isEmpty()){
            returnPositionOfZero();
            if(finalHeuristicValue()==0){
                finalparentposition = currentkey;
                break;
            }else{                
                Arrays.fill(moves, 0);
                moves = getPossibleMoves();
                for(int i=0;i<4;i++){
                    copyElementsInDummy();
                    if(moves[i]==1){                     
                        if(i==0){
                            if(previousstep!=1){    
                                int temp = dummy[iofzero][jofzero];
                                dummy[iofzero][jofzero] = dummy[iofzero][jofzero-1];
                                dummy[iofzero][jofzero-1] = temp;  
                            }else{continue;}                                     
                        }else if(i==1){
                            if(previousstep!=0){    
                                int temp = dummy[iofzero][jofzero];
                                dummy[iofzero][jofzero] = dummy[iofzero][jofzero+1];
                                dummy[iofzero][jofzero+1] = temp;  
                            }else{continue;}  
                        }else if(i==2){
                            if(previousstep!=3){    
                                 int temp = dummy[iofzero][jofzero];
                                dummy[iofzero][jofzero] = dummy[iofzero-1][jofzero];
                                dummy[iofzero-1][jofzero] = temp;  
                            }else{continue;}  
                        }else if(i==3){
                            if(previousstep!=2){    
                               int temp = dummy[iofzero][jofzero];
                                dummy[iofzero][jofzero] = dummy[iofzero+1][jofzero];
                                dummy[iofzero+1][jofzero] = temp;
                            }else{continue;}  
                        }                                 
                    }else{continue;}        
                    if(!closedListCheck()){                        
                        if(!openListCheck()){                                
                            processid++;                            
                            int value = getHeuristicValue()+configuration[currentkey][11];
                            System.out.println("Process: "+processid+" Value :"+value);
                            openlist.put(processid, value);                           
                            copyElementsInConfiguration(processid);
                            configuration[processid][9] = i;
                            configuration[processid][10] = currentkey;
                            configuration[processid][11] = configuration[currentkey][11]+1;
                        }
                    }                    
                }                            
            }           
            copyElementsInClosed();
            openlist.remove(currentkey);                
            currentkey = returnTheSmallestValueKey();              
            copyElementsInPuzzle(currentkey);
            previousstep = configuration[currentkey][9];
            first = 1;               
        }
    }
    
    void printResult(){
        System.out.println("The path of the eight puzzle problem is as follows: ");
        System.out.print(finalparentposition+"<= ");
        int pid = configuration[finalparentposition][10];
        while(true){
            System.out.print(pid+"<= ");
            pid = configuration[pid][10];
            if(pid==0){System.out.print(0+"<=");break;}
        }
        System.out.println("The steps of the eight puzzle problem is as follows: ");
        System.out.print(configuration[finalparentposition][9]+"<= ");
        int stepid = configuration[finalparentposition][10];
        while(true){
            System.out.print(configuration[stepid][9]+"<= ");
            stepid = configuration[stepid][10];
            if(stepid==0){break;}
        }     
    }
    
    public static void main(String args[]){
        Ep ep = new Ep();
        ep.input();
        ep.processEp();
        ep.printResult();
    }    
}
