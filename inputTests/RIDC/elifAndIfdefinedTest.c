#define D

#ifdef A
int f() {
#ifdef B
    return 1;
#elif C
    return 3;
#elif D 
    return 4; 
#else
    return 2;
#endif
}
#endif

void main() {
#if defined(A)

#endif
}
