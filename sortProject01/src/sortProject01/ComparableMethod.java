package sortProject01;
// Early on I designed this interface to do a fraction of the function that the built in java interface Comparator offers.
// Why re-invent the wheel? I mean, there is a time and place for everything but if you make -that- a habit you will spend a lot of time on it.

public interface ComparableMethod<T>{
	int compareTo(T a, T b);
}
