import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FindPeps {
    public static void main(String[] args) {
        WorkWithPeps workWithPeps = new WorkWithPeps();
        List<Payment> payments;
        List<String> peps;
        List<Payment> alreadyPep = new ArrayList<Payment>();
        try {
            payments = workWithPeps.getPayments();
            peps =  workWithPeps.getPeps().getPepList();

            for (Payment pa : payments) {
                for (String pep: peps) {
                    String pepName = pep.split(" - ")[0];
                    String pepCountry = pep.split(" - ")[1];

                    if (pa.getRecipientCountry().equals(pepCountry) && pa.getRecipientName().equals(pepName)) {
                        System.out.println(pa.getRecipientName());
                        workWithPeps.markTransactionPEP(pa.getId());
                        alreadyPep.add(pa);
                    }
                }
            }

            for (Payment pa: payments) {
                Boolean status = false;
                for (Payment alreadyPa: alreadyPep) {
                    if (pa.getId().equals(alreadyPa.getId())) {
                        status = true;
                    }
                }

                if (!status) {
                    workWithPeps.deleteTransactionAsNotPEP(pa.getId());
                    System.out.println(pa.getRecipientName());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
