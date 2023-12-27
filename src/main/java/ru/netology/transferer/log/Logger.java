package ru.netology.transferer.log;

import org.springframework.stereotype.Component;
import ru.netology.transferer.enumerable.LogType;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class Logger {
    private static Logger instance;
    private static String FILE = "logs/log.txt";

    protected AtomicInteger logNumber = new AtomicInteger(1);
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
        String str;
        if (logNumber.get() <= 0) {
            str = message + " " + LocalDateTime.now().format(formatter) + "\n";
        } else {
            str = "log № " + logNumber + " " + LocalDateTime.now().format(formatter) + " " + message + "\n";
        }
        logNumber.getAndIncrement();
        return (writeToFile(str));
    }

    public boolean log(String message, LogType logType) {
        String str;
        if (logNumber.get() <= 0) {
            str = message + " " + LocalDateTime.now().format(formatter) + "\n";
        } else {
            String strLogType;
            switch(logType) {
                case INFO :
                    strLogType = "INFO";
                    break;
                case ERROR:
                    strLogType = "ERROR";
                    break;
                default:
                    strLogType = "UNKNOWN";
                    break;
            }
            str = "log № " + logNumber + " / " + strLogType + " / " + LocalDateTime.now().format(formatter) + " " + message + "\n";
        }
        logNumber.getAndIncrement();
        return (writeToFile(str));
    }

    private boolean writeToFile(String str) {
        try (FileOutputStream fos = new FileOutputStream(FILE, true)) {
            byte[] bytes = str.getBytes();
            fos.write(bytes, 0, bytes.length);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}