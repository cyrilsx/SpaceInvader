package org.nexu.spaceinvader.behaviours.controller;

import org.nexu.spaceinvader.domain.Shape;

import java.util.*;

public class InFieldEnemyRegistry {

    public enum Group {
        ENEMY,
        PLAYER
    }

    private Map<Group, Set<Shape>> groups = new EnumMap<Group, Set<Shape>>(Group.class);

    public InFieldEnemyRegistry() {
           for(Group group : Group.values()) {
                groups.put(group, new HashSet<Shape>());
           }
    }


    public void addTo(Group group, Shape shape) {
        this.groups.get(group).add(shape);
    }


    public Collection<Shape> getShapeFromGroup(Group group) {
        return groups.get(group);
    }

}
