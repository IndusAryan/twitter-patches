package indus.org.patches.twitter.brand

import app.revanced.patcher.data.ResourceContext
import app.revanced.patcher.patch.ResourcePatch
import app.revanced.patcher.patch.annotation.CompatiblePackage
import app.revanced.patcher.patch.annotation.Patch
import indus.org.patches.twitter.SplashScreenHelper
import indus.org.patches.twitter.XMLUtils

@Patch(
    name = "White Bird icon",
    description = "Replaces the X icon with a white Bird keeping black background.",
    compatiblePackages = [CompatiblePackage("com.twitter.android")]
)
@Suppress("unused")
object WhiteBirdIcon : ResourcePatch() {

    override fun execute(context: ResourceContext) {

        XMLUtils(context).whiteIconBlackBG()
        XMLUtils(context).updateLauncherXmlFiles()
        XMLUtils(context).updateXVector(context)
        SplashScreenHelper().changeSplashScreen(context)
    }
}