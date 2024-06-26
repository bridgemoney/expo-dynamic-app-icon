package expo.modules.dynamicappicon

import android.content.pm.PackageManager;
import android.content.ComponentName;
import android.app.Activity
import android.os.Bundle
import expo.modules.core.interfaces.ReactActivityLifecycleListener

import java.util.ArrayList;


object SharedObject {
  var packageName = ""
  var classesToKill = ArrayList<String>()
  var icon = ""
  var pm:PackageManager? = null
}


class ExpoDynamicAppIconReactActivityLifecycleListener : ReactActivityLifecycleListener {

  fun cleanUp() {
    SharedObject.classesToKill.forEach{ cls ->
      if(SharedObject.pm != null) {

        if (cls != SharedObject.icon) {
          SharedObject.pm?.setComponentEnabledSetting(
            ComponentName(SharedObject.packageName, cls),
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
            PackageManager.DONT_KILL_APP
          )
        }
      }
    }

    SharedObject.classesToKill.clear()
  }

  override fun onPause(activity: Activity) {
    cleanUp()
  }

  override fun onDestroy(activity: Activity) {
    cleanUp()
  }
}
