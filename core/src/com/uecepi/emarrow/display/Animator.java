package com.uecepi.emarrow.display;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
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
    private String lastAnimation = "standing";
    private String characterNumber;
    private boolean isLooping = true;
    private boolean isFlippedToLeft = false;
    private boolean hurt =false;
    String vertexShader = "attribute vec4 " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
            + "attribute vec4 " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" //
            + "attribute vec2 " + ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" //
            + "uniform mat4 u_projTrans;\n" //
            + "varying vec4 v_color;\n" //
            + "varying vec2 v_texCoords;\n" //
            + "\n" //
            + "void main()\n" //
            + "{\n" //
            + "   v_color = " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" //
            + "   v_texCoords = " + ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" //
            + "   gl_Position =  u_projTrans * " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
            + "}\n";
    String fragmentShader = "#ifdef GL_ES\n" //
            + "#define LOWP lowp\n" //
            + "precision mediump float;\n" //
            + "#else\n" //
            + "#define LOWP \n" //
            + "#endif\n" //
            + "varying LOWP vec4 v_color;\n" //
            + "varying vec2 v_texCoords;\n" //
            + "uniform sampler2D u_texture;\n" //
            + "void main()\n"//
            + "{\n" //
            + "  gl_FragColor = v_color * texture2D(u_texture, v_texCoords).a;\n" //
            + "}";

    ShaderProgram shader = new ShaderProgram(vertexShader, fragmentShader);

    public static void load() {
        loadAnimation("1");
        loadAnimation("2");
    }

    public Animator(String characterNumber) {
        this.characterNumber = characterNumber;
    }

    public void render(SpriteBatch spriteBatch, int x, int y) {
        stateTime += Gdx.graphics.getDeltaTime();

        if(!isLooping) {
            if(stateTime > FRAME_COLS*frameDuration) {
                setCurrentAnimation(lastAnimation);
                isLooping = true;
            }
        }

        // Get current frame of animation for the current stateTime
        TextureRegion currentFrame = Assets.ANIMATIONS.get(currentAnimation+characterNumber).getKeyFrame(stateTime, true);
        if(isFlippedToLeft && !currentFrame.isFlipX()) {
            currentFrame.flip(true,false);
        }
        else if (currentFrame.isFlipX() && !isFlippedToLeft){
            currentFrame.flip(true,false);
        }
        if (hurt)
            spriteBatch.setShader(shader);
        else
            spriteBatch.setShader(null);
        spriteBatch.draw(currentFrame, x, y);

    }

    public String getCurrentAnimation() {
        return currentAnimation;
    }

    public void setCurrentAnimation(String animation) {
        currentAnimation = animation;
        stateTime = 0f;
    }

    public void playOnceAnimation(String animation) {
        isLooping = false;
        lastAnimation = currentAnimation;
        setCurrentAnimation(animation);
    }

    public boolean isFlippedToLeft() {
        return isFlippedToLeft;
    }

    public void setFlippedToLeft(boolean flippedToLeft) {
        isFlippedToLeft = flippedToLeft;
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

    public boolean isHurt() {
        return hurt;
    }

    public void setHurt(boolean hurt) {
        this.hurt = hurt;
    }
}