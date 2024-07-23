package com.andersen.ticketToRide.graph;

import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
@Getter
public class Node {

    int value;

    LinkedHashSet<Edge> edges = new LinkedHashSet<>();

    LinkedHashMap<Node, Edge> parents = new LinkedHashMap<Node, Edge>();

    public Node(int value) {
        this.value = value;
    }
}