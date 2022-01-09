package Translator;

import java.io.IOException;
import java.util.Scanner;
import Translator.Translate;

public class Translate {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java translate <input file> <output directory>");
            System.exit(1);
        } else {
            String inputFile = args[0];
            String outputDir = args[1];
            try {
                Translate translator = new Translate();
                translator.translate(inputFile, outputDir);
            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
                System.exit(1);
            }
        }
    }

    private void translate(String inputFile, String outputDir) throws IOException {
        String fullInput = readFile(inputFile);
        System.out.println(fullInput);
    }

    private String readFile(String inputFile) {
        Scanner scanner = new Scanner(inputFile);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.startsWith("//")) {
                // go to next line
                continue;
            } else if (line.startsWith("public class") || line.startsWith("class")) {
                // get the class name
                String className = line.substring(line.indexOf("class") + 6, line.indexOf("{"));
                return className;
            } else {
                // translate the line
                continue;
            }
        }


        return "File is empty";
    }
}