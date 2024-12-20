import map.*;
public class LocalTest {
    public static void main(String[] args) {
        Map mapping = new Map();
        mapping.generateMap(3);
        mapping.Display();
        House[][] result = mapping.getHouse();
        int[][] magicLists = mapping.getMagicLists();
    }
}