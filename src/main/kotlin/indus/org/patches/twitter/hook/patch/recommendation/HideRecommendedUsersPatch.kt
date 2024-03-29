package indus.org.patches.twitter.hook.patch.recommendation

import app.revanced.patcher.patch.annotation.CompatiblePackage
import app.revanced.patcher.patch.annotation.Patch
import indus.org.patches.twitter.hook.json.JsonHookPatch
import indus.org.patches.twitter.hook.patch.BaseHookPatch

@Patch(
    name = "Hide promoted users",
    description = "DON'T USE WHEN PATCHING ALONG WITH CRIMERA PATCHES AND INTEGRATIONS (NOT NEEDED ANYWAYS THEN).",
    dependencies = [JsonHookPatch::class],
    compatiblePackages = [CompatiblePackage("com.twitter.android")],
    use = false
)
@Suppress("unused")
object HideRecommendedUsersPatch : BaseHookPatch(
    "Lapp/revanced/integrations/twitter/patches/hook/patch/recommendation/RecommendedUsersHook;")