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
 * Menu class to sign in.
 */
public class SignInMenu extends ScreenMenu {

    Table secondTable;
    Table fieldsTable;
    TextField pseudo;
    TextField id;
    TextField password;
    TextButton signInButton;
    Label title;
    Label pseudoLabel;
    Label idLabel;
    Label passwordLabel;

    public SignInMenu(){
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
     * Method called to check the account before singing in.
     */
    private void signIn() {

        Emarrow.getInstance().setScreen(new ConnectionMenu());
    }


    // __________________ Extracted Methods __________________ //

    private void addSignInButton() {
        signInButton = new TextButton("Sign In", skin);
        signInButton.setColor(new Color(0.25f,1f,0f,1f));
        secondTable.add(signInButton).height(80).width(200).padTop(40).row();
        signInButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                signIn();
            }
        });
    }

    private void addFieldsTable() {
        fieldsTable = new Table();
        secondTable.add(fieldsTable).padTop(40).row();

        pseudoLabel = new Label("Pseudo :", skin);
        fieldsTable.add(pseudoLabel).padRight(10);
        pseudo = new TextField("", skin);
        fieldsTable.add(pseudo).height(40).width(200).row();

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
        title = new Label("Sign In Menu", skin);
        secondTable.add(title).row();
    }

    private void createBackGroundTable() {
        secondTable = new Table();
        Pixmap bgPixmap = new Pixmap(1,1, Pixmap.Format.RGB565);
        bgPixmap.setColor(new Color(0.40f,0.53f,0.40f,1f));
        bgPixmap.fill();
        TextureRegionDrawable textureRegionDrawableBg = new TextureRegionDrawable(new TextureRegion(new Texture(bgPixmap)));
        secondTable.setBackground(textureRegionDrawableBg);
        table.add(secondTable).height(400).width(400);
        bgPixmap.dispose();
    }
}