# nicholaschuayunzhi
###### \java\seedu\address\commons\events\ui\CommandInputChangedEvent.java
``` java
public class CommandInputChangedEvent extends BaseEvent {

    public final String currentInput;

    public CommandInputChangedEvent(String newCurrentInput) {
        currentInput = newCurrentInput;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\util\TextUtil.java
``` java

public class TextUtil {

    static final Text HELPER;
    static final double DEFAULT_WRAPPING_WIDTH;
    static final double DEFAULT_LINE_SPACING;
    static final String DEFAULT_TEXT;
    static final TextBoundsType DEFAULT_BOUNDS_TYPE;
    static {
        HELPER = new Text();
        DEFAULT_WRAPPING_WIDTH = HELPER.getWrappingWidth();
        DEFAULT_LINE_SPACING = HELPER.getLineSpacing();
        DEFAULT_TEXT = HELPER.getText();
        DEFAULT_BOUNDS_TYPE = HELPER.getBoundsType();
    }

    /**
     * Return's Text Width based on {@code Font font, String text}
     */
    public static double computeTextWidth(Font font, String text, double help0) {
        HELPER.setText(text);
        HELPER.setFont(font);

        HELPER.setWrappingWidth(0.0D);
        HELPER.setLineSpacing(0.0D);
        double d = Math.min(HELPER.prefWidth(-1.0D), help0);
        HELPER.setWrappingWidth((int) Math.ceil(d));
        d = Math.ceil(HELPER.getLayoutBounds().getWidth());

        HELPER.setWrappingWidth(DEFAULT_WRAPPING_WIDTH);
        HELPER.setLineSpacing(DEFAULT_LINE_SPACING);
        HELPER.setText(DEFAULT_TEXT);
        return d;
    }
}
```
###### \java\seedu\address\logic\commands\AddCommand.java
``` java
            toAdd.saveAvatar();
```
###### \java\seedu\address\logic\commands\FindCommand.java
``` java
/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose details contain ALL of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: PREFIX/KEYWORD [MORE_PREFIX/KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " n/alice a/Blk 100 Street t/friend";

    private final Predicate predicate;

    public FindCommand(Predicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
```
###### \java\seedu\address\logic\commands\FindCommand.java
``` java
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && this.predicate.equals(((FindCommand) other).predicate)); // state check
    }

}
```
###### \java\seedu\address\logic\commands\hints\AddCommandHint.java
``` java
/**
 * Generates hint and tab auto complete for add command
 * Assumes that {@code userInput} and {@code arguments} provided are from
 * an incomplete/complete add command.
 */
public class AddCommandHint extends ArgumentsHint {

    private static final List<Prefix> PREFIXES =
            Arrays.asList(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_REMARK,
            PREFIX_TAG, PREFIX_AVATAR);


    private List<Prefix> possiblePrefixesToComplete;

    public AddCommandHint(String userInput, String arguments) {
        this.userInput = userInput;
        this.arguments = arguments;
        parse();
    }

    /**
     * parses {@code userInput} and {@code arguments}
     * sets appropriate {@code argumentHint}, {@code description}, {@code onTab}
     * for Add Command
     */
    private void parse() {
        possiblePrefixesToComplete = HintUtil.getUncompletedPrefixes(arguments, PREFIXES);

        Optional<String> prefixCompletionOptional =
                HintUtil.findPrefixCompletionHint(arguments, possiblePrefixesToComplete);

        // are we completing a hint?
        // case: add n|/
        if (prefixCompletionOptional.isPresent()) {
            handlePrefixCompletion(prefixCompletionOptional.get(), possiblePrefixesToComplete);
            return;
        }

        // can we tab through prefixes
        // case: add n/|
        Optional<Prefix> endPrefix = HintUtil.findEndPrefix(userInput, PREFIXES);
        if (endPrefix.isPresent()) {
            handlePrefixTabbing(endPrefix.get(), PREFIXES, possiblePrefixesToComplete);
            return;
        }

        // we should offer a hint
        // case: add n/* |
        handleOfferHint(PREFIXES);
    }

    @Override
    protected String getDescription(Prefix prefix) {
        switch (prefix.toString()) {
        case PREFIX_NAME_STRING:
            return "name";
        case PREFIX_PHONE_STRING:
            return "phone";
        case PREFIX_ADDRESS_STRING:
            return "address";
        case PREFIX_EMAIL_STRING:
            return "email";
        case PREFIX_TAG_STRING:
            return "tag (optional)";
        case PREFIX_AVATAR_STRING:
            return "avatar file path (optional)";
        case PREFIX_REMARK_STRING:
            return "remark (optional)";
        default:
            return "";
        }
    }

}
```
###### \java\seedu\address\logic\commands\hints\AliasCommandHint.java
``` java
/**
 * Generates hint and tab auto complete for alias command
 * Assumes that {@code userInput} and {@code arguments} provided are from
 * an incomplete/complete alias command.
 */
public class AliasCommandHint extends Hint {

    public AliasCommandHint(String userInput, String arguments) {
        this.userInput = userInput;
        this.arguments = arguments;
        argumentHint = "";
        parse();
    }

    @Override
    public String autocomplete() {
        String whitespace = userInput.endsWith(" ") ? "" : " ";
        return userInput + whitespace;
    }

    /**
     * parses {@code userInput} and {@code arguments}
     * sets appropriate {@code argumentHint}, {@code description}
     * for an Alias Command
     */
    private void parse() {

        if (arguments.length() == 0 && !userInput.endsWith(" ")) {
            description = " shows all aliases";
            return;
        }
        int delimiterPosition = arguments.trim().indexOf(' ');

        if ((delimiterPosition == -1 && userInput.endsWith(" ") && !arguments.isEmpty()) || delimiterPosition > 0) {
            int fakeDelimiterPosition = (arguments + " pad").trim().indexOf(' ');
            description = " - set what "
                    + (arguments + " pad").trim().substring(0, fakeDelimiterPosition).trim() + " represents";
            return;
        }

        description = " - set your new command word";
        return;
    }
}
```
###### \java\seedu\address\logic\commands\hints\ArgumentsHint.java
``` java
/**
 * Template class for hints that have arguments in their command format
 * These arguments include prefixes and indices.
 * Specifies autocomplete to return {@code onTab}
 */
public abstract class ArgumentsHint extends Hint {

    protected String onTab = "";

    protected String getDescription(Prefix p) {
        return "description";
    }

    /**
     * Should be used when prefix is half completed
     * {@code prefixCompletion} which is auto completed portion of the prefix (ie "/")
     * {@code possbilePrefixesToComplete} required to parse for prefix to get description TODO: rethink this
     * updates {@code onTab} to return {@code userInput} auto completed with {@code prefixCompletion}
     */
    protected void handlePrefixCompletion(String prefixCompletion, List<Prefix> possiblePrefixesToComplete) {
        argumentHint = prefixCompletion;
        Prefix p = HintUtil.findPrefixOfCompletionHint(arguments, possiblePrefixesToComplete);
        description = getDescription(p);
        onTab = userInput + argumentHint;
        LOGGER.info("ArgumentsHint - Handled Prefix Completion");
    }

    /**
     * Should be used when prefix is completed and caret is next to prefix (ie: n/|)
     * {@code currentPrefix} that is completed
     * {@code possiblePrefixesToComplete, prefixList} to generate the next prefix option on pressing tab
     * updates {@code onTab} to return {@code userInput} auto completed with the next prefix option
     */
    protected void handlePrefixTabbing(Prefix currentPrefix,
                                       List<Prefix> prefixList,
                                       List<Prefix> possiblePrefixesToComplete) {
        argumentHint = "";
        description = getDescription(currentPrefix);
        Prefix nextPrefix = generateNextPrefix(currentPrefix, prefixList, possiblePrefixesToComplete);
        onTab = userInput.substring(0, userInput.length() - 2) + nextPrefix.toString();
        LOGGER.info("ArgumentsHint - Handled Prefix Tabbing");
    }

