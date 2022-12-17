import { useRouter } from "next/router";
import { useEffect, useState } from "react";
import ShowRecipe from ".";
import { GetRecipe } from "../../Firebase/DataStreams";

const Post = () => {
  const router = useRouter();
  const { pid } = router.query;

  const [recipe, setRecipe] = useState({});

  useEffect(() => {
    GetRecipe(pid).then((recipe) => {
      if (recipe == undefined) {
        setRecipe(recipe);
      } else {
        setRecipe(recipe);
      }
    });
  }, [pid]);

  return <ShowRecipe recipe={recipe} />;
};

export default Post;
