#include <stdio.h>
#include <signal.h>
#include <unistd.h>
#include <string.h>

static int nb_sig = 0;

void attrape_sig(int sig, siginfo_t *siginfo, void *contexte) {
    nb_sig++;
    if (sig == SIGINT) {
        printf("Reçu un CTRL+C.\nPour arrêter le programme il faut taper 5 fois CTRL+C.\nIl en manque encore %d.\n", 5 - nb_sig);
    } else if (sig == SIGTSTP) {
        printf("Reçu un CTRL+Z.\nRemise à zéro du compteur des CTRL+C.\n");
        nb_sig = 0;
    }
}

int main() {
    struct sigaction act;
    memset (&act, '\0', sizeof(act));
    act.sa_sigaction = attrape_sig;
    act.sa_flags = SA_SIGINFO;

    int loop;
    printf("%d\n", getpid());
    sigaction(SIGINT, &act, NULL);
    sigaction(SIGTSTP, &act, NULL);
    for (loop = 0; loop < 600; loop++) {
        if (nb_sig >= 5) {
            printf("Arrêt du programme.\n");
            break;
        }
        printf("PID %d - Passage %d\n", getpid(), loop);
        sleep(1);
    }
    return 0;
}