    /**
     * generates next prefix given {@code currentPrefix} and {@code possiblePrefixesToComplete}\
     * uses {@code prefixList} as an ordering so that we can cycle through all prefixes in the same order
     */
    private Prefix generateNextPrefix(Prefix currentPrefix,
                                      List<Prefix> prefixList,
                                      List<Prefix> possiblePrefixesToComplete) {
        int startIndex = prefixList.indexOf(currentPrefix);
        for (int i = 0; i < prefixList.size(); i++) {
            int index = (i + startIndex) % prefixList.size();
            if (possiblePrefixesToComplete.contains(prefixList.get(index))) {
                return prefixList.get(index);
            }
        }
        return currentPrefix;
    }

    /**
     * Should be used when you want the addressbook to offer the next argument (ie n/nicholas |)
     * {@code prefixList} to generate the next prefix hint
     * updates {@code onTab} to return {@code userInput} auto completed with the next prefix hint
     */
    protected void handleOfferHint(List<Prefix> prefixList) {
        String whitespace = userInput.endsWith(" ") ? "" : " ";
        Prefix offeredPrefix = HintUtil.offerHint(arguments, prefixList);
        argumentHint = whitespace + offeredPrefix.toString();
        description = getDescription(offeredPrefix);
        onTab = userInput + argumentHint;
        LOGGER.info("ArgumentsHint - Handled Prefix Offer");
    }

    /**
     * Should be used when you want the addressbook to offer an index hint (ie delete|)
     * updates {@code onTab} to return {@code userInput} auto completed with a default index of 1
     * TODO: default index based on current model
     */
    protected void handleOfferIndex(String userInput) {
        String whitespace = userInput.endsWith(" ") ? "" : " ";
        argumentHint = whitespace + "1";
        description = " index";
        onTab = userInput + argumentHint;
        LOGGER.info("ArgumentsHint - Handled Index Offer");
    }

    /**
     * Should be used when index is written and caret is directly next to index (ie edit 12|)
     * updates {@code onTab} to return {@code userInput} with an increment to the index
     * TODO: index update based on current model
     */
    protected void handleIndexTabbing(int currentIndex) {
        argumentHint = "";
        description = " index";
        int length = String.valueOf(currentIndex).length();

        int newIndex = currentIndex + 1;
        onTab = userInput.substring(0, userInput.length() - length) + newIndex;
        LOGGER.info("ArgumentsHint - Handled Index Tabbing");
    }

    public String autocomplete() {
        return onTab;
    }

}
```
###### \java\seedu\address\logic\commands\hints\ClearCommandHint.java
``` java
/**
 * Generates description for Clear Command
 * Assumes that {@code userInput} are from a Clear Command.
 */
public class ClearCommandHint extends NoArgumentsHint {

    protected static final String CLEAR_COMMAND_DESC = "clears all contacts";

    public ClearCommandHint(String userInput) {
        this.userInput = userInput;
        this.description = CLEAR_COMMAND_DESC;
        parse();
    }

}
```
###### \java\seedu\address\logic\commands\hints\CommandHint.java
``` java
/**
 * Generates hint and autocompletion for any uncompleted command word
 */
public class CommandHint extends Hint {

    protected String autoCompleteCommandWord = "";

    protected String commandWord;

    public CommandHint(String userInput, String commandWord) {
        this.userInput = userInput;
        this.commandWord = commandWord;
        parse();
    }

    @Override
    public String autocomplete() {
        return autoCompleteCommandWord;
    }

    /**
     * parses {@code userInput} and {@code commandWord}
     * sets appropriate {@code argumentHint}, {@code description}
     * for any uncompleted command word
     */
    private void parse() {
        String autocompleted = Autocomplete.autocompleteCommand(commandWord);

        if (autocompleted == null) {
            description = " type help for user guide";
            argumentHint = "";
        } else {
            autoCompleteCommandWord = autocompleted + " ";
            argumentHint = StringUtil.difference(commandWord, autocompleted) + " ";
            description = getDescription(autocompleted);
        }
    }

    private static String getDescription(String commandWord) {

        Aliases aliases = UserPrefs.getInstance().getAliases();
        String aliasedCommand = aliases.getCommand(commandWord);

        commandWord = aliasedCommand != null ? aliasedCommand : commandWord;

        switch (commandWord) {
        case AddCommand.COMMAND_WORD:
            return "adds a person";
        case EditCommand.COMMAND_WORD:
            return "edits a person";
        case FindCommand.COMMAND_WORD:
            return "finds a person";
        case SelectCommand.COMMAND_WORD:
            return "selects a person";
        case DeleteCommand.COMMAND_WORD:
            return "deletes a person";
        case ShareCommand.COMMAND_WORD:
            return "shares a contact via email";
        case ClearCommand.COMMAND_WORD:
            return ClearCommandHint.CLEAR_COMMAND_DESC;
        case ListCommand.COMMAND_WORD:
            return ListCommandHint.LIST_COMMAND_DESC;
        case HistoryCommand.COMMAND_WORD:
            return HistoryCommandHint.HISTORY_COMMAND_DESC;
        case ExitCommand.COMMAND_WORD:
            return ExitCommandHint.EXIT_COMMAND_DESC;
        case UndoCommand.COMMAND_WORD:
            return UndoCommandHint.UNDO_COMMAND_DESC;
        case RedoCommand.COMMAND_WORD:
            return RedoCommandHint.REDO_COMMAND_DESC;
        case HelpCommand.COMMAND_WORD:
            return HelpCommandHint.HELP_COMMAND_DESC;
        case MusicCommand.COMMAND_WORD:
            return "plays music";
        case RadioCommand.COMMAND_WORD:
            return "plays the radio";
        case AliasCommand.COMMAND_WORD:
            return "sets or show alias";
        case UnaliasCommand.COMMAND_WORD:
            return "removes alias";
        default:
            return "";
        }
    }
}
```
###### \java\seedu\address\logic\commands\hints\DeleteCommandHint.java
``` java
/**
 * Generates hint and tab auto complete for delete command
 * Assumes that {@code userInput} and {@code arguments} provided are from
 * an incomplete/complete delete command.
 */
public class DeleteCommandHint extends ArgumentsHint {

    public DeleteCommandHint(String userInput, String arguments) {
        this.userInput = userInput;
        this.arguments = arguments;
        parse();

    }

    /**
     * parses {@code userInput} and {@code arguments}
     * sets appropriate {@code argumentHint}, {@code description}, {@code onTab}
     * for Delete Command
     */
    private void parse() {
        //case : delete *|
        if (!HintUtil.hasIndex(arguments)) {
            handleOfferIndex(userInput);
            return;
        }

        if (Character.isDigit(userInput.charAt(userInput.length() - 1))) {
            //case delete 1|
            handleIndexTabbing(HintUtil.getIndex(arguments));
            return;
        }

        description = "";
        argumentHint = "";
        onTab = userInput;
    }
}
```
###### \java\seedu\address\logic\commands\hints\EditCommandHint.java
``` java
/**
 * Generates hint and tab auto complete for edit command
 * Assumes that {@code userInput} and {@code arguments} provided are from
 * an incomplete/complete edit command.
 */
public class EditCommandHint extends ArgumentsHint {

