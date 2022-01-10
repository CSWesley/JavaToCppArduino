package Translator;

import java.io.IOException;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;

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
        String fullInput = readFile(file, outputDir);
        System.out.println("\n--\n" + fullInput + "\n--\n");
    }

    private String readFile(File inputFile, String outputDir) throws IOException {
        Scanner scanner = new Scanner(inputFile);
        StringBuilder fileToPaste = new StringBuilder();

        String className = inputFile.getName().replace(".java", "");
        File outputFile = new File(outputDir + "/" + className.replaceAll(" ", "") + ".ino");
        outputFile.createNewFile();
        System.out.println("Creating file: " + outputFile.getAbsolutePath());
        FileWriter fw = new FileWriter(outputFile);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            if (line.contains("//")) {
                String comment = line.substring(line.indexOf("//"));
                fileToPaste.append(comment + "\n");
                continue;
            } else if (line.startsWith("public class") || line.startsWith("class")) {
                continue;
            } else if (line.startsWith("package")) {
                continue;
            } else if (line.startsWith("    public static void setup()") || line.startsWith("void setup()") || line.startsWith("public void setup()")) {
                fileToPaste.append("void setup() {\n");
                continue;
            } else if (line.startsWith("    public static void loop()") || line.startsWith("void loop()") || line.startsWith("public void loop()")) {
                fileToPaste.append("void loop() {\n");
                continue;
            } else if (line.equals("}") || line.equals("    }")) {
                fileToPaste.append("}\n");
                continue;
            } else if (line.contains("System.out.println") || line.contains("System.out.print")) {
                String print = line.replace("System.out.println", "Serial.println").replace("System.out.print", "Serial.print");
                // remove four spaces before the print statement
                print = print.substring(4);
                fileToPaste.append(print + "\n");
            }
            
            else {
                if (!line.equals("")) {
                    fileToPaste.append(line.substring(4) + "\n");
                } else {
                    fileToPaste.append("\n");
                }
                
                continue;
            }
        }

        fileToPaste.deleteCharAt(fileToPaste.length() - 2);

        fw.write(fileToPaste.toString());
        fw.close();

        scanner.close();


        return fileToPaste.toString();
    }
}