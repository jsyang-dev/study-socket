package me.study.socket.server;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

@Component
public class SocketRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) {

        /*
        [공통부분]
        전문길이(4), 업무구분(4), 일자(8), 시간(6)

        [DATA부분]
        신청(0001)
        이름(10), 신청금액(10)
        0042 A001 20200807 093000 'YANG      ' 0100000000

        신청응답(1001)
        승인여부(1), 승인금액(10)
        0033 A001 20200807 093000 1 0090000000

        0042A00120200807093000YANG      0100000000
        0033B0012020080709300010090000000
         */

       int port = 8000;

        try (ServerSocket serverSocket = new ServerSocket(port)) {

            System.out.println("Server is listening on port " + port);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("[ " + socket.getInetAddress() + " ] client connected");

                InputStream input = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));

                OutputStream output = socket.getOutputStream();
                PrintWriter writer = new PrintWriter(output, true);

                String line;

                while ((line = reader.readLine()) != null) {
                    System.out.println("Message received: " + line);

                    if ("A001".equals(line.substring(4, 8))) {
                        writer.println("0033B0012020080709300010090000000");
                    }
                }
            }

        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
