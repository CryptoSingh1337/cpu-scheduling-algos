#include<stdio.h>

void readArray(int arr[], int n) {
	for (int i = 0; i < n; i++)
		scanf("%d", &arr[i]);
}

void printArray(int arr[], int n) {
	for (int i = 0; i < n; i++)
		printf("%d ", arr[i]);
	printf("\n");
}

void first_come_first_serve() {
	int n;
	scanf("%d", &n);
	int AT[n], BT[n], TAT[n], WT[n];
	readArray(AT, n);
	readArray(BT, n);
	int ft = 0, sum_TAT = 0, sum_WT = 0;
	for (int i = 0; i < n; i++) {
		ft += BT[i];
		TAT[i] = ft - AT[i];
		WT[i] = TAT[i] - BT[i];
		sum_TAT += TAT[i];
		sum_WT += WT[i];
	}
	printf("Turn Around Time of each Process: \n");
	printArray(TAT, n);
	printf("Waiting Time of each Process: \n");
	printArray(WT, n);
	printf("Avg. Turn Around Time: %lf ms.\n", (sum_TAT / (double)n));
	printf("Avg. Waiting Time: %lf ms.\n", (sum_WT / (double)n));
}

int main() {
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);
	int t, a;
	scanf("%d", &t);
	a = t;
	while (t--) {
		printf("For Input %d:\n", a - t);
		// first_come_first_serve();
		printf("\n");
	}
	return 0;
}