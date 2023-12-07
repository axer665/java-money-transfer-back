package ru.netology.transferer.domain.log;

import ru.netology.transferer.enumerable.LogType;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {

    private static Logger instance;
    private static final String PATH = "./src/main/java/ru/netology/transferer/domain/log/log.txt";

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private Logger() {
    }

    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    public boolean log(String message) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(PATH, true));
            writer.write(LocalDateTime.now().format(formatter) + " " + message + "\n");
            writer.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean log(String message, LogType logType) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(PATH, true));
            switch(logType) {
                case INFO :
                    writer.write(LocalDateTime.now().format(formatter) + " / INFO / " + message + "\n");
                    writer.flush();
                    break;
                case ERROR:
                    writer.write(LocalDateTime.now().format(formatter) + " / ERROR / " + message + "\n");
                    break;
                default:
                    writer.write(LocalDateTime.now().format(formatter) + " / UNKNOWN / " + message + "\n");
                    break;
            }
            writer.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}