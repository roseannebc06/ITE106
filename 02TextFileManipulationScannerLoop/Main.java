import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        File myFile = new File("JauxshFile.txt");
        Scanner fileScanner = new Scanner(myFile);
        
        while (fileScanner.hasNextLine()) {
            System.out.println(fileScanner.nextLine());
        }
        
        fileScanner.close();
    }
}
