package indus.org.patches.twitter.themes

import app.revanced.patcher.data.ResourceContext
import app.revanced.patcher.patch.PatchException
import app.revanced.patcher.patch.ResourcePatch
import app.revanced.patcher.patch.annotation.CompatiblePackage
import app.revanced.patcher.patch.annotation.Patch
import java.io.FileWriter
import java.nio.file.Files

@Patch(
    name = "Grey Theme Patch",
    description = "Replaces the LightsOut (Amoled) Twitter theme with lighter Grey color.",
    compatiblePackages = [CompatiblePackage("com.twitter.android")]
)
@Suppress("unused")
object ShadesOfGreyPatch  : ResourcePatch() {

    override fun execute(context: ResourceContext) {
        val resDirectory = context["res"]
        if (!resDirectory.isDirectory) throw PatchException("The res folder can not be found.")

        val valuesDirectory = resDirectory.resolve("values")
        if (!valuesDirectory.isDirectory) Files.createDirectories(valuesDirectory.toPath())

        listOf(valuesDirectory).forEach { it ->
            val stylesXml = it.resolve("styles.xml")

            if (!stylesXml.exists()) {
                FileWriter(stylesXml).use {
                    it.write("<?xml version=\"1.0\" encoding=\"utf-8\"?><resources></resources>")
                }
            }
        }

        context.xmlEditor["res/values/styles.xml"].use { editor ->
            val document = editor.file

            val newStyle = document.createElement("style")
            newStyle.setAttribute("name", "PaletteLightsOut")
            newStyle.setAttribute("parent", "@style/HorizonColorPaletteDark")

            val styleItems = mapOf(
                "abstractColorCellBackground" to "#101010",
                "abstractColorCellBackgroundTranslucent" to "#101010",
                "abstractColorDeepGray" to "#ff7c838a",
                "abstractColorDivider" to "#ff2f3336",
                "abstractColorFadedGray" to "#101010",
                "abstractColorFaintGray" to "#101010",
                "abstractColorHighlightBackground" to "@color/black",
                "abstractColorLightGray" to "#ff2f3336",
                "abstractColorLink" to "@color/twitter_blue",
                "abstractColorMediumGray" to "#ffd9d9d9",
                "abstractColorText" to "@color/white",
                "abstractColorUnread" to "#ff051d2b",
                "abstractElevatedBackground" to "#ff1b2023",
                "abstractElevatedBackgroundShadow" to "@color/black_opacity_10"
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