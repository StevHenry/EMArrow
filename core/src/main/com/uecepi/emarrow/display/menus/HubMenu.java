package com.uecepi.emarrow.display.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Menu class to join hub queue.
 */
public class HubMenu extends ScreenMenu {

    Image titleImage;
    Table bottomTable;
    Table midTable;
    Table rightTable;
    Label severIP;
    TextButton queueButton;

    private boolean isInQueue = false;


    public HubMenu() {
        super();
        create();
    }

    private void create() {

        addTitle();
        addBottomTable();
        createMidTable();
        addRightTable();
        addQueueButton();
        addExitButton();

    }

    private void queueButtonPressed() {
        if(isInQueue){
            isInQueue = false;
            queueButton.setColor(new Color(0.5f,0.25f,1f,1f));
            queueButton.setText("Enter Queue");
        } else {
            isInQueue = true;
            queueButton.setColor(new Color(1f,0.25f,0f,1f));
            queueButton.setText("Exit Queue");
        }
    }

    /**
     * Called to receive pseudos of players in the Hub to display them.
     * @param pseudos
     */
    public void receive(String[] pseudos) {
        midTable.clear();
        for(String pseudo : pseudos) {
            Label l = new Label(pseudo, skin);
            midTable.add(l).left().row();
        }

        // Check if there are at least 2 players in the queue
        // if it is the case, then Emarrow.getInstance().setScreen(new GameScreen()); with the 2 players
    }

    /**
     * Called to send if the player is in the queue to server.
     */
    public void send() {
        // send to server : isInQueue + playerAccount (à trouver dans Engine)
    }

    // __________________ Extracted Methods __________________ //

    private void addBottomTable() {
        bottomTable = new Table();
        table.add(bottomTable);
        bottomTable.add(midTable);
        bottomTable.add(rightTable);
    }

    private void addRightTable() {
        rightTable = new Table();
        bottomTable.add(rightTable);
    }

    private void addExitButton() {
        TextButton exitButton;
        exitButton = new TextButton("Deconnection", skin);
        exitButton.setColor(new Color(1f,0.25f,0f,1f));
        rightTable.add(exitButton).height(80).width(200).padTop(20).row();
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
    }

    private void addQueueButton() {
        queueButton = new TextButton("Enter Queue", skin);
        queueButton.setColor(new Color(0.5f,0.25f,1f,1f));
        rightTable.add(queueButton).height(80).width(200).padTop(20).row();
        queueButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                queueButtonPressed();
            }
        });
    }

    private void addTitle() {
        titleImage = new Image(new Texture("images/title.png") );
        table.add(titleImage).row();

        // Modifier le 192.168.1.1 par Engine.getInstance().getServerIP()
        severIP = new Label("Server IP : " + "192.168.1.1", skin);
        table.add(severIP).padTop(20).padBottom(20).row();

    }

    private void createMidTable() {
        midTable = new Table();
        Pixmap bgPixmap = new Pixmap(1,1, Pixmap.Format.RGB565);
        bgPixmap.setColor(new Color(0.43f,0.43f,0.43f,1f));
        bgPixmap.fill();
        TextureRegionDrawable textureRegionDrawableBg = new TextureRegionDrawable(new TextureRegion(new Texture(bgPixmap)));
        midTable.setBackground(textureRegionDrawableBg);
        bottomTable.add(midTable).height(550).width(1000).padRight(100);
        bgPixmap.dispose();
    }


}
