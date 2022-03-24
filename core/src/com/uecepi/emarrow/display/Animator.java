package com.uecepi.emarrow.display;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.uecepi.emarrow.assets.Assets;

import java.util.ArrayList;

public class Animator {

    public static final String STANDING_ANIMATION = "standing";
    public static final String FLYING_ANIMATION = "flying";
    public static final String FLYING_SHOT_ANIMATION = "flyingShot";
    public static final String STANDING_SHOT_ANIMATION = "standingShot";
    public static final String JUMPING_ANIMATION = "jumping";
    public static final String RUNNING_ANIMATION = "running";

    private static final int FRAME_COLS = 4, FRAME_ROWS = 1;
    public static final int width = 20;
    public static final int height = 20;

    private static final float frameDuration = 0.1f;

    private float stateTime = 0f;
    private String currentAnimation = "standing";
    private String characterNumber;


    public static void load() {
        loadAnimation("1");
        loadAnimation("2");
    }

    public Animator(String characterNumber) {
        this.characterNumber = characterNumber;
    }

    public void render(SpriteBatch spriteBatch, int x, int y) {
        stateTime += Gdx.graphics.getDeltaTime();

        // Get current frame of animation for the current stateTime
        TextureRegion currentFrame = Assets.ANIMATIONS.get(currentAnimation+characterNumber).getKeyFrame(stateTime, true);
        spriteBatch.draw(currentFrame, x, y);

    }

    public void setCurrentAnimation(String animation) {
        currentAnimation = animation;
        stateTime = 0f;
    }


    private static void loadAnimation(String charNum) {
        ArrayList<Texture> textures = new ArrayList<>();
        ArrayList<TextureRegion[][]> textxureRegion = new ArrayList<>();
        ArrayList<TextureRegion[]> textxureRegion1D = new ArrayList<>();

        textures.add(new Texture(Gdx.files.internal("images/char/"+ charNum +"/flying.png")));
        textures.add(new Texture(Gdx.files.internal("images/char/"+ charNum +"/flyingShot.png")));
        textures.add(new Texture(Gdx.files.internal("images/char/"+ charNum +"/jumping.png")));
        textures.add(new Texture(Gdx.files.internal("images/char/"+ charNum +"/running.png")));
        textures.add(new Texture(Gdx.files.internal("images/char/"+ charNum +"/standing.png")));
        textures.add(new Texture(Gdx.files.internal("images/char/"+ charNum +"/standingShot.png")));

        for(int k = 0; k < textures.size(); k++) {
            // Split sheets //
            textxureRegion.add(TextureRegion.split(textures.get(k),
                    textures.get(k).getWidth() / FRAME_COLS,
                    textures.get(k).getHeight() / FRAME_ROWS));

            // Place it into a 1D Array //
            TextureRegion[] tR1D = new TextureRegion[FRAME_COLS];
            for (int i = 0; i < FRAME_COLS; i++) {
                tR1D[i] = textxureRegion.get(k)[0][i];
            }
            textxureRegion1D.add(tR1D);
        }

        Assets.ANIMATIONS.put("flying" + charNum, new Animation<>(frameDuration, textxureRegion1D.get(0)));
        Assets.ANIMATIONS.put("flyingShot" + charNum, new Animation<>(frameDuration, textxureRegion1D.get(1)));
        Assets.ANIMATIONS.put("jumping" + charNum, new Animation<>(frameDuration, textxureRegion1D.get(2)));
        Assets.ANIMATIONS.put("running" + charNum, new Animation<>(frameDuration, textxureRegion1D.get(3)));
        Assets.ANIMATIONS.put("standing" + charNum, new Animation<>(frameDuration, textxureRegion1D.get(4)));
        Assets.ANIMATIONS.put("standingShot" + charNum, new Animation<>(frameDuration, textxureRegion1D.get(5)));


    }

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }
}