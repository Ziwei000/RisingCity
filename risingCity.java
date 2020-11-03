
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList; 
import java.io.PrintStream; 

public class risingCity{
	public static void main(String[] args) throws Exception  {		
		MinHeap minHeap = new MinHeap();
		RBTree<Integer> tree=new RBTree<Integer>();
		 ArrayList arry = new ArrayList();
		int count=0;
		int global_time=0;
		
		String readname = args[0]; 
		File tp = new File(readname); 		 
		BufferedReader br = new BufferedReader(new FileReader(tp));	 
		String s = null;	 
		while( (s = br.readLine()) != null) {
			StringBuilder sb = new StringBuilder();
			count++;
			sb.append(s);
			arry.add(sb.toString());	
		}
		ArrayList a = new ArrayList();
		for(int i=0;i<count;i++){
			//Get each row of arry (dynamic array), assign it to ss, 
			//assign ss to a, as each row may be different in size so need to be processed to the same length
			//System.out.println(arry.get(i));
			String ss=(String) arry.get(i);
			String[] a0 = ss.split("\\: |\\(|\\)|\\,|\\)"); 
			for(int j=0;j<a0.length;j++) {
				a.add(a0[j]);
			}
			int leng0=a.size();
			//System.out.print(leng0);
			
			//when printBuilding(x), add a 'null' to the fourth variable
			if(leng0%4!=0) {
				a.add("null");
				leng0+=1;
			}
		}
		//System.out.println(a);
		int[] checkday=new int[a.size()/4];//save the day with order as int type in the input
		int[] bnum=new int[a.size()/4];//save the bnum as int type
		String[] ops=new String[a.size()/4];//save the operation in the input
		for(int vv=0;vv<a.size();vv++) {
			switch(vv%4) {
			case 0:{
				checkday[vv/4]=Integer.parseInt((String) a.get(vv));
				break;
			}
			case 1:{
				ops[vv/4]=(String)a.get(vv);
				break;
			}
			case 2:{
				bnum[vv/4]=Integer.parseInt((String) a.get(vv));
				break;
			}	
			default:
				break;
			}
		}      

		int tmp=0;//go through the operation in input.txt
		Item triplet1=new Item(0,0,0);
		int fiveday=0;//work on a building for continuously five days
		boolean construct=false;
		//Item triplet2=new Item(0,0,0);
		int bnum_=0;
		int etime_=0;
		int ttime_=0;
		
		PrintStream ps = new PrintStream("output_file.txt");
		System.setOut(ps);//Assign the created printout stream to the system.
		//the system will output to ps next time.
		
		//under construction, 
		//including everyday work routine and someday there's a order to follow
		for(;global_time<1000000000;global_time++) {
			
			//if there exists building in heap to construct			
			if(minHeap.count!=0||construct) {			
				if(fiveday==0) {
					//pick a new building to construct
					construct=true;
					triplet1=minHeap.getMin();	
					bnum_=triplet1.bnum;
					etime_=triplet1.etime+1;
					tree.update_time(bnum_, etime_);
					ttime_=triplet1.ttime;
					fiveday+=1;
				}else {
					//the building for 5days' work hasn't finished, 
					//continue from last day's work
					while(fiveday<5) {
						if(etime_<ttime_) {
							etime_+=1;
							tree.update_time(bnum_, etime_);
							fiveday+=1;
							break;
						}else {
							//this building need to be delete both from RBT and minheap
							//which means this building no need to insert back to minheap anymore
							tree.remove(bnum_);
							System.out.println("("+bnum_+","+global_time+")");
							fiveday=0;
							construct=false;
							break;
						}	
					}
					
					//after 5days, check if the building has finished
					if(fiveday==5 && etime_<ttime_) {
						//not finished,insert building back 
						triplet1=new Item(bnum_,etime_,ttime_);
						minHeap.insert(triplet1);
						fiveday=0;
						construct=false;
					}else if(etime_==ttime_) {
						//no need to insert back to minheap,also extract this building from RBT, 
						tree.remove(bnum_);
						// bw.write("("+bnum_+","+global_time+")"+"\r\n");
						System.out.println("("+bnum_+","+global_time+")");
						fiveday=0;
						construct=false;
					}
				}					
			}
			
			//after work routine, to check if there is any order need to be done
			if(tmp < checkday.length && global_time==checkday[tmp]) {
				switch(ops[tmp]) {
				case "Insert":{
					int ttime=Integer.parseInt((String) a.get(4*tmp+3));
					tree.insert(bnum[tmp],0,ttime);
					triplet1=new Item(bnum[tmp],0,ttime);
					minHeap.insert(triplet1);
					//tree.print();
					tmp++;//prepare for next order
					break;
				}
				case "PrintBuilding":{
					if(a.get(4*tmp+3)=="null") {
						//PrintBuilding(bnum)
						tree.printNode(bnum[tmp]);
					}else {//PrintBuilding(bnum1,bnum2)
						//System.out.println("------Print(x,y)");
						int bnum1=Integer.parseInt((String) a.get(4*tmp+2));//bnum[i]
						int bnum2=Integer.parseInt((String) a.get(4*tmp+3));
						if(bnum1<=bnum2) {
							tree.printNodeBetween(bnum1, bnum2);
						}else {
							System.out.println("(0,0,0)");
						}
					}
					tmp++;
					break;
				}
				default:{
					System.out.println("error in read input order");
					break;
				}							
				}
			}
		}
		br.close();			
	}
	
}

 
