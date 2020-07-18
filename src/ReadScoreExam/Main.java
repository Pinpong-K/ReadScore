package ReadScoreExam;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        //get text from file
        String filename = args[0];
        String filePath = System.getProperty("user.dir") +"\\"+ filename;
        System.out.println("File Path: "+filePath);
        String message = "";
        try {
            BufferedReader file = new BufferedReader(new FileReader (filePath));
            String temMessage = "";
            while((temMessage = file.readLine()) != null)
            {
                message = message + temMessage;
            }
        }
        catch (FileNotFoundException ex) {
            System.out.println("File not found");
        }
        catch (IOException e){
            System.out.println("IOException");
        }

        System.out.println("print text : "+message);
        Readability readability = new Readability(message);
        readability.printResult();

        while (true) {
            System.out.print("Enter the score you want to calculate: ");
            String socreSelect = scan.next();
            if (socreSelect.equals("all")) {
                readability.printReadabilityScore();
                break;
            }
        }







    }


}
