package kim.uno.kotlin.base.annotation

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class FragmentArg(val key: String = "")
