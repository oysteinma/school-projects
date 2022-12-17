import {
  Box,
  ChakraProvider,
  Container,
  Flex,
  Heading,
  Image,
  SimpleGrid,
  Spacer,
  Stack,
  StackDivider,
  Text,
  useColorModeValue,
  VStack,
  Table,
  Thead,
  Tbody,
  Tr,
  Th,
  Td,
  HStack,
  NumberInput,
  NumberInputField,
  NumberInputStepper,
  NumberIncrementStepper,
  NumberDecrementStepper,
  Spinner,
  Center,
  Tooltip,
  Icon,
  chakra,
  /*   AlertDialog,
  AlertDialogOverlay,
  AlertDialogContent,
  AlertDialogHeader,
  AlertDialogBody,
  AlertDialogFooter, */
} from "@chakra-ui/react";
//import { DeleteRecipe } from "../../Firebase/DataStreams";
import { FiClock } from "react-icons/fi";
import { BsFillBookmarkFill } from "react-icons/bs";
import { GiChefToque } from "react-icons/gi";
import { GiKnifeFork } from "react-icons/gi";
//import { BsStar, BsStarFill, BsStarHalf } from "react-icons/bs";
import { useState } from "react";
import { getAuth } from "firebase/auth";
import { app } from "../../Firebase/Firebase";
import { Modify } from "../../components/Modify";
import { AddBookmark, RemoveBookmark } from "../../Firebase/DataStreams";

/* function Rating({ rating, numReviews }) {
  let size = "28";
  return (
    <Box d="flex" alignItems="center">
      {Array(5)
        .fill("")
        .map((_, i) => {
          const roundedRating = Math.round(rating * 2) / 2;
          if (roundedRating - i >= 1) {
            return (
              <BsStarFill
                size={size}
                key={i}
                style={{ marginLeft: "1" }}
                color={i < rating ? "teal.400" : "gray.300"}
              />
            );
          }
          if (roundedRating - i === 0.5) {
            return (
              <BsStarHalf size={size} key={i} style={{ marginLeft: "1" }} />
            );
          }
          return <BsStar size={size} key={i} style={{ marginLeft: "1" }} />;
        })}
      <Box
        as="span"
        ml="2"
        color={useColorModeValue("gray.800", "white")}
        fontSize="xl"
      >
        {numReviews} vurdering{numReviews != 1 && "er"}
      </Box>
    </Box>
  );
}

 */
