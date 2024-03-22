package indus.org.patches.twitter.crimeraswak.timeline.foryou

import app.revanced.patcher.data.BytecodeContext
import app.revanced.patcher.extensions.InstructionExtensions.addInstruction
import app.revanced.patcher.extensions.InstructionExtensions.addInstructionsWithLabels
import app.revanced.patcher.extensions.InstructionExtensions.getInstruction
import app.revanced.patcher.extensions.InstructionExtensions.getInstructions
import app.revanced.patcher.extensions.InstructionExtensions.removeInstruction
import app.revanced.patcher.patch.BytecodePatch
import app.revanced.patcher.patch.PatchException
import app.revanced.patcher.patch.annotation.CompatiblePackage
import app.revanced.patcher.patch.annotation.Patch
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.iface.instruction.OneRegisterInstruction
import indus.org.patches.twitter.crimeraswak.timeline.foryou.fingerprints.HideForYouFingerprint
import indus.org.patches.twitter.crimeraswak.misc.settings.SettingsPatch
import indus.org.patches.twitter.crimeraswak.misc.settings.fingerprints.SettingsStatusLoadFingerprint

@Patch(
    name = "Hide For You",
    description = "Hides For You tab from timeline",
    dependencies = [SettingsPatch::class],
    compatiblePackages = [CompatiblePackage("com.twitter.android")],
    use = false
)
@Suppress("unused")
object HideForYouPatch : BytecodePatch(
    setOf(HideForYouFingerprint, SettingsStatusLoadFingerprint)
){

    override fun execute(context: BytecodeContext) {
        val result = HideForYouFingerprint.result
            ?: throw PatchException("Fingerprint not found")

        val method = result.mutableMethod

        val instructions = method.getInstructions()

        val check = instructions.first { it.opcode == Opcode.CONST_16 }.location.index
        val reg = method.getInstruction<OneRegisterInstruction>(check).registerA
        method.removeInstruction(check)
        method.addInstructionsWithLabels(check,"""
           invoke-static {}, ${SettingsPatch.PREF_DESCRIPTOR};->hideForYou()I
        
           move-result v$reg
        """.trimIndent())

        SettingsStatusLoadFingerprint.result!!.mutableMethod.addInstruction(
            0,
            "${SettingsPatch.SSTS_DESCRIPTOR}->hideForYou()V"
        )
    }
}
