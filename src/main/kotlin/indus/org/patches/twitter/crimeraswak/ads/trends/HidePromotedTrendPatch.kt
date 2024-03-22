package indus.org.patches.twitter.crimeraswak.ads.trends

import app.revanced.patcher.data.BytecodeContext
import app.revanced.patcher.extensions.InstructionExtensions.addInstruction
import app.revanced.patcher.extensions.InstructionExtensions.addInstructionsWithLabels
import app.revanced.patcher.extensions.InstructionExtensions.getInstruction
import app.revanced.patcher.extensions.InstructionExtensions.getInstructions
import app.revanced.patcher.patch.BytecodePatch
import app.revanced.patcher.patch.PatchException
import app.revanced.patcher.patch.annotation.CompatiblePackage
import app.revanced.patcher.patch.annotation.Patch
import app.revanced.patcher.util.smali.ExternalLabel
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.iface.instruction.OneRegisterInstruction
import com.android.tools.smali.dexlib2.iface.instruction.TwoRegisterInstruction
import indus.org.patches.twitter.crimeraswak.ads.trends.fingerprints.HidePromotedTrendFingerprint
import indus.org.patches.twitter.crimeraswak.misc.settings.SettingsPatch
import indus.org.patches.twitter.crimeraswak.misc.settings.fingerprints.SettingsStatusLoadFingerprint

@Patch(
    name = "Hide Promoted Trends",
    dependencies = [SettingsPatch::class],
    compatiblePackages = [CompatiblePackage("com.twitter.android")],
    use = true
)
@Suppress("unused")
class HidePromotedTrendPatch : BytecodePatch(
    setOf(HidePromotedTrendFingerprint, SettingsStatusLoadFingerprint)
) {
    override fun execute(context: BytecodeContext) {
        val result = HidePromotedTrendFingerprint.result
            ?: throw PatchException("Fingerprint not found")

        val method = result.mutableMethod
        val instructions = method.getInstructions()

        val return_obj = instructions.last { it.opcode == Opcode.RETURN_OBJECT }
        val return_loc = return_obj.location.index
        val return_reg = method.getInstruction<OneRegisterInstruction>(return_loc).registerA
        val loc = return_loc-7
        val reg = method.getInstruction<TwoRegisterInstruction>(loc).registerA

        val HOOK_DESCRIPTOR =
            "invoke-static {v$reg}, ${SettingsPatch.PREF_DESCRIPTOR};->hidePromotedTrend(Ljava/lang/Object;)Z"


        method.addInstructionsWithLabels(return_loc,"""
            $HOOK_DESCRIPTOR
            move-result v$reg
            if-eqz v$reg, :cond_1212
            const v$return_reg, 0x0
        """.trimIndent(),
            ExternalLabel("cond_1212", return_obj)
        )


        SettingsStatusLoadFingerprint.result!!.mutableMethod.addInstruction(
            0,
            "${SettingsPatch.SSTS_DESCRIPTOR}->hidePromotedTrends()V"
        )
    }
}