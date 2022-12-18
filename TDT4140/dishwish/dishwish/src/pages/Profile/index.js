import {
  Flex,
  FormControl,
  FormLabel,
  Heading,
  Stack,
  useColorModeValue,
  Avatar,
  Center,
  Text,
  Box,
  Button,
  HStack,
  Switch,
  useColorMode,
} from "@chakra-ui/react";
import { signInWithGoogle } from "../../Firebase/MyLogin";
import { useState } from "react";
import { getAuth, onAuthStateChanged } from "firebase/auth";
import { app } from "../../Firebase/Firebase";
import { MySignOut } from "../../Firebase/MySignOut";

export default function Profile() {
  const defName = "Velkommen til DishWish!";
  const defMail = "Trykk på logg inn knappen for å starte";
  const [name, setName] = useState(defName);
  const [email, setEmail] = useState(defMail);
  const [photo, setPhoto] = useState("");
  const [LoggedIn, setLoggedIn] = useState(false);
  const auth = getAuth(app);
  const { toggleColorMode } = useColorMode();

  onAuthStateChanged(auth, (user) => {
    if (user) {
      setName(user.displayName);
      setEmail(user.email);
      setPhoto(user.photoURL);
      setLoggedIn(true);
    } else {
      setName(defName);
      setEmail(defMail);
      setPhoto("");
      setLoggedIn(false);
    }
  });

  return (
    <Flex
      minH={"100vh"}
      align={"center"}
      justify={"center"}
      bg={useColorModeValue("gray.50", "gray.800")}
    >
      <Stack
        align={"center"}
        spacing={4}
        w={"full"}
        maxW={"md"}
        bg={useColorModeValue("white", "gray.700")}
        rounded={"xl"}
        boxShadow={"lg"}
        p={6}
        my={12}
      >
        <Heading lineHeight={1.1} fontSize={{ base: "2xl", sm: "3xl" }}>
          Profil
        </Heading>
        <FormControl id="userName">
          <Stack direction={["column"]} spacing={6}>
            <Center>
              <Avatar size="xl" src={photo}></Avatar>
            </Center>
          </Stack>
        </FormControl>
        <FormControl id="userName">
          <FormLabel>Navn</FormLabel>
          <Text type="text">{name}</Text>
        </FormControl>
        <FormControl id="email">
          <FormLabel>E-postadresse</FormLabel>
          <Text type="email">{email}</Text>
        </FormControl>
        <FormControl id="toggleMode" display="flex">
          <HStack spacing={3.5}>
            <Switch
              size="md"
              onChange={toggleColorMode}
              defaultChecked={
                typeof window !== "undefined"
                  ? localStorage.getItem("chakra-ui-color-mode") === "dark"
                    ? true
                    : false
                  : console.log("Could not load color mode")
              }
            />
            <FormLabel mb="0">Modus (Lys/Mørk)</FormLabel>
          </HStack>
        </FormControl>
        <Stack spacing={6} direction={["column"]}>
          {!LoggedIn ? (
            <Box>
              <Button
                colorScheme="green"
                onClick={() => {
                  signInWithGoogle();
                }}
              >
                Logg inn
              </Button>
            </Box>
          ) : (
            <Box>
              <Button
                colorScheme="red"
                onClick={() => {
                  MySignOut();
                }}
              >
                Logg ut
              </Button>
            </Box>
          )}
        </Stack>
      </Stack>
    </Flex>
  );
}
