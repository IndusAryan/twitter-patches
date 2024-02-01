package indus.org.patches.twitter.themes

import app.revanced.patcher.data.ResourceContext
import app.revanced.patcher.patch.PatchException
import app.revanced.patcher.patch.ResourcePatch
import app.revanced.patcher.patch.annotation.CompatiblePackage
import app.revanced.patcher.patch.annotation.Patch
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

        context.xmlEditor["res/values/styles.xml"].use { editor ->
            val document = editor.file
            // Find the existing PaletteLightsOut style
            val styles = document.getElementsByTagName("style")
            for (i in 0 until styles.length) {
                val styleNode = styles.item(i)
                if (styleNode.attributes.getNamedItem("name")?.nodeValue == "PaletteLightsOut") {

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

                    // Clear existing items in the style
                    val childNodes = styleNode.childNodes
                    for (j in childNodes.length - 1 downTo 0) {
                        styleNode.removeChild(childNodes.item(j))
                    }

                    // Add the updated items to the existing style
                    styleItems.forEach { (k, v) ->
                        val styleElement = document.createElement("item")
                        styleElement.setAttribute("name", k)
                        styleElement.textContent = v
                        styleNode.appendChild(styleElement)
                    }

                    // Exit the loop after updating the style
                    break
                }
            }
        }
    }
}