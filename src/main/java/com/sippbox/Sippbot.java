package com.sippbox;

import com.sippbox.bot.JdaService;
import com.sippbox.bot.commands.manager.CommandRegistry;

import java.util.Scanner;

/**
 * Created by Nyvil on 11/06/2021 at 21:16
 * in project Discord Base
 */

public class Sippbot {

    private static final Sippbot instance = new Sippbot();
    private static JdaService jdaService;
    private static CommandRegistry commandRegistry;

    public static void main(String[] args) {
        if(args.length < 1) {
            System.out.println("Enter your bot's token!");

            args = new String[1];
            //args[0] = instance.scanner.nextLine();

            args[0] = "MTE4ODY2ODU1ODA2NzExMzk5NA.GBtwIM.68HWVRSfLmBdR-dujMHIP_XrelpiDjsKY1bnSE";
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
