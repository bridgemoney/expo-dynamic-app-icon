import { NativeModule, requireNativeModule } from 'expo-modules-core';

declare class ExpoDynamicAppIconModule extends NativeModule {
  setAppIconIOS(name: string): string | false;
  setAppIconAndroidAsync(name: string): Promise<string | false>;
  getAppIconIOS(): string;
  getAppIconAndroidAsync(): Promise<string>;
}

export default requireNativeModule<ExpoDynamicAppIconModule>(
  'ExpoDynamicAppIcon'
);
