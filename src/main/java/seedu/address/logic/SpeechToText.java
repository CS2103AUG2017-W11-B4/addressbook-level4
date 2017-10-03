package seedu.address.logic;
import javafx.scene.control.TextField;
import javax.speech.recognition.*;

public class SpeechToText extends ResultAdapter {
	static Recognizer rec;
	private Logic logic;
	public TextField commandTextField;

	public SpeechToText(Logic logic) {
		this.logic = logic;
	}
	public SpeechToText(TextField commandTextField) {
		this.commandTextField = commandTextField;
	}
	// Receives RESULT_ACCEPTED event: print it, clean up, exit
	public void resultAccepted(ResultEvent e) {
		Result r = (Result)(e.getSource());
		ResultToken tokens[] = r.getBestTokens();
		String speechCommand="";
		for (int i = 0; i < tokens.length; i++){
			System.out.println(tokens[i].getSpokenText());
			/*
			CommandBox commandBox = new CommandBox(logic);
			commandBox.commandTextField.setText(tokens[i].getSpokenText());
			String oldtext = commandBox.commandTextField.getText();
			commandBox.commandTextField.setText(oldtext+" "+ tokens[i].getSpokenText() + " ");
			*/
			speechCommand+=tokens[i].getSpokenText() + " ";
		}
		commandTextField.setText(speechCommand);
		if(tokens[0].getSpokenText().equals("delete")) {

		}
	}
}
