package Views;

import Controller.CurseController;
import Domain.Cursa;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

public class CurseView {
    private CurseController ctrl;
    private Scene scene;
    private BorderPane root;



    private TableView<Cursa> tableView;

    public TextField textFieldDestinatie;
    public TextField textFieldData;
    public TextField textFieldOra;


    public CurseView(CurseController ctrl) {
        this.ctrl = ctrl;
        initView();
    }

    public CurseView() {
        initView();
    }

    public void setController(CurseController ctrl){
        this.ctrl=ctrl;
    }

    private void initView() {
        root = new BorderPane();

        scene = new Scene(root, 1024, 512);


        root.prefHeightProperty().bind(scene.heightProperty());
        root.prefWidthProperty().bind(scene.widthProperty());

        root.setCenter(initCenter());
        root.setLeft(initLeft());
        root.setRight(initRight());
    }


    public Scene getView(){ return scene;}

    public AnchorPane initRight(){
        AnchorPane rightAnchorPane=new AnchorPane();
        Button logout=new Button("Log out");

        rightAnchorPane.getChildren().add(logout);

        AnchorPane.setRightAnchor(logout,100d);
        AnchorPane.setBottomAnchor(logout,270d);

        logout.setOnAction(ctrl::handleLogout);

        return  rightAnchorPane;
    }

    public AnchorPane initLeft(){
        AnchorPane leftAnchorPane=new AnchorPane();
        tableView=createTable();

        leftAnchorPane.getChildren().add(tableView);

        AnchorPane.setLeftAnchor(tableView,60d);
        AnchorPane.setBottomAnchor(tableView,147d);

        return  leftAnchorPane;
    }

    public TableView<Cursa> getTableView() {
        return tableView;
    }

    private TableView<Cursa> createTable() {
        tableView=new TableView<>();

        TableColumn<Cursa, String> destinatieColumn=new TableColumn<>("Destinatia");
        TableColumn<Cursa, String> dataColumn=new TableColumn<>("Data");
        TableColumn<Cursa, String> nrColumn=new TableColumn<>("Nr Locuri");

        tableView.getColumns().add(destinatieColumn);
        tableView.getColumns().add(dataColumn);
        tableView.getColumns().add(nrColumn);

        destinatieColumn.setCellValueFactory(new PropertyValueFactory<Cursa, String>("destinatia"));
        dataColumn.setCellValueFactory(new PropertyValueFactory<Cursa, String>("data"));
        nrColumn.setCellValueFactory(new PropertyValueFactory<Cursa, String>("locuriDisponibile"));


        tableView.setItems(ctrl.getModel());
        tableView.setMaxHeight(270);

        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue)-> {ctrl.showDetails(newValue);});

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        return tableView;
    }




    public AnchorPane initCenter(){
        AnchorPane centerAnchorPane=new AnchorPane();
        GridPane gridpane=createGridPane();
        Button search=new Button("Search");
        centerAnchorPane.getChildren().add(gridpane);
        centerAnchorPane.getChildren().add(search);

        AnchorPane.setLeftAnchor(gridpane,90d);
        AnchorPane.setBottomAnchor(gridpane,200d);
        AnchorPane.setLeftAnchor(search,260d);
        AnchorPane.setBottomAnchor(search,170d);

        search.setOnAction(ctrl::handleSearch);

        return centerAnchorPane;
    }

    private GridPane createGridPane() {
        GridPane gridPaneMessageDetails=new GridPane();
        gridPaneMessageDetails.setPadding(new Insets(20, 20, 20, 20));

        gridPaneMessageDetails.setHgap(20);
        gridPaneMessageDetails.setVgap(20);

        Label labelDestinatia= new Label("Destinatia: ");
        Label labelData= new Label("Data: ");
        Label labelOra=new Label("Ora: ");

        gridPaneMessageDetails.add(labelDestinatia,0,0);
        gridPaneMessageDetails.add(labelData,0,1);
        gridPaneMessageDetails.add(labelOra,0,2);

        textFieldDestinatie=new TextField();
        textFieldData=new TextField();
        textFieldOra=new TextField();

        gridPaneMessageDetails.add(textFieldDestinatie,1,0);
        gridPaneMessageDetails.add(textFieldData,1,1);
        gridPaneMessageDetails.add(textFieldOra,1,2);

        ColumnConstraints c1=new ColumnConstraints();
        ColumnConstraints c2=new ColumnConstraints();
        c2.setPrefWidth(200d);
        gridPaneMessageDetails.getColumnConstraints().addAll(c1,c2);

        return gridPaneMessageDetails;
    }
}
