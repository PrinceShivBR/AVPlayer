package com.avplayer.audio.interfaces;

/**
 * Created by shivappar.b on 14-03-2019
 */

/**
 * {@code AudioFocusAwarePlayer} defines an interface for players
 * to respond to audio focus changes.
 */
public interface AudioFocusAwarePlayer {

    boolean isPlaying();

    void play();

    void pause();

    void stop();

    void setVolume(float volume);
}
