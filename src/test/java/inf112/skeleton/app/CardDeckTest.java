package inf112.skeleton.app;

import inf112.skeleton.app.base.actors.Player;
import inf112.skeleton.app.base.actors.Robot;
import inf112.skeleton.app.base.board.Board;
import inf112.skeleton.app.base.cards.Card;
import inf112.skeleton.app.base.cards.CardDecks;
import inf112.skeleton.app.base.cards.CardType;
import inf112.skeleton.app.base.utils.Direction;
import inf112.skeleton.app.base.utils.Pos;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CardDeckTest {

    @Test
    void executeTest() {
        Board board = new Board(10, 10);
        Player player = new Player("test");
        CardDecks decks = new CardDecks();
        player.setCards(decks.getCards(50));
        for (int i = 0; i < 45; i++) {
            decks.addUsed(player.useFirstCard());
            System.out.println("fresh "+decks.getFreshDeck().size());
            System.out.println("used "+decks.getUsedDeck().size());
            System.out.println("player "+player.getCards().size());
            System.out.println("---------");
        }

        player.setCards(decks.getCards(50));
        for (int i = 0; i < 50; i++) {
            decks.addUsed(player.useFirstCard());
        }

    }


}
