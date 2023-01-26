package sortProject01;

import java.util.concurrent.TimeUnit;
import com.github.javafaker.Faker;

//So this class is a generic model for the type of data we are going to use to test our search methods.
public class Person implements Comparable<Person>{
	String first_name;
	String last_name;
	java.util.Date birthday;
	
	//This field will provide an identifier based on the number of instantized objects
	private int id;
	private static int counter = 0;
	
	public int getId() {
		return id;
	}
	public int getAge() {
		var d = new java.util.Date();
		return d.getYear() - birthday.getYear();
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
	
	public Person() {
		this.id = counter;
		counter++;
	}
	
	public Person(String first_name, String last_name, java.util.Date birthday) {
		this();
		this.first_name=first_name;
		this.last_name=last_name;
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