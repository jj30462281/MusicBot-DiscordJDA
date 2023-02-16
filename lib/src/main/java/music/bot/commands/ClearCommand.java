package music.bot.commands;

import music.bot.classes.MusicPlayerList;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;

public class ClearCommand {
	public static void execute(Message message) {
		Guild guild = message.getGuild();
		MusicPlayerList.get(guild.getId()).clear();
		message.reply("已清空播放列表").queue();
	}
}
