/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class uk_co_nickthecoder_jame_Sound */

#ifndef _Included_uk_co_nickthecoder_jame_Sound
#define _Included_uk_co_nickthecoder_jame_Sound
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     uk_co_nickthecoder_jame_Sound
 * Method:    sound_load
 * Signature: (Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_uk_co_nickthecoder_jame_Sound_sound_1load
  (JNIEnv *, jobject, jstring);

/*
 * Class:     uk_co_nickthecoder_jame_Sound
 * Method:    sound_free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_uk_co_nickthecoder_jame_Sound_sound_1free
  (JNIEnv *, jobject, jlong);

/*
 * Class:     uk_co_nickthecoder_jame_Sound
 * Method:    sound_play
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_uk_co_nickthecoder_jame_Sound_sound_1play
  (JNIEnv *, jobject, jlong);

#ifdef __cplusplus
}
#endif
#endif
