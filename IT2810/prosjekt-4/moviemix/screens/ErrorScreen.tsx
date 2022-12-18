import { StyleSheet } from "react-native";
import { Card, Button } from "react-native-paper";
import { View } from "react-native";
import { RootStackScreenProps } from "../types";

/**
 *
 * @param navigation
 * @returns a screen that is shown when an error occurs
 */
export default function ErrorScreen({
  navigation,
}: RootStackScreenProps<"ErrorScreen">) {
  return (
    <View style={styles.container}>
      <Card style={styles.card}>
        <Card.Title titleStyle={styles.text} title="Error screen!" />
        <Card.Actions style={styles.actions}>
          <Button
            accessibilityLabel="go back to homescreen"
            color={"black"}
            style={styles.button}
            onPress={() => navigation.replace("Root")}
          >
            Go Back!
          </Button>
        </Card.Actions>
      </Card>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: "center",
    justifyContent: "center",
  },
  actions: {
    alignSelf: "center",
  },
  button: {
    backgroundColor: "#82FFC3",
    marginBottom: 10,
  },
  text: {
    color: "white",
    textAlign: "center",
  },
  card: {
    width: 300,
    backgroundColor: "#252E40",
    elevation: 10,
  },
});
