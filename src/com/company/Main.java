
package  com.company;

import java.io.IOException;

public class Main{
    
public static void main(String[] args) throws IOException {
    InvertedIndex myIndex = new InvertedIndex("stop_words.txt");
    myIndex.indexCollection("collection_html");
    myIndex.printResult("Juliet");
    myIndex.printResult("Juliet",1);
    myIndex.printResult("king",2);
    myIndex.printResult("Romeo");
    myIndex.printResult("Caesar");
    myIndex.printResult("SpiderMan");
    myIndex.printResult("Brutus Caesar Calpurnia");
    myIndex.printResult("Brutus Caesar Calpurnia is a");

    }
}
