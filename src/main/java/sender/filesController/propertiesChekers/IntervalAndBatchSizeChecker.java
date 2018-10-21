package sender.filesController.propertiesChekers;
/*
Klasa sprawdza ilość linijek i długość interwału przeszukiwań
 */
public class IntervalAndBatchSizeChecker implements ValueVerifier {

    @Override
    public boolean isIntegerAdequate(int integerToVerify) {

        if ((integerToVerify < 0) || (integerToVerify > 1000)) {
            return false;
        } else return true;
    }
}
