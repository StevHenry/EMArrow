package com.uecepi.emarrow.display.menus;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.uecepi.emarrow.GameEngine;
import com.uecepi.emarrow.display.KeyboardController;

public class ControlsMenu extends ScreenMenu {

    private Label controls;
    private Label instructions;
    private TextButton leftButton;
    private TextButton rightButton;
    private TextButton upButton;
    private TextButton downButton;
    private TextButton dashButton;
    private TextButton jumpButton;
    private TextButton backButton;

    public ControlsMenu() {
        super();
    }

    @Override
    protected void create() {
        KeyboardController controller = GameEngine.getInstance().getInputManager().getController();
        controls = new Label("CONTROLS", skin);
        table.add(controls).row();
        controls.setFontScale(10);

        instructions = new Label("Click on the buttons to change the controls", skin);
        table.add(instructions).row();

        if (GameEngine.getInstance().getInputManager().getController().getLeftKey() == Input.Keys.Q) {
            leftButton = new TextButton("Left : Q", skin);
        } else {
            leftButton = new TextButton("Left : left arrow", skin);
        }
        table.add(leftButton).height(100).width(200).padTop(30).row();
        leftButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (controller.getLeftKey() == Input.Keys.Q) {
                    leftButton.setText("Left : left arrow");
                    controller.setLeftKey(Input.Keys.LEFT);
                } else if (controller.getLeftKey() == Input.Keys.LEFT) {
                    leftButton.setText("Left : Q");
                    controller.setLeftKey(Input.Keys.Q);
                }
            }
        });


        if (controller.getRightKey() == Input.Keys.D) {
            rightButton = new TextButton("Right : D", skin);
        } else {
            rightButton = new TextButton("Right : right arrow", skin);
        }
        table.add(rightButton).height(100).width(200).padTop(30).row();
        rightButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (controller.getRightKey() == Input.Keys.D) {
                    rightButton.setText("Right : right arrow");
                    controller.setRightKey(Input.Keys.RIGHT);
                } else if (controller.getRightKey() == Input.Keys.RIGHT) {
                    rightButton.setText("Right : D");
                    controller.setRightKey(Input.Keys.D);
                }
            }
        });

        if (controller.getDownKey() == Input.Keys.S) {
            downButton = new TextButton("Down : S", skin);
        } else {
            downButton = new TextButton("Down : down arrow", skin);
        }
        table.add(downButton).height(100).width(200).padTop(30).row();
        downButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (controller.getDownKey() == Input.Keys.S) {
                    downButton.setText("Down : down arrow");
                    controller.setDownKey(Input.Keys.DOWN);
                } else if (controller.getDownKey() == Input.Keys.DOWN) {
                    downButton.setText("Down : S");
                    controller.setDownKey(Input.Keys.S);
                }
            }
        });

        if (controller.getDashKey() == Input.Keys.SHIFT_LEFT) {
            dashButton = new TextButton("Dash : Left Shift", skin);
        } else {
            dashButton = new TextButton("Dash : E", skin);
        }
        table.add(dashButton).height(100).width(200).padTop(30).row();
        dashButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (controller.getDashKey() == Input.Keys.SHIFT_LEFT) {
                    dashButton.setText("Dash : E");
                    controller.setDashKey(Input.Keys.E);
                } else if (controller.getDashKey() == Input.Keys.E) {
                    dashButton.setText("Dash : Left Shift");
                    controller.setDashKey(Input.Keys.SHIFT_LEFT);
                }
            }
        });

        table.add(createBackToMainMenuButton()).height(100).width(200).padTop(30).row();

    }
}
