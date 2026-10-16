#include <stdio.h>
#include <signal.h>
#include <unistd.h>
#include <string.h>

// void mon_action(int sig) {
//     printf("Recu signal %d\n", sig);
// }

// int main() {
//     int loop;
//     printf("%d\n", getpid());
//     signal(SIGTERM, mon_action);
//     for (loop = 0; loop < 600; loop++) {
//         printf("PID %d - Passage %d\n", getpid(), loop);
//         printf("%d\n", sleep(5));
//     }
//     return 0;
// }

void attrape_sig(int sig, siginfo_t *siginfo, void *contexte) {
    printf("Signal reçu de PID %d\n", siginfo->si_pid);
    printf("Signal reçu de UID %d\n", siginfo->si_uid);
}

int main() {
    struct sigaction act;
    memset (&act, '\0', sizeof(act));
    act.sa_sigaction = attrape_sig;
    act.sa_flags = SA_SIGINFO;

    int loop;
    printf("%d\n", getpid());
    sigaction(SIGTSTP, &act, NULL);
    for (loop = 0; loop < 600; loop++) {
        printf("PID %d - Passage %d\n", getpid(), loop);
        sleep(5);
    }
    return 0;
}