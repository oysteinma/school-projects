import GridView from "../components/GridView";
import FilterSearch from "../components/FilterSearch";
import { GetAllRecipes } from "../Firebase/DataStreams";
import { useEffect, useState } from "react";
import { Box } from "@chakra-ui/react";

const Home = () => {
  let [recipes, setRecipes] = useState([]);
  const [filterCategory, setFilterCategory] = useState("Alle");
  useEffect(() => {
    GetAllRecipes().then((recipes) => {
      if (filterCategory !== "Alle") {
        recipes = recipes.filter((e) => e.category === filterCategory);
      }
      setRecipes(recipes);
    });
  }, [filterCategory]);
  return (
    <Box>
      <Box paddingLeft={12} paddingTop={10}>
        <FilterSearch setFilterCategory={setFilterCategory} />
      </Box>
      <GridView recipes={recipes} />
    </Box>
  );
};

export default Home;
