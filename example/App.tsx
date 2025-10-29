import { Button, Platform, Text, View } from 'react-native';

import ExpoDynamicAppIconModule from 'expo-dynamic-app-icon';
import { useState } from 'react';

export default function App() {
  const [iconName, setIconName] = useState<string>();

  function updateIcon(icon: string) {
    if (Platform.OS === 'ios') {
      ExpoDynamicAppIconModule.setAppIconIOS(icon);
    } else {
      ExpoDynamicAppIconModule.setAppIconAndroidAsync(icon);
    }
  }

  function updateIconName() {
    if (Platform.OS === 'ios') {
      setIconName(ExpoDynamicAppIconModule.getAppIconIOS());
    } else {
      ExpoDynamicAppIconModule.getAppIconAndroidAsync().then((icon: string) => {
        setIconName(icon);
      });
    }
  }

  return (
    <View
      style={{
        flex: 1,
        backgroundColor: 'white',
        alignItems: 'center',
        justifyContent: 'center',
      }}
    >
      <View style={{ marginBottom: 16 }}>
        <Button title="get icon!" onPress={updateIconName} />
        <Text>{iconName || 'Press Button!'}</Text>
      </View>

      <View style={{ marginBottom: 16 }}>
        <Button
          title="change red icon"
          onPress={updateIcon.bind(null, 'red')}
        />
      </View>

      <Button
        title="change gray icon"
        onPress={updateIcon.bind(null, 'gray')}
      />
    </View>
  );
}
