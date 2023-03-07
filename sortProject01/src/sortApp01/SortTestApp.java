package sortApp01;

import java.util.List;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import sortProject01.Person;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Dimension2D;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;


class STAPanel_TableView extends HBox{
	
	private TableView<Person> tb_display;
	public STAPanel_TableView(){		
		tb_display = new TableView<Person>();
		
		TableColumn<Person,Integer> pidCol = new TableColumn<>("ID");
		pidCol.setCellValueFactory(new PropertyValueFactory<>("pid"));
		TableColumn<Person,String> firstNameCol = new TableColumn<>("First Name");
		firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
		TableColumn<Person,String> lastNameCol = new TableColumn<>("Last Name");
		lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));

		tb_display.getColumns().setAll(pidCol, firstNameCol, lastNameCol);
		setHgrow(tb_display, Priority.ALWAYS);
		
		this.getChildren().add(tb_display);
	}
	public TableView<Person> getTable() {
		return this.tb_display;
	}
}
class STAPanel_Generate extends HBox{
	public STAPanel_Generate() {
		Label gen_genLabel = new Label("Enter an integer for list size creation. Large integers may take longer to analyze.");
		Button gen_genButton = new Button("Generate");
		TextField gen_genNumField = new TextField();
		setHgrow(gen_genLabel, Priority.SOMETIMES);
		setHgrow(gen_genNumField, Priority.SOMETIMES);

		this.getChildren().addAll(gen_genLabel, gen_genButton, gen_genNumField);
	}
}
public class SortTestApp extends Application {

	
	private Dimension2D cfg_windowSize;
	
	private ObservableList<Person> currentData;
	final int release = 0;
	STAPanel_Generate ui_pg;
	STAPanel_TableView ui_table;
	Stage primary;
	private BorderPane ui_root;
	public BorderPane getRoot() {
		return ui_root;
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		   
		primary = primaryStage;
		cfg_windowSize	= new Dimension2D(800,600);
		   primaryStage.setTitle("Sort App Test V"+release);
		   ui_root = new BorderPane();	
		   
		   generateControls();		 
		   
	      primaryStage.setScene(new Scene(ui_root, cfg_windowSize.getWidth(), cfg_windowSize.getHeight()));
	     
	       primaryStage.show();
		
	}
	private void generateControls() {
		
		var root = getRoot();
		
		root.paddingProperty().set(new Insets(10,10,10,10));

		this.ui_pg = new STAPanel_Generate();
		this.ui_table = new STAPanel_TableView();

		
		
		currentData = FXCollections.observableArrayList();
		currentData.addAll(Person.createTestList(100));
		
		ui_table.getTable().setItems(currentData);
		
		root.topProperty().set(ui_pg);
		root.centerProperty().set(ui_table);
		//root.getChildren().addAll(new javafx.scene.Node[] {ui_pg, ui_table});
	}
	public static void main(String[] args) {
		
		launch(args);
	}
}
