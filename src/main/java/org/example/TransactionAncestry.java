package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.example.HttpClient.MAPPER;

public class TransactionAncestry {
    Map<String, Set<String>> blockAncestors = new HashMap<>();
    IAncestrySet ancestrySet = new AncestrySetImpl();
    public static final String TRANSACTIONS_RESOURCE_FOLDER_PATH = "/transactions.json";

    public void process() {
        try {
            InputStream inputStream = Main.class.getResourceAsStream(TRANSACTIONS_RESOURCE_FOLDER_PATH);

            List<Transaction> transactions;
            if (inputStream == null) {
                transactions = fetchAndWriteTransactionsToFile();
            } else {
                transactions = MAPPER.readValue(inputStream, new TypeReference<List<Transaction>>() {
                });
            }

            prepareAncestrySet(transactions);

            Map<String, Set<String>> ancestrySetRes = ancestrySet.getAncestrySet(blockAncestors, transactions);
            System.out.println("Transactions Ancestors Set: " + ancestrySetRes);

            List<TransactionAncestorCountPair> largeAncestrySet = ancestrySet.getNTransactionsWithLargeAncestrySet(10);
            System.out.println("\n\nTransactions with largest Ancestors Set: " + largeAncestrySet);
        } catch (IOException e) {
            System.out.println("Failed to write to file.");
        }
    }

    private void prepareAncestrySet(List<Transaction> transactions) {
        // Maintain Block Transaction Ids
        for (Transaction transaction : transactions) {
            Set<String> ancestors = new HashSet<>();

            for (Transaction.TransactionIn transactionIn : transaction.getVin()) {
                ancestors.add(transactionIn.getTxid());
            }
            blockAncestors.put(transaction.getTxid(), ancestors);
        }
    }

    private static List<Transaction> fetchAndWriteTransactionsToFile() {
        Iterator<List<Transaction>> iterator = new HttpClient().fetchAllTransactions();

        List<Transaction> finalTransactions = new ArrayList<>();
        while (iterator.hasNext()) {
            List<Transaction> transactions = iterator.next();
            if (transactions != null) {
                finalTransactions.addAll(transactions);
            }
        }

        try {
            ObjectWriter writer = MAPPER.writer(new DefaultPrettyPrinter());
            writer.writeValue(new File(Main.class.getResource(TRANSACTIONS_RESOURCE_FOLDER_PATH).getFile()),
                finalTransactions);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return finalTransactions;
    }
}
