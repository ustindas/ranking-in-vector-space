package com.company;

public class DocumentRelevance {

    private int docID;
    private double relevance;

    public DocumentRelevance(int docID){
        this.docID = docID;

    }
    public int getDocID(){

        return this.docID;
    }

    public double getRelevance(){
        return this.relevance;
    }

    public void updateRelevance(double tf){
        this.relevance+=tf;

    }

    @Override
    public String toString() {
        return String.format("%2d - %.5f", docID, relevance);
    }
}
