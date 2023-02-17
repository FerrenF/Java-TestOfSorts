package sortApp01;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;


class SortTestViewTable extends ListView{
	
}
class SortTestTabPane extends VBox{
	private Tab[] tabs;
	
	private Pane root;
	private TabPane tp_tab;
	
	private VBox gen_root;
	private HBox gen_input;
	
	private VBox view_root;
	private SortTestViewTable view_list;
	public SortTestTabPane(Pane root){
		this.root=root;
	
		//Initialization
		tp_tab = new TabPane();	
		tabs = new Tab[] {
				new Tab("Generate"),
				new Tab("View"),
				new Tab("Analyze")
		};
		
		setVgrow(tp_tab, Priority.ALWAYS);
		
		// Generate Tab Content
		gen_root = new VBox();
		gen_input = new HBox();
		Label gen_genLabel = new Label("Enter an integer for list size creation. Large integers may take longer to analyze.");
		Button gen_genButton = new Button("Generate");
		TextField gen_genNumField = new TextField();
		gen_input.getChildren().addAll(gen_genButton, gen_genNumField);
		gen_root.getChildren().addAll(gen_genLabel, gen_input);
		tabs[0].setContent(gen_root);
		
		//End Generate Tab Content
		
		//View Tab Content
		
		view_root = new VBox();
		view_list = new SortTestViewTable();
		
		view_root.getChildren().addAll(view_list);
		tabs[1].setContent(view_root);
		
		//End View Tab Content
		
		tp_tab.getTabs().addAll(tabs);		
		this.getChildren().add(tp_tab);
		tp_tab.getSelectionModel().select(0);
		
		//This enables us to click through the Pane that represents this class
		this.setPickOnBounds(false);
	
	}
}
public class SortTestApp extends Application {

	final int release = 0;
	SortTestTabPane ui_tabPane;
	StackPane ui_root;
	@Override
	public void start(Stage primaryStage) throws Exception {
		   
		   primaryStage.setTitle("Sort App Test V"+release);
		  
		   ui_root = new StackPane();
		   ui_tabPane = new SortTestTabPane(ui_root);
		   
		   ui_root.getChildren().add(ui_tabPane);
	        primaryStage.setScene(new Scene(ui_root, 800, 600));
	        primaryStage.show();
		
	}
	public static void main(String[] args) {
		
		launch(args);
	}
}
