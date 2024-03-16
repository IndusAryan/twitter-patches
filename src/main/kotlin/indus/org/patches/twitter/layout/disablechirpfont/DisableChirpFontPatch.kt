package indus.org.patches.twitter.layout.disablechirpfont

import app.revanced.patcher.data.BytecodeContext
import app.revanced.patcher.extensions.InstructionExtensions.addInstructions
import app.revanced.patcher.patch.BytecodePatch
import app.revanced.patcher.patch.annotation.CompatiblePackage
import app.revanced.patcher.patch.annotation.Patch

// credits to https://github.com/crimera/piko
@Patch(
    name = "Disable chirp font",
    use = false,
    compatiblePackages = [CompatiblePackage("com.twitter.android")]
)
@Suppress("unused")
object DisableChirpFontPatch: BytecodePatch(
    setOf(ChirpFontFingerprint)
) {
    override fun execute(context: BytecodeContext) {
        ChirpFontFingerprint.result?.mutableMethod?.addInstructions(
            0,
            """
                const v0, false
                return v0
            """
        )
    }
}