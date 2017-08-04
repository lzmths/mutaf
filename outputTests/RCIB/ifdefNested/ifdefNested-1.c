#ifdef A
void f() {



}
#endif

void main () {
#ifdef A
#ifdef B
int x = 1;
#endif
#endif

}
