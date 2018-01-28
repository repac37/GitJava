
import java.util.Iterator;

import linkedlists.MyLinkedListTwo;

public class TestPlatform {

	private void run() {
		//MyArrayList<Integer> mA = new MyArrayList<>();
		MyLinkedListTwo<Float> bA = new MyLinkedListTwo<>();

		float x = 3.0f;
		float y = 4.2f;
		
		bA.add(x);
		bA.add(y);
		
		for(int i = 0; i < 5; i++){
			bA.add((float)i);
		}
		
		bA.addToFront(50f);
//		for(float e : bA){
//			System.out.println(e);
//		}
		
		bA.add(0, 540f);
	
		bA.add(2f);
		bA.add(2f);
		bA.add(2f);
		bA.add(2f);
		bA.add(2f);
		System.out.println(bA.remove(2f));
		
		for(float e : bA){
			System.out.println(e);
		}
		System.out.println("print size: "+ bA.size());
	
		bA.remove(2f);
		bA.remove(2f);
		bA.remove(2f);
		bA.remove(2f);
		bA.remove(2f);
		bA.remove(2f);
		bA.remove(2f);
		
		System.out.println(bA.remove(2f));
		
			
//		Iterator<Float> ir = bA.iterator();
//		while(ir.hasNext()){
//			if(2f == ir.next()){
//				ir.remove();
//			}
//		}
		
		for(float e : bA){
			System.out.println(e);
		}
		System.out.println("print size: "+ bA.size());
		

	}
		 

	
	public static void main(String[] args){
		new TestPlatform().run();
	}

}

