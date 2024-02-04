package indus.org.patches.twitter.hook.patch.recommendation

import app.revanced.patcher.patch.annotation.CompatiblePackage
import app.revanced.patcher.patch.annotation.Patch
import indus.org.patches.twitter.hook.json.JsonHookPatch
import indus.org.patches.twitter.hook.patch.BaseHookPatch

@Patch(
    name = "Hide promoted users",
    dependencies = [JsonHookPatch::class],
    compatiblePackages = [CompatiblePackage("com.twitter.android")]
)
@Suppress("unused")
object HideRecommendedUsersPatch : BaseHookPatch(
    "Lapp/revanced/integrations/twitter/patches/hook/patch/recommendation/RecommendedUsersHook;"
)