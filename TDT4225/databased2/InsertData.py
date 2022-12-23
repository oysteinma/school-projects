from DbConnector import DbConnector
from datetime import datetime
import os


class InsertData:

    def __init__(self):
        self.connection = DbConnector()
        self.db_connection = self.connection.db_connection
        self.cursor = self.connection.cursor
        self.table_user = "User"
        self.table_activity = "Activity"
        self.table_trackpoint = "TrackPoint"
        self.path_to_main = 'dataset/dataset/Data'
        self.path_to_labels = "dataset/dataset/labeled_ids.txt"
        self.activity_id = 1
        self.user_id = ""

    # --------------------------------- Reading Helper Functions --------------------------------- #

    def read_root_label_id(self, path_label):
        liste = []
        with open(path_label) as f:
            for line in f:
                liste.append(line.strip())
        return liste

    def read_plt_start_end(self, path_plt):
        with open(path_plt) as file:

            firstLine = file.readlines()[6]
            startTimeSplit = firstLine.split(',')
            startTime = startTimeSplit[-2] + " " + startTimeSplit[-1]
            startTime_object = datetime.fromisoformat(startTime.strip())

            file.seek(0)

            lastLine = file.readlines()[-1]
            endTimeSplit = lastLine.split(',')
            endTime = endTimeSplit[-2] + " " + endTimeSplit[-1]
            endTime_object = datetime.fromisoformat(endTime.strip())

            return startTime_object, endTime_object

    def read_trackpoints(self, path_plt):
        trackpoints = []
        with open(path_plt) as file:
            for tp in file.readlines()[6:]:
                tp_split = tp.split(',')
                trackpoints.append(
                    (self.activity_id, tp_split[0], tp_split[1], tp_split[3],
                     tp_split[5] + " " + tp_split[6].strip()))

        return trackpoints

    def read_label_start_end_transport(self, path_for_label):
        liste = []
        with open(path_for_label) as file:
            for line in file.readlines()[1:]:
                split_line = line.strip().split()
                liste.append((datetime.fromisoformat(split_line[0].replace(
                    "/", "-") + " " + split_line[1]), datetime.fromisoformat(
                    split_line[2].replace("/", "-") + " " + split_line[3]), split_line[4]))

        return liste

    # --------------------------------- Inserting Data --------------------------------- #

    def insert_data(self):
        has_labels = self.read_root_label_id(self.path_to_labels)
        create_all_users = True
        for (root, dirs, files) in os.walk(self.path_to_main, topdown=True):
            if (create_all_users):
                # Inserting Users
                for dir in dirs:
                    if (dir.isnumeric()):
                        is_labeled = dir in has_labels
                        query_user = "INSERT INTO %s (id, has_labels) VALUES ('%s', %s)"
                        self.cursor.execute(query_user %
                                            (self.table_user, dir, is_labeled))
                        self.db_connection.commit()
                create_all_users = False

            if root[-14:-11].isnumeric():
                self.user_id = root[-14:-11]

            if "Trajectory" in root:
                for plt in files:
                    path_plt = root + "/" + plt

                    if sum(1 for _ in open(path_plt)) > 2506:
                        continue

                    path_start, path_end = self.read_plt_start_end(
                        path_plt=path_plt)

                    if self.user_id not in has_labels:

                        self.insert_activities(
                            None, path_start, path_end)

                        trackpoints = self.read_trackpoints(
                            path_plt=path_plt)

                        self.insert_trackpoints(
                            trackpoints=trackpoints)

                    else:

                        all_labels = self.read_label_start_end_transport(
                            path_for_label=root[:-10] + "labels.txt")

                        for label in all_labels:
                            value_label = None
                            if label[0] == path_start and label[1] == path_end:
                                value_label = label[2]

                            self.insert_activities(
                                value_label, path_start, path_end)

                            trackpoints = self.read_trackpoints(
                                path_plt=path_plt)

                            self.insert_trackpoints(
                                trackpoints=trackpoints)

    # Inserting Activities
    def insert_activities(self, type_label, start, end):
        query_activity = "INSERT INTO %s (id, user_id, transportation_mode, start_date_time, end_date_time) VALUES (%s, '%s', '%s', '%s', '%s')"
        self.cursor.execute(query_activity %
                            (self.table_activity, self.activity_id, self.user_id, type_label, start, end))
        self.db_connection.commit()

    # Inserting batches of TrackPoints by concatination
    def insert_trackpoints(self, trackpoints):
        self.cursor.execute(
            f"INSERT INTO {self.table_trackpoint} (activity_id, lat, lon, altitude, date_time) VALUES {str(trackpoints).strip('[]')}")
        self.db_connection.commit()

        self.activity_id += 1


def main():
    program_for_inserting = None
    try:
        program_for_inserting = InsertData()

        program_for_inserting.insert_data()

    except Exception as e:
        print("ERROR: Failed to use database:", e)
    finally:
        if program_for_inserting:
            program_for_inserting.connection.close_connection()


if __name__ == '__main__':
    main()
