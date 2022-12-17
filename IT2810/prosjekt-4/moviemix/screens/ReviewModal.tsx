import { NavigationProp, ParamListBase } from "@react-navigation/native";
import ReviewCard from "../components/ReviewCard";
import { IMovie } from "../model/Interfaces";

/**
 * @param navigation
 * @param route
 * @returns a modal that shows a review of a movie
 */
export default function ReviewModal({
  route,
  navigation,
}: {
  route: any;
  navigation: NavigationProp<ParamListBase>;
}) {
  const { movie }: { movie: IMovie } = route.params;
  return <ReviewCard movie={movie} navigation={navigation} />;
}
