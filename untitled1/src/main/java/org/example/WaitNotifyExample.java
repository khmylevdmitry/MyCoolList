package org.example;

public class WaitNotifyExample {
    public static void main(String[] args) {
        Message message = new Message();

        Thread senderThread = new Thread(new Sender(message));
        Thread receiverThread = new Thread(new Receiver(message));

        senderThread.start();
        receiverThread.start();
    }
}





