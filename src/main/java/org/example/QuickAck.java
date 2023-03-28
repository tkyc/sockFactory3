package org.example;

public class QuickAck {
    static {
        
        //System.load("/home/ioanc/java/sockFactory3/native/libquickack.so");
        //System.load("/mnt/c/POCApp/sockFactory3/native/libquickack.so");
        System.load("/app/native/libquickack.so");
    }

    public native int setQuickAck(int sockfd, int value);

}