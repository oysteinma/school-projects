// import { useRouter } from "next/router";
// import CreateRecipe from ".";

// const Post = () => {
//   const router = useRouter();
//   const pid = router.query;
//   console.log("Here");
//   console.log(pid);
//   console.log(pid.pid);

//   return <CreateRecipe recipe={pid} />;
// };

// export default Post;
import { useRouter } from "next/router";
import { useEffect, useState } from "react";
import CreateRecipe from ".";
import { GetRecipe } from "../../Firebase/DataStreams";

const Post = () => {
  const router = useRouter();
  const { pid } = router.query;

  const [recipe, setRecipe] = useState({});

  useEffect(() => {
    GetRecipe(pid).then((recipe) => {
      setRecipe(recipe);
    });
  }, [pid]);

  return <CreateRecipe recipe={recipe} />;
};

export default Post;
