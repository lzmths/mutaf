#include <stdio.h>

#define A 10
#define B 10
#define C 20

#if defined(A) && defined(D)
int test() {

}
#endif

#if defined(A)   && defined(C)
void main() {

}
#endif
