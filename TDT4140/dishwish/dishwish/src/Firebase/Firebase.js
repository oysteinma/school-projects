import { initializeApp } from "firebase/app";
//import { getAnalytics } from "firebase/analytics";
//import { getFirestore } from "firebase/firestore"

// Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries

// Web app's Firebase configuration
// For Firebase JS SDK v7.20.0 and later, measurementId is optional
const firebaseConfig = {
  apiKey: "AIzaSyCgWVtZDkUfl6-jZ5Gx0WDtczXcrdkYthc",
  authDomain: "dishwish-cf6bf.firebaseapp.com",
  projectId: "dishwish-cf6bf",
  storageBucket: "dishwish-cf6bf.appspot.com",
  messagingSenderId: "520324430445",
  appId: "1:520324430445:web:32cac76b9c52aa78401d64",
  measurementId: "G-504EQLW8H1",
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);
export { app };
//initialize Auth and DB
//const db = getFirestore(firebaseConfig);
//const analytics = getAnalytics(app); //Analytics not needed for now
