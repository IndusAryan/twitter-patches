package indus.org.patches.twitter.crimeraswak.misc.disablechirpfont

import app.revanced.patcher.data.BytecodeContext
import app.revanced.patcher.extensions.InstructionExtensions.addInstruction
import app.revanced.patcher.extensions.InstructionExtensions.addInstructions
import app.revanced.patcher.patch.BytecodePatch
import app.revanced.patcher.patch.annotation.CompatiblePackage
import app.revanced.patcher.patch.annotation.Patch
import indus.org.patches.twitter.crimeraswak.misc.disablechirpfont.fingerprints.ChirpFontFingerprint
import indus.org.patches.twitter.crimeraswak.misc.settings.SettingsPatch
import indus.org.patches.twitter.crimeraswak.misc.settings.fingerprints.SettingsStatusLoadFingerprint

@Patch(
    name = "Disable chirp font",
    use = false,
    dependencies = [SettingsPatch::class],
    compatiblePackages = [CompatiblePackage("com.twitter.android")]
)
@Suppress("unused")
object DisableChirpFontPatch: BytecodePatch(
    setOf(ChirpFontFingerprint)
) {
    private const val CHIRP_FONT_DESCRIPTOR =
        "invoke-static {}, ${SettingsPatch.PREF_DESCRIPTOR};->isChirpFontEnabled()Z"

    override fun execute(context: BytecodeContext) {
        ChirpFontFingerprint.result!!.mutableMethod.addInstructions(
            0,
            """
                $CHIRP_FONT_DESCRIPTOR
                move-result v0
                return v0
            """
        )

        SettingsStatusLoadFingerprint.result!!.mutableMethod.addInstruction(
            0,
            "${SettingsPatch.SSTS_DESCRIPTOR}->enableFont()V"
        )
    }
}