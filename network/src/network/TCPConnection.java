package network;

import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;

public class TCPConnection {

    //сокет для TCP
    private final Socket socket;

    //Поток, слушающий входящее сообщение
    private final Thread rxThread;

    //Поток для чтения и записи строк
    private final BufferedReader in;
    private final BufferedWriter out;

    //На вход примет готовый объект сокета и создаст с ним соединение
    //Метод может генерировать исключения
    public TCPConnection(Socket socket) throws IOException {
        this.socket = socket;

        //Для сокета примем входящий и исходящий поток
        in = new BufferedReader(new InputStreamReader(socket.getInputStream(),
                Charset.forName("UTF-8")));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),
                Charset.forName("UTF-8")));

        

    }


}
