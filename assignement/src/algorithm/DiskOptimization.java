package algorithm;
 
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;
 
public class DiskOptimization{
    Properties p = new Properties();
    DiskParameter dp = null;
   
    public static void main(String args[]){
        new DiskOptimization("diskq1.properties");
    }
   
    public DiskOptimization (String filename){
        try{
            p.load(new BufferedReader(new FileReader(filename)));
            
            dp = new DiskParameter(p);
            
        }catch (Exception e){
            e.printStackTrace();
        }
        generateAnalysis();
    }
   
    public void generateAnalysis(){
        generateFCFS();
        generateSSTF();
        generateSCAN();
        generateCSCAN();
        generateLOOK();
    }
   
    private void generateLOOK() {
		// TODO Auto-generated method stub
		int location[] = arrangeByLOOK(dp.getCurrent(), dp.getSequence());
		printSequence("LOOK", location);
	}

	private int[] arrangeByLOOK(int current, int[] sequence) {
		// TODO Auto-generated method stub
		int total = sequence.length; 
		int cutOffNumber = 0;
		int startNumber = current;
		ArrayList<Integer>sortedList = new ArrayList<Integer>();
		ArrayList<Integer> finalList = new ArrayList<Integer>();
		boolean stop = false;
		for(int i = 0; i < sequence.length; i++)
		{
			sortedList.add(sequence[i]);
		}
		
		sortedList.add(current);
		Collections.sort(sortedList);
	
		
		int smallestNumber = sortedList.get(0);
		int largestNumber = sortedList.get(sortedList.size()-1);
	
		if(current > 0)
		{
			for(int i = current; i > 0; i--)
			{
			
				for(int j = 0; j < sequence.length; j++)
				{
					if(sequence[j] == i)
					{
						finalList.add(sequence[j]);
						current = sequence[j];
					}
					
					
				}
				
				if(i == smallestNumber)
				{
					stop = true;
					
					break;
				}
			}
		}
		
		if(stop = true)
		{
			for(int i = startNumber+1; i < dp.getCylinders(); i++ )
			{	
				for(int j = 0; j < sequence.length; j++)
				{
					if(sequence[j] == i)
					{
						finalList.add(sequence[j]);
						current = sequence[j];
					}
				}
			}
		}
		
		int [] finalArray = new int[finalList.size()];
		for(int i = 0; i < finalList.size(); i++)
		{
			finalArray[i] = finalList.get(i); 
		}
		return finalArray; 
	}

	private ArrayList<Integer> sortList(int[] sequence) {
		// TODO Auto-generated method stub
		
		return null;
	}
	


	private void generateCSCAN() {
		// TODO Auto-generated method stub
		int location[] = arrangeByCSCAN(dp.getCurrent(), dp.getSequence());
		printSequence("CSCAN", location);
	}

	private int[] arrangeByCSCAN(int current, int[] sequence) {
		// TODO Auto-generated method stub
		ArrayList<Integer> finalList = new ArrayList<Integer>(); 
		int total = sequence.length; 
		int temp = 0; 
		int secondTemp = dp.current; 
		
		//Checks to make sure that the current is not at 0 and then check all the way back down to 0
		if(current > 0)
		{
				for(int i = current; i >0; i--)
				{
					for(int j = 0; j < total; j++)
					{
						if(i == sequence[j])
						{
							finalList.add(sequence[j]);
							current = sequence[j];
						}
			
				}
					
				current = 0; 
				temp = current;
			}
			
			
		}
		//When the head reaches 0, make it start again from 5000 and go back all the way until the initial value
		if(temp == 0)
		{
			current = 5000; 
			for(int i =  current; i > secondTemp; i--)
			{
				
				for(int j = 0; j < total; j++)
				{
					if(sequence[j] == i)
					{
					finalList.add(sequence[j]);
					current = sequence[j];
					}
				}
			}
		}
		for(int i = 0; i < finalList.size(); i++)
		{
			System.out.println(finalList.get(i));
		}
	
		
		
		
		int [] finalArray = new int[finalList.size()];
		for(int i = 0; i < finalList.size(); i++)
		{
			finalArray[i] = finalList.get(i); 
		}
		return finalArray; 
		
	}

