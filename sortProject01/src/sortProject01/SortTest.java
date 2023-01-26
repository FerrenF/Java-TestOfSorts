package sortProject01;

import java.util.Random;
import java.util.random.RandomGenerator;

import com.github.javafaker.Faker;
public class SortTest {

	
	public static <T> T[] shuffle(T[] in){
		for(int i = 0; i < in.length;i++) {
			int ran = (int)(Math.random() * in.length);
			T held = in[i];
			in[i]=in[ran];
			in[ran]=held;
		}
		return in;
	}
	SortClass testSortObject;
	public static void main(String[] args) {
		
		int sz = 100;
		// Let's create our n length list of fake people.
		System.out.println("Generated a list of size " + sz);
		Person[] testList = SortTest.shuffle(Person.createTestList(sz));
		
		
		// Let's feed it into our SortClass and try some sorting!
		
		var newList = testList.clone();
		SortClass<Person> p = new SortClass<Person>(newList);
		p.sortListMerge();
		for(int i = 0;i < newList.length;i++) {
			System.out.println(newList[i].toString());
		}
		System.out.println( String.format("Iterations: %d \nManipulations: %d \nComparisons: %d \nTime: %d ms",p.getIterationCounter(),p.getManipulationCounter(),p.getComparisonCounter(),p.getSortTime() / 1000000));
		
		int ranInt = new Random().nextInt(0, testList.length);
		Person randomElement = testList[ranInt];
		System.out.println("Got element "+ranInt+": "+randomElement.toString()+" - " +"searching...");
		
		//int bSearch = new BinarySearch<Person>().search(testList, randomElement);
	//	System.out.println("Search Result: " + bSearch);
	}

}
