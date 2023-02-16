package music.bot.listener;

import java.util.List;

import music.bot.classes.MusicPlayer;
import music.bot.classes.MusicPlayerList;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.middleman.AudioChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildListener extends ListenerAdapter {
	@Override
    public void onGuildVoiceUpdate(GuildVoiceUpdateEvent event) {
		if(!MusicPlayerList.has(event.getGuild().getId())) return;
		if(event.getChannelLeft() == null) return;
		
		MusicPlayer music_player = MusicPlayerList.get(event.getGuild().getId());
		AudioChannel voice_channel = music_player.get_VoiceChannel();
		if(!voice_channel.getId().equals(event.getChannelLeft().getId())) return;
		
        if(get_size(event.getChannelLeft().getMembers()) <= 0 || event.getChannelLeft().getId().equals(event.getJDA().getSelfUser().getId())) {
        	Guild guild = event.getGuild();
    		if(!MusicPlayerList.has(guild.getId())) return;
    		TextChannel channel = MusicPlayerList.get(guild.getId()).trackScheduler.channel;
    		MusicPlayerList.remove(guild.getId());
    		guild.getAudioManager().closeAudioConnection();
    		channel.sendMessage("再見！").queue();
    		return;
        }
    }
	
	public Integer get_size(List<Member> list) {
		Integer result = 0;
		for(Member member : list) {
			if(!member.getUser().isBot()) result++;
		}
		return result;
	}
}
