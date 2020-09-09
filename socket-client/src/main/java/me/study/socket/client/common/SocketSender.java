package me.study.socket.client.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

@Component
@Slf4j
public class SocketSender {

    @Value("${app.socket-server.host}")
    private String host;

    @Value("${app.socket-server.port}")
    private int port;

    public String send(String text) {

        String receivedText = null;
        log.debug("[Send] \"" + text + "\"");

        try (Socket socket = new Socket(host, port)) {
            socket.setSoTimeout(5000);

            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);
            writer.println(text);

            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            receivedText = reader.readLine();
            log.debug("[Receive] \"" + receivedText + "\"");

        } catch (UnknownHostException e) {
            log.error("Server not found: " + e.toString());
        } catch (IOException e) {
            log.error("I/O Exception: " + e.toString());
        } catch (Exception e) {
            log.error(e.toString());
        }

        return receivedText;
    }
}
