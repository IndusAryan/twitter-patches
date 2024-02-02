package indus.org.patches.twitter.brand

import app.revanced.patcher.data.ResourceContext
import app.revanced.patcher.patch.PatchException
import app.revanced.patcher.patch.ResourcePatch
import app.revanced.patcher.patch.annotation.CompatiblePackage
import app.revanced.patcher.patch.annotation.Patch
import java.io.File
import java.io.FileOutputStream
import java.nio.file.Files

@Patch(
    name = "White Bird icon",
    description = "Replaces the X icon with a white edition of Blue Bird.",
    compatiblePackages = [CompatiblePackage("com.twitter.android")]
)
@Suppress("unused")
object OGBirdIcon : ResourcePatch() {

    override fun execute(context: ResourceContext) {

        val customIconFile = File("brand/raw/ic_launcher_twitter.webp")
        val customIconRoundFile = File("brand/raw/ic_launcher_twitter_round.webp")
        val customIconMonochromeFile = File("brand/raw/ic_launcher_twitter_monochrome.webp")

        val resDirectory = context["res"]
        if (!resDirectory.isDirectory) throw PatchException("The res folder can not be found.")

        val drawableDirectory = resDirectory.resolve("drawable")

        if (!drawableDirectory.isDirectory) Files.createDirectories(drawableDirectory.toPath())

        // Copy our icons to res/drawable
        copyFile(customIconFile, drawableDirectory.resolve("ic_launcher_twitter.webp"))
        copyFile(customIconRoundFile, drawableDirectory.resolve("ic_launcher_twitter_round.webp"))
        copyFile(customIconMonochromeFile, drawableDirectory.resolve("ic_launcher_twitter_monochrome.webp"))

        val mipmapDirectory = resDirectory.resolve("mipmap-anydpi")
        val icLauncherTwitterXml = mipmapDirectory.resolve("ic_launcher_twitter.xml")
        val icLauncherTwitterRoundXml = mipmapDirectory.resolve("ic_launcher_twitter_round.xml")

        // Modify the ic_launcher_twitter.xml and $round
        modifyIcLauncherTwitterXml(icLauncherTwitterXml)
        modifyIcLauncherTwitterRoundXml(icLauncherTwitterRoundXml)
    }

    // copy drawables
    private fun copyFile(source: File, destination: File) {
        destination.outputStream().use { output ->
            source.inputStream().use { input ->
                input.copyTo(output)
            }
        }
    }

    // function to modify the ic_launcher_twitter.xml and ic_launcher_twitter_round.xml
    private fun modifyIcLauncherTwitterXml(xmlFile: File) {
        val content = xmlFile.readText()

        val modifiedContent = content.replace(
            """android:drawable="@mipmap/ic_launcher_twitter_foreground"""",
            """android:drawable="@drawable/ic_launcher_twitter""""
        ).replace(
            """android:drawable="@drawable/ic_launcher_twitter_foreground"""",
            """android:drawable="@drawable/ic_launcher_twitter_monochrome""""
        )

        xmlFile.writeText(modifiedContent)
    }

    private fun modifyIcLauncherTwitterRoundXml(xmlFile: File) {
        val content = xmlFile.readText()

        val modifiedContent = content.replace(
        """android:drawable="@mipmap/ic_launcher_twitter_foreground"""",
        """android:drawable="@drawable/ic_launcher_twitter_round""""
        ).replace(
            """android:drawable="@mipmap/ic_launcher_twitter_foreground"""",
            """android:drawable="@drawable/ic_launcher_twitter_monochrome""""
        )

        xmlFile.writeText(modifiedContent)
    }
}