# hanselblack
###### \java\seedu\address\logic\Audio.java
``` java
/**
 * Common class to play audio file such as mp3 file etc.
 */
public class Audio {

    private String audioFileName;
    private Player player;

    public Audio(String audioFileName) {
        this.audioFileName = audioFileName;
    }

    /**
     * Plays audio file, returns void
     */
    public void playSound() {
        URL url = this.getClass().getClassLoader().getResource(audioFileName);
        //Async, by creating a single thread to prevent freezing on UI (main) thread
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            try {
                BufferedInputStream in = new BufferedInputStream(url.openStream());
                player = new Player(in);
                player.play();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JavaLayerException e) {
                e.printStackTrace();
            }
        });
    }
}
```
###### \java\seedu\address\logic\commands\AddCommand.java
``` java
            //Text to Speech
            new TextToSpeech(toAdd.getName().toString() + " has been added to the list of contacts").speak();
```
###### \java\seedu\address\logic\commands\AliasCommand.java
``` java
        //Text to Speech
        new TextToSpeech(String.format(MESSAGE_ADD_SUCCESS, alias, command)).speak();;
```
###### \java\seedu\address\logic\commands\ClearCommand.java
``` java
        //Text to Speech
        new TextToSpeech(MESSAGE_SUCCESS).speak();;
```
###### \java\seedu\address\logic\commands\DeleteCommand.java
``` java
            //Text to Speech
            new TextToSpeech("Index " + targetIndex.getOneBased() + " does not exist").speak();;
```
###### \java\seedu\address\logic\commands\DeleteCommand.java
``` java
        //Text to Speech
        new TextToSpeech(personToDelete.getName().toString() + " has been deleted").speak();;
```
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
        //Text to Speech
        new TextToSpeech(editedPerson.getName().toString() + " has been edited").speak();;
```
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
        Remark updatedRemark = editPersonDescriptor.getRemark().orElse(personToEdit.getRemark());
```
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
            this.remark = toCopy.remark;
