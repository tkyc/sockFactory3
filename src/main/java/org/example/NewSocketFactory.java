package org.example;

import java.io.*;
import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.net.SocketFactory;
import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Field;
import org.example.QuickAck;
import java.lang.reflect.Method;
import java.nio.channels.SocketChannel;



public class NewSocketFactory extends SocketFactory {

    public NewSocketFactory() {
    }


    @Override
    public Socket createSocket() throws IOException {
        // Socket socket = new Socket();
        // Socket socket = SocketFactory.getDefault().createSocket();
        SocketChannel channel = SocketChannel.open();
        Socket socket = channel.socket();
        System.out.println("1");
        enableSocketOptions(socket);
        return socket;
    }

    @Override
    public Socket createSocket(String host, int port) throws IOException {
        Socket socket = SocketFactory.getDefault().createSocket(host, port);
        System.out.println("2");
        enableSocketOptions(socket);
        return socket;
    }

    @Override
    public Socket createSocket(String host, int port, InetAddress localHost,
                               int localPort) throws IOException, UnknownHostException {
        Socket socket = new Socket(host, port, localHost, localPort);
        System.out.println("3");
        enableSocketOptions(socket);
        return socket;
    }

    @Override
    public Socket createSocket(InetAddress host, int port) throws IOException {
        Socket socket =new Socket(host, port);
        System.out.println("4");
        enableSocketOptions(socket);
        return socket;
    }

    @Override
    public Socket createSocket(InetAddress address, int port, InetAddress localAddress,
                               int localPort) throws IOException {
        Socket socket = new Socket(address, port, localAddress, localPort);
        System.out.println("5");
        enableSocketOptions(socket);
        return socket;
    }

    private void enableSocketOptions(Socket socket)
    {
        int fd = getFd(socket);
        System.out.println("Socket file descriptor: " + fd);
        QuickAck quickAck = new QuickAck();
        int result = quickAck.setQuickAck(fd, 1);
        if (result == 0) {
            System.out.println("TCP_QUICKACK enabled");
        } else {
            System.out.println("Failed to enable TCP_QUICKACK");
        }
    }

    private static int getFd(Socket socket) {
try {
Field socketImplField = Socket.class.getDeclaredField("impl");
socketImplField.setAccessible(true);
Object socketImpl = socketImplField.get(socket);
if (socketImpl == null)
            {
                Method createImpl = Socket.class.getDeclaredMethod("createImpl", boolean.class);
                createImpl.setAccessible(true);
                createImpl.invoke(socket, true);
                socketImpl = socketImplField.get(socket);
            }
            Field fileDescriptorField = SocketImpl.class.getDeclaredField("fd");
            fileDescriptorField.setAccessible(true);
            FileDescriptor fileDescriptor = (FileDescriptor) fileDescriptorField.get(socketImpl);
            Field fdField = FileDescriptor.class.getDeclaredField("fd");
            fdField.setAccessible(true);
            return fdField.getInt(fileDescriptor);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get file descriptor from socket", e);
        }
    }
}
