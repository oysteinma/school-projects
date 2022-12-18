import { getAuth, signOut } from "firebase/auth";
import { app } from "./Firebase.js";
export const MySignOut = () => {
  const auth = getAuth(app);
  signOut(auth)
    .then(() => {
      console.log("Signed out, seeeeya<3");
    })
    .catch((error) => {
      console.log(error);
    });
};
