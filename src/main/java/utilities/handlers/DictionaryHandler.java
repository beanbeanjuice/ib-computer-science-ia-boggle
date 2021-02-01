package utilities.handlers;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class DictionaryHandler {

    private HashMap<String, ArrayList<String>> dictionaryTree;

    public DictionaryHandler() {
        dictionaryTree = new HashMap<>();
        makeDictionary();
    }

    private void makeDictionary() {
        try (Scanner scanner = new Scanner(FileHandler.getResourceAsFile("dictionary.csv"))) {
            while (scanner.hasNextLine()) { // For every line in the csv file, get the word, then add it to the dictionary.
                String word = getWord(scanner.nextLine());
                if (word.length() >= 3 && (qLength(word) || word.length() <= 16)) {
                    addDictionaryTree(word);
                }
            }
        } catch (Exception e) {
            System.out.println("ERROR: No words found.");
        }
    }

    // NEEDED AS A BASE-CASE SINCE "Q" IS ALWAYS FOLLOWED BY A "U"
    private boolean qLength(String word) {
        if (word.contains("QU")) {
            return word.length() <= 17;
        }

        return true;
    }

    public void addDictionaryTree(String word) {
        // If the hashmap key does not exist for that character, create it.
        if (dictionaryTree.get(Character.toString(word.charAt(0))) == null) {
            ArrayList<String> tmp = new ArrayList<>();
            tmp.add(word);
            dictionaryTree.put(Character.toString(word.charAt(0)), tmp);

        }
        dictionaryTree.get(Character.toString(word.charAt(0))).add(word);
    }

    // TRAVERSES THE DICTIONARY TREE FROM THE STARTING CHARACTER TO SEE IF THE DICTIONARY CONTAINS THAT WORD.
    public boolean traverseDictionaryTree(String word) {
        return dictionaryTree.get(Character.toString(word.charAt(0))).contains(word);
    }

    // TRAVERSES THE DICTIONARY TREE FROM THE STARTING STRING AND RETURNS FALSE IF NO WORDS START WITH THAT STRING.
    public boolean characterTraverseDictionaryTree(String word) {
        for (String string : dictionaryTree.get(Character.toString(word.charAt(0)))) {
            if (string.startsWith(word)) {
                return true;
            }
        }
        return false;
    }

    private String getWord(String word) {
        try (Scanner rowScanner = new Scanner(word)) {
            rowScanner.useDelimiter(",");
            word = word.replaceAll("\"", "");
            return word.toUpperCase();
        }
    }

    public HashMap<String, ArrayList<String>> getDictionaryTree() { // Checks if that word is in the dictionary tree.
        return dictionaryTree;
    }

}