```
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
        public void setRemark(Remark remark) {
            this.remark = remark;
        }

        public Optional<Remark> getRemark() {
            return Optional.ofNullable(remark);
        }
```
###### \java\seedu\address\logic\commands\FindCommand.java
``` java
        //Text to Speech
        new TextToSpeech("Heres what I have found").speak();
```
###### \java\seedu\address\logic\commands\HelpCommand.java
``` java
        //Text to Speech
        new TextToSpeech("Showing help").speak();;
```
###### \java\seedu\address\logic\commands\HistoryCommand.java
``` java
        //Text to Speech
        new TextToSpeech("Showing list of previous commands").speak();;
```
###### \java\seedu\address\logic\commands\ListCommand.java
``` java
        //Text to Speech
        new TextToSpeech(MESSAGE_SUCCESS).speak();;
```
###### \java\seedu\address\logic\commands\MusicCommand.java
``` java
/**
 * Plays Music with music play command
 * Stop Music with music stop command
 */
public class MusicCommand extends Command {

    public static final String COMMAND_WORD = "music";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": play/pause/stop music "
            + "of your selected genre.\n"
            + "Parameters: ACTION (must be either play or stop) "
            + "GENRE (must be either pop, dance or classic) \n"
            + "Example: " + COMMAND_WORD + " play classic ";

    public static final String[] GENRE_LIST = {"pop", "dance", "classic"};
    public static final String MESSAGE_NO_MUSIC_PLAYING = "No music is currently playing";
    public static final String MESSAGE_STOP = "Music Stopped";
    private static final int maxTrackNumber = 2;

    private static String messageSuccess = "Music Playing";
    private static int trackNumber = 1;
    private static String previousGenre = "";
    private static Music music;
    private static boolean isMusicPlaying = false;

    private String command;
    private String genre = "pop";

    public MusicCommand(String command, String genre) {
        this.command = command;
        this.genre = genre;
    }

    public MusicCommand(String command) {
        this.command = command;
    }


    /**
     * Returns true if music player is currently playing.
     * else return false
     */
    public static boolean getIsMusicPlaying() {
        return isMusicPlaying;
    }

    /**
     * set the boolean status of isMusicPlaying static variable
     */
    public static void setIsMusicPlaying(boolean status) {
        isMusicPlaying = status;
    }

    /**
     * Stops music playing in the player
     */
    public static void stopMusicPlayer() {
        if (music != null) {
            music.stop();
        }
    }

    @Override
    public CommandResult execute() {
        boolean genreExist = Arrays.asList(GENRE_LIST).contains(genre);
        switch (command) {
        case "play":
            if (RadioCommand.getIsRadioPlaying()) {
                RadioCommand.stopRadioPlayer();
                RadioCommand.setIsRadioPlaying(false);
            }
            stopMusicPlayer();
            if (genreExist) {
                //check if current genre same previous playing music genre
                //if different reset track number
                if (!genre.equals(previousGenre)) {
                    trackNumber = 1;
                }
                messageSuccess = genre.toUpperCase() + " Music " + trackNumber + " Playing";
                music = new Music("audio/music/"
                        + genre + trackNumber + ".mp3");
                music.start();

                if (trackNumber < maxTrackNumber) {
                    trackNumber++;
                } else {
                    //reset track number back to 1
                    trackNumber = 1;
                }
                //Text to Speech
                new TextToSpeech(messageSuccess).speak();
                //set current playing genre as previousGenre
                previousGenre = genre;
                setIsMusicPlaying(true);
                return new CommandResult(messageSuccess);
            } else {
                return new CommandResult(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MusicCommand.MESSAGE_USAGE));
            }
        //Stop the music that is currently playing
        case "stop":
            if (!getIsMusicPlaying()) {
                //Text to Speech
                new TextToSpeech(MESSAGE_NO_MUSIC_PLAYING).speak();
                return new CommandResult(MESSAGE_NO_MUSIC_PLAYING);
            }
            setIsMusicPlaying(false);
            stopMusicPlayer();
            //Text to Speech
            new TextToSpeech(MESSAGE_STOP).speak();
            return new CommandResult(MESSAGE_STOP);

        default:
            return new CommandResult(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MusicCommand.MESSAGE_USAGE));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MusicCommand // instanceof handles nulls
                && (this.genre == null || this.genre.equals(((MusicCommand) other).genre)) // state check
                && (this.command == null || this.command.equals(((MusicCommand) other).command))); // state check
    }
}
```
###### \java\seedu\address\logic\commands\RadioCommand.java
``` java
/**
 * Plays Radio with radio play command
 * Stop Radio with radio stop command
 */
public class RadioCommand extends Command {

    public static final String COMMAND_WORD = "radio";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": play/stop radio "
            + "of your selected genre.\n"
            + "Parameters: ACTION (must be either play or stop) "
            + "GENRE (must be either chinese, classic, news, pop) \n"
            + "Example: " + COMMAND_WORD + " play news ";

    public static final String[] GENRE_LIST = {"chinese", "classic", "news", "pop"};
    public static final String MESSAGE_NO_RADIO_PLAYING = "No radio is currently playing";
    public static final String MESSAGE_STOP = "Radio Stopped";
    public static final String MESSAGE_SUCCESS = "Radio Playing";
    public static final String MESSAGE_NO_INTERNET = "Not Connected to the Internet";

    private static boolean isRadioPlaying = false;
    private static Radio radio;

    private String command;
    private String genre = "pop";
    private String[] genreList = {"chinese", "classic", "news", "pop"};

    public RadioCommand(String command, String genre) {
        this.command = command;
        this.genre = genre;
    }

    public RadioCommand(String command) {
        this.command = command;
    }

    /**
     * Returns true if radio player is currently playing.
     * else return false
     */
    public static boolean getIsRadioPlaying() {
        return isRadioPlaying;
    }

    /**
     * set the boolean status of isRadioPlaying static variable
     */
    public static void setIsRadioPlaying(boolean status) {
        isRadioPlaying = status;
    }

    /**
     * Stops radio playing in the player
     */
    public static void stopRadioPlayer() {
        if (radio != null) {
            radio.stop();
        }
    }

    @Override
    public CommandResult execute() {

        boolean genreExist = Arrays.asList(GENRE_LIST).contains(genre);
        switch (command) {
        case "play":
            if (genreExist) {
                if (InternetConnectionCheck.isConnectedToInternet()) {
                    if (MusicCommand.getIsMusicPlaying()) {
                        MusicCommand.stopMusicPlayer();
                        MusicCommand.setIsMusicPlaying(false);
                    }
                    stopRadioPlayer();
                    radio = new Radio(genre);
                    radio.start();
                    RadioCommand.setIsRadioPlaying(true);

                    String printedSuccessMessage = genre.toUpperCase() + " " + MESSAGE_SUCCESS;
                    //Text to Speech
                    new TextToSpeech(printedSuccessMessage).speak();
                    return new CommandResult(printedSuccessMessage);
                } else {
                    return new CommandResult(MESSAGE_NO_INTERNET);
                }
            } else {
                return new CommandResult(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RadioCommand.MESSAGE_USAGE));
            }
        case "stop":
            if (getIsRadioPlaying()) {
                radio.stop();
                //Text to Speech
                new TextToSpeech(MESSAGE_STOP).speak();
                RadioCommand.setIsRadioPlaying(false);
                return new CommandResult(MESSAGE_STOP);
            } else {
                //Text to Speech
                new TextToSpeech(MESSAGE_NO_RADIO_PLAYING).speak();
                return new CommandResult(MESSAGE_NO_RADIO_PLAYING);
            }
        default:
            return new CommandResult(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RadioCommand.MESSAGE_USAGE));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RadioCommand // instanceof handles nulls
                && (this.genre == null || this.genre.equals(((RadioCommand) other).genre)) // state check
                && (this.command == null || this.command.equals(((RadioCommand) other).command))); // state check
    }
}


```
###### \java\seedu\address\logic\commands\ShareCommand.java
``` java
/**
 * Emails the list of contact details to the input email address
 */
public class ShareCommand extends Command {

    public static final String COMMAND_WORD = "share";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Emails the person's contact details identified by the index number used in the listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_SHARE + "EMAIL ADDRESS";

    public static final String MESSAGE_SUCCESS = "Email Sent!";
    public static final String MESSAGE_EMAIL_NOT_VALID = "Email address is not valid!";
    public static final String MESSAGE_NO_INTERNET = "Not Connected to the Internet";

    public static final String MESSAGE_FAILURE = "Email was not sent!";

    private static SendEmail sendEmail;

    private Index targetIndex;
    private String[] shareEmailArray;

    public ShareCommand(Index targetIndex, String[] shareEmailArray) {
        this.targetIndex = targetIndex;
        this.shareEmailArray = shareEmailArray;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);
        requireNonNull(targetIndex);

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        if (InternetConnectionCheck.isConnectedToInternet()) {
            ReadOnlyPerson person = lastShownList.get(targetIndex.getZeroBased());
            String to;

            for (int index = 0; index < shareEmailArray.length; index++) {
                to = shareEmailArray[index];
                if (isNumeric(to)) {
                    try {
                        Index recipientIndex = ParserUtil.parseIndex(to);
                        if (recipientIndex.getZeroBased() >= lastShownList.size()) {
                            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
                        }
                        ReadOnlyPerson personRecipient = lastShownList.get(recipientIndex.getZeroBased());
                        to = personRecipient.getEmail().toString();

                    } catch (IllegalValueException ive) {
                        //Text to Speech
                        new TextToSpeech(MESSAGE_FAILURE).speak();
                        return new CommandResult(MESSAGE_FAILURE);
                    }
                }
                if (isValidEmailAddress(to)) {
                    sendEmail = new SendEmail(to, person);
                    sendEmail.start();
                } else {
                    //Text to Speech
                    new TextToSpeech(MESSAGE_EMAIL_NOT_VALID).speak();;
                    return new CommandResult(MESSAGE_EMAIL_NOT_VALID);
                }
            }
            //Text to Speech
            new TextToSpeech(MESSAGE_SUCCESS).speak();;
            return new CommandResult(MESSAGE_SUCCESS);
        } else {
            return new CommandResult(MESSAGE_NO_INTERNET);
        }
    }

    /**
     * Returns true if string is numeric number. This method is to identify which are
     * index or email address in the s/ parameter.
     */
    public boolean isNumeric(String str) {
        return str != null && str.matches("[-+]?\\d*\\.?\\d+");
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ShareCommand // instanceof handles nulls
                && this.targetIndex.equals(((ShareCommand) other).targetIndex)); // state check
    }

    /**
     * returns true if string is a valid email address
     */
    public static boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }
}
```
###### \java\seedu\address\logic\commands\UndoCommand.java
``` java
            //Text to Speech
            new TextToSpeech(MESSAGE_FAILURE);
```
###### \java\seedu\address\logic\commands\UndoCommand.java
``` java
        //Text to Speech
        new TextToSpeech(MESSAGE_SUCCESS).speak();
```
###### \java\seedu\address\logic\InternetConnectionCheck.java
``` java
/**
 * To check for internet connection
 */
public class InternetConnectionCheck {

    private static final Logger logger = LogsCenter.getLogger(InternetConnectionCheck.class);

    /**
     * Returns true if there is internet connection to google.com
     */
    public static boolean isConnectedToInternet() {
        Socket sock = new Socket();
        InetSocketAddress addr = new InetSocketAddress("google.com", 80);
        try {
            sock.connect(addr, 3000);
            return true;
        } catch (IOException e) {
            return false;
        } finally {
            try {
                sock.close();
            } catch (IOException e) {
                logger.info("Unable to close socket");
            }
        }
    }
}
```
###### \java\seedu\address\logic\parser\AddCommandParser.java
``` java
            Remark remark = ParserUtil.parseRemark(argMultimap.getValue(PREFIX_REMARK)).get();
```
###### \java\seedu\address\logic\parser\EditCommandParser.java
``` java
            ParserUtil.editParseRemark(argMultimap.getValue(PREFIX_REMARK)).ifPresent(editPersonDescriptor::setRemark);
```
###### \java\seedu\address\logic\parser\MusicCommandParser.java
``` java
/**
 * Parses input arguments and creates a new MusicCommand object
 */
public class MusicCommandParser implements Parser<MusicCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the String of words
     * and returns an MusicCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MusicCommand parse(String arguments) throws ParseException {
        String[] args = arguments.trim().split("\\s+");
        if (args.length == 1) {
            return new MusicCommand(args[0]);
        } else if (args.length == 2) {
            return new MusicCommand(args[0], args[1]);
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MusicCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> remark} into an {@code Optional<Remark>} if {@code remark} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Remark> parseRemark(Optional<String> remark) throws IllegalValueException {
        requireNonNull(remark);
        return remark.isPresent() ? Optional.of(new Remark(remark.get())) : Optional.of(new Remark(""));
    }
    /**
     * Parses a {@code Optional<String> remark} into an {@code Optional<Remark>} if {@code remark} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Remark> editParseRemark(Optional<String> remark) throws IllegalValueException {
        requireNonNull(remark);
        return remark.isPresent() ? Optional.of(new Remark(remark.get())) : Optional.empty();
    }
```
###### \java\seedu\address\logic\parser\RadioCommandParser.java
``` java
/**
 * Parses input arguments and creates a new RadioCommand object
 */
public class RadioCommandParser implements Parser<RadioCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the String of words
     * and returns an RadioCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RadioCommand parse(String arguments) throws ParseException {
        String[] args = arguments.trim().split("\\s+");
        if (args.length == 1) {
            return new RadioCommand(args[0]);
        } else if (args.length == 2) {
            return new RadioCommand(args[0], args[1]);
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RadioCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\logic\parser\ShareCommandParser.java
``` java
/**
 * Parses input arguments and creates a new ShareCommand object
 */
