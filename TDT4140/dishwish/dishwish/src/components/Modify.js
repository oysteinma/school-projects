import {
  Button,
  Link,
  AlertDialog,
  AlertDialogOverlay,
  AlertDialogContent,
  AlertDialogHeader,
  AlertDialogBody,
  AlertDialogFooter,
} from "@chakra-ui/react";
import { DeleteRecipe } from "../Firebase/DataStreams";
import { useRef, useState } from "react";
import { useRouter } from "next/router";
import { FiEdit, FiDelete } from "react-icons/fi";

export function Modify({ enabled, recipe }) {
  const [isOpen, setIsOpen] = useState(false);
  const router = useRouter();
  const cancelRef = useRef();
  if (!enabled) return <></>;

  return (
    <>
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
      </Button>
      <AlertDialog
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
      </AlertDialog>
    </>
  );
}
