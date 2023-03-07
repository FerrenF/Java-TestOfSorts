package sortProject01;

import java.util.concurrent.TimeUnit;
import com.github.javafaker.Faker;

import javafx.beans.property.*;

import java.time.LocalDate;
import java.util.Date;



//So this class is a generic model for the type of data we are going to use to test our search methods.
public class Person implements Comparable<Person>{
	String first_name;
	String last_name;
	java.util.Date birthday;
	
	
	private StringProperty firstName;
    public void setFirstName(String value) { firstNameProperty().set(value); }
    public String getFirstName() { return firstNameProperty().get(); }
    public StringProperty firstNameProperty() {
        if (firstName == null) firstName = new SimpleStringProperty(this, "firstName");
        return firstName;
    }

    private StringProperty lastName;
    public void setLastName(String value) { lastNameProperty().set(value); }
    public String getLastName() { return lastNameProperty().get(); }
    public StringProperty lastNameProperty() {
        if (lastName == null) lastName = new SimpleStringProperty(this, "lastName");
        return lastName;
    }
    
    private static int counter = 0;
    private IntegerProperty pid;
    public Integer getId() { return pidProperty().get(); }
    public IntegerProperty pidProperty() {
        if (pid == null) {
        	counter++;
        	pid = new SimpleIntegerProperty(counter);}
        return pid;
    }
    
    private ObjectProperty<LocalDate> birthdate;
    public void setBirthdate(LocalDate value) { birthdateProperty().set(value); }
    public LocalDate getBirthdate() { return birthdateProperty().get(); }
    public ObjectProperty<LocalDate> birthdateProperty() {
        if (birthdate == null) birthdate = new SimpleObjectProperty<LocalDate>(LocalDate.now());
        return birthdate;
    }
    	
	public int getAge() {
		var d = LocalDate.now();
		return d.getYear() - this.getBirthdate().getYear();
	}
	
	@Override
	public int compareTo(Person b) {
		if(this.getId() > b.getId()) {
			return 1;
		}
		else if(this.getId() < b.getId()) {
			return -1;
		}	
		return 0;
	}
	
	public Person(String first_name, String last_name, java.util.Date birthday) {
		this.setFirstName(first_name);
		this.setLastName(last_name);
		this.birthday=birthday;
	}
	
	public static Person[] createTestList(int size) {
		Person[] p = new Person[size];
		Faker f = new Faker();
		for(int i = 0; i < size; i++) {
			p[i] = new Person(f.name().firstName(),f.name().lastName(), f.date().past(365*50,TimeUnit.DAYS));
		}
		return p;
	}
	
	@Override
	public String toString() {
		return String.format("%d %s %s: %s (Age: %d)", this.getId(), this.first_name, this.last_name, this.birthday.toString(),this.getAge());
	}
}