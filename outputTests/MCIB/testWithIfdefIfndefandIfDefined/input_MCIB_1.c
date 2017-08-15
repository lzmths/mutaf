#define A


#ifdef A
int a;
#endif

int x;
int f() {
    int a, b, c;
	int i = 1;
#ifdef A
	int j = 2;
	int k = 3;
#endif
	return i + j + k;
}
#ifndef A
int b;
#endif

void main() {
	x = 1;
}

