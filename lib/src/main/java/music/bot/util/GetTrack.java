package music.bot.util;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import music.bot.classes.MusicPlayer;
import net.dv8tion.jda.api.entities.Message;

public class GetTrack {
	public static void execute(Message message, String query, MusicPlayer music_player) throws URISyntaxException {
		if(!isUrl(query)) {
			music_player.search(query);
		}
		
		URI url = new URI(query);
		if(!HostIsSupport(url) && !FileIsSupport(url)) {
			message.reply("目前尚不支持此類型的URL").queue();
			return;
		}
		if(IsSpotify(url)) music_player.spotify(query, message);
		else music_player.get(query);
	}
	
	static boolean isUrl(String str) {
    	try {
    		new URI(str).toURL();
    		return str.startsWith("https://") || str.startsWith("http://");
    	}
    	catch(Exception e) {
    		return false;
    	}
    }
	
	static Boolean HostIsSupport(URI url) {
		List<String> list = Arrays.asList("youtube", "youtu.be", "soundcloud", "bandcamp", "vimeo", "twitch", "spotify");
		String host = url.getHost();
		for(String str : list) {
			if(host.matches(String.format("(.*)%s(.*)", str))) return true;
		}
		return false;
	}
	
	static Boolean FileIsSupport(URI url) {
		List<String> list = Arrays.asList(".mp3", "ogg", ".flac", ".wav", ".mkv", ".mp4", ".aac");
		String path = url.getPath();
		for(String str : list) {
			if(path.endsWith(str)) return true;
		}
		
		return false;
	}
	
	static Boolean IsSpotify(URI url) {
		String host = url.getHost();
		return host.matches("(.*)spotify(.*)");
	}
}
