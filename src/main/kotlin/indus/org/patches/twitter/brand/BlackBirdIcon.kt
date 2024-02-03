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
    name = "Black Bird icon",
    description = "Black bird with white background.",
    compatiblePackages = [CompatiblePackage("com.twitter.android")]
)
@Suppress("unused")
object BlackBirdIcon : ResourcePatch() {

    private val newAvatarMarkerTwitterContent = XMLUtils.newAvatarMarkerTwitterContent.trimIndent()
    private val blackbird = XMLUtils.blackBirdIco.trimIndent()

    override fun execute(context: ResourceContext) {

        updateAvatarMarkerTwitterXml(context)
        updateLauncherXmlFiles(context)
        XMLUtils.updateXVector(context)
        SplashScreenHelper().changeSplashScreen(context)
    }

    private fun updateAvatarMarkerTwitterXml(context: ResourceContext) {
        val avatarMarkerTwitterFile = context["res/drawable/avatar_marker_twitter.xml"]
        if (!avatarMarkerTwitterFile.isFile) Files.createFile(avatarMarkerTwitterFile.toPath())

        avatarMarkerTwitterFile.writeText(blackbird)
    }

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