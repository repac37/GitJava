import Classes.SalesData;

public class Demo {

	public static void main(String[] args){
		SalesData data = new SalesData();
		int input = 10;
		int finalResult = summation(input);
		System.out.println("Result: "+finalResult);
		data.display();
		
	}
	
	public static int summation(int n){
		int result = n+2;
		return result;
	}
	
}