public class ShareCommandParser implements Parser<ShareCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ShareCommand
     * and returns an ShareCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ShareCommand parse(String arguments) throws ParseException {
        requireNonNull(arguments);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(arguments, PREFIX_SHARE);
        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShareCommand.MESSAGE_USAGE));
        }
        String share = argMultimap.getValue(PREFIX_SHARE).orElse("");
        if (share.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShareCommand.MESSAGE_USAGE));
        }
        String[] shareEmailArray = share.trim().split("\\s+");
        return new ShareCommand(index, shareEmailArray);
    }
}
```
###### \java\seedu\address\logic\TextToSpeech.java
``` java
/**
 * Text To Speech, converts a string into audible speech audio.
 */
public class TextToSpeech {

    // Some available voices are (kevin, kevin16, alan)
    // alan only have a limited number of vocabulary hence
    // kevin16 is used for ensure all words can be turned into speech
    private static final String VOICE_NAME = "kevin16";

    private String word;

    public TextToSpeech(String word) {
        this.word = word;
    }

    /**
     * Execute the Text to Speech Function
     * and returns void.
     */
    public void speak() {
        //Async, by creating a new thread to prevent freezing on UI (main) thread
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            VoiceManager vm = VoiceManager.getInstance();
            Voice voice = vm.getVoice(VOICE_NAME);
            //Voice settings to adjust how it sound like
            voice.setRate(120f);
            voice.setPitchShift(1.5f);
            voice.setVolume(200f);
            voice.allocate();
            //Play the TextToSpeech Voice
            voice.speak(word);
        });
    }
}
```
###### \java\seedu\address\logic\threads\Music.java
``` java
/**
 * Creates a new thread to stream music source, this is to prevent UI thread from freezing
 */
