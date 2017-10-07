package seedu.address.ui;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.tag.Tag;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    public static final String TAG_IN_BETWEEN = ", ";
    public static final String TAG_START = "- ";
    private static final String FXML = "PersonListCard.fxml";
    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final ReadOnlyPerson person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label tags;

    public PersonCard(ReadOnlyPerson person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        initTags(person);
        bindListeners(person);
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyPerson person) {
        name.textProperty().bind(Bindings.convert(person.nameProperty()));
        person.tagProperty().addListener((observable, oldValue, newValue) -> {
            tags.setText(formatTagForCard(person));
        });
    }

    /**
     * Initializes tags for single person
     */
    private void initTags(ReadOnlyPerson person) {
        tags.setText(formatTagForCard(person));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonCard)) {
            return false;
        }

        // state check
        PersonCard card = (PersonCard) other;
        return id.getText().equals(card.id.getText())
                && person.equals(card.person);
    }

    /**
     * Returns string representation of person's tag for Person Card
     */
    public static String formatTagForCard(ReadOnlyPerson person) {
        if (person.getTags().isEmpty()) {
            return "";
        }

        String tagString = TAG_START;
        for (Tag tag : person.getTags()) {
            tagString += tag.tagName + TAG_IN_BETWEEN;
        }
        return (tagString.substring(0, tagString.length() - TAG_IN_BETWEEN.length()));
    }
}
