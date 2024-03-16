package indus.org.patches.twitter.adsandpromotions

import app.revanced.patcher.data.BytecodeContext
import app.revanced.patcher.extensions.InstructionExtensions.addInstruction
import app.revanced.patcher.extensions.InstructionExtensions.getInstructions
import app.revanced.patcher.patch.BytecodePatch
import app.revanced.patcher.patch.PatchException
import app.revanced.patcher.patch.annotation.CompatiblePackage
import app.revanced.patcher.patch.annotation.Patch
import com.android.tools.smali.dexlib2.Opcode
import indus.org.patches.twitter.adsandpromotions.fingerprints.GAdsFingerprint

@Patch(
    name = "Remove Google Ads",
    compatiblePackages = [CompatiblePackage("com.twitter.android")],
    use = true
)
@Suppress("unused")
object GoogleAdsPatch: BytecodePatch(
    setOf(GAdsFingerprint)
) {
    override fun execute(context: BytecodeContext) {
        val result = GAdsFingerprint.result
            ?: throw PatchException("Fingerprint not found")

        val method = result.mutableMethod
        val instructions = method.getInstructions()

        val bro = instructions.last { it.opcode == Opcode.INVOKE_VIRTUAL }.location.index

        method.addInstruction(bro, """
            const v0, false
            move-object v0, p5
        """.trimIndent())
    }
}