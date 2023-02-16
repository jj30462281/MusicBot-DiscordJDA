package music.bot.commands;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import music.bot.classes.MusicPlayer;
import music.bot.classes.MusicPlayerList;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;

public class ListCommand {
	public static void execute(Message message) {
		Guild guild = message.getGuild();
		MusicPlayer music_player = MusicPlayerList.get(guild.getId());
		Integer num = 1;
		
		String reply = "";
		if(music_player.player.getPlayingTrack() != null)
			reply += String.format("正在播放: %s\n\n", music_player.player.getPlayingTrack().getInfo().title);
		
		for(AudioTrack track : music_player.getQueue()) {
			if(reply.length() < 1900) reply += String.format("%d.%s\n", num, track.getInfo().title);
			else break;
			
			num++;
		}
		if(num != music_player.getQueue().size()+1) reply += String.format("......與其他%d項", music_player.getQueue().size() - num + 1);
		if(reply.length() == 0) reply += "沒有音樂在隊列中了";
		message.reply(reply).queue();
	}
}
