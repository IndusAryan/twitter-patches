package indus.org.patches.twitter.hook.patch.ads

import app.revanced.patcher.patch.annotation.CompatiblePackage
import app.revanced.patcher.patch.annotation.Patch
import indus.org.patches.twitter.hook.json.JsonHookPatch
import indus.org.patches.twitter.hook.patch.BaseHookPatch

@Patch(
    name = "Hide ads",
    description = "DON'T USE WHEN PATCHING ALONG WITH CRIMERA PATCHES AND INTEGRATIONS (NOT NEEDED ANYWAYS THEN).",
    dependencies = [JsonHookPatch::class],
    compatiblePackages = [CompatiblePackage("com.twitter.android")],
    use = false
)
@Suppress("unused")
object HideAdsHookPatch : BaseHookPatch("Lapp/revanced/integrations/twitter/patches/hook/patch/ads/AdsHook;")