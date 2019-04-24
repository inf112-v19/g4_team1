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
import inf112.skeleton.app.base.cards.CardType;
import inf112.skeleton.app.roborally.screens.RoboRallyGame;

import java.util.ArrayList;
import java.util.HashMap;

public class CardPhaseButtons {

    private final RoboRallyGame game;
    private final Stage stage;
    private CardDecks cardDecks;
    private HashMap<Card, Button> currentButtonsAndCards = new HashMap<>();
    private HashMap<Card, Button> allButtonsAndCards = new HashMap<>();
    private ArrayList<Card> allCards = new ArrayList<>();
    private ArrayList<Card> currentPlayerCards = new ArrayList<>();
    private float delay = 0f;
    private Skin skin;
    private final float CARD_FADE_TIME = 1f;

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
            button.setPosition((int)(98*15/1.5) + 87 * i, 10);
            currentButtonsAndCards.put(card, button);
            allButtonsAndCards.put(card,button);
            game.getForeground().addActor(button);
            button.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent changeEvent, Actor actor) {
                    //System.out.println("klicked " + card);
                    if (!usedslots.get(number)) {
                        if (selectedCards.size() < 5) {
                            if (!selectedCards.contains(card)) {

                                int placement = allCards.size()/5;
                                Actor currentCard = game.getForeground().getChildren().get(game.getForeground().getChildren().indexOf(button, false));
                                //finds the correct cardArea and sets the initial position(position of the leftmost slot in the cardArea)
                                int x = (int) game.getCardAreaSlots().get(placement).getX()+3;
                                int y = (int) game.getCardAreaSlots().get(placement).getY()+4;
                                //sets the position in the cardArea based on the amount of selected cards
                                currentCard.setPosition(x + selectedCards.size()*(currentCard.getWidth()+7), y);

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
        reset.setPosition(98 * 15 / 1.5f + 210, 130);
        reset.setSize(200, 75);
        stage.addActor(reset);
        reset.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {

                if (selectedCards.size() > 0) {
                    currentPlayerCards.removeAll(selectedCards);

                    // remove the available cards from the screen
                    int j = 0;
                    for (Button btn: currentButtonsAndCards.values()) {
//                        stage.getActors().removeValue(currentButtonsAndCards.get(
//                                selectedCards.remove(0)), false);
                        //TODO: Resetting is bugged. First time you reset will shuffle the cards at the bottom.
                        game.getForeground().getChildren().get(game.getForeground().getChildren().indexOf(btn,false)).setPosition((int)(98*15/1.5) + 87 * j, 10);
                        j++;

                    }
                    availableCards.addAll(selectedCards);
                    selectedCards.clear();


                }
            }
        });
        //Make finish button
        TextButton finish = new TextButton("Set Cards", skin);
        finish.setPosition(98 * 15 / 1.5f, 130);
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
                        game.getForeground().getChildren().removeValue(currentButtonsAndCards.get(
                                availableCards.remove(0)), false);
                    }

                    // remove the finish button from the screen
                    //stage.getActors().removeValue(finish, false);
                    //stage.getActors().removeValue(reset,false);

//                    if (allCards.size() == 10) {
//                        float n = 0;
//                        for(Card card : allCards) {
//                            System.out.println(card);
//                            System.out.println(currentButtonsAndCards.keySet());
//                            stage.getActors().get(stage.getActors().indexOf(
//                                    currentButtonsAndCards.get(card),false)).addAction(new SequenceAction(
//                                    Actions.delay(n), Actions.fadeOut(3f), new RemoveActorAction()));
//
//                            n += 3f;
//                            // backup code to remove listeners from buttons
////                        stage.getActors().get(stage.getActors().indexOf(
////                                currentButtonsAndCards.get(card),false)).removeListener(currentButtonsAndCards.get(
////                                card).getListeners().get(0));
//                        }
//                        allCards.clear();
//                        currentButtonsAndCards = new HashMap<>();
//                    }

                    //continue game when finished selecting cards if there are no more players

                    currentButtonsAndCards.clear();
                    game.doTurn();


                }else{
                    System.out.println("not enough cards");
                }
            }
        });
    }
    public void fadeCard(Card card) {
        System.out.println("fading with delay "+delay);

        game.getForeground().getChildren().get(game.getForeground().getChildren().indexOf(
                allButtonsAndCards.get(card),false)).addAction(new SequenceAction(
                Actions.delay(delay), Actions.fadeOut(CARD_FADE_TIME), new RemoveActorAction()));

    }

    public void clear() {
        allCards.clear();
        delay = 0f;
    }

    public void addDelay(float i) {
        System.out.println("adds delay "+i);
        delay+=i;
    }
}
