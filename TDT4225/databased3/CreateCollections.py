from DbConnector import DbConnector


class CreateCollections:

    def __init__(self):
        self.connection = DbConnector()
        self.client = self.connection.client
        self.db = self.connection.db

    def create_collection(self, collection_name):
        collection = self.db.create_collection(collection_name)
        print('Created collection: ', collection)

    def drop_collections(self, collection_name):
        my_collection = self.db[collection_name]
        my_collection.drop()
        print("Dropping:", collection_name)


def main():
    program_for_collections = None
    try:
        program_for_collections = CreateCollections()
        program_for_collections.create_collection(collection_name="User")
        program_for_collections.create_collection(collection_name="Activity")
        program_for_collections.create_collection(
            collection_name="TrackPoint")

        print("__Running program for dropping collections__")
        # program_for_collections.drop_collections(collection_name="User")
        # program_for_collections.drop_collections(collection_name="Activity")
        # program_for_collections.drop_collections(collection_name="TrackPoint")

    except Exception as e:
        print("ERROR: Failed to use database:", e)
    finally:
        if program_for_collections:
            program_for_collections.connection.close_connection()


if __name__ == '__main__':
    main()
