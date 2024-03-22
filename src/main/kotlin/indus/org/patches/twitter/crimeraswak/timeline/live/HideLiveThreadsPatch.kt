package indus.org.patches.twitter.crimeraswak.timeline.live

import app.revanced.patcher.data.BytecodeContext
import app.revanced.patcher.extensions.InstructionExtensions.addInstruction
import app.revanced.patcher.extensions.InstructionExtensions.addInstructions
import app.revanced.patcher.extensions.InstructionExtensions.getInstruction
import app.revanced.patcher.extensions.InstructionExtensions.getInstructions
import app.revanced.patcher.patch.BytecodePatch
import app.revanced.patcher.patch.PatchException
import app.revanced.patcher.patch.annotation.CompatiblePackage
import app.revanced.patcher.patch.annotation.Patch
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.iface.instruction.OneRegisterInstruction
import indus.org.patches.twitter.crimeraswak.misc.settings.SettingsPatch
import indus.org.patches.twitter.crimeraswak.misc.settings.fingerprints.SettingsStatusLoadFingerprint
import indus.org.patches.twitter.crimeraswak.timeline.live.fingerprints.HideLiveThreadsFingerprint

@Patch(
    name = "Hide Live Threads",
    dependencies = [SettingsPatch::class],
    compatiblePackages = [CompatiblePackage("com.twitter.android")],
    use = false
)
@Suppress("unused")
object HideLiveThreadsPatch :  BytecodePatch(
    setOf(HideLiveThreadsFingerprint)
){
    override fun execute(context: BytecodeContext) {
        val result = HideLiveThreadsFingerprint.result
            ?: throw PatchException("Fingerprint not found")

        val method = result.mutableMethod
        val instructions = method.getInstructions()

        val loc = instructions.first{it.opcode == Opcode.IGET_OBJECT}.location.index
        val reg = method.getInstruction<OneRegisterInstruction>(loc).registerA

        val HIDE_LIVE_DESCRIPTOR =
            "invoke-static {v$reg}, ${SettingsPatch.PREF_DESCRIPTOR};->liveThread(Ljava/util/ArrayList;)Ljava/util/ArrayList;"

        method.addInstructions(loc+1,"""
            $HIDE_LIVE_DESCRIPTOR
            move-result-object v$reg
        """.trimIndent())

        SettingsStatusLoadFingerprint.result!!.mutableMethod.addInstruction(
            0,
            "${SettingsPatch.SSTS_DESCRIPTOR}->hideLiveThreads()V"
        )
    }
}