/* eslint-disable no-useless-catch */
import {
  getStorage,
  ref,
  uploadBytes,
  getDownloadURL,
  deleteObject,
} from "firebase/storage";
// eslint-disable-next-line no-unused-vars
import { app } from "./Firebase";

export const uploadFromBlobAsync = async ({
  url,
  name,
  oldImageURL,
  callback,
}) => {
  if (!url || !name) return null;
  console.log("uploadFromBlobAsync", url, name);

  // If the image hasn't changed, provide callback with the current url and return
  if (url.includes("firebasestorage.googleapis.com")) {
    callback(url);
    return true;
  } else if (oldImageURL) {
    const storage = getStorage();
    deleteObject(ref(storage, oldImageURL))
      .then(() => {})
      .catch((error) => {
        console.log(
          "Could not delete image (likely it has already been deleted manually\nerror: ",
          error
        );
      });
  }

  try {
    const blob = await fetch(url).then((r) => r.blob());
    const storage = getStorage();
    const storageRef = ref(storage, `Recipe Images/${name}`);

    // 'file' comes from the Blob or File API
    uploadBytes(storageRef, blob).then((snapshot) => {
      getDownloadURL(snapshot.ref).then((url) => {
        console.log("uploaded image at " + url);
        callback(url);
        return true;
      });
    });
  } catch (error) {
    throw error;
  }
};
