package org.example;

public class TransactionAncestorCountPair {
    private String transactionId;
    private int count;

    public TransactionAncestorCountPair() {
    }

    public TransactionAncestorCountPair(String transactionId, int count) {
        this.transactionId = transactionId;
        this.count = count;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "TransactionAncestorCountPair{" +
            "transactionId='" + transactionId + '\'' +
            ", count=" + count +
            '}';
    }
}
