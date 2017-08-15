#define A

int x;
#ifdef A
int a;
#endif

int f() {
    int a, b, c;
	
#ifdef A
	int j = 2;
	int k = 3;
#endif
	int i = 1;
return i + j + k;
}
#ifndef A
int b;
#endif

void main() {
	x = 1;
}

