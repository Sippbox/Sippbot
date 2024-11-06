package com.sippbox.bot;

import com.sippbox.bot.commands.commands.ReportMessageCommand;
import com.sippbox.bot.commands.manager.SlashCommandHandler;
import com.sippbox.bot.listeners.JoinListener;
import com.sippbox.bot.listeners.MessageListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import com.sippbox.bot.listeners.ReadyListener;

import java.util.*;

/**
 * This class is responsible for managing the JDA (Java Discord API) service.
 */
public class JdaService {

    private final String token;

    private final List<GatewayIntent> gatewayIntents = new ArrayList<>();
    private static JDA jda;

    /**
     * Constructor for the JdaService class.
     * @param token The token used to authenticate with the Discord API.
     */
    public JdaService(String token) {
        this.token = token;

        gatewayIntents.add(GatewayIntent.MESSAGE_CONTENT); // Intent for receiving messages
        gatewayIntents.add(GatewayIntent.GUILD_MEMBERS); // Intent for receiving guild member events

        start(gatewayIntents);

    }


    /**
     * This method is used to start the JDA service.
     * @param gatewayIntents the gatewayIntents to set - https://ci.dv8tion.net/job/JDA/javadoc/net/dv8tion/jda/api/requests/GatewayIntent.html. If you want none, just pass an empty List -> Collections.emptyList()
     */
    private void start(List<GatewayIntent> gatewayIntents) {
        try {
            jda = JDABuilder.createDefault(token)
                    .enableIntents(gatewayIntents)
                    .addEventListeners(new SlashCommandHandler(),
                            new ReadyListener(),
                            new MessageListener(),
                            new JoinListener())
                    .build();

            jda.addEventListener(new ReportMessageCommand());

            jda.awaitReady();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }



    /**
     * This method is used to get the JDA instance.
     * @return An Optional containing the JDA instance if it exists, otherwise an empty Optional.
     */
    public Optional<JDA> getJda() {
        return Optional.ofNullable(jda);
    }
}