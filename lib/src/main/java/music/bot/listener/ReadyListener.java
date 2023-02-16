package music.bot.listener;

import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.EventListener;

import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;

import music.bot.classes.MusicPlayerList;
import music.bot.util.Login;

public class ReadyListener implements EventListener
{
    @Override
    public void onEvent(GenericEvent event)
    {
        if (event instanceof ReadyEvent) {
        	AudioSourceManagers.registerRemoteSources(MusicPlayerList.playerManager);
            System.out.printf("API is ready with %s!\n", Login.jda.getSelfUser().getAsTag());
        }
    }
}