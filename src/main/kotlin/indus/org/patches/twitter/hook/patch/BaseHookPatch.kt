package indus.org.patches.twitter.hook.patch

import app.revanced.patcher.data.BytecodeContext
import app.revanced.patcher.patch.BytecodePatch
import indus.org.patches.twitter.hook.json.JsonHookPatch

abstract class BaseHookPatch(private val hookClassDescriptor: String) : BytecodePatch() {
    override fun execute(context: BytecodeContext) =
        JsonHookPatch.hooks.addHook(JsonHookPatch.Hook(context, hookClassDescriptor))
}