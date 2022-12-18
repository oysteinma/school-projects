import {
  doc,
  getDoc,
  getDocs,
  collection,
  getFirestore,
  addDoc,
  deleteDoc,
  updateDoc,
  query,
  where,
  documentId,
} from "firebase/firestore";
import { getAuth } from "firebase/auth";
import { getStorage, ref, deleteObject } from "firebase/storage";
// eslint-disable-next-line no-unused-vars
import { app } from "./Firebase";

export async function GetRecipe(id) {
  if (id == null) {
    return undefined;
  }
  const db = getFirestore();
  const docSnap = await getDoc(doc(db, "Recipe", id));

  // Check if user is logged in
  const user = getAuth(app).currentUser;
  if (user == null) {
    return {
      id: docSnap.id,
      isBookmarked: false,
      ...docSnap.data(),
    };
  }

  let bookmarkQuery = query(
    collection(db, "Bookmarked"),
    where("userId", "==", user.uid)
  );
  bookmarkQuery = query(bookmarkQuery, where("recipeId", "==", id));
  const isBookmarked = !(await getDocs(bookmarkQuery)).empty;
  return {
    id: docSnap.id,
    isBookmarked: isBookmarked,
    ...docSnap.data(),
  };
}

export async function GetAllRecipes() {
  const db = getFirestore();
  // Check if user is logged in
  const user = getAuth(app).currentUser;
  if (user == null) {
    const querySnapshot = await getDocs(collection(db, "Recipe"));
    const recipes = querySnapshot.docs.map((doc) => {
      return {
        id: doc.id,
        isBookmarked: false,
        ...doc.data(),
      };
    });
    return recipes;
  }

  const bookmarkedRecipes = await GetRecipesOnBookmark(true);
  const notBookmarkedRecipes = await GetRecipesOnBookmark(false);

  return [...notBookmarkedRecipes, ...bookmarkedRecipes];
}

export async function GetRecipesOnBookmark(isBookmarked) {
  let condition = isBookmarked ? "in" : "not-in";
  const db = getFirestore();

  // First, getting the logged in user
  const user = getAuth(app).currentUser;
  if (user == null) {
    return null;
  }

  // Then, getting all entries in the "Bookmarked"-database which contain this user's id
  const bookmarkedCollection = collection(db, "Bookmarked");
  const bookmarkQuery = query(
    bookmarkedCollection,
    where("userId", "==", user.uid)
  );
  const bookmarkSnapshot = await getDocs(bookmarkQuery);

  // From these entries, make a list of the corresponding recipe ids
  let recipesToFetch = bookmarkSnapshot.docs.map((doc) => doc.get("recipeId"));
  if (recipesToFetch.length == 0) {
    recipesToFetch = ["null"]; // needs at least one value for where("in") to work
  }

  console.log("Fetching recipes:", recipesToFetch);
  // Finally, fetching this list of recipes from the "Recipe"-collection
  const recipeCollection = collection(db, "Recipe");
  const recipeQuery = query(
    recipeCollection,
    where(documentId(), condition, recipesToFetch)
  );

  const recipeSnapshot = await getDocs(recipeQuery);
  const recipes = recipeSnapshot.docs.map((doc) => {
    return {
      id: doc.id,
      isBookmarked: isBookmarked,
      ...doc.data(),
    };
  });
  console.log(recipes);
  return recipes;
}

export async function AddBookmark(recipeId) {
  // First, getting the logged in user
  const user = getAuth(app).currentUser;
  if (user == null) {
    return false;
  }

  const db = getFirestore();
  await addDoc(collection(db, "Bookmarked"), {
    recipeId: recipeId,
    userId: user.uid,
  });
  return true;
}

export async function RemoveBookmark(recipeId) {
  // First, getting the logged in user
  const user = getAuth(app).currentUser;
  if (user == null) {
    return false;
  }

  const db = getFirestore();
  // Get bookmark entires where userId and recipeId both match
  const bookmarkedCollection = collection(db, "Bookmarked");
  let bookmarkQuery = query(
    bookmarkedCollection,
    where("recipeId", "==", recipeId)
  );
  bookmarkQuery = query(bookmarkQuery, where("userId", "==", user.uid));

  // Get a snapshot and delete all matching entries
  const bookmarkDocs = await getDocs(bookmarkQuery);
  bookmarkDocs.docs.forEach(async (doc) => {
    await deleteDoc(doc.ref);
  });
  return true;
}

export async function UploadRecipe(recipe, callback) {
  const db = getFirestore();
  const doc = await addDoc(collection(db, "Recipe"), {
    ...recipe,
  });
  callback(doc.id);
}

export async function UpdateRecipe(recipe, id, callback) {
  const db = getFirestore();
  const docRef = doc(db, "Recipe", id);
  console.log("updatedoc started");
  await updateDoc(docRef, {
    ...recipe,
  });
  callback();
}

export async function DeleteRecipe(recipe) {
  const db = getFirestore();
  const recipeDoc = doc(db, "Recipe", recipe.id);
  await deleteDoc(recipeDoc);
  const storage = getStorage();
  deleteObject(ref(storage, recipe.imageURL))
    .then(() => {})
    .catch((error) => {
      console.log(
        "Could not delete image (likely it has already been deleted manually\nerror: ",
        error
      );
    });
}