    private static final List<Prefix> PREFIXES =
            Arrays.asList(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_REMARK, PREFIX_TAG);

    private List<Prefix> possiblePrefixesToComplete;

    public EditCommandHint(String userInput, String arguments) {
        this.userInput = userInput;
        this.arguments = arguments;
        parse();
    }

    /**
     * parses {@code userInput} and {@code arguments}
     * sets appropriate {@code argumentHint}, {@code description}, {@code onTab}
     * for Edit Command
     */
    private void parse() {
        //case : edit *|
        if (!HintUtil.hasPreambleIndex(arguments)) {
            handleOfferIndex(userInput);
            return;
        }

        if (HintUtil.hasIndex(arguments) && Character.isDigit(userInput.charAt(userInput.length() - 1))) {
            handleIndexTabbing(HintUtil.getPreambleIndex(arguments, PREFIXES));
            return;
        }

        possiblePrefixesToComplete = HintUtil.getUncompletedPrefixes(arguments, PREFIXES);
        Optional<String> prefixCompletionOptional =
                HintUtil.findPrefixCompletionHint(arguments, possiblePrefixesToComplete);

        // are we completing a hint?
        // case: edit 1 n|/
        if (prefixCompletionOptional.isPresent()) {
            handlePrefixCompletion(prefixCompletionOptional.get(), possiblePrefixesToComplete);
            return;
        }

        // can we tab through prefixes
        // case: edit 1 n/|
        Optional<Prefix> endPrefix = HintUtil.findEndPrefix(userInput, PREFIXES);
        if (endPrefix.isPresent()) {
            handlePrefixTabbing(endPrefix.get(), PREFIXES, possiblePrefixesToComplete);
            return;
        }

        // we should offer a hint
        // case: edit 1 n/* |
        handleOfferHint(PREFIXES);
    }

    @Override
    protected String getDescription(Prefix prefix) {
        switch (prefix.toString()) {
        case PREFIX_NAME_STRING:
            return "name";
        case PREFIX_PHONE_STRING:
            return "phone";
        case PREFIX_ADDRESS_STRING:
            return "address";
        case PREFIX_EMAIL_STRING:
            return "email";
        case PREFIX_REMARK_STRING:
            return "remark";
        case PREFIX_TAG_STRING:
            return "tag";
        default:
            return "";
        }
    }
}
```
###### \java\seedu\address\logic\commands\hints\ExitCommandHint.java
``` java
/**
 * Generates description for Exit Command
 * Assumes that {@code userInput} are from an exit command.
 */
public class ExitCommandHint extends NoArgumentsHint {

    protected static final String EXIT_COMMAND_DESC = "exits the application";

    public ExitCommandHint(String userInput) {
        this.userInput = userInput;
        this.description = EXIT_COMMAND_DESC;
        parse();
    }
}
```
###### \java\seedu\address\logic\commands\hints\FindCommandHint.java
``` java
/**
 * Generates hint and tab auto complete for find command
 * Assumes that {@code userInput} and {@code arguments} provided are from
 * an incomplete/complete find command.
 */
public class FindCommandHint extends ArgumentsHint {

    private static final List<Prefix> PREFIXES =
            Arrays.asList(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_REMARK, PREFIX_TAG);

    public FindCommandHint(String userInput, String arguments) {
        this.userInput = userInput;
        this.arguments = arguments;
        parse();
    }

    /**
     * parses {@code userInput} and {@code arguments}
     * sets appropriate {@code argumentHint}, {@code description}, {@code onTab}
     * for Find Command
     */
    private void parse() {
        Optional<String> prefixCompletionOptional = HintUtil.findPrefixCompletionHint(arguments, PREFIXES);

        // are we completing a hint?
        // case: find n|/
        if (prefixCompletionOptional.isPresent()) {
            handlePrefixCompletion(prefixCompletionOptional.get(), PREFIXES);
            return;
        }

        // can we tab through prefixes
        // case: find n/|
        Optional<Prefix> endPrefix = HintUtil.findEndPrefix(userInput, PREFIXES);
        if (endPrefix.isPresent()) {

            //we can offer duplicates, so offer every other prefix
            List<Prefix> otherPrefixes =
                    PREFIXES
                            .stream()
                            .filter(p -> !p.equals(endPrefix.get()))
                            .collect(Collectors.toList());

            handlePrefixTabbing(endPrefix.get(), PREFIXES, otherPrefixes);
            return;
        }

        // we should offer a hint
        // case: find n/* |

        //TODO: never exhaust the prefix offers
        handleOfferHint(PREFIXES);
    }

    @Override
    protected String getDescription(Prefix prefix) {
        switch (prefix.toString()) {
        case PREFIX_NAME_STRING:
            return "name";
        case PREFIX_PHONE_STRING:
            return "phone";
        case PREFIX_ADDRESS_STRING:
            return "address";
        case PREFIX_EMAIL_STRING:
            return "email";
        case PREFIX_TAG_STRING:
            return "tag";
        case PREFIX_REMARK_STRING:
            return "remark";
        default:
            return "";
        }
    }
}
```
###### \java\seedu\address\logic\commands\hints\FixedArgumentsHint.java
``` java
/**
 * Template class for hints that have fixed arguments
 * Specifies autocomplete to return the {@code autoCorrectInput}
 * As the arguments are not variable, we can easily help the user autocorrect his input based on the situation.
 */
public abstract class FixedArgumentsHint extends Hint {

    protected String autoCorrectInput;

    protected String descriptionFromArg(String arg) {
        return "";
    }

    @Override
    public String autocomplete() {
        return autoCorrectInput;
    }

    /**
     * return the next argument after {@code arg} in {@code args}
     * asserts that args contains arg
     */
    protected String nextArg(String arg, String[] args) {
        List<String> argsList = Arrays.asList(args);
        assert argsList.contains(arg);

        int index = argsList.indexOf(arg);
        return argsList.get((index + 1) % argsList.size());
    }

    /**
     * offers argument hint based on {@code arg}
     * sets tab to return {@code autoCorrectInput}
     */
    protected void offerHint(String arg, String autoCorrectInput) {
        String whitespace = userInput.endsWith(" ") ? "" : " ";
        argumentHint = whitespace + arg;
        this.autoCorrectInput = autoCorrectInput;
        description = descriptionFromArg(arg);
    }

    /**
     * returns true if {@code args} contains {@code arg}
     */
    protected boolean isValidFixedArg(String arg, String[] args) {
        for (String s : args) {
            if (arg.equals(s)) {
                return true;
            }
        }

        return false;
    }
    /**
     * Should be used when argument is half completed
     * {@code autoCompletedArg} is the completed argument of {@code arg}
     * updates {@code autoCorrectInput} with given {@code autoCorrectInput}
     */
    protected void handleCompletingArg(String arg, String autoCompletedArg, String autoCorrectInput) {
        requireNonNull(autoCompletedArg);
        argumentHint = StringUtil.difference(arg, autoCompletedArg);
        description = descriptionFromArg(autoCompletedArg);
        this.autoCorrectInput = autoCorrectInput;
    }
    /**
     * Should be used when argument completed and is cyclable
     * {@code arg} is the current completed argument
     * {@code args} is an array of other possible arguments
     * {@code inputBeforeArg} specifies the userInput before the argument that will remain the same on tab
     */
    protected void handleNextArg(String arg, String[] args, String inputBeforeArg) {
        argumentHint = "";
        autoCorrectInput = inputBeforeArg + " " + nextArg(arg, args);
        description = descriptionFromArg(arg);
    }

