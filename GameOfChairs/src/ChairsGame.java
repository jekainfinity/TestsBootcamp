import java.util.ArrayList;
import java.util.List;

public class ChairsGame {

    public static void main(String[] args) {
        List<Chair> chairs = new ArrayList<Chair>();
        int chairsCount = 100;

        for (int i = 0; i < chairsCount; i++) {
            chairs.add(new Chair(i+1));
        }

        int count = 0;
        int lastDeleted = 0;

        while (chairs.size() != 1) {
            int deleted = (lastDeleted + count) % chairs.size();
            chairs.remove(deleted);

            lastDeleted = deleted;
            count = count + 1;
        }

        System.out.println(chairs.get(0).getNumber());

        System.out.println("end");

    }
}
