package inf112.skeleton.app.roborally.screens.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RemoveActorAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import inf112.skeleton.app.base.actors.Player;
import inf112.skeleton.app.base.cards.Card;
import inf112.skeleton.app.base.cards.CardDecks;
import inf112.skeleton.app.roborally.screens.RoboRallyGame;

import java.util.ArrayList;
import java.util.HashMap;

public class CardPhaseButtons {

    private final RoboRallyGame game;
    private final Stage stage;
    private CardDecks cardDecks;
    private HashMap<Card, Button> buttonsAndCards = new HashMap<>();
    private ArrayList<Card> allCards = new ArrayList<>();
    private ArrayList<Card> currentPlayerCards = new ArrayList<>();
    private float delay = 0f;
    private Skin skin;


    public CardPhaseButtons(RoboRallyGame game, CardDecks carddecks){
        this.game = game;
        this.cardDecks = carddecks;
        this.skin = new Skin(Gdx.files.internal("assets/roborally/skin/comic-ui.json"));
        stage = game.getStage();
    }

    /**
     * allows one player to choose cards
     */
    public void chooseCards(int nCards, Player player) {
        currentPlayerCards.clear();
        ArrayList<Card> availableCards = cardDecks.getCards(nCards);
        ArrayList<Card> selectedCards = new ArrayList<>();
        ArrayList<Boolean> usedslots = new ArrayList<>();
        for (int i = 0; i < nCards; i++) {
            usedslots.add(false);
        }

        //Creating a button for each card
        for (int i = 0; i < availableCards.size(); i++) {
            Card card = availableCards.get(i);
            int number = i;
            Texture testTexture = new Texture("assets/roborally/cards/movement/" + card.imageFileName());
            Drawable drawable = new TextureRegionDrawable(testTexture);
            Button button = new Button(drawable);

            button.setSize((int)(button.getWidth()/1.5), (int)(button.getHeight()/1.5));
            button.setPosition((int)(98*13/1.5) + 87 * i, 10);
            buttonsAndCards.put(card, button);
            game.getForeground().addActor(button);
            button.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent changeEvent, Actor actor) {
                    //System.out.println("klicked " + card);
                    if (!usedslots.get(number)) {
                        if (selectedCards.size() < 5) {
                            if (!selectedCards.contains(card)) {
                                //TODO: Currently placement will only work for two players.
                                if (allCards.size() < 5) {
                                    Actor currentCard = game.getForeground().getChildren().get(game.getForeground().getChildren().indexOf(button, false));
                                    int x = (int) game.getCardAreaSlots().get(0).getX()+3;
                                    int y = (int) game.getCardAreaSlots().get(0).getY()+4;
                                    currentCard.setPosition(x + selectedCards.size()*(currentCard.getWidth()+7), y);

                                } else {
                                    Actor currentCard = game.getForeground().getChildren().get(game.getForeground().getChildren().indexOf(button, false));
                                    int x = (int) game.getCardAreaSlots().get(1).getX()+3;
                                    int y = (int) game.getCardAreaSlots().get(1).getY()+4;
                                    currentCard.setPosition(x + selectedCards.size()*(currentCard.getWidth()+7), y);
                                }
                                selectedCards.add(card);
                                availableCards.remove(card);
                            }
                        }
                    }
                }
            });
        }
        //Make reset button
        TextButton reset = new TextButton("Reset Cards", skin);
        reset.setPosition(stage.getWidth()-200, 75);
        reset.setSize(200, 75);
        stage.addActor(reset);
        reset.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {

                if (selectedCards.size() > 0) {
                    currentPlayerCards.removeAll(selectedCards);

                    // remove the available cards from the screen
                    int j = 0;
                    for (Button btn: buttonsAndCards.values()) {
//                        stage.getActors().removeValue(buttonsAndCards.get(
//                                selectedCards.remove(0)), false);
                        //TODO: Resetting is broken. Player-cards for every player gets reset, and some kind of duplication is happening
                        game.getForeground().getChildren().get(game.getForeground().getChildren().indexOf(btn,false)).setPosition((int)(98*13/1.5) + 87 * j, 10);
                        j++;

                    }
                    availableCards.addAll(selectedCards);
                    selectedCards.clear();
                }
            }
        });
        //Make finish button
        TextButton finish = new TextButton("Set Cards", skin);
        finish.setPosition(stage.getWidth()-200, 0);
        finish.setSize(200, 75);
        stage.addActor(finish);



        finish.addListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                System.out.println("klicked finish");
                if(selectedCards.size() == 5) {
                    currentPlayerCards.addAll(selectedCards);
                    player.setCards(new ArrayList<>(currentPlayerCards));
                    allCards.addAll(player.getCards());
                    System.out.println("selected for "+player+" : " + currentPlayerCards);

                    // remove the available and selected cards from the screen
                    int count = availableCards.size();
                    for (int i = 0; i <count; i++) {
                        cardDecks.addUsed(availableCards.get(0));
                        stage.getActors().removeValue(buttonsAndCards.get(
                                availableCards.remove(0)), false);
                    }

                    // remove the finish button from the screen
                    stage.getActors().removeValue(finish, false);
                    stage.getActors().removeValue(reset,false);

//                    if (allCards.size() == 10) {
//                        float n = 0;
//                        for(Card card : allCards) {
//                            System.out.println(card);
//                            System.out.println(buttonsAndCards.keySet());
//                            stage.getActors().get(stage.getActors().indexOf(
//                                    buttonsAndCards.get(card),false)).addAction(new SequenceAction(
//                                    Actions.delay(n), Actions.fadeOut(3f), new RemoveActorAction()));
//
//                            n += 3f;
//                            // backup code to remove listeners from buttons
////                        stage.getActors().get(stage.getActors().indexOf(
////                                buttonsAndCards.get(card),false)).removeListener(buttonsAndCards.get(
////                                card).getListeners().get(0));
//                        }
//                        allCards.clear();
//                        buttonsAndCards = new HashMap<>();
//                    }

                    //continue game when finished selecting cards if there are no more players

                    game.doTurn();


                }else{
                    System.out.println("not enough cards");
                }
            }
        });
    }

    public void fadeCard(Card card) {
        game.getForeground().getChildren().get(game.getForeground().getChildren().indexOf(
                buttonsAndCards.get(card),false)).addAction(new SequenceAction(
                Actions.delay(delay), Actions.fadeOut(3f), new RemoveActorAction()));
        delay += 3f;

    }

    public void clear() {
        allCards.clear();
        delay = 0f;
    }
}
