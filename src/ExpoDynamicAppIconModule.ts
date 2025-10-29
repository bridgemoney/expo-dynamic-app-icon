import { NativeModule, requireNativeModule } from 'expo-modules-core';

declare class ExpoDynamicAppIconModule extends NativeModule {
  setAppIcon(name: string): Promise<string | false>;
  getAppIcon(): Promise<string>;
}

export default requireNativeModule<ExpoDynamicAppIconModule>('ExpoDynamicAppIcon');
