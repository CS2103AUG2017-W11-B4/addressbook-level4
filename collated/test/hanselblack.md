# hanselblack
###### \java\seedu\address\logic\commands\MusicCommandTest.java
``` java
public class MusicCommandTest {

    @Test
    public void execute_music_wrongGenre() {
        MusicCommand musicCommand = new MusicCommand("play", "nonExistedGenre");
        CommandResult commandResult = musicCommand.execute();
        assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MusicCommand.MESSAGE_USAGE), commandResult.feedbackToUser);
    }

    @Test
    public void execute_music_wrongCommand() {
        MusicCommand musicCommand = new MusicCommand("wrongCommand", "nonExistedGenre");
        CommandResult commandResult = musicCommand.execute();
        assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MusicCommand.MESSAGE_USAGE), commandResult.feedbackToUser);

        musicCommand = new MusicCommand("play", "nonExistedGenre");
        commandResult = musicCommand.execute();
        assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MusicCommand.MESSAGE_USAGE), commandResult.feedbackToUser);

        musicCommand = new MusicCommand("wrongCommand");
        commandResult = musicCommand.execute();
        assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MusicCommand.MESSAGE_USAGE), commandResult.feedbackToUser);
    }

    @Test
    public void execute_stop_noExistingPlayer() {
        MusicCommand musicCommand = new MusicCommand("stop");
        CommandResult commandResult = musicCommand.execute();
        assertEquals(MESSAGE_NO_MUSIC_PLAYING, commandResult.feedbackToUser);

        musicCommand = new MusicCommand("stop", "additionalParameterGenre");
        commandResult = musicCommand.execute();
        assertEquals(MESSAGE_NO_MUSIC_PLAYING, commandResult.feedbackToUser);
    }

    @Test
    public void execute_music_successCommand() {
        String genre = "pop";
        String trackNumber = "1";
        MusicCommand musicCommand = new MusicCommand("play");
        CommandResult commandResult = musicCommand.execute();
        assertEquals(genre.toUpperCase() + " Music " + trackNumber
                + " Playing", commandResult.feedbackToUser);
        musicCommand = new MusicCommand("stop");
        musicCommand.execute();

        genre = "pop";
        trackNumber = "2";
        musicCommand = new MusicCommand("play", genre);
        commandResult = musicCommand.execute();
        assertEquals(genre.toUpperCase() + " Music " + trackNumber
                + " Playing", commandResult.feedbackToUser);
        musicCommand = new MusicCommand("stop");
        musicCommand.execute();

        genre = "dance";
        trackNumber = "1";
        musicCommand = new MusicCommand("play", genre);
        commandResult = musicCommand.execute();
        assertEquals(genre.toUpperCase() + " Music " + trackNumber
                + " Playing", commandResult.feedbackToUser);
        musicCommand = new MusicCommand("stop");
        musicCommand.execute();

        genre = "dance";
        trackNumber = "2";
        musicCommand = new MusicCommand("play", genre);
        commandResult = musicCommand.execute();
        assertEquals(genre.toUpperCase() + " Music " + trackNumber
                + " Playing", commandResult.feedbackToUser);
        musicCommand = new MusicCommand("stop");
        musicCommand.execute();

        genre = "classic";
        trackNumber = "1";
        musicCommand = new MusicCommand("play", genre);
        commandResult = musicCommand.execute();
        assertEquals(genre.toUpperCase() + " Music " + trackNumber
                + " Playing", commandResult.feedbackToUser);
        musicCommand = new MusicCommand("stop");
        musicCommand.execute();

        genre = "classic";
        trackNumber = "2";
        musicCommand = new MusicCommand("play", genre);
        commandResult = musicCommand.execute();
        assertEquals(genre.toUpperCase() + " Music " + trackNumber
                + " Playing", commandResult.feedbackToUser);
        musicCommand = new MusicCommand("stop");
        musicCommand.execute();
    }

    @Test
    public void execute_music_successNexTrack() {
        String genre = "pop";
        String trackNumber = "1";
        MusicCommand musicCommand = new MusicCommand("play", genre);
        CommandResult commandResult = musicCommand.execute();
        assertEquals(genre.toUpperCase() + " Music " + trackNumber
                + " Playing", commandResult.feedbackToUser);
        musicCommand = new MusicCommand("stop");
        musicCommand.execute();

        genre = "pop";
        trackNumber = "2";
        musicCommand = new MusicCommand("play", genre);
        commandResult = musicCommand.execute();
        assertEquals(genre.toUpperCase() + " Music " + trackNumber
                + " Playing", commandResult.feedbackToUser);
        musicCommand = new MusicCommand("stop");
        musicCommand.execute();

        genre = "classic";
        trackNumber = "1";
        musicCommand = new MusicCommand("play", genre);
        commandResult = musicCommand.execute();
        assertEquals(genre.toUpperCase() + " Music " + trackNumber
                + " Playing", commandResult.feedbackToUser);
        musicCommand = new MusicCommand("stop");
        musicCommand.execute();
    }

    @Test
    public void equals() {

        MusicCommand musicFirstCommand = new MusicCommand("play");

        // same object -> returns true
        assertTrue(musicFirstCommand.equals(musicFirstCommand));

        // same values -> returns true
        MusicCommand emailFirstCommandCopy = new MusicCommand("play");
        assertTrue(musicFirstCommand.equals(emailFirstCommandCopy));

        // different types -> returns false
        assertFalse(musicFirstCommand.equals(1));

        // null -> returns false
        assertFalse(musicFirstCommand.equals(null));
    }
}
```
###### \java\seedu\address\logic\commands\RadioCommandTest.java
``` java
public class RadioCommandTest {

    @Test
    public void execute_radio_wrongGenre() {
        RadioCommand radioCommand = new RadioCommand("play", "nonExistedGenre");
        CommandResult commandResult = radioCommand.execute();
        assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RadioCommand.MESSAGE_USAGE), commandResult.feedbackToUser);
    }

    @Test
    public void execute_radio_wrongCommand() {
        RadioCommand radioCommand = new RadioCommand("wrongCommand", "nonExistedGenre");
        CommandResult commandResult = radioCommand.execute();
        assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RadioCommand.MESSAGE_USAGE), commandResult.feedbackToUser);

        radioCommand = new RadioCommand("play", "nonExistedGenre");
        commandResult = radioCommand.execute();
        assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RadioCommand.MESSAGE_USAGE), commandResult.feedbackToUser);

        radioCommand = new RadioCommand("wrongCommand");
        commandResult = radioCommand.execute();
        assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RadioCommand.MESSAGE_USAGE), commandResult.feedbackToUser);
    }

    @Test
    public void execute_stop_noExistingPlayer() {
        RadioCommand radioCommand = new RadioCommand("stop");
        CommandResult commandResult = radioCommand.execute();
        assertEquals(MESSAGE_NO_RADIO_PLAYING, commandResult.feedbackToUser);

        radioCommand = new RadioCommand("stop", "additionalParameterGenre");
        commandResult = radioCommand.execute();
        assertEquals(MESSAGE_NO_RADIO_PLAYING, commandResult.feedbackToUser);
    }

    @Test
    public void execute_radio_successCommand() {
        String genre = "";
        RadioCommand radioCommand = new RadioCommand("play");
        CommandResult commandResult = radioCommand.execute();
        if (InternetConnectionCheck.isConnectedToInternet()) {
            assertEquals("POP " + RadioCommand.MESSAGE_SUCCESS, commandResult.feedbackToUser);
            radioCommand = new RadioCommand("stop");
            radioCommand.execute();
        } else {
            assertEquals(RadioCommand.MESSAGE_NO_INTERNET, commandResult.feedbackToUser);
        }

        genre = "pop";
        radioCommand = new RadioCommand("play", genre);
        commandResult = radioCommand.execute();
        if (InternetConnectionCheck.isConnectedToInternet()) {
            assertEquals(genre.toUpperCase() + " " + RadioCommand.MESSAGE_SUCCESS, commandResult.feedbackToUser);
            radioCommand = new RadioCommand("stop");
            radioCommand.execute();
        } else {
            assertEquals(RadioCommand.MESSAGE_NO_INTERNET, commandResult.feedbackToUser);
        }

        genre = "chinese";
        radioCommand = new RadioCommand("play", genre);
        commandResult = radioCommand.execute();
        if (InternetConnectionCheck.isConnectedToInternet()) {
            assertEquals(genre.toUpperCase() + " " + RadioCommand.MESSAGE_SUCCESS, commandResult.feedbackToUser);
            radioCommand = new RadioCommand("stop");
            radioCommand.execute();
        } else {
            assertEquals(RadioCommand.MESSAGE_NO_INTERNET, commandResult.feedbackToUser);
        }

        genre = "news";
        radioCommand = new RadioCommand("play", genre);
        commandResult = radioCommand.execute();
        if (InternetConnectionCheck.isConnectedToInternet()) {
            assertEquals(genre.toUpperCase() + " " + RadioCommand.MESSAGE_SUCCESS, commandResult.feedbackToUser);
            radioCommand = new RadioCommand("stop");
            radioCommand.execute();
        } else {
            assertEquals(RadioCommand.MESSAGE_NO_INTERNET, commandResult.feedbackToUser);
        }

        genre = "classic";
        radioCommand = new RadioCommand("play", genre);
        commandResult = radioCommand.execute();
        if (InternetConnectionCheck.isConnectedToInternet()) {
            assertEquals(genre.toUpperCase() + " " + RadioCommand.MESSAGE_SUCCESS, commandResult.feedbackToUser);
            radioCommand = new RadioCommand("stop");
            radioCommand.execute();
        } else {
            assertEquals(RadioCommand.MESSAGE_NO_INTERNET, commandResult.feedbackToUser);
        }
    }

    @Test
    public void equals() {

        RadioCommand radioFirstCommand = new RadioCommand("play");

        // same object -> returns true
        assertTrue(radioFirstCommand.equals(radioFirstCommand));

        // same values -> returns true
        RadioCommand emailFirstCommandCopy = new RadioCommand("play");
        assertTrue(radioFirstCommand.equals(emailFirstCommandCopy));

        // different types -> returns false
        assertFalse(radioFirstCommand.equals(1));

        // null -> returns false
        assertFalse(radioFirstCommand.equals(null));
    }
}

```
###### \java\seedu\address\logic\commands\ShareCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code ShareCommand}.
 */
