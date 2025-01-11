package io.github.GRUMv2.EngSim.entities;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import com.badlogic.gdx.math.Vector2;

/**
 * BuildingFactory
 */
public final class BuildingFactory {

    private static BuildingFactory instance;

    public enum Available {
        GYM(Gym.class),
        HALLS(Halls.class),
        LECTUREHALL(LectureHall.class),
        PUB(Pub.class),
        RESTAURANT(Restaurant.class);

        private Class<? extends Building> building;
        private static final HashMap<Class<? extends Building>, Available> lookup = new HashMap<Class<? extends Building>, Available>();

        static {
            for (Available d : Available.values()) {
                lookup.put(d.getBuildingClass(), d);
            }
        }


        private Available(Class<? extends Building> building) {
            this.building = building;
        }

        private Building getBuilding(Vector2 mapPos) throws
                    NoSuchMethodException,
                    InvocationTargetException,
                    InstantiationException,
                    IllegalAccessException  {
            return this.building.getDeclaredConstructor(Vector2.class).newInstance(mapPos);
        }

        private Class<? extends Building> getBuildingClass() {
            return this.building;
        }

        @Override
        public String toString() {
            return this.building.getSimpleName();
        }

        public static Available get(Class<? extends Building> building) {
            return lookup.get(building);
        }

    }

    private BuildingFactory() {
    }

    public static BuildingFactory getInstance() {
        if (instance == null) {
            instance = new BuildingFactory();
        }
        return instance;
    }

    public Building newBuilding(Available building, Vector2 mapPos) {
        try {
            return building.getBuilding(mapPos);
        } catch (NoSuchMethodException |
                 InvocationTargetException |
                 InstantiationException |
                 IllegalAccessException e) {
            System.out.println(e);
            return null;
        }
    }
}
