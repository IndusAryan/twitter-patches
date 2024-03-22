package indus.org.patches.twitter.crimeraswak.ads.timelineEntryHook

import app.revanced.patcher.data.BytecodeContext
import app.revanced.patcher.extensions.InstructionExtensions.addInstruction
import app.revanced.patcher.patch.BytecodePatch
import app.revanced.patcher.patch.annotation.CompatiblePackage
import app.revanced.patcher.patch.annotation.Patch
import indus.org.patches.twitter.crimeraswak.misc.settings.SettingsPatch
import indus.org.patches.twitter.crimeraswak.misc.settings.fingerprints.SettingsStatusLoadFingerprint

@Patch(
    name = "Remove \"Who to follow\" Banner",
    dependencies = [SettingsPatch::class, TimelineEntryHookPatch::class],
    compatiblePackages = [CompatiblePackage("com.twitter.android")],
    use = true
)
object HideWTF :BytecodePatch(
    setOf()
){
    override fun execute(context: BytecodeContext) {
        SettingsStatusLoadFingerprint.result!!.mutableMethod.addInstruction(
            0,
            "${SettingsPatch.SSTS_DESCRIPTOR}->hideWhoToFollow()V"
        )

    }
}