package music.bot.commands;

import music.bot.classes.MusicPlayerList;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;

public class LoopCommand {
	public static void execute(Message message) {
		Guild guild = message.getGuild();
		MusicPlayerList.get(guild.getId()).loop();
		String str;
		if(MusicPlayerList.get(guild.getId()).getLoop()) {
			str = "已開啟循環播放";
		}else {
			str = "已關閉循環播放";
		}
		message.reply(str).queue();
	}
}