	private void generateSCAN() {
		// TODO Auto-generated method stub
		int location[] = arrangeBySCAN(dp.getCurrent(),dp.getSequence());
		printSequence("SCAN",location);
	}

	private int[] arrangeBySCAN(int current, int[] sequence) {
		System.out.println("Current is " + current);
		int total = sequence.length;
		ArrayList<Integer> finalList = new ArrayList<Integer>(); //Create a Empty list 
		ArrayList<Integer> duplicateList = new ArrayList<Integer>();  //Create another empty list
		int count = 0; //initialize the counter
		int temp = 0; // a variable to store the temporary value 
		int secondTemp = 0;
		boolean stop = false; 
		// used to indicate whether the scan will go downwards towards 0 or upwards towards 5000. 
		//if its false, it is going downwards, if its true, its going upwards
		
		if(current > 0 && stop == false) 
		// a if condition to check if the current head is above 0. 
		{
			
			for(int i = current; i > 0; i--) //minus from current all the way down to 0 
			{
				
				for(int j = 0; j < total; j++)
				{	
					if(sequence[j] == i )
					{
						current = sequence[j];
						finalList.add(current); 
						//then check if any of the number in the sequence do match, if match add them to a list 
						
				
						
					}
				}
			}
			temp = current; //temporary store the current value into the temp variable
			current = 0; // Current = zero 
			finalList.add(current); //add the current to the finalList for it means that the head has scan all the way to cylinder 0 
			secondTemp = count; //tells how many current slots have been used
		
		}
		
		
		//When the header reaches 0, then stop will equals true and it will then scan back up all the way up to 5000 the maximum cylinder size
		//If there is any number in the sequence that matches any of the number, add it into the list. 
		if(current == 0) // happens once the head reaches 0 
		{
			stop = true; 
			duplicateList = finalList; //create another list 
		
			if(stop == true)
			{
				for(int i = temp+1; i < dp.getCylinders(); i++) 
				{
					for(int j = 0; j < total; j++)
					{
							if(sequence[j] == i)
							{
								System.out.println(sequence[j]);
								current = sequence[j];
								finalList.add(current);
							}
						}
					}
				stop = false; 
				current = 5000; 
				}
			}
		//Converts the arraylist into an array
		
		int [] finalArray = new int[finalList.size()];
		for(int i = 0; i < finalList.size(); i++)
		{
			finalArray[i] = finalList.get(i); 
		}
		return finalArray; 
		
	}

	public void generateFCFS(){
        int location[] = dp.getSequence();
       
        printSequence("FCFS",location);
    }
   
    public void printSequence(String name, int location[]){
        String sequence = "";
        String working1 = "";
        String working2 = "";
        int total = 0;
        sequence += dp.getCurrent();
        int previous = dp.getCurrent();
        for (int i=0; i<location.length; i++){
            int current = location[i];
            sequence += "," +current;
            int d = Math.abs(previous-current);
           
            working1 += "|" + previous + "-" +current +"|+";
            working2 += d + " + ";
            total += d;
            previous = current;
        }
       
        System.out.println(name+'\n'+"====");
        System.out.println("Order of Access: "+sequence);
       
        System.out.println("Total Distance = " + working1.substring(0,working1.length()-1));
        System.out.println("               = " + working2.substring(0,working2.length()-2));
        System.out.println("               = " + total + '\n');
       
    }
   
   
    public void generateSSTF(){
        int location[] = arrangeBySSTF(dp.getCurrent(),dp.getSequence());
        printSequence("SSTF", location);
    }
   
    private int[] arrangeBySSTF(int current, int sequence[]){
        int n = sequence.length;
        int sstf[] = new int[n];
        for (int i = 0; i<n; i++){
            sstf[i] = sequence[i];
        }
       
        int ii= -1;
        for (int i = 0; i<n; i++){
            int minimum = Integer.MAX_VALUE;
            ii=i;
            for (int j=i; j<n; j++){
                int distance = Math.abs(current - sstf[j]);
                if (distance < minimum){
                    ii=j;
                    minimum = distance;
                }
            }
            int tmp = sstf[i];
            sstf[i] = sstf[ii];
            sstf[ii] = tmp;
            current = sstf[i];
        }
        return sstf;
       
    }

	
}