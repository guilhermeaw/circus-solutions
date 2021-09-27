package common;

import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Callback;
import services.AlertService;
import utils.ApplicationUtilities;

import java.util.ArrayList;
import java.util.List;

public abstract class DefaultEditor<T> extends Dialog<T> {
    public EditorCallback callback;
    public T source;

    public DefaultEditor( EditorCallback<T> callback ) {
        this.callback = callback;
        source = callback.getSource();

        initComponents();
    }

    private boolean onSave() {
        try {
            List<String> errorList  = new ArrayList<>();

            validateInput( errorList );

            if ( !errorList.isEmpty() ) {
                String message = ApplicationUtilities.getInstance().formatErrorMessage(errorList);

                AlertService.showWarning(message);

                return false;
            } else {
                obtainInput();
                callback.onEvent();
            }
        } catch ( Exception e ) {
            ApplicationUtilities.getInstance().handleException(e);
        }

        return true;
    }

    protected void onCancel() {}

    protected void onClear() {}

    protected void activeClearButton() {
        ObservableList<ButtonType> buttonTypes = getDialogPane().getButtonTypes();

        if (!buttonTypes.contains(clearButton)) {
            getDialogPane().getButtonTypes().add(0, clearButton);
        }
    }

    protected void setIcon(Image icon) {
        ((Stage) getDialogPane().getScene().getWindow()).getIcons().add(icon);
    }

    private void initComponents() {
        setTitle( "Editor" );
        setHeaderText( "Editor de Items" );
        setIcon(new Image(getClass().getResourceAsStream("/res/images/edit.png")));
        setResizable( false );

        getDialogPane().getButtonTypes().addAll( cancelBtn, saveBtn );

        setOnCloseRequest( new EventHandler() {
            @Override
            public void handle( Event t ) {
                if (  selectedBtn == saveBtn ) {
                    if ( ! onSave() ) {
                        t.consume();
                    }
                } else if (selectedBtn == cancelBtn) {
                    onCancel();
                } else {
                    onClear();
                    t.consume();
                }
            }
        } );

        setResultConverter( new Callback() {
            @Override
            public Object call( Object p ) {
                return selectedBtn = (ButtonType) p;
            }
        } );
    }

    public void open() {
        showAndWait();
    }

    protected void handleException(Exception e) {
        ApplicationUtilities.getInstance().handleException(e);
    }

    protected abstract void validateInput( List<String> errors ) throws Exception;
    protected abstract void obtainInput();
    protected abstract void setSource( T source );

    private ButtonType saveBtn   = new ButtonType( "Salvar",   ButtonBar.ButtonData.OK_DONE );
    private ButtonType clearButton = new ButtonType( "Limpar", ButtonBar.ButtonData.BACK_PREVIOUS );
    private ButtonType cancelBtn = new ButtonType( "Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE );

    private ButtonType selectedBtn;
}
