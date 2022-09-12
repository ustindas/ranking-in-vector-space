package com.company;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class Term {
    int termFrequency;
    LinkedList<TermDocument> list;

     public Term(int docID) {
        termFrequency = 1;
        list = new LinkedList<>();
        list.add(new TermDocument(docID));
    }

    public void addDocument(int docID){
        termFrequency++;
        if (list.getLast().getDocID() == docID){
            list.getLast().increaseFrequency();
        }
        else list.add(new TermDocument(docID));
    }

    public void computeTfIdf(double idf){
        ListIterator<TermDocument> L = list.listIterator();
        TermDocument i;
       while (L.hasNext()){
           i = L.next();
           i.computeTfIdf(idf);
       }
    }

    public int getDocumentFrequency(){
        return list.size();
    }

    public LinkedList<TermDocument> getList(){
        return list;
    }

    @Override
    public String toString() {
        return "Term{" +
                "termFrequency=" + termFrequency +
                ", list=" + list +
                '}';
    }
}
