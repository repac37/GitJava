import java.util.Iterator;

import MyArray.DynamicArrayTwo;
import MyArray.MyArrayList;
public class TestPlatform {

	private void run() {
		//MyArrayList<Integer> mA = new MyArrayList<>();
		DynamicArrayTwo<Float> bA = new DynamicArrayTwo<>();

		
		float x = 3.0f;
		float y = 4.2f;
		
		bA.add(x);
		bA.add(y);
		
		
		

		for(int i = 0; i < 5; i++){
			bA.add((float)i);
		}
		
		
		
	

		bA.add(2, 540f);
		bA.add(0, 70f);
		bA.add(0, 170f);
		bA.add(2f);
		bA.add(2f);
		bA.add(2f);
		bA.add(2f);
		bA.add(2f);
		
		for(float e : bA){
			System.out.print(e+" ,");
		}
		
		System.out.println();
		
		Iterator<Float> ir = bA.iterator();
		while(ir.hasNext()){
			
			if(2f == ir.next()){
				ir.remove();
			}
		}
	
		
	
		
		
		System.out.println();
		for(float e : bA){
			System.out.print(e+" ,");
		}
		
		
	
	
		 
	}
	
	public static void main(String[] args){
		new TestPlatform().run();
	}

}

