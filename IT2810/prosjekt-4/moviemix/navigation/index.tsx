import { createBottomTabNavigator } from "@react-navigation/bottom-tabs";
import { NavigationContainer, DarkTheme } from "@react-navigation/native";
import { createNativeStackNavigator } from "@react-navigation/native-stack";
import { RootStackParamList, RootTabParamList } from "../types";
import LinkingConfiguration from "./LinkingConfiguration";
import SearchResultScreen from "../screens/SearchResultScreen";
import FrontPageScreen from "../screens/FrontPageScreen";
import YourRatingsScreen from "../screens/YourRatingsScreen";
import ErrorScreen from "../screens/ErrorScreen";
import ReviewModal from "../screens/ReviewModal";
import { AntDesign } from "@expo/vector-icons";
import { Entypo } from "@expo/vector-icons";
import { limitVar } from "../state/limitVariable";

/**
 * Navigation component
 * Standard setup for react native navigation.
 * It came with the expo template.
 * Heavily inspired by the template, but with
 * several modifications to fit my app.
 */

const Stack = createNativeStackNavigator<RootStackParamList>();
const BottomTab = createBottomTabNavigator<RootTabParamList>();

export default function Navigation() {
  return (
    <NavigationContainer linking={LinkingConfiguration} theme={DarkTheme}>
      <Stack.Navigator>
        <Stack.Screen
          name="Root"
          component={BottomTabNavigator}
          options={{ headerShown: false }}
        />
        <Stack.Screen
          name="SearchResultScreen"
          component={SearchResultScreen}
          options={{ title: "Your Search Results" }}
        />
        <Stack.Screen
          name="ErrorScreen"
          component={ErrorScreen}
          options={{ title: "Oops!" }}
        />
        <Stack.Group screenOptions={{ presentation: "modal" }}>
          <Stack.Screen name="Review" component={ReviewModal} />
        </Stack.Group>
      </Stack.Navigator>
    </NavigationContainer>
  );
}

function BottomTabNavigator() {
  return (
    <BottomTab.Navigator
      initialRouteName="FrontPageScreen"
      screenOptions={{
        tabBarActiveTintColor: "white",
        tabBarInactiveTintColor: "gray",
      }}
    >
      <BottomTab.Screen
        name="FrontPageScreen"
        component={FrontPageScreen}
        listeners={{
          tabPress: (_) => {
            limitVar(6);
          },
        }}
        options={{
          title: "MovieMix",
          unmountOnBlur: true,
          tabBarIcon: ({ color }) => (
            <Entypo name="home" size={24} color={color} />
          ),
        }}
      />
      <BottomTab.Screen
        name="YourRatingsScreen"
        component={YourRatingsScreen}
        options={{
          title: "Your Ratings",
          unmountOnBlur: true,
          tabBarIcon: ({ color }) => (
            <AntDesign name="star" size={24} color={color} />
          ),
        }}
      />
    </BottomTab.Navigator>
  );
}
