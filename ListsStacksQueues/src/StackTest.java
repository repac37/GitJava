

import stack.MyStack;

public class StackTest {

	public void run(){
		MyStack<Integer> stack = new MyStack<>();
		
		stack.push(4);
		stack.push(1);
		stack.push(2);
		stack.push(5);
		stack.push(4);
		stack.push(23);
		stack.push(1);	
		System.out.println(stack.pop());
		System.out.println(stack.pop());
		System.out.println(stack.pop());
		System.out.println(stack.contains(5));
		
		
		System.out.println(stack.toString());
	
	}
	
	public static void main(String[] args){
		new StackTest().run();
	}

}
