package sortApp01;

import java.util.List;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import sortProject01.Person;
import sortProject01.SortClass;
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




class STAPanel_Analytics extends HBox{
	private VBox analyticsSimpleBox;
	private VBox analyticsGraphBox;
	private Label an_Time, an_Iterations, an_Comparisons, an_Movements;
	
	public Label STAPanel_Analytic_setTime(double d) {
		if(an_Time==null) an_Time = new Label("Time: "+String.valueOf(d));
		an_Time.textProperty().set("Time: "+String.valueOf(d));
		return an_Time;
	}
	public Label STAPanel_Analytic_setIterations(int d) {
		if(an_Iterations==null) an_Iterations = new Label("Iterations: "+String.valueOf(d));
		an_Iterations.textProperty().set("Iterations: "+String.valueOf(d));
		return an_Iterations;
	}
	public Label STAPanel_Analytic_setComparisons(int d) {
		if(an_Comparisons==null) an_Comparisons = new Label("Comparisons: "+String.valueOf(d));
		an_Comparisons.textProperty().set("Comparisons: "+String.valueOf(d));
		return an_Comparisons;
	}
	public Label STAPanel_Analytic_setMovements(int d) {
		if(an_Movements==null) an_Movements = new Label("Movements: "+String.valueOf(d));
		an_Movements.textProperty().set("Movements: "+String.valueOf(d));
		return an_Movements;
	}
	public STAPanel_Analytics() {
		analyticsSimpleBox = new VBox();
		analyticsGraphBox = new VBox();
		
		var l1 = STAPanel_Analytic_setTime(0);
		var l2 = STAPanel_Analytic_setIterations(0);
		var l3 = STAPanel_Analytic_setComparisons(0);
		var l4 = STAPanel_Analytic_setMovements(0);
		
		analyticsSimpleBox.getChildren().addAll(l1,l2,l3,l4);
		this.getChildren().addAll(analyticsSimpleBox,analyticsGraphBox);
	}
}
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

	
	
	private class generateList implements Runnable {

	
	    private Person[] returnList;

	    public generateList(int var) {
	       // this.returnList = var;
	    }

	    public void run() {
	        // code in the other thread, can reference "var" variable
	    }
	}
	Thread generatorThread;
	SortClass<Person> sortClass;
	private Dimension2D cfg_windowSize;
	private ObservableList<Person> currentData;
	final int release = 0;
	STAPanel_Generate ui_pg;
	STAPanel_TableView ui_table;
	STAPanel_Analytics ui_anal;
	Stage primary;
	private BorderPane ui_root;
	public BorderPane getRoot() {
		return ui_root;
	}
	public void registerComponents() {
		
	}
	public void updateTable() {
		
	}
	public void generateList(int size) {
		
		currentData = FXCollections.observableArrayList();
		currentData.addAll(Person.createTestList(size));
		
		ui_table.getTable().setItems(currentData);
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
		generateList(100);
	}
	private void generateControls() {
		
		var root = getRoot();
		
		root.paddingProperty().set(new Insets(10,10,10,10));

		this.ui_pg = new STAPanel_Generate();
		this.ui_table = new STAPanel_TableView();
		this.ui_anal = new STAPanel_Analytics();
		
		
		
		root.topProperty().set(ui_pg);
		root.centerProperty().set(ui_table);
		root.bottomProperty().set(ui_anal);
	}
	public static void main(String[] args) {
		
		launch(args);
	}
}
