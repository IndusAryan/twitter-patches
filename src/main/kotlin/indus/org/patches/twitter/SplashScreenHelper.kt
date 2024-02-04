package indus.org.patches.twitter

import app.revanced.patcher.data.ResourceContext

class SplashScreenHelper {

    fun changeSplashScreen(context: ResourceContext) {

        val newSplashVector = """
<?xml version='1.0' encoding='utf-8' ?>
<vector android:height="192.0dp"
        android:width="192.0dp"
        android:viewportWidth="288.0"
        android:viewportHeight="288.0" xmlns:android="http://schemas.android.com/apk/res/android">
  <path android:fillColor="#ffffffff"
        android:pathData="M205.66,112.13c-4.254,1.885 -8.823,3.158 -13.627,3.734 4.9,-2.934 8.66,-7.59 10.432,-13.132 -4.584,2.72 -9.663,4.696 -15.067,5.756 -4.33,-4.605 -10.493,-7.488 -17.32,-7.488 -13.1,0 -23.726,10.626 -23.726,23.737 0,1.854 0.214,3.657 0.61,5.4 -19.727,-0.994 -37.204,-10.443 -48.91,-24.797 -2.036,3.515 -3.208,7.59 -3.208,11.93 0,8.23 4.192,15.5 10.554,19.754 -3.892,-0.127 -7.55,-1.192 -10.748,-2.97v0.306c0,11.497 8.175,21.088 19.035,23.268 -1.997,0.54 -4.09,0.825 -6.25,0.825 -1.528,0 -3.02,-0.143 -4.467,-0.418 3.02,9.423 11.782,16.29 22.168,16.473 -8.125,6.367 -18.358,10.162 -29.472,10.162 -1.915,0 -3.805,-0.112 -5.664,-0.33 10.503,6.738 22.973,10.66 36.37,10.66 43.652,0 67.517,-36.155 67.517,-67.513 0,-1.02 -0.025,-2.048 -0.07,-3.066 4.634,-3.35 8.658,-7.522 11.832,-12.275l0.01,-0.015z" />
</vector>
    """.trimIndent()

        val splashFile = context["res/drawable/splash_screen_icon.xml"]
        splashFile.writeText(newSplashVector)
    }
}
