package com.company;

import java.util.Comparator;

class DocumentRelevanceComparator implements Comparator<DocumentRelevance> {

     @Override
     public int compare(DocumentRelevance o1, DocumentRelevance o2) {
        return Double.compare(o2.getRelevance(), o1.getRelevance());
    }
}
