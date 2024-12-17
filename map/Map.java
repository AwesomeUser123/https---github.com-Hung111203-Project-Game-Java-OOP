package map;
import auxiliary.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Map {
    private House[][] house;
    public Map() {
        house = new House[9][9];
        for (int i = 1; i < 9; i += 2) { // Loop through each room-wall-room row
            for (int j = 1; j < 9; j += 2) { // Loop through each object in a row
                house[i][j] = new Room();
                house[i][j].setType("Room"); //room

                house[i][j + 1] = new Wall(); //wall
            }
        }

        for (int i = 2; i < 9; i += 2) { // Loop through wall-only row
            for (int j = 1; j < 9; j += 2) { // Loop through each object in a row
                house[i][j] = new Wall();

                house[i][j + 1] = new House();
                house[i][j + 1].setType("Empty");  //intersection of walls
            }
        }
            
        for (int i = 1; i <= 7; i++) {
            house[0][i] = new Wall();
            house[0][i].setType("_");
            house[8][i] = new Wall();
            house[8][i].setType("_");
        }

        for (int i = 1; i <= 7; i++) {
            house[i][0] = new Wall();
            house[i][0].setType("|");
            house[i][8] = new Wall();
            house[i][8].setType("|");
        }
        
        house[0][0] = new Wall(); house[0][0].setType("E");
        house[0][8] = new Wall(); house[0][8].setType("E");
        house[8][0] = new Wall(); house[8][0].setType("E");
        house[8][8] = new Wall(); house[8][8].setType("E");
        
        }

    public void generateMap(int magicNums)  {
        PossibleGameplay("Rabbit");
        PossibleGameplay("Rat");
        setMagicWall(magicNums); 
        //fill the remaining valid wall (if any)
        Random random = new Random();
        String[] Types= {"Free","Block","Rabbit","Block","Rat","Block"};          
        for (int i = 1; i <=7; i ++) { // Loop through wall-only row
            for (int j = 1; j <=7; j ++) { // Loop through each object in a row
                if (house[i][j].getType()==null)    {
                    house[i][j].setType(Types[random.nextInt(Types.length)]);
                }
            }
        }
    }
    void setMagicWall(int magicNums)     {
        Random random = new Random();
        Set<int[]> wallsIndex = Auxiliary.generateDistinctArrays(magicNums);
        for (int[] wallid : wallsIndex) {
            House houseWall = house[wallid[0]][wallid[1]];
            houseWall.setType("Magic");
            Wall wall = (Wall)houseWall;
            int[][] neigboringRooms = wall.findNeighbors(house, wallid[0], wallid[1]);
            int choice = random.nextInt(2);
            wall.setAccessible(neigboringRooms[choice]);
        }
    }    


    public void Display() {
        for (int i = 0; i < 9; i++) { // Loop through each row
            for (int j = 0; j < 9; j++) { // Loop through each object in a row
                System.out.print(house[i][j].getType() + "\t");
            }
            System.out.println();
        }
    }
    


    private void PossibleGameplay(String AnimalType) {
    int[][] connectedToRoot = new int[9][9]; connectedToRoot[1][1]=1;
    Random rand = new Random();
    int[][] adjacentSteps = {{0, -2}, {0, 2}, {-2, 0}, {2, 0}};
    List<int[]> rooms = new ArrayList<>();
    List<int[]> adjacentRooms = new ArrayList<>();
    int[] initialRoom = {1, 1};
    adjacentRooms.add(initialRoom);
    while (rooms.size() < 16) {
        if (!adjacentRooms.isEmpty()) {
            int choice = rand.nextInt(adjacentRooms.size());
            int[] ChosenRoom = adjacentRooms.get(choice);
            adjacentRooms.remove(choice);
            rooms.add(ChosenRoom);
            for (int i = 0; i < 4; i++) {
                int nextRoomRow = ChosenRoom[0] + adjacentSteps[i][0];
                int nextRoomCol = ChosenRoom[1] + adjacentSteps[i][1];
                if ((nextRoomRow < 1 || nextRoomRow > 8) || nextRoomCol < 1 || nextRoomCol > 8) {
                    continue;
                }
                int[] adjacentChosenRoom = {nextRoomRow, nextRoomCol};
                if (!containsArray(rooms, adjacentChosenRoom)) {
                    if (!containsArray(adjacentRooms, adjacentChosenRoom)) { // avoid duplication
                        adjacentRooms.add(adjacentChosenRoom);
                    }
                }
                // modify the wall because adjacent of the chosen room already in room list
                else if (connectedToRoot[ChosenRoom[0]][ChosenRoom[1]]!=1 
                        || connectedToRoot[adjacentChosenRoom[0]][adjacentChosenRoom[1]]!=1){

                    int WalRow = (ChosenRoom[0] + adjacentChosenRoom[0]) / 2;
                    int WalCol = (ChosenRoom[1] + adjacentChosenRoom[1]) / 2;

                    if (house[WalRow][WalCol].getType()==null) {
                        house[WalRow][WalCol].setType(AnimalType);
                    } else {
                        house[WalRow][WalCol].setType("Free");
                    }
                    connectedToRoot[ChosenRoom[0]][ChosenRoom[1]] = 1;
                    connectedToRoot[adjacentChosenRoom[0]][adjacentChosenRoom[1]]=1;
                    if (house[ChosenRoom[0]][ChosenRoom[1]] instanceof Room)    {
                        Room thisRoom = (Room) house[ChosenRoom[0]][ChosenRoom[1]];
                        int[] temp={ChosenRoom[0],ChosenRoom[1]};
                        thisRoom.setSource(temp);
                    }
                }
            }

        }
    }
}

// Custom method to check if a list contains an array with the same content
    public static boolean containsArray(List<int[]> list, int[] array) {
    for (int[] item : list) {
        if (arraysEqual(item, array)) {
            return true;
        }
    }
    return false;
}

// Custom method to check if two arrays are equal
    public static boolean arraysEqual(int[] a, int[] b) {
        if (a == b) return true;
        if (a == null || b == null) return false;
        if (a.length != b.length) return false;
        for (int i = 0; i < a.length; i++) {
            if (a[i] != b[i]) return false;
        }
        return true;
    }
   
}

class House {
    private String type;
    public void setType(String type) {
        this.type = type;
    }
    
    public String getType() {
        return type;
    }
    public int[][] findNeighbors(House[][] house, int rowIndex, int colIndex)     
    {
        return new int[0][0];
    }
}

class Wall extends House {
    private int[] accessibleFrom;
    Wall()  {
        accessibleFrom = new int[2];
    }
    public void setAccessible(int[] roomid)    {
        accessibleFrom[0] = roomid[0];
        accessibleFrom[1] = roomid[1];
    }
    public int[] accessibleFrom()  {
        return accessibleFrom;
    }
    @Override
    public int[][] findNeighbors(House[][] house, int rowIndex, int colIndex)    {
        int[][] result = new int[2][2];
        int[][] step = {{0,-1},{0,1},{-1,0},{1,0}};
        int curRow=rowIndex; int curCol=colIndex;
        int currentIndex=0;
        for (int i=0;i<4;i++)   {
            int nextRow=step[i][0]+curRow;
            int nextCol=step[i][1]+curCol;
            if (nextRow>=1 && nextRow<=7 && nextCol>=1 && nextCol<=7)   {
                if (house[nextRow][nextCol] instanceof Room)   {
                    result[currentIndex][0] = nextRow;
                    result[currentIndex][1] = nextCol;
                    currentIndex++;
                }
            }
        }
        return result;
    }
}
class Room extends House {
    List<int[]> SourceRoom = new ArrayList<>();
    public void setSource(int[] SourceRoom)     {
        int temp[] = {SourceRoom[0],SourceRoom[1]};
        this.SourceRoom.add(temp);
    }
    public List<int[]> getSource()     {
        return SourceRoom;
    }
    @Override
    public int[][] findNeighbors(House[][] house, int rowIndex, int colIndex)    {
        int[][] result = new int[4][2];
        int[][] step = {{0,-2},{0,2},{-2,0},{2,0}};
        int curRow=rowIndex; int curCol=colIndex;
        for (int i=0;i<4;i++)   {
            int nextRow=step[i][0]+curRow;
            int nextCol=step[i][1]+curCol;
            if (nextRow>=1 && nextRow<=7 && nextCol>=1 && nextCol<=7)   {
                result[i][0] = nextRow;
                result[i][1] = nextCol;
            }
        }
        return result;
    }
    
}
