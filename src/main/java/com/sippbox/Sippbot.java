package com.sippbox;

import com.sippbox.bot.JdaService;
import com.sippbox.bot.commands.manager.CommandRegistry;

import java.io.*;
import java.util.Scanner;

/**
 * Created by Nyvil on 11/06/2021 at 21:16
 * in project Discord Base
 */

public class Sippbot {
    Scanner scanner = new Scanner(System.in);
    private static final Sippbot instance = new Sippbot();
    private static JdaService jdaService;
    private static CommandRegistry commandRegistry;

    public static void main(String[] args) {
        if(args.length < 1) {
            //Check if token.txt exists
            //If it does, read the token from it
            if (new File("token.txt").exists()) {
                try {
                    File file = new java.io.File("token.txt");
                    FileReader fileReader = new java.io.FileReader(file);
                    BufferedReader bufferedReader = new BufferedReader(fileReader);
                    args = new String[1];
                    args[0] = bufferedReader.readLine();
                    bufferedReader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else {
                System.out.println("Enter your bot's token!");

                args = new String[1];
                args[0] = instance.scanner.nextLine();

                BufferedWriter writer;
                try {
                    writer = new BufferedWriter(new FileWriter("token.txt"));
                    writer.write(args[0]);
                    writer.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        jdaService = new JdaService(args[0]);
        commandRegistry = new CommandRegistry();

    }

    public static Sippbot getInstance() {
        return instance;
    }

    public JdaService getJdaService() {
        return jdaService;
    }

    public CommandRegistry getCommandRegistry() {
        return commandRegistry;
    }
}
