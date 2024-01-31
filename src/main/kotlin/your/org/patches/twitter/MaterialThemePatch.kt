package your.org.patches.twitter

import app.revanced.patcher.data.ResourceContext
import app.revanced.patcher.patch.PatchException
import app.revanced.patcher.patch.ResourcePatch
import app.revanced.patcher.patch.annotation.CompatiblePackage
import app.revanced.patcher.patch.annotation.Patch
import java.io.FileWriter
import java.nio.file.Files

@Patch(
    name = "Full Monet Theme",
    description = "Replaces the Twitter Bluish Dim theme with the user's Material You 3 neutral palette.",
    compatiblePackages = [CompatiblePackage("com.twitter.android")]
)
@Suppress("unused")
object MaterialThemePatch : ResourcePatch() {

    override fun execute(context: ResourceContext) {
        val resDirectory = context["res"]
        if (!resDirectory.isDirectory) throw PatchException("The res folder can not be found.")

        val valuesNightV31Directory = resDirectory.resolve("values-night-v31")
        if (!valuesNightV31Directory.isDirectory) Files.createDirectories(valuesNightV31Directory.toPath())

        listOf(valuesNightV31Directory).forEach { it ->
            val stylesXml = it.resolve("styles.xml")

            if (!stylesXml.exists()) {
                FileWriter(stylesXml).use {
                    it.write("<?xml version=\"1.0\" encoding=\"utf-8\"?><resources></resources>")
                }
            }
        }

        context.xmlEditor["res/values-night-v31/styles.xml"].use { editor ->
            val document = editor.file

            val newStyle = document.createElement("style")
            newStyle.setAttribute("name", "PaletteDim")
            newStyle.setAttribute("parent", "@style/HorizonColorPaletteDark")

            val styleItems = mapOf(
                "abstractColorCellBackground" to "@color/material_dynamic_neutral10",
                "abstractColorCellBackgroundTranslucent" to "@color/material_dynamic_neutral10",
                "abstractColorDeepGray" to "#ff8899a6",
                "abstractColorDivider" to "#ff38444d",
                "abstractColorFadedGray" to "@color/material_dynamic_neutral10",
                "abstractColorFaintGray" to "@color/material_dynamic_neutral10",
                "abstractColorHighlightBackground" to "@color/material_dynamic_neutral20",
                "abstractColorLightGray" to "#ff3d5466",
                "abstractColorLink" to "@color/twitter_blue",
                "abstractColorMediumGray" to "#ff6b7d8c",
                "abstractColorText" to "@color/white",
                "abstractColorUnread" to "#ff163043",
                "abstractElevatedBackground" to "#ff1c2c3c",
                "abstractElevatedBackgroundShadow" to "#1a15202b"
            )

            styleItems.forEach { (k, v) ->
                val styleElement = document.createElement("item")

                styleElement.setAttribute("name", k)
                styleElement.textContent = v
                newStyle.appendChild(styleElement)
            }

            document.getElementsByTagName("resources").item(0).appendChild(newStyle)
        }
    }
}
