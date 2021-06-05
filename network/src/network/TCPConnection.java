package network;

import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;

public class TCPConnection {

    //сокет для TCP
    private final Socket socket;

    //Поток, слушающий входящее сообщение
    private final Thread rxThread;

    //Слушатель событий
    private final TCPConnectionListener eventListener;

    //Поток для чтения и записи строк
    private final BufferedReader in;
    private final BufferedWriter out;

    //Конструктор для сокета, создаваемого внутри
    public TCPConnection(TCPConnectionListener eventListener, String ipAddr, int port) throws  IOException {
        this(eventListener, new Socket(ipAddr,port));
    }

    //На вход примет готовый объект сокета и создаст с ним соединение
    //Конструктор для сокета, создаваемого снаружи
    public TCPConnection(TCPConnectionListener eventListener, Socket socket) throws IOException {
        this.socket = socket;
        this.eventListener = eventListener;

        //Для сокета примем входящий и исходящий поток
        in = new BufferedReader(new InputStreamReader(socket.getInputStream(),
                Charset.forName("UTF-8")));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),
                Charset.forName("UTF-8")));

        //Запуск нового поток, слущающего входящее соединение
        //Создаем анонимный класс
        rxThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    eventListener.onConnectionReady(TCPConnection.this);
                    while (!rxThread.isInterrupted()) {
                        String msg = in.readLine();
                        eventListener.onRecitveString(TCPConnection.this,msg);
                    }
                } catch (IOException e) {
                    eventListener.onException(TCPConnection.this,e);
                } finally {
                    eventListener.onDisconnect(TCPConnection.this);

                }
            }
        });
        rxThread.start();


    }

    public synchronized void sendString(String value){
        try {
            out.write(value + "\r\n");
            //Сбрасывает буфферы
            out.flush();
        } catch (IOException e) {
            eventListener.onException(TCPConnection.this,e);
            disconnect();
        }

    }

    public synchronized void disconnect() {
        rxThread.interrupt();
        try {
            socket.close();
        } catch (IOException e) {
            eventListener.onException(TCPConnection.this,e);
        }
    }

    @Override
    public String toString() {
        return "TCPConnection: " + socket.getInetAddress() + ": " + socket.getPort();
    }
}
