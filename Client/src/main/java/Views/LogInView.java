package Views;

import Controller.LogInController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

public class LogInView {
    private LogInController ctrl;
    private Scene scene;
    private BorderPane root;

    public TextField textFieldUsername;
    public PasswordField textFieldPassword;
    Button b;


    public LogInView(LogInController ctrl) {
        this.ctrl = ctrl;
        initView();
    }

    public LogInView() {
        initView();
    }

    public void setController(LogInController ctrl){
        this.ctrl=ctrl;
    }

    private void initView() {
        root = new BorderPane();

        scene = new Scene(root, 1024, 512);

        textFieldUsername=new TextField();
        textFieldPassword=new PasswordField();

        root.prefHeightProperty().bind(scene.heightProperty());
        root.prefWidthProperty().bind(scene.widthProperty());

        root.setCenter(initCenter());
    }

    public Scene getView(){ return scene;}


    public AnchorPane initCenter(){
        AnchorPane centerAnchorPane=new AnchorPane();
        GridPane gridPane=createGridPane();

        b=new Button("Log in");

        centerAnchorPane.getChildren().add(gridPane);
        centerAnchorPane.getChildren().add(b);
        AnchorPane.setLeftAnchor(gridPane,330d);
        AnchorPane.setBottomAnchor(gridPane, 200d);
        AnchorPane.setLeftAnchor(b,480d);
        AnchorPane.setBottomAnchor(b,170d);

        b.setOnAction(ctrl::handleLogInButton);

        return centerAnchorPane;
    }

    private GridPane createGridPane() {
        GridPane gridPaneLogIn=new GridPane();
        gridPaneLogIn.setPadding(new Insets(20, 20, 20, 20));

        gridPaneLogIn.setHgap(20);
        gridPaneLogIn.setVgap(20);

        Label labelUsername= new Label("Username: ");
        Label labelPassword= new Label("Password: ");

        gridPaneLogIn.add(labelUsername,0,0);
        gridPaneLogIn.add(labelPassword,0,1);

        gridPaneLogIn.add(textFieldUsername,1,0);
        gridPaneLogIn.add(textFieldPassword,1,1);

        ColumnConstraints c1=new ColumnConstraints();
        ColumnConstraints c2=new ColumnConstraints();
        c2.setPrefWidth(200d);
        gridPaneLogIn.getColumnConstraints().addAll(c1,c2);

        return gridPaneLogIn;
    }
}
