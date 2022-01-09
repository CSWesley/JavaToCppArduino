package Translator;

import java.io.IOException;
import java.util.Scanner;
import java.io.File;

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
        File file = new File(inputFile);
        String fullInput = readFile(file);
        System.out.println(fullInput);
    }

    private String readFile(File inputFile) throws IOException {
        Scanner scanner = new Scanner(inputFile);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.startsWith("//")) {
                // go to next line
                System.out.println("Skipping comment: " + line);
                continue;
            } else if (line.startsWith("public class") || line.startsWith("class")) {
                // get the class name
                String className = line.substring(line.indexOf("class") + 6, line.indexOf("{"));
                System.out.println("Class name: " + className);
                continue;
            } else {
                // translate the line
                System.out.println("Translating line: " + line + " (Skipped)");
                continue;
            }
        }


        return "File is empty";
    }
}