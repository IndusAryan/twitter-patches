package indus.org.patches.twitter.brand

import app.revanced.patcher.data.ResourceContext
import app.revanced.patcher.patch.ResourcePatch
import app.revanced.patcher.patch.annotation.CompatiblePackage
import app.revanced.patcher.patch.annotation.Patch

@Patch(
    name = "White Bird icon",
    description = "Replaces the X icon with a white edition of Blue Bird.",
    compatiblePackages = [CompatiblePackage("com.twitter.android")]
)
@Suppress("unused")
object OGBirdIcon : ResourcePatch() {

        override fun execute(context: ResourceContext) {

            // Modify the fill color in avatar_marker_twitter.xml
            context.xmlEditor["res/drawable/avatar_marker_twitter.xml"].use { editor ->
                val document = editor.file

                val vector = document.getElementsByTagName("vector").item(0)
                val path = vector?.childNodes?.item(1)

                if (path != null && path.nodeName == "path") {
                    path.attributes.getNamedItem("fillColor")?.nodeValue = "#ff0f1419"
                }
            }

            // Update ic_launcher_twitter.xml and ic_launcher_twitter_round.xml
            val mipmapDirectory = context["res"].resolve("mipmap-anydpi")
            val icLauncherTwitterXml = mipmapDirectory.resolve("ic_launcher_twitter.xml")
            val icLauncherTwitterRoundXml = mipmapDirectory.resolve("ic_launcher_twitter_round.xml")

            // Update ic_launcher_twitter.xml
            context.xmlEditor["$icLauncherTwitterXml"].use { editor ->
                val content = icLauncherTwitterXml.readText()

                val modifiedContent = content.replace(
                    """android:drawable="@mipmap/ic_launcher_twitter_foreground"""",
                    """android:drawable="@drawable/ic_launcher_twitter""""
                ).replace(
                    """android:drawable="@drawable/ic_launcher_twitter_foreground"""",
                    """android:drawable="@drawable/ic_launcher_twitter_monochrome""""
                )

                icLauncherTwitterXml.writeText(modifiedContent)
            }

            // Update ic_launcher_twitter_round.xml
            context.xmlEditor["$icLauncherTwitterRoundXml"].use { editor ->
                val content = icLauncherTwitterRoundXml.readText()

                val modifiedContent = content.replace(
                    """android:drawable="@mipmap/ic_launcher_twitter_foreground"""",
                    """android:drawable="@drawable/ic_launcher_twitter_round""""
                ).replace(
                    """android:drawable="@mipmap/ic_launcher_twitter_foreground"""",
                    """android:drawable="@drawable/ic_launcher_twitter_monochrome""""
                )

                icLauncherTwitterRoundXml.writeText(modifiedContent)
            }
        }
}