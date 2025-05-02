package com.example.javafxthread;

import com.example.javafxthread.exercise2.dummyjson.User;
import com.example.javafxthread.exercise1.MessageScheduler;
import com.example.javafxthread.exercise2.RemoteUsersManager;
import com.example.javafxthread.exercise3.ollama.OllamaResponse;
import com.example.javafxthread.exercise3.ollama.OllamaResponseFetcher;
import com.example.javafxthread.exercise3.ollama.ResponseListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
/**
 * Controller for the Hello view.
 * Handles user interactions and updates the view accordingly.
 */
public class MainController {
    @FXML
    private TextField newMessageTextField;
    @FXML
    private Spinner<Integer> delaySpinner;
    @FXML
    private TextArea historyTextArea;
    private int messageCount = 0;

    @FXML
    private TextField searchTextField;
    @FXML
    private ListView<User> remoteListView;
    @FXML
    private ListView<User> localListView;
    @FXML
    private TextField chatTextField;
    @FXML
    private TextArea chatTextArea;

    private MessageScheduler messageScheduler;
    private RemoteUsersManager remoteUsersManager;

    /**
     * Initializes the controller.
     * Sets up the message scheduler and remote users manager.
     */
    @FXML
    public void initialize() {
        messageScheduler = new MessageScheduler(historyTextArea);
        remoteUsersManager = new RemoteUsersManager(remoteListView);
    }
    /**
     * Handles the send button click event.
     * Schedules a new message and updates the message count.
     */
    public void onSendButtonClicked() {
        String message = newMessageTextField.getText();
        int delay = delaySpinner.getValue();
        messageScheduler.scheduleMessage(message, delay);
        messageCount++;
        newMessageTextField.setText("Test message " + messageCount);
    }
    /**
     * Handles the users search button click event.
     * Searches for users based on the search text field value.
     */
    public void onUsersSearchButtonClicked() {
        String search = searchTextField.getText();
        remoteUsersManager.searchUsers(search);
    }
    /**
     * Handles the clear button click event.
     * Clears the list of remote users.
     */
    public void onClearButtonClicked() {
        remoteUsersManager.clearUsers();
    }
    /**
     * Handles the copy button click event.
     * Copies the selected user from the remote list to the local list.
     */
    public void onCopyButtonClicked() {
        User selectedUser = remoteListView.getSelectionModel().getSelectedItem();
        if (selectedUser != null && !localListView.getItems().contains(selectedUser)) {
            localListView.getItems().add(selectedUser);
        }
    }

    public void onChatButtonClicked(ActionEvent actionEvent) {
        String prompt = chatTextField.getText();

        class MyResponseListener implements ResponseListener {

            @Override
            public void onResponseReceived(OllamaResponse response) {
                chatTextArea.setText(response.getResponse());
            }
        };

        String apiURL = "http://127.0.0.1:11434/api/generate/";
        String model = "llama3.2"; //replace with the model you are using

        OllamaResponseFetcher fetcher = new OllamaResponseFetcher(apiURL);
        fetcher.fetchAsynchronousOllamaResponse(model, prompt, new MyResponseListener());


    }
}