package com.uecepi.emarrow.display.menus;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.uecepi.emarrow.GameEngine;
import com.uecepi.emarrow.display.Screens;
import com.uecepi.emarrow.network.account.PlayerDataPacket;
import com.uecepi.emarrow.network.game.update.ConnectionResultPacket;

/**
 * Menu class to connect to server.
 */
public class ConnectionMenu extends ScreenMenu {

    private Table secondTable;
    private Table fieldsTable;
    private TextField connection;
    private TextButton connectionButton;
    private Label title;
    private Label connectionLabel;
    private Label errorMessage;
    private String lastIP = "127.0.0.1";

    public ConnectionMenu() {
        super();
    }

    @Override
    protected void create() {
        createBackGroundTable();
        addTitle();
        addFieldsTable();
        addConnectionButton();
    }

    /**
     * Method called to check the account before connecting to server.
     */
    private void connection() {
        GameEngine gameEngine = GameEngine.getInstance();
        if (!gameEngine.getSelfClient().connect(connection.getText())) {
            addErrorMessage("Server unreachable! Please check the address and ports!");
        } else {
            gameEngine.getSelfClient().sendTCP(new PlayerDataPacket(gameEngine.getSelfPlayer().getUuid(),
                    gameEngine.getSelfPlayer().getName()));
        }
    }

    /**
     * Adding message error because of a failed connection to server.
     *
     * @param error Error created details
     */
    public void addErrorMessage(String error) {
        errorMessage = new Label("Connection to server failed. %s".formatted(error), skin);
        errorMessage.setColor(new Color(1f, 0.5f, 0f, 1f));
        secondTable.add(errorMessage);
        secondTable.padTop(5).row();

    }
    // __________________ Extracted Methods __________________ //

    private void addConnectionButton() {
        connectionButton = new TextButton("Connection", skin);
        connectionButton.setColor(new Color(1f, 1f, 0f, 1f));
        secondTable.add(connectionButton).height(80).width(200).padTop(40).row();
        connectionButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                connection();
            }
        });
    }

    private void addFieldsTable() {
        fieldsTable = new Table();
        secondTable.add(fieldsTable).padTop(40).row();

        connectionLabel = new Label("Connection IP :", skin);
        fieldsTable.add(connectionLabel).padRight(10);
        connection = new TextField(lastIP, skin);
        fieldsTable.add(connection).height(40).width(200).padTop(20).row();
    }

    private void addTitle() {
        title = new Label("Connection Menu", skin);
        secondTable.add(title).row();
    }

    private void createBackGroundTable() {
        secondTable = new Table();
        Pixmap bgPixmap = new Pixmap(1, 1, Pixmap.Format.RGB565);
        bgPixmap.setColor(new Color(0.43f, 0.43f, 0.43f, 1f));
        bgPixmap.fill();
        TextureRegionDrawable textureRegionDrawableBg = new TextureRegionDrawable(new TextureRegion(new Texture(bgPixmap)));
        secondTable.setBackground(textureRegionDrawableBg);
        table.add(secondTable).height(300).width(400);
        bgPixmap.dispose();
    }

    public void responseReceived(ConnectionResultPacket.ResultMeaning meaning) {
        Gdx.app.log("connection_menu", "Result=%s".formatted(meaning.name()));
        if (meaning == ConnectionResultPacket.ResultMeaning.ACCEPTED) {
            Gdx.app.log("connection_menu", "Client logged in!");
            lastIP = connection.getText();
            GameEngine gameEngine = GameEngine.getInstance();
            Gdx.app.postRunnable(() -> {
                //Resets the inputs
                gameEngine.getInputManager().resetInputs();
                //Resets the game
                gameEngine.startGame();
                //Shows the Game Screen
                Screens.setScreen(Screens.GAME_SCREEN);
            });
        } else {
            addErrorMessage(meaning.name());
        }
    }
}
