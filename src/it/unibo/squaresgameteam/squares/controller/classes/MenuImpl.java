package it.unibo.squaresgameteam.squares.controller.classes;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import it.unibo.squaresgameteam.squares.controller.enumerations.TypeGame;
import it.unibo.squaresgameteam.squares.controller.interfaces.Match;
import it.unibo.squaresgameteam.squares.controller.interfaces.Menu;
import it.unibo.squaresgameteam.squares.controller.interfaces.ShowRanking;
import it.unibo.squaresgameteam.squares.model.exceptions.DuplicatedPlayerStatsException;

/**
 * 
 * @author Licia Valentini
 * 
 *         This class manages the start Menu.
 *
 */
public class MenuImpl implements Menu {

    private String rules;

    @Override
    public Match createMatch(final int columsNumber, final int rowsNumber, final String namePlayer1,
            final String namePlayer2, final TypeGame mode) {
        return new MatchImpl(columsNumber, rowsNumber, namePlayer1, namePlayer2, mode);

    }

    @Override
    public String showRules() throws IOException {

        final String txtDirectory = ClassLoader.class.getResource("/Rules.txt").getPath();
        // final FileReader in = new FileReader(txtDirectory);

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(txtDirectory), "UTF8"))) {
            String s;
            while ((s = br.readLine()) != null) {
                System.out.println(s);
                this.rules = this.rules + s;

            }
        } catch (IOException ex) {
            throw new IOException();
        }

        return this.rules;

    }

    @Override
    public ShowRanking createRankingClass() throws DuplicatedPlayerStatsException {
        return new ShowRankingImpl();
    }

    @Override
    public void exitApp() {
        System.exit(0);
    }

}
