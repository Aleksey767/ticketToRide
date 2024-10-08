package com.andersen.ticketToRide.graph;

import com.andersen.ticketToRide.enums.Cities;
import lombok.Getter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;

/**
 * The {@code CalculatePrice} class provides methods for calculating the shortest path
 * between cities and determining the price of travel based on the number of segments.
 */
@Getter
public class CalculatePrice {

    private final static Map<Cities, Integer> citiesMap = Map.ofEntries(
            Map.entry(Cities.BRISTOL, 1),
            Map.entry(Cities.BIRMINGHAM, 2),
            Map.entry(Cities.SWINDEN, 3),
            Map.entry(Cities.COVENTRY, 4),
            Map.entry(Cities.READING, 5),
            Map.entry(Cities.NORTHAMPTON, 6),
            Map.entry(Cities.LONDON, 7)
    );

    int[][] graphData =
            {
                    {1, 2, 3},
                    {1, 3, 2},
                    {2, 1, 3},
                    {2, 3, 4},
                    {2, 4, 1},
                    {3, 1, 2},
                    {3, 2, 4},
                    {3, 5, 4},
                    {4, 2, 1},
                    {4, 6, 2},
                    {5, 3, 4},
                    {5, 7, 1},
                    {6, 4, 2},
                    {6, 7, 2},
                    {7, 6, 2},
                    {7, 5, 1},
            };

    HashMap<Integer, Node> graph = createGraph(graphData);

    /**
     * Starts the calculation process to determine the travel price between two cities.
     *
     * @param departure the city of departure
     * @param arrival the city of arrival
     * @return the calculated price of travel
     */
    public int startCalculationProcess(Cities departure, Cities arrival) {

        Node startNode = graph.get(citiesMap.get(departure));
        Node endNode = graph.get(citiesMap.get(arrival));

        LinkedList<Node> shortestPath = getShortestPath(graph, startNode, endNode);
        int segments = calculateSumOfWeight(shortestPath);

        return calculatePrice(segments);
    }

    /**
     * Calculates the price based on the number of segments.
     *
     * @param segmentAmount the number of segments
     * @return the calculated price
     */
    public int calculatePrice(int segmentAmount) {
        int price = 0;

        while (segmentAmount > 0) {
            if (segmentAmount >= 3) {
                price += 10;
                segmentAmount -= 3;
            } else if (segmentAmount == 2) {
                price += 7;
                segmentAmount -= 2;
            } else {
                price += 5;
                segmentAmount -= 1;
            }
        }
        return price;
    }

    /**
     * Calculates the sum of weights of the edges in the shortest path.
     *
     * @param shortestPath the shortest path
     * @return the sum of weights
     */
    public int calculateSumOfWeight(LinkedList<Node> shortestPath) {
        int sum = 0;

        for (int i = 0; i < shortestPath.size() - 1; i++) {
            Node current = shortestPath.get(i);
            Node next = shortestPath.get(i + 1);

            Edge edge = findEdgeBetweenNodes(current, next);
            assert edge != null;
            sum += edge.weight;
        }
        return sum;
    }

    /**
     * Finds the edge between two nodes.
     *
     * @param from the starting node
     * @param to the ending node
     * @return the edge between the nodes, or null if no edge exists
     */
    private Edge findEdgeBetweenNodes(Node from, Node to) {
        for (Edge edge : from.edges) {
            if (edge.adjacentNode.equals(to)) {
                return edge;
            }
        }
        return null;
    }

    /**
     * Uses Dijkstra's algorithm to find the shortest path between two nodes.
     *
     * @param start the starting node
     * @param end the ending node
     * @param timeToNodes a map of nodes and their times
     * @return the shortest path as a linked list of nodes
     */
    LinkedList<Node> dijkstraAlgorithm(Node start, Node end, HashMap<Node, Integer> timeToNodes) {
        LinkedList<Node> path = new LinkedList<>();
        Node node = end;
        while (node != start) {
            int minTimeToNode = timeToNodes.get(node);
            System.out.println(minTimeToNode);
            path.addFirst(node);
            for (Map.Entry<Node, Edge> parentAndEdge : node.parents.entrySet()) {
                Node parent = parentAndEdge.getKey();
                Edge parentEdge = parentAndEdge.getValue();
                if (!timeToNodes.containsKey(parent)) {
                    continue;
                }
                boolean prevNodeFound = (parentEdge.weight + timeToNodes.get(parent)) == minTimeToNode;
                if (prevNodeFound) {
                    timeToNodes.remove(node);
                    node = parent;
                    break;
                }
            }
        }
        path.addFirst(node);
        return path;
    }

