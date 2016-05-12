package model.classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import model.enumerations.RankingOption;
import model.interfaces.Player;
import model.interfaces.Ranking;

/**
 * This class implements the interface Ranking. It is used to manage the ranking
 * system and to set the player's last match results.
 */
public class RankingImpl implements Ranking {

    private final List<Player> playerList;

    /**
     * The consctructor is used to wkow the old players' matches results and
     * upate them if it is necessary.
     * 
     * @param playerList the old list of the players' ranking
     * @throws an IllegalArgumentException if the playerList contains two or more players with the same name
     */
    // CHECKSTYLE:OFF:
    public RankingImpl(final List<Player> playerList) {
        // CHECKSTYLE:ON:
        final List<String> playerNameList = new ArrayList<>();
        final Set<String> playerNameSet = new HashSet<>();
        for (final Player player : playerList) {
            playerNameList.add(player.getPlayerName());
            playerNameSet.add(player.getPlayerName());
        }
        if (playerNameList.size() > playerNameSet.size()) {
            throw new IllegalArgumentException();
        }
        this.playerList = playerList;
    }

    @Override
    public void addPlayerResults(final String playerName, final boolean victory, final Integer totalPointsScored) {
        for (final Player player : playerList) {
            if (player.getPlayerName().equals(playerName)) {
                addLastMatchResults(player, victory, totalPointsScored);
                return;
            }
        }
        final PlayerImpl newPlayer = new PlayerImpl();
        newPlayer.setPlayerName(playerName);
        playerList.add(newPlayer);
        addLastMatchResults(newPlayer, victory, totalPointsScored);

    }

    private void addLastMatchResults(final Player player, final boolean victory, final Integer totalPointsScored) {
        player.setTotalMatches(player.getTotalMatches() + 1);
        if (victory) {
            player.setWonMatches(player.getTotalWins() + 1);
        }
        player.setTotalPointsScored(player.getTotalPointsScored() + totalPointsScored);
    }

    @Override
    public List<Player> orderListBy(final RankingOption option, final boolean reverseRanking) {
        switch (option) {
        case WINRATE:
            playerList.sort(new Comparator<Player>() {

                @Override
                public int compare(final Player player1, final Player player2) {
                    if (compareWinrate(player1, player2).equals(0)) {
                        if (compareTotalMatches(player1, player2).equals(0)) {
                            if (compareTotalPointsScored(player1, player2).equals(0)) {
                                return compareNames(player1, player2);
                            }
                            return compareTotalPointsScored(player1, player2);
                        }
                        return compareTotalMatches(player1, player2);
                    }
                    return compareWinrate(player1, player2);
                }
            });
            break;
        case TOTAL_WINS:
            playerList.sort(new Comparator<Player>() {

                @Override
                public int compare(final Player player1, final Player player2) {
                    if (compareTotalWins(player1, player2).equals(0)) {
                        if (compareWinrate(player1, player2).equals(0)) {
                            if (compareTotalPointsScored(player1, player2).equals(0)) {
                                return compareNames(player1, player2);
                            }
                            return compareTotalPointsScored(player1, player2);
                        }
                        return compareWinrate(player1, player2);
                    }
                    return compareTotalWins(player1, player2);
                }
            });
            break;
        case TOTAL_MATCHES:
            playerList.sort(new Comparator<Player>() {

                @Override
                public int compare(final Player player1, final Player player2) {
                    if (compareTotalMatches(player1, player2).equals(0)) {
                        if (compareWinrate(player1, player2).equals(0)) {
                            if (compareTotalPointsScored(player1, player2).equals(0)) {
                                return compareNames(player1, player2);
                            }
                            return compareTotalPointsScored(player1, player2);
                        }
                        return compareWinrate(player1, player2);
                    }
                    return compareTotalMatches(player1, player2);
                }
            });
            break;
        case TOTAL_POINTS_SCORED:
            playerList.sort(new Comparator<Player>() {

                @Override
                public int compare(final Player player1, final Player player2) {
                    if (compareTotalPointsScored(player1, player2).equals(0)) {
                        if (compareWinrate(player1, player2).equals(0)) {
                            if (compareTotalMatches(player1, player2).equals(0)) {
                                return compareNames(player1, player2);
                            }
                            return compareTotalMatches(player1, player2);
                        }
                        return compareWinrate(player1, player2);
                    }
                    return compareTotalPointsScored(player1, player2);
                }
            });
            break;
        default:
            throw new IllegalArgumentException();
        }
        if (reverseRanking) {
            Collections.reverse(playerList);
        }
        return Collections.unmodifiableList(playerList);
    }

    private Integer compareWinrate(final Player player1, final Player player2) {
        return Double.compare(player1.getWinRate(), player2.getWinRate());
    }

    private Integer compareTotalMatches(final Player player1, final Player player2) {
        return Integer.compare(player1.getTotalMatches(), player2.getTotalMatches());
    }

    private Integer compareTotalWins(final Player player1, final Player player2) {
        return Integer.compare(player1.getTotalWins(), player2.getTotalWins());
    }

    private Integer compareTotalPointsScored(final Player player1, final Player player2) {
        return Integer.compare(player1.getTotalPointsScored(), player2.getTotalPointsScored());
    }

    private Integer compareNames(final Player player1, final Player player2) {
        return player1.getPlayerName().compareTo(player2.getPlayerName());
    }

}
