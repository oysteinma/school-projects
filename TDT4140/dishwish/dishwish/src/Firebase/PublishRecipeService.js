import { getFirestore, doc, setDoc } from "firebase/firestore";
import { useState } from "react";
//import { app } from "./Firebase.js";

function PublishRecipeService() {
  const [state, setState] = useState("");
  const handleSubmit = async () => {
    const title = state.details.title;
    const desc = state.details.desc;

    const db = getFirestore();
    //const colRef = collection(db, "Recipee");
    const docRef = doc(db, "Recipe", title);
    await setDoc(docRef, {
      title: title,
      desc: desc,
    });
    setState("");
  };

  function onTitleChange(event) {
    const value = event.target.value;

    setState(function (state) {
      return {
        details: Object.assign({}, state.details, {
          title: value,
        }),
      };
    });
  }

  function onDescChange(event) {
    const value = event.target.value;

    setState(function (state) {
      return {
        details: Object.assign({}, state.details, {
          desc: value,
        }),
      };
    });
  }

  return (
    <div>
      <input
        type="text"
        name=""
        onChange={onTitleChange}
        id="recipetitle"
        placeholder="Recipe title"
      ></input>
      <input
        type="text"
        name=""
        onChange={onDescChange}
        id="recipedesc"
        placeholder="Recipe description"
      ></input>
      <button onClick={handleSubmit}>Send</button>
    </div>
  );
}

export default PublishRecipeService;

/*
//import React from 'react';
import {getAuth, GoogleAuthProvider, signInWithPopup} from "firebase/auth";
import {app} from "./Firebase.js"

function MyLogin(){
    const auth = getAuth(app);
    const provider = new  GoogleAuthProvider();
    const signInWithGoogle = () => {
    signInWithPopup(auth, provider)
    .catch((error)=> console.log(error,"!!d!!!im here!!!!"))
    }
    return <div><button type="button" onClick={ signInWithGoogle } >Sign in with Google</button></div>
}
export default MyLogin;
*/
