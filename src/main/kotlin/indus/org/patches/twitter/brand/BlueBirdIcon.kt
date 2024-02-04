package indus.org.patches.twitter.brand

import app.revanced.patcher.data.ResourceContext
import app.revanced.patcher.patch.ResourcePatch
import app.revanced.patcher.patch.annotation.CompatiblePackage
import app.revanced.patcher.patch.annotation.Patch
import indus.org.patches.twitter.SplashScreenHelper
import indus.org.patches.twitter.XMLUtils

@Patch(
    name = "OG Bird icon",
    description = "Replaces the X icon with original Blue Twitter icon.",
    compatiblePackages = [CompatiblePackage("com.twitter.android")]
)
@Suppress("unused")
object BlueBirdIcon : ResourcePatch() {

    /** FutureProof way in case someone changes icon frequently **/
    override fun execute(context: ResourceContext) {

        XMLUtils(context).blueIcon()
        XMLUtils(context).updateLauncherXmlFiles()
        XMLUtils(context).updateXVector(context)
        SplashScreenHelper().changeSplashScreen(context)
    }
}