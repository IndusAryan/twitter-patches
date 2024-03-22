package indus.org.patches.twitter.crimeraswak.misc.recommendedusers

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
import indus.org.patches.twitter.crimeraswak.misc.recommendedusers.fingerprints.HideRecommendedUsersFingerprint


@Patch(
    name = "Hide Recommended Users",
    description = "Hide recommended users that pops up when you follow someone",
    compatiblePackages = [CompatiblePackage("com.twitter.android")]
)
@Suppress("unused")
object HideRecommendedUsers: BytecodePatch(
    setOf(HideRecommendedUsersFingerprint)
) {
    override fun execute(context: BytecodeContext) {
        val result = HideRecommendedUsersFingerprint.result
            ?: throw PatchException("Fingerprint not found")

        val method = result.mutableMethod
        val instructions = method.getInstructions()

        val check = instructions.last { it.opcode == Opcode.IGET_OBJECT }.location.index
        val reg = method.getInstruction<OneRegisterInstruction>(check).registerA

        val HIDE_RECOMMENDED_USERS_DESCRIPTOR =
            "invoke-static {v$reg}, ${SettingsPatch.PREF_DESCRIPTOR};->hideRecommendedUsers(Ljava/util/ArrayList;)Ljava/util/ArrayList;"

        method.addInstructions(check+1, """
            $HIDE_RECOMMENDED_USERS_DESCRIPTOR
            move-result-object v$reg
        """.trimIndent())

        SettingsStatusLoadFingerprint.result!!.mutableMethod.addInstruction(
            0,
            "${SettingsPatch.SSTS_DESCRIPTOR}->hideRecommendedUsers()V"
        )
    }
}