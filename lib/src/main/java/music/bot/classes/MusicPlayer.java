package music.bot.classes;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import music.bot.classes.util.AudioLoadHandler;
import music.bot.classes.util.TrackScheduler;
import music.bot.util.GetSpotify;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.middleman.AudioChannel;
import net.dv8tion.jda.api.entities.channel.unions.AudioChannelUnion;

public class MusicPlayer {
	public TrackScheduler trackScheduler;
	public AudioChannel voice_channel;
	public AudioPlayer player;
	
	public MusicPlayer(TextChannel channel, AudioChannelUnion voice_channel) {
		this.player = MusicPlayerList.playerManager.createPlayer();
		this.voice_channel = voice_channel;
		this.trackScheduler = new TrackScheduler(player, channel);
		player.addListener(this.trackScheduler);
	}
	
	public AudioPlayer getPlayer() {
		return this.player;
	}
	
	public TrackScheduler getTrackScheduler() {
		return this.trackScheduler;
	}
	
	public BlockingQueue<AudioTrack> getQueue() {
		return this.trackScheduler.queue;
	}
	
	public void search(String query) {
		MusicPlayerList.playerManager.loadItem("ytsearch:" + query, new AudioLoadHandler(this, this.trackScheduler.channel, true, false));
	}
	
	public void get(String url) {
		MusicPlayerList.playerManager.loadItem(url, new AudioLoadHandler(this, this.trackScheduler.channel, false, false));
	}
	
	public void put_track(AudioTrack track) {
		this.trackScheduler.queue.add(track);
	}
	
	public void play() {
		this.player.playTrack(this.trackScheduler.queue.poll());
	}
	
	public void clear() {
		this.trackScheduler.queue.clear();
	}
	
	public void loop() {
		this.trackScheduler.loop = !this.trackScheduler.loop;
	}
	
	public Boolean getLoop() {
		return this.trackScheduler.loop;
	}

	public void pause() {
		this.player.setPaused(true);
	}

	public void resume() {
		this.player.setPaused(false);
	}
	
	public TextChannel get_TextChannel() {
		return this.trackScheduler.channel;
	}
	
	public AudioChannel get_VoiceChannel() {
		return this.voice_channel;
	}
	
	public void delete(Integer num) {
		try {
			BlockingQueue<AudioTrack> cache = new LinkedBlockingQueue<>();
			for(Integer a = 0; a < num-1 ; a++) {
				cache.put(this.trackScheduler.queue.poll());
			}
			this.trackScheduler.queue.poll();
			while(this.trackScheduler.queue.size() > 0) {
				cache.put(this.trackScheduler.queue.poll());
			}
			while(cache.size() > 0) {
				this.trackScheduler.queue.put(cache.poll());
			}
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}

	public void spotify(String query, Message message) {
		List<String> list = GetSpotify.request(query);
		if(list.size() == 0) {
			message.reply("找不到此音樂").queue();
			return;
		}
		
		for(String str : list) MusicPlayerList.playerManager.loadItem(str, new AudioLoadHandler(this, this.trackScheduler.channel, false, list.size() != 1));
		
		if(list.size() > 1) {
			message.getChannel().sendMessage("已將音樂加入到播放清單").queue();
		}
	}
}
