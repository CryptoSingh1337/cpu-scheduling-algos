import java.io.*;
import java.util.*;

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

public class RoundRobin {

	static long TIME_START, TIME_END;

	public static class Task {

		void roundRobin(int[][] process, int timeSlice, PrintWriter pw) {
			int n = process.length;
			int ft = 0;
			boolean[] start = new boolean[n];
			Arrays.fill(start, true);
			Queue<int[]> waitingQueue = new LinkedList<>();
			for (int[] arr : process)
				waitingQueue.add(arr);
			while (!waitingQueue.isEmpty()) {
				int[] currProcess = waitingQueue.remove();
				if (start[currProcess[0] - 1]) {
					currProcess[8] = ft;
					start[currProcess[0] - 1] = false;
				}
				if (currProcess[7] > 0) {
					if (currProcess[7] <= timeSlice) {
						if (currProcess[7] == timeSlice) {
							ft += timeSlice;
						} else {
							ft += currProcess[7];
						}
						currProcess[7] = 0;
						currProcess[3] = ft;
						currProcess[4] = ft - currProcess[1];
						currProcess[5] = currProcess[4] - currProcess[2];
					} else {
						ft += timeSlice;
						currProcess[7] -= timeSlice;
						waitingQueue.add(currProcess);
					}
				}
			}
			for (int[] arr : process)
				arr[6] = arr[8] - arr[1];
		}

		public void solve(Scanner sc, PrintWriter pw) throws IOException {
			int n = sc.nextInt();
			int[][] process = new int[n][9];
			for (int i = 0; i < n; i++) {
				process[i][0] = sc.nextInt();
				process[i][1] = sc.nextInt();
				process[i][2] = sc.nextInt();
				process[i][7] = process[i][2];
			}
			int timeSlice = sc.nextInt();
			roundRobin(process, timeSlice, pw);
			pw.println("\tPID\t\tAT\t\tBT\t\tFT\t\tTAT\t\tWT\t\tRT\t\tST");
			for (int i = 0; i < n; i++) {
				pw.println("\t" + process[i][0] + "\t\t" + process[i][1] + "\t\t" + process[i][2] + "\t\t" + process[i][3] + "\t\t" + process[i][4] + "\t\t" + process[i][5] + "\t\t" + process[i][6] + "\t\t" + process[i][8]);
			}
			int sum_TAT = 0, sum_WT = 0, sum_RT = 0;
			for (int i = 0; i < n; i++) {
				sum_TAT += process[i][4];
				sum_WT += process[i][5];
				sum_RT += process[i][6];
			}
			pw.println("\tAvg. TAT: " + (sum_TAT / (double) n) + " ms.");
			pw.println("\tAvg. WT: " + (sum_WT / (double) n) + " ms.");
			pw.println("\tAvg. RT: " + (sum_RT / (double) n) + " ms.");
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