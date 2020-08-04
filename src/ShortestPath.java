/**
 * Class that represents a valid path if there is one found for a map object. If
 * there is no valid path, an appropriate message will be delivered.
 * 
 * @author Jacob Chun
 *
 */
public class ShortestPath {

	/**
	 * Instance variable of the desired map to be processed
	 */
	CityMap cityMap;

	/**
	 * Constructor of the class creates a map object
	 * 
	 * @param theMap the desired map to be processed
	 */
	public ShortestPath(CityMap theMap) {
		cityMap = theMap;
	}

	/**
	 * the findPath method will implement the use of an ordered circular array to
	 * find a valid path to the destination if there is one. Method prints the
	 * number of cells in the path including start and finish if there was a valid
	 * path.
	 */
	public void findShortestPath() {
		try {
			OrderedCircularArray<MapCell> list = new OrderedCircularArray<MapCell>();
			MapCell startingCell = cityMap.getStart();
			list.insert(startingCell, 0);
			startingCell.markInList();
			boolean found = false;

			/*
			 * while loop will implement an algorithm to loop through the cells of the
			 * desired map. This loop makes use of the nextCell method to find the next best
			 * cell to travel down
			 */
			while (!list.isEmpty()) {
				MapCell cell = list.getSmallest();
				cell.markOutList();

				if (cell.isDestination()) {
					System.out.println(
							"Destination found: There were " + (cell.getDistanceToStart() + 1) + " cells in the path");
					found = true;
					break;
				}

				// algorithm to organize cells to travel down based on distance to
				// starting cell
				for (int i = 0; i < 4; i++) {
					MapCell neighbour = nextCell(cell);
					if (neighbour != null && !neighbour.isBlock() && !neighbour.isMarked()) {
						int distance = 1 + cell.getDistanceToStart();
						if (neighbour.getDistanceToStart() > distance) {
							neighbour.setDistanceToStart(distance);
							neighbour.setPredecessor(cell);
						}
						int distance2 = neighbour.getDistanceToStart();
						if (neighbour.isMarkedInList() && (distance2 < list.getValue(neighbour))) {
							list.changeValue(neighbour, distance2);
						}
						if (!neighbour.isMarkedInList()) {
							list.insert(neighbour, distance2);
							neighbour.markInList();
						}
					}
				}
			}
			if (!found) {
				System.out.println("No path to the destination was found!");
			}
		}

		// catches any exception that may be thrown at runtime
		catch (IllegalArgumentException e) {
			System.out.println("The map could not be processed! There was an error along the way.");
			System.exit(0);
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("The map could not be processed! There was an error along the way.");
			System.exit(0);
		} catch (Exception e) {
			System.out.println("The map could not be processed! There was an error along the way.");
			System.exit(0);
		}

	}

	/**
	 * This method will find and return the a valid next cell to travel down. If no
	 * cell is found, it will return null.
	 * 
	 * @param cell the cell that you want to find possible adjacent travel paths to
	 * @return a valid cell to travel down
	 */
	private MapCell nextCell(MapCell cell) {
		try {
			for (int i = 0; i < 4; i++) {
				MapCell adjacentCell = cell.getNeighbour(i);
				if (adjacentCell == null) {
					continue;
				}
				if ((cell.isStart() || cell.isIntersection() || cell.isNorthRoad())
						&& (adjacentCell.isDestination() || adjacentCell.isIntersection() || adjacentCell.isNorthRoad())
						&& i == 0 && !adjacentCell.isMarked())
					return adjacentCell;
				else if ((cell.isStart() || cell.isIntersection() || cell.isEastRoad())
						&& (adjacentCell.isDestination() || adjacentCell.isIntersection() || adjacentCell.isEastRoad())
						&& i == 1 && !adjacentCell.isMarked())
					return adjacentCell;
				else if ((cell.isStart() || cell.isIntersection() || cell.isSouthRoad())
						&& (adjacentCell.isDestination() || adjacentCell.isIntersection() || adjacentCell.isSouthRoad())
						&& i == 2 && !adjacentCell.isMarked())
					return adjacentCell;
				else if ((cell.isStart() || cell.isIntersection() || cell.isWestRoad())
						&& (adjacentCell.isDestination() || adjacentCell.isIntersection() || adjacentCell.isWestRoad())
						&& i == 3 && !adjacentCell.isMarked())
					return adjacentCell;
			}
			return null;
		}

		// catches any exception that may be thrown at runtime
		catch (IllegalArgumentException e) {
			System.out.println("The map could not be processed! There was an error along the way.");
			return null;
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("The map could not be processed! There was an error along the way.");
			return null;
		} catch (Exception e) {
			System.out.println("The map could not be processed! There was an error along the way.");
			return null;
		}
	}
}