#include "Add.h"
#include "com_demo_androidontheway_testjni_TestJNI.h"
#ifndef NULL
#define NULL 0
#endif

CAdd *pCAdd = NULL;

JNIEXPORT jboolean JNICALL Java_com_demo_androidontheway_testjni_TestJNI_init(
		JNIEnv *env, jobject obj) {
	if (pCAdd == NULL) {
		pCAdd = new CAdd;
	}
	return pCAdd != NULL;
}

JNIEXPORT jint JNICALL Java_com_demo_androidontheway_testjni_TestJNI_add(
		JNIEnv *env, jobject obj, jint x, jint y) {
	jint res = -1;
	if (pCAdd != NULL) {
		res = pCAdd->add(x, y);
	}
	return res;
}

JNIEXPORT void JNICALL Java_com_demo_androidontheway_testjni_TestJNI_destory
(JNIEnv * env, jobject obj) {
	if(pCAdd!=NULL) {
		delete pCAdd;
		pCAdd=NULL;
	}
}
