package adapters.UI;

import entities.Sala;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import usecases.dto.OperationResult;
import usecases.services.ReservarSalasApp;

import java.util.List;

public class MainView {

    private final ReservarSalasApp app;
    private final VBox salasListBox;

    public MainView() {
        app          = new ReservarSalasApp();
        salasListBox = new VBox(8);
    }

    public Scene createScene() {
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        Tab tabAdmin = new Tab("Administrador", buildAdminPanel());
        tabPane.getTabs().add(tabAdmin);

        refreshSalas();

        BorderPane root = new BorderPane();
        root.setCenter(tabPane);
        return new Scene(root, 800, 540);
    }

    // =========================================================================
    //  Panel Administrador
    // =========================================================================
    private ScrollPane buildAdminPanel() {
        VBox panel = new VBox(20);
        panel.setPadding(new Insets(20));
        panel.getChildren().addAll(
                buildRegistrarSalaSection(),
                buildListarSalasSection()
        );
        ScrollPane scroll = new ScrollPane(panel);
        scroll.setFitToWidth(true);
        return scroll;
    }

    // =========================================================================
    //  UC: Registrar Sala
    // =========================================================================
    private VBox buildRegistrarSalaSection() {
        VBox section = createSection("Registrar Sala");

        TextField nombreField = new TextField();
        nombreField.setPromptText("Nombre de la sala");

        TextField capacidadField = new TextField();
        capacidadField.setPromptText("Capacidad");
        capacidadField.setPrefWidth(100);

        ComboBox<String> tipoCombo = new ComboBox<>();
        tipoCombo.getItems().addAll("Aula", "Laboratorio", "Auditorio");
        tipoCombo.setPromptText("Tipo de sala");
        tipoCombo.setPrefWidth(160);

        Button registrarBtn = new Button("Registrar Sala");
        registrarBtn.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-weight: bold;");

        registrarBtn.setOnAction(e -> {
            String nombre  = nombreField.getText().trim();
            String capStr  = capacidadField.getText().trim();
            String tipo    = tipoCombo.getValue();

            if (nombre.isEmpty() || capStr.isEmpty() || tipo == null) {
                showMessage("Aviso", "Por favor complete todos los campos.", Alert.AlertType.WARNING);
                return;
            }

            int capacidad;
            try {
                capacidad = Integer.parseInt(capStr);
                if (capacidad <= 0) throw new NumberFormatException();
            } catch (NumberFormatException ex) {
                showMessage("Error", "La capacidad debe ser un número entero positivo.", Alert.AlertType.WARNING);
                return;
            }

            OperationResult result = app.registrarSala(nombre, capacidad, tipo);
            showMessage(
                    result.isSuccess() ? "Éxito" : "Error",
                    result.getMessage(),
                    result.isSuccess() ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR
            );

            if (result.isSuccess()) {
                nombreField.clear();
                capacidadField.clear();
                tipoCombo.setValue(null);
                refreshSalas();
            }
        });

        HBox formRow = new HBox(10, nombreField, capacidadField, tipoCombo, registrarBtn);
        formRow.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(nombreField, Priority.ALWAYS);

        section.getChildren().add(formRow);
        return section;
    }

    // =========================================================================
    //  Listar Salas
    // =========================================================================
    private VBox buildListarSalasSection() {
        VBox section = createSection("Salas Registradas");

        Button refrescarBtn = new Button("Actualizar");
        refrescarBtn.setOnAction(e -> refreshSalas());

        section.getChildren().addAll(refrescarBtn, salasListBox);
        return section;
    }

    private void refreshSalas() {
        salasListBox.getChildren().clear();

        List<Sala> salas = app.listarSalas();
        if (salas.isEmpty()) {
            salasListBox.getChildren().add(new Label("No hay salas registradas."));
            return;
        }

        for (Sala s : salas) {
            HBox row = new HBox(15);
            row.setPadding(new Insets(6));
            row.setStyle("-fx-border-color: #DDDDDD; -fx-border-radius: 4; -fx-background-color: #FAFAFA;");
            row.setAlignment(Pos.CENTER_LEFT);

            Label idLabel        = new Label("ID: " + s.getId());
            idLabel.setMinWidth(80);
            Label nombreLabel    = new Label(s.getNombre());
            nombreLabel.setMinWidth(160);
            Label tipoLabel      = new Label("[" + s.getTipo() + "]");
            tipoLabel.setStyle("-fx-text-fill: #555;");
            Label capacidadLabel = new Label("Cap: " + s.getCapacidad());

            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            Label disponibleLabel = new Label(s.isDisponible() ? "Disponible" : "Ocupada");
            disponibleLabel.setStyle(s.isDisponible()
                    ? "-fx-text-fill: #2e7d32; -fx-font-weight: bold;"
                    : "-fx-text-fill: #c62828; -fx-font-weight: bold;");

            row.getChildren().addAll(idLabel, nombreLabel, tipoLabel, capacidadLabel, spacer, disponibleLabel);
            salasListBox.getChildren().add(row);
        }
    }

    // =========================================================================
    //  Utilidades
    // =========================================================================
    private VBox createSection(String titulo) {
        Label titleLabel = new Label(titulo);
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 15));
        titleLabel.setStyle("-fx-text-fill: #1a237e;");

        VBox section = new VBox(10);
        section.setPadding(new Insets(14));
        section.setStyle(
                "-fx-border-color: #C5CAE9; " +
                        "-fx-border-radius: 6; " +
                        "-fx-background-color: #F5F5FF; " +
                        "-fx-background-radius: 6;"
        );
        section.getChildren().add(titleLabel);
        return section;
    }

    private void showMessage(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
