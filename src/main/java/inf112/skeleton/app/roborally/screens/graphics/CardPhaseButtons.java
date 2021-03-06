package inf112.skeleton.app.roborally.screens.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
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
import com.badlogic.gdx.utils.Timer;
import inf112.skeleton.app.base.actors.AI;
import inf112.skeleton.app.base.actors.Player;
import inf112.skeleton.app.base.cards.Card;
import inf112.skeleton.app.base.cards.CardDecks;
import inf112.skeleton.app.base.cards.CardType;
import inf112.skeleton.app.roborally.screens.RoboRallyGame;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class that contains main logic of cards
 */
public class CardPhaseButtons {
    private final RoboRallyGame game;
    private final Stage stage;
    private CardDecks cardDecks;
    private HashMap<Card, Button> currentButtonsAndCards = new HashMap<>();
    private HashMap<Card, Button> allButtonsAndCards = new HashMap<>();
    private ArrayList<Card> currentPlayerCards = new ArrayList<>();
    private float delay = 0f;
    private Skin skin;
    private int allowedCards;
    private int placement;

    public CardPhaseButtons(RoboRallyGame game, CardDecks carddecks) {
        this.game = game;
        this.cardDecks = carddecks;
        this.skin = new Skin(Gdx.files.internal("assets/roborally/skin/comic-ui.json"));
        stage = game.getStage();
    }

