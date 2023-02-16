package music.bot.commands;

import music.bot.classes.MusicPlayer;
import music.bot.classes.MusicPlayerList;
import music.bot.classes.util.AudioPlayerSendHandler;
import music.bot.util.GetTrack;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.middleman.AudioChannel;

public class PlayCommand {
	private static MusicPlayer music_player;
	private static AudioChannel voice_channel;
	
	public static void execute(Message message) {
		Guild guild = message.getGuild();
    	String content = message.getContentDisplay();
    	
    	if(content.equals("mplay")) {
    		message.reply("指令格式: mplay <關鍵字/連結>").queue();
    		return;
    	}
    	
    	if(!message.getMember().getVoiceState().inAudioChannel()) {
    		message.reply("請至一個語音聊天室再使用此指令").queue();
    		return;
    	}
    	
    	if(guild.getSelfMember().getVoiceState().inAudioChannel()) {
    		String bot_voice = guild.getSelfMember().getVoiceState().getChannel().getId();
    		String member_voice = message.getMember().getVoiceState().getChannel().getId();
    		if(!bot_voice.equals(member_voice)) {
    			message.reply("機器人正在其他頻道服務 請稍等").queue();
    			return;
    		}
    	}
    	voice_channel = message.getMember().getVoiceState().getChannel();
    	if(!guild.getAudioManager().isConnected()) {
    		guild.getAudioManager().openAudioConnection(voice_channel);
    		
    		music_player = new MusicPlayer(message.getChannel().asTextChannel());
    		guild.getAudioManager().setSendingHandler(new AudioPlayerSendHandler(music_player.player));
    		MusicPlayerList.put(guild.getId(), music_player);
    	}
    	
    	String query = content.replace("mplay ", "");
    	try {
    		GetTrack.execute(message, query, music_player);
    	}
    	catch(Exception err) {
    		System.out.println(err);
    	};
    }
}
