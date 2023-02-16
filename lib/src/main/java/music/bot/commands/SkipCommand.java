package music.bot.commands;

import music.bot.classes.MusicPlayer;
import music.bot.classes.MusicPlayerList;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;

public class SkipCommand {
	public static void execute(Message message) {
		Guild guild = message.getGuild();
		MusicPlayer music_player = MusicPlayerList.get(guild.getId());
		music_player.getPlayer().stopTrack();
	}
}
