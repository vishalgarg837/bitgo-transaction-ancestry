package org.example;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IAncestrySet {
    Map<String, Set<String>> getAncestrySet(Map<String, Set<String>> blockAncestors, List<Transaction> transactions);

    List<TransactionAncestorCountPair> getNTransactionsWithLargeAncestrySet(int n);
}
