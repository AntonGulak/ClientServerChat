package network;

public class TCPConnectionListener {

    //Соединение готово к работе
    void onConnectionReady(TCPConnection tcpConnection);

    //Соединение приняло входящую строчку
    void onRecitveString(TCPConnection tcpConnection, String value);

    //Соединение порвалось
    void onDisconnect(TCPConnection tcpConnection);

    //Поймали исключение
    void onException(TCPConnection tcpConnection, Exception e);

}