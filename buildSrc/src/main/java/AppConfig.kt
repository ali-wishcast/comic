// app level config constants
object AppConfig {
	const val compileSdk = 33
	const val minSdk = 26
	const val targetSdk = 33

	// version info
	const val versionCode = 1
	const val versionName = "1.0.0"
	const val applicationId = "com.android.comic"
	const val androidTestInstrumentation = "androidx.test.runner.AndroidJUnitRunner"

	//db
	const val dbName = """"Comic""""
	const val preferencesName = """"comicPreferences""""

	const val testSignConfigName = "comic-test"
	const val testSignConfigFile = "../credentials/testSignature"

	const val prodSignConfigName = "comic-prod"
	const val prodSignConfigFile = "../credentials/prodSignature"
}

object BaseUrl {
	const val development = """"https://xkcd.com""""
	const val testEnv = """"""""
	const val production = """"""""
}

object Username {
	const val development = """"""""
	const val testEnv = """"""""
	const val production = """"""""
}

object Password {
	const val development = """"""""
	const val testEnv = """"""""
	const val production = """"""""
}
