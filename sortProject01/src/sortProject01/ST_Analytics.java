package sortProject01;

public class ST_Analytics{
	public double time;
	public int iterations;
	public int comparisons;
	public int movements;
	public SortClass.SortType type;
	public ST_Analytics(double time, int iterations, int comparisons, int movements, SortClass.SortType type) {
		this.time = time; this.iterations = iterations; this.comparisons = comparisons; this.movements = movements; this.type = type;
	}
}