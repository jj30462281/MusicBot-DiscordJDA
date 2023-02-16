package music.bot.commands;

import music.bot.classes.MusicPlayerList;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.emoji.Emoji;

public class DeleteCommand {
	public static void execute(Message message) {
		Guild guild = message.getGuild();
		String content = message.getContentDisplay();
		if(content.equals("mdelete")) {
			message.reply("指令格式: mdelete <數字>").queue();
			return;
		}
		
		String str = content.replace("mdelete ", "");
		if(isNumber(str)) {
			Integer num = Integer.parseInt(str);
			if(num < MusicPlayerList.get(guild.getId()).getQueue().size()) {
				MusicPlayerList.get(guild.getId()).delete(num);
				message.addReaction(Emoji.fromUnicode("✅")).queue();;
				return;
			}else {
				message.addReaction(Emoji.fromUnicode("❎")).queue();;
				return;
			}
		}else message.reply("指令格式: mdelete <數字>").queue();
	}
	
	public static boolean isNumber(String strNum) {
	    if (strNum == null) {
	        return false;
	    }
	    try {
	        Integer.parseInt(strNum);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	    return true;
	}
}
