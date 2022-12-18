import {
  Button,
  Flex,
  FormControl,
  FormLabel,
  Heading,
  Input,
  Stack,
  useColorModeValue,
  Center,
  HStack,
  Textarea,
  Box,
  Spacer,
  NumberInput,
  NumberInputField,
  NumberInputStepper,
  NumberIncrementStepper,
  NumberDecrementStepper,
  Select,
  Spinner,
  VStack,
  FormErrorMessage,
} from "@chakra-ui/react";
import { app } from "../../Firebase/Firebase";
import { useState } from "react";
import ImageUploader from "../../components/ImageUploader";
import { UploadRecipe, UpdateRecipe } from "../../Firebase/DataStreams";
import { uploadFromBlobAsync } from "../../Firebase/Storage";
import { useRouter } from "next/router";
import { getAuth } from "firebase/auth";
import { Field, Form, Formik } from "formik";

const CreateRecipe = ({ recipe }) => {
  let tempTitle = "";
  let tempDescription = "";
  let tempImage = null;
  let tempInstructions = "";
  let tempTimeEstimate = 45;
  let tempDifficulty = "Enkel";
  let tempDefaultServings = 4;
  let tempCategory = "Frokost";
  let tempIngredients = [{ ingredient: "", amount: 2, unit: "ml" }];

  const auth = getAuth(app);
  let userId = null;
  let username = "Gjest";
  let userImage = null;
  const user = auth.currentUser;
  if (user) {
    // User is signed in, see docs for a list of available properties
    // https://firebase.google.com/docs/reference/js/firebase.User
    userId = user.uid;
    username = user.displayName;
    userImage = user.photoURL;
    // ...
  }

  const { asPath } = useRouter();
  const editingPath = asPath.split("/").length === 3;
  if (asPath.split("/").length === 2) {
    console.log(
      "Temporary IF statement such that normal CreateRecipe page will load."
    );
  } else if (typeof recipe !== "undefined" && editingPath) {
    tempTitle = recipe.title;
    tempDescription = recipe.description;
    tempInstructions = recipe.instructions;
    tempTimeEstimate = recipe.timeEstimate;
    tempDifficulty = recipe.difficulty;
    tempCategory = recipe.category;
    tempDefaultServings = recipe.defaultServings;
    tempIngredients = recipe.ingredients;
    tempImage = { name: recipe.id, url: recipe.imageURL };
  } else {
    return (
      <VStack>
        <Center h="500px">
          <Spinner size="xl" />
        </Center>
      </VStack>
    );
  }

  const router = useRouter();
  const placeholderText = useColorModeValue("gray.500", "gray.100");

  const [imageFile, setImageFile] = useState(tempImage);
  const [ingredients, setIngredients] = useState(tempIngredients);
  const [timeEstimate, setTimeEstimate] = useState(tempTimeEstimate);
  const [difficulty, setDifficulty] = useState(tempDifficulty);
  const [defaultServings, setDefaultServings] = useState(tempDefaultServings);
  const [category, setCategory] = useState(tempCategory);
  const [ingredientErrors, setIngredientErrors] = useState([]);
  const [hasPressedSubmit, setHasPressedSubmit] = useState(false);

  const addNewInput = () => {
    setIngredients([...ingredients, { ingredient: "", amount: 2, unit: "ml" }]);
  };
  const removeLastInput = () => {
    setIngredients(ingredients.slice(0, ingredients.length - 1));
  };

  return (
    <Formik
      initialValues={{
        recipeTitle: tempTitle,
        description: tempDescription,
        instructions: tempInstructions,
      }}
      onSubmit={(values, actions) => {
        {
          setHasPressedSubmit(true);

          // Check that image is valid
          if (imageFile === null) {
            setHasPressedSubmit(true);
            actions.setSubmitting(false);
            return;
          }
          // Check that ingredients is valid
          for (let error of ingredientErrors) {
            if (error) {
              actions.setSubmitting(false);
              return;
            }
          }

          if (!editingPath) {
            uploadFromBlobAsync({
              ...imageFile,
              callback: (imageURL) => {
                let recipe = {
                  author: username,
                  authorImage: userImage,
                  authorId: userId,
                  title: values.recipeTitle,
                  description: values.description,
                  ingredients,
                  instructions: values.instructions,
                  timeEstimate,
                  defaultServings,
                  category,
                  difficulty,
                  imageURL: imageURL,
                  numReviews: 0,
                  rating: 0,
                };
                UploadRecipe(recipe, (id) => {
                  actions.setSubmitting(false);
                  router.push(`/ShowRecipe/${id}`);
                });
              },
            });
          } else if (editingPath) {
            uploadFromBlobAsync({
              ...imageFile,
              oldImageURL: recipe.imageURL,
              callback: (imageURL) => {
                let newRecipe = {
                  author: username,
                  authorImage: userImage,
                  authorId: userId,
                  title: values.recipeTitle,
                  description: values.description,
                  ingredients,
                  instructions: values.instructions,
                  timeEstimate,
                  defaultServings,
                  category,
                  difficulty,
                  imageURL: imageURL,
                  numReviews: 0,
                  rating: 0,
                };
                UpdateRecipe(newRecipe, recipe.id, () => {
                  actions.setSubmitting(false);
                  router.push(`/ShowRecipe/${recipe.id}`);
                });
              },
            });
          }
          console.log("run");
        }
      }}
    >
      {(props) => (
        <Form>
          <Flex minH={"100vh"} align={"center"} justify={"center"}>
            <Stack
              spacing={4}
              w={"full"}
              maxW={"md"}
              bg={useColorModeValue("white", "gray.900")}
              rounded={"xl"}
              boxShadow={"lg"}
              p={6}
              my={12}
            >
              {!editingPath && (
                <Heading lineHeight={1.1} fontSize={{ base: "2xl", sm: "3xl" }}>
                  Lag ny oppskrift
                </Heading>
              )}
              {editingPath && (
                <Heading lineHeight={1.1} fontSize={{ base: "2xl", sm: "3xl" }}>
                  Oppdater oppskrift
                </Heading>
              )}
              <FormControl
                id="recipeImage"
                isInvalid={!imageFile && hasPressedSubmit}
              >
                <Center>
                  <ImageUploader
                    imageFile={imageFile}
                    setImageFile={setImageFile}
                  ></ImageUploader>
                </Center>
                <FormErrorMessage>
                  {"Oppskriften trenger et bilde"}
                </FormErrorMessage>
              </FormControl>
              <Field
                name="recipeTitle"
                validate={(value) => {
                  if (value.length < 3) {
                    return "Må være minst 3 tegn lang";
                  }
                  if (value.length > 20) {
                    return "Kan være maks 20 tegn lang";
                  }
                }}
              >
                {({ field, form }) => (
                  <FormControl
                    isInvalid={
                      form.errors.recipeTitle && form.touched.recipeTitle
                    }
                  >
                    <FormLabel>Navn på oppskrift</FormLabel>
                    <Input
                      placeholder="Skriv inn navnet"
                      _placeholder={{ color: placeholderText }}
                      type="text"
                      {...field}
                    />
                    <FormErrorMessage>
                      {form.errors.recipeTitle}
                    </FormErrorMessage>
                  </FormControl>
                )}
              </Field>
              <Field
                name="description"
                validate={(value) => {
                  if (value.length < 3) {
                    return "Må være minst 3 tegn lang";
                  }
                  if (value.length > 2000) {
                    return "Kan være maks 2000 tegn lang";
                  }
                }}
              >
                {({ field, form }) => (
                  <FormControl
                    isInvalid={
                      form.errors.description && form.touched.description
                    }
                  >
                    <FormLabel>Beskrivelse</FormLabel>
                    <Textarea
                      placeholder="Beskriv retten din"
                      _placeholder={{ color: placeholderText }}
                      type="text"
                      {...field}
                    />
                    <FormErrorMessage>
                      {form.errors.description}
                    </FormErrorMessage>
                  </FormControl>
                )}
              </Field>

              <HStack>
                <FormControl id="recipeCategory">
                  <FormLabel>Kategori</FormLabel>
                  <Select
                    value={category}
                    onChange={(e) => setCategory(e.target.value)}
                  >
                    <option value="Frokost">Frokost</option>
                    <option value="Lunsj">Lunsj</option>
                    <option value="Middag">Middag</option>
                  </Select>
                </FormControl>

                <FormControl id="recipeDifficulty">
                  <FormLabel>Vanskelighetsgrad</FormLabel>
                  <Select
                    value={difficulty}
                    onChange={(e) => setDifficulty(e.target.value)}
                  >
                    <option value="Enkel">Enkel</option>
                    <option value="Middels">Middels</option>
                    <option value="Avansert">Avansert</option>
                  </Select>
                </FormControl>
              </HStack>

              <FormControl id="recipeTimeEstimate" isRequired>
                <FormLabel>Tidsestimat (minutter)</FormLabel>
                <NumberInput
                  size="md"
                  defaultValue={45}
                  min={1}
                  max={999}
                  value={timeEstimate}
                  onChange={(e) => setTimeEstimate(e === "" ? e : parseInt(e))}
                >
                  <NumberInputField />
                </NumberInput>
              </FormControl>

              <FormControl id="recipeServings" isRequired>
                <FormLabel>Antall porsjoner</FormLabel>
                <NumberInput
                  size="md"
                  maxW={24}
                  defaultValue={4}
                  min={1}
                  max={99}
                  value={defaultServings}
                  onChange={(e) =>
                    setDefaultServings(e === "" ? e : parseInt(e))
                  }
                >
                  <NumberInputField />
                  <NumberInputStepper>
                    <NumberIncrementStepper />
                    <NumberDecrementStepper />
                  </NumberInputStepper>
                </NumberInput>
              </FormControl>

              <FormControl id="recipeIngredients" isRequired>
                <FormLabel>Ingredienser</FormLabel>
                {ingredients?.map((element, index) => (
                  <Stack key={index}>
                    <HStack spacing={3.5} align={"start"}>
                      <FormControl
                        isInvalid={ingredientErrors[index]}
                        isRequired
                      >
                        <Input
                          placeholder="Ingrediens"
                          _placeholder={{ color: placeholderText }}
                          type="text"
                          value={ingredients[index].ingredient}
                          onChange={(e) => {
                            let newIngredients = [...ingredients];
                            newIngredients[index].ingredient = e.target.value;
                            setIngredients(newIngredients);

                            let newIngredientErrors = [...ingredientErrors];

                            if (e.target.value.length < 3) {
                              newIngredientErrors[index] =
                                "Må være minst 3 tegn lang";
                            } else if (e.target.value.length > 20) {
                              newIngredientErrors[index] =
                                "Kan være maks 20 tegn lang";
                            } else {
                              newIngredientErrors[index] = "";
                            }
                            setIngredientErrors(newIngredientErrors);
                          }}
                        />
                        <FormErrorMessage>
                          {ingredientErrors[index]}
                        </FormErrorMessage>
                      </FormControl>
                      <HStack>
                        <FormControl id="recipeUnit" isRequired>
                          <NumberInput
                            size="md"
                            defaultValue={ingredients[index].amount}
                            min={1}
                            max={999}
                            onChange={(e) => {
                              ingredients[index].amount = parseFloat(e);
                              setIngredients(ingredients);
                              console.log(ingredients);
                            }}
                          >
                            <NumberInputField />
                          </NumberInput>
                        </FormControl>

                        <FormControl id="recipeUnit" isRequired>
                          <Select
                            defaultValue={ingredients[index].unit}
                            onChange={(e) => {
                              ingredients[index].unit = e.target.value;
                              setIngredients(ingredients);
                            }}
                          >
                            <option value="ml">ml</option>
                            <option value="dl">dl</option>
                            <option value="l">l</option>
                            <option value="g">g</option>
                            <option value="kg">kg</option>
                            <option value="stk">stk</option>
                            <option value="pose">pose</option>
                            <option value="boks">boks</option>
                            <option value="ts">ts</option>
                            <option value="ss">ss</option>
                            <option value="krm">krm</option>
                          </Select>
                        </FormControl>
                      </HStack>
                    </HStack>

                    <Flex>
                      <Spacer></Spacer>
                      <Box marginRight={1}>
                        {ingredients.length - 1 === index && index != 0 && (
                          <Button
                            rounded="full"
                            onClick={removeLastInput}
                            size="sm"
                            colorScheme="red"
                            w={8}
                          >
                            –
                          </Button>
                        )}
                      </Box>

                      <Box>
                        {ingredients.length - 1 === index && (
                          <Button
                            rounded="full"
                            onClick={addNewInput}
                            size="sm"
                            colorScheme="teal"
                            w={8}
                          >
                            +
                          </Button>
                        )}
                      </Box>
                    </Flex>
                  </Stack>
                ))}
              </FormControl>

              <Field
                name="instructions"
                validate={(value) => {
                  if (value.length < 3) {
                    return "Må være minst 3 tegn lang";
                  }
                  if (value.length > 2000) {
                    return "Kan være maks 2000 tegn lang";
                  }
                }}
              >
                {({ field, form }) => (
                  <FormControl
                    isInvalid={
                      form.errors.instructions && form.touched.instructions
                    }
                  >
                    <FormLabel>Fremgangsmåte</FormLabel>
                    <Textarea
                      placeholder="Hvordan tilberedes retten?"
                      _placeholder={{ color: placeholderText }}
                      type="text"
                      size="md"
                      {...field}
                      id="desc"
                    />
                    <FormErrorMessage>
                      {form.errors.instructions}
                    </FormErrorMessage>
                  </FormControl>
                )}
              </Field>

              <Stack spacing={6} direction={["column", "row"]}>
                {!editingPath && (
                  <Button
                    colorScheme={"teal"}
                    w="full"
                    type="submit"
                    isLoading={props.isSubmitting}
                  >
                    Publiser oppskrift
                  </Button>
                )}
                {editingPath && (
                  <Button
                    colorScheme={"teal"}
                    w="full"
                    type="submit"
                    isLoading={props.isSubmitting}
                  >
                    Oppdater oppskrift
                  </Button>
                )}
              </Stack>
            </Stack>
          </Flex>
        </Form>
      )}
    </Formik>
  );
};

export default CreateRecipe;
