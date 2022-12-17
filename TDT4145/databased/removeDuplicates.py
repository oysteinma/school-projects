for filename in ('mock_data/inneholder.sql', 'mock_data/dyrker.sql'):
  with open (filename, 'r') as file:
    unique_lines = set(file.readlines())
  with open(filename, 'w') as file:
    file.write("".join(unique_lines))