public class ShareCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private String[] shareEmailArray = {"unifycs2103@gmail.com"};

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        ShareCommand shareCommand = prepareCommand(outOfBoundIndex, shareEmailArray);
        assertCommandFailure(shareCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexRecipient() throws Exception {

        shareEmailArray = new String[]{"unifycs2103@gmail.com", "-1"};
        ShareCommand shareCommand = prepareCommand(INDEX_FIRST_PERSON, shareEmailArray);
        try {
            CommandResult commandResult = shareCommand.execute();
            if (InternetConnectionCheck.isConnectedToInternet()) {
                assertEquals(ShareCommand.MESSAGE_FAILURE, commandResult.feedbackToUser);
            } else {
                assertEquals(ShareCommand.MESSAGE_NO_INTERNET, commandResult.feedbackToUser);
            }
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }
    }

    @Test
    public void execute_share_success() {

        ShareCommand shareCommand = prepareCommand(INDEX_FIRST_PERSON, shareEmailArray);
        try {
            CommandResult commandResult = shareCommand.execute();
            if (InternetConnectionCheck.isConnectedToInternet()) {
                assertEquals(ShareCommand.MESSAGE_SUCCESS, commandResult.feedbackToUser);
            } else {
                assertEquals(ShareCommand.MESSAGE_NO_INTERNET, commandResult.feedbackToUser);
            }
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        shareCommand = prepareCommand(INDEX_SECOND_PERSON, shareEmailArray);
        try {
            CommandResult commandResult = shareCommand.execute();
            if (InternetConnectionCheck.isConnectedToInternet()) {
                assertEquals(ShareCommand.MESSAGE_SUCCESS, commandResult.feedbackToUser);
            } else {
                assertEquals(ShareCommand.MESSAGE_NO_INTERNET, commandResult.feedbackToUser);
            }
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        shareEmailArray = new String[]{"unifycs2103@gmail.com", "1"};
        shareCommand = prepareCommand(INDEX_FIRST_PERSON, shareEmailArray);
        try {
            CommandResult commandResult = shareCommand.execute();
            if (InternetConnectionCheck.isConnectedToInternet()) {
                assertEquals(ShareCommand.MESSAGE_SUCCESS, commandResult.feedbackToUser);
            } else {
                assertEquals(ShareCommand.MESSAGE_NO_INTERNET, commandResult.feedbackToUser);
            }
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }
    }

    @Test
    public void execute_share_invalidEmail() {

        shareEmailArray = new String[]{"wrongemailformat"};
        ShareCommand shareCommand = prepareCommand(INDEX_FIRST_PERSON, shareEmailArray);

        try {
            CommandResult commandResult = shareCommand.execute();
            if (InternetConnectionCheck.isConnectedToInternet()) {
                assertEquals(ShareCommand.MESSAGE_EMAIL_NOT_VALID, commandResult.feedbackToUser);
            } else {
                assertEquals(ShareCommand.MESSAGE_NO_INTERNET, commandResult.feedbackToUser);
            }
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        shareEmailArray = new String[]{"unifycs2103@gmail.com", "wrongemailformat"};
        shareCommand = prepareCommand(INDEX_FIRST_PERSON, shareEmailArray);

        try {
            CommandResult commandResult = shareCommand.execute();
            if (InternetConnectionCheck.isConnectedToInternet()) {
                assertEquals(ShareCommand.MESSAGE_EMAIL_NOT_VALID, commandResult.feedbackToUser);
            } else {
                assertEquals(ShareCommand.MESSAGE_NO_INTERNET, commandResult.feedbackToUser);
            }
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }
    }

    @Test
    public void equals() {

        ShareCommand shareFirstCommand = new ShareCommand(INDEX_FIRST_PERSON, shareEmailArray);
        ShareCommand shareSecondCommand = new ShareCommand(INDEX_SECOND_PERSON, shareEmailArray);

        // same object -> returns true
        assertTrue(shareFirstCommand.equals(shareFirstCommand));

        // same values -> returns true
        ShareCommand emailFirstCommandCopy = new ShareCommand(INDEX_FIRST_PERSON, shareEmailArray);
        assertTrue(shareFirstCommand.equals(emailFirstCommandCopy));

        // different types -> returns false
        assertFalse(shareFirstCommand.equals(1));

        // null -> returns false
        assertFalse(shareFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(shareFirstCommand.equals(shareSecondCommand));
    }


    /**
     * Returns a {@code ShareCommand} with the parameter {@code index}.
     */
    private ShareCommand prepareCommand(Index index, String[] shareEmailArray) {
        ShareCommand shareCommand = new ShareCommand(index, shareEmailArray);
        shareCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return shareCommand;
    }
}
```
###### \java\seedu\address\logic\parser\AddCommandParserTest.java
``` java
    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withRemark(VALID_REMARK_BOB).withTags(VALID_TAG_FRIEND).build();

        // multiple names - last name accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + REMARK_DESC_BOB
                + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + REMARK_DESC_BOB
                + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple emails - last email accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_AMY
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + REMARK_DESC_BOB
                + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_AMY + ADDRESS_DESC_BOB + REMARK_DESC_BOB
                + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple tags - all accepted
        Person expectedPersonMultipleTags = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withRemark(VALID_REMARK_BOB)
                .withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND).build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB
                        + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + REMARK_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                new AddCommand(expectedPersonMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Person expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withRemark(VALID_REMARK_AMY).withTags().build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + REMARK_DESC_AMY, new AddCommand(expectedPerson));
    }
```
###### \java\seedu\address\logic\parser\AddCommandParserTest.java
``` java
        // invalid tag
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + REMARK_DESC_BOB + INVALID_TAG_DESC
                + VALID_TAG_FRIEND, Tag.MESSAGE_TAG_CONSTRAINTS);
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_music() throws Exception {
        MusicCommand command = (MusicCommand) parser.parseCommand(
                MusicCommand.COMMAND_WORD + " play ");
        assertEquals(new MusicCommand("play"), command);
    }

    @Test
    public void parseCommand_radio() throws Exception {
        RadioCommand command = (RadioCommand) parser.parseCommand(
                RadioCommand.COMMAND_WORD + " play ");
        assertEquals(new RadioCommand("play"), command);
    }

    @Test
    public void parseCommand_share() throws Exception {
        String[] shareEmailArray = {"unifycs2103@gmail.com"};
        ShareCommand command = (ShareCommand) parser.parseCommand(
                ShareCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased()
                        + " s/" + shareEmailArray);
        assertEquals(new ShareCommand(INDEX_FIRST_PERSON, shareEmailArray) , command);
    }
```
###### \java\seedu\address\logic\parser\MusicCommandParserTest.java
``` java
public class MusicCommandParserTest {

    private MusicCommandParser parser = new MusicCommandParser();

    @Test
    public void parse_validArgs_returnsSelectCommand() {
        assertParseSuccess(parser, "play", new MusicCommand("play"));
        assertParseSuccess(parser, "play", new MusicCommand("play", "pop"));
        assertParseSuccess(parser, "play pop", new MusicCommand("play", "pop"));
        assertParseSuccess(parser, "stop", new MusicCommand("stop"));
        assertParseSuccess(parser, "pause", new MusicCommand("pause"));
    }
    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "any argument 23232",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MusicCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "play pop wdwdwdw",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MusicCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\logic\parser\RadioCommandParserTest.java
``` java
public class RadioCommandParserTest {

    private RadioCommandParser parser = new RadioCommandParser();

    @Test
    public void parse_validArgs_returnsSelectCommand() {
        assertParseSuccess(parser, "play", new RadioCommand("play"));
        assertParseSuccess(parser, "play", new RadioCommand("play", "pop"));
        assertParseSuccess(parser, "play pop", new RadioCommand("play", "pop"));
        assertParseSuccess(parser, "stop", new RadioCommand("stop"));
    }
    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "any argument 23232",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RadioCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\logic\parser\ShareCommandParserTest.java
``` java
public class ShareCommandParserTest {

    private ShareCommandParser parser = new ShareCommandParser();

    @Test
    public void parse_validArgs_returnsSelectCommand() {
        String[] shareEmailArray = {"unifycs2103@gmail.com"};
        Index targetIndex = INDEX_FIRST_PERSON;
        assertParseSuccess(parser, "1 s/unifycs2103@gmail.com", new ShareCommand(targetIndex, shareEmailArray));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "any argument",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShareCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShareCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "    ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShareCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "1 s/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShareCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "0",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShareCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "-1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShareCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "1 1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShareCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\model\person\RemarkTest.java
``` java
public class RemarkTest {

    @Test
    public void equals() {
        Remark remark = new Remark("Hello");

        //same object -> returns true
        assertTrue(remark.equals(remark));

        //same values -> returns true
        Remark remarkCopy = new Remark(remark.value);
        assertTrue(remark.equals(remarkCopy));

        //different types -> return false
        assertFalse(remark.equals(1));

        //null -> returns false
        assertFalse(remark.equals(null));

        //different person -> returns false
        Remark differentRemark = new Remark("Bye");
        assertFalse(remark.equals(differentRemark));
    }

    @Test
    public void hashCodeTest() throws IllegalValueException {
        Remark remarkStub = new Remark("remark stub");
        assertEquals("remark stub".hashCode(), remarkStub.hashCode());

        Remark remarkStub2 = new Remark("remark stub 2");
        assertEquals("remark stub 2".hashCode(), remarkStub2.hashCode());
    }
}
```
###### \java\seedu\address\testutil\EditPersonDescriptorBuilder.java
``` java
    /**
     * Sets the {@code Remark} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withRemark(String remark) {
        return this;
    }
```
###### \java\seedu\address\testutil\PersonBuilder.java
``` java
/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_REMARK = "some remarks";
    public static final String DEFAULT_TAGS = "friends";
    public static final String DEFAULT_AVATAR = "";

    private Person person;

    public PersonBuilder() {
        try {
            Name defaultName = new Name(DEFAULT_NAME);
            Phone defaultPhone = new Phone(DEFAULT_PHONE);
            Email defaultEmail = new Email(DEFAULT_EMAIL);
            Address defaultAddress = new Address(DEFAULT_ADDRESS);
            Remark defaultRemark = new Remark(DEFAULT_REMARK);
            Set<Tag> defaultTags = SampleDataUtil.getTagSet(DEFAULT_TAGS);
            Avatar defaultAvatar = new Avatar(DEFAULT_AVATAR);
            this.person = new Person(defaultName, defaultPhone, defaultEmail,
                    defaultAddress, defaultRemark, defaultAvatar, defaultTags);
        } catch (IllegalValueException ive) {
            throw new AssertionError("Default person's values are invalid.");
        }
    }
```
###### \java\seedu\address\testutil\PersonBuilder.java
``` java
    /**
     * Sets the {@code Remark} of the {@code Person} that we are building.
     */
    public PersonBuilder withRemark(String remark) {
        this.person.setRemark(new Remark(remark));
        return this;
    }
```
###### \java\seedu\address\testutil\PersonUtil.java
``` java
        sb.append(PREFIX_REMARK + person.getRemark().value + " ");
```
###### \java\seedu\address\testutil\TypicalPersons.java
``` java
    public static final ReadOnlyPerson GEORGE = new PersonBuilder().withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").withAddress("4th street").withRemark("Likes to swim").build();
```
###### \java\seedu\address\testutil\TypicalPersons.java
``` java
    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final ReadOnlyPerson AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
            .withRemark(VALID_REMARK_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final ReadOnlyPerson BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
            .withRemark(VALID_REMARK_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();
```
###### \java\systemtests\AddCommandSystemTest.java
``` java
        /* Case: add a duplicate person -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + REMARK_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_PERSON);

        /* Case: add a duplicate person except with different tags -> rejected */
        // "friends" is an existing tag used in the default model, see TypicalPersons#ALICE
        // This test will fail is a new tag that is not in the model is used, see the bug documented in
        // AddressBook#addPerson(ReadOnlyPerson)
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + REMARK_DESC_AMY + " " + PREFIX_TAG.getPrefix() + "friends";
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_PERSON);

        /* Case: add a person with all fields same as another person in the address book except name -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_AMY).withRemark(VALID_REMARK_AMY).withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + REMARK_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: add a person with all fields same as another person in the address book except phone -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_AMY).withRemark(VALID_REMARK_AMY).withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_BOB + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + REMARK_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: add a person with all fields same as another person in the address book except email -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_AMY).withRemark(VALID_REMARK_AMY).withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_BOB + ADDRESS_DESC_AMY
                + REMARK_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: add a person with all fields same as another person in the address book except address -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_BOB).withRemark(VALID_REMARK_AMY).withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_BOB
                + REMARK_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);
```
###### \java\systemtests\AddCommandSystemTest.java
``` java
        /* Case: missing name -> rejected */
        command = AddCommand.COMMAND_WORD + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + REMARK_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing phone -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + REMARK_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing email -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + ADDRESS_DESC_AMY
                + REMARK_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing address -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + REMARK_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
```
###### \java\systemtests\AddCommandSystemTest.java
``` java
        /* Case: invalid name -> rejected */
        command = AddCommand.COMMAND_WORD + INVALID_NAME_DESC + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + REMARK_DESC_AMY;
        assertCommandFailure(command, Name.MESSAGE_NAME_CONSTRAINTS);

        /* Case: invalid phone -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + INVALID_PHONE_DESC + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + REMARK_DESC_AMY;
        assertCommandFailure(command, Phone.MESSAGE_PHONE_CONSTRAINTS);

        /* Case: invalid email -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + INVALID_EMAIL_DESC + ADDRESS_DESC_AMY
                + REMARK_DESC_AMY;
        assertCommandFailure(command, Email.MESSAGE_EMAIL_CONSTRAINTS);
```
###### \java\systemtests\AddCommandSystemTest.java
``` java
        /* Case: invalid tag -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + REMARK_DESC_AMY + INVALID_TAG_DESC;
        assertCommandFailure(command, Tag.MESSAGE_TAG_CONSTRAINTS);

        /* Case: invalid avatar -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + REMARK_DESC_AMY + INVALID_AVATAR_DESC;
        assertCommandFailure(command, Avatar.MESSAGE_AVATAR_CONSTRAINTS);
```
