#ifdef A
void f() {
#ifdef B
int a = 2;
#endif
}
#endif

void main () {
#ifdef A
#ifdef B
int x = 1;
#endif
#endif

}
