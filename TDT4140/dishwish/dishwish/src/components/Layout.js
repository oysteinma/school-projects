import { ChakraProvider } from "@chakra-ui/react";
import Navbar from "./Navbar";

const Layout = ({ children }) => {
  return (
    <ChakraProvider>
      <Navbar>{children}</Navbar>;
    </ChakraProvider>
  );
};

export default Layout;
