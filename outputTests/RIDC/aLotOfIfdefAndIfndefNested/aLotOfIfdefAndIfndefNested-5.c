#include <stdio.h>

#define A 10

#ifndef A
int f() {
    int a = 10;
}
#endif 

#ifdef C
void main() {
#ifdef A
#ifdef B
#ifdef C
    int i = 0;
#endif
#endif

    int x = 1;

    int j = 2;
#endif
}
#endif
