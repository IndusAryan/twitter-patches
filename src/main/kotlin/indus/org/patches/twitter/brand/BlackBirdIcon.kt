package indus.org.patches.twitter.brand

import app.revanced.patcher.data.ResourceContext
import app.revanced.patcher.patch.ResourcePatch
import app.revanced.patcher.patch.annotation.CompatiblePackage
import app.revanced.patcher.patch.annotation.Patch
import indus.org.patches.twitter.SplashScreenHelper
import indus.org.patches.twitter.XMLUtils

@Patch(
    name = "Black Bird icon",
    description = "Black bird with white background.",
    compatiblePackages = [CompatiblePackage("com.twitter.android")],
    use = false
)
@Suppress("unused")
object BlackBirdIcon : ResourcePatch() {

    override fun execute(context: ResourceContext) {

        XMLUtils(context).blackIconWhiteBG()
        XMLUtils(context).updateLauncherXmlFiles()
        XMLUtils(context).updateXVector(context)
        SplashScreenHelper().changeSplashScreen(context)
    }
}