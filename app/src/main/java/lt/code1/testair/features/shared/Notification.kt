package lt.code1.testair.features.shared

data class Notification(
    val isOperationSuccessfull: Boolean,
    val successMessageId: Int,
    val unsuccessMessageId: Int
)