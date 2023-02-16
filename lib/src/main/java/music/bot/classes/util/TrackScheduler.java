package music.bot.classes.util;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

public class TrackScheduler extends AudioEventAdapter {
	  private final AudioPlayer player;
	  public Boolean loop;
	  
	  public final BlockingQueue<AudioTrack> queue;
	  public final TextChannel channel;
	  
	  public TrackScheduler(AudioPlayer player, TextChannel channel) {
		  this.loop = false;
		  this.channel = channel;
		  this.player = player;
		  this.queue = new LinkedBlockingQueue<>();
	  }
	  
	  @Override
	  public void onPlayerPause(AudioPlayer player) {
	    // Player was paused
	  }

	  @Override
	  public void onPlayerResume(AudioPlayer player) {
	    // Player was resumed
	  }

	  @Override
	  public void onTrackStart(AudioPlayer player, AudioTrack track) {
		  if(!this.loop) this.channel.sendMessage("正在播放: " + track.getInfo().title).queue();
	  }

	  @Override
	  public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
		  if (endReason.mayStartNext || endReason.name() == "STOPPED") {
			  if(this.loop && endReason.name() != "STOPPED") {
				  player.playTrack(track.makeClone());
			  }
	    	
			  else {
				  if(this.queue.size() != 0) player.playTrack(this.queue.poll());
				  else this.channel.sendMessage("音樂已全數播放完畢").queue();
			  }
		  }

	    // endReason == FINISHED: A track finished or died by an exception (mayStartNext = true).
	    // endReason == LOAD_FAILED: Loading of a track failed (mayStartNext = true).
	    // endReason == STOPPED: The player was stopped.
	    // endReason == REPLACED: Another track started playing while this had not finished
	    // endReason == CLEANUP: Player hasn't been queried for a while, if you want you can put a
	    //                       clone of this back to your queue
	  }

	  @Override
	  public void onTrackException(AudioPlayer player, AudioTrack track, FriendlyException exception) {
		  this.channel.sendMessage("播放音樂時發生錯誤\n```\n" + exception.getMessage() + "\n```").queue();
		  this.player.playTrack(this.queue.poll());
	  }

	  @Override
	  public void onTrackStuck(AudioPlayer player, AudioTrack track, long thresholdMs) {
		  this.channel.sendMessage("此音樂無任何內容 跳過").queue();
		  this.player.playTrack(this.queue.poll());
	  }
}