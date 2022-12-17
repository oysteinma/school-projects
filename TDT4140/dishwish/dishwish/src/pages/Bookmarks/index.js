import GridView from "../../components/GridView";
import FilterSearch from "../../components/FilterSearch";
import { GetRecipesOnBookmark } from "../../Firebase/DataStreams";
import { useEffect, useState } from "react";
import { Box, Text, Center, Flex, useColorModeValue } from "@chakra-ui/react";

const Bookmarks = () => {
  let [recipes, setRecipes] = useState([]);
  const [filterCategory, setFilterCategory] = useState("Alle");
  useEffect(() => {
    GetRecipesOnBookmark(true).then((recipes) => {
      if (filterCategory !== "Alle") {
        recipes = recipes.filter((e) => e.category === filterCategory);
      }
      setRecipes(recipes);
    });
  }, [filterCategory]);
  return (
    <Box>
      {recipes && (
        <Box>
          <Box paddingLeft={12} paddingTop={10}>
            <FilterSearch setFilterCategory={setFilterCategory} />
          </Box>
          <GridView recipes={recipes} />
        </Box>
      )}
      {!recipes && (
        <Flex
          minH={"100vh"}
          align={"center"}
          justify={"center"}
          py={12}
          bg={useColorModeValue("gray.50", "gray.800")}
        >
          <Center>
            <Text fontSize={"2xl"}>
              Du må logge inn for å kunne bruke bokmerker
            </Text>
          </Center>
        </Flex>
      )}
    </Box>
  );
};

export default Bookmarks;
