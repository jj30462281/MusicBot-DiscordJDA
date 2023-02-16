package music.bot.util;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.io.IOException;

import org.apache.hc.core5.http.ParseException;

import music.bot.listener.*;

public class Login {
	public static JDA jda;
	public static void login(JDABuilder builder) throws ParseException, SpotifyWebApiException, IOException {
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
