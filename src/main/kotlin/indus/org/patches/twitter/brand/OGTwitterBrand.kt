package indus.org.patches.twitter.brand

import app.revanced.patcher.data.ResourceContext
import app.revanced.patcher.patch.PatchException
import app.revanced.patcher.patch.ResourcePatch
import app.revanced.patcher.patch.annotation.CompatiblePackage
import app.revanced.patcher.patch.annotation.Patch

@Patch(
    name = "OG Twitter brand name",
    description = "Replaces the app name from nonsense X to Twitter.Experimental [may cause some issues]",
    compatiblePackages = [CompatiblePackage("com.twitter.android")]
)
@Suppress("unused")
object OGTwitterBrand : ResourcePatch() {

    override fun execute(context: ResourceContext) {

        val manifestFile = context["AndroidManifest.xml"]
        if (!manifestFile.isFile) throw PatchException("Manifest file can not be found.")

        context.xmlEditor["AndroidManifest.xml"].use { editor ->
            val document = editor.file

            // Modify the app name in the application node
            val applicationNode = document.getElementsByTagName("application").item(0)
            val appNameAttribute = applicationNode.attributes.getNamedItem("android:label")
            appNameAttribute.textContent = "Twitter"

        }
    }
}