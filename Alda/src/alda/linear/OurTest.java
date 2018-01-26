package alda.linear;

public class OurTest {
	
	private void run() {
		MyALDAList<String> list = new MyALDAList<String>();
		

		
		list.add("Fuck");
		list.add("MF");
		list.add("Shit");
		list.add("Shiet");
		list.add("Shisseeish");
		
		System.out.println(list.toString());
		

		
		for(String s : list){
			if(s== "MF"){
				System.out.println(s);
			}
		
		}
		System.out.println(list.toString());
		
	}
	
	
	public static void main(String[] args){
		new OurTest().run();
	}


}
