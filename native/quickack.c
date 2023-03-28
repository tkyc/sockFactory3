// quickack.c
#include <jni.h>
#include <sys/socket.h>
#include <netinet/tcp.h>
#include <netinet/in.h>
#include "org_example_QuickAck.h"
# include <stdio.h>


JNIEXPORT jint JNICALL Java_org_example_QuickAck_setQuickAck(JNIEnv *env, jobject obj, jint sockfd, jint value) {
    return setsockopt(sockfd, IPPROTO_TCP, TCP_QUICKACK, (void *)&value, sizeof(value));
}