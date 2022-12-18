import { Grid, GridItem, Box } from "@chakra-ui/react";
import RecipePreview from "../components/RecipePreview";
import { useWindowWidth } from "../utils/Resizing";

const GridView = ({ recipes }) => {
  const [width] = useWindowWidth();

  return (
    <Box>
      <Grid
        templateColumns={
          width < 800
            ? "repeat(1, 1fr)"
            : width < 1110
            ? "repeat(2, 1fr)"
            : "repeat(3, 1fr)"
        }
      >
        {recipes.map((recipe) => (
          <GridItem key={recipe.id}>
            <RecipePreview recipe={recipe} />
          </GridItem>
        ))}
      </Grid>
    </Box>
  );
};

export default GridView;