    /**
     * Should be used when arguments are completed and are not cyclable
     * {@code finalArg} is the last completed argument
     */
    protected void handleFinishedArgs(String finalArg) {
        argumentHint = "";
        description = descriptionFromArg(finalArg);
        autoCorrectInput = userInput;
    }

}
```
###### \java\seedu\address\logic\commands\hints\HelpCommandHint.java
``` java
/**
 * Generates description for Help Command
 * Assumes that {@code userInput} are from Help Command.
 */
public class HelpCommandHint extends NoArgumentsHint {

    protected static final String HELP_COMMAND_DESC = "shows user guide";

    public HelpCommandHint(String userInput) {
        this.userInput = userInput;
        this.description = HELP_COMMAND_DESC;
        parse();
    }
}
```
###### \java\seedu\address\logic\commands\hints\Hint.java
``` java
/**
 * Hints for inline hint suggestion and tab autocompletion
 */
public abstract class Hint {

    protected static final Logger LOGGER = LogsCenter.getLogger(Hint.class);

    protected String argumentHint;
    protected String description;
    protected String userInput;
    protected String arguments;

    /**
     * returns the new user input when user presses tab
     */
    public abstract String autocomplete();

    /**
     * returns the argument hint of current user input
     */
    public String getArgumentHint() {
        return argumentHint;
    }

    /**
     * returns the description of current user input
     */
    public String getDescription() {
        return description;
    }

    /**
     * asserts that require info is non null
     * should be called after parse()
     */
    public final void requireFieldsNonNull() {
        requireNonNull(argumentHint);
        requireNonNull(description);
        requireNonNull(userInput);
        requireNonNull(autocomplete());
    }


}
```
###### \java\seedu\address\logic\commands\hints\HistoryCommandHint.java
``` java
/**
 * Generates description for History Command
 * Assumes that {@code userInput} are from a History Command.
 */
public class HistoryCommandHint extends NoArgumentsHint {

    protected static final String HISTORY_COMMAND_DESC = "shows command history";

    public HistoryCommandHint(String userInput) {
        this.userInput = userInput;
        this.description = HISTORY_COMMAND_DESC;
        parse();
    }
}
```
###### \java\seedu\address\logic\commands\hints\ListCommandHint.java
``` java
/**
 * Generates description for List Command
 * Assumes that {@code userInput} are from a List Command.
 */
public class ListCommandHint extends NoArgumentsHint {

    protected static final String LIST_COMMAND_DESC = "lists all contacts";

    public ListCommandHint(String userInput) {
        this.userInput = userInput;
        this.description = LIST_COMMAND_DESC;
        parse();
    }
}
```
###### \java\seedu\address\logic\commands\hints\MusicCommandHint.java
``` java
/**
 * Generates hint and tab auto complete for Music command
 * Assumes that {@code userInput} and {@code arguments} provided are from
 * an incomplete/complete Music command.
 */
public class MusicCommandHint extends FixedArgumentsHint {

    private static final String[] ACTION = new String[] {"play", "stop"};
    private static final String[] GENRE = MusicCommand.GENRE_LIST;

    public MusicCommandHint(String userInput, String arguments) {
        this.userInput = userInput;
        this.arguments = arguments;
        parse();
    }

    /**
     * parses {@code userInput} and {@code arguments}
     * sets appropriate {@code argumentHint}, {@code description}, {@code autoCorrectInput}
     * for Music Command
     */
    private void parse() {

        String[] args = arguments.trim().split("\\s+");

        String actionArgument = args[0];
        if (!isValidFixedArg(actionArgument, ACTION)) {
            //completing an arg?
            String autoCompletedArg = Autocomplete.autocompleteFromList(actionArgument, ACTION);
            if (autoCompletedArg == null || actionArgument.isEmpty()) {
                String autoCorrectHint = (MusicCommand.getIsMusicPlaying()) ? "stop" : "play";
                offerHint(autoCorrectHint, "music " + autoCorrectHint);
                return;
            } else {
                String autoCorrectInput = "music " + ((!MusicCommand.getIsMusicPlaying()) ? "play" : "stop");
                handleCompletingArg(actionArgument, autoCompletedArg, autoCorrectInput);
                return;
            }
        }

        if (args.length == 1) {
            //music play|
            if (actionArgument.equals("play")) {
                offerHint("pop", "music play pop");
            } else {
                //pause and stop don't need any more args
                handleFinishedArgs(actionArgument);
            }
            return;
        }

        String genreArgument = args[1];

        if (!isValidFixedArg(genreArgument, GENRE) && actionArgument.equals("play")) {
            //completing an arg?
            String autoCompletedArg = Autocomplete.autocompleteFromList(genreArgument, GENRE);
            if (autoCompletedArg == null || genreArgument.isEmpty()) {
                offerHint("pop", "music play pop");
                return;
            } else {
                String autoCompletedInput = (MusicCommand.getIsMusicPlaying()) ? "music stop " : "music play "
                        + autoCompletedArg;
                handleCompletingArg(genreArgument, autoCompletedArg, autoCompletedInput);
                return;
            }
        }

        //music play|
        handleNextArg(genreArgument, GENRE, "music play");
    }

    @Override
    protected String descriptionFromArg(String arg) {
        switch (arg) {
        case "play":
            return " plays music";
        case "stop":
            return " stops music";
        case "pop":
            return " plays pop";
        case "classic":
            return " plays the classics";
        case "dance":
            return " plays dance tracks";
        default:
            return "";
        }
    }

}
```
###### \java\seedu\address\logic\commands\hints\NoArgumentsHint.java
``` java
/**
 * Template class for hints that have no arguments in their command format
 * Specifies autocomplete to return the original {@code userInput}
 * {@code argumentHint} set to "" as there are no arguments
 */
public abstract class NoArgumentsHint extends Hint {

    NoArgumentsHint() {
        argumentHint = "";
    }

    /**
     * parses {@code userInput}
     * sets appropriate {@code description}
     * for any No Argument Command
     */
    protected void parse() {
        String whitespace = userInput.endsWith(" ") ? "" : " ";
        this.description = whitespace + this.description;
    }

    @Override
    public String autocomplete() {
        return userInput;
    }
}
```
###### \java\seedu\address\logic\commands\hints\RadioCommandHint.java
``` java
/**
 * Generates hint and tab auto complete for Radio command
 * Assumes that {@code userInput} and {@code arguments} provided are from
 * an incomplete/complete Radio command.
 */
public class RadioCommandHint extends FixedArgumentsHint {

    private static final String[] ACTION = new String[] {"play", "stop"};
    private static final String[] GENRE = RadioCommand.GENRE_LIST;

    public RadioCommandHint(String userInput, String arguments) {
        this.userInput = userInput;
        this.arguments = arguments;
        parse();
    }

    /**
     * parses {@code userInput} and {@code arguments}
     * sets appropriate {@code argumentHint}, {@code description}, {@code autoCorrectInput}
     * for Radio Command
     */
    private void parse() {
        String[] args = arguments.trim().split("\\s+");

        String actionArgument = args[0];
        if (!isValidFixedArg(actionArgument, ACTION)) {
            //completing an arg?
            String autoCompletedArg = Autocomplete.autocompleteFromList(actionArgument, ACTION);
            if (autoCompletedArg == null || actionArgument.isEmpty()) {
                String autoCorrectHint = (RadioCommand.getIsRadioPlaying()) ? "stop" : "play";
                offerHint((RadioCommand.getIsRadioPlaying()) ? "stop" : "play", "radio " + autoCorrectHint);
                return;
            } else {
                handleCompletingArg(actionArgument, autoCompletedArg,
                        "radio " + ((RadioCommand.getIsRadioPlaying()) ? "stop" : "play"));
                return;
            }
        }

        if (args.length == 1) {
            //radio play|
            if (actionArgument.equals("play")) {
                offerHint("pop", "radio play pop");
            } else {
                //stop doesn't need any more args
                handleFinishedArgs(actionArgument);
            }
            return;
        }

        String genreArgument = args[1];

        if (!isValidFixedArg(genreArgument, GENRE) && actionArgument.equals("play")) {
            //completing an arg?
            String autoCompletedArg = Autocomplete.autocompleteFromList(genreArgument, GENRE);
            if (autoCompletedArg == null || genreArgument.isEmpty()) {
                offerHint("pop", "radio play pop");
                return;
            } else {
                String autoCompletedInput = (RadioCommand.getIsRadioPlaying()) ? "radio stop" : "radio play "
                        + autoCompletedArg;
                handleCompletingArg(genreArgument, autoCompletedArg, autoCompletedInput);
                return;
            }
        }

        handleNextArg(genreArgument, GENRE, "radio play");
    }

