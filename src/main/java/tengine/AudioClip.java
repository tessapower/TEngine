package tengine;

import javax.sound.sampled.*;
import java.io.InputStream;

public class AudioClip {
    private AudioInputStream audio;
    private final AudioFormat mFormat;
    private final byte[] mData;
    private final long mLength;
    private Clip mLoopClip;

    public AudioClip(InputStream inputStream) {
        try {
            audio = AudioSystem.getAudioInputStream(inputStream);
        } catch (Exception e) {
            System.err.println("Error: cannot open audio file " + inputStream);
        }

        mFormat = audio.getFormat();
        mLength = audio.getFrameLength() * mFormat.getFrameSize();
        mData = new byte[(int) mLength];

        // TODO: maybe rework this? It seems like a bit of a hack from Massey Engine authors
        // Try reading the data and if there is an error, throw an exception
        try {
            audio.read(mData);
        } catch (Exception exception) {
            System.err.println("Error: reading audio file");

            System.exit(1);
        }

        mLoopClip = null;
    }

    public Clip getLoopClip() {
        return mLoopClip;
    }

    public void setLoopClip(Clip clip) {
        mLoopClip = clip;
    }

    public AudioFormat getAudioFormat() {
        return mFormat;
    }

    public byte[] getData() {
        return mData;
    }

    public long getBufferSize() {
        return mLength;
    }

    public void play() {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(mFormat, mData, 0, (int) mLength);

            clip.start();
        } catch (Exception exception) {
            System.err.println("Error: playing audio clip");
        }
    }

    public void play(float volume) {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(mFormat, mData, 0, (int) mLength);

            FloatControl control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            control.setValue(volume);

            clip.start();
        } catch (Exception exception) {
            System.err.println("Error: could not play audio clip");
        }
    }

    // Start playing an AudioClip on loop
    public void playOnLoop() {
        Clip clip = mLoopClip;

        if (clip == null) {
            try {
                clip = AudioSystem.getClip();
                clip.open(mFormat, mData, 0, (int) mLength);
                clip.loop(Clip.LOOP_CONTINUOUSLY);

                this.setLoopClip(clip);
            } catch (Exception exception) {
                System.err.println("Error: could not play audio clip");
            }
        }

        // Set Frame Position to 0
        assert clip != null;
        clip.setFramePosition(0);

        clip.start();
    }

    // Start playing an AudioClip on loop with a volume in decibels
    public void playOnLoop(float volume) {
        Clip clip = mLoopClip;

        if (clip == null) {
            try {
                clip = AudioSystem.getClip();
                clip.open(mFormat, mData, 0, (int) mLength);

                FloatControl control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                control.setValue(volume);

                clip.loop(Clip.LOOP_CONTINUOUSLY);
                this.setLoopClip(clip);
            } catch (Exception exception) {
                System.err.println("Error: could not play audio clip");
            }
        }

        assert clip != null;
        clip.setFramePosition(0);

        clip.start();
    }

    // Stop an AudioClip playing
    public void stopPlayingLoop() {
        Clip clip = mLoopClip;

        if (clip != null) {
            clip.stop();
        }
    }
}
