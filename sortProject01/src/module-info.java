module sortProject01 {
	requires javafaker;
	requires java.sql;
	requires javafx.base;
	requires javafx.controls;
	requires javafx.graphics;
	
	 opens sortApp01 to javafx.graphics;
}