public class Music extends Thread {


    private String audioFileName;
    private Player player;
    private final Logger logger = LogsCenter.getLogger(Music.class);

    public Music(String audioFileName) {
        this.audioFileName = audioFileName;
    }

    /**
     * Opens buffer input stream to stream music source
     */
    public void run() {
        URL url = this.getClass().getClassLoader().getResource(audioFileName);
        try {
            //loop music
            while (true) {
                BufferedInputStream in = new BufferedInputStream(url.openStream());
                player = new Player(in);
                player.play();
                in.close();
            }
        } catch (IOException e) {
            logger.info("Invalid IO for BufferedInputStream: " + audioFileName);
        } catch (JavaLayerException e) {
            logger.info("JavaLayerExeception: Invalid File Type for Music Player");
        }
    }
}

```
###### \java\seedu\address\logic\threads\Radio.java
``` java
/**
 * Creates a new thread to stream radio source, this is to prevent UI thread from freezing
 */
public class Radio extends Thread {


    private String genre;
    private String radioStreamUrl;
    private Player player;
    private final Logger logger = LogsCenter.getLogger(Radio.class);

    public Radio(String genre) {
        this.genre = genre;
    }

    /**
     * Opens buffer input stream to stream radio source
     */
    public void run() {
        BufferedInputStream in = null;
        try {
            switch (genre) {
            case "chinese":
                radioStreamUrl = "http://198.105.214.140:2000/Live?icy=http";
                break;
            case "classic":
                radioStreamUrl = "http://media-sov.musicradio.com/ClassicFMMP3";
                break;
            case "news":
                radioStreamUrl = "http://streams.kqed.org/kqedradio?";
                break;
            case "pop":
                radioStreamUrl = "http://bbcmedia.ic.llnwd.net/stream/bbcmedia_radio1_mf_q";
                break;
            default:
                radioStreamUrl = "http://bbcmedia.ic.llnwd.net/stream/bbcmedia_radio1_mf_q";
                break;
            }

            in = new BufferedInputStream(new URL(radioStreamUrl).openStream());
            player = new Player(in);
            player.play();
            in.close();
        } catch (IOException e) {
            logger.info("Invalid IO for BufferedInputStream: " + radioStreamUrl);
        } catch (JavaLayerException e) {
            logger.info("JavaLayerExeception: Invalid File Type for Radio Player");
        }
    }
}

