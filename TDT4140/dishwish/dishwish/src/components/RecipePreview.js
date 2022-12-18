/* eslint-disable no-unused-vars */
import {
  Avatar,
  Flex,
  Box,
  Image,
  useColorModeValue,
  Icon,
  chakra,
  Tooltip,
  HStack,
  Text,
  VStack,
  Center,
  Button,
} from "@chakra-ui/react";
import { BsStar, BsStarFill, BsStarHalf } from "react-icons/bs";
import { FiClock } from "react-icons/fi";
import { BsFillBookmarkFill } from "react-icons/bs";
import { GiChefToque } from "react-icons/gi";
import Link from "next/link";
import { useEffect, useRef, useState } from "react";
import { AddBookmark, RemoveBookmark } from "../Firebase/DataStreams";

function Rating({ rating, numReviews }) {
  return (
    <HStack align={"left"}>
      <Box d="flex" alignItems="center">
        {Array(5)
          .fill("")
          .map((_, i) => {
            const roundedRating = Math.round(rating * 2) / 2;
            if (roundedRating - i >= 1) {
              return (
                <BsStarFill
                  key={i}
                  style={{ marginLeft: "1" }}
                  color={i < rating ? "teal.500" : "gray.300"}
                />
              );
            }
            if (roundedRating - i === 0.5) {
              return <BsStarHalf key={i} style={{ marginLeft: "1" }} />;
            }
            return <BsStar key={i} style={{ marginLeft: "1" }} />;
          })}
      </Box>
      <Center>
        <Box
          as="span"
          ml="-5px"
          color={useColorModeValue("gray.800", "white")}
          fontSize="md"
        >
          {`(${numReviews})`}
        </Box>
      </Center>
    </HStack>
  );
}

function TitleText({ recipe }) {
  const ref = useRef(null);
  const [isOverflown, setIsOverflown] = useState(false);
  useEffect(() => {
    const element = ref.current;
    if (element !== null) {
      setIsOverflown(element.scrollWidth > element.clientWidth);
    }
  }, []);

  return (
    <Link href={`/ShowRecipe/${recipe.id}`}>
      <Text
        size="lg"
        variant="link"
        cursor={"pointer"}
        isTruncated={true}
        ref={ref}
      >
        <Tooltip
          label={recipe.title}
          isDisabled={!isOverflown}
          bg="white"
          placement={"top-start"}
          color={"gray.800"}
          fontSize={"1.2em"}
        >
          {recipe.title}
        </Tooltip>
      </Text>
    </Link>
  );
}

function RecipePreview({ recipe }) {
  // recipe = {
  //   author: "Kari Nordmann",
  //   imageURL:
  //     "https://res.cloudinary.com/norgesgruppen/images/c_scale,dpr_auto,f_auto,q_auto:eco,w_1600/abt0dcva6rkllmw3w9kg/klassisk-angusburger-med-cheddar-bacon-og-sprostekt-lok",
  //   title: "Angusburger",
  //   rating: 4.5,
  //   numReviews: 34,
  //   timeEstimate: 45,
  //   difficulty: "Middels",
  // };

  const [isBookmarked, setIsBookmarked] = useState(recipe.isBookmarked);

  return (
    <Flex p={50} w="full" alignItems="center" justifyContent="center">
      <Box
        bg={useColorModeValue("white", "gray.900")}
        maxW="sm"
        borderWidth="1px"
        rounded="lg"
        shadow="lg"
        position="relative"
      >
        <Link href={`/ShowRecipe/${recipe.id}`}>
          <Image
            width={"420px"}
            height={"280px"}
            layout={"fill"}
            objectFit={"cover"}
            style={{ cursor: "pointer" }}
            src={recipe.imageURL}
            alt={`Bilde av ${recipe.title}`}
            roundedTop="lg"
          />
        </Link>

        <Box p="6">
          <Flex mt="1" justifyContent="space-between" alignContent="center">
            <Box
              fontSize="2xl"
              fontWeight="semibold"
              as="h4"
              lineHeight="tight"
              isTruncated
            >
              <TitleText recipe={recipe}></TitleText>
            </Box>
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
                      alert("Du må logge inn for å kunne bruke bokmerker");
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
          </Flex>

          <Flex justifyContent="space-between" alignContent="center">
            <VStack align={"left"}>
              {/* <Rating rating={recipe.rating} numReviews={recipe.numReviews} /> */}
              <HStack>
                <FiClock size={20} />
                <Text fontSize="sm">{recipe.timeEstimate} min</Text>
                <GiChefToque size={20} />
                <Text fontSize="sm">{recipe.difficulty}</Text>
              </HStack>
            </VStack>
            <Box fontSize="md" color={useColorModeValue("gray.800", "white")}>
              <Tooltip
                label={recipe.author}
                bg="white"
                placement={"bottom"}
                color={"gray.800"}
                fontSize={"1.2em"}
              >
                <chakra.a display={"flex"}>
                  <Avatar size={"sm"} src={recipe.authorImage} />
                </chakra.a>
              </Tooltip>
            </Box>
          </Flex>
        </Box>
      </Box>
    </Flex>
  );
}

export default RecipePreview;
