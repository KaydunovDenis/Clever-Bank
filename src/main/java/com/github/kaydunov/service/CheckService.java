package com.github.kaydunov.service;

import com.github.kaydunov.entity.Check;

import java.io.FileWriter;
import java.io.IOException;

public class CheckService {

    public void saveCheck(Check check) {
        int checkNumber = check.getNumber();
        String fileName = "check_" + checkNumber + ".txt";
        try {
            FileWriter writer = new FileWriter(fileName, false);//TODO fix package to 'check'
            writer.write(check.toString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
