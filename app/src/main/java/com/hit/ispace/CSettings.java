package com.hit.ispace;

public abstract class CSettings {
    public static class Animation{
        public static final int BOMB_ANIMATION = 1;
        public static final int ROCK_ANIMATION = 2;
    }

    public static class Speed{
        public static final double SLOW = 1;
        public static final double REGULAR = 1.25;
        public static final double FAST = 1.5;

    }

    public static class LevelTypes{
        public static final int FREE_STYLE = 1;
        public static final int FASTER = 2;
        public static final int GETTING_SICK = 3;
    }

    public static class RequestCodes{
        public static final int GAME_ENDED = 1;
        public static final int LEVEL_NOT_EXIST = 2;
        public static final int START_GAME = 3;
    }

    public static class Dimension {
        public static final int SPACESHIP_SIZE = 115;
        public static final int ELEMENT_SIZE = 115;
    }

    public static class Margin {
        public static final int TOP = -(Dimension.ELEMENT_SIZE);
    }

    public static class Strength {
        public static final double HALF = 0.5;
    }

    public enum FlyingElements{
        COIN,
        GOODBOMB,
        BOMB,
        ROCK,
        SUPERROCK,
        SPACE
    }
}
