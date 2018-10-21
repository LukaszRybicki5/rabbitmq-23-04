package sender.filesController.propertiesChekers;

/*
interfejs do sprawdzania wartości Batch Size i interwału
 */
public interface ValueVerifier {
    boolean isIntegerAdequate(int integerToVerify);
}
