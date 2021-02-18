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
			if (a[2] > b[2])
				return 1;
			else if (a[2] < b[2])
				return -1;
			else
				return 0;
		}
	}
}

class HeapCmp implements Comparator<int[]> {

	@Override
	public int compare(int[] a, int[] b) {
		if (a[2] > b[2])
			return 1;
		else if (a[2] < b[2])
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

public class ShortestJobFirst {

	static long TIME_START, TIME_END;

	public static class Task {

		void shortestJobFirst(int[][] process, PrintWriter pw) {
			int n = process.length;
			Arrays.sort(process, new Cmp());
			for (int[] arr : process)
				pw.println(Arrays.toString(arr));
			int ft = 0;
			for (int i = 0; i < n; i++) {
				// pw.println("Iteration: " + (i + 1));
				ft += process[i][2];
				process[i][3] = ft;
				process[i][4] = process[i][3] - process[i][1];
				process[i][5] = process[i][4] - process[i][2];
				int timeTaken = process[i][2];
				i += 1;
				PriorityQueue<int[]> minHeap = new PriorityQueue<>(5, new HeapCmp());
				while (i < n && process[i][1] <= timeTaken) {
					// ft += process[i][2];
					// process[i][3] = ft;
					// process[i][4] = process[i][3] - process[i][1];
					// process[i][5] = process[i][4] - process[i][2];
					// i++;
					minHeap.add(process[i++]);
				}
				// for (int[] arr : minHeap)
				// 	pw.println(Arrays.toString(arr));
				// int j = 1;
				while (!minHeap.isEmpty()) {
					int[] currProcess = minHeap.poll();
					ft += currProcess[2];
					currProcess[3] = ft;
					currProcess[4] = currProcess[3] - currProcess[1];
					currProcess[5] = currProcess[4] - currProcess[2];
					// if (j++ == 1)
					// 	pw.println(Arrays.toString(currProcess));
				}
			}
		}

		public void solve(Scanner sc, PrintWriter pw) throws IOException {
			int n = sc.nextInt();
			int[][] process = new int[n][6];
			for (int i = 0; i < n; i++) {
				process[i][0] = sc.nextInt();
				process[i][1] = sc.nextInt();
				process[i][2] = sc.nextInt();
			}
			shortestJobFirst(process, pw);
			pw.println("PID\t\tAT\t\tBT\t\tFT\t\tTAT\t\tWT");
			for (int i = 0; i < n; i++) {
				pw.println(process[i][0] + "\t\t" + process[i][1] + "\t\t" + process[i][2] + "\t\t" + process[i][3] + "\t\t" + process[i][4] + "\t\t" + process[i][5]);
			}
			int sum_TAT = 0, sum_WT = 0;
			for (int i = 0; i < n; i++) {
				sum_TAT += process[i][4];
				sum_WT += process[i][5];
			}
			pw.println("Avg. TAT: " + (sum_TAT / (double) n) + " ms.");
			pw.println("Avg. WT: " + (sum_WT / (double) n) + " ms.");
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
		while (T-- > 0) {
			t.solve(sc, pw);
		}
		TIME_END = System.currentTimeMillis();
		long usedMemoryAfter = runtime.totalMemory() - runtime.freeMemory();
		pw.close();
		System.err.println("Memory increased: " + (usedMemoryAfter - usedMemoryBefore) / 1000000);
		System.err.println("Time used: " + (TIME_END - TIME_START) + ".");
	}
}