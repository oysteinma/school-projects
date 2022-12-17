from DbConnector import DbConnector
from tabulate import tabulate


class CreateTables:

    def __init__(self):
        self.connection = DbConnector()
        self.db_connection = self.connection.db_connection
        self.cursor = self.connection.cursor

    def create_table(self, query, table_name):
        self.cursor.execute(query % table_name)
        self.db_connection.commit()

    def drop_table(self, table_name):
        print("Dropping table %s..." % table_name)
        query = "DROP TABLE %s"
        self.cursor.execute(query % table_name)


def main():
    program_for_tables = None
    try:
        program_for_tables = CreateTables()

        user = """CREATE TABLE IF NOT EXISTS %s (
                   id VARCHAR(3) NOT NULL PRIMARY KEY,
                   has_labels BOOLEAN NOT NULL)
                """

        activity = """CREATE TABLE IF NOT EXISTS %s (
                   id INT NOT NULL PRIMARY KEY,
                   user_id VARCHAR(3) NOT NULL,
                   transportation_mode VARCHAR(20),
                   start_date_time DATETIME NOT NULL,
                   end_date_time DATETIME NOT NULL,
                   FOREIGN KEY (user_id) REFERENCES User(id) ON DELETE CASCADE)
                """

        trackpoints = """CREATE TABLE IF NOT EXISTS %s (
                    id INT(1) AUTO_INCREMENT PRIMARY KEY,
                    activity_id INT NOT NULL,
                    lat DOUBLE NOT NULL,
                    lon DOUBLE NOT NULL,
                    altitude INT NOT NULL,
                    date_time DATETIME NOT NULL,
                    FOREIGN KEY (activity_id) REFERENCES Activity(id) ON DELETE CASCADE)
                """

        program_for_tables.create_table(query=user, table_name="User")
        program_for_tables.create_table(query=activity, table_name="Activity")
        program_for_tables.create_table(
            query=trackpoints, table_name="TrackPoint")

        # program_for_tables.drop_table(table_name="TrackPoint")
        # program_for_tables.drop_table(table_name="Activity")
        # program_for_tables.drop_table(table_name="User")

    except Exception as e:
        print("ERROR: Failed to use database:", e)
    finally:
        if program_for_tables:
            program_for_tables.connection.close_connection()


if __name__ == '__main__':
    main()
