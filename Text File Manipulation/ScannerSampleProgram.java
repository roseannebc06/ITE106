import java.util.Scanner;

public class  ScannerSampleProgram
{
	public static void main(String[] args) {
	Scanner getData = new Scanner(System.in);
	System.out.print("Enter your age: ");
	int age = getData.nextInt();
	System.out.print("Enter your name: ");
	String name = getData.next();
	System.out.println("Hi " + name);
	System.out.println("Your age is " + age);
	}
} 
