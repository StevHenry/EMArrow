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

/**
 * Menu class to log in.
 */
public class LogInMenu extends ScreenMenu {

    Table secondTable;
    Table fieldsTable;
    TextField id;
    TextField password;
    TextButton logInButton;
    Label title;
    Label idLabel;
    Label passwordLabel;
    Label errorMessage;

    public LogInMenu(){
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
     * Method called to check the account before logging in.
     */
    private void logIn() {

        Emarrow.getInstance().setScreen(new ConnectionMenu());
    }

    /**
     * Adding message error because of a wrong ID/Password.
     */
    public void addErrorMessage(){
        errorMessage = new Label("Wrong ID or Password", skin);
        errorMessage.setColor(new Color(1f,0.5f,0f,1f));
        secondTable.add(errorMessage);
        secondTable.padTop(5).row();
    }

    // __________________ Extracted Methods __________________ //

    private void addSignInButton() {
        logInButton = new TextButton("Log In", skin);
        logInButton.setColor(new Color(0.5f,0.25f,1f,1f));
        secondTable.add(logInButton).height(80).width(200).padTop(40).row();
        logInButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                logIn();
            }
        });
    }

    private void addFieldsTable() {
        fieldsTable = new Table();
        secondTable.add(fieldsTable).padTop(40).row();

        idLabel = new Label("ID :", skin);
        fieldsTable.add(idLabel).padRight(20);
        id = new TextField("", skin);
        fieldsTable.add(id).height(40).width(200).padTop(20).row();

        passwordLabel = new Label("Password :", skin);
        fieldsTable.add(passwordLabel).padRight(10);
        password = new TextField("", skin);
        fieldsTable.add(password).height(40).width(200).padTop(20).row();
    }

    private void addTitle() {
        title = new Label("Log In Menu", skin);
        secondTable.add(title).row();
    }

    private void createBackGroundTable() {
        secondTable = new Table();
        Pixmap bgPixmap = new Pixmap(1,1, Pixmap.Format.RGB565);
        bgPixmap.setColor(new Color(0.40f,0.40f,0.53f,1f));
        bgPixmap.fill();
        TextureRegionDrawable textureRegionDrawableBg = new TextureRegionDrawable(new TextureRegion(new Texture(bgPixmap)));
        secondTable.setBackground(textureRegionDrawableBg);
        table.add(secondTable).height(350).width(400);
        bgPixmap.dispose();
    }


}
