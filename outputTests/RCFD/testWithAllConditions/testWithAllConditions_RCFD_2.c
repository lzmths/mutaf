#define X

#ifdef A
#define B
#endif

#ifndef C

#endif


void main() {
    #ifdef A
        #ifdef B
            if (x > 10) {
                #define CONST 10
            }
        #endif
    
    #endif
}

#if A > 20
    #define A 10
#endif
