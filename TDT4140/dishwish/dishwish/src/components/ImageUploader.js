import { useCallback, useState } from "react";
import { useDropzone } from "react-dropzone";
import {
  Flex,
  Spinner,
  Alert,
  AlertIcon,
  AlertDescription,
  Image,
} from "@chakra-ui/react";

// Code from https://pablorocha.me/blog/firebase-storage-react-dropzone-2

function ImageUploader({ imageFile, setImageFile }) {
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState(null);
  const onImageChange = useCallback(async (acceptedFiles) => {
    const file = acceptedFiles?.[0];

    if (!file) {
      return;
    }

    setIsLoading(true);
    setError(null);

    try {
      const newImageFile = {
        url: URL.createObjectURL(file),
        name: `${file.name}_${Date.now()}`,
      };
      setImageFile(newImageFile);
    } catch (e) {
      setIsLoading(false);
      setError(e.message);
      return;
    }

    setIsLoading(false);
  }, []);

  const { getRootProps, getInputProps } = useDropzone({
    onDrop: onImageChange,
    accept: "image/jpeg,image/png,image/gif",
  });

  return (
    <>
      <Flex
        justify="center"
        align="center"
        borderRadius={5}
        textAlign="center"
        {...getRootProps()}
      >
        <input {...getInputProps()} />
        {isLoading && <Spinner />}
        {!isLoading && imageFile === null && (
          <Image
            width={"420px"}
            height={"280px"}
            layout={"fill"}
            objectFit={"cover"}
            src={"images/uploadPlaceholder.png"}
            alt={`Last opp bilde her`}
            roundedTop="lg"
          ></Image>
        )}
        {!isLoading && imageFile !== null && (
          <Image
            width={"420px"}
            height={"280px"}
            layout={"fill"}
            objectFit={"cover"}
            src={imageFile.url}
            alt={`Midlertidig opplastningsbilde`}
            roundedTop="lg"
          ></Image>
        )}
      </Flex>
      {error && (
        <Alert
          status={error ? "error" : "success"}
          w={250}
          borderRadius={5}
          m={2}
        >
          <AlertIcon />
          <AlertDescription w={200}>{error}</AlertDescription>
        </Alert>
      )}
    </>
  );
}

export default ImageUploader;
