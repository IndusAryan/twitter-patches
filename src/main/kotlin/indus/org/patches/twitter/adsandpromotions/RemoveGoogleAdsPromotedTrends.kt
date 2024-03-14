package indus.org.patches.twitter.adsandpromotions

import app.revanced.patcher.data.BytecodeContext
import app.revanced.patcher.extensions.InstructionExtensions.addInstruction
import app.revanced.patcher.extensions.InstructionExtensions.addInstructionsWithLabels
import app.revanced.patcher.extensions.InstructionExtensions.getInstruction
import app.revanced.patcher.extensions.InstructionExtensions.getInstructions
import app.revanced.patcher.fingerprint.MethodFingerprint
import app.revanced.patcher.patch.BytecodePatch
import app.revanced.patcher.patch.PatchException
import app.revanced.patcher.patch.annotation.CompatiblePackage
import app.revanced.patcher.patch.annotation.Patch
import app.revanced.patcher.util.smali.ExternalLabel
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.iface.instruction.OneRegisterInstruction
import com.android.tools.smali.dexlib2.iface.instruction.TwoRegisterInstruction
import indus.org.patches.twitter.adsandpromotions.fingerprints.HidePromotedTrendFingerprint

object GoogleAdsPatchFingerprint: MethodFingerprint(
    returnType = "V",
    strings = listOf(
        "ssp_ads_google_dsp_client_context_enabled"
    )
)

@Patch(
    name = "Remove Google Ads and Hide Promoted Trends",
    compatiblePackages = [CompatiblePackage("com.twitter.android")],
    use = true
)
@Suppress("unused")
class RemoveGoogleAdsPromotedTrends : BytecodePatch(
    setOf(HidePromotedTrendFingerprint)
) {
    override fun execute(context: BytecodeContext) {
        val result = HidePromotedTrendFingerprint.result
            ?: throw PatchException("Fingerprint not found")

        val method = result.mutableMethod
        val instructions = method.getInstructions()

        val return_obj = instructions.last { it.opcode == Opcode.RETURN_OBJECT }
        val return_loc = return_obj.location.index
        val return_reg = method.getInstruction<OneRegisterInstruction>(return_loc).registerA
        val loc = return_loc - 7
        val reg = method.getInstruction<TwoRegisterInstruction>(loc).registerA

        method.addInstructionsWithLabels(
            return_loc, """
            if-eqz v$reg, :cond_1212
            const v$return_reg, 0x0
        """.trimIndent(),
            ExternalLabel("cond_1212", return_obj)
        )

        removeGoogleAds()
    }

    private fun removeGoogleAds() {
        val result = GoogleAdsPatchFingerprint.result
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
