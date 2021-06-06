package network;

public interface TCPConnectionListener {

    //Соединение готово к работе
    void onConnectionReady(TCPConnection tcpConnection);

    //Соединение приняло входящую строчку
    void onRecieveString(TCPConnection tcpConnection, String value);

    //Соединение порвалось
    void onDisconnect(TCPConnection tcpConnection);

    //Поймали исключение
    void onException(TCPConnection tcpConnection, Exception e);

}
