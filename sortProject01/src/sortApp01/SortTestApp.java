package sortApp01;

import java.time.LocalTime;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import sortProject01.Person;
import sortProject01.SortClass;
import javafx.geometry.Dimension2D;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.SortType;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;




class STAPanel_Analytics extends HBox{
	private VBox analyticsSortBox;
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
		
		
		this.setPadding(new Insets(5,0,5,0));
		
		analyticsSimpleBox = new VBox();
		analyticsGraphBox = new VBox();
		analyticsSortBox = new VBox();
		var l1 = STAPanel_Analytic_setTime(0);
		var l2 = STAPanel_Analytic_setIterations(0);
		var l3 = STAPanel_Analytic_setComparisons(0);
		var l4 = STAPanel_Analytic_setMovements(0);
		
		analyticsSimpleBox.getChildren().addAll(l1,l2,l3,l4);
		
		
		ChoiceBox<String> sortTypeSelector = new ChoiceBox<String>();
		ObservableList<String> a = FXCollections.observableArrayList();
		for(var v : SortClass.SortType.values()) {
			a.add(v.name());
		}
		sortTypeSelector.itemsProperty().set(a);
		
		analyticsSortBox.getChildren().add(sortTypeSelector);
		
		this.getChildren().addAll(analyticsSortBox, analyticsSimpleBox, analyticsGraphBox);
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
		for(var c : tb_display.getColumns()) {
			c.setSortable(false);
	
		}
		setHgrow(tb_display, Priority.ALWAYS);
		
		this.getChildren().add(tb_display);
	}
	public TableView<Person> getTable() {
		return this.tb_display;
	}
}


class STAButton extends Button{
	public STAButton(String s){
		super(s);
	}
}
class STAPanel_Generate extends HBox{
	Label gen_genLabel;
	STAButton gen_genButton;
	STAButton gen_shuffleButton;
	TextField gen_genNumField;
	public STAPanel_Generate() {
		 gen_genLabel = new Label("Enter an integer for list size creation. Large integers may take longer to analyze.");
		 gen_shuffleButton = new STAButton("Shuffle");
		 gen_genButton = new STAButton("Generate");	 
		 gen_genNumField = new TextField();
		setHgrow(gen_genLabel, Priority.SOMETIMES);
		setHgrow(gen_genNumField, Priority.SOMETIMES);
		
		this.setPadding(new Insets(10,0,10,0));
		
		this.getChildren().addAll(gen_genLabel,gen_shuffleButton, gen_genButton, gen_genNumField);
		
		
		this.gen_genNumField.textProperty().addListener(new ChangeListener<String>() {			
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				newValue = newValue.replaceAll("[A-z]*","");
				gen_genNumField.textProperty().set(newValue);
			}			
		});
	}
}
public class SortTestApp extends Application {

	// Generate list on another thread, because it might take a while.
	interface glCallback {
	    void callback(Person[] result);
	}
	private class generateList implements Runnable {
		glCallback g;
		int size;
	    public generateList(int size, glCallback g) {
	    	this.size=size; this.g=g;
	    }

	    public void run() {
	    	var r = Person.createTestList(size);
	    	g.callback(r);
	    }
	}
	private class shuffleLister implements Runnable {
		glCallback g;
		Person[] input;
	    public shuffleLister(Person[] input, glCallback g) {
	    	this.input=input; this.g=g;
	    }

	    public void run() {
	    	var r = SortTestApp.shuffle(input);	    	
	    	g.callback(r);
	    }
	    
	}
	private class listSorter implements Runnable {
		glCallback g;
		Person[] input;
		SortClass.SortType type;
	    public listSorter(Person[] input, SortClass.SortType type, glCallback g) {
	    	this.input=input; this.g=g; this.type=type;
	    }

	    public void run() {
	    		
	    	g.callback(r);
	    }
	    
	}
	Thread generatorThread;
	SortClass<Person> sortClass;
	private Dimension2D cfg_windowSize;
	private ObservableList<Person> currentData;
	
	public ObservableList<Person> getCurrentData(){
		if(currentData==null) currentData = FXCollections.observableArrayList();		
		return currentData;
	}
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
		ui_pg.gen_genButton.setOnMouseClicked((e) -> {
			int attConv = Integer.parseInt(ui_pg.gen_genNumField.textProperty().getValueSafe());
			if(attConv > 0) {
				generateList(attConv);
			}
		});
		ui_pg.gen_shuffleButton.setOnMouseClicked((e) -> {
			shuffleList();
		});
	}
	public void updateTable() {
		ui_table.getTable().setItems(currentData);
	}
	public void shuffleList() {
		generatorThread = new Thread(new shuffleLister(currentData.toArray(new Person[1]), (r) -> {
			getCurrentData().setAll(r);
			updateTable();
			}));
		generatorThread.run();
	}
	public void generateList(int size) {
		currentData = FXCollections.observableArrayList();		
		generatorThread = new Thread(new generateList(size, (r) -> {
			getCurrentData().addAll(r);
			updateTable();
			}));
		generatorThread.run();
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		   
		primary = primaryStage;
		cfg_windowSize	= new Dimension2D(800,600);
		primaryStage.setTitle("Sort App Test V"+release);
		ui_root = new BorderPane();	
		   
		generateControls();	
		registerComponents();
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
	
	
	// ------------------------------------ Utility Functions
	
	public static <T> T[] shuffle(T[] in){
		for(int i = 0; i < in.length;i++) {
			int ran = (int)(Math.random() * in.length);
			T held = in[i];
			in[i]=in[ran];
			in[ran]=held;
		}
		return in;
	}
	
}
