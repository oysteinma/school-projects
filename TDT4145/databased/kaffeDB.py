import sqlite3
from os import listdir, path

connection = sqlite3.connect("kaffeDB.sqlite3")
cursor = connection.cursor()

def clean_statement(s):
  return s \
    .replace('  ', ' ') \
    .replace('( ', '(') \
    .replace(' )', ')')

with open('kaffeDB.sql', 'r') as blueprint:
  statements = blueprint.read()
  for s in (clean_statement(s) for s in statements.split('\n\n')):
    print(f'{s}\n')
    cursor.execute(s)

# See https://www.mockaroo.com/

for filename in listdir('mock_data'):
  with open(path.join('mock_data', filename), encoding='utf-8') as blueprint:
    print(f'\n\nInserting data into {filename}')
    for s in blueprint.readlines():
      if (s.startswith('insert')):
        print(f'{s[:-1]}')
        cursor.execute(s)

connection.commit()
connection.close()