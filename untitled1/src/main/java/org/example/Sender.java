package org.example;

class Sender implements Runnable {
    private Message message;

    public Sender(Message message) {
        this.message = message;
    }

    @Override
    public void run() {
        String[] messages = {
                "Hello", "How are you?", "Goodbye"
        };

        for (String msg : messages) {
            message.send(msg);
            System.out.println("Sent: " + msg);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        message.send("end");
    }
}
