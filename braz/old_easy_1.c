#define C

#ifdef C
char character = 'a';
#endif

int main(int argc, char **argv) {

    int a = 0;

#if defined(A) && defined(B)
    a = 1;
    #ifdef D
    a = 2;
    #endif
#endif

    return a;
}