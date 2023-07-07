package org.example;

//ajays97
//9008003968
//
//https://blockstream.info/api/block-height/680001
//https://blockstream.info/api/block/000000000000000000076c036ff5119e5a5a74df77abf64203473364509f7732/txs/50

//txId1 -> txId2 / txId3
//txId2 -> txId4
//txId3 -> txId5

public class Main {
    public static void main(String[] args) {
        new TransactionAncestry().process();
    }
}