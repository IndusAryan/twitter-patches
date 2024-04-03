package indus.org.patches.twitter.crimeraswak.premium.customAppIconAndNavBtns

import app.revanced.patcher.data.BytecodeContext
import app.revanced.patcher.extensions.InstructionExtensions.addInstruction
import app.revanced.patcher.extensions.InstructionExtensions.getInstructions
import app.revanced.patcher.extensions.InstructionExtensions.removeInstruction
import app.revanced.patcher.patch.BytecodePatch
import app.revanced.patcher.patch.PatchException
import indus.org.patches.twitter.crimeraswak.premium.customAppIconAndNavBtns.fingerprints.CustomAppIconAndNavBtnsFingerprint
import app.revanced.patcher.patch.annotation.CompatiblePackage
import app.revanced.patcher.patch.annotation.Patch
import com.android.tools.smali.dexlib2.Opcode
import indus.org.patches.twitter.crimeraswak.misc.settings.SettingsPatch
import indus.org.patches.twitter.crimeraswak.misc.settings.fingerprints.SettingsStatusLoadFingerprint

@Patch(
    name = "Enable custom app icon and nav icon settings",
    compatiblePackages = [CompatiblePackage("com.twitter.android")],
    dependencies = [SettingsPatch::class],
    requiresIntegrations = true
)
object CustomAppIconAndNavBtnsPatch:BytecodePatch(
    setOf(CustomAppIconAndNavBtnsFingerprint)
) {
    override fun execute(context: BytecodeContext) {
        val result = CustomAppIconAndNavBtnsFingerprint.result
            ?:throw PatchException("CustomAppIconAndNavBtnsFingerprint not found")

        //usually the last method
        val methods = result.mutableClass.methods.last()
        val loc = methods.getInstructions().last { it.opcode == Opcode.CONST }.location.index

        //removes toast condition
        methods.removeInstruction(loc)
        methods.removeInstruction(loc-1)

        SettingsStatusLoadFingerprint.result!!.mutableMethod.addInstruction(
            0,
            "${SettingsPatch.SSTS_DESCRIPTOR}->enableAppIconNNavIcon()V"
        )
        //end
    }
}