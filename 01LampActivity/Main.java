import java.util.Scanner;

public class Main
{
  public static void main(String[] args) {
    Lamp led = new Lamp();
    Lamp halogen = new Lamp();
    Lamp Flourescent = new Lamp();
    Lamp Incandescent = new Lamp();

    led.name = args[0];
    halogen.name = args[2];
    Flourescent.name = args[0];
    Incandescent.name = args[2];

    led.isOn = Boolean.parseBoolean(args[3]);
    led.turnOn();
    System.out.println("Led turn On by " + led.name);

    Flourescent.isOn = Boolean.parseBoolean(args[3]);
    Flourescent.turnOn();
    System.out.println("Flourescent turn On by " + Flourescent.name);

    halogen.isOn = Boolean.parseBoolean(args[1]);
    Incandescent.isOn = Boolean.parseBoolean(args[1]);
    Incandescent.turnOff();
    System.out.println("Incandescent turn Off by " + Incandescent.name);
    halogen.turnOff();
    System.out.println("Halogen turn Off by " + halogen.name);
  }
}
