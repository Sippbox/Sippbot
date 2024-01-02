package com.sippbox;

import com.sippbox.bot.JdaService;
import com.sippbox.bot.commands.manager.CommandRegistry;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Main class for Sippbot.
 */
public class Sippbot {
    // Singleton instance of the Sippbot class
    private static final Sippbot instance = new Sippbot();
    // Service for interacting with the Discord API
    private static JdaService jdaService;
    // Registry for managing bot commands
    private static CommandRegistry commandRegistry;

    /**
     * Main method for Sippbot.
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        // Retrieve the bot token
        String token = getToken(args);
        // Initialize the JDA service with the bot token
        jdaService = new JdaService(token);
        // Initialize the command registry
        commandRegistry = new CommandRegistry();
    }

    /**
     * Retrieves the bot token.
     * If no token is provided as a command line argument, it attempts to read it from the file "token.txt".
     * If the file does not exist, it prompts the user to enter the token and saves it to the file.
     * @param args Command line arguments
     * @return The bot token
     */
    private static String getToken(String[] args) {
        String token = "";
        if (args.length < 1) {
            try {
                // Attempt to read the token from a file
                token = Files.readString(Paths.get("token.txt"));
            } catch (IOException e) {
                System.out.println("Enter your bot's token!");
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("token.txt"))) {
                    // Prompt the user to enter the token and save it to the file
                    token = new Scanner(System.in).nextLine();
                    writer.write(token);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } else {
            // Use the token provided as a command line argument
            token = args[0];
        }
        return token;
    }


    /**
     * Returns the singleton instance of the Sippbot class.
     * @return The singleton instance
     */
    public static Sippbot getInstance() {
        return instance;
    }

    /**
     * Returns the JDA service.
     * @return The JDA service
     */
    public JdaService getJdaService() {
        return jdaService;
    }

    /**
     * Returns the command registry.
     * @return The command registry
     */
    public CommandRegistry getCommandRegistry() {
        return commandRegistry;
    }
}