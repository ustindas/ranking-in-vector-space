package  com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.*;
import java.util.*;


public class InvertedIndex {
    static List<String> documents = new ArrayList<String>();
    Map<String, Term> index = new HashMap<String, com.company.Term>();

    public InvertedIndex(String str) throws FileNotFoundException {
        File file = new File(str);
        Scanner in = new Scanner(file);
    }
    public void indexDocument(String path) throws IOException {
        File f = new File(path);
        String filePath = path;
        Document doc = Jsoup.parse(f, "UTF-8");
        String docHTML = doc.select("body").text();

        int docID = documents.size();
        if (documents.contains(path)) {
            return;
        }
        String word = "";
        documents.add(path);

        docHTML = docHTML.toLowerCase();
        String[] words = docHTML.split("[^a-zA-Z0-9_']+");
        Stemmer stemmer = new Stemmer();

        Term idx;
        boolean b = false;

        for (int i = 0; i < words.length; i++) {

            word = words[i];

            stemmer.add(word.toCharArray(), word.length());
            stemmer.stem();
            String term = stemmer.toString();
            idx = index.get(term);

            if (idx == null) {
                idx = new Term(docID);
                index.put(term, idx);
            }
            else
                idx.addDocument(docID);
        }
    }

    public void indexCollection(String folder)throws IOException{
        File dir = new File(folder);
        String[] files = dir.list();
        for (int i = 0; i < files.length; i++){
            this.indexDocument(folder+"\\"+files[i]);
        }

        Iterator<String> iter = index.keySet().iterator();
        double idf;
        String term;
        int N = documents.size(),dt;
        while (iter.hasNext()) {
            term = iter.next();
            dt = index.get(term).getDocumentFrequency();
            idf =  Math.log10((double) N/dt);
            index.get(term).computeTfIdf(idf);
        }
    }

    void getIntersection(ArrayList<DocumentRelevance> answer, Term term){
        LinkedList<TermDocument> list = term.getList();
        ListIterator<TermDocument> it = list.listIterator();
        TermDocument a;
        while(it.hasNext()){
            a = it.next();
            answer.get(a.getDocID()).updateRelevance(a.getTfIdf());
        }
    }

    ArrayList<DocumentRelevance> executeQuery(String query){
        String line = query;
        line = line.toLowerCase();
        ArrayList<DocumentRelevance> answer = new ArrayList<>();
        //string = query;
        String[] words = line.split(" ");
        Stemmer stemmer = new Stemmer();
        for (int i = 0; i < documents.size(); i++){
            answer.add(new DocumentRelevance(i));
        }
        for (int i = 0; i < words.length; i++){
            stemmer.add(words[i].toCharArray(), words[i].length());
            stemmer.stem();
            String term = stemmer.toString();
            if(index.get(term)!=null){
                getIntersection(answer,index.get(term));
            }
        }
        Collections.sort(answer, new DocumentRelevanceComparator());
        Iterator<DocumentRelevance> it = answer.iterator();
        DocumentRelevance a;
        while(it.hasNext()) {
            a = it.next();
            if (a.getRelevance()<=0)
                it.remove();
        }
       return answer;
    }

    public ArrayList<DocumentRelevance> executeQuery(String q, int n){
        ArrayList<DocumentRelevance> answer = executeQuery(q);
        Iterator<DocumentRelevance> it = answer.iterator();
        DocumentRelevance a;
        int c =0;
        while(it.hasNext()) {
            a = it.next();
            c++;
            if (a.getRelevance()<0 || c>n)
                it.remove();
        }
        return answer;
    }

    public void printResult(String query){

        System.out.println("Запрос: "+query);
        print(executeQuery(query));
    }

    private void print(ArrayList<DocumentRelevance> query){
        ArrayList<DocumentRelevance> answer = query;
        Iterator<DocumentRelevance> it = answer.iterator();
        DocumentRelevance a;
        int c = 0;
        while(it.hasNext()) {
            a = it.next();
            c++;
            System.out.printf("%2d. (%.5f) %d.%s  \n", c, a.getRelevance(), a.getDocID(), documents.get(a.getDocID()));
        }
    }

    public void printResult(String query, int n){
        System.out.println("Запрос: "+query+" n="+n);
        print(executeQuery(query,n));
    }
}
    


