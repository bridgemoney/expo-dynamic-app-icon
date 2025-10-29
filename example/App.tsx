import { Button, Text, View } from 'react-native';

import ExpoDynamicAppIconModule from 'expo-dynamic-app-icon';
import { useState } from 'react';

export default function App() {
  const [iconName, setIconName] = useState<string>();

  async function updateIcon(icon: string) {
    await ExpoDynamicAppIconModule.setAppIcon(icon);
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
        <Button
          title="get icon!"
          onPress={async () =>
            setIconName(await ExpoDynamicAppIconModule.getAppIcon())
          }
        />
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
