package music.bot.classes;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import music.bot.classes.util.AudioLoadHandler;
import music.bot.classes.util.TrackScheduler;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

public class MusicPlayer {
	public TrackScheduler trackScheduler;
	public AudioPlayer player;
	
	public MusicPlayer(TextChannel channel) {
		this.player = MusicPlayerList.playerManager.createPlayer();
		
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
		MusicPlayerList.playerManager.loadItem("ytsearch:" + query, new AudioLoadHandler(this, this.trackScheduler.channel, true));
	}
	
	public void get(String url) {
		MusicPlayerList.playerManager.loadItem(url, new AudioLoadHandler(this, this.trackScheduler.channel, false));
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
}
