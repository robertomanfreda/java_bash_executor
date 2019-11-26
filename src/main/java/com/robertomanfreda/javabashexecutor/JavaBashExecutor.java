package com.robertomanfreda.javabashexecutor;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.Base64;

@Slf4j
public class JavaBashExecutor {

    public static void main(String[] args) throws IOException {
        Files.lines(new File("/Users/roberto/Desktop/demo/src/main/resources/test1.sh").toPath())
                .forEach(line -> {
                    Process process;
                    try {
                        String[] cmd = {"/bin/bash", "-c", "base64 -D<<<" + toBase64(line) + "|/bin/bash"};
                        process = Runtime.getRuntime().exec(cmd);
                        process.waitFor();
                        BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
                        log.debug(stdInput.readLine());
                        log.debug("{}", process.exitValue());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                })
        ;
    }

    private static String toBase64(String value) {
        return Base64.getEncoder().encodeToString(value.getBytes());
    }
}