    @Override
    protected String descriptionFromArg(String arg) {
        switch (arg) {
        case "play":
            return " plays radio";
        case "stop":
            return " stops radio";
        case "pop":
            return " plays pop radio";
        case "classic":
            return " plays classic radio";
        case "chinese":
            return " plays chinese radio";
        case "news":
            return " plays news radio";
        default:
            return "";
        }
    }

}
```
###### \java\seedu\address\logic\commands\hints\RedoCommandHint.java
``` java
/**
 * Generates description for Redo Command
 * Assumes that {@code userInput} are from a Redo Command.
 */
public class RedoCommandHint extends NoArgumentsHint {

    protected static final String REDO_COMMAND_DESC = "redo command";

    public RedoCommandHint(String userInput) {
        this.userInput = userInput;
        this.description = REDO_COMMAND_DESC;
        parse();
    }

}
```
###### \java\seedu\address\logic\commands\hints\ShareCommandHint.java
``` java
/**
 * Generates hint and tab auto complete for Share command
 * Assumes that {@code userInput} and {@code arguments} provided are from
 * an incomplete/complete Share command.
 */
public class ShareCommandHint extends ArgumentsHint {

    private static final List<Prefix> PREFIXES =
            Arrays.asList(PREFIX_SHARE);

    public ShareCommandHint(String userInput, String arguments) {
        this.userInput = userInput;
        this.arguments = arguments;
        parse();
    }

    /**
     * parses {@code userInput} and {@code arguments}
     * sets appropriate {@code argumentHint}, {@code description}, {@code onTab}
     * for Share Command
     */
    private void parse() {
        if (!HintUtil.hasPreambleIndex(arguments)) {
            handleOfferIndex(userInput);
            return;
        }

        if (HintUtil.hasIndex(arguments) && Character.isDigit(userInput.charAt(userInput.length() - 1))) {
            handleIndexTabbing(HintUtil.getPreambleIndex(arguments, PREFIXES));
            return;
        }

        List<Prefix> possiblePrefixesToComplete = HintUtil.getUncompletedPrefixes(arguments, PREFIXES);

        Optional<String> prefixCompletionOptional =
                HintUtil.findPrefixCompletionHint(arguments, possiblePrefixesToComplete);

        // are we completing a hint?
        // case: share 1 s|/
        if (prefixCompletionOptional.isPresent()) {
            handlePrefixCompletion(prefixCompletionOptional.get(), possiblePrefixesToComplete);
            return;
        }

        // case: share 1 s/|
        Optional<Prefix> endPrefix = HintUtil.findEndPrefix(userInput, PREFIXES);
        if (endPrefix.isPresent()) {
            handlePrefixTabbing(endPrefix.get(), PREFIXES, possiblePrefixesToComplete);
            return;
        }

        // we should offer a hint
        // case: share 1 s/|
        handleOfferHint(PREFIXES);
    }

    @Override
    protected void handleOfferHint(List<Prefix> prefixList) {
        String whitespace = userInput.endsWith(" ") ? "" : " ";
        Prefix offeredPrefix = HintUtil.offerHint(arguments, prefixList);
        argumentHint = whitespace + offeredPrefix.toString();

        if (offeredPrefix.equals(PREFIX_EMPTY)) {
            onTab = userInput + whitespace;
            description = "next email or index";
        } else {
            onTab = userInput + argumentHint;
            description = getDescription(offeredPrefix);
        }
        LOGGER.info("ArgumentsHint - Handled Prefix Offer");
    }


    @Override
    protected String getDescription(Prefix prefix) {
        switch (prefix.toString()) {
        case PREFIX_SHARE_STRING:
            return "email or index";
        default:
            return "";
        }
    }
}
```
###### \java\seedu\address\logic\commands\hints\UnaliasCommandHint.java
``` java
/**
 * Generates hint and tab auto complete for unalias command
 * Assumes that {@code userInput} and {@code arguments} provided are from
 * an incomplete/complete unalias command.
 */
public class UnaliasCommandHint extends Hint {

    public UnaliasCommandHint(String userInput, String arguments) {
        this.userInput = userInput;
        this.arguments = arguments;
        this.argumentHint = "";
        parse();
    }

    @Override
    public String autocomplete() {
        String whitespace = userInput.endsWith(" ") ? "" : " ";
        return userInput + whitespace;
    }

    /**
     * parses {@code userInput} and {@code arguments}
     * sets appropriate {@code argumentHint}, {@code description}
     * for Unalias Command
     */
    private void parse() {
        String[] args = arguments.trim().split("\\s+");

        if (args[0].isEmpty()) {

            if (userInput.endsWith(" ")) {
                description = " alias to remove";
            } else {
                description = " removes alias";
            }
        } else {
            description = " removes " + args[0] + " from aliases";
        }
    }
}
```
###### \java\seedu\address\logic\commands\hints\UndoCommandHint.java
``` java
/**
 * Generates description for Undo Command
 * Assumes that {@code userInput} are from an Undo Command.
 */
public class UndoCommandHint extends NoArgumentsHint {

    protected static final String UNDO_COMMAND_DESC = "undo previous command";

    public UndoCommandHint(String userInput) {
        this.userInput = userInput;
        this.description = UNDO_COMMAND_DESC;
        parse();
    }
}
```
###### \java\seedu\address\logic\parser\AddCommandParser.java
``` java
            Avatar avatar = ParserUtil.parseAvatar(argMultimap.getValue(PREFIX_AVATAR)).get();
```
###### \java\seedu\address\logic\parser\FindCommandParser.java
``` java

