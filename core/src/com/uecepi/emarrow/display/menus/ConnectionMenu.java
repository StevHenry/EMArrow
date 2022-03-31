package com.uecepi.emarrow.display.menus;


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
import com.uecepi.emarrow.Emarrow;
import com.uecepi.emarrow.GameScreen;

/**
 * Menu class to connect to server.
 */
public class ConnectionMenu extends ScreenMenu {

    Table secondTable;
    Table fieldsTable;
    TextField connection;
    TextButton connectionButton;
    Label title;
    Label connectionLabel;

    public ConnectionMenu(){
        super();
        create();
    }

    private void create() {
        createBackGroundTable();
        addTitle();
        addFieldsTable();
        addSignInButton();
    }

    /**
     * Method called to check the account before connecting to server.
     */
    private void connection() {

        Emarrow.getInstance().setScreen(new GameScreen());
    }


    // __________________ Extracted Methods __________________ //

    private void addSignInButton() {
        connectionButton = new TextButton("Connection", skin);
        connectionButton.setColor(new Color(1f,1f,0f,1f));
        secondTable.add(connectionButton).height(80).width(200).padTop(40).row();
        connectionButton.addListener(new ClickListener(){
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
        connection = new TextField("", skin);
        fieldsTable.add(connection).height(40).width(200).padTop(20).row();
    }

    private void addTitle() {
        title = new Label("Connection Menu", skin);
        secondTable.add(title).row();
    }

    private void createBackGroundTable() {
        secondTable = new Table();
        Pixmap bgPixmap = new Pixmap(1,1, Pixmap.Format.RGB565);
        bgPixmap.setColor(new Color(0.43f,0.43f,0.43f,1f));
        bgPixmap.fill();
        TextureRegionDrawable textureRegionDrawableBg = new TextureRegionDrawable(new TextureRegion(new Texture(bgPixmap)));
        secondTable.setBackground(textureRegionDrawableBg);
        table.add(secondTable).height(270).width(400);
        bgPixmap.dispose();
    }
}

