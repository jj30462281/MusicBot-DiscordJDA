package music.bot.commands;

import music.bot.classes.MusicPlayerList;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;

public class LeaveCommand {
	public static void execute(Message message) {
		Guild guild = message.getGuild();
		MusicPlayerList.remove(guild.getId());
		guild.getAudioManager().closeAudioConnection();
		message.reply("閃人拉！").queue();
	}
}
