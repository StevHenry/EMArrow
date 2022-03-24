package com.uecepi.emarrow;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.uecepi.emarrow.display.MainMenu;
import com.uecepi.emarrow.display.ScreenMenu;

public class ControlsMenu extends ScreenMenu {
    Label controls;
    TextButton leftButton;
    TextButton rightButton;
    TextButton dashButton;
    TextButton jumpButton;
    TextButton backButton;



    public ControlsMenu(){
        super();
        create();
    }

    private void create(){
        controls = new Label("CONTROLS", skin);
        table.add(controls).row();
        //controls.setFontScale(10);

        if (Emarrow.getInstance().getController().getLeftKey() == Input.Keys.Q){
            leftButton = new TextButton("Left : Q", skin);
        }
        else{
            leftButton = new TextButton("Left : left arrow", skin);
        }
        table.add(leftButton).height(100).width(200).padTop(30).row();
        leftButton.addListener(new ClickListener(){
                public void clicked(InputEvent event, float x, float y) {
                    if (Emarrow.getInstance().getController().getLeftKey() == Input.Keys.Q){
                        leftButton.setText("Left : left arrow");
                        Emarrow.getInstance().controller.setLeftKey(Input.Keys.LEFT);
                    }
                    else if (Emarrow.getInstance().getController().getLeftKey() == Input.Keys.LEFT){
                        leftButton.setText("Left : Q");
                        Emarrow.getInstance().controller.setLeftKey(Input.Keys.Q);
                    }
                }
        });


        if (Emarrow.getInstance().getController().getRightKey() == Input.Keys.D){
            rightButton = new TextButton("Right : D", skin);
        }
        else{
            rightButton = new TextButton("Right : right arrow", skin);
        }
        table.add(rightButton).height(100).width(200).padTop(30).row();
        rightButton.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y) {
                if (Emarrow.getInstance().getController().getRightKey() == Input.Keys.D){
                    rightButton.setText("Right : right arrow");
                    Emarrow.getInstance().controller.setRightKey(Input.Keys.RIGHT);
                }
                else if (Emarrow.getInstance().getController().getRightKey() == Input.Keys.RIGHT){
                    rightButton.setText("Right : D");
                    Emarrow.getInstance().controller.setRightKey(Input.Keys.D);
                }
            }
        });

        if (Emarrow.getInstance().getController().getDashKey() == Input.Keys.SHIFT_LEFT){
            dashButton = new TextButton("Dash : Left Shift", skin);
        }
        else{
            dashButton = new TextButton("Dash : E", skin);
        }
        table.add(dashButton).height(100).width(200).padTop(30).row();
        dashButton.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y) {
                if (Emarrow.getInstance().getController().getDashKey() == Input.Keys.SHIFT_LEFT){
                    dashButton.setText("Dash : E");
                    Emarrow.getInstance().controller.setDashKey(Input.Keys.E);
                }
                else if (Emarrow.getInstance().getController().getDashKey() == Input.Keys.E){
                    dashButton.setText("Dash : Left Shift");
                    Emarrow.getInstance().controller.setDashKey(Input.Keys.SHIFT_LEFT);
                }
            }
        });


        jumpButton = new TextButton("Jump : Espace", skin);
        table.add(jumpButton).height(100).width(200).padTop(30).row();

        backButton = new TextButton("Back", skin);
        table.add(backButton).height(100).width(200).padTop(30).row();
        backButton.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                Emarrow.getInstance().setScreen(new MainMenu());
            }
        });


    }
}
