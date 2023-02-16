package music.bot.listener;

import music.bot.classes.MusicPlayerList;
import music.bot.commands.*;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class MessageListener extends ListenerAdapter
{
    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
    	Message message = event.getMessage();
    	Guild guild = event.getGuild();
    	String content = message.getContentDisplay();
    	
    	if(message.getAuthor().isBot()) return;
    	if(content.startsWith("mplay")) PlayCommand.execute(message);
    	
    	if(!MusicPlayerList.has(guild.getId())) return;
    	if(content.startsWith("mlist")) ListCommand.execute(message);
    	if(content.startsWith("mskip")) SkipCommand.execute(message);
    	if(content.startsWith("mloop")) LoopCommand.execute(message);
    	if(content.startsWith("mclear")) ClearCommand.execute(message);
    	if(content.startsWith("mleave")) LeaveCommand.execute(message);
    	if(content.startsWith("mpause")) PauseCommand.execute(message);
    	if(content.startsWith("mresume")) ResumeCommand.execute(message);
    	if(content.startsWith("mdelete")) DeleteCommand.execute(message);
    }
}
