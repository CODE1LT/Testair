package lt.code1.testair.utils

fun getTag(stackTraceElement: StackTraceElement?) =
    stackTraceElement
        ?.className
        ?.substringAfterLast(".")
        ?.substringBefore("$")
        ?: ""

fun getFunctionName(stackTraceElement: StackTraceElement?) =
    stackTraceElement
        ?.let {
            if (stackTraceElement.className.contains("$")) {  //Logger called from lambda
                stackTraceElement
                    .className
                    .substringAfter("$")
                    .substringBeforeLast("$")
                    .plus("()")
            } else {
                stackTraceElement
                    .methodName
                    .plus("()")
            }
        } ?: ""

fun getStackTraceElement(javaClass: Class<*>): StackTraceElement? {
    Thread.currentThread()
        .stackTrace
        .let { stackTrace ->
            stackTrace.indices.forEach { stackTraceElementIndex ->
                if (stackTraceElementIndex > 0 &&
                    stackTrace[stackTraceElementIndex].className != javaClass.canonicalName &&
                    stackTrace[stackTraceElementIndex - 1].className == javaClass.canonicalName
                ) {
                    return stackTrace[stackTraceElementIndex]
                }
            }
        }
    return null
}