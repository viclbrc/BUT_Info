#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <unistd.h>

// char *p = NULL;
// int main()
// {
//     strcpy(p, "Ca plante !");
//     return EXIT_SUCCESS;
// }


int main()
{
    int loop;
    for (loop = 0; loop < 600; loop++)
    {
        printf("PID %d - Passage %d\n", getpid(), loop);
        sleep(1);
    }
    return 0;
}