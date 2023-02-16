package music.bot.util;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

import music.bot.listener.*;

public class Login {
	public static JDA jda;
	public static void login(JDABuilder builder) {
		builder.enableIntents(GatewayIntent.MESSAGE_CONTENT);
		
		builder.addEventListeners(new ReadyListener());
		builder.addEventListeners(new MessageListener());
		builder.addEventListeners(new GuildListener());
		
		try {
			jda = builder.build();
		}
		catch(Exception e) {
			System.out.println("Token Wrong\n");
			return;
		}
	}
}
