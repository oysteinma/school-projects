import { useMutation } from "@apollo/client";
import { CREATE_USER } from "../queries/userQueries";
import AsyncStorage from "@react-native-async-storage/async-storage";
import { userIdVar } from "../state/userVariable";

/**
 * Creates a new user in the Database if the user does not exist.
 * Heavily inspired by https://react-native-async-storage.github.io/async-storage/docs/usage
 */
export const fetchUserInfo = () => {
  const [createUsers] = useMutation(CREATE_USER);

  const getUser = async () => {
    try {
      const value = await AsyncStorage.getItem("@storage_Key");
      if (value !== null) {
        console.log("UserID", value);
        userIdVar(value);
      } else {
        storeData();
      }
    } catch (e) {
      console.log("Somthing went wrong while reading storage!", e);
    }
  };

  const storeData = async () => {
    try {
      await createUsers({ variables: { input: [{}] } }).then((result) => {
        AsyncStorage.setItem(
          "@storage_Key",
          result.data.createUsers.users[0].id
        );
      });
    } catch (e) {
      console.log(
        "Something went wrong while creating user or writing to storage!",
        e
      );
    }
  };

  getUser();
};
