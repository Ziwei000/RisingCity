

public class Item implements Comparable<Item>{
	int bnum;
	int etime;
	int ttime;
	
	public Item(int bnum, int etime, int ttime) {
		this.bnum = bnum;
		this.etime = etime;
		this.ttime = ttime;
	}
	
	public int compareTo(Item b) {
		if (this.etime == b.etime)
		//in minheap, when 2 building have same execute time, 
		//then building with smaller bnum will have priority
			return this.bnum - b.bnum;
		return this.etime - b.etime;
	}


}
