package org.example;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AncestrySetImpl implements IAncestrySet {
    private Map<String, Set<String>> ancestryMap = new HashMap<>();

    @Override
    public Map<String, Set<String>> getAncestrySet(Map<String, Set<String>> blockAncestors,
                                                   List<Transaction> transactions) {
        for (Transaction transaction : transactions) {
            ancestryMap.put(transaction.getTxid(), prepareAncestrySet(blockAncestors, transaction.getTxid()));
        }

        return ancestryMap;
    }

    @Override
    public List<TransactionAncestorCountPair> getNTransactionsWithLargeAncestrySet(int n) {
        ArrayList<Map.Entry<String, Set<String>>> transactions = new ArrayList<>(ancestryMap.entrySet());
        transactions.sort(Map.Entry.comparingByValue(Comparator.comparingInt(Set::size)));

        List<TransactionAncestorCountPair> transactionAncestorCountPairs = new ArrayList<>();
        int size = transactions.size();
        for (int i = size - 1; i >= size - n && i >= 0; i--) {
            transactionAncestorCountPairs.add(new TransactionAncestorCountPair(transactions.get(i).getKey(),
                transactions.get(i).getValue().size()));
        }

        return transactionAncestorCountPairs;
    }

    private Set<String> prepareAncestrySet(Map<String, Set<String>> blockAncestors, String transactionId) {
        Set<String> parents = blockAncestors.getOrDefault(transactionId, new HashSet<>());

        if (!parents.isEmpty()) {
            for (String parentTransactionId : parents) {
                if (blockAncestors.containsKey(parentTransactionId)) {
                    Set<String> parentAncestors = prepareAncestrySet(blockAncestors, parentTransactionId);
                    ancestryMap.put(parentTransactionId, parentAncestors);

                    HashSet<String> ancestorsSet = new HashSet<>(parentAncestors);
                    ancestorsSet.add(parentTransactionId);

                    return ancestorsSet;
                }
            }
        }

        return new HashSet<>();
    }
}
