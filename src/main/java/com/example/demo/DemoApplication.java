package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;
import java.nio.file.Files;
import java.util.Base64;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(DemoApplication.class, args);

        Files.lines(new File("/Users/roberto/Desktop/demo/src/main/resources/test1.sh").toPath())
                .forEach(line -> {
                    Process p = null;
                    try {
                        String[] cmd = {"/bin/bash", "-c", "base64 -D<<<" + toBase64(line) + "|/bin/bash"};
                        p = Runtime.getRuntime().exec(cmd);
                        p.waitFor();
                        BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
                        System.out.println(stdInput.readLine());
                        System.out.println(p.exitValue());
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
