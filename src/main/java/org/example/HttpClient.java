package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class HttpClient {
    private static final CloseableHttpClient HTTP_CLIENT = HttpClients.createDefault();
    public static final String ENDPOINT = "https://blockstream.info/api/block/000000000000000000076c036ff5119e5a5a74df77abf64203473364509f7732/txs/";
    public static final ObjectMapper MAPPER = new ObjectMapper();
    public static final String API_ERROR_RESPONSE = "start index out of range";

    public Iterator<List<Transaction>> fetchAllTransactions() {
        return new ResponseIterator();
    }

    // Using Iterator Pattern for Lazy Loading
    private class ResponseIterator implements Iterator<List<Transaction>> {
        private int nextPage;

        public ResponseIterator() {
            nextPage = 0;
        }

        @Override
        public boolean hasNext() {
            return nextPage != -1;
        }

        @Override
        public List<Transaction> next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Reached the last page of the API response.");
            }
            System.out.println("Fetching page: " + nextPage);
            List<Transaction> transactions = invokeTransactionsEndpoint(nextPage);

            if (transactions == null) {
                nextPage = -1;
            } else {
                nextPage += 25;
            }

            return transactions;
        }

        private List<Transaction> invokeTransactionsEndpoint(int page) {
            try {
                HttpResponse httpresponse = HTTP_CLIENT.execute(new HttpGet(ENDPOINT + page));

                return MAPPER.readValue(httpresponse.getEntity().getContent(), new TypeReference<List<Transaction>>() {});
            } catch (IOException e) {
                System.out.println("Invalid API response.");
            }
            return null;
        }
    }
}
