#include <stdio.h>

#define A 10
#define B 10
#define C 20

#if defined(A)  
int test() {

}
#endif

#if defined(A) && defined(B) && defined(C)
void main() {

}
#endif
