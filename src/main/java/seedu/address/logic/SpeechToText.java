package seedu.address.logic;
import javafx.scene.control.TextField;
import javax.speech.recognition.*;

public class SpeechToText extends ResultAdapter {
	public TextField commandTextField;

	public SpeechToText(TextField commandTextField) {
		this.commandTextField = commandTextField;
	}
	// Receives RESULT_ACCEPTED event: print it, clean up, exit
	public void resultAccepted(ResultEvent e) {
		Result r = (Result)(e.getSource());
		ResultToken tokens[] = r.getBestTokens();
		String speechCommand = "";
		for (int i = 0; i < tokens.length; i++) {
			System.out.println(tokens[i].getSpokenText());
			speechCommand += tokens[i].getSpokenText() + " ";
		}
		commandTextField.setText(speechCommand);
		if(tokens[0].getSpokenText().equals("delete")) {

		}
	}
}
