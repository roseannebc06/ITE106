class Animal {
	public void walk() {
		System.out.println("Animal walks");
	}
}

class Puppy extends Animal {
	public void sleep() {
		System.out.println("Puppy sleeps");
	}	
}

class Lion extends Animal {
	public void roar() {
		System.out.println("Lion roars");
	}	
}

public class HeirarchicalInheritance {
	public static void main(String[] args) {
		Puppy Mitchi = new Puppy();
		Mitchi.walk();
		Mitchi.sleep();
		
		Lion myLion = new Lion();
		myLion.walk();
		myLion.roar();
	}
}
