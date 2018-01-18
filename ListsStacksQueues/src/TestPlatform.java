import MyArray.OurArray;
import MyArray.MyArrayList;

public class TestPlatform {

	private void run() {
		//MyArrayList<Integer> mA = new MyArrayList<>();
		OurArray<Integer> bA = new OurArray<>();

		
		int x = 3;
		int y = 4;
		
		bA.add(x);
		bA.add(y);
	

		for(int i = 0; i < 5; i++){
			bA.add(i);
		}
		
		
		
		for(int e : bA){
			System.out.print(e+" ,");
		}
		
		bA.removeAtIndex(3);
		bA.removeLast();	
			
		System.out.println();
		for(int e : bA){
			System.out.print(e+" ,");
		}
		
		 
	}
	
	public static void main(String[] args){
		new TestPlatform().run();
	}

}

