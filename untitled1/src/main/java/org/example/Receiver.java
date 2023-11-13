package org.example;

class Receiver implements Runnable {
    private Message message;

    public Receiver(Message message) {
        this.message = message;
    }

    @Override
    public void run() {
        while (true) {
            String receivedMessage = message.receive();
            System.out.println("Received: " + receivedMessage);
            if (receivedMessage.equals("end")) {
                break;
            }
        }
    }
}
