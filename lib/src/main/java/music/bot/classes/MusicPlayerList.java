package music.bot.classes;

import java.util.HashMap;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;

public class MusicPlayerList {
	public static HashMap<String, MusicPlayer> list = new HashMap<>();
	public static AudioPlayerManager playerManager = new DefaultAudioPlayerManager();
	
	public static void put(String id, MusicPlayer player) {
		list.put(id, player);
	}
	
	public static MusicPlayer get(String id) {
		return list.get(id);
	}
	
	public static Boolean has(String id) {
		return list.containsKey(id);
	}
	
	public static void remove(String id) {
		MusicPlayer music_player = list.get(id);
		music_player.player.removeListener(music_player.trackScheduler);
		music_player.player.destroy();
		list.remove(id);
	}
}
