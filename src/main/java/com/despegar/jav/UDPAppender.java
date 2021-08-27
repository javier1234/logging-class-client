package com.despegar.jav;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import ch.qos.logback.core.encoder.Encoder;

import java.io.IOException;
import java.net.*;

public class UDPAppender extends AppenderBase<ILoggingEvent> {
    private Encoder<ILoggingEvent> encoder;
    private DatagramSocket socket;
    private InetAddress host;
    private int port;

    @Override
    protected void append(ILoggingEvent eventObject) {
        byte[] msg = this.encoder.encode(eventObject);

        try {
            this.socket.send(new DatagramPacket(msg, msg.length, this.host, this.port));
            return;
        } catch (IOException ex) {
            this.addError("No fue posible enviar el mensaje " + eventObject.getFormattedMessage()
                    + " al servidor " + this.host.getHostName() + ":" + this.port);
        }
    }

    @Override
    public void start() {
        this.addInfo("Configurando los endpoints con Logback");
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            this.addError("No fue posible inicializar el socket UDP");
        }
        super.start();
    }

    @Override
    public void stop() {
        this.socket.close();
        super.stop();
    }

    public Encoder<ILoggingEvent> getEncoder() {
        return this.encoder;
    }
    public void setEncoder(Encoder<ILoggingEvent> encoder) {
        this.encoder = encoder;
    }

    public void setHost(String host) {
        try {
            this.host = InetAddress.getByName(host);
        } catch (UnknownHostException e) {
            this.addError("No se pudo conectar al servidor " + host);
        }
    }

    public void setPort(int port) {
        this.port = port;
    }
}

