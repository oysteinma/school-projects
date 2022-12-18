import {
  Avatar,
  Box,
  Flex,
  HStack,
  Icon,
  useColorModeValue,
  Text,
} from "@chakra-ui/react";
import { FiCompass, FiBookmark } from "react-icons/fi";
import { MdOutlineCreate } from "react-icons/md";
import Link from "next/link";
import { getAuth, onAuthStateChanged } from "firebase/auth";
import { app } from "../Firebase/Firebase";
import { useState } from "react";

export default function Navbar({ children }) {
  return (
    <Box minH="100vh">
      <SidebarContent />
      <Box marginLeft="200px">{children}</Box>
    </Box>
  );
}

const SidebarContent = ({ ...rest }) => {
  const [avatar, setAvatar] = useState("");
  const LinkItems = [
    {
      name: "Profil",
      icon: avatar,
      isURLImage: true,
      path: "/Profile",
    },
    { name: "Utforsk", icon: FiCompass, path: "/" },
    { name: "Bokmerker", icon: FiBookmark, path: "/Bookmarks" },
    { name: "Ny Oppskrift", icon: MdOutlineCreate, path: "/CreateRecipe" },
  ];

  const auth = getAuth(app);
  onAuthStateChanged(auth, (user) => {
    if (user) {
      setAvatar(user.photoURL);
    } else {
      setAvatar("");
    }
  });
  return (
    <Box
      bg={useColorModeValue("gray.50", "gray.800")}
      borderRight="1px"
      borderRightColor={useColorModeValue("gray.200", "gray.700")}
      pos="fixed"
      h="full"
      {...rest}
    >
      <Link href={"/"}>
        <Flex
          h="20"
          alignItems="center"
          mx="8"
          justifyContent="space-between"
          cursor="pointer"
        >
          <Text fontSize="2xl" fontFamily="monospace" fontWeight="bold">
            DishWish
          </Text>
        </Flex>
      </Link>

      {LinkItems.map((link) => (
        <NavItem
          key={link.name}
          name={link.name}
          icon={link.icon}
          path={link.path}
          isURLImage={link.isURLImage}
        ></NavItem>
      ))}
    </Box>
  );
};

const NavItem = ({
  name,
  icon,
  path,
  isURLImage = false,
  children,
  ...rest
}) => {
  return (
    <Link
      href={path}
      style={{ textDecoration: "none" }}
      _focus={{ boxShadow: "none" }}
    >
      <Flex
        align="center"
        p="4"
        mx="4"
        borderRadius="lg"
        role="group"
        cursor="pointer"
        _hover={{
          bg: "teal.400",
          color: "white",
        }}
        {...rest}
      >
        {isURLImage && (
          <HStack spacing={5}>
            <Avatar size={"sm"} src={icon} />
            <Text>{name}</Text>
          </HStack>
        )}
        {!isURLImage && (
          <HStack>
            <Icon
              mr="4"
              fontSize="16"
              _groupHover={{
                color: "white",
              }}
              as={icon}
            />
            <Text>{name}</Text>
          </HStack>
        )}
        {children}
      </Flex>
    </Link>
  );
};
