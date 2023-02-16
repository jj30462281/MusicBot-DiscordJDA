package music.bot.classes.util;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import music.bot.classes.MusicPlayer;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

public class AudioLoadHandler implements AudioLoadResultHandler {
	private TextChannel channel;
	private MusicPlayer music_player;
	private Boolean isSearch;
	
	public AudioLoadHandler(MusicPlayer music_player, TextChannel channel, Boolean isSearch) {
		this.music_player = music_player;
		this.channel = channel;
		this.isSearch = isSearch;
	}

	@Override
	public void trackLoaded(AudioTrack track) {
		this.music_player.put_track(track);
		
		if(this.music_player.trackScheduler.queue.size() == 1) {
			this.music_player.play();
		}else this.channel.sendMessage(track.getInfo().title + " 已加入到播放清單").queue();
	}

	@Override
	public void playlistLoaded(AudioPlaylist playlist) {
		if(!isSearch) for(AudioTrack track : playlist.getTracks()) {
			this.music_player.put_track(track);
		}
		else this.music_player.put_track(playlist.getTracks().get(0));
		
		if(playlist.getTracks().size() > 1 && !isSearch) this.channel.sendMessage("已將音樂加入到播放清單").queue();
		else if(playlist.getTracks().size() == 1 || isSearch) this.channel.sendMessage(playlist.getTracks().get(0).getInfo().title + " 已加入到播放清單").queue();
		
		if((!isSearch && this.music_player.trackScheduler.queue.size() == playlist.getTracks().size()) 
		|| ( isSearch && this.music_player.trackScheduler.queue.size() == 1)) {
			this.music_player.play();
		}
	}

	@Override
	public void noMatches() {
		this.channel.sendMessage("找不到音樂").queue();
	}

	@Override
	public void loadFailed(FriendlyException exception) {
		this.channel.sendMessage("獲取音樂時發生錯誤錯誤\n```\n" + exception.getMessage() + "\n```").queue();
	}

}
