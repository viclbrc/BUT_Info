#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <signal.h>

int main(int argc, char *argv[]) {
    kill(atoi(argv[1]), SIGCONT);
    return 0;
}