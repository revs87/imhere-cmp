@JsModule("uuid")
//@JsNonModule // Or remove if it's a proper ES module
external object uuid {
    fun v4(): String
}