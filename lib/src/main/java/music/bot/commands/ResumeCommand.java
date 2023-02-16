package music.bot.commands;

import music.bot.classes.MusicPlayerList;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;

public class ResumeCommand {
	public static void execute(Message message) {
		Guild guild = message.getGuild();
		MusicPlayerList.get(guild.getId()).resume();
	}
}
