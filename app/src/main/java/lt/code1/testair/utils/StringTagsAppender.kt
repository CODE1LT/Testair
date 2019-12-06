package lt.code1.testair.utils

class StringTagsAppender {
    fun appendTag(text: String, tag: String, startPosition: Int, endPosition: Int? = null): String {
        val textBeforeTag = text.substring(0, startPosition)
        val textBetweenTag = endPosition?.let { text.substring(startPosition, it) }
        val textAfterTag = endPosition?.let {
            text.substring(it)
        } ?: text.substring(startPosition)

        return endPosition?.let { "$textBeforeTag<$tag>$textBetweenTag</$tag>$textAfterTag" }
            ?: "$textBeforeTag<$tag>$textAfterTag"
    }
}