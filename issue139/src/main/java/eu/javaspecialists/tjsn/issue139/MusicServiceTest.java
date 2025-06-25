package eu.javaspecialists.tjsn.issue139;

import java.util.*;

public class MusicServiceTest {
    public static void main(String... args) {
        ServiceLoader<MusicService> musicServices =
                ServiceLoader.load(MusicService.class);
        for (MusicService musicService : musicServices) {
            System.out.println(musicService.getClass());
            List<String> titles = musicService.getTitleList();
            for (String title : titles) {
                musicService.play(title);
            }
        }
    }
}
