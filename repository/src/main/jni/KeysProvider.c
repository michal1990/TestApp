//
// version 27.07.2020
//

#include <jni.h>
#include <string.h>

JNIEXPORT jstring JNICALL
Java_pl_mradtke_repository_api_ApiProvider_getUsersApiSecureHostname(
        JNIEnv *env,
        jobject instance) {
    return (*env)->NewStringUTF(env, "aHR0cHM6Ly9hcGkuZ2l0aHViLmNvbS8=");
}

JNIEXPORT jstring JNICALL
Java_pl_mradtke_repository_api_ApiProvider_getAvatarsApiSecureHostname(
        JNIEnv *env,
        jobject instance) {
    return (*env)->NewStringUTF(env, "aHR0cHM6Ly9hcGkuZGFpbHltb3Rpb24uY29tLw==");
}