public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        //replace new line character
        String trimmedArgs = args.trim().replace("\n", "").replace("\r", "");
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
        return new FindCommand(inputIntoPredicate(trimmedArgs));
    }

    /**
     * Parses {@code userInput}
     * returns {@code PersonContainsFieldsPredicate} that matches the requirements of the input
     */
    private PersonContainsFieldsPredicate inputIntoPredicate(String userInput) {
        String trimmedArgs = userInput.trim();
        //we input white space infront so that ArgumentMultimap will be able to identify
        //first argument without prefix as a name
        //ie find alex
        String formattedText = " " + trimmedArgs;
        ArgumentMultimap argumentMultimap =
                ArgumentTokenizer.tokenize(formattedText,
                        PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG, PREFIX_REMARK);

        List<Predicate> predicateList = new ArrayList<>();

        for (Prefix prefix : LIST_OF_PREFIXES) {
            for (String value : argumentMultimap.getAllValues(prefix)) {
                if (!value.equals("")) {
                    predicateList.add(valueAndPrefixIntoPredicate(value.trim(), prefix));

                }
            }
        }
        return new PersonContainsFieldsPredicate(predicateList);
    }

    /**
     * Takes in {@code value} and {@code prefix}
     * returns {@code Predicate} that checks for the value in person's field based on prefix
     */
    private Predicate valueAndPrefixIntoPredicate(String value, Prefix prefix) {
        switch (prefix.toString()) {
        case CliSyntax.PREFIX_NAME_STRING:
            return new NameContainsKeywordPredicate(value);
        case CliSyntax.PREFIX_PHONE_STRING:
            return new PhoneContainsKeywordPredicate(value);
        case CliSyntax.PREFIX_ADDRESS_STRING:
            return new AddressContainsKeywordPredicate(value);
        case CliSyntax.PREFIX_EMAIL_STRING:
            return new EmailContainsKeywordPredicate(value);
        case CliSyntax.PREFIX_TAG_STRING:
            return new TagsContainKeywordPredicate(value);
        case CliSyntax.PREFIX_EMPTY_STRING:
            return new NameContainsKeywordPredicate(value);
        case CliSyntax.PREFIX_REMARK_STRING:
            return new RemarkContainsKeywordPredicate(value);
        default:
            return new NameContainsKeywordPredicate(value);
        }
    }
}

```
###### \java\seedu\address\logic\parser\HintParser.java
``` java
/**
 * Class that is responsible for generating hints based on user input
 */
public class HintParser {

    /**
     * Parses {@code String input} and returns an appropriate hint
     */
    public static String generateHint(String input) {
        String[] command;

        try {
            command = ParserUtil.parseCommandAndArguments(input);
        } catch (ParseException e) {
            return " type help for guide";
        }

        String userInput = input;
        String commandWord = command[0];
        String arguments = command[1];
        Hint hint = generateHint(userInput, arguments, commandWord);
        hint.requireFieldsNonNull();
        return hint.getArgumentHint() + hint.getDescription();
    }

    /**
     * returns an appropriate hint based on commandWord and arguments
     * userInput and arguments are referenced to decide whether whitespace should be added to
     * the front of the hint
     */
    public static Hint generateHint(String userInput, String arguments, String commandWord) {

        switch (commandWord) {
        case AddCommand.COMMAND_WORD:
            return new AddCommandHint(userInput, arguments);
        case EditCommand.COMMAND_WORD:
            return new EditCommandHint(userInput, arguments);
        case FindCommand.COMMAND_WORD:
            return new FindCommandHint(userInput, arguments);
        case SelectCommand.COMMAND_WORD:
        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandHint(userInput, arguments);
        case ShareCommand.COMMAND_WORD:
            return new ShareCommandHint(userInput, arguments);
        case ClearCommand.COMMAND_WORD:
            return new ClearCommandHint(userInput);
        case ListCommand.COMMAND_WORD:
            return new ListCommandHint(userInput);
        case HistoryCommand.COMMAND_WORD:
            return new HistoryCommandHint(userInput);
        case ExitCommand.COMMAND_WORD:
            return new ExitCommandHint(userInput);
        case UndoCommand.COMMAND_WORD:
            return new UndoCommandHint(userInput);
        case RedoCommand.COMMAND_WORD:
            return new RedoCommandHint(userInput);
        case HelpCommand.COMMAND_WORD:
            return new HelpCommandHint(userInput);
        case MusicCommand.COMMAND_WORD:
            return new MusicCommandHint(userInput, arguments);
        case RadioCommand.COMMAND_WORD:
            return new RadioCommandHint(userInput, arguments);
        case AliasCommand.COMMAND_WORD:
            return new AliasCommandHint(userInput, arguments);
        case UnaliasCommand.COMMAND_WORD:
            return new UnaliasCommandHint(userInput, arguments);
        default:
            return new CommandHint(userInput, commandWord);
        }
    }
}
```
###### \java\seedu\address\model\person\AddressContainsKeywordPredicate.java
``` java
public class AddressContainsKeywordPredicate implements Predicate<ReadOnlyPerson> {

    private final String keyword;
    public AddressContainsKeywordPredicate(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return person.getAddress().toString().toLowerCase().contains(keyword.toLowerCase());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressContainsKeywordPredicate // instanceof handles nulls
                && this.keyword.equals(((AddressContainsKeywordPredicate) other).keyword)); // state check
    }

    @Override
    public int hashCode() {
        return keyword.hashCode();
    }

}
```
###### \java\seedu\address\model\person\Avatar.java
``` java
public class Avatar {


    public static final String MESSAGE_AVATAR_CONSTRAINTS = "Avatar file path must be .jpg or .png and must exist";

    public final String value;
    private String originalFilePath;

    /**
     * Creates an avatar with string
     * @param fileName can be invalid. Displayed avatar will then be default image
     */
    public Avatar(String fileName) {
        this.value = fileName;
    }

    /**
     * Creates an avatar with
     * @param fileName name of image to be saved as avatar
     * @param originalFilePath file path of file to be saved as avatar
     */
    private Avatar(String fileName, String originalFilePath) {
        this.value = fileName;
        this.originalFilePath = originalFilePath;
    }

    /**
     * Creates an avatar with given file path
     * file path is check to see if it is valid and if is a jpg or png
     */
    public static Avatar readAndCreateAvatar(String filePath) throws IllegalValueException {

        if (filePath == null) {
            return new Avatar("", "");
        }

        if (!ImageStorage.isValidImagePath(filePath)) {
            throw new IllegalValueException(MESSAGE_AVATAR_CONSTRAINTS);
        }

        if (!ImageStorage.isJpgOrPng(filePath)) {
            throw new IllegalValueException(MESSAGE_AVATAR_CONSTRAINTS);
        }

        //use a unique id for each image to prevent overwriting old images.
        return new Avatar(UUID.randomUUID().toString() + "." + ImageStorage.getFormat(filePath), filePath);
    }

    /**
     *  Saves image at {@code originalFilePath} with uid value via ImageStorage
     *  This is required at the moment as current implementation of input validation before enter
     *  uses AddressBookParser#parse
     */

    public void saveAvatar() {

        if (originalFilePath == null || originalFilePath.isEmpty()) {
            return;
        }

        ImageStorage.saveAvatar(originalFilePath, value);
    }

    public String getOriginalFilePath() {
        return originalFilePath;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Avatar // instanceof handles nulls
                && this.value.equals(((Avatar) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}

```
###### \java\seedu\address\model\person\EmailContainsKeywordPredicate.java
``` java
public class EmailContainsKeywordPredicate implements Predicate<ReadOnlyPerson> {

    private final String keyword;
    public EmailContainsKeywordPredicate(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return person.getEmail().toString().toLowerCase().contains(keyword.toLowerCase());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmailContainsKeywordPredicate // instanceof handles nulls
                && this.keyword.equals(((EmailContainsKeywordPredicate) other).keyword)); // state check
    }

    @Override
    public int hashCode() {
        return keyword.hashCode();
    }

}
```
###### \java\seedu\address\model\person\NameContainsKeywordPredicate.java
``` java
public class NameContainsKeywordPredicate implements Predicate<ReadOnlyPerson> {
    private final String keyword;

    public NameContainsKeywordPredicate(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return person.getName().fullName.toLowerCase().contains(keyword.toLowerCase());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordPredicate // instanceof handles nulls
                && this.keyword.equals(((NameContainsKeywordPredicate) other).keyword)); // state check
    }

    @Override
    public int hashCode() {
        return keyword.hashCode();
    }

}
```
###### \java\seedu\address\model\person\PersonContainsFieldsPredicate.java
``` java
public class PersonContainsFieldsPredicate implements Predicate<ReadOnlyPerson> {
    private final Set<Predicate> predicates;

    public PersonContainsFieldsPredicate(List<Predicate> predicates) {
        this.predicates = predicates.stream().collect(Collectors.toSet());
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        for (Predicate searchQuery : predicates) {
            if (!searchQuery.test(person)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonContainsFieldsPredicate // instanceof handles nulls
                && checkEquality((PersonContainsFieldsPredicate) other)); // state check
    }

    /**
     * returns true if other predicate is functionally the same as this predicate
     * and have the same number of predicates
     */
    public boolean checkEquality(PersonContainsFieldsPredicate other) {
        return predicates.equals(other.predicates);
    }
}
```
###### \java\seedu\address\model\person\PhoneContainsKeywordPredicate.java
``` java
public class PhoneContainsKeywordPredicate implements Predicate<ReadOnlyPerson> {

    private final String keyword;
    public PhoneContainsKeywordPredicate(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return person.getPhone().value.toLowerCase().contains(keyword.toLowerCase());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PhoneContainsKeywordPredicate // instanceof handles nulls
                && this.keyword.equals(((PhoneContainsKeywordPredicate) other).keyword)); // state check
    }

    @Override
    public int hashCode() {
        return keyword.hashCode();
    }

}
```
###### \java\seedu\address\model\person\ReadOnlyPerson.java
``` java
    ObjectProperty<Avatar> avatarProperty();
    Avatar getAvatar();
    /**
     * This is required as explained in Avatar#saveAvatar
     * Must be changed in the future as this breaks the read-only interface of ReadOnlyPerson
     */
    void saveAvatar();
```
###### \java\seedu\address\model\person\RemarkContainsKeywordPredicate.java
``` java
public class RemarkContainsKeywordPredicate implements Predicate<ReadOnlyPerson> {

    private final String keyword;
    public RemarkContainsKeywordPredicate(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return containsWordIgnoreCase(person.getRemark().value, keyword);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemarkContainsKeywordPredicate // instanceof handles nulls
                && this.keyword.equals(((RemarkContainsKeywordPredicate) other).keyword)); // state check
    }

    @Override
    public int hashCode() {
        return keyword.hashCode();
    }
}
```
###### \java\seedu\address\model\person\TagsContainKeywordPredicate.java
``` java
public class TagsContainKeywordPredicate implements Predicate<ReadOnlyPerson> {
    private final String keyword;

    public TagsContainKeywordPredicate(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return personTagsContainKeyword(person, keyword);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagsContainKeywordPredicate // instanceof handles nulls
                && this.keyword.equals(((TagsContainKeywordPredicate) other).keyword)); // state check
    }

    /**
     * Returns true if person's tags list contains keyword (case insensitive)
     */
    private boolean personTagsContainKeyword(ReadOnlyPerson person, String keyword) {
        return person.getTags().stream().anyMatch(tag -> tag.tagName.toLowerCase().contains(keyword.toLowerCase()));
    }

    @Override
    public int hashCode() {
        return keyword.hashCode();
    }
}
```
###### \java\seedu\address\storage\ImageStorage.java
``` java
public class ImageStorage {

    public static final String PNG = "png";
    public static final String JPG = "jpg";
    public static final String AVATAR_STORAGE_PATH = "data/images/avatars/";
    public static final String DEFAULT_RESOURCE_PATH = "/images/avatars/default.png";

    /**
     * Looks for image in {@code AVATAR_STORAGE_PATH} based on imageName
     * @param imageName
     * @return image if it exists or default image if image does not exist
     */
    public static Image getAvatar(String imageName) {
        Image image;

        try {

            String fullPath = AVATAR_STORAGE_PATH + imageName;
            image = new Image(new FileInputStream(new File(fullPath)));

        } catch (FileNotFoundException e) {
            image = AppUtil.getImage(DEFAULT_RESOURCE_PATH);
        }

        return image;
    }

    /**
     * Saves image to {@code AVATAR_STORAGE_PATH}
     * @param imageFilePath file path of image to be saved
     * @param name name of image to be saved as
     * @return true if avatar is successfully saved and false if avatar is not saved
     */
    public static boolean saveAvatar(String imageFilePath, String name) {

        String format = getFormat(imageFilePath);

        try {
            File imageFile = new File(imageFilePath);
            File imageFileToWrite = new File(AVATAR_STORAGE_PATH + name);
            FileUtil.createIfMissing(imageFileToWrite);
            ImageIO.write(ImageIO.read(imageFile), format, imageFileToWrite);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    /**
     * parses file path and returns format
     * @return image format of {@param imageFilePath}
     * assumes filePath either ends with .png or .jpg (see #isJpgOrPng)
     */
    public static String getFormat(String imageFilePath) {
        String format = PNG;

        if (isJpg(imageFilePath)) {
            format = JPG;
        }
        return format;
    }

    /**
     * @return true if filePath is a path to a .jpg or .png
     */
    public static boolean isJpgOrPng(String filePath) {
        return isJpg(filePath) || isPng(filePath);
    }

    /**
     * parses {@code filePath} and checks if its a path to .jpg or .png
     * and checks if the file exists and is not a directory
     * @return true if above criteria is met
     */
    public static boolean isValidImagePath(String filePath) {

        if (!isJpgOrPng(filePath)) {
            return false;
        }

        File f = new File(filePath);
        if (!f.exists() || f.isDirectory()) {
            return false;
        }

        return true;
    }

    private static boolean isJpg(String filePath) {
        return filePath.toLowerCase().endsWith(".jpg");
    }

    private static boolean isPng(String filePath) {
        return filePath.toLowerCase().endsWith(".png");
    }

}
```
###### \java\seedu\address\ui\CommandBox.java
``` java
        commandTextField.textProperty().addListener((ob, o, n) -> {
            // expand the textfield
            double width = TextUtil.computeTextWidth(commandTextField.getFont(),
                    commandTextField.getText(), 0.0D) + 2;
            double halfWindowWidth = (MainWindow.getInstance() == null)
                    ? 250 : MainWindow.getInstance().getRoot().getWidth() / 2;
            width = (width < 1) ? 1 : width;
            width = (width > halfWindowWidth) ? halfWindowWidth : width;
            commandTextField.setPrefWidth(width);
            commandTextField.setAlignment(Pos.CENTER_RIGHT);
```
###### \java\seedu\address\ui\CommandBox.java
``` java
        commandBoxItems.setOnMouseClicked((event) -> {
            commandTextField.requestFocus();
            commandTextField.positionCaret(commandTextField.getText().length());
        });

        CommandBoxHints commandBoxHints = new CommandBoxHints(commandTextField);
        commandBoxItems.getChildren().add(commandBoxHints.getRoot());
        commandTextField.prefColumnCountProperty().bind(commandTextField.textProperty().length());

```
###### \java\seedu\address\ui\CommandBox.java
``` java

        // posts a CommandInputChangedEvent whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((observable, oldInput, newInput) ->
                EventsCenter.getInstance().post(new CommandInputChangedEvent(newInput)));
        historySnapshot = logic.getHistorySnapshot();

```
###### \java\seedu\address\ui\CommandBoxHints.java
``` java
public class CommandBoxHints extends UiPart<TextField> {

    private static final String FXML = "CommandBoxHints.fxml";

    @FXML
    private TextField commandBoxHints;

    private TextField commandTextField;

    public CommandBoxHints(TextField commandTextField) {
        super(FXML);
        registerAsAnEventHandler(this);
        this.commandTextField = commandTextField;

        commandBoxHints.textProperty().addListener((ob, o, n) -> {
            // expand the textfield
            double width = TextUtil.computeTextWidth(commandBoxHints.getFont(),
                    commandBoxHints.getText(), 0.0D) + 1;
            width = (width < 1) ? 1 : width;
            commandBoxHints.setPrefWidth(width);
        });
    }


    @Subscribe
    private void handleCommandInputChangedEvent(CommandInputChangedEvent event) {
        String userInput = event.currentInput;
        if (userInput.isEmpty()) {
            commandBoxHints.setText("Enter Command Here");
            return;
        }
        String hint = generateHint(userInput);
        commandBoxHints.setText(hint);

    }

    @FXML
    private void handleOnClick() {
        commandTextField.requestFocus();
        commandTextField.positionCaret(commandTextField.getText().length());
    }


}
```
###### \java\seedu\address\ui\PersonPanel.java
``` java
public class PersonPanel extends UiPart<Region> {

    private static final String FXML = "PersonPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private VBox panel;
    @FXML
    private Label name;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label remark;
    @FXML
    private FlowPane tags;
    @FXML
    private ImageView avatar;

    public PersonPanel() {
        super(FXML);
        registerAsAnEventHandler(this);
    }

    /**
     * Updates person details showcased on the panel
     */
    private void loadPersonDetails(ReadOnlyPerson person) {
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().toString());
        address.setText(person.getAddress().toString());
        email.setText(person.getEmail().toString());
        tags.getChildren().clear();
        person.getTags().forEach(tag -> tags.getChildren().add(new Tag(tag.tagName).getRoot()));
```
###### \java\seedu\address\ui\PersonPanel.java
``` java
    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {

        Image image = ImageStorage.getAvatar(event.getNewSelection().person.getAvatar().value);
        avatar.setImage(image);

        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonDetails(event.getNewSelection().person);
    }
}
```
###### \java\seedu\address\ui\Tag.java
``` java
    public Tag(String tagValue) {
        super(FXML);
        tag.setText(tagValue);
        value = tagValue;
        if (MainWindow.getInstance() != null) {
            logic = MainWindow.getInstance().getLogic();
        }
    }

    /**
     * onMouseClicked, a search for tag will be executed
     */
    @FXML
    private void handleClick(MouseEvent event) {
        try {
            CommandResult commandResult =
                    logic.execute(FindCommand.COMMAND_WORD + " " + CliSyntax.PREFIX_TAG_STRING + value);
            // process result of the command
            logger.info("Result: " + commandResult.feedbackToUser);
            raise(new NewResultAvailableEvent(commandResult.feedbackToUser));

        } catch (CommandException | ParseException e) {
            // handle command failure
            logger.info("Invalid tag find: " + value);
            raise(new NewResultAvailableEvent(e.getMessage()));
        } catch (NullPointerException ne) {
            logger.info("Address Book Logic not initialised");
            assert false;
        }
    }

    public String getText() {
        return value;
    }

    public Label getLabel() {
        return tag;
    }

}
```
###### \resources\view\CommandBoxHints.fxml
``` fxml
<?import javafx.scene.Cursor?>
<?import javafx.scene.control.TextField?>


<TextField fx:id="commandBoxHints" editable="false" onMouseClicked="#handleOnClick" pickOnBounds="false" text="Enter Command Here" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
   <cursor>
      <Cursor fx:constant="TEXT" />
   </cursor>
</TextField>
```
###### \resources\view\main.css
``` css

@font-face {
    src: url("./ProximaNovaAltRegular-webfont.ttf");
}

.root * {
    base: black;
    accent: #141E30;
    light: white;
    -fx-font-family: "Proxima Nova Alt Rg", "Helvetica";
    -fx-background-color: transparent;
    -fx-text-fill: light;
}

#id, #tags .label {
    -fx-text-fill: derive(light, -40%);
}

#tag:hover {
    -fx-underline: true;
    -fx-text-fill: light !important;
}

.scroll-bar .thumb {
    -fx-background-color: derive(base, -5%);
    -fx-fill-width: 10;
}

/**
 * Menu Bar
 */

#menuBar * {
    -fx-background-color: light;
    -fx-text-fill: base;
}

/**
 * Command Box
 */

#commandBoxWrapper {
    -fx-padding: 20;
}

#commandBoxItems {
    -fx-border-color: transparent transparent light transparent;
}

#commandTextField, #commandBoxHints{
    -fx-font-size: 25pt;
    -fx-font-weight: bold;
    -fx-effect: dropshadow(gaussian, base, 5, 0, 0, 0);
    -fx-padding: 0 0 0 0;
}

#commandBoxHints {
    -fx-opacity: 0.4;
}


#commandBoxIconPlaceholder {
    -fx-padding: 0 0 0 10;
}

#commandBoxIconPlaceholder .ikonli-font-icon {
    -fx-icon-color: light;
}

/**
 * Result Display
 */

#resultDisplay {
    -fx-font-size: 13pt;
    -fx-text-fill: derive(light, -40%);
}

#resultDisplayPlaceholder {
    -fx-padding: 0 10 0 10;
}

/**
 * Person List
 */

#personListVBox {
    -fx-padding: 0 0 0 20;
}

#personListVBox #name, #personListVBox #id{
    -fx-font-size: 15pt;
}

#personListVBox .scroll-bar:horizontal .thumb {
    -fx-background-color: transparent;
}

.list-cell:filled:hover {
    -fx-background-color: rgba(0, 0, 0, 0.5);
}

.list-cell:filled:selected {
    -fx-background-color: rgba(0, 0, 0, 0.7);
}

/**
 * Person Panel
 */
#personPanel {
    -fx-font-size: 15pt;
}

#personPanel #name {
    -fx-font-size: 25pt;
    -fx-effect: dropshadow(gaussian, base, 10, 0, 0, 2);
}

#details {
    lighterBase: derive(base, 10%);
    -fx-background-color: linear-gradient(from 50% 0% to 50% 100%, accent 0%, lighterBase 100%);
}
```
###### \resources\view\PersonPanel.fxml
``` fxml
<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.image.ImageView?>
<?import javafx.scene.layout.FlowPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.VBox?>

<VBox fx:id="personPanel" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
   <children>
      <HBox>
         <children>
            <ImageView fx:id="avatar" fitHeight="150.0" fitWidth="150.0" pickOnBounds="true" preserveRatio="true">
               <HBox.margin>
                  <Insets bottom="10.0" left="10.0" right="10.0" top="10.0" />
               </HBox.margin></ImageView>
            <VBox alignment="CENTER_LEFT">
               <children>
                  <Label fx:id="name" textAlignment="LEFT" wrapText="true" />
                  <FlowPane fx:id="tags" alignment="TOP_LEFT" hgap="8.0" rowValignment="TOP">
                     <padding>
                        <Insets bottom="5.0" right="5.0" />
                     </padding>
                  </FlowPane>
                  <Label fx:id="phone" alignment="TOP_LEFT" contentDisplay="LEFT" textAlignment="LEFT" wrapText="true" />
                  <Label fx:id="address" alignment="TOP_LEFT" contentDisplay="LEFT" textAlignment="LEFT" wrapText="true" />
                  <Label fx:id="email" alignment="TOP_LEFT" contentDisplay="LEFT" textAlignment="LEFT" wrapText="true" />
               </children>
            </VBox>
         </children>
      </HBox>
      <Label fx:id="remark" alignment="TOP_LEFT" contentDisplay="LEFT" textAlignment="LEFT" wrapText="true" />
   </children>
</VBox>
```
###### \resources\view\Tag.fxml
``` fxml
<?import javafx.scene.control.Label?>


<Label fx:id="tag" onMouseClicked="#handleClick" text="Label" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1" />
```