```
###### \java\seedu\address\logic\threads\SendEmail.java
``` java
/**
 * Creates a new thread to send email, this is to prevent UI thread from freezing
 */
public class SendEmail extends Thread {

    private String recipientEmail;
    private ReadOnlyPerson person;

    private final Logger logger = LogsCenter.getLogger(SendEmail.class);

    public SendEmail(String recipientEmail, ReadOnlyPerson person) {
        this.recipientEmail = recipientEmail;
        this.person = person;
    }

    /**
     * Opens buffer input stream to stream radio source
     */
    public void run() {
        // Sender's email ID needs to be mentioned
        String senderEmail = "unifycs2103@gmail.com";
        String password = "CS2103CS2103";
        // For Gmail host
        String host = "smtp.gmail.com";
        // Get system properties
        Properties props = System.getProperties();

        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(senderEmail, password);
                    }
                }
        );
        String name = person.getName().fullName;
        String phone = person.getPhone().toString();
        String address = person.getAddress().toString();
        String email = person.getEmail().toString();
        String remark  = person.getRemark().toString();
        String tags = "";
        for (Tag tag :  person.getTags()) {
            tags += tag.tagName + " ";
        }
        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(senderEmail));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));

            // Set Subject: header field
            message.setSubject("Unify: Address Book: " + name + " Exported Data");

            MimeBodyPart messageBodyPart = new MimeBodyPart();

            //set the actual message
            messageBodyPart.setContent("<img src='https://github.com/CS2103AUG2017-W11-B4/"
                            + "main/blob/master/docs/images/email_header.png?raw=true'/>"
                            + "<br/><br/><br/><img src='https://github.com/CS2103AUG2017-W11-B4/"
                            + "main/blob/master/docs/images/email_subheader.png?raw=true'/>"
                            + "<br/><br/>"
                            + "<table>"
                            + "<tr><td style=\"height:20px; width:80px; margin:0;\">"
                            + "<b>Name</b></td><td>" + name + "</td></tr>"
                            + "<tr><td style=\"height:20px; width:80px; margin:0;\">"
                            + "<b>Phone</b></td><td>" + phone + "</td></tr>"
                            + "<tr><td style=\"height:20px; width:80px; margin:0;\">"
                            + "<b>Address</b></td><td>" + address + "</td></tr>"
                            + "<tr><td style=\"height:20px; width:80px; margin:0;\">"
                            + "<b>Email</b></td><td>" + email + "</td></tr>"
                            + "<tr><td style=\"height:20px; width:80px; margin:0;\">"
                            + "<b>Remark</b></td><td>" + remark + "</td></tr>"
                            + "<tr><td style=\"height:20px; width:80px; margin:0;\">"
                            + "<b>Tags</b></td><td>" + tags + "</td></tr>"
                            + "</table>",
                    "text/html");

            Multipart multipart = new MimeMultipart();

            //set text message part
            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart);

            Transport.send(message);
        } catch (MessagingException msg) {
            logger.info(msg.toString());
        }
    }
}
```
###### \java\seedu\address\model\person\Remark.java
``` java
/**
 * Represents a Person's remark in the address book.
 * Guarantees: immutable; is always valid
 **/

public class Remark {

    public static final String MESSAGE_REMARK_CONSTRAINTS = "Person remarks can take any values, can even be blank";

    public final String value;

    public Remark(String remark) {
        requireNonNull(remark);
        this.value = remark;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this || (other instanceof Remark && this.value.equals(((Remark) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
```
###### \java\seedu\address\ui\CommandBox.java
``` java
            //Plays typing Sound
            new Audio("audio/typing.mp3").playSound();
```
###### \java\seedu\address\ui\PersonPanel.java
``` java
        remark.setText(person.getRemark().toString());
        //Text to Speech
        new TextToSpeech(person.getName().fullName).speak();;
```
