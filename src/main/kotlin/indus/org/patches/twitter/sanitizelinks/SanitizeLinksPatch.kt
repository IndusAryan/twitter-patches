package indus.org.patches.twitter.sanitizelinks

import app.revanced.patcher.data.BytecodeContext
import app.revanced.patcher.extensions.InstructionExtensions.addInstruction
import app.revanced.patcher.extensions.InstructionExtensions.addInstructions
import app.revanced.patcher.extensions.InstructionExtensions.getInstructions
import app.revanced.patcher.patch.BytecodePatch
import app.revanced.patcher.patch.PatchException
import app.revanced.patcher.patch.annotation.CompatiblePackage
import app.revanced.patcher.patch.annotation.Patch
import com.android.tools.smali.dexlib2.Opcode
import indus.org.patches.twitter.sanitizelinks.fingerprints.DexFingerprint

// parts taken from https://github.com/crimera/piko
@Patch(
    name = "Sanitize URLs",
    description = "Remove t.co short links and trackers from shared URL.",
    compatiblePackages = [CompatiblePackage("com.twitter.android")],
    use = true
)
@Suppress("unused")
object SanitizeLinksPatch : BytecodePatch(
    setOf(DexFingerprint.JsonObjectMapperFingerprint)
) {
    private const val METHOD_REFERENCE =
        "Lapp/revanced/integrations/twitter/patches/links/SanitizeLinksPatch;->" +
                "unshort(Ljava/lang/Object;)V"

    override fun execute(context: BytecodeContext) {

        val result = DexFingerprint.JsonObjectMapperFingerprint.result
            ?: throw Exception("Fingerprint not found")

        val method = result.mutableMethod
        val instructions = method.getInstructions()

        // somehow targetIndex2 is above :cond_2, inject again before branching
        var targetIndex = -1
        val targetIndex2 = instructions.size - 1
        for (i in 0..targetIndex2) {
            if (instructions[i].opcode == Opcode.IF_EQ) {
                targetIndex = i;
            }
        }

        val inject = """
                invoke-static { v0 }, $METHOD_REFERENCE
            """.trimIndent()

        result.mutableMethod.addInstructions(targetIndex, inject)
        result.mutableMethod.addInstructions(targetIndex2, inject)

    }

    private fun removeTrackingLinks() {
        // removes telemetry ?si=
        val result = DexFingerprint.AddSessionTokenFingerprint.result
            ?: throw PatchException("Fingerprint not found")

        result.mutableMethod.addInstruction(0, "return-object p0")
    }
}