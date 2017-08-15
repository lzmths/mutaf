#define A

int x;
#ifdef A
int a;
#endif

int f() {
    int a, b, c;
	int i = 1;
#ifdef A
	int j = 2;
	int k = 3;
#endif
	return i + j + k;
}
void main() {
	x = 1;
}
#ifndef A
int b;
#endif



