import { Select, useColorModeValue } from "@chakra-ui/react";

const FilterSearch = ({ setFilterCategory }) => {
  return (
    <Select
      onChange={(e) => setFilterCategory(e.target.value)}
      w="20%"
      bg={useColorModeValue("gray.200", "gray.900")}
    >
      <option value="Alle"> Alle</option>
      <option value="Frokost"> Frokost</option>
      <option value="Lunsj"> Lunsj</option>
      <option value="Middag"> Middag</option>
    </Select>
  );
};

export default FilterSearch;
