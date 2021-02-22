import java.io.*;
import java.util.*;

class Cmp implements Comparator<int[]> {

	@Override
	public int compare(int[] a, int[] b) {
		if (a[1] > b[1])
			return 1;
		else if (a[1] < b[1])
			return -1;
		else {
			if (a[3] > b[3])
				return 1;
			else if (a[3] < b[3])
				return -1;
			else
				return 0;
		}
	}
}

class HeapCmp implements Comparator<int[]> {

	@Override
	public int compare(int[] a, int[] b) {
		if (a[3] > b[3])
			return 1;
		else if (a[3] < b[3])
			return -1;
		else
			return 0;
	}
}

class Scanner {

	StringTokenizer st;
	BufferedReader br;

	public Scanner(InputStream s) {
		br = new BufferedReader(new InputStreamReader(s));
	}

	public Scanner(FileReader s) throws FileNotFoundException {
		br = new BufferedReader(s);
	}

	public String next() throws IOException {
		while (st == null || ! st.hasMoreTokens())
			st = new StringTokenizer(br.readLine());
		return st.nextToken();
	}

	public int nextInt() throws IOException {
		return Integer.parseInt(next());
	}

	public long nextLong() throws IOException {
		return Long.parseLong(next());
	}

	public String nextLine() throws IOException {
		return br.readLine();
	}

	public double nextDouble() throws IOException {
		return Double.parseDouble(next());
	}

	public int[] readIntArray(int n) throws IOException {
		int[] arr = new int[n];
		String[] a = nextLine().split(" ");
		for (int i = 0; i < n; i++)
			arr[i] = Integer.parseInt(a[i]);
		return arr;
	}

	public long[] readLongArray(int n) throws IOException {
		long[] arr = new long[n];
		String[] a = nextLine().split(" ");
		for (int i = 0; i < n; i++)
			arr[i] = Long.parseLong(a[i]);
		return arr;
	}

	public boolean ready() throws IOException {
		return br.ready();
	}
}

public class Priority {

	static long TIME_START, TIME_END;

	public static class Task {

		void priority(int[][] process, PrintWriter pw) {
			int n = process.length;
			Arrays.sort(process, new Cmp());
			int ft = 0;
			for (int i = 0; i < n; i++) {
				ft += process[i][2];
				process[i][4] = ft;
				process[i][5] = process[i][4] - process[i][1];
				process[i][6] = process[i][5] - process[i][2];
				int timeTaken = process[i][2];
				i += 1;
				PriorityQueue<int[]> minHeap = new PriorityQueue<>(5, new HeapCmp());
				while (i < n && process[i][1] <= timeTaken)
					minHeap.add(process[i++]);
				while (!minHeap.isEmpty()) {
					int[] currProcess = minHeap.poll();
					ft += currProcess[2];
					currProcess[4] = ft;
					currProcess[5] = currProcess[4] - currProcess[1];
					currProcess[6] = currProcess[5] - currProcess[2];
				}
			}
		}

		public void solve(Scanner sc, PrintWriter pw) throws IOException {
			int n = sc.nextInt();
			int[][] process = new int[n][7];
			for (int i = 0; i < n; i++) {
				process[i][0] = sc.nextInt();
				process[i][1] = sc.nextInt();
				process[i][2] = sc.nextInt();
				process[i][3] = sc.nextInt();
			}
			priority(process, pw);
			pw.println("\t\tPID\t\tAT\t\tBT\t\tPR\t\tFT\t\tTAT\t\tWT");
			for (int i = 0; i < n; i++) {
				pw.println("\t" + process[i][0] + "\t\t" + process[i][1] + "\t\t" + process[i][2] + "\t\t" + process[i][3] + "\t\t" + process[i][4] + "\t\t" + process[i][5] + "\t\t" + process[i][6]);
			}
			int sum_TAT = 0, sum_WT = 0;
			for (int i = 0; i < n; i++) {
				sum_TAT += process[i][5];
				sum_WT += process[i][6];
			}
			pw.println("\tAvg. TAT: " + (sum_TAT / (double) n) + " ms.");
			pw.println("\tAvg. WT: " + (sum_WT / (double) n) + " ms.");
		}
	}

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(new FileReader(new File("out/input.txt")));
		// Scanner sc = new Scanner(System.in);
		PrintWriter pw = new PrintWriter(new BufferedOutputStream(new FileOutputStream(new File("out/output.txt"))));
		// PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out));
		Runtime runtime = Runtime.getRuntime();
		long usedMemoryBefore = runtime.totalMemory() - runtime.freeMemory();
		TIME_START = System.currentTimeMillis();
		Task t = new Task();
		int T = sc.nextInt();
		int a = T;
		while (T-- > 0) {
			pw.println("For input #" + (a - T));
			t.solve(sc, pw);
		}
		TIME_END = System.currentTimeMillis();
		long usedMemoryAfter = runtime.totalMemory() - runtime.freeMemory();
		pw.close();
		System.err.println("Memory increased: " + (usedMemoryAfter - usedMemoryBefore) / 1000000);
		System.err.println("Time used: " + (TIME_END - TIME_START) + ".");
	}
}