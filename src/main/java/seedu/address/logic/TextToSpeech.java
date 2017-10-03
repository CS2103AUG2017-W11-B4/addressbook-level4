package seedu.address.logic;

import java.util.Locale;

import javax.speech.Central;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;

/**
 * The Text to Speech Component
 */
public class TextToSpeech {
    public TextToSpeech (String message) {
        try {
            Synthesizer synth = Central.createSynthesizer(new SynthesizerModeDesc(Locale.ENGLISH));
            synth.allocate();
            synth.resume();
            synth.speakPlainText(message,null);
            synth.waitEngineState(Synthesizer.QUEUE_EMPTY);
            synth.deallocate();

        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
