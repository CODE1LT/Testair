package lt.code1.testair.utils

fun hasEnoughTimePassed(period: Long, since: Long?): Boolean {
    return System.currentTimeMillis() - (since ?: 0) > period
}