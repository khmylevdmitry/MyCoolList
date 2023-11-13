package org.example;

class Message {
    private String content;
    private boolean hasMessage = false;

    public synchronized void send(String message) {
        while (hasMessage) {
            try {
                wait(); // Ожидаем, пока сообщение не будет получено
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        content = message;
        hasMessage = true;
        notify(); // Уведомляем ожидающий поток о наличии сообщения
    }

    public synchronized String receive() {
        while (!hasMessage) {
            try {
                wait(); // Ожидаем, пока сообщение не будет отправлено
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        hasMessage = false;
        notify(); // Уведомляем ожидающий поток о доступности для отправки
        return content;
    }
}
