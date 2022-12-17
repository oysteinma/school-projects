import argparse
import sqlite3
import json
import re

def DB_FUNC(func):
  def wrapper(*args, **kwArgs):
    with sqlite3.connect("kaffeDB.sqlite3") as connection:
      cursor = connection.cursor()
      func(cursor, *args, **kwArgs)
      connection.commit()

  return wrapper

@DB_FUNC
def brukerhistorie1(cursor, datafile):
  with open(datafile, 'r') as file:
    data = json.load(file)
    for table, values in data.items():
      for value in values:

        # This is guaranteed to never become anything else than a bunch of
        # question marks separated by commas. Hence it's safe.
        fields = ", ".join("?" * len(value))

        # We don't really use any table/attribute names outside this letter scope.
        # This will make sure no sql injection shenanigans can pass through.
        def value_is_valid(value):
          if type(value) == str:
            return re.compile("[a-zA-Z_]+").match(value)

        if not all(value_is_valid(name) for name in [table, *value.keys()]):
          raise f"Names is not valid: {table} ({value.keys()})"

        print(f'INSERT INTO {table} ({", ".join(value.keys())}) VALUES ({list(value.values())})')
        cursor.execute(f'INSERT INTO {table} ({", ".join(value.keys())}) VALUES ({fields})', list(value.values()))

@DB_FUNC
def brukerhistorie2(cursor):
    print("Brukerhistorie 2: \n")
    for row in cursor.execute('''
        SELECT Bruker.FulltNavn, Count(Epost)
        FROM (
            SELECT Epost
            FROM Kaffe
            JOIN Kaffesmaking USING (KaffeID)
            WHERE strftime('%Y', SmaksDato) IS strftime('%Y', 'now')
            GROUP BY KaffeID, Epost
        )
        JOIN Bruker USING (Epost)
        GROUP BY Epost
        ORDER BY Count(Epost) DESC '''):
        print(row)
    print("\n")

@DB_FUNC
def brukerhistorie3(cursor):
    print("Brukerhistorie 3: \n")
    for row in cursor.execute('''
        SELECT Brenneri.Navn AS Brennerinavn, Kaffe.Navn AS Kaffenavn, Kaffe.KiloPris, avg(Kaffesmaking.Poeng) AS Gjennomsnittscore
        FROM 
        Brenneri JOIN Kaffe USING (BrenneriID)
        JOIN Kaffesmaking USING (KaffeID) 
        GROUP BY KaffeID
        ORDER BY (Gjennomsnittscore / KiloPris) DESC'''):
        print(row)
    print("\n")


@DB_FUNC
def brukerhistorie4(cursor):
    print("Brukerhistorie 4: \n")
    for row in cursor.execute('''
        SELECT Brenneri.Navn AS Brennerinavn, Kaffe.Navn AS Kaffenavn
        FROM 
            Brenneri JOIN Kaffe USING (BrenneriID)
            LEFT OUTER JOIN Kaffesmaking USING (KaffeID)
            WHERE Kaffe.Beskrivelse LIKE "%floral%" 
            OR Kaffesmaking.Notater LIKE "%floral%" '''):
        print(row)
    print("\n")

@DB_FUNC
def brukerhistorie5(cursor):
    print("Brukerhistorie 5: \n")
    for row in cursor.execute('''
        SELECT Brenneri.Navn AS Brennerinavn, Kaffe.Navn AS Kaffenavn
        FROM 
            Gaard JOIN Parti USING (GaardsID)
            JOIN ForedlingsMetode USING (MetodeNavn)
            JOIN Kaffe USING (PartiID)
            JOIN Brenneri USING (BrenneriID)
            WHERE (Gaard.Land LIKE '%Rwanda%'
            OR Gaard.Land LIKE '%Colombia%')
            AND Foredlingsmetode.MetodeNavn <> 'Vasket' '''):
        print(row)
    print("\n")

brukerhistorie_data = {
  1: {
    'description': 'Sett inn data i databasen. Filargumentet må være en json-fil i formatet {Table: [{Attributes}]}. Se input_example.json',
    'function': brukerhistorie1,
  },
  2: {
    'description': 'Print en liste over hvilke brukere som har smakt flest unike kaffer så langt i år, sortert synkende.',
    'function': brukerhistorie2
  },
  3: {
    'description': 'Print ut en liste over hvilke kaffer som gir forbrukeren mest for pengene.',
    'function': brukerhistorie3
  },
  4: {
    'description': 'Print ut en liste over hvilke kaffer som har blitt beskrevet av enten bruker og/eller brenneri med flora.',
    'function': brukerhistorie4
  },
  5: {
    'description': 'Print ut en liste over kaffer som kommer fra Rwanda eller Colombia og som IKKE er vasket.',
    'function': brukerhistorie5
  },
}

if __name__ == '__main__':
  parser = argparse.ArgumentParser(description='Brukerhistorier for DB2')
  brukerhistorier = parser.add_mutually_exclusive_group(required=True)

  brukerhistorier.add_argument(
    f'-1',
    f'--bruker-historie-1',
    type=str,
    metavar="datafile",
    help=brukerhistorie_data[1]['description']
  )

  for n in range(2,6):
    brukerhistorier.add_argument(
      f'-{n}',
      f'--bruker-historie-{n}',
      action="store_true",
      help=brukerhistorie_data[n]['description']
    )

  args = parser.parse_args()

  if args.bruker_historie_1 != None:
    brukerhistorie1(args.bruker_historie_1)
  else:
    n = int([k for k,v in vars(args).items() if v == True][0][-1])
    brukerhistorie_data[n]['function']()
