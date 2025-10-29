package expo.modules.dynamicappicon

import android.content.Context
import android.content.pm.PackageManager;
import android.content.ComponentName;
import android.util.Log

import expo.modules.kotlin.modules.Module
import expo.modules.kotlin.modules.ModuleDefinition

class ExpoDynamicAppIconModule : Module() {
  override fun definition() = ModuleDefinition {
    Name("ExpoDynamicAppIcon")

    AsyncFunction("setAppIcon") { name: String ->
      try {
        val newIcon:String = context.packageName + ".MainActivity" + name
        val currentIcon:String = if(!SharedObject.icon.isEmpty()) SharedObject.icon else context.packageName + ".MainActivity"

        SharedObject.packageName = context.packageName
        SharedObject.pm = pm

        cleanUp()

        pm.setComponentEnabledSetting(
          ComponentName(context.packageName, currentIcon),
          PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
          PackageManager.DONT_KILL_APP
        )

        pm.setComponentEnabledSetting(
          ComponentName(context.packageName, newIcon),
          PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
          PackageManager.DONT_KILL_APP
        )

        SharedObject.classesToKill.add(currentIcon)
        SharedObject.icon = newIcon

        return@AsyncFunction name
      } catch (e: Exception) {
        Log.e("ExpoDynamicAppIcon", e.message, e)
        return@AsyncFunction false
      }
    }

    AsyncFunction("getAppIcon") {
      try {
        val componentClass:String = currentActivity.getComponentName().getClassName()
        val currentIcon:String = if(!SharedObject.icon.isEmpty()) SharedObject.icon else componentClass
        val currentIconName:String = currentIcon.split("MainActivity")[1]

        if (currentIconName.isEmpty()) {
          return@AsyncFunction "DEFAULT"
        }

        return@AsyncFunction currentIconName
      } catch (e: Exception) {
        Log.e("ExpoDynamicAppIcon", e.message, e)
        return@AsyncFunction "DEFAULT"
      }
    }
  }

  private val context: Context
    get() = requireNotNull(appContext.reactContext) { "React Application Context is null" }

  private val currentActivity
    get() = requireNotNull(appContext.activityProvider?.currentActivity)

  private val pm
    get() = requireNotNull(currentActivity.packageManager)

  private fun cleanUp() {

  }
}
