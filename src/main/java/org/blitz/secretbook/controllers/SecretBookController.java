package org.blitz.secretbook.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;
import lombok.extern.slf4j.Slf4j;
import org.blitz.secretbook.domain.Book;
import org.blitz.secretbook.domain.Page;
import org.blitz.secretbook.domain.PageType;
import org.blitz.secretbook.domain.TextPageData;
import org.blitz.secretbook.ui.UiTextPage;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public class SecretBookController extends VBox {

    final FileChooser fileChooser = createFileChooser();
    final ObjectMapper objectMapper = new ObjectMapper();
    private Stage stage;
    public Book book;
    public Page selectedPage;

    @FXML
    public ListView listView;

    @FXML
    public Label labelBookPath;

    @FXML
    public Label leftStatus;

    @FXML
    public Label rightStatus;

    @FXML
    public Button buttonSave;

    @FXML
    public Button buttonNewPage;

    @FXML
    public AnchorPane mainContainer;

    public SecretBookController() {
        super();
    }

    @FXML
    private void initialize() {
        log.info("In initialize");
        setupListView();
    }

    private void setupListView() {
        StringConverter<String> converter = new DefaultStringConverter();
        listView.setEditable(true);
        listView.setCellFactory(param -> new TextFieldListCell<>(converter));
        listView.setOnEditCommit((EventHandler<ListView.EditEvent>) event -> {
            this.book.getPages().get(event.getIndex()).setLabel((String) event.getNewValue());
            this.buttonSave.setDisable(false);
            updateListView();
        });
        listView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    this.openPage((String) newValue);
                }
        );
    }

    private void updateListView() {
        this.listView.setItems(FXCollections.observableList(this.book.getPages().stream().map(Page::getLabel).collect(Collectors.toList())));
    }

    @FXML
    private void menuClicked(MouseEvent event) {
        leftStatus.setText("MenuClicked");
    }

    @FXML
    private void openBook(ActionEvent event) {
        leftStatus.setText("Select Book");
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            labelBookPath.setText(file.getAbsolutePath());
            leftStatus.setText("Book selected : " + file.getAbsolutePath());
            loadFile(file);
            this.buttonNewPage.setDisable(false);
        } else {
            leftStatus.setText("Not Selected");
        }
    }

    @FXML
    private void closeBook(ActionEvent event) {
        leftStatus.setText("Closed Book");
        labelBookPath.setText("Select Book");
        this.book = new Book();
        this.selectedPage = null;
        this.buttonSave.setDisable(true);
        this.buttonNewPage.setDisable(true);
        this.updateListView();
    }

    @FXML
    private void createNewBook(ActionEvent event) {
        leftStatus.setText("Load Book!");
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            try {
                createNewFile(file);
                loadFile(file);
                this.buttonNewPage.setDisable(false);
            } catch (IOException e) {
                leftStatus.setText("Exception while creating file");
            }
            labelBookPath.setText(file.getAbsolutePath());
        } else {
            leftStatus.setText("Not Selected");
        }
    }

    @FXML
    private void addNewPage(ActionEvent event) {
        leftStatus.setText("Added New Page");
        Page page = new Page();
        page.setLabel("Newly added page");
        page.setPageType(PageType.TEXT);
        TextPageData textPageData = new TextPageData();
        textPageData.setPageData("");
        page.setPageData(textPageData);
        this.book.getPages().add(page);
        this.buttonSave.setDisable(false);
        this.updateListView();
    }

    private void loadFile(File file) {
        try {
            this.book = objectMapper.readValue(file, Book.class);
            this.book.getPages().forEach(page -> {
                log.info("Page " + page.getLabel());
            });
            updateListView();
        } catch (IOException e) {
            leftStatus.setText("Exception while reading file");
        }
    }

    private void openPage(String newValue) {
        Optional<Page> selectedPage = book.getPages().stream().filter(page -> page.getLabel().equals(newValue)).findFirst();
        selectedPage.ifPresent(value -> {
            this.selectedPage = value;
            showSelectedPage();
        });
    }

    private void showSelectedPage() {
        this.mainContainer.getChildren().removeAll(this.mainContainer.getChildren());
        if (this.selectedPage.getPageType().equals(PageType.TEXT)) {
            TextArea textArea = new TextArea(((TextPageData) (this.selectedPage.getPageData())).getPageData());
            this.mainContainer.getChildren().add(new UiTextPage(this));
        }
    }

    private void createNewFile(File file) throws IOException {
        file.createNewFile();
        Book newBook = new Book();
        objectMapper.writeValue(file, newBook);
    }

    @FXML
    private void saveBook(ActionEvent event) {
        try {
            objectMapper.writeValue(new File(this.labelBookPath.getText()), this.book);
            this.buttonSave.setDisable(true);
        } catch (IOException e) {
            log.error("Exception while writing file ", e);
            this.leftStatus.setText("Exception while writing file");
        }
    }

    private FileChooser createFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Book");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Secret books", "*.book", "*.scbk"));
        return fileChooser;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
