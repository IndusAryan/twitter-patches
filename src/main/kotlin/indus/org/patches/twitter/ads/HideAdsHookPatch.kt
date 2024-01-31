package indus.org.patches.twitter.ads

import app.revanced.patcher.patch.annotation.CompatiblePackage
import app.revanced.patcher.patch.annotation.Patch
import indus.org.patches.twitter.json.JsonHookPatch
import indus.org.patches.twitter.recommendation.BaseHookPatch

@Patch(
    name = "Hide recommended users",
    dependencies = [JsonHookPatch::class],
    compatiblePackages = [CompatiblePackage("com.twitter.android")]
)
@Suppress("unused")
object HideRecommendedUsersPatch : BaseHookPatch(
    "Lapp/revanced/integrations/twitter/patches/hook/patch/recommendation/RecommendedUsersHook;"
)