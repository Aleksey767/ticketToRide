package com.andersen.ticketToRide.graph;

public class Edge {

    Node adjacentNode;

    int weight;

    Edge(Node adjacentNode, int weight) {
        this.adjacentNode = adjacentNode;
        this.weight = weight;
    }
}