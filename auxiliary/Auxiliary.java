package auxiliary;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Auxiliary {
    public static Set<int[]> generateDistinctArrays(int n) {
        Set<int[]> arrays = new HashSet<>();
        Random random = new Random();
        int[] oddNumbers = {1, 3, 5, 7};
        int[] evenNumbers = {2,4,6};
        while (arrays.size() < n) {
            int[] array = new int[2];
            array[0] = (random.nextInt(7))+1;
            if (array[0]%2==0)   {
                array[1] = oddNumbers[random.nextInt(oddNumbers.length)];
            }
            else    {
                array[1] = evenNumbers[random.nextInt(evenNumbers.length)];
            }
            boolean isDistinct = true;
            for (int[] existingArray : arrays) {
                if (array[0] == existingArray[0] && array[1] == existingArray[1]) {
                    isDistinct = false;
                    break;
                }
            }

            if (isDistinct) {
                arrays.add(array);
            }
        }

        return arrays;
    }
}
