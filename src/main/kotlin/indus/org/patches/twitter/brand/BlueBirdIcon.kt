package indus.org.patches.twitter.brand

import app.revanced.patcher.data.ResourceContext
import app.revanced.patcher.patch.PatchException
import app.revanced.patcher.patch.ResourcePatch
import app.revanced.patcher.patch.annotation.CompatiblePackage
import app.revanced.patcher.patch.annotation.Patch
import indus.org.patches.twitter.SplashScreenHelper
import indus.org.patches.twitter.XMLUtils
import java.io.File
import java.nio.file.Files

@Patch(
    name = "OG Bird icon",
    description = "Replaces the X icon with original Blue Twitter icon.",
    compatiblePackages = [CompatiblePackage("com.twitter.android")]
)
@Suppress("unused")
object BlueBirdIcon : ResourcePatch() {

    /** FutureProof way in case someone changes icon frequently **/
    private val newAvatarMarkerTwitterContent =
        """
        <?xml version='1.0' encoding='utf-8' ?>
        <vector android:height="24dp"
            android:width="24dp"
            android:viewportWidth="16.0"
            android:viewportHeight="16.0" xmlns:android="http://schemas.android.com/apk/res/android">
            <group android:translateX="-66.0"
                android:translateY="-407.0">
                <group android:translateX="66.0"
                    android:translateY="407.0">
                    <group android:translateX="1.0"
                        android:translateY="1.0">
                        <path android:fillColor="#ff2aa4f1"
                            android:pathData="M 7 0 L 7 0 Q 14 0 14 7 L 14 7 Q 14 14 7 14 L 7 14 Q 0 14 0 7 L 0 7 Q 0 0 7 0 Z"
                            android:strokeWidth="1.0"
                            android:fillType="evenOdd" />
                        <path android:pathData="M 7 -0.5 L 7 -0.5 Q 14.5 -0.5 14.5 7 L 14.5 7 Q 14.5 14.5 7 14.5 L 7 14.5 Q -0.5 14.5 -0.5 7 L -0.5 7 Q -0.5 -0.5 7 -0.5 Z"
                            android:strokeColor="#ffffffff"
                            android:strokeWidth="1.0"
                            android:fillType="evenOdd" />
                        <path android:fillColor="#ffffffff"
                            android:pathData="M5.62124476,10.3746148 C8.63327742,10.3746148 10.2807322,7.87916777 10.2807322,5.71512734 C10.2807322,5.64424847 10.2807322,5.57368888 10.2759431,5.50344856 C10.5964405,5.27162704 10.8730993,4.9845895 11.0929657,4.65577559 C10.7940865,4.78821109 10.4770313,4.87506225 10.152384,4.91342986 C10.4942449,4.70876938 10.7501047,4.38687393 10.8723473,4.007649 C10.5508885,4.19840106 10.1992043,4.33283359 9.83247126,4.40514536 C9.32485173,3.86537859 8.51824911,3.73326878 7.86495983,4.08289552 C7.21167054,4.43252225 6.87416446,5.17693506 7.04169561,5.89871 C5.72497558,5.83269991 4.49819008,5.21077734 3.66664819,4.18771964 C3.23199558,4.9359849 3.45400772,5.89323747 4.1736556,6.37378999 C3.91304524,6.36606596 3.65811699,6.29576358 3.4303853,6.16881596 L3.4303853,6.18956878 C3.43059861,6.96910639 3.9800961,7.64052154 4.74419857,7.79487939 C4.50310492,7.86063073 4.2501475,7.87024224 4.00475956,7.82297551 C4.21929518,8.49007429 4.83409806,8.94707091 5.53472145,8.96023016 C4.95483494,9.41597233 4.23848726,9.66337655 3.50094489,9.66263337 C3.37065024,9.66238323 3.24048231,9.65449427 3.11111111,9.63900708 C3.86001212,10.119603 4.73139995,10.3745244 5.62124476,10.3733377"
                            android:strokeWidth="1.0"
                            android:fillType="evenOdd" />
                    </group>
                </group>
            </group>
        </vector>
    """.trimIndent()
    //private val newBirdVectorWhite = XMLUtils.ogBirdVectorWhite.trimIndent()
    //private val newBirdVector = XMLUtils.ogBirdVector.trimIndent()

    override fun execute(context: ResourceContext) {

        updateAvatarMarkerTwitterXml(context)
        updateLauncherXmlFiles(context)
        //XMLUtils.updateXVector(context)
        SplashScreenHelper().changeSplashScreen(context)
    }

    private fun updateAvatarMarkerTwitterXml(context: ResourceContext) {
        val avatarMarkerTwitterFile = context["res/drawable/avatar_marker_twitter.xml"]
        if (!avatarMarkerTwitterFile.isFile) Files.createFile(avatarMarkerTwitterFile.toPath())

        avatarMarkerTwitterFile.writeText(newAvatarMarkerTwitterContent)
    }

    /*fun updateXVector(context: ResourceContext) {
            val vectorFile = context["res/drawable/ic_vector_twitter_white.xml"]
            vectorFile.writeText(newBirdVectorWhite)

            val vectorFileDark = context["res/drawable/ic_vector_twitter_white.xml"]
            vectorFileDark.writeText(newBirdVector)
    }*/

    private fun updateLauncherXmlFiles(context: ResourceContext) {
        val mipmapDirectory = context["res"].resolve("mipmap-anydpi")
        val icLauncherTwitterXml = mipmapDirectory.resolve("ic_launcher_twitter.xml")
        val icLauncherTwitterRoundXml = mipmapDirectory.resolve("ic_launcher_twitter_round.xml")

        // Update ic_launcher_twitter.xml && ic_launcher_twitter_round.xml
        updateXmlFile(icLauncherTwitterXml)
        updateXmlFile(icLauncherTwitterRoundXml)
    }

    private fun updateXmlFile(xmlFile: File) {
        if (!Files.isRegularFile(xmlFile.toPath())) throw PatchException("$xmlFile not found.")

        val content = xmlFile.readText()
        val modifiedContent = content.replace(
            """android:drawable="@mipmap/ic_launcher_twitter_foreground"""",
            """android:drawable="@drawable/avatar_marker_twitter""""
        )

        xmlFile.writeText(modifiedContent)
    }
}