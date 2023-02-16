package music.bot.listener;

import music.bot.classes.MusicPlayerList;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildListener extends ListenerAdapter {
	@Override
    public void onGuildVoiceUpdate(GuildVoiceUpdateEvent event) {
		if(event.getChannelLeft() == null) return;
		
        if(event.getChannelLeft().getMembers().size() <= 1) {
        	Guild guild = event.getGuild();
    		if(!MusicPlayerList.has(guild.getId())) return;
    		TextChannel channel = MusicPlayerList.get(guild.getId()).trackScheduler.channel;
    		MusicPlayerList.remove(guild.getId());
    		guild.getAudioManager().closeAudioConnection();
    		channel.sendMessage("沒人拉 再見！").queue();
        }
    }
}