const ShowRecipe = ({ recipe }) => {
  const auth = getAuth(app);
  const user = auth.currentUser;
  //let userId = null;
  let authorId = null;
  if (recipe) authorId = recipe.authorId;
  let canModify = authorId == null;

  if (user != null) {
    const userId = user.uid;

    if (userId == authorId) {
      canModify = true;
    }
  }

  if (
    recipe === {} ||
    recipe === undefined ||
    recipe.ingredients === undefined
  ) {
    return (
      <VStack>
        <Center h="500px">
          <Spinner size="xl" />
        </Center>
      </VStack>
    );
  }

  // Scaling number of servings for recipe
  const [servings, setServings] = useState(recipe.defaultServings);

  const [isBookmarked, setIsBookmarked] = useState(recipe.isBookmarked);

  // On delete alert dialog
  //const [isOpen, setIsOpen] = useState(false);
  //const cancelRef = useRef();

  {
    /* if (user.id==recipe.autherid) {

               return statement?
              
              */
  }
  return (
    <ChakraProvider>
      <Container maxW={"4xl"}>
        <SimpleGrid columns={1} spacing={8} py={{ base: 18, md: 24 }}>
          <Flex>
            <Spacer />
            <Image
              boxSize={"400px"}
              align={"center"}
              borderRadius="lg"
              alt={`Bilde av ${recipe.title}`}
              src={recipe.imageURL}
              fit={"cover"}
            />
            <Spacer />
          </Flex>
          <Stack spacing={{ base: 6, md: 10 }} align="center">
            <Stack spacing={4} direction="column" align="center">
              <Box as={"header"}>
                <HStack>
                  <Heading
                    lineHeight={1.1}
                    fontWeight={600}
                    fontSize={{ base: "2xl", sm: "4xl", lg: "5xl" }}
                  >
                    {recipe.title}
                  </Heading>
                  <Tooltip
                    label="Bokmerk oppskrift"
                    bg="white"
                    placement={"top"}
                    color={"gray.800"}
                    fontSize={"1.2em"}
                  >
                    <chakra.a display={"flex"}>
                      <Icon
                        style={{ cursor: "pointer" }}
                        onClick={async () => {
                          let result;
                          if (isBookmarked) {
                            result = await RemoveBookmark(recipe.id);
                          } else {
                            result = await AddBookmark(recipe.id);
                          }
                          if (result) {
                            setIsBookmarked(!isBookmarked);
                          } else {
                            alert(
                              "Du må logge inn for å kunne bruke bokmerker"
                            );
                          }
                        }}
                        as={BsFillBookmarkFill}
                        color={isBookmarked ? "teal.500" : "gray.300"}
                        h={7}
                        w={7}
                        alignSelf={"center"}
                      />
                    </chakra.a>
                  </Tooltip>
                </HStack>
              </Box>
              {/*  <Rating rating={recipe.rating} numReviews={recipe.numReviews} /> */}
              <HStack>
                <FiClock size={25} />
                <Text fontSize="xl">{recipe.timeEstimate} min</Text>
                <GiChefToque size={25} />
                <Text fontSize="xl">{recipe.difficulty}</Text>
                <GiKnifeFork size={25} />
                <Text fontSize="xl">{recipe.category}</Text>
              </HStack>
              <Text>
                {"Av "}
                {recipe.author}
              </Text>
            </Stack>
            <Stack spacing={2} direction="row" align="center">
              <Spacer />

              {/* 
              <Link
                href={`/CreateRecipe/${recipe.id}`}
                style={{ textDecoration: "none" }}
              >
                <Button leftIcon={<FiEdit />} colorScheme="teal" size="md">
                  Rediger oppskrift
                </Button>
              </Link>
              <Button
                onClick={() => setIsOpen(true)}
                leftIcon={<FiDelete />}
                colorScheme="red"
                size="md"
              >
                Slett oppskrift
              </Button> */}
              <Modify enabled={canModify} recipe={recipe} />

              {/*        <AlertDialog
                isOpen={isOpen}
                leastDestructiveRef={cancelRef}
                onClose={() => setIsOpen(false)}
                isCentered
              >
                <AlertDialogOverlay>
                  <AlertDialogContent>
                    <AlertDialogHeader fontSize="lg" fontWeight="bold">
                      Slett oppskrift
                    </AlertDialogHeader>

                    <AlertDialogBody>
                      Er du sikker? Denne handlingen kan ikke angres!
                    </AlertDialogBody>

                    <AlertDialogFooter>
                      <Button ref={cancelRef} onClick={() => setIsOpen(false)}>
                        Avbryt
                      </Button>
                      <Button
                        colorScheme="red"
                        onClick={() => {
                          DeleteRecipe(recipe);
                          setIsOpen(false);
                          router.push("/");
                        }}
                        ml={3}
                      >
                        Slett
                      </Button>
                    </AlertDialogFooter>
                  </AlertDialogContent>
                </AlertDialogOverlay>
              </AlertDialog> */}

              <Spacer />
            </Stack>

            <Box>
              <Text fontSize="xl">{recipe.description}</Text>
            </Box>

            <Stack
              spacing={{ base: 4, sm: 6 }}
              direction={"column"}
              divider={
                <StackDivider
                  borderColor={useColorModeValue("gray.200", "gray.600")}
                />
              }
            >
              <VStack spacing={{ base: 4, sm: 6 }}></VStack>

              <SimpleGrid columns={{ base: 1, md: 2 }} spacing={10}>
                <Box>
                  <Text
                    fontSize={{ base: "16px", lg: "18px" }}
                    color={useColorModeValue("black.500", "black.300")}
                    fontWeight={"600"}
                    textTransform={"uppercase"}
                    mb={"4"}
                  >
                    Handleliste
                  </Text>
                  <Table variant="simple">
                    <Thead>
                      <Tr>
                        <Th fontSize={"m"}>Ingredienser</Th>
                        <Th fontSize={"m"}>Mengde</Th>
                      </Tr>
                    </Thead>
                    <Tbody>
                      {recipe.ingredients.map((element, index) => (
                        <Tr key={index}>
                          <Td>{element.ingredient}</Td>
                          <Td>{`${
                            Math.round(
                              ((element.amount * servings) /
                                recipe.defaultServings) *
                                100
                            ) / 100
                          } ${element.unit}`}</Td>
                        </Tr>
                      ))}
                      <Tr key="portions">
                        <Th fontSize={"xs"}>Antall porsjoner</Th>
                        <Th fontSize={"m"}>
                          {" "}
                          <NumberInput
                            size="md"
                            maxW={24}
                            value={servings}
                            onChange={(v) => setServings(v)}
                            min={1}
                            max={99}
                          >
                            <NumberInputField />
                            <NumberInputStepper>
                              <NumberIncrementStepper />
                              <NumberDecrementStepper />
                            </NumberInputStepper>
                          </NumberInput>
                        </Th>
                      </Tr>
                    </Tbody>
                  </Table>
                </Box>
                <Box w="5s80">
                  <Text
                    fontSize={{ base: "16px", lg: "18px" }}
                    color={useColorModeValue("black.500", "black.300")}
                    fontWeight={"600"}
                    textTransform={"uppercase"}
                    mb={"4"}
                  >
                    Fremgangsmåte
                  </Text>

                  <Text fontSize="xl">{recipe.instructions}</Text>
                </Box>
              </SimpleGrid>
            </Stack>
          </Stack>
        </SimpleGrid>
      </Container>
    </ChakraProvider>
  );
};

export default ShowRecipe;
