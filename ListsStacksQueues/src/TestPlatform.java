import linkedlists.OurLinkedList;

public class TestPlatform {

	private void run() {
		//MyArrayList<Integer> mA = new MyArrayList<>();
		OurLinkedList<Integer> bA = new OurLinkedList<>();

		
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
		
		int remove = bA.remove(2);
		
		System.out.println();
		for(int e : bA){
			System.out.print(e+" ,");
		}
		System.out.println();
		
		System.out.println(remove);
		
		 
	}
	
	public static void main(String[] args){
		new TestPlatform().run();
	}

}

