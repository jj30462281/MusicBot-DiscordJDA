package music.bot;

import net.dv8tion.jda.api.JDABuilder;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.io.IOException;

import org.apache.hc.core5.http.ParseException;

import music.bot.util.Login;

public class Main {
	public static void main(String args[]) {
		if(check_arg(args)) {
			System.out.println("Cannot find your token");
			return;
		}
		JDABuilder builder = JDABuilder.createDefault(args[0]);
		try {
			Login.login(builder);
		} catch (ParseException | SpotifyWebApiException | IOException e) {
			e.printStackTrace();
		}
		return;
	}
	
	static boolean check_arg(String args[]) {
		if(args.length < 1) return true;
		else return false;
	}
}
