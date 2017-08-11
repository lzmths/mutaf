#define D


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


void main() {
#if defined(A)

#endif
}
