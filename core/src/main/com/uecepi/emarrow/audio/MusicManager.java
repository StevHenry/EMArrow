package com.uecepi.emarrow.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import java.util.HashMap;

public class MusicManager {

    // Constants //
    public static final int MUSIC1_BGM = 0;
    public static final int MUSIC2_BGM = 1;

    public static final int SHOT_SE = 0;
    public static final int SHOT2_SE = 1;
    public static final int FLY_SE = 2;
    public static final int DASH_SE = 3;
    public static final int RUN_SE = 4;
    public static final int TOUCHED_SE = 5;
    public static final int JUMP_SE = 6;


    // Attributes //
    private static Music currentMusic;
    private static float volume = 0f;
    private static float BGMVolume = 1.0f;
    private static float SEVolume = 1.0f;


    // Assets //
        // BackGround Musics (BGM) //
    private static HashMap<Integer, Music> BGM = new HashMap<>();
        // Sound Effects (SE) //
    private static HashMap<Integer, Sound> SE = new HashMap<>();


    public static void load() {
        BGM.put(MUSIC1_BGM, Gdx.audio.newMusic(Gdx.files.internal("audios/bgm/music1.ogg")));
        BGM.put(MUSIC2_BGM, Gdx.audio.newMusic(Gdx.files.internal("audios/bgm/music1.ogg")));

        SE.put(SHOT_SE, Gdx.audio.newSound(Gdx.files.internal("audios/se/shot1.ogg")));
        SE.put(SHOT2_SE, Gdx.audio.newSound(Gdx.files.internal("audios/se/shot2.ogg")));
        SE.put(JUMP_SE, Gdx.audio.newSound(Gdx.files.internal("audios/se/jump.ogg")));
        SE.put(DASH_SE, Gdx.audio.newSound(Gdx.files.internal("audios/se/dash.ogg")));
        SE.put(FLY_SE, Gdx.audio.newSound(Gdx.files.internal("audios/se/fly.ogg")));
        SE.put(RUN_SE, Gdx.audio.newSound(Gdx.files.internal("audios/se/run.ogg")));
        SE.put(TOUCHED_SE, Gdx.audio.newSound(Gdx.files.internal("audios/se/touched.ogg")));

    }

    public static void playSE(int nb) {
        SE.get(nb).play(volume*SEVolume);
    }

    public static void playSE(int nb, float volumeAdjuster) {
        float realVolume = Math.min(1.0f, volumeAdjuster*volume*SEVolume);
        SE.get(nb).play(realVolume);
    }

    public static void setMusic(int key) {
        if(currentMusic != null) {
            currentMusic.stop();
        }

        MusicManager.currentMusic = BGM.get(key);
        currentMusic.setVolume(volume*BGMVolume);
        currentMusic.setLooping(true);
        currentMusic.play();
    }

    public static void dispose() {
        for(Music m : BGM.values()) {
            m.dispose();
        }
        for(Sound s : SE.values()) {
            s.dispose();
        }
    }


    // __________________ Getters & Setters ____________________________ //
    public static void setVolume(float value) {
        volume = value;
        currentMusic.setVolume(BGMVolume*volume);
    }

    public static void setBGMVolume(float BGMVolume) {
        MusicManager.BGMVolume = BGMVolume;
        currentMusic.setVolume(BGMVolume*volume);
    }

    public static void setSEVolume(float SEVolume) {
        MusicManager.SEVolume = SEVolume;
    }

    public static float getVolume() {
        return volume;
    }

    public static float getBGMVolume() {
        return BGMVolume;
    }

    public static float getSEVolume() {
        return SEVolume;
    }
}
