# hanselblack
###### \SpeechToTextCommand.java
``` java
/**
 * Spech to text, using sphinx 4
 */
public class SpeechToTextCommand extends Command{

    public static final String COMMAND_WORD = "speak";

    public static final String MESSAGE_SUCCESS = "speaking...";

    private static MediaPlayer mediaPlayer;

    public String getCommandWord() {
        return COMMAND_WORD;
    }

    @Override
    public CommandResult execute() {

        return new CommandResult(MESSAGE_SUCCESS);
    }


    public static void main(String[] args) throws Exception {

        Logger cmRootLogger = Logger.getLogger("default.config");
        cmRootLogger.setLevel(java.util.logging.Level.OFF);
        String conFile = System.getProperty("java.util.logging.config.file");
        if (conFile == null) {
            System.setProperty("java.util.logging.config.file", "ignoreAllSphinx4LoggingOutput");
        }
        Configuration configuration = new Configuration();

        configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
        configuration.setDictionaryPath("cmudict-en-us.dict");
        configuration.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");

        LiveSpeechRecognizer recognizer = new LiveSpeechRecognizer(configuration);
        // Start recognition process pruning previously cached data.
        recognizer.startRecognition(true);
        // Pause recognition process. It can be resumed then with startRecognition(false).

        while (true) {
            String utterance = recognizer.getResult().getHypothesis();
            if (utterance.equals("one zero one")
                    || utterance.equals("one oh one"))
                break;
            else
                System.out.println(utterance);
        }
        recognizer.stopRecognition();
    }
}
```