    /**
     * Allows one player to choose cards
     */
    public void chooseCards(int nCards, Player player, boolean isPoweredDown) {
        currentPlayerCards.clear();
        allowedCards = 5;
        placement = game.getPlayerPos(player);

        if (nCards < 5) {
            allowedCards = player.getRobot().getHealth() - 1;
            nCards = 5;
        }

        if (isPoweredDown) {
            nCards = 0;
            allowedCards = 0;
            player.getRobot().maxHealth();
        }

        ArrayList<Card> availableCards = cardDecks.getCards(nCards);
        ArrayList<Card> selectedCards = new ArrayList<>();
        ArrayList<Button> buttonList = new ArrayList<>();

        // Creating a button for each card
        for (int i = 0; i < availableCards.size(); i++) {
            Card card = availableCards.get(i);
            Texture testTexture = new Texture("assets/roborally/cards/movement/" + card.imageFileName());
            Drawable drawable = new TextureRegionDrawable(testTexture);
            Button button = new Button(drawable);
            button.setSize((int) (button.getWidth() / 1.5), (int) (button.getHeight() / 1.5));
            button.setPosition((int) (96 * 17 / 1.5) + 87 * i, 10);
            currentButtonsAndCards.put(card, button);
            allButtonsAndCards.put(card, button);
            buttonList.add(button);
            game.getForeground().addActor(button);

            button.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent changeEvent, Actor actor) {
                    if (selectedCards.size() < allowedCards) {
                        if (!selectedCards.contains(card)) {
                            Actor currentCard = game.getForeground().getChildren().get(
                                    game.getForeground().getChildren().indexOf(button, false));

                            //finds the correct cardArea and sets the initial position(position of the leftmost slot in the cardArea)
                            int x = (int) game.getCardAreaSlots().get(placement).getX() + 3;
                            int y = (int) game.getCardAreaSlots().get(placement).getY() + 4;

                            //sets the position in the cardArea based on the amount of selected cards
                            currentCard.setPosition(x + selectedCards.size() * (currentCard.getWidth() + 7), y);

                            selectedCards.add(card);
                            availableCards.remove(card);
                        }
                    }
                }

            });
        }

        // Make reset button
        TextButton reset = new TextButton("Reset Cards", skin);
        reset.setPosition(96 * 17 / 1.5f + 190, 130);
        reset.setSize(215, 75);
        stage.addActor(reset);

        reset.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                if (selectedCards.size() > 0) {
                    currentPlayerCards.removeAll(selectedCards);
                    // remove the available cards from the screen
                    int j = 0;
                    for (Button btn : buttonList) {
                        game.getForeground().getChildren().get(game.getForeground().getChildren().indexOf(
                                btn, false)).setPosition((int) (96 * 17 / 1.5) + 87 * j, 10);
                        j++;
                    }
                    availableCards.addAll(selectedCards);
                    selectedCards.clear();
                }
            }
        });

        //powerdown buton
        TextButton powerDownButton = new TextButton("announce powerdown", skin);
        if (isPoweredDown) powerDownButton.setText("continue powerdown");
        powerDownButton.setPosition(96 * 17 / 1.5f + 430, 130);
        powerDownButton.setSize(350, 75);
        stage.addActor(powerDownButton);

        powerDownButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                if (player.getPowerDown() != 2) {
                    player.setPowerDown(2);
                    player.getPowerButton().setDrawable(new TextureRegionDrawable(new TextureRegion(
                            new Texture("assets/roborally/power_down_active.png"))));
                } else {
                    player.getPowerButton().setDrawable(new TextureRegionDrawable(new TextureRegion(
                            new Texture("assets/roborally/power_down.png"))));
                    player.setPowerDown(-1);
                }
            }
        });

        //Make finish button
        TextButton finish = new TextButton("Set Cards", skin);
        if (isPoweredDown) finish.setText("continue turn");

        finish.setPosition(96 * 16 / 1.5f, 130);
        finish.setSize(215, 75);
        stage.addActor(finish);

        finish.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                player.setPowerDown(player.getPowerDown() - 1);
                if (isPoweredDown) {
                    //add power down card
                    Card card = new Card(CardType.POWERDOWN, -1);
                    Texture testTexture = new Texture("assets/roborally/cards/movement/" + card.imageFileName());
                    Drawable drawable = new TextureRegionDrawable(testTexture);
                    Button button = new Button(drawable);
                    button.setSize((int) (button.getWidth() / 1.5), (int) (button.getHeight() / 1.5));
                    selectedCards.add(card);
                    currentPlayerCards.addAll(selectedCards);
                    player.setCards(new ArrayList<>(currentPlayerCards));

                    // remove the available and selected cards from the screen
                    int count = availableCards.size();
                    for (int i = 0; i < count; i++) {
                        cardDecks.addUsed(availableCards.get(0));
                        game.getForeground().getChildren().removeValue(currentButtonsAndCards.get(
                                availableCards.remove(0)), false);
                    }

                    // remove the finish button from the screen
                    stage.getActors().removeValue(finish, false);
                    stage.getActors().removeValue(reset, false);
                    stage.getActors().removeValue(powerDownButton, false);

                    currentButtonsAndCards.clear();
                    game.doTurn();
                    return;
                }

                if (selectedCards.size() == allowedCards) {
                    currentPlayerCards.addAll(selectedCards);

                    // TODO SEND THE LIST OF CARDS HERE TO THE SERVER THEN IT SHOULD SEND IT TO OTHER PLAYERS

                    player.setCards(new ArrayList<>(currentPlayerCards));

                    // remove the available and selected cards from the screen
                    int count = availableCards.size();
                    for (int i = 0; i < count; i++) {
                        cardDecks.addUsed(availableCards.get(0));
                        game.getForeground().getChildren().removeValue(currentButtonsAndCards.get(
                                availableCards.remove(0)), false);
                    }

                    // remove the finish button from the screen
                    stage.getActors().removeValue(finish, false);
                    stage.getActors().removeValue(reset, false);
                    stage.getActors().removeValue(powerDownButton, false);

                    currentButtonsAndCards.clear();
                    game.hideCards(player);
                    game.doTurn();
                }
            }
        });

        //choose cards automatically if AI-Player
        if (player instanceof AI) {
            Timer timer = new Timer();
            Timer.Task task = new Timer.Task() {
                @Override
                public void run() {
                    InputEvent event1 = new InputEvent();
                    event1.setType(InputEvent.Type.touchDown);
                    InputEvent event2 = new InputEvent();
                    event2.setType(InputEvent.Type.touchUp);

                    if (isPoweredDown) {
                        finish.fire(event1);
                        finish.fire(event2);
                    }

                    else if (player.getRobot().getHealth() == 1) {
                        powerDownButton.fire(event1);
                        powerDownButton.fire(event2);
                        finish.fire(event1);
                        finish.fire(event2);
                    }

                    else {
                        while (selectedCards.size() < allowedCards) {
                            currentButtonsAndCards.get(availableCards.get(0)).fire(event1);
                            currentButtonsAndCards.get(availableCards.get(0)).fire(event2);
                        }
                        finish.fire(event1);
                        finish.fire(event2);
                    }
                }
            };

            timer.scheduleTask(task, 0.5f);
        }
    }

    public void fadeCard(Card card) {
        if (card.getType() == CardType.POWERDOWN) return;

        float CARD_FADE_TIME = 1f;
        game.getForeground().getChildren().get(game.getForeground().getChildren().indexOf(
                allButtonsAndCards.get(card), false)).addAction(new SequenceAction(
                Actions.delay(delay), Actions.fadeOut(CARD_FADE_TIME), new RemoveActorAction()));

    }

    public void clear() {
        delay = 0f;
    }

    public void addDelay(float i) {
        delay += i;
    }
}
