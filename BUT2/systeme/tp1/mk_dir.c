#include <stdio.h>
#include <sys/stat.h>
#include <fcntl.h>

int main() {
    int result = mkdir("doss_sys2A", 0770);
    int droits = chmod("doss_sys2A", 0770);
}