    /**
     * Gets the shortest path between two nodes in the graph.
     *
     * @param graph the graph as a map of nodes
     * @param start the starting node
     * @param end the ending node
     * @return the shortest path as a linked list of nodes, or null if no path exists
     */
    LinkedList<Node> getShortestPath(HashMap<Integer, Node> graph, Node start, Node end) {
        HashSet<Node> unprocessedNodes = new HashSet<>();
        HashMap<Node, Integer> timesToNodes = new HashMap<>();
        initHashTables(start, graph, unprocessedNodes, timesToNodes);
        calculateTimeToEachNode(unprocessedNodes, timesToNodes);
        if (timesToNodes.get(end) == Integer.MAX_VALUE) {
            return null;
        }
        return dijkstraAlgorithm(start, end, timesToNodes);
    }

    /**
     * Calculates the time to each node in the graph.
     *
     * @param unprocessedNodes the set of unprocessed nodes
     * @param timeToNodes the map of nodes and their times
     */
    void calculateTimeToEachNode(HashSet<Node> unprocessedNodes, HashMap<Node, Integer> timeToNodes) {
        while (!unprocessedNodes.isEmpty()) {
            Node node = getNodeWithMinTimeToIt(unprocessedNodes, timeToNodes);
            if (timeToNodes.get(node) == Integer.MAX_VALUE) {
                return;
            }
            for (Edge edge : node.edges) {
                Node adjacentNode = edge.adjacentNode;
                if (unprocessedNodes.contains(adjacentNode)) {
                    int timeToCheck = timeToNodes.get(node) + edge.weight;
                    if (timeToCheck < timeToNodes.get(adjacentNode)) {
                        timeToNodes.put(adjacentNode, timeToCheck);
                    }
                }
            }
            unprocessedNodes.remove(node);
        }
    }

    /**
     * Gets the node with the minimum time to it.
     *
     * @param unprocessedNodes the set of unprocessed nodes
     * @param timeToNodes the map of nodes and their times
     * @return the node with the minimum time to it
     */
    Node getNodeWithMinTimeToIt(HashSet<Node> unprocessedNodes, HashMap<Node, Integer> timeToNodes) {
        Node nodeWithMinTimeToIt = null;
        int minTime = Integer.MAX_VALUE;
        for (Node node : unprocessedNodes) {
            int time = timeToNodes.get(node);
            if (time < minTime) {
                minTime = time;
                nodeWithMinTimeToIt = node;
            }
        }
        return nodeWithMinTimeToIt;
    }

    /**
     * Initializes hash tables for the Dijkstra algorithm.
     *
     * @param start the starting node
     * @param graph the graph as a map of nodes
     * @param unprocessedNodes the set of unprocessed nodes
     * @param timesToNodes the map of nodes and their times
     */
    void initHashTables(Node start, HashMap<Integer, Node> graph,
                        HashSet<Node> unprocessedNodes,
                        HashMap<Node, Integer> timesToNodes) {
        for (Map.Entry<Integer, Node> mapEntry : graph.entrySet()) {
            Node node = mapEntry.getValue();
            unprocessedNodes.add(node);
            timesToNodes.put(node, Integer.MAX_VALUE);
            timesToNodes.put(start, 0);
        }
    }

    /**
     * Creates a graph from the provided data.
     *
     * @param graphData the data representing the graph
     * @return a map of nodes representing the graph
     */
    HashMap<Integer, Node> createGraph(int[][] graphData) {
        HashMap<Integer, Node> graph = new HashMap<>();
        for (int[] row : graphData) {
            Node node = addOrGetNode(graph, row[0]);
            Node adjacentNode = addOrGetNode(graph, row[1]);

            if (adjacentNode == null) {
                continue;
            }
            Edge edge = new Edge(adjacentNode, row[2]);
            node.edges.add(edge);
            adjacentNode.parents.put(node, edge);
        }
        return graph;
    }

    /**
     * Adds a node to the graph or retrieves it if it already exists.
     *
     * @param graph the graph as a map of nodes
     * @param value the value of the node
     * @return the added or retrieved node
     */
    public Node addOrGetNode(HashMap<Integer, Node> graph, Integer value) {
        if (value == -1) {
            return null;
        }
        if (graph.containsKey(value)) {
            return graph.get(value);
        }
        Node newNode = new Node(value);
        graph.put(value, newNode);
        return newNode;
    }
}