#include <stdio.h>
#include <signal.h>
#include <unistd.h>
#include <string.h>

void attrape_sig(int sig, siginfo_t *siginfo, void *contexte) {
    if (sig == SIGUSR1) {
        printf("Reçu un SIGUSR1.\n");

    }
}

int main() {
    struct sigaction act;
    memset (&act, '\0', sizeof(act));
    act.sa_sigaction = attrape_sig;
    act.sa_flags = SA_SIGINFO;

    while (1) {
        printf("Programme lancé.");
        sleep(60);
        sigaction(SIGUSR1, &act, NULL);
    }
    return 0;
}