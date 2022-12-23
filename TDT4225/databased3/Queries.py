from datetime import timedelta
from pprint import pprint
from tabulate import tabulate
from haversine import haversine
from DbConnector import DbConnector


class MongoMain:

    def __init__(self):
        self.connection = DbConnector()
        self.client = self.connection.client
        self.db = self.connection.db
        self.collection_user = "User"
        self.collection_activity = "Activity"
        self.collection_trackpoint = "TrackPoint"

    def q1(self):
        liste = [self.collection_user, self.collection_activity,
                 self.collection_trackpoint]

        result = []
        for name in liste:
            collection = self.db[name]
            total_count = collection.count_documents({})
            result.append((name, total_count))

        print("Result from query 1: \n")
        print(tabulate(result, ["Collection", "Count"]))

    def q2(self):
        result = []
        userCount = self.db[self.collection_user].count_documents({})
        activityCount = self.db[self.collection_activity].count_documents({})
        result.append((userCount, round(activityCount/userCount)))

        print("Result from query 2: \n")
        print(tabulate(result, ['User Count', 'Avg Activity per User']))

    def q3(self):
        result = list(self.db[self.collection_activity].aggregate([
            {"$group": {"_id": "$user_id", "count": {"$sum": 1}}},
            {"$sort": {"count": -1}},
            {"$limit": 20}
        ]))

        print("Result from query 3: \n")
        self.tabulate_queries(
            result, ['Top 20 users', 'Count Activities'])

    def q4(self):
        result = list(self.db[self.collection_activity].aggregate([
            {"$match": {"transportation_mode": "taxi"}},
            {"$group": {"_id": "$user_id"}},
        ]))

        print("Result from query 4: \n")
        self.tabulate_queries(result, ['Taxi Users', 'Count'])

    def q5(self):
        result = list(self.db[self.collection_activity].aggregate([
            {"$match": {"transportation_mode": {"$ne": None}}},
            {"$group": {"_id": "$transportation_mode", "count": {"$sum": 1}}},
            {"$sort": {"count": -1}},
        ]))

        print("Result from query 5: \n")
        self.tabulate_queries(result, ['Transportation Mode', 'Count'])

    def q6(self):
        resultA = list(self.db[self.collection_activity].aggregate([
            {"$group": {"_id": {"$year": "$start_date_time"}, "count": {"$sum": 1}}},
            {"$sort": {"count": -1}},
            {"$limit": 1}
        ]))

        print("Result from query 6 a: \n")
        self.tabulate_queries(resultA, ['Year with most activities:', 'Count'])

        resultB = list(self.db[self.collection_activity].aggregate([
            {"$group": {"_id":
                        {"$year": "$start_date_time"},
                        "count":
                        {"$sum": {"$dateDiff": {"startDate": "$start_date_time",
                         "endDate": "$end_date_time", "unit": "hour"}}}}},
            {"$sort": {"count": -1}},
            {"$limit": 1}
        ]))

        print("Result from query 6 b: \n")
        self.tabulate_queries(resultB, ['Year with most hours:', 'Count'])

    def q7(self):
        correct_activities = list(self.db[self.collection_activity].aggregate([
            {"$match": {"user_id": "112", "transportation_mode": "walk"}}
        ]))

        matching_trackpoints = []
        i = 0
        for _ in correct_activities:
            if (correct_activities[i]["start_date_time"].strftime("%Y") == "2008"):
                for tp in correct_activities[i]['tp_ids_connected_to_activity']:
                    matching_trackpoints.append(tp)
            i += 1

        all_matching_trackpoints = list(self.db[self.collection_trackpoint].aggregate([
            {"$match": {"_id": {"$in": matching_trackpoints}}}
        ]))

        distance_traveled = 0
        for trackpnt in range(len(all_matching_trackpoints)-1):

            start_location = (
                all_matching_trackpoints[trackpnt]['lat'],
                all_matching_trackpoints[trackpnt]['lon'])
            end_location = (all_matching_trackpoints[trackpnt + 1]
                            ['lat'], all_matching_trackpoints[trackpnt + 1]['lon'])
            distance_traveled += haversine(start_location, end_location)

        print("Result from query 7: \n")
        print("Total distance (in km) walked in 2008, by user = 112:",
              round(distance_traveled, 2))

    def q8(self):
        result = list(self.db[self.collection_activity].aggregate([
            {'$lookup': {
                'from': 'TrackPoint',
                'localField': "tp_ids_connected_to_activity",
                'foreignField': "_id",
                'as': 'tp'
            }},
            {"$unwind": "$tp"},
            {"$group": {"_id": {"user_id": "$user_id", "altitude": {
                "$multiply": ["$tp.altitude", 0.3048]}}}},
            {"$sort": {"_id.user_id": 1}},
        ]))

        dic = {}
        for element in result:
            if element['_id']['user_id'] in dic:
                dic[element['_id']['user_id']].append(
                    round(element['_id']['altitude']))
            else:
                dic[element['_id']['user_id']] = [
                    round(element['_id']['altitude'])]

        caluclated_gained = {}
        for k, v in dic.items():
            gained = 0
            for x, y in zip(v[0::], v[1::]):
                difference = y - x
                if (x != -777) or (y != -777) or (difference > 0):
                    gained += difference

            caluclated_gained[k] = gained

        sorted_by_altitude = dict(
            sorted(caluclated_gained.items(), key=lambda item: item[1], reverse=True))

        formatted_results = []
        stop = 0
        for k, v in sorted_by_altitude.items():
            if stop == 20:
                break
            formatted_results.append((k, v))
            stop += 1

        print("Result from query 8: \n")
        print(tabulate(formatted_results, [
              "User", "Altitude gained (in meters)"]))

    def q9(self):
        result = list(self.db[self.collection_activity].aggregate([
            {'$lookup': {
                'from': 'TrackPoint',
                'localField': "tp_ids_connected_to_activity",
                'foreignField': "_id",
                'as': 'tp'
            }},
            {"$unwind": "$tp"},
            {"$match": {"tp.lat": {"$gte": 39.916000, "$lte": 39.916999},
                        "tp.lon": {"$gte": 116.397000, "$lte": 116.397999}}},

            {"$group": {"_id": "$user_id", }},
        ]))

        print("Result from query 10: \n")
        self.tabulate_queries(result, [
            "Users which entered the Forbidden City", "Count"])

    def q10(self):
        result = list(self.db[self.collection_activity].aggregate([
            {"$match": {"transportation_mode": {"$ne": None}, }},
            {"$group": {"_id": {"user_id": "$user_id",
                                "transportation_mode": "$transportation_mode"},
                        "count": {"$sum": 1}}},
            {"$sort": {"_id.user_id": 1, "count": -1}},
        ]))

        transportation_count = {}

        for element in result:
            key = element.get("_id").get("user_id")
            if key not in transportation_count:
                transportation_count[key] = element.get(
                    "_id").get("transportation_mode")

        formatted_results = []
        for k, v in transportation_count.items():
            formatted_results.append((k, v))

        print("Result from query 11: \n")
        print(tabulate(formatted_results, [
              "User", "Most Used Transportation Mode"]))

    def view_top10_data(self):
        result = list(self.db[self.collection_user].aggregate([
            {"$limit": 10}
        ]))

        print("Users Top 10: \n")
        self.tabulate_queries(result, ["id", " has_labels"])
        print()
        result = list(self.db[self.collection_activity].aggregate([
            {"$project": {"_id": "$_id", "user_id": "$user_id",
                          "transportation_mode": "$transportation_mode",
                          "start_date_time": "$start_date_time",
                          "end_date_time": "$end_date_time"}},

            {"$limit": 10}
        ]))

        print("Activity Top 10: \n")
        self.tabulate_queries(result, [
                              "id", "user_id", "transportation_mode",
                              "start_date_time", "end_date_time"])

        result = list(self.db[self.collection_trackpoint].aggregate([
            {"$limit": 10}
        ]))

        print()
        print("TrackPoint Top 10: \n")
        self.tabulate_queries(
            result, ["id", "activity_id", "lat", "lon", "altitude", "datetime"])

    def tabulate_queries(self, result, headers):
        rows = [_.values() for _ in result]
        print(tabulate(rows, headers))


def main():
    mongoquery = None
    try:
        mongoquery = MongoMain()
        # mongoquery.q1()
        # mongoquery.q2()
        # mongoquery.q3()
        # mongoquery.q4()
        # mongoquery.q5()
        # mongoquery.q6()
        # mongoquery.q7()
        # mongoquery.q8()
        # mongoquery.q9()
        # mongoquery.q10()

        # mongoquery.view_top10_data()

    except Exception as e:
        print("ERROR: Failed to use database:", e)
    finally:
        if mongoquery:
            mongoquery.connection.close_connection()


if __name__ == '__main__':
    main()
