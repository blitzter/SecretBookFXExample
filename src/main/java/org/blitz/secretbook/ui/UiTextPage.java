package org.blitz.secretbook.ui;

import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import org.blitz.secretbook.controllers.SecretBookController;
import org.blitz.secretbook.domain.TextPageData;

public class UiTextPage extends TextArea {

    private final SecretBookController secretBookController;

    public UiTextPage(SecretBookController secretBookController) {
        super();
        AnchorPane.setBottomAnchor(this, 0.0);
        AnchorPane.setTopAnchor(this, 0.0);
        AnchorPane.setLeftAnchor(this, 0.0);
        AnchorPane.setRightAnchor(this, 0.0);
        this.secretBookController = secretBookController;
        this.setText(((TextPageData) (this.secretBookController.selectedPage.getPageData())).getPageData());
        this.textProperty().addListener(
                (observable, oldValue, newValue) -> {
                    ((TextPageData) this.secretBookController.selectedPage.getPageData()).setPageData(newValue);
                    this.secretBookController.buttonSave.setDisable(false);
                }
        );
    }
}
