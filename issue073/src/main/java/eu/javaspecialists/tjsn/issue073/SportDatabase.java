package eu.javaspecialists.tjsn.issue073;

/**
 * This represents some database that retrieves the Players from
 * a backend database.
 */
public interface SportDatabase {
    Player[] getPlayers();
}