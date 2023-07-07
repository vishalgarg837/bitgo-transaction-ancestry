package org.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Transaction {
    private String txid;
    private List<TransactionIn> vin = new ArrayList<>();

    public Transaction() {
    }

    public Transaction(String txid, List<TransactionIn> vin) {
        this.txid = txid;
        this.vin = vin;
    }

    public String getTxid() {
        return txid;
    }

    public void setTxid(String txid) {
        this.txid = txid;
    }

    public List<TransactionIn> getVin() {
        return vin;
    }

    public void setVin(List<TransactionIn> vin) {
        this.vin = vin;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TransactionIn {
        private String txid;

        public TransactionIn() {
        }

        public TransactionIn(String txid) {
            this.txid = txid;
        }

        public String getTxid() {
            return txid;
        }

        public void setTxid(String txid) {
            this.txid = txid;
        }
    }
}
