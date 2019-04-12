package Views;

import Controller.RezervariController;
import DTO.RezervareDTO;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class RezervariView {
    private RezervariController ctrl;
    private Stage stage;
    private Scene scene;
    private BorderPane root;

    public TableView<RezervareDTO> getTableView() {
        return tableView;
    }

    private TableView<RezervareDTO> tableView;

    public TextField textFieldNume;
    public TextField textFieldLocuri;


    public RezervariView(RezervariController ctrl) {
        this.ctrl = ctrl;

        initView();
    }

    public Stage getStage() {
        return stage;
    }

    public RezervariView() {
        initView();
    }

    public void setController(RezervariController ctrl){
        this.ctrl=ctrl;
    }

    private void initView() {
        root = new BorderPane();

        scene = new Scene(root, 424, 512);

        stage=new Stage();
        stage.setScene(scene);

        root.prefHeightProperty().bind(scene.heightProperty());
        root.prefWidthProperty().bind(scene.widthProperty());

        root.setCenter(initCenter());
    }

    public Scene getView(){ return scene;}


    public AnchorPane initCenter(){
        AnchorPane centerAnchorPane=new AnchorPane();
        GridPane gridpane=createGridPane();
        tableView=createTable();
        Button add=new Button("Add");
        centerAnchorPane.getChildren().add(tableView);
        centerAnchorPane.getChildren().add(gridpane);
        centerAnchorPane.getChildren().add(add);

        AnchorPane.setLeftAnchor(tableView,90d);
        AnchorPane.setBottomAnchor(tableView,350d);
        AnchorPane.setLeftAnchor(gridpane,40d);
        AnchorPane.setBottomAnchor(gridpane,200d);
        AnchorPane.setLeftAnchor(add,190d);
        AnchorPane.setBottomAnchor(add,170d);

        add.setOnAction(ctrl::handleAdd);

        return centerAnchorPane;
    }

    private GridPane createGridPane() {
        GridPane gridPaneMessageDetails=new GridPane();
        gridPaneMessageDetails.setPadding(new Insets(20, 20, 20, 20));

        gridPaneMessageDetails.setHgap(20);
        gridPaneMessageDetails.setVgap(20);

        Label labelNume= new Label("Nume: ");
        Label labelNrLocuri= new Label("Nr locuri: ");

        gridPaneMessageDetails.add(labelNume,0,0);
        gridPaneMessageDetails.add(labelNrLocuri,0,1);

        textFieldNume=new TextField();
        textFieldLocuri=new TextField();

        gridPaneMessageDetails.add(textFieldNume,1,0);
        gridPaneMessageDetails.add(textFieldLocuri,1,1);

        ColumnConstraints c1=new ColumnConstraints();
        ColumnConstraints c2=new ColumnConstraints();
        c2.setPrefWidth(200d);
        gridPaneMessageDetails.getColumnConstraints().addAll(c1,c2);

        return gridPaneMessageDetails;
    }

    private TableView<RezervareDTO> createTable() {
        tableView = new TableView<>();

        TableColumn<RezervareDTO, String> nrColumn = new TableColumn<>("Nr");
        TableColumn<RezervareDTO, String> numeColumn = new TableColumn<>("Nume");

        tableView.getColumns().add(nrColumn);
        tableView.getColumns().add(numeColumn);

        nrColumn.setCellValueFactory(new PropertyValueFactory<RezervareDTO, String>("nrLoc"));
        numeColumn.setCellValueFactory(new PropertyValueFactory<RezervareDTO, String>("nume"));


        tableView.setItems(ctrl.getModel());
        tableView.setMaxHeight(270);

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        return tableView;
    